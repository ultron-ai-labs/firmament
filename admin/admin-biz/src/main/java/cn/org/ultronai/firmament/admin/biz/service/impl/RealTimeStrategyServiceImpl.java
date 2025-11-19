package cn.org.ultronai.firmament.admin.biz.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.org.ultronai.firmament.admin.biz.corntask.queue.SDQueueDynamicTaskService;
import cn.org.ultronai.firmament.admin.biz.corntask.queue.SDQueueStrategyRunner;
import cn.org.ultronai.firmament.admin.biz.model.realtimestrategy.*;
import cn.org.ultronai.firmament.admin.biz.service.AITradingService;
import cn.org.ultronai.firmament.admin.biz.service.RealTimeStrategyService;
import cn.org.ultronai.firmament.admin.biz.utils.PrefixUtils;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.OrderDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.RealTimeStrategyDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.BaseDO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.OrderDO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.RealTimeStrategyDO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.UserDO;
import cn.org.ultronai.firmament.commonapi.model.TimeUnitEnum;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 16:56
 */
@Service("realTimeStrategyService")
public class RealTimeStrategyServiceImpl extends BaseService implements RealTimeStrategyService {
    @Resource
    private RealTimeStrategyDAO       realTimeStrategyDAO;
    @Resource
    private AITradingService          aiTradingService;
    @Resource
    private SDQueueDynamicTaskService sdQueueDynamicTaskService;
    @Resource
    private OrderDAO                  orderDAO;

    /**
     * 获取实时数据模型
     *
     * @param accessToken accessToken
     * @param req req
     * @return 实时数据模型
     */
    @Override
    public DashboardResp dashboard(String accessToken, RealTimeStrategyDashboardReq req) {
        UserDO userDO = getUserDO(accessToken);
        String memberId = userDO.getMemberId();
        List<RealTimeStrategyDO> realTimeStrategies = realTimeStrategyDAO.queryByMemberId(memberId, req.getStrategyId());
        // 空数据不处理
        if (CollectionUtils.isEmpty(realTimeStrategies)) {
            return new DashboardResp();
        }

        // 返回模型
        DashboardResp dashboardResp = new DashboardResp();
        // 正在运行的模型数量
        dashboardResp.setRunningModel(String.valueOf((int) realTimeStrategies.stream().map(item -> item.getStrategyState().equals("running")).count()));
        // 初始总金额
        BigDecimal totalAmount = realTimeStrategies.stream().map(RealTimeStrategyDO::getOriginalAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        totalAmount = totalAmount.setScale(2, RoundingMode.HALF_UP);
        dashboardResp.setTotalAmount(totalAmount.toPlainString());
        // 佣金总金额
        BigDecimal commission = realTimeStrategies.stream().map(RealTimeStrategyDO::getCommission).filter(Objects::nonNull).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        dashboardResp.setCommission(commission.toPlainString());
        // 佣金比例
        BigDecimal commissionRatio = commission.multiply(BigDecimal.valueOf(100)).divide(totalAmount, 4, RoundingMode.HALF_UP);
        dashboardResp.setCommissionRatio(commissionRatio.toPlainString());
        // 剩余总金额(非滑点不去除佣金、不含浮动)
        BigDecimal currentTotalAmount = realTimeStrategies.stream().map(RealTimeStrategyDO::getCurrentAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        currentTotalAmount = currentTotalAmount.setScale(2, RoundingMode.HALF_UP);
        dashboardResp.setCurrentTotalAmount(currentTotalAmount.toPlainString());
        // 总收益（非滑点不去除佣金、不含浮动）
        BigDecimal totalProfit = currentTotalAmount.subtract(totalAmount);
        dashboardResp.setTotalProfit(totalProfit.toPlainString());
        // 总收益比例 总收益率（非滑点不去除佣金、不含浮动）
        BigDecimal totalProfitRatio = totalProfit.multiply(BigDecimal.valueOf(100)).divide(totalAmount, 4, RoundingMode.HALF_UP);
        dashboardResp.setTotalProfitRatio(totalProfitRatio.toPlainString());
        // 滑点总收益（不去除佣金、不含浮动）
        BigDecimal totalSlippageAmount = realTimeStrategies.stream().map(RealTimeStrategyDO::getCurrentSlippageAmount).filter(Objects::nonNull).reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
        dashboardResp.setTotalSlippageProfit(totalSlippageAmount.setScale(2, RoundingMode.HALF_UP).toPlainString());
        // 滑点总收益率（不去除佣金、不含浮动）
        BigDecimal totalSlippageRatio = totalSlippageAmount.multiply(BigDecimal.valueOf(100)).divide(totalAmount, 4, RoundingMode.HALF_UP);
        dashboardResp.setTotalSlippageProfitRatio(totalSlippageRatio.setScale(4, RoundingMode.HALF_UP).toPlainString());
        // 滑点收益差额 = 滑点总收益 - 总收益
        BigDecimal slippageProfitDifference = totalSlippageAmount.subtract(totalProfit);
        dashboardResp.setSlippageProfitDifference(slippageProfitDifference.setScale(2, RoundingMode.HALF_UP).toPlainString());
        // 滑点差额收益率 = 滑点收益差额 / 初始总金额
        BigDecimal slippageProfitDifferenceRatio = slippageProfitDifference.multiply(BigDecimal.valueOf(100)).divide(totalAmount, 4, RoundingMode.HALF_UP);
        dashboardResp.setSlippageProfitDifferenceRatio(slippageProfitDifferenceRatio.setScale(4, RoundingMode.HALF_UP).toPlainString());
        // 接近真实收益额 = 滑点总收益 - 佣金
        BigDecimal nearRealProfit = totalSlippageAmount.subtract(commission);
        dashboardResp.setNearRealProfit(nearRealProfit.setScale(2, RoundingMode.HALF_UP).toPlainString());
        // 接近真实收益比例 = 接近真实收益额 / 初始总金额
        BigDecimal nearRealProfitRatio = nearRealProfit.multiply(BigDecimal.valueOf(100)).divide(totalAmount, 4, RoundingMode.HALF_UP);
        dashboardResp.setNearRealProfitRatio(nearRealProfitRatio.setScale(4, RoundingMode.HALF_UP).toPlainString());
        // 返回数据
        return dashboardResp;
    }

    /**
     * 添加实时数据模型
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否添加成功
     */
    @Override
    public boolean addRealTimeModel(String accessToken, RealTimeStrategyAddReq req) {
        UserDO userDO = getUserDO(accessToken);
        // 校验名字是否有重复的
        RealTimeStrategyDO dbRecord = realTimeStrategyDAO.queryByName(userDO.getMemberId(), req.getStrategyName());
        if (dbRecord != null) {
            throw new RuntimeException("模型名字已存在");
        }
        RealTimeStrategyDO realTimeStrategyDO = new RealTimeStrategyDO();
        realTimeStrategyDO.setStrategyName(req.getStrategyName());
        realTimeStrategyDO.setStrategyId(PrefixUtils.genRealTimeModelId(userDO.getMemberId()));
        realTimeStrategyDO.setStrategyState(req.getStrategyState());
        realTimeStrategyDO.setModelId(req.getModelId());
        realTimeStrategyDO.setModelCategory(req.getModelCategory());
        realTimeStrategyDO.setOriginalAmount(BigDecimal.valueOf(req.getOriginalAmount()));
        realTimeStrategyDO.setCurrentAmount(BigDecimal.valueOf(req.getOriginalAmount()));
        realTimeStrategyDO.setPromptStrategyId(req.getPromptStrategyId());
        realTimeStrategyDO.setExchange(req.getExchange());
        realTimeStrategyDO.setCryptocurrencies(req.getCryptocurrencies());
        realTimeStrategyDO.setTimeUnit(req.getTimeUnit());
        realTimeStrategyDO.setAlertId(req.getAlertId());
        realTimeStrategyDO.setMemberId(userDO.getMemberId());
        realTimeStrategyDAO.insert(realTimeStrategyDO);
        return true;
    }

    /**
     * 更新实时数据模型
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否添加成功
     */
    @Override
    public boolean updateRealTimeModel(String accessToken, RealTimeStrategyAddReq req) {
        UserDO userDO = getUserDO(accessToken);
        // 如果判断数据库中的name是否有重复的
        RealTimeStrategyDO dbRecord = realTimeStrategyDAO.queryByName(req.getStrategyName(), userDO.getMemberId());
        if (dbRecord != null && !dbRecord.getStrategyState().equals(req.getStrategyId())) {
            throw new RuntimeException("模型名字已存在");
        }
        RealTimeStrategyDO realTimeStrategyDO = realTimeStrategyDAO.queryByStrategyId(req.getStrategyId());
        if (realTimeStrategyDO == null) {
            throw new RuntimeException("模型不存在");
        }
        realTimeStrategyDO.setStrategyName(req.getStrategyName());
        realTimeStrategyDO.setStrategyState(req.getStrategyState());
        realTimeStrategyDO.setModelId(req.getModelId());
        realTimeStrategyDO.setModelCategory(req.getModelCategory());
        realTimeStrategyDO.setOriginalAmount(BigDecimal.valueOf(req.getOriginalAmount()));
        realTimeStrategyDO.setCurrentAmount(BigDecimal.valueOf(req.getOriginalAmount()));
        realTimeStrategyDO.setPromptStrategyId(req.getPromptStrategyId());
        realTimeStrategyDO.setExchange(req.getExchange());
        realTimeStrategyDO.setCryptocurrencies(req.getCryptocurrencies());
        realTimeStrategyDO.setTimeUnit(req.getTimeUnit());
        realTimeStrategyDO.setAlertId(realTimeStrategyDO.getAlertId());
        realTimeStrategyDO.setMemberId(userDO.getMemberId());
        realTimeStrategyDAO.update(realTimeStrategyDO);
        // 如果在运行中的模型，则添加到动态任务中
        // TODO 处理任务
        if (realTimeStrategyDO.getStrategyState().equals("running")) {
            // sdQueueDynamicTaskService.updateTask(req.getExchange(), TimeUnitEnum.valueOf(req.getTimeUnit()), new SDQueueStrategyRunner(aiTradingService, realTimeStrategyDO));
        }
        return true;
    }

    /**
     * 启动实时数据模型
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否启动成功
     */
    @Override
    public boolean startRealTimeModel(String accessToken, RealTimeStrategyAddReq req) {
        UserDO userDO = getUserDO(accessToken);
        RealTimeStrategyDO realTimeStrategy = realTimeStrategyDAO.queryByStrategyId(req.getStrategyId());
        if (realTimeStrategy != null && realTimeStrategy.getMemberId().equals(userDO.getMemberId())) {
            realTimeStrategy.setStrategyState("running");
            realTimeStrategyDAO.update(realTimeStrategy);
            // 开启任务去执行AI分析
            // 建立数据库去插入数据，然后添加到内部数据中去
            sdQueueDynamicTaskService.addOrUpdateTask(req.getExchange(), TimeUnitEnum.valueOf(req.getTimeUnit()), new SDQueueStrategyRunner(aiTradingService, realTimeStrategy));
        }
        return false;
    }

    /**
     * 停止实时数据模型
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否停止成功
     */
    @Override
    public boolean stopRealTimeModel(String accessToken, RealTimeStrategyAddReq req) {
        UserDO userDO = getUserDO(accessToken);
        RealTimeStrategyDO realTimeStrategy = realTimeStrategyDAO.queryByStrategyId(req.getStrategyId());
        if (realTimeStrategy != null && realTimeStrategy.getMemberId().equals(userDO.getMemberId())) {
            realTimeStrategy.setStrategyState("stop");
            realTimeStrategyDAO.update(realTimeStrategy);
            // 停止任务
            sdQueueDynamicTaskService.removeTask(req.getExchange(), TimeUnitEnum.valueOf(req.getTimeUnit()), new SDQueueStrategyRunner(aiTradingService, realTimeStrategy));
        }
        return false;
    }

    /**
     * 获取正在运行的实时数据模型
     *
     * @param accessToken accessToken
     * @return 正在运行的实时数据模型
     */
    @Override
    public List<RealTimeStrategyAddResp> runningRealTimeStrategy(String accessToken) {
        UserDO userDO = getUserDO(accessToken);
        List<RealTimeStrategyDO> realTimeStrategyDOList = realTimeStrategyDAO.queryByMemberId(userDO.getMemberId(), StringUtils.EMPTY);

        return realTimeStrategyDOList.stream().map(item -> {
            RealTimeStrategyAddResp realTimeStrategyAddResp = new RealTimeStrategyAddResp();
            realTimeStrategyAddResp.setStrategyName(item.getStrategyName());
            realTimeStrategyAddResp.setStrategyId(item.getStrategyId());
            realTimeStrategyAddResp.setStrategyState(item.getStrategyState());
            realTimeStrategyAddResp.setModelId(item.getModelId());
            realTimeStrategyAddResp.setModelCategory(item.getModelCategory());
            realTimeStrategyAddResp.setOriginalAmount(item.getOriginalAmount().doubleValue());
            realTimeStrategyAddResp.setCurrentAmount(item.getCurrentAmount().doubleValue());
            realTimeStrategyAddResp.setPromptStrategyId(item.getPromptStrategyId());
            realTimeStrategyAddResp.setExchange(item.getExchange());
            realTimeStrategyAddResp.setCryptocurrencies(item.getCryptocurrencies());
            realTimeStrategyAddResp.setTimeUnit(item.getTimeUnit());
            realTimeStrategyAddResp.setAlertId(item.getAlertId());
            realTimeStrategyAddResp.setTotalProfitAndLossRatio(item.getTotalProfitAndLossRatio());
            realTimeStrategyAddResp.setAverageTime(item.getAverageTime());
            return realTimeStrategyAddResp;
        }).collect(Collectors.toList());
    }

    /**
     * 获取实时数据模型
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 实时数据模型
     */
    @Override
    public RealTimeStrategyLineResp realTimeStrategyLineChart(String accessToken, RealTimeStrategyLineReq req) {
        UserDO userDO = getUserDO(accessToken);
        // 查询当前策略所有订单数据
        RealTimeStrategyDO realTimeStrategyDO = realTimeStrategyDAO.queryByStrategyId(req.getStrategyId());
        if (realTimeStrategyDO == null || !realTimeStrategyDO.getMemberId().equals(userDO.getMemberId())) {
            throw new RuntimeException("策略不存在");
        }
        BigDecimal originalAmount = realTimeStrategyDO.getOriginalAmount();
        List<OrderDO> orders = orderDAO.queryByStrategyId(req.getStrategyId());
        // 顺序排列
        orders.sort(Comparator.comparing(BaseDO::getCreateTime));
        List<String> x = Lists.newArrayList();
        List<Double> y = Lists.newArrayList();
        for (OrderDO order : orders) {
            x.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateTime()));
            y.add(originalAmount.add(order.getTotalRevenue() == null ? BigDecimal.ZERO : order.getTotalRevenue()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }

        return new RealTimeStrategyLineResp(x, y);
    }
}
