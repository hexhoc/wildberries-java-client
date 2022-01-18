package ru.rendezvous.wildberriesapi.client.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class RequestCardCreate {
    private String id;
    private String jsonrpc = "2.0";
    private HashMap<String, Object> Params;
}
