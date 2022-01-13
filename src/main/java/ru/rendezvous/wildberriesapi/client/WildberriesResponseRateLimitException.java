package ru.rendezvous.wildberriesapi.client;

import org.asynchttpclient.Response;

import java.io.IOException;

public class WildberriesResponseRateLimitException extends WildberriesResponseException {

    private static final long serialVersionUID = 1L;
    private static final String RETRY_AFTER_HEADER = "Retry-After";
    private static final long DEFAULT_RETRY_AFTER = 60L;

    private Long retryAfter = DEFAULT_RETRY_AFTER;

    public WildberriesResponseRateLimitException(Response resp) throws IOException {
            super(resp);
            try {
                this.retryAfter = Long.valueOf(resp.getHeader(RETRY_AFTER_HEADER));
            } catch (NumberFormatException e) {
                // Ignore, use the default value already set
            }
        }

    protected WildberriesResponseRateLimitException(WildberriesResponseRateLimitException e) {
            super(e);
            this.retryAfter = e.getRetryAfter();
        }

    public Long getRetryAfter() {
        return retryAfter;
    }
}
