package com.tinkerly.tinkerly.components;

import lombok.Getter;

@Getter
public class EndpointResponse<T> {
    boolean errored;
    String errorMessage;
    T data;

    private EndpointResponse(
            boolean errored,
            String errorMessage,
            T data
    ) {
        this.errored = errored;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public static <U> EndpointResponse<U> passed(U data) {
        return new EndpointResponse<>(false, null, data);
    }

    public static <U> EndpointResponse<U> failed(String errorMessage) {
        return new EndpointResponse<>(true, errorMessage, null);
    }
}
