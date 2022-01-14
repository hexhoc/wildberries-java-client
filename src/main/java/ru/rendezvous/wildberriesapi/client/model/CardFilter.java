package ru.rendezvous.wildberriesapi.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class CardFilter {
    private float id;
    private String jsonrpc;
    Params ParamsObject;

    @Getter @Setter
    static class Params {
        Filter FilterObject;
        private boolean isArchive;
        Query QueryObject;
        private String supplierID;
        private boolean withError;
    }

    @Getter @Setter
    static class Query {
        private float limit;
        private float offset;
        private float total;
    }

    @Getter @Setter
    static class Filter {
        ArrayList<Object> filter = new ArrayList<Object>();
        ArrayList<Object> find = new ArrayList<Object>();
        Order OrderObject;
    }

    @Getter @Setter
    static class Order {
        private String column;
        private String order;

    }
}

