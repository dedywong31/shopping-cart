package org.playground.shoppingcart.users;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
