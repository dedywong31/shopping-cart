package org.playground.shoppingcart.mappers;

import org.mapstruct.Mapper;
import org.playground.shoppingcart.dtos.OrderDto;
import org.playground.shoppingcart.entities.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
