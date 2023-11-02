package com.example.proj1;

public interface CerrarSesion {
    @POST("logout")
    Call<Void> logout();
}
