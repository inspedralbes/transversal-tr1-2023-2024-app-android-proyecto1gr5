package com.example.proj1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuantitatFragment extends Fragment {



    private ProductesRebre.Producte producte;

    private TextView unitatsTextView;
    private int unitats_seleccionades = 1; // Unidades por defecto
    private Button incrementButton;
    private Button decrementButton;
    private Button saveButton;
    private Button cancelButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.escollir_quantitat, container, false);

        unitatsTextView = rootView.findViewById(R.id.unitatsTextView);
        incrementButton = rootView.findViewById(R.id.incrementButton);
        decrementButton = rootView.findViewById(R.id.decrementButton);
        saveButton = rootView.findViewById(R.id.saveButton);
        cancelButton = rootView.findViewById(R.id.cancelButton);
        // Recupera los argumentos pasados
            producte = (ProductesRebre.Producte) getArguments().getSerializable("producte");
            // Configura tus vistas según los datos del producto, por ejemplo:
            // producte.getNombre(), producte.getCantidad(), etc.


        // Mostrar la quantitat inicial d'unitats
        updateUnitsTextView();

        // Configurar el botón para aumentar la cantidad
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitats_seleccionades++;
                updateUnitsTextView();
            }
        });

        // Configurar el botón para disminuir la cantidad
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unitats_seleccionades > 1) {
                    unitats_seleccionades--;
                    updateUnitsTextView();
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtén la cantidad seleccionada, por ejemplo, desde un TextView o una variable.

                // Llama al método onQuantitySelected de la interfaz

                    ProductesEnviar.Producte producte_enviar = new ProductesEnviar.Producte(producte, unitats_seleccionades);

                    String BASE_URL_updateComanda = "http://192.168.122.188:3001/afegirProducteComanda/"; //Canviar la IP cada vegada que varii

                    Retrofit retrofit_updateComanda = new Retrofit.Builder()
                            .baseUrl(BASE_URL_updateComanda)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    // Crea una instancia de la interfaz TriviaApi
                    ProductesApi productesApi = retrofit_updateComanda.create(ProductesApi.class);


                    Log.d("producte_id","" + producte_enviar.getId());

                    // Realiza la llamada a la API
                    Call<Void> call = productesApi.updateComanda(producte_enviar);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("Prueba","Estoy entrando al onResponse");
                            if (response.isSuccessful()) {

                                Log.d("Missatge:","Post realitzat");

                            } else {
                                Log.d("msg", "Error al fer el call" + response.code()+ " " + response);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("msg", "error onFailure "+t.getMessage()+" "+t+" "+ call);
                        }
                    });

                // Cierra QuantitatFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.remove(QuantitatFragment.this);
                transaction.addToBackStack(null); // Opcional, para agregar la transacción a la pila de retroceso
                transaction.commit();


            }



        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cierra QuantitatFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.remove(QuantitatFragment.this);
                transaction.addToBackStack(null); // Opcional, para agregar la transacción a la pila de retroceso
                transaction.commit();

            }
        });

        return rootView;
    }

    // Actualizar el texto que muestra la cantidad de unidades
    private void updateUnitsTextView() {
        unitatsTextView.setText(String.valueOf(unitats_seleccionades));
    }

    /*public void showDatePickerFragment(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();

        if (getActivity() != null) {
            newFragment.show(getActivity().getSupportFragmentManager(), getString(R.string.datepicker));
        }
    }*/




}
