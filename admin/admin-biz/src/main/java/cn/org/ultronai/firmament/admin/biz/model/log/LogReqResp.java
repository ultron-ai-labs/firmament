package cn.org.ultronai.firmament.admin.biz.model.log;

import lombok.Data;

import java.util.Date;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/10 21:20
 */
@Data
public class LogReqResp {
    private String module;
    private String targetId;
    private String operatorType;
    private Date   createTime;
    private String content;
}
