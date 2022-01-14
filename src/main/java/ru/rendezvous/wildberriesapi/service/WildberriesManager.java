package ru.rendezvous.wildberriesapi.service;

import ru.rendezvous.wildberriesapi.client.WildberriesClient;
import ru.rendezvous.wildberriesapi.client.model.Card;
import ru.rendezvous.wildberriesapi.client.model.CardFilter;

public class WildberriesManager {

    private WildberriesClient client;

    public WildberriesManager(WildberriesClient client) {
        this.client = client;
    }

    public void createCards() {
        Card card = new Card();

        client.getCardList(new CardFilter());
    }

}
