package com.example.proj1;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductesApi {

    @GET("/getProductes")
    Call<Productes> getProductes();

    /*@POST("/sendRespostes")
    Call<Void> sendRespostes(@Body Respostes respostes);*/
}
