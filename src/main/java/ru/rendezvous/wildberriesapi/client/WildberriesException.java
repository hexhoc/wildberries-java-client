package ru.rendezvous.wildberriesapi.client;

public class WildberriesException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WildberriesException(String message) {
        super(message);
    }

    public WildberriesException() {
    }

    public WildberriesException(Throwable cause) {
        super(cause);
    }

    public WildberriesException(String message, Throwable cause) {
        super(message, cause);
    }
}
