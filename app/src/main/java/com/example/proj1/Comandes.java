package com.example.proj1;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Comandes {

    @SerializedName("comandes")
    private List<Comanda> comanda;

    public List<Comanda> getComandes() {
        return comanda;
    }

    public static class Comanda {
        @SerializedName("id")
        private int id;
        @SerializedName("id_usuari")
        private int id_usuari;

        @SerializedName("entrega")
        private String entrega;

        @SerializedName("estat")
        private String estat;

        @SerializedName("productes")
        private List<ProductesRebre.Producte> productes;

        public int getId() { return id; }

        public int getId_usuari() { return id_usuari; }

        public String getEntrega() { return entrega; }

        public String getEstat() { return estat; }

        public List<ProductesRebre.Producte> getProductes() { return productes; }



    }
}
