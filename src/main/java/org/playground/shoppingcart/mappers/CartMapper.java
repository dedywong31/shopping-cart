package org.playground.shoppingcart.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.playground.shoppingcart.dtos.CartDto;
import org.playground.shoppingcart.dtos.CartItemDto;
import org.playground.shoppingcart.entities.Cart;
import org.playground.shoppingcart.entities.CartItem;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
