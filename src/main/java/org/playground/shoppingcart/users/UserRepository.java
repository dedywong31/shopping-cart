package org.playground.shoppingcart.users;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);

    @Procedure("findLoyalUsers")
    List<UserSummary> findLoyalUsers(int loyaltyPoints);

    Optional<User> findByEmail(String email);
}
