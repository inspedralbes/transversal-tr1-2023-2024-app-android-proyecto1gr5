package com.example.proj1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProductesApi {

    @GET("/getProductesAndroid")
    Call<ProductesRebre> getProductesAndroid();

    @POST("/afegirProducteComanda")
    Call<Void> afegirProducteComanda(@Body ProductesEnviar.Producte producte);
}
