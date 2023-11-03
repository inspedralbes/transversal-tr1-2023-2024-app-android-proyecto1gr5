package com.example.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityUser extends AppCompatActivity {

    private static final String BASE_URL_DADES_USU = "http://192.168.122.188:3001/getUsu/"; //Canviar la IP cada vegada que varii

    // Inicializa Retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL_DADES_USU)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    dadesUsuariApi dadesUsuariApi = retrofit.create(dadesUsuariApi.class);

    Usuaris usuaris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);



        // Realiza la llamada a la API
        Call<Usuaris> call = dadesUsuariApi.getUsuaris();
        call.enqueue(new Callback<Usuaris>() {
            @Override
            public void onResponse(Call<Usuaris> call, Response<Usuaris> response) {

                Log.d("msg", "Estic entrant al onResponse al User");
                if (response.isSuccessful()) {

                    usuaris = response.body();
                    Log.d("producte:", usuaris.getUsuaris().get(0).getNom());


                }
            }

            @Override
            public void onFailure(Call<Usuaris> call, Throwable t) {
                Log.d("prueba", "error onFailure " + t.getMessage() + " " + t + " " + call);
            }

        });

    }


}