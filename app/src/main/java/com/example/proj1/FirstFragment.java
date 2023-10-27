package com.example.proj1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FirstFragment extends Fragment {

    private static final String BASE_URL = "http://192.168.56.1:3001/getProductes/"; //Canviar la IP cada vegada que varii

    // Inicializa Retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // Crea una instancia de la interfaz TriviaApi
    ProductesApi productesApi = retrofit.create(ProductesApi.class);

    Productes productes;

    private View rootView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState


    )

    {

        rootView = inflater.inflate(R.layout.fragment_first, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        // Realiza la llamada a la API
        Call<Productes> call = productesApi.getProductes();
        call.enqueue(new Callback<Productes>() {
            @Override
            public void onResponse(Call<Productes> call, Response<Productes> response) {

                Log.d("msg", "Estic entrant al onResponse");
                if (response.isSuccessful()) {

                    productes = response.body();
                    Log.d("producte:",productes.getProductes().get(0).getNom());

                    MiAdaptador adaptador = new MiAdaptador(productes);
                    recyclerView.setAdapter(adaptador);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // Puedes usar otro LayoutManager según tus necesidades

                }
            }

            @Override
            public void onFailure(Call<Productes> call, Throwable t) {
                Log.d("prueba", "error onFailure "+t.getMessage()+" "+t+" "+ call);
            }
        });




        return rootView;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
    }

    public class MiAdaptador extends RecyclerView.Adapter<MiAdaptador.ViewHolder> {
        // Declaraciones de variables y constructor
        private Productes data; // Suponiendo que estás mostrando una lista de cadenas

        public MiAdaptador(Productes data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Asigna los datos a las vistas en el ViewHolder
            Productes.Producte item = data.getProductes().get(position);
            //holder.textView.setText(item); // Asignar el dato a la vista de texto
            int img_producte = getResources().getIdentifier(item.getUrlImatge().substring(0, item.getUrlImatge().length() - 4), "drawable", getContext().getPackageName());
            int nom_producte = getResources().getIdentifier(item.getNom(), "drawable", getContext().getPackageName());
            int categoria_producte = getResources().getIdentifier(item.getCategoria(), "drawable", getContext().getPackageName());
            holder.img_producte.setImageResource(img_producte);
            holder.nom_producte.setText(item.getNom());
            holder.categoria_producte.setText(item.getCategoria());

        }

        @Override
        public int getItemCount() {
            return data.getProductes().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            // Declaraciones de vistas dentro del elemento

            ImageView img_producte;
            TextView nom_producte;
            TextView categoria_producte;

            public ViewHolder(View itemView) {
                super(itemView);
                // Inicializa las vistas aquí
                img_producte = itemView.findViewById(R.id.img_producte);
                nom_producte = itemView.findViewById(R.id.nom_producte);
                categoria_producte = itemView.findViewById(R.id.categoria_producte);
            }
        }
    }


}