package com.example.proj1;

import retrofit2.Call;
import retrofit2.http.GET;

public interface dadesUsuariApi {
    @GET("/dadesUsuari")
    Call<Usuaris> getUsuaris();
}
