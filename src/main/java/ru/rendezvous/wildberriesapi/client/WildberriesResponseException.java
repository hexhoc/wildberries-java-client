package ru.rendezvous.wildberriesapi.client;

import org.asynchttpclient.Response;

import java.io.IOException;
import java.text.MessageFormat;

public class WildberriesResponseException extends WildberriesException {

    private static final long serialVersionUID = 1L;

    private int statusCode;
    private String statusText;
    private String body;

    public WildberriesResponseException(Response resp) throws IOException {
        this(resp.getStatusCode(), resp.getStatusText(), resp.getResponseBody());
    }

    public WildberriesResponseException(int statusCode, String statusText, String body) {
        super(MessageFormat.format("HTTP/{0}: {1} - {2}", statusCode, statusText, body));
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.body = body;
    }

    public WildberriesResponseException(WildberriesResponseException cause) {
        super(cause.getMessage(), cause);
        this.statusCode = cause.getStatusCode();
        this.statusText = cause.getStatusText();
        this.body = cause.getBody();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getBody() {
        return body;
    }
}
