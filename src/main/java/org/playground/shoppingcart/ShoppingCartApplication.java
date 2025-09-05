package org.playground.shoppingcart;

import org.playground.shoppingcart.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ShoppingCartApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ShoppingCartApplication.class, args);
        context.getBean(UserService.class);
    }
}
