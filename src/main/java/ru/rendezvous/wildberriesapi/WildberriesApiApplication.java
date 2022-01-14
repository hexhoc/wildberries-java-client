package ru.rendezvous.wildberriesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.rendezvous.wildberriesapi.client.WildberriesClient;
import ru.rendezvous.wildberriesapi.service.WildberriesManager;

@SpringBootApplication
public class WildberriesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WildberriesApiApplication.class, args);

        WildberriesClient client = new WildberriesClient("https://suppliers-api.wildberries.ru",
                "123456789");

        WildberriesManager manager = new WildberriesManager(client);
        manager.createCards();
    }
}
