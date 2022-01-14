package ru.rendezvous.wildberriesapi.client;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.asynchttpclient.*;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rendezvous.wildberriesapi.client.exception.WildberriesException;
import ru.rendezvous.wildberriesapi.client.exception.WildberriesResponseException;
import ru.rendezvous.wildberriesapi.client.exception.WildberriesResponseRateLimitException;
import ru.rendezvous.wildberriesapi.client.model.Card;
import ru.rendezvous.wildberriesapi.client.model.CardFilter;
import ru.rendezvous.wildberriesapi.client.model.JobStatus;

@Getter
public class WildberriesClient implements Closeable {
    private static final String JSON = "application/json; charset=UTF-8";
    private static final DefaultAsyncHttpClientConfig DEFAULT_ASYNC_HTTP_CLIENT_CONFIG =
    new DefaultAsyncHttpClientConfig.Builder().setFollowRedirect(true).build();
    private final String url;
    private final String token;
    private final AsyncHttpClient client;
    private final Logger logger;
    private final Map<String, String> headers;
    private final ObjectMapper mapper;
    private boolean closed = false;
    private final boolean closeClient;


    public WildberriesClient(String url, String token) {
        this.token = token;
        this.url = url;
        this.client = new DefaultAsyncHttpClient(DEFAULT_ASYNC_HTTP_CLIENT_CONFIG);
        this.logger = LoggerFactory.getLogger(WildberriesClient.class);
        this.headers = Collections.unmodifiableMap(new HashMap<>());
        this.mapper = createMapper();
        this.closeClient = client == null;
    }

    public static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat());
        mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
        return mapper;
    }

    ///////////////////////////////////////////////////////////////
    // COMMANDS
    ///////////////////////////////////////////////////////////////

    public List<Card> getCardList(CardFilter cardFilter) {
//        return
//        var response = complete(
//                submit(
//                        req("POST",cnst("/card/list")),
//                        handleList(Card.class,"cards")
//                )
//        );

        return complete(
                submit(
                        req(
                                "POST",cnst("/card/list"),
                                JSON,
                                json(cardFilter)
                        ),
                        handleList(Card.class,"cards")
                )
        );
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

    ///////////////////////////////////////////////////////////////
    // SERVICE METHODS
    ///////////////////////////////////////////////////////////////

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

    private <T> ListenableFuture<T> submit(Request request, WildberriesClient.WildberriesAsyncCompletionHandler<T> handler) {
        if (logger.isDebugEnabled()) {
            if (request.getStringData() != null) {
                logger.debug("Request {} {}\n{}", request.getMethod(), request.getUrl(), request.getStringData());
            } else if (request.getByteData() != null) {
                logger.debug("Request {} {} {} {} bytes", request.getMethod(), request.getUrl(),
                        request.getHeaders().get("Content-type"), request.getByteData().length);
            } else {
                logger.debug("Request {} {}", request.getMethod(), request.getUrl());
            }
        }
        return client.executeRequest(request, handler);
    }

    private Request req(String method, Uri template) {
        return req(method, template.toString());
    }

    private Request req(String method, String url) {
        return reqBuilder(method, url).build();
    }

    private Request req(String method, Uri template, String contentType, byte[] body) {
        RequestBuilder builder = reqBuilder(method, template.toString());
        builder.addHeader("Content-type", contentType);
        builder.setBody(body);
        return builder.build();
    }

    private RequestBuilder reqBuilder(String method, String url) {
        RequestBuilder builder = new RequestBuilder(method);
            builder.addHeader("Authorization", "Bearer " + this.token);
        headers.forEach(builder::setHeader);
        return builder.setUrl(url);
    }

    private Uri cnst(String template) {
        return new FixedUri(url + template);
    }

    private void logResponse(Response response) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("Response HTTP/{} {}\n{}", response.getStatusCode(), response.getStatusText(),
                    response.getResponseBody());
        }
        if (logger.isTraceEnabled()) {
            logger.trace("Response headers {}", response.getHeaders());
        }
    }

    private boolean isStatus2xx(Response response) {
        return response.getStatusCode() / 100 == 2;
    }

    private boolean isRateLimitResponse(Response response) {
        return response.getStatusCode() == 429;
    }

    private byte[] json(Object object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new WildberriesException(e.getMessage(), e);
        }
    }

    public boolean isClosed() {
        return closed || client.isClosed();
    }

    @Override
    public void close() {
        if (closeClient && !client.isClosed()) {
            try {
                client.close();
            } catch (IOException e) {
                logger.warn("Unexpected error on client close", e);
            }
        }
        closed = true;
    }

    ///////////////////////////////////////////////////////////////
    // HANDLERS
    ///////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    protected <T> WildberriesAsyncCompletionHandler<T> handle(final Class<T> clazz) {
        return new WildberriesAsyncCompletionHandler<T>() {
            @Override
            public T onCompleted(Response response) throws Exception {
                logResponse(response);
                if (isStatus2xx(response)) {
                    return (T) mapper.readerFor(clazz).readValue(response.getResponseBodyAsStream());
                } else if (isRateLimitResponse(response)) {
                    throw new WildberriesResponseRateLimitException(response);
                }
                if (response.getStatusCode() == 404) {
                    return null;
                }
                throw new WildberriesResponseException(response);
            }
        };
    }

    protected WildberriesAsyncCompletionHandler<JobStatus> handleJobStatus() {
        return new BasicAsyncCompletionHandler<JobStatus>(JobStatus.class, "job_status") {
            @Override
            public JobStatus onCompleted(Response response) throws Exception {
                JobStatus result = super.onCompleted(response);
                if (result == null) {
                    // null is when we receive a 404 response.
                    // For an async job we trigger an error
                    throw new WildberriesResponseException(response);
                }
                return result;
            }
        };
    }

    private class BasicAsyncCompletionHandler<T> extends WildberriesAsyncCompletionHandler<T> {
        private final Class<T> clazz;
        private final String name;
        private final Class[] typeParams;

        public BasicAsyncCompletionHandler(Class clazz, String name, Class... typeParams) {
            this.clazz = clazz;
            this.name = name;
            this.typeParams = typeParams;
        }

        @Override
        public T onCompleted(Response response) throws Exception {
            logResponse(response);
            if (isStatus2xx(response)) {
                if (typeParams.length > 0) {
                    JavaType type = mapper.getTypeFactory().constructParametricType(clazz, typeParams);
                    return mapper.convertValue(mapper.readTree(response.getResponseBodyAsStream()).get(name), type);
                }
                return mapper.convertValue(mapper.readTree(response.getResponseBodyAsStream()).get(name), clazz);
            } else if (isRateLimitResponse(response)) {
                throw new WildberriesResponseRateLimitException(response);
            }
            if (response.getStatusCode() == 404) {
                return null;
            }
            throw new WildberriesResponseException(response);
        }
    }

    protected <T> WildberriesAsyncCompletionHandler<T> handle(final Class<T> clazz, final String name, final Class... typeParams) {
        return new BasicAsyncCompletionHandler<>(clazz, name, typeParams);
    }

    protected <T> PagedAsyncCompletionHandler<List<T>> handleList(final Class<T> clazz, final String name) {
        return new WildberriesClient.PagedAsyncListCompletionHandler<>(clazz, name);
    }


    ///////////////////////////////////////////////////////////////
    // STATIC CLASSES
    ///////////////////////////////////////////////////////////////

    private abstract class PagedAsyncCompletionHandler<T> extends WildberriesAsyncCompletionHandler<T> {
        private String nextPage;
        private static final String NEXT_PAGE = "next_page";

        public void setPagedProperties(JsonNode responseNode, Class<?> clazz) {
            JsonNode node = responseNode.get(NEXT_PAGE);
            if (node == null) {
                this.nextPage = null;
                if (logger.isDebugEnabled()) {
                    logger.debug(NEXT_PAGE + " property not found, pagination not supported" +
                            (clazz != null ? " for " + clazz.getName() : ""));
                }
            } else {
                this.nextPage = node.asText();
            }
        }

        public String getNextPage() {
            return nextPage;
        }

        public void setNextPage(String nextPage) {
            this.nextPage = nextPage;
        }
    }

    private static abstract class WildberriesAsyncCompletionHandler<T> extends AsyncCompletionHandler<T> {
        @Override
        public void onThrowable(Throwable t) {
            if (t instanceof IOException) {
                throw new WildberriesException(t);
            } else {
                super.onThrowable(t);
            }
        }
    }

    private class PagedAsyncListCompletionHandler<T> extends PagedAsyncCompletionHandler<List<T>> {
        private final Class<T> clazz;
        private final String name;
        public PagedAsyncListCompletionHandler(Class<T> clazz, String name) {
            this.clazz = clazz;
            this.name = name;
        }

        @Override
        public List<T> onCompleted(Response response) throws Exception {
            logResponse(response);
            if (isStatus2xx(response)) {
                JsonNode responseNode = mapper.readTree(response.getResponseBodyAsBytes());
                setPagedProperties(responseNode, clazz);
                List<T> values = new ArrayList<>();

                for (JsonNode node : responseNode.get(name)) {
                    values.add(mapper.convertValue(node, clazz));
                }
                return values;
            } else if (isRateLimitResponse(response)) {
                throw new WildberriesResponseRateLimitException(response);
            }
            throw new WildberriesResponseRateLimitException(response);
        }
    }



}
