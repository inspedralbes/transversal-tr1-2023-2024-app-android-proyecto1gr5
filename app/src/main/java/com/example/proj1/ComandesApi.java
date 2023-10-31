package com.example.proj1;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ComandesApi {

    @GET("/getComandes")
    Call<Comandes> getComandes();

    /*@POST("/sendRespostes")
    Call<Void> sendRespostes(@Body Respostes respostes);*/

}
