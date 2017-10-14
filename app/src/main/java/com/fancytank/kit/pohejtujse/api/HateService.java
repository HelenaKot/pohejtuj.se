package com.fancytank.kit.pohejtujse.api;

import com.fancytank.kit.pohejtujse.api.dto.Hate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface HateService {
    @POST(HateClient.POST_ENDPOINT)
    Call<Void> postHate(@Body Hate hate);
}
