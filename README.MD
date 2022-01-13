Wildberries Java Client
===================

This is a wildberries client implementation written in Java using AsyncHttpClient and Jackson.

Using the API
-------------

Start by creating a `Wildberries` instance

    WildberriesClient client = new WildberriesClient("https://suppliers-api.wildberries.ru", "123345567789");
    Card card = client.getCardByImtID(String imtId);
    client.CreateCard(Card card);
