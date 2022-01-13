package ru.rendezvous.wildberriesapi.client;

import lombok.Getter;

@Getter
public class WildberriesClient {
    private final String url;
    private final String token;

    public WildberriesClient(String url, String token) {
        this.token = token;
        this.url = url;
    }



}
