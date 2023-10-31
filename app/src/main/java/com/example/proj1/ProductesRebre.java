package com.example.proj1;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductesRebre {

    @SerializedName("result")
    private List<Producte> productes;

    public List<Producte> getProductes() { return productes; }

    public static class Producte implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("categoria")
        private String categoria;
        @SerializedName("nom")
        private String nom;
        @SerializedName("descripció")
        private String descripcio;
        @SerializedName("preu")
        private float preu;
        @SerializedName("url_imatge")
        private String url_imatge;

        public int getId() {
            return id;
        }

        public String getCategoria() {
            return categoria;
        }

        public String getNom() {
            return nom;
        }

        public String getDescripcio() {
            return descripcio;
        }

        public float getPreu() {
            return preu;
        }

        public String getUrlImatge() {
            return url_imatge;
        }
    }
}
