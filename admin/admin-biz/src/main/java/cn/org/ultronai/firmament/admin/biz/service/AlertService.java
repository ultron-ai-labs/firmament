package cn.org.ultronai.firmament.admin.biz.service;

import java.util.List;

import cn.org.ultronai.firmament.admin.biz.model.alert.AlertQueryReq;
import cn.org.ultronai.firmament.admin.biz.model.alert.AlertQueryResp;
import cn.org.ultronai.firmament.admin.biz.model.alert.AlertRecordResp;
import cn.org.ultronai.firmament.admin.biz.model.alert.AlertWebReq;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 15:44
 */
public interface AlertService {

    /**
     * 获取告警配置
     *
     * @param accessToken accessToken
     * @param req req
     * @return 告警配置
     */
    AlertQueryResp configs(String accessToken, AlertQueryReq req);

    /**
     * 添加告警
     *
     * @param accessToken accessToken
     * @param req req
     * @return 是否添加成功
     */
    boolean addAlert(String accessToken, AlertWebReq req);

    /**
     * 更新告警
     *
     * @param accessToken accessToken
     * @param req req
     * @return 是否添加成功
     */
    boolean updateAlert(String accessToken, AlertWebReq req);

    /**
     * 测试告警
     *
     * @param accessToken accessToken
     * @param req req
     * @return 是否添加成功
     */
    boolean testAlert(String accessToken, AlertWebReq req);

    /**
     * 获取所有告警配置
     *
     * @param accessToken accessToken
     * @return 所有告警配置
     */
    List<AlertRecordResp> allConfigs(String accessToken);

    /**
     * 执行告警
     *
     * @param alertId alertId
     * @param message message
     */
    void doAlert(String alertId, String memberId, String message);

    /**
     * 删除告警
     *
     * @param accessToken accessToken
     * @param req req
     * @return 是否删除成功
     */
    boolean deleteAlert(String accessToken, AlertWebReq req);
}
