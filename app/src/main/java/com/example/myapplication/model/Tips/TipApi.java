package com.example.myapplication.model.Tips;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TipApi {

    @GET("advice/search/life")
    Call<TipSearchResult> getTip();

}