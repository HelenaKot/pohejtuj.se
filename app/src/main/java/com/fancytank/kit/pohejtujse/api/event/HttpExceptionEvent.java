package com.fancytank.kit.pohejtujse.api.event;

// 10/14/2017.
public class HttpExceptionEvent {
    Throwable throwable;

    public HttpExceptionEvent(Throwable throwable) {
        this.throwable = throwable;
    }
}
