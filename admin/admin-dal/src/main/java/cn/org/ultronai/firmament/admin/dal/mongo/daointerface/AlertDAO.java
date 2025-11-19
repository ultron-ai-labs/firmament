package cn.org.ultronai.firmament.admin.dal.mongo.daointerface;

import java.util.List;

import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.AlertDO;

/**
 * 告警持久层接口
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/06 08:00
 */
public interface AlertDAO extends BaseDAO<AlertDO> {

    /**
     * 根据会员ID查询告警信息
     *
     * @param memberId  会员ID
     * @param alertName 告警名称
     * @param alertType 告警类型
     * @param page      页码
     * @param pageSize  每页大小
     * @return 告警信息
     */
    PageList<AlertDO> queryByMemberId(String memberId, String alertName, String alertType, Integer page, Integer pageSize);

    /**
     * 根据告警ID查询告警信息
     *
     * @param alertId 告警ID
     * @return 告警信息
     */
    AlertDO queryByAlertId(String alertId);

    /**
     * 根据会员ID查询告警信息
     *
     * @param memberId 会员ID
     * @return 告警信息
     */
    List<AlertDO> queryByMemberId(String memberId);

    /**
     * 根据告警ID删除告警信息
     *
     * @param alertId 告警ID
     */
    void deleteByAlertId(String alertId);

    /**
     * 根据告警名称查询告警信息
     *
     * @param memberId 会员ID
     * @param alertName 告警名称
     * @return 告警信息
     */
    AlertDO queryByAlertName(String memberId, String alertName);
}
