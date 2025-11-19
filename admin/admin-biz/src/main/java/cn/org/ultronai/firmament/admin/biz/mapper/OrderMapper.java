package cn.org.ultronai.firmament.admin.biz.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import cn.org.ultronai.firmament.admin.biz.model.order.OrderResp;
import cn.org.ultronai.firmament.admin.dal.mongo.dateobject.OrderDO;

/**
 * @author icanci
 * @since 1.0 Created in 2025/11/14 10:24
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface OrderMapper {
    OrderResp toDTO(OrderDO orderDO);

    List<OrderResp> toDTOs(List<OrderDO> orderDO);
}
