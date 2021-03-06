package com.acuo.common.http.client;

import lombok.Data;
import okhttp3.Request;

import java.util.function.Predicate;

@Data
public class OkHttpCall implements Call {

    private final ClientEndPoint client;
    private final Request request;
    private final Predicate<String> predicate;

    OkHttpCall(ClientEndPoint client, Request request, Predicate<String> predicate) {
        this.client = client;
        this.request = request;
        this.predicate = predicate;
    }

    public String send() {
        return client.send(this);
    }
}