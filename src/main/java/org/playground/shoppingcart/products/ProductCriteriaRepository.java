package org.playground.shoppingcart.products;

import java.math.BigDecimal;
import java.util.List;

public interface ProductCriteriaRepository {
    List<Product> findProductByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findProductByCategoryCriteria(Category category);
}
