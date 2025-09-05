package org.playground.shoppingcart.repositories;

import org.playground.shoppingcart.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}
