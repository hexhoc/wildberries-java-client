package ru.rendezvous.wildberriesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.rendezvous.wildberriesapi.client.WildberriesClient;
import ru.rendezvous.wildberriesapi.client.model.config.ObjectConfig;
import ru.rendezvous.wildberriesapi.service.WildberriesManager;

import java.util.HashMap;
import java.util.UUID;

@SpringBootApplication
public class WildberriesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WildberriesApiApplication.class, args);

        WildberriesClient client = new WildberriesClient("https://suppliers-api.wildberries.ru",
                "1234567890");

        WildberriesManager manager = new WildberriesManager(client);

//        UUID uuid = manager.uploadFile();
        UUID uuid = UUID.fromString("2422ac4f-5f28-456d-91e9-6721590bc70b");
        HashMap<String, UUID> files = new HashMap<>();
        files.put("Фото", uuid);
        manager.createCard(files);

        ObjectConfig cardConfig = manager.getObjectConfig("Кроссовки");
    }
}
