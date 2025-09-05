package org.playground.shoppingcart.repositories;

import org.playground.shoppingcart.entities.Category;
import org.playground.shoppingcart.entities.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductCriteriaRepository {
    List<Product> findProductByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findProductByCategoryCriteria(Category category);
}
