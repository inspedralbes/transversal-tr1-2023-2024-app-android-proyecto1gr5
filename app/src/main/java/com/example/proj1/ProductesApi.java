package com.example.proj1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProductesApi {

    @GET("/getProductes")
    Call<ProductesRebre> getProductes();

    @POST("/afegirProducteComanda")
    Call<Void> updateComanda(@Body ProductesEnviar.Producte producte);
}
