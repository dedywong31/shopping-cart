package org.playground.shoppingcart.controller;

import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.playground.shoppingcart.dtos.CheckoutRequest;
import org.playground.shoppingcart.dtos.CheckoutResponse;
import org.playground.shoppingcart.dtos.ErrorDto;
import org.playground.shoppingcart.exceptions.CartEmptyException;
import org.playground.shoppingcart.exceptions.CartNotFoundException;
import org.playground.shoppingcart.services.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest checkout) {
        try {
            return ResponseEntity.ok(checkoutService.checkout(checkout));
        } catch (StripeException ex) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating a checkout session"));
        }
    }

    @ExceptionHandler({ CartNotFoundException.class, CartEmptyException.class })
    public ResponseEntity<ErrorDto> handleException(Exception exception) {
        return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage()));
    }
}
