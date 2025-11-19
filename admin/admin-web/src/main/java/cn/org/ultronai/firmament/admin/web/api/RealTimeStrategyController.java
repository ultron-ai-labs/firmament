package cn.org.ultronai.firmament.admin.web.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import cn.org.ultronai.firmament.admin.biz.model.realtimestrategy.RealTimeStrategyAddReq;
import cn.org.ultronai.firmament.admin.biz.model.realtimestrategy.RealTimeStrategyDashboardReq;
import cn.org.ultronai.firmament.admin.biz.model.realtimestrategy.RealTimeStrategyLineReq;
import cn.org.ultronai.firmament.admin.biz.service.RealTimeStrategyService;
import cn.org.ultronai.firmament.admin.dal.common.R;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/08 16:51
 */
@RestController
@RequestMapping("/api/realTimeStrategy")
public class RealTimeStrategyController {
    @Resource
    private RealTimeStrategyService realTimeStrategyService;

    @PostMapping("/dashboard")
    public R dashboard(@RequestHeader String accessToken, @RequestBody RealTimeStrategyDashboardReq req) {
        return R.builderOk().data(realTimeStrategyService.dashboard(accessToken, req)).build();
    }

    @PostMapping("/addRealTimeModel")
    public R addRealTimeModel(@RequestHeader String accessToken, @RequestBody RealTimeStrategyAddReq req) {
        return R.builderOk().data(realTimeStrategyService.addRealTimeModel(accessToken, req)).build();
    }

    @PostMapping("/startRealTimeModel")
    public R startRealTimeModel(@RequestHeader String accessToken, @RequestBody RealTimeStrategyAddReq req) {
        return R.builderOk().data(realTimeStrategyService.startRealTimeModel(accessToken, req)).build();
    }

    @PostMapping("/stopRealTimeModel")
    public R stopRealTimeModel(@RequestHeader String accessToken, @RequestBody RealTimeStrategyAddReq req) {
        return R.builderOk().data(realTimeStrategyService.stopRealTimeModel(accessToken, req)).build();
    }

    @PostMapping("/runningRealTimeStrategy")
    public R runningRealTimeStrategy(@RequestHeader String accessToken) {
        return R.builderOk().data(realTimeStrategyService.runningRealTimeStrategy(accessToken)).build();
    }

    @PostMapping("/realTimeStrategyLineChart")
    public R realTimeStrategyLineChart(@RequestHeader String accessToken, @RequestBody RealTimeStrategyLineReq req) {
        return R.builderOk().data(realTimeStrategyService.realTimeStrategyLineChart(accessToken, req)).build();
    }

}
