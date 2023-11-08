package com.example.proj1;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Comandes {

    @SerializedName("comandes")
    private List<Comanda> comanda;

    public List<Comanda> getComandes() {
        return comanda;
    }

    public static class Comanda implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("id_usuari")
        private int id_usuari;

        @SerializedName("entrega")
        private String entrega;

        @SerializedName("estat")
        private String estat;
        @SerializedName("entrega")
        public void setEntrega(String entrega) { //Se utiliza para configurar la hora y los minutos cuando el usuario selecciona el DatePicker
            this.entrega = entrega;
        }

        @SerializedName("productes")
        private List<ProductesEnviar.Producte> productes;

        public int getId() { return id; }

        public int getId_usuari() { return id_usuari; }

        public String getEntrega() { return entrega; }

        public String getEstat() { return estat; }

        public List<ProductesEnviar.Producte> getProductes() { return productes; }

    }
}
