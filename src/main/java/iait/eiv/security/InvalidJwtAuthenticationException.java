package iait.eiv.security;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 1166173695636518380L;

    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}