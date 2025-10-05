package org.playground.shoppingcart.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.playground.shoppingcart.dtos.CheckoutRequest;
import org.playground.shoppingcart.dtos.CheckoutResponse;
import org.playground.shoppingcart.dtos.ErrorDto;
import org.playground.shoppingcart.entities.OrderStatus;
import org.playground.shoppingcart.exceptions.CartEmptyException;
import org.playground.shoppingcart.exceptions.CartNotFoundException;
import org.playground.shoppingcart.exceptions.PaymentException;
import org.playground.shoppingcart.repositories.OrderRepository;
import org.playground.shoppingcart.services.CheckoutService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest checkout) {
        return checkoutService.checkout(checkout);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(
        @RequestHeader("Stripe-Signature") String signature,
        @RequestBody String payload
    ) {
        try {
            var event = Webhook.constructEvent(signature, payload, webhookSecretKey);
            System.out.println(event.getType());

            var stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);
            switch (event.getType()) {
                case "payment_intent.succeeded" -> {
                    var paymentIntent = (PaymentIntent) stripeObject;
                    if (paymentIntent != null) {
                        var orderId = paymentIntent.getMetadata().get("order-id");
                        var order = orderRepository.findById(Long.valueOf(orderId)).orElseThrow();
                        order.setStatus(OrderStatus.PAID);
                        orderRepository.save(order);
                    }
                }
                case "payment_intent.failed" -> {
                    System.out.println("Payment failed: " + stripeObject);
                }
            }

            return ResponseEntity.ok().build();
        } catch (SignatureVerificationException e) {
            throw new RuntimeException(e);
        }
    }

    @ExceptionHandler({ CartNotFoundException.class, CartEmptyException.class })
    public ResponseEntity<ErrorDto> handleException(Exception exception) {
        return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage()));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDto> handlePaymentException(Exception exception) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorDto("Error creating a checkout session"));
    }
}
