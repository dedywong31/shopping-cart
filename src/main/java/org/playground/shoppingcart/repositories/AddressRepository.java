package org.playground.shoppingcart.repositories;

import org.playground.shoppingcart.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
