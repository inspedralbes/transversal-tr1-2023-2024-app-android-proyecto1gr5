package com.example.proj1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ComandesApi {

    @GET("/getComandes")
    Call<Comandes> getComandes();

    @POST("/pagar")
    Call<Void> pagar(@Body Comandes.Comanda comanda);

}
