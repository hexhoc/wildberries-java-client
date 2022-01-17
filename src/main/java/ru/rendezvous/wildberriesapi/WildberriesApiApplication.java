package ru.rendezvous.wildberriesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.rendezvous.wildberriesapi.client.WildberriesClient;
import ru.rendezvous.wildberriesapi.client.model.config.CategoryConfig;
import ru.rendezvous.wildberriesapi.service.WildberriesManager;

@SpringBootApplication
public class WildberriesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WildberriesApiApplication.class, args);

        WildberriesClient client = new WildberriesClient("https://suppliers-api.wildberries.ru",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NJRCI6ImVjNTdmNDU3LTJjYmQtNDRkNy05YWY4LTI2ODU1OTBlNWM0MSJ9.HDZcTwdnR0pi570RYYIO8ogUCaxL2LoaCWo-aNmUXuk");

        WildberriesManager manager = new WildberriesManager(client);
        CategoryConfig cardConfig = manager.getCategoryConfig("Кроссовки");
    }
}
