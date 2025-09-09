package org.playground.shoppingcart.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.playground.shoppingcart.dtos.AddItemToCartRequest;
import org.playground.shoppingcart.dtos.CartDto;
import org.playground.shoppingcart.dtos.CartItemDto;
import org.playground.shoppingcart.dtos.UpdateCartItemRequest;
import org.playground.shoppingcart.entities.Cart;
import org.playground.shoppingcart.entities.CartItem;
import org.playground.shoppingcart.mappers.CartMapper;
import org.playground.shoppingcart.repositories.CartRepository;
import org.playground.shoppingcart.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
        UriComponentsBuilder uriComponentsBuilder
    ) {
        var cart = new Cart();
        cartRepository.save(cart);

        var cartDto = cartMapper.toDto(cart);
        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
        @PathVariable UUID cartId,
        @RequestBody AddItemToCartRequest request
    ) {
        var cart = cartRepository.getCartWithItemsById(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }

        var cartItem = cart.addItem(product);

        cartRepository.save(cart);

        var cartItemDto = cartMapper.toDto(cartItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId) {
        var cart = cartRepository.getCartWithItemsById(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        var cartDto = cartMapper.toDto(cart);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCartItem(
        @PathVariable("cartId") UUID cartId,
        @PathVariable("productId") Long productId,
        @Valid @RequestBody UpdateCartItemRequest request
    ) {
        var cart = cartRepository.getCartWithItemsById(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Cart not found.")
            );
        }
        var cartItem = cart.getItems(productId);
        if (cartItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Product not found.")
            );
        }
        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cart);
        var cartItemDto = cartMapper.toDto(cartItem);
        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(
        @PathVariable("cartId") UUID cartId,
        @PathVariable("productId") Long productId
    ) {
        var cart = cartRepository.getCartWithItemsById(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Cart not found.")
            );
        }
        var cartItem = cart.getItems(productId);
        if (cartItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Product not found.")
            );
        }
        cart.removeItem(productId);

        cartRepository.save(cart);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> deleteCart(
        @PathVariable("cartId") UUID cartId
    ) {
        var cart = cartRepository.getCartWithItemsById(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Cart not found.")
            );
        }
        cart.clear();

        cartRepository.save(cart);

        return ResponseEntity.noContent().build();
    }
}
