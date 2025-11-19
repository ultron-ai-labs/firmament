package cn.org.ultronai.firmament.admin.biz.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.org.ultronai.firmament.admin.biz.model.alert.AlertQueryReq;
import cn.org.ultronai.firmament.admin.biz.model.alert.AlertQueryResp;
import cn.org.ultronai.firmament.admin.biz.model.alert.AlertRecordResp;
import cn.org.ultronai.firmament.admin.biz.model.alert.AlertWebReq;
import cn.org.ultronai.firmament.admin.biz.service.AlertService;
import cn.org.ultronai.firmament.admin.biz.service.LogService;
import cn.org.ultronai.firmament.admin.biz.utils.PrefixUtils;
import cn.org.ultronai.firmament.admin.dal.common.PageList;
import cn.org.ultronai.firmament.admin.dal.mongo.daointerface.AlertDAO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.AlertDO;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.UserDO;
import cn.org.ultronai.firmament.alarm.AlarmDispatcher;
import cn.org.ultronai.firmament.alarm.AlertTypeEnum;
import cn.org.ultronai.firmament.alarm.model.AlertResp;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 15:44
 */
@Service("alertService")
public class AlertServiceImpl extends BaseService implements AlertService {
    @Resource
    private AlertDAO   alertDAO;
    @Resource
    private LogService logService;

    /**
     * 获取告警配置
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 告警配置
     */
    @Override
    public AlertQueryResp configs(String accessToken, AlertQueryReq req) {
        UserDO userDO = getUserDO(accessToken);
        PageList<AlertDO> pageList = alertDAO.queryByMemberId(userDO.getMemberId(), req.getAlertName(), req.getAlertType(), req.getPage(), req.getPageSize());
        return new AlertQueryResp(pageList.getData().stream().map(alertDO -> {
            AlertRecordResp alertRecordResp = new AlertRecordResp();
            alertRecordResp.setAlertId(alertDO.getAlertId());
            alertRecordResp.setAlertName(alertDO.getAlertName());
            alertRecordResp.setAlertType(alertDO.getAlertType());
            alertRecordResp.setAlertAddress(alertDO.getAlertAddress());
            alertRecordResp.setAlertSign(alertDO.getAlertSign());
            alertRecordResp.setCreateTime(alertDO.getCreateTime());
            return alertRecordResp;
        }).collect(Collectors.toList()), pageList.getPaginator().getTotalCount(), pageList.getPaginator().getCurrentPage(), pageList.getPaginator().getPageSize());
    }

    /**
     * 添加告警
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否添加成功
     */
    @Override
    public boolean addAlert(String accessToken, AlertWebReq req) {
        UserDO userDO = getUserDO(accessToken);
        // 根据名字查询是否有重复的
        AlertDO dbRecord = alertDAO.queryByAlertName(userDO.getMemberId(), req.getAlertName());
        if (dbRecord != null) {
            throw new RuntimeException("告警名字已存在");
        }
        AlertDO alertDO = new AlertDO();
        alertDO.setAlertName(req.getAlertName());
        alertDO.setAlertType(req.getAlertType());
        alertDO.setAlertAddress(req.getAlertAddress());
        alertDO.setAlertSign(req.getAlertSign());
        alertDO.setMemberId(userDO.getMemberId());
        alertDO.setAlertId(PrefixUtils.genAlertId(userDO.getMemberId()));
        alertDAO.insert(alertDO);
        return true;
    }

    /**
     * 更新告警
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否添加成功
     */
    @Override
    public boolean updateAlert(String accessToken, AlertWebReq req) {
        getUserDO(accessToken);
        AlertDO alertDO = alertDAO.queryByAlertId(req.getAlertId());
        if (alertDO == null) {
            throw new RuntimeException("告警不存在");
        }
        // 名字不能重复
        AlertDO dbRecord = alertDAO.queryByAlertName(alertDO.getMemberId(), req.getAlertName());
        if (dbRecord != null && !dbRecord.getAlertId().equals(alertDO.getAlertId())) {
            throw new RuntimeException("告警名字已存在");
        }
        alertDO.setAlertName(req.getAlertName());
        alertDO.setAlertType(req.getAlertType());
        alertDO.setAlertAddress(req.getAlertAddress());
        alertDO.setAlertSign(req.getAlertSign());
        alertDAO.update(alertDO);
        return true;
    }

    /**
     * 测试告警
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否添加成功
     */
    @Override
    public boolean testAlert(String accessToken, AlertWebReq req) {
        getUserDO(accessToken);
        cn.org.ultronai.firmament.alarm.model.AlertReq alertReq = new cn.org.ultronai.firmament.alarm.model.AlertReq();
        alertReq.setAlertName(req.getAlertName());
        alertReq.setAlertType(AlertTypeEnum.getByType(req.getAlertType()));
        alertReq.setAlertAddress(req.getAlertAddress());
        alertReq.setSign(req.getAlertSign());
        alertReq.setMemberId("test");
        alertReq.setAlertId("test");
        alertReq.setAlertContent("测试告警");
        AlertResp dispatch = AlarmDispatcher.dispatch(alertReq);
        return dispatch.isAlertSuccess();
    }

    /**
     * 获取所有告警配置
     *
     * @param accessToken accessToken
     * @return 所有告警配置
     */
    @Override
    public List<AlertRecordResp> allConfigs(String accessToken) {
        UserDO userDO = getUserDO(accessToken);
        List<AlertDO> alertDOList = alertDAO.queryByMemberId(userDO.getMemberId());
        return alertDOList.stream().map(alertDO -> {
            AlertRecordResp alertRecordResp = new AlertRecordResp();
            alertRecordResp.setAlertId(alertDO.getAlertId());
            alertRecordResp.setAlertName(alertDO.getAlertName());
            alertRecordResp.setAlertType(alertDO.getAlertType());
            alertRecordResp.setAlertAddress(alertDO.getAlertAddress());
            alertRecordResp.setAlertSign(alertDO.getAlertSign());
            alertRecordResp.setCreateTime(alertDO.getCreateTime());
            return alertRecordResp;
        }).collect(Collectors.toList());
    }

    /**
     * 执行告警
     *
     * @param alertId alertId
     * @param message  message
     */
    @Override
    public void doAlert(String alertId, String memberId, String message) {
        AlertDO alertDO = alertDAO.queryByAlertId(alertId);
        if (alertDO == null) {
            return;
        }
        cn.org.ultronai.firmament.alarm.model.AlertReq alertReq = new cn.org.ultronai.firmament.alarm.model.AlertReq();
        alertReq.setAlertName(alertDO.getAlertName());
        alertReq.setAlertType(AlertTypeEnum.getByType(alertDO.getAlertType()));
        alertReq.setAlertAddress(alertDO.getAlertAddress());
        alertReq.setSign(alertDO.getAlertSign());
        alertReq.setMemberId(memberId);
        alertReq.setAlertId(alertId);
        alertReq.setAlertContent(message);
        AlarmDispatcher.dispatch(alertReq);
        // 记录日志
        logService.logAlert(alertId, memberId, message);
    }

    /**
     * 删除告警
     *
     * @param accessToken accessToken
     * @param req         req
     * @return 是否删除成功
     */
    @Override
    public boolean deleteAlert(String accessToken, AlertWebReq req) {
        getUserDO(accessToken);
        alertDAO.deleteByAlertId(req.getAlertId());
        return true;
    }
}
