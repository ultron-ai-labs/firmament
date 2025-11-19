package cn.org.ultronai.firmament.alarm.model;

import java.io.Serializable;

import cn.hutool.json.JSONUtil;
import cn.org.ultronai.firmament.alarm.AlertTypeEnum;
import cn.org.ultronai.firmament.alarm.dingding.model.DingdingMessageResp;
import cn.org.ultronai.firmament.alarm.feishu.model.FeishuMessageResp;
import cn.org.ultronai.firmament.alarm.utils.RecordIdGenUtils;
import cn.org.ultronai.firmament.alarm.wechat.model.WechatMessageResp;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/06 11:28
 */
@Data
public class AlertResp implements Serializable {
    /**
     * 会员Id
     */
    private String        memberId;
    /**
     * 报警类型
     */
    private AlertTypeEnum alertType;
    /**
     * 告警id
     */
    private String        alertId;
    /**
     * 告警名称
     */
    private String        alertName;
    /**
     * 报警地址
     */
    private String        alertAddress;
    /**
     * 报警内容
     */
    private String        alertContent;
    // =========================  统一告警返回模型 ========================= 
    /**
     * 告警结果记录[唯一id]
     */
    private String        alertRecordId;
    /**
     * 告警结果记录[原始结果]
     */
    private String        alertRecordJson;
    /**
     * 告警结果
     */
    private boolean       alertSuccess;
    /**
     * 告警错误信息
     */
    private String        alertErrorMsg;

    public AlertResp(AlertReq req, DingdingMessageResp dingdingMessageResp) {
        this.memberId = req.getMemberId();
        this.alertRecordId = RecordIdGenUtils.genRecordId(req.getMemberId());
        this.alertType = req.getAlertType();
        this.alertId = req.getAlertId();
        this.alertName = req.getAlertName();
        this.alertAddress = req.getAlertAddress();
        this.alertContent = req.getAlertContent();
        this.alertRecordJson = JSONUtil.toJsonStr(dingdingMessageResp);
        this.alertSuccess = dingdingMessageResp.isSuccess();
        this.alertErrorMsg = dingdingMessageResp.getErrmsg();
    }

    public AlertResp(AlertReq req, WechatMessageResp wechatMessageResp) {
        this.memberId = req.getMemberId();
        this.alertRecordId = RecordIdGenUtils.genRecordId(req.getMemberId());
        this.alertType = req.getAlertType();
        this.alertId = req.getAlertId();
        this.alertName = req.getAlertName();
        this.alertAddress = req.getAlertAddress();
        this.alertContent = req.getAlertContent();
        this.alertRecordJson = JSONUtil.toJsonStr(wechatMessageResp);
        this.alertSuccess = wechatMessageResp.isSuccess();
        this.alertErrorMsg = wechatMessageResp.getErrmsg();
    }

    public AlertResp(AlertReq req, FeishuMessageResp feishuMessageResp) {
        this.memberId = req.getMemberId();
        this.alertRecordId = RecordIdGenUtils.genRecordId(req.getMemberId());
        this.alertType = req.getAlertType();
        this.alertId = req.getAlertId();
        this.alertName = req.getAlertName();
        this.alertAddress = req.getAlertAddress();
        this.alertContent = req.getAlertContent();
        this.alertRecordJson = JSONUtil.toJsonStr(feishuMessageResp);
        this.alertSuccess = feishuMessageResp.isSuccess();
        this.alertErrorMsg = feishuMessageResp.getMsg();
    }
}
