package ru.rendezvous.wildberriesapi.client.model.config;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
/**
 * /api/v1/config/get/object/all Получение всех имен доступных категорий
 * /api/v1/config/get/object/translated Получение конфигурации предмета
 */
public class ObjectConfig {
    ArrayList<AddinConfig> addin = new ArrayList<>();
    private float id;
    private boolean isDeleted;
    private String name;
    private float noReturnMap;
    NomenclatureConfig nomenclature;
    private String parent;
    private float parent_id;
    private float rv;
    private boolean visible;

    @Getter @Setter
    public static class AddinConfig {
        private String dictionary;
        private float id;
        private boolean isAvailable;
        private boolean isNumber;
        private float maxCount;
        private boolean required;
        private String type;
        ArrayList<String> units = new ArrayList<>();
        private boolean useOnlyDictionaryValues;
    }

    @Getter @Setter
    public static class NomenclatureConfig {
        ArrayList<AddinConfig> addin = new ArrayList<>();
        VariationConfig variation;
    }

    @Getter @Setter
    public static class VariationConfig {
        ArrayList<AddinConfig> addin = new ArrayList<>();
    }
}


