package com.example.proj1;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductesEnviar  {

    @SerializedName("result")
    private List<ProductesEnviar.Producte> productes;

    public static class Producte implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("categoria")
        private String categoria;
        @SerializedName("nom")
        private String nom;
        @SerializedName("descripcio")
        private String descripcio;
        @SerializedName("preu")
        private float preu;
        @SerializedName("url_imatge")
        private String url_imatge;

        @SerializedName("quantitat")
        private int quantitat;

        @SerializedName("editarQuantitat")
        private boolean editarQuantitat;

        public Producte (ProductesRebre.Producte producte, int quantitat, boolean editarQuantitat) {
            this.id = producte.getId();
            this.categoria = producte.getCategoria();
            this.nom = producte.getNom();
            this.descripcio = producte.getDescripcio();
            this.preu = producte.getPreu();
            this.url_imatge = null;
            this.quantitat = quantitat;
            this.editarQuantitat = editarQuantitat;
        }

        public void setQuantitat (int quantitat) {
            this.quantitat = quantitat;
        }

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

        public int getQuantitat() {
            return quantitat;
        }
    }
}
