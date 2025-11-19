package cn.org.ultronai.firmament.admin.biz.model.log;

import lombok.Data;

import java.util.Set;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/10 21:19
 */
@Data
public class LogReq {
    private String      module;
    private String      targetId;
    private Set<String> operatorType;
}
