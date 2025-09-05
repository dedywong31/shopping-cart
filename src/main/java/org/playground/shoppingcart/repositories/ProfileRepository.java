package org.playground.shoppingcart.repositories;

import org.playground.shoppingcart.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
