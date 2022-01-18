package ru.rendezvous.wildberriesapi.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class Card {
    List<Addin> addin = new ArrayList<>();
    private String countryProduction;
//    private String createdAt;
    private String id;
//    private float imtId;
//    private float imtSupplierId;
    List<Nomenclature> nomenclatures = new ArrayList<>();
    private String object;
    private String parent;
//    private String supplierId;
    private String supplierVendorCode;
//    private String updatedAt;
    private String uploadID;
//    private float userId;

    @Getter @Setter
    public static class Nomenclature {
        List <Addin> addin = new ArrayList<>();
        private String concatVendorCode;
        private String id;
        private boolean isArchive;
//        private float nmId;
        List <Variation> variations = new ArrayList<>();
        private String vendorCode;
    }

    @Getter @Setter
    public static class Addin {
        public Addin() { }

        public Addin(String type) {
            this.type = type;
        }

        public Addin(String type, Params params) {
            this.type = type;
            this.params = Arrays.asList(params);
        }

        public Addin(String type, List<Params> params) {
            this.type = type;
            this.params = params;
        }

        List <Params> params = new ArrayList<>();
        private String type;
    }

    @Getter @Setter
    public static class Params {
        private Float count;
        private String units;
        private String value;

        public Params() { }

        public Params(float count) {
            this.count = count;
        }

        public Params(String value) {
            this.value = value;
        }

        public Params(Float count, String units, String value) {
            this.count = count;
            this.units = units;
            this.value = value;
        }
    }

    @Getter @Setter
    public static class Variation {
        List <Addin> addin = new ArrayList<>();
        private String barcode;
        List <String> barcodes = new ArrayList<>();
//        private float chrtId;
        List <String> errors = new ArrayList<>();
        private String id;
    }
}