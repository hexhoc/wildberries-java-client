package ru.rendezvous.wildberriesapi.service;

import ru.rendezvous.wildberriesapi.client.WildberriesClient;
import ru.rendezvous.wildberriesapi.client.model.Card;
import ru.rendezvous.wildberriesapi.client.model.config.ObjectConfig;

import java.util.ArrayList;
import java.util.List;
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


    public Card createCard() {
        //TODO Make category mapping
        //use /api/v1/config/get/object/all?top=10000 Получение всех имен доступных категорий

        Card card = new Card();
        card.setId(UUID.randomUUID().toString());
        // Страна проиводитель.
        card.setCountryProduction("Россия");
        // Категория товара (Джинсы, Книги и другие).
        card.setObject("Кроссовки");
        // Артикул поставщика.
        card.setSupplierVendorCode("NEBULA PUFFY WNTR MID");
        //Структура, содержащая характеристики карточки, общие для всех номенклатур и размеров.
        card.setAddin(createCardAddin());
        // Массив номенклатур товара.
        card.setNomenclatures(createCardNomenclatures());

        //CREATE CARD
        client.createCard(card);

        return card;
    }

    /**
     * Структура, содержащая характеристики карточки, общие для всех номенклатур и размеров.
     * @return
     */
    private List<Card.Addin> createCardAddin() {
        //CREATE ADDIN
        List<Card.Addin> cardAddinList = new ArrayList<>();
        //TODO Бренд нужно проверять по словарю https://suppliers-api.wildberries.ru/api/v1/directory/brands?pattern=nike&top=5000
        //"required": true, "useOnlyDictionaryValues": true
        cardAddinList.add(new Card.Addin("Бренд", new Card.Params("Nike")));
        //"required": false, "useOnlyDictionaryValues": false,
        cardAddinList.add(new Card.Addin("Описание", new Card.Params("Спортивные кроссовки. Беговые.")));
        //"required": false, "useOnlyDictionaryValues": false
        cardAddinList.add(new Card.Addin("Ключевые слова", new Card.Params("Кроссовки, NIKE")));
        //"required": true, "useOnlyDictionaryValues": false https://suppliers-api.wildberries.ru/api/v1/directory/contents?top=5000&pattern=Кроссовки
        cardAddinList.add(new Card.Addin("Комплектация", new Card.Params("Кроссовки - 1 пара")));
        //TODO Compare data
        //"required": false, "useOnlyDictionaryValues": true, https://suppliers-api.wildberries.ru/api/v1/directory/consists?top=5000&pattern=нейлон
        cardAddinList.add(new Card.Addin("Состав", new Card.Params("нейлон")));
        //TODO Compare data
        //"required": true, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/kinds?top=5000
        cardAddinList.add(new Card.Addin("Пол", new Card.Params("Мальчики")));
        //TODO Compare data
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/seasons?top=5000
        cardAddinList.add(new Card.Addin("Сезон", new Card.Params("демисезон")));
        //TODO Compare data
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&pattern=Шпилька&option=Вид каблука
        cardAddinList.add(new Card.Addin("Вид каблука", new Card.Params("шпилька")));
        //TODO Compare data
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Вид застежки
        cardAddinList.add(new Card.Addin("Вид застежки", new Card.Params("липучка")));
        //"required": false, "useOnlyDictionaryValues": false https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Материал подкладки обуви
        cardAddinList.add(new Card.Addin("Материал подкладки обуви", new Card.Params("искусственная кожа")));
        //"required": false, "useOnlyDictionaryValues": false https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Материал подошвы обуви
        cardAddinList.add(new Card.Addin("Материал подошвы обуви", new Card.Params("искусственная кожа")));
        //"required": false, "useOnlyDictionaryValues": false https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Материал подкладки обуви
        cardAddinList.add(new Card.Addin("Материал подкладки обуви", new Card.Params("искусственная кожа")));

        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Модель обуви
        //cardAddinList.add(new Card.Addin("Модель обуви", new Card.Params("Nike")));
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Тип пронации
        //cardAddinList.add(new Card.Addin("Тип пронации", new Card.Params("")));
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Наличие мембраны
        //cardAddinList.add(new Card.Addin("Наличие мембраны", new Card.Params("")));
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Перепад с пятки на носок
        //cardAddinList.add(new Card.Addin("Перепад с пятки на носок", new Card.Params("")));
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Оптимальная скорость спортсмена
        //cardAddinList.add(new Card.Addin("Оптимальная скорость спортсмена", new Card.Params("")));
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Оптимальный вес спортсмена
        //cardAddinList.add(new Card.Addin("Оптимальный вес спортсмена", new Card.Params("")));
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Тип покрытия
        //cardAddinList.add(new Card.Addin("Тип покрытия", new Card.Params("")));
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Ортопедия
        //cardAddinList.add(new Card.Addin("Ортопедия", new Card.Params("")));
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Полнота обуви (EUR)
        //cardAddinList.add(new Card.Addin("Полнота обуви (EUR)", new Card.Params("")));
        //"required": false, "useOnlyDictionaryValues": true https://suppliers-api.wildberries.ru/api/v1/directory/ext?top=5000&option=Назначение обуви
        //cardAddinList.add(new Card.Addin("Назначение обуви", new Card.Params("")));


        return cardAddinList;
    }

    private List<Card.Addin> createNomenclaturesAddin() {
        //CREATE ADDIN
        List<Card.Addin> nomenclaturesAddinList = new ArrayList<>();
        nomenclaturesAddinList.add(new Card.Addin("Фото", new Card.Params("Nike")));
        nomenclaturesAddinList.add(new Card.Addin("Описание", new Card.Params("Спортивные кроссовки. Беговые.")));

        return nomenclaturesAddinList;
    }

    private List<Card.Variation> createVariations() {
        List<Card.Variation> variationsList = new ArrayList<>();
        Card.Variation variation = new Card.Variation();
        //TODO Create table with barcode OR function to generate barcode
        // Штрихкод товара.
        variation.setBarcode("2001925979004");
        // Структура, содержащая характеристики конкретной вариации товара.
        variation.setAddin(createVariationsAddin());

        variationsList.add(variation);

        return variationsList;
    }

    private List<Card.Addin> createVariationsAddin() {
        //CREATE ADDIN
        List<Card.Addin> variationsAddinList = new ArrayList<>();
        variationsAddinList.add(new Card.Addin("Розничная цена", new Card.Params(6990)));
        variationsAddinList.add(new Card.Addin("Размер", new Card.Params("38")));
        variationsAddinList.add(new Card.Addin("Рос. размер", new Card.Params("38")));

        return variationsAddinList;
    }

    private List<Card.Nomenclature> createCardNomenclatures() {
        //CREATE NOMENCLATURES
        ArrayList<Card.Nomenclature> cardNomenclatureList = new ArrayList<>();

        Card.Nomenclature cardNomenclature = new Card.Nomenclature();
        // Артикул товара.
        cardNomenclature.setVendorCode("NEBULA PUFFY WNTR MID, черный");
        // Массив вариаций товара. Одна цена - один размер - одна вариация.
        cardNomenclature.setVariations(createVariations());
        // Структура, содержащая характеристики конкретной номенклатуры.
        cardNomenclature.setAddin(createNomenclaturesAddin());

        cardNomenclatureList.add(cardNomenclature);

        return cardNomenclatureList;
    }

    public ObjectConfig getObjectConfig(String categoryName) {
        return client.getObjectConfig(categoryName);
    }

}
