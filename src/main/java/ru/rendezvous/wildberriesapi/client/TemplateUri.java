package ru.rendezvous.wildberriesapi.client;

import com.damnhandy.uri.template.UriTemplate;

import java.util.Date;
import java.util.Map;

class TemplateUri extends Uri {

    private final UriTemplate uri;

    public TemplateUri(UriTemplate uri) {
        this.uri = uri;
    }

    public TemplateUri(String uri) {
        this.uri = UriTemplate.fromTemplate(uri);
    }

    public TemplateUri set(Map<String, Object> values) {
        uri.set(values);
        return this;
    }

    public TemplateUri set(String variableName, Date value) {
        uri.set(variableName, value);
        return this;
    }

    public TemplateUri set(String variableName, Object value) {
        uri.set(variableName, value);
        return this;
    }

    @Override
    public String toString() {
        return uri.expand();
    }
}
