package org.playground.shoppingcart.services;

import lombok.AllArgsConstructor;
import org.playground.shoppingcart.dtos.OrderDto;
import org.playground.shoppingcart.mappers.OrderMapper;
import org.playground.shoppingcart.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getAllByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }
}
