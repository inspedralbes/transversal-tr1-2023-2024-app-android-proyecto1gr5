package com.example.proj1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextNombrePersona = findViewById(R.id.editTextTextPersonName);
        EditText editTextContrasenya = findViewById(R.id.editTextTextPassword);
        Button botonIrAMenu = findViewById(R.id.buttonInsert);

        botonIrAMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtengo los valores de usuario y contraseña de los EditText
                String username = editTextNombrePersona.getText().toString();
                String password = editTextContrasenya.getText().toString();
                Log.d("Valores del usuario: ", username);
                Log.d("Valores de la contraseña: ", password);

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    // Creo un objeto DatosEnviar con los valores
                    DatosEnviar datosEnviar = new DatosEnviar(username, password);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.56.1:3968/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiService apiService = retrofit.create(ApiService.class);

                    Call<RespuestaAutenticacion> call = apiService.autenticarUsuario(datosEnviar);
                    call.enqueue(new Callback<RespuestaAutenticacion>() {
                        @Override
                        public void onResponse(Call<RespuestaAutenticacion> call, Response<RespuestaAutenticacion> response) {
                            if (response.isSuccessful()) {
                                RespuestaAutenticacion respuesta = response.body();
                                String mensaje = respuesta.getMensaje();

                                // Verifico el mensaje de respuesta del servidor
                                if ("Inicio de sesión exitoso".equals(mensaje)) {
                                    // La autenticación fue exitosa, entra a la página de productos
                                    Intent intent = new Intent(MainActivity.this, MenuProductos.class);
                                    startActivity(intent);
                                } else {
                                    Log.d("Respuesta del servidor: ", mensaje);
                                    Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.e("ERROR EN LA RESPUESTA", "Código de espuesta: " + response.code());
                                // Error en la respuesta del servidor
                                Toast.makeText(MainActivity.this, "Usuario no auténticado, prueba a registrarte", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RespuestaAutenticacion> call, Throwable t) {
                            Log.e("Error en la solicitud", "Mensaje de error: " + t.getMessage());
                            // Error en la solicitud
                            Toast.makeText(MainActivity.this, "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}