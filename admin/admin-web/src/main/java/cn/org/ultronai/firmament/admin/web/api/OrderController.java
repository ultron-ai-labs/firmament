package cn.org.ultronai.firmament.admin.web.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import cn.org.ultronai.firmament.admin.biz.model.order.CloseOrderReq;
import cn.org.ultronai.firmament.admin.biz.model.order.OrderReq;
import cn.org.ultronai.firmament.admin.biz.service.OrderService;
import cn.org.ultronai.firmament.admin.dal.common.R;

/**
 * 订单数据查询
 *
 * @author icanci
 * @since 1.0 Created in 2025/11/10 13:44
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping("/strategyOrders")
    public R strategyOrders(@RequestHeader String accessToken, @RequestBody OrderReq req) {
        return R.builderOk().data(orderService.strategyOrders(accessToken, req)).build();
    }

    @PostMapping("/closeOrder")
    public R closeOrder(@RequestHeader String accessToken, @RequestBody CloseOrderReq req) {
        return R.builderOk().data(orderService.closeOrder(accessToken, req)).build();
    }
}
