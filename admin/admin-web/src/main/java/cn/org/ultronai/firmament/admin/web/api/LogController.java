package cn.org.ultronai.firmament.admin.web.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.org.ultronai.firmament.admin.biz.model.log.LogReq;
import cn.org.ultronai.firmament.admin.biz.service.LogService;
import cn.org.ultronai.firmament.admin.dal.common.R;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/10 21:17
 */
@RestController
@RequestMapping("/api/log")
public class LogController {
    @Resource
    private LogService logService;

    @RequestMapping("/getLog")
    public R getLog(@RequestHeader String accessToken, @RequestBody LogReq logReq) {
        return R.builderOk().data(logService.getLog(accessToken, logReq)).build();
    }
}
