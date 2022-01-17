package ru.rendezvous.wildberriesapi.service;

import ru.rendezvous.wildberriesapi.client.WildberriesClient;
import ru.rendezvous.wildberriesapi.client.model.Card;
import ru.rendezvous.wildberriesapi.client.model.CardFilter;
import ru.rendezvous.wildberriesapi.client.model.config.CategoryConfig;

import java.util.UUID;

public class WildberriesManager {

    private WildberriesClient client;

    public WildberriesManager(WildberriesClient client) {
        this.client = client;
    }

    public void getCardsList() {
//        Card card = new Card();
//        card.setId(UUID.randomUUID().toString());
//        card.
//        client.getCardList(new CardFilter());
    }


//    public Card createCard() {
//        Card card = new Card();
//
//        client.getCardList(new CardFilter());
//    }

    public CategoryConfig getCategoryConfig(String categoryName) {
        return client.getCategoryConfig(categoryName);
    }

}
