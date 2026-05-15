package com.momo.sparta.mainapi.domains.order.mapper;

import com.momo.sparta.commonmysqldb.entity.Order;
import com.momo.sparta.mainapi.domains.order.dto.CreateOrderDto;
import com.momo.sparta.mainapi.domains.order.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "product.name", target = "productName")
    OrderDto toDto(Order order);

    Order fromDto(OrderDto orderDto);

    Order fromDto(CreateOrderDto createOrderDto);

}
