package com.example.proj1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class First2Fragment extends Fragment  {

    private MiAdaptador adaptador;


    private static final String BASE_URL = "http://192.168.0.18:3968/getComandes/"; //Canviar la IP cada vegada que varii

    // Inicializa Retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // Crea una instancia de la interfaz TriviaApi
    ComandesApi comandesApi = retrofit.create(ComandesApi.class);

    Comandes comandes;

    private View rootView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState


    )

    {


        rootView = inflater.inflate(R.layout.fragment_first2, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewComandes);

        // Realiza la llamada a la API
        Call<Comandes> call = comandesApi.getComandes();
        call.enqueue(new Callback<Comandes>() {
            @Override
            public void onResponse(Call<Comandes> call, Response<Comandes> response) {

                Log.d("msg", "Estic entrant al onResponse");
                if (response.isSuccessful()) {

                    comandes = response.body();
                    Log.d("comanda:","" + comandes.getComandes().get(0).getId());

                    // Invierte el orden de la lista de comandas
                    Collections.reverse(comandes.getComandes());



                    adaptador = new MiAdaptador(comandes);
                    recyclerView.setAdapter(adaptador);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // Puedes usar otro LayoutManager según tus necesidades

                }
            }

            @Override
            public void onFailure(Call<Comandes> call, Throwable t) {
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
        private Comandes data; // Suponiendo que estás mostrando una lista de cadenas

        public MiAdaptador(Comandes data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_comandes, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Asigna los datos a las vistas en el ViewHolder
            Comandes.Comanda item = data.getComandes().get(position);
            //holder.textView.setText(item); // Asignar el dato a la vista de texto
            String nom_comanda = "Comanda " + (position+1);
            holder.layout_productes.removeAllViews();
            float total = 0;
            for (int i=0;i<item.getProductes().size();i++) {
                LinearLayout layout_producte = new LinearLayout(rootView.getContext());
                layout_producte.setOrientation(LinearLayout.HORIZONTAL);
                TextView nom_producte = new TextView(rootView.getContext());
                nom_producte.setText("- " + item.getProductes().get(i).getNom());
                nom_producte.setWidth(700);
                TextView preu = new TextView(rootView.getContext());
                preu.setText("" + item.getProductes().get(i).getPreu() + "€");
                preu.setWidth(250);
                TextView quantitat = new TextView(rootView.getContext());
                quantitat.setText("x" + item.getProductes().get(i).getQuantitat());

                total = total + item.getProductes().get(i).getPreu()*item.getProductes().get(i).getQuantitat();

                layout_producte.addView(nom_producte);
                layout_producte.addView(preu);
                layout_producte.addView(quantitat);

                holder.layout_productes.addView(layout_producte);
            }

            String estat = item.getEstat();
            String dataComanda = convertirFormatoFechaHora(item.getDataComanda());


            holder.nom_comanda.setText(nom_comanda);
            holder.total.setText("TOTAL: " + total + " €");
            holder.estat.setText("Estat: " + estat);
            holder.dataComanda.setText("Data Comanda: " + dataComanda);

            holder.boto_pagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    showConfirmationFragment(view, item);



                }
            });

            holder.boto_editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    showEditarComandaFragment(view, item);




                }
            });

            if (!item.getEstat().equals("oberta")) {
                // Comanda pagada, deshabilita los botones y cambia la apariencia
                holder.boto_pagar.setEnabled(false);
                holder.boto_editar.setEnabled(false);
                holder.boto_eliminar.setEnabled(false);
                // Cambia la apariencia de los botones, por ejemplo, el color de fondo
                holder.boto_pagar.setBackgroundColor(ContextCompat.getColor(rootView.getContext(), R.color.colorBotonDeshabilitado));
                holder.boto_editar.setBackgroundColor(ContextCompat.getColor(rootView.getContext(), R.color.colorBotonDeshabilitado));
                holder.boto_eliminar.setBackgroundColor(ContextCompat.getColor(rootView.getContext(), R.color.colorBotonDeshabilitado));
            }

        }

        @Override
        public int getItemCount() {

            return data.getComandes().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            // Declaraciones de vistas dentro del elemento

            TextView nom_comanda;
            LinearLayout layout_productes;
            TextView total;
            TextView estat;
            TextView dataComanda;
            Button boto_editar;
            Button boto_eliminar;
            Button boto_pagar;

            public ViewHolder(View itemView) {
                super(itemView);
                // Inicializa las vistas aquí
                nom_comanda = itemView.findViewById(R.id.nom_comanda);
                layout_productes = itemView.findViewById(R.id.layout_productes);
                total = itemView.findViewById(R.id.total);
                estat = itemView.findViewById(R.id.estat);
                dataComanda = itemView.findViewById(R.id.dataComanda);
                boto_editar = itemView.findViewById(R.id.boto_editar);
                boto_eliminar = itemView.findViewById(R.id.boto_eliminar);
                boto_pagar = itemView.findViewById(R.id.botor_pagar);
            }
        }
    }

    private String convertirFormatoFechaHora(String fechaHoraISO8601) {
        try {
            SimpleDateFormat formatoISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            SimpleDateFormat formatoLegible = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date fechaHora = formatoISO8601.parse(fechaHoraISO8601);
            return formatoLegible.format(fechaHora);
        } catch (ParseException e) {
            e.printStackTrace();
            return fechaHoraISO8601; // En caso de error, se muestra la cadena original
        }
    }


    public void showConfirmationFragment(View v, Comandes.Comanda comanda) {
        ConfirmationFragment newFragment = new ConfirmationFragment();
        Bundle args = new Bundle();
        args.putSerializable("comanda", comanda);
        newFragment.setArguments(args);
        if (getActivity() != null) {
            newFragment.show(getActivity().getSupportFragmentManager(), getString(R.string.confirmationfragment));
        }
    }

    public void showEditarComandaFragment(View v, Comandes.Comanda comanda) {
        EditarComandaFragment newFragment = new EditarComandaFragment();
        Bundle args = new Bundle();
        args.putSerializable("comanda", comanda);
        newFragment.setArguments(args);
        if (getActivity() != null) {
            newFragment.show(getActivity().getSupportFragmentManager(), getString(R.string.editarcomandafragment));
        }
    }





}