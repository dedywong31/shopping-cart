package org.playground.shoppingcart.repositories;

import org.playground.shoppingcart.dtos.UserSummary;
import org.playground.shoppingcart.entities.User;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository  extends CrudRepository<User, Long> {
    @Procedure("findLoyalUsers")
    List<UserSummary> findLoyalUsers(int loyaltyPoints);
}
