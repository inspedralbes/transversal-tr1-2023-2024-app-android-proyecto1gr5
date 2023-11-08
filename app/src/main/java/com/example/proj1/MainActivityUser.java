package com.example.proj1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityUser extends AppCompatActivity {

    private static final String BASE_URL_DADES_USU = "http://takeawayg5.dam.inspedralbes.cat:3968"; //Canviar la IP cada vegada que varii

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
                    Usuaris dades = response.body();
                    // Accede a los datos usando los getter
                    String nom = dades.getNom();
                    String cognoms = dades.getCognoms();
                    String nick = dades.getNick();
                    String dades_targeta = dades.getDades_targeta();

                    TextView nomUsuari = findViewById(R.id.nickUsu);
                    nomUsuari.setText(nick);

                    TextView nombre = findViewById(R.id.nomUsu);
                    nombre.setText(nom);

                    TextView cognom =findViewById(R.id.cognomsUsu);
                    cognom.setText(cognoms);

                    TextView tarjeta = findViewById(R.id.TargetaUsu);
                    tarjeta.setText(dades_targeta);


                }
            }

            @Override
            public void onFailure(Call<Usuaris> call, Throwable t) {
                Log.d("prueba", "error onFailure " + t.getMessage() + " " + t + " " + call);
            }

        });

    }
    //Overflow options menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Administrador de el directorio
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        //Opcion para mostrar datos del usuario
        if(id == R.id.item1) {
            Intent intent = new Intent(MainActivityUser.this, MainActivityUser.class);
            startActivity(intent);

            //Opcion para ir al Menu de Productos
        }else if(id == R.id.item2){
            Intent intent = new Intent(MainActivityUser.this,MenuProductos.class);
            startActivity(intent);

            //Opcion para ir al Menu de Comandes
        }else if(id == R.id.item3){
            Intent intent= new Intent(MainActivityUser.this, MainActivity2.class);
            startActivity(intent);
        } //Opcion para salir de la aplicacion
        else if (id == R.id.item4){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}