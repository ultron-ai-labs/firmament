package cn.org.ultronai.firmament.admin.web.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.org.ultronai.firmament.admin.biz.model.prompt.PromptReq;
import cn.org.ultronai.firmament.admin.biz.service.PromptService;
import cn.org.ultronai.firmament.admin.dal.common.R;

/**
 * 提示词
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/07 16:39
 */
@RestController
@RequestMapping("/api/prompt")
public class PromptController {
    @Resource
    private PromptService promptService;

    /**
     * 获取提示词模板
     * 
     * @return 提示词
     */
    @RequestMapping("/getPromptList")
    public R getPromptList(@RequestHeader String accessToken, @RequestBody PromptReq req) {
        return R.builderOk().data(promptService.getPromptList(accessToken, req)).build();
    }

    /**
     * 获取提示词模板
     *
     * @return 提示词
     */
    @RequestMapping("/addPrompt")
    public R addPrompt(@RequestHeader String accessToken, @RequestBody PromptReq req) {
        return R.builderOk().data("result", promptService.addPrompt(accessToken, req)).build();
    }

    /**
     * 获取提示词模板
     *
     * @return 提示词
     */
    @RequestMapping("/updatePrompt")
    public R updatePrompt(@RequestHeader String accessToken, @RequestBody PromptReq req) {
        return R.builderOk().data("result", promptService.updatePrompt(accessToken, req)).build();
    }

    /**
     * 获取提示词模板
     *
     * @return 提示词
     */
    @RequestMapping("/getAllPromptList")
    public R getAllPromptList(@RequestHeader String accessToken) {
        return R.builderOk().data(promptService.getAllPromptList(accessToken)).build();
    }
}
