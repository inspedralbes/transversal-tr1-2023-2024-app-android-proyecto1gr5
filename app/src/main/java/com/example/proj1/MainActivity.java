package com.example.proj1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://192.168.56.1:3001/getPreguntes/"; //Canviar la IP cada vegada que varii

    // Inicializa Retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // Crea una instancia de la interfaz TriviaApi
    ProductesApi productesApi = retrofit.create(ProductesApi.class);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Realiza la llamada a la API
        Call<Productes> call = productesApi.getProductes();
        call.enqueue(new Callback<Productes>() {
            @Override
            public void onResponse(Call<Productes> call, Response<Productes> response) {

                Log.d("msg", "Estic entrant al onResponse");
                if (response.isSuccessful()) {

                    Productes productes = response.body();
                    Log.d("producte:",productes.getProductes().get(0).getNom());
                }
            }

            @Override
            public void onFailure(Call<Productes> call, Throwable t) {
                Log.d("prueba", "error onFailure "+t.getMessage()+" "+t+" "+ call);
            }
        });

        Button botonIrAMenu = findViewById(R.id.buttonInsert);

        botonIrAMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuProductos.class);
                startActivity(intent);
            }
        });
    }


}