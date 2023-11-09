package com.example.proj1;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.app.AlertDialog;
import android.content.DialogInterface;

import android.util.Log;

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
public class CargaFragment extends DialogFragment {

    private SharedViewModel sharedViewModel;


    //private ConfirmationListener listener;

    //private ConfirmationListener confirmationListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CargaFragment() {
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
    public static CargaFragment newInstance(String param1, String param2) {
        CargaFragment fragment = new CargaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Configura el listener cuando se adjunta el Fragment a la Activity
            listener = (ConfirmationListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " debe implementar ConfirmationListener");
        }
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        mostrarCargaFragment();
    }


    private void mostrarCargaFragment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        // Agregar un botón positivo (Sí)
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                // Aquí colocas el código que deseas ejecutar si el usuario confirma
                // por ejemplo, eliminar un elemento o realizar una acción.
                Comandes.Comanda comanda = (Comandes.Comanda) getArguments().getSerializable("comanda");
                String entrega = (String) getArguments().getString("entrega");
                comanda.setEntrega(entrega);
                // Realiza la solicitud POST al servidor
                String BASE_URL = "http://takeawayg5.dam.inspedralbes.cat:3968/"; // Cambiar la IP según sea necesario

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

                // Cuando el usuario confirma
                sharedViewModel.setConfirmationResult(true);

            }
        });

        // Agregar un botón negativo (No)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Código a ejecutar si el usuario cancela
                // Por ejemplo, puedes no hacer nada o mostrar un mensaje.
                // Reemplaza este comentario con tu código.
                // Llama al método del listener indicando que el usuario hizo clic en "No"
                //listener.onConfirmationResult(false);
            }
        });

        // Mostrar el diálogo
        builder.show();
    }


