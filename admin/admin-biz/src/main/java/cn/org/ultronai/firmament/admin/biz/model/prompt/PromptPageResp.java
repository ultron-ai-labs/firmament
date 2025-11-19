package cn.org.ultronai.firmament.admin.biz.model.prompt;

import java.util.List;

import cn.org.ultronai.firmament.admin.biz.model.BasePageResp;
import lombok.Data;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/12 21:32
 */
@Data
public class PromptPageResp extends BasePageResp {
    private List<PromptResp> records;
}
