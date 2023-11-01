package com.example.proj1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarComandaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarComandaFragment extends DialogFragment {

    private View rootView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarComandaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarComandaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarComandaFragment newInstance(String param1, String param2) {
        EditarComandaFragment fragment = new EditarComandaFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_first, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewProductes);

        Comandes.Comanda comanda = (Comandes.Comanda) getArguments().getSerializable("comanda");
        MiAdaptador adaptador = new MiAdaptador(comanda.getProductes());
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // Puedes usar otro LayoutManager según tus necesidades
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_comanda, container, false);


    }

    public class MiAdaptador extends RecyclerView.Adapter<MiAdaptador.ViewHolder> {
        // Declaraciones de variables y constructor
        private List<ProductesEnviar.Producte> data; // Suponiendo que estás mostrando una lista de cadenas

        public MiAdaptador(List<ProductesEnviar.Producte> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.editar_comanda, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Asigna los datos a las vistas en el ViewHolder
            ProductesEnviar.Producte item = data.get(position);
            //holder.textView.setText(item); // Asignar el dato a la vista de texto

            holder.nom_producte.setText(item.getNom());
            holder.categoria_producte.setText(item.getCategoria());
            holder.preu_quant_producte.setText(item.getPreu() + " €   " + "x" + item.getQuantitat());
            /*holder.boto_modificar_quantitat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    showQuantitatFragment(view, item);



                }
            });*/
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            // Declaraciones de vistas dentro del elemento

            TextView nom_producte;
            TextView categoria_producte;
            TextView preu_quant_producte;
            Button boto_modificar_quantitat;
            Button boto_eliminar;

            public ViewHolder(View itemView) {
                super(itemView);
                // Inicializa las vistas aquí
                nom_producte = itemView.findViewById(R.id.nom_comanda);
                categoria_producte = itemView.findViewById(R.id.categoria_producte);
                preu_quant_producte = itemView.findViewById(R.id.preu_quant_producte);
                boto_modificar_quantitat = itemView.findViewById(R.id.boto_modificar_quantitat);
                boto_eliminar = itemView.findViewById(R.id.boto_eliminar2);
            }
        }





    }

    /*public void showQuantitatFragment(View v, ProductesEnviar.Producte producte) {
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
    }*/
}