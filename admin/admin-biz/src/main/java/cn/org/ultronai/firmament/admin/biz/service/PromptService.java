package cn.org.ultronai.firmament.admin.biz.service;

import java.util.List;

import cn.org.ultronai.firmament.admin.biz.model.prompt.PromptPageResp;
import cn.org.ultronai.firmament.admin.biz.model.prompt.PromptReq;
import cn.org.ultronai.firmament.admin.biz.model.prompt.PromptResp;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/12 21:30
 */
public interface PromptService {

    /**
     * 获取提示词
     *
     * @return 提示词
     */
    PromptPageResp getPromptList(String accessToken, PromptReq req);

    /**
     * 获取提示词模板
     *
     * @return 提示词
     */
    boolean addPrompt(String accessToken, PromptReq req);

    /**
     * 获取提示词模板
     *
     * @return 提示词
     */
    boolean updatePrompt(String accessToken, PromptReq req);

    /**
     * 获取提示词
     *
     * @return 提示词
     */
    List<PromptResp> getAllPromptList(String accessToken);
}
