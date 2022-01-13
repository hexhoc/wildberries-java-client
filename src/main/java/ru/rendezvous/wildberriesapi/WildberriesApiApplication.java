package ru.rendezvous.wildberriesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zendesk.client.v2.Zendesk;
import ru.rendezvous.wildberriesapi.client.WildberriesClient;

@SpringBootApplication
public class WildberriesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WildberriesApiApplication.class, args);

        Zendesk zd = new Zendesk.Builder("https://{{your domain}}.zendesk.com")
                .setUsername("...")
                .setToken("...") // or .setPassword("...")
                .build();

        WildberriesClient client = new WildberriesClient("https://suppliers-api.wildberries.ru", "123345567789");
    }
}
