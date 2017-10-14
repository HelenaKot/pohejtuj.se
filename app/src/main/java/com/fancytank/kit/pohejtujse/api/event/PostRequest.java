package com.fancytank.kit.pohejtujse.api.event;

import com.fancytank.kit.pohejtujse.api.dto.Hate;

// 10/14/2017.
public class PostRequest {
    Hate hate;

    public PostRequest(Hate hate) {
        this.hate = hate;
    }
}
