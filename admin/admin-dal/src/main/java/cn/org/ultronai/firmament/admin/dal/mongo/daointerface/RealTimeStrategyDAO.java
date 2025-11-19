package cn.org.ultronai.firmament.admin.dal.mongo.daointerface;

import java.util.List;

import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.RealTimeStrategyDO;

/**
 * @author icanci(1205068)
 * @version Id: RealTimeStrategyDAO, v 0.1 2025/11/8 16:57 icanci Exp $
 */
public interface RealTimeStrategyDAO extends BaseDAO<RealTimeStrategyDO> {
    /**
     * 根据会员id查询实时数据模型
     *
     * @param memberId   会员id
     * @param strategyId strategyId
     * @return 实时数据模型
     */
    List<RealTimeStrategyDO> queryByMemberId(String memberId, String strategyId);

    /**
     * 根据策略id查询实时数据模型
     *
     * @param strategyId 策略id
     * @return 策略id
     */
    RealTimeStrategyDO queryByStrategyId(String strategyId);

    /**
     * 根据策略名称查询实时数据模型
     *
     * @param memberId  会员id
     * @param strategyName 策略名称
     * @return 策略名称
     */
    RealTimeStrategyDO queryByName(String memberId, String strategyName);
}