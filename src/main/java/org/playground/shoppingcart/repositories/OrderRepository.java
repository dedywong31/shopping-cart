package org.playground.shoppingcart.repositories;

import org.playground.shoppingcart.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}