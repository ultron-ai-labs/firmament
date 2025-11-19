package cn.org.ultronai.firmament.admin.web.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import cn.org.ultronai.firmament.admin.biz.model.alert.AlertQueryReq;
import cn.org.ultronai.firmament.admin.biz.model.alert.AlertWebReq;
import cn.org.ultronai.firmament.admin.biz.service.AlertService;
import cn.org.ultronai.firmament.admin.dal.common.R;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 15:41
 */
@RestController
@RequestMapping("/api/alert")
public class AlertController {
    @Resource
    private AlertService alertService;

    @PostMapping("/configs")
    public R configs(@RequestHeader String accessToken, @RequestBody AlertQueryReq req) {
        return R.builderOk().data(alertService.configs(accessToken, req)).build();
    }

    @PostMapping("/allConfigs")
    public R allConfigs(@RequestHeader String accessToken) {
        return R.builderOk().data(alertService.allConfigs(accessToken)).build();
    }

    @PostMapping("/addAlert")
    public R addAlert(@RequestHeader String accessToken, @RequestBody AlertWebReq req) {
        return R.builderOk().data("result", alertService.addAlert(accessToken, req)).build();
    }

    @PostMapping("/updateAlert")
    public R updateAlert(@RequestHeader String accessToken, @RequestBody AlertWebReq req) {
        return R.builderOk().data("result", alertService.updateAlert(accessToken, req)).build();
    }

    @PostMapping("/testAlert")
    public R testAlert(@RequestHeader String accessToken, @RequestBody AlertWebReq req) {
        return R.builderOk().data("result", alertService.testAlert(accessToken, req)).build();
    }

    @PostMapping("/deleteAlert")
    public R deleteAlert(@RequestHeader String accessToken, @RequestBody AlertWebReq req) {
        return R.builderOk().data("result", alertService.deleteAlert(accessToken, req)).build();
    }

}
