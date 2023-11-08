package com.example.proj1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuantityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuantityFragment extends DialogFragment {

    //private SharedViewModel sharedViewModel;

    private View rootView;

    private ProductesRebre.Producte producte;

    private TextView unitatsTextView;
    private int unitats_seleccionades = 1; // Unidades por defecto
    private Button incrementButton;
    private Button decrementButton;
    private Button saveButton;
    private Button cancelButton;
    int posicio;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuantityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuantityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuantityFragment newInstance(String param1, String param2) {
        QuantityFragment fragment = new QuantityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        // Resto del código de onCreate
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.escollir_quantitat, container, false);

        //LinearLayout linearLayout = rootView.findViewById(R.id.linearlayout_quant);


        unitatsTextView = rootView.findViewById(R.id.unitatsTextView);
        incrementButton = rootView.findViewById(R.id.incrementButton);
        decrementButton = rootView.findViewById(R.id.decrementButton);
        saveButton = rootView.findViewById(R.id.saveButton);
        cancelButton = rootView.findViewById(R.id.cancelButton);
        // Recupera los argumentos pasados
        producte = (ProductesRebre.Producte) getArguments().getSerializable("producte");
        posicio = getArguments().getInt("posicio");
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


                ProductesEnviar.Producte producte_enviar = new ProductesEnviar.Producte(producte, unitats_seleccionades);

                String BASE_URL_updateComanda = "http://192.168.56.1:3968/afegirProducteComanda/"; //Canviar la IP cada vegada que varii

                Retrofit retrofit_updateComanda = new Retrofit.Builder()
                        .baseUrl(BASE_URL_updateComanda)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Crea una instancia de la interfaz TriviaApi
                ProductesApi productesApi = retrofit_updateComanda.create(ProductesApi.class);


                Log.d("producte_id", "" + producte_enviar.getId());

                // Realiza la llamada a la API
                Call<Void> call = productesApi.updateComanda(producte_enviar);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("Prueba", "Estoy entrando al onResponse");
                        if (response.isSuccessful()) {

                            Log.d("Missatge:", "Post realitzat");

                        } else {
                            Log.d("msg", "Error al fer el call" + response.code() + " " + response);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("msg", "error onFailure " + t.getMessage() + " " + t + " " + call);
                    }
                });

                // Actualiza el ViewModel con la nueva cantidad y la posición
                //sharedViewModel.selectProduct(posicio, unitats_seleccionades);

            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cierra QuantitatFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.remove(QuantityFragment.this);
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


}