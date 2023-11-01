package com.example.proj1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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


    public void showQuantitatFragment(View v, ProductesRebre.Producte producte) {
        QuantitatFragment newFragment = new QuantitatFragment();
        Bundle args = new Bundle();
        args.putSerializable("producte", producte);
        newFragment.setArguments(args);



        if (getChildFragmentManager() != null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment); // Reemplaza R.id.fragment_container con el ID de tu contenedor de fragmentos
            transaction.addToBackStack(null); // Opcional, para agregar la transacción a la pila de retroceso
            transaction.commit();
        }
    }

    private static final String BASE_URL_getProductes = "http://192.168.56.1:3001/getProductes/"; //Canviar la IP cada vegada que varii


    // Inicializa Retrofit
    Retrofit retrofit_getProductes = new Retrofit.Builder()
            .baseUrl(BASE_URL_getProductes)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    // Crea una instancia de la interfaz TriviaApi
    ProductesApi productesApi = retrofit_getProductes.create(ProductesApi.class);

    ProductesRebre productes;

    private View rootView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState


    )

    {

        rootView = inflater.inflate(R.layout.fragment_first, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewProductes);

        // Realiza la llamada a la API
        Call<ProductesRebre> call = productesApi.getProductes();
        call.enqueue(new Callback<ProductesRebre>() {
            @Override
            public void onResponse(Call<ProductesRebre> call, Response<ProductesRebre> response) {

                Log.d("msg", "Estic entrant al onResponse");
                if (response.isSuccessful()) {

                    productes = response.body();
                    Log.d("producte:",productes.getProductes().get(0).getNom());

                    List<String> llista_categories = new ArrayList<String>();
                    for (int i=0;i<productes.getProductes().size();i++) {
                        boolean categoria_existent = false;
                        for (int j=0;j<llista_categories.size();j++) {
                            if (llista_categories.get(j).equals(productes.getProductes().get(i).getCategoria())) {
                                categoria_existent = true;
                            }
                        }
                        if (!categoria_existent) {
                            llista_categories.add(productes.getProductes().get(i).getCategoria());
                        }
                    }

                    MiAdaptador adaptador = new MiAdaptador(productes);
                    recyclerView.setAdapter(adaptador);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // Puedes usar otro LayoutManager según tus necesidades

                }
            }

            @Override
            public void onFailure(Call<ProductesRebre> call, Throwable t) {
                Log.d("prueba", "error onFailure "+t.getMessage()+" "+t+" "+ call);
            }
        });




        return rootView;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button boto_afegir_comanda = (Button) rootView.findViewById(R.id.boto_modificar_quantitat);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
    }

    public class MiAdaptador extends RecyclerView.Adapter<MiAdaptador.ViewHolder> {
        // Declaraciones de variables y constructor
        private ProductesRebre data; // Suponiendo que estás mostrando una lista de cadenas

        public MiAdaptador(ProductesRebre data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_productes, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Asigna los datos a las vistas en el ViewHolder
            ProductesRebre.Producte item = data.getProductes().get(position);

            int img_producte = getResources().getIdentifier(item.getUrlImatge().substring(0, item.getUrlImatge().length() - 4), "drawable", getContext().getPackageName());

            holder.img_producte.setImageResource(img_producte);
            holder.nom_producte.setText(item.getNom());
            holder.categoria_producte.setText(item.getCategoria());
            holder.boto_afegir_comanda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    showQuantitatFragment(view, item);



                }
            });
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
            Button boto_afegir_comanda;

            public ViewHolder(View itemView) {
                super(itemView);
                // Inicializa las vistas aquí
                img_producte = itemView.findViewById(R.id.img_producte);
                nom_producte = itemView.findViewById(R.id.nom_comanda);
                categoria_producte = itemView.findViewById(R.id.categoria_producte);
                boto_afegir_comanda = itemView.findViewById(R.id.boto_modificar_quantitat);
            }
        }





    }


}