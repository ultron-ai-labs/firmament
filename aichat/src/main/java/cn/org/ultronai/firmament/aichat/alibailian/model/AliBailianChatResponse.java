package cn.org.ultronai.firmament.aichat.alibailian.model;

import java.io.Serializable;
import java.util.List;

import cn.org.ultronai.firmament.aichat.common.ChatChoice;
import cn.org.ultronai.firmament.aichat.common.ChatUsage;
import lombok.Data;

/**
 * {
 *     "choices": [
 *         {
 *             "message": {
 *                 "content": "你好！我是DeepSeek，由深度求索公司创造的AI助手。😊\n\n我是一个纯文本模型，擅长回答各种问题、协助处理文字工作、进行对话交流等。虽然我不支持多模态识别功能，但我可以帮你处理上传的文件（包括图像、txt、pdf、ppt、word、excel等），从中读取文字信息进行分析处理。\n\n我的一些特点：\n- 完全免费使用，没有收费计划\n- 上下文长度达128K\n- 支持联网搜索（需要手动开启）\n- 可以通过官方应用商店下载App使用\n- 知识截止到2024年7月\n\n有什么我可以帮助你的吗？无论是学习、工作还是日常问题，我都很乐意为你提供帮助！✨",
 *                 "role": "assistant"
 *             },
 *             "finish_reason": "stop",
 *             "index": 0,
 *             "logprobs": null
 *         }
 *     ],
 *     "object": "chat.completion",
 *     "usage": {
 *         "prompt_tokens": 6,
 *         "completion_tokens": 152,
 *         "total_tokens": 158
 *     },
 *     "created": 1763000403,
 *     "system_fingerprint": null,
 *     "model": "deepseek-v3.2-exp",
 *     "id": "chatcmpl-4669b05b-8bb3-4024-ae78-d6196157e6ba"
 * }
 * @author icanci
 * @since 1.0 Created in 2025/11/13 10:27
 */
@Data
public class AliBailianChatResponse implements Serializable {
    private String           id;
    private String           object;
    private long             created;
    private String           model;
    private List<ChatChoice> choices;
    private ChatUsage        usage;
    private String           system_fingerprint;
}
