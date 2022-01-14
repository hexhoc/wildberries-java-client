package ru.rendezvous.wildberriesapi.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class Card {
    ArrayList<Object> addin = new ArrayList<Object>();
    private String countryProduction;
    private String createdAt;
    private String id;
    private float imtId;
    private float imtSupplierId;
    ArrayList<Nomenclature> nomenclatures = new ArrayList<>();
    private String object;
    private String parent;
    private String supplierId;
    private String supplierVendorCode;
    private String updatedAt;
    private String uploadID;
    private float userId;

    @Getter @Setter
    public static class Nomenclature {
        ArrayList < Object > addin = new ArrayList < Object > ();
        private String concatVendorCode;
        private String id;
        private boolean isArchive;
        private float nmId;
        ArrayList < Object > variations = new ArrayList < Object > ();
        private String vendorCode;

    }
}