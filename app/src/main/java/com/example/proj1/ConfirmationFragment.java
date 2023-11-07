package com.example.proj1;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;

import android.util.Log;
import android.widget.TimePicker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmationFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfirmationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmationFragment newInstance(String param1, String param2) {
        ConfirmationFragment fragment = new ConfirmationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mostrarDialogoDeConfirmacion();
    }


    private void mostrarDialogoDeConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmació");
        builder.setMessage("¿Estàs segur que vols continuar?");

        // Agregar un botón positivo (Sí)
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Aquí colocas el código que deseas ejecutar si el usuario confirma
                // por ejemplo, eliminar un elemento o realizar una acción.
                Comandes.Comanda comanda = (Comandes.Comanda) getArguments().getSerializable("comanda");
                // Realiza la solicitud POST al servidor
                String BASE_URL = "http://192.168.56.1:3001/pagar/"; // Cambiar la IP según sea necesario

                // Inicializa Retrofit
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Crea una instancia de la interfaz TriviaApi
                ComandesApi comandesApi = retrofit.create(ComandesApi.class);
                Log.d("Mensaje:", "Intentando realizar el pago");
                Log.d("Mensaje:", "Datos de la comanda que se enviarán:");
                Log.d("Mensaje:", "ID: " + comanda.getId());
                Log.d("Mensaje:", "ID Usuario: " + comanda.getId_usuari());
                Log.d("Mensaje:", "Entrega: " + comanda.getEntrega());
                Log.d("Mensaje:", "Estat: " + comanda.getEstat());

                // Realiza la llamada a la API
                Call<Void> call = comandesApi.pagar(comanda);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("Mensaje:", "Pago realizado");

                            // Aquí puedes realizar acciones adicionales si el pago es exitoso
                        } else {
                            Log.d("msg", "Error al hacer la llamada: " + response.code() + " " + response);

                            // Aquí puedes manejar errores de la solicitud
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("msg", "Error onFailure " + t.getMessage() + " " + t + " " + call);

                        // Aquí puedes manejar errores de conexión
                    }
                });
            }
        });

        // Agregar un botón negativo (No)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Código a ejecutar si el usuario cancela
                // Por ejemplo, puedes no hacer nada o mostrar un mensaje.
                // Reemplaza este comentario con tu código.
            }
        });

        // Mostrar el diálogo
        builder.show();
    }


}
