package com.example.proj1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import androidx.fragment.app.DialogFragment;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class First2Fragment extends Fragment {

    Socket mSocket;

    private static final String BASE_URL = "http://192.168.56.1:3001/getComandes/"; //Canviar la IP cada vegada que varii

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

        try {
            mSocket = IO.socket("http://192.168.56.1:3001");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mSocket.connect();

        if (mSocket.connected()) {
            Toast.makeText(rootView.getContext(), "Socket Connected!!", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d("prueba", "error onFailure ");
        }





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



                    MiAdaptador adaptador = new MiAdaptador(comandes);
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
            String nom_comanda = "Comanda " + (item.getId());
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


            holder.nom_comanda.setText(nom_comanda);
            holder.total.setText("TOTAL: " + total + " €");
            holder.estat.setText("Estat: " + estat);

            mSocket.on("canviEstat", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String data = args[0].toString();
                    Log.d("msg:",data);

                    //TextView missatge = new TextView(MainActivity2.this);
                    if (data.substring(8).equals(""+item.getId())) {
                        holder.estat.setText(data);
                    }


                /*runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        ((LinearLayout)findViewById(R.id.layout)).addView(missatge);
                    }
                });*/
                }


            });

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
                boto_editar = itemView.findViewById(R.id.boto_editar);
                boto_eliminar = itemView.findViewById(R.id.boto_eliminar);
                boto_pagar = itemView.findViewById(R.id.botor_pagar);
            }
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
            newFragment.show(getActivity().getSupportFragmentManager(), getString(R.string.confirmationfragment));
        }
    }





}