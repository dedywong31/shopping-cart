package org.playground.shoppingcart.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long id;
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    private BigDecimal price;

    @Size(max = 255, message = "Name must be less than 255 characters")
    private String description;
    private Byte categoryId;
}
