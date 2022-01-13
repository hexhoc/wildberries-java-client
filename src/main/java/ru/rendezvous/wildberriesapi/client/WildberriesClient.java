package ru.rendezvous.wildberriesapi.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.ListenableFuture;

import lombok.Getter;

@Getter
public class WildberriesClient {
    private static final DefaultAsyncHttpClientConfig DEFAULT_ASYNC_HTTP_CLIENT_CONFIG =
    new DefaultAsyncHttpClientConfig.Builder().setFollowRedirect(true).build();
    private final String url;
    private final String token;
    private final AsyncHttpClient client;


    public WildberriesClient(String url, String token) {
        this.token = token;
        this.url = url;
        this.client = new DefaultAsyncHttpClient(DEFAULT_ASYNC_HTTP_CLIENT_CONFIG);
    }

    public Card createCard(Card card) {
        return complete(createCardAsync(card));
    }

    public JobStatus createCards(Card... cards) {
        return createCards(Arrays.asList(cards));
    }

    public JobStatus createCards(List<Card> cards) {
        return complete(createCardsAsync(cards));
    }

    public ListenableFuture<Card> createCardAsync(Card card) {
        return submit(req("POST", cnst("/tickets.json"),
                JSON, json(Collections.singletonMap("card", card))),
                handle(Card.class, "card"));
    }

    public ListenableFuture<JobStatus> createCardsAsync(List<Card> cards) {
        return submit(req("POST", cnst("/tickets/create_many.json"), JSON, json(
                Collections.singletonMap("tickets", cards))), handleJobStatus());
    }


    private static <T> T complete(ListenableFuture<T> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            throw new WildberriesException(e.getMessage(), e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof WildberriesException) {
                if (e.getCause() instanceof WildberriesResponseRateLimitException) {
                    throw new WildberriesResponseRateLimitException((WildberriesResponseRateLimitException) e.getCause());
                }
                if (e.getCause() instanceof WildberriesResponseException) {
                    throw new WildberriesResponseException((WildberriesResponseException)e.getCause());
                }
                throw new WildberriesException(e.getCause());
            }
            throw new WildberriesException(e.getMessage(), e);
        }
    }

}
