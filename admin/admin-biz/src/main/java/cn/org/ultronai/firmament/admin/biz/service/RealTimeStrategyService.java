package cn.org.ultronai.firmament.admin.biz.service;

import java.util.List;

import cn.org.ultronai.firmament.admin.biz.model.realtimestrategy.*;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 16:56
 */
public interface RealTimeStrategyService {
    /**
     * 获取实时数据模型
     *
     * @param accessToken accessToken
     * @param req req
     * @return 实时数据模型
     */
    DashboardResp dashboard(String accessToken, RealTimeStrategyDashboardReq req);

    /**
     * 添加实时数据模型
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否添加成功
     */
    boolean addRealTimeModel(String accessToken, RealTimeStrategyAddReq req);

    /**
     * 更新实时数据模型
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否添加成功
     */
    boolean updateRealTimeModel(String accessToken, RealTimeStrategyAddReq req);

    /**
     * 启动实时数据模型
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否启动成功
     */
    boolean startRealTimeModel(String accessToken, RealTimeStrategyAddReq req);

    /**
     * 停止实时数据模型
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否停止成功
     */
    boolean stopRealTimeModel(String accessToken, RealTimeStrategyAddReq req);

    /**
     * 获取正在运行的实时数据模型
     *
     * @param accessToken accessToken
     * @return 正在运行的实时数据模型
     */
    List<RealTimeStrategyAddResp> runningRealTimeStrategy(String accessToken);

    /**
     * 获取实时数据模型
     *
     * @param accessToken accessToken
     * @param req req
     * @return 实时数据模型
     */
    RealTimeStrategyLineResp realTimeStrategyLineChart(String accessToken, RealTimeStrategyLineReq req);
}
