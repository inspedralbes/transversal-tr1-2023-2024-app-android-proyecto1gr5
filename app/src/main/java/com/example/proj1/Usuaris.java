package com.example.proj1;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Usuaris implements Serializable {
    @SerializedName("nom")
    private String nom;

    @SerializedName("cognoms")
    private String cognoms;

    @SerializedName("nick")
    private String nick;

    @SerializedName("dades_targeta")
    private String dades_targeta;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDades_targeta() {
        return dades_targeta;
    }

    public void setDades_targeta(String dades_targeta) {
        this.dades_targeta = dades_targeta;
    }
}
