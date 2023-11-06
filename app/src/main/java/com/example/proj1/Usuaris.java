package com.example.proj1;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Usuaris {

    @SerializedName("Usuaris")

    private List <Usuari> user;

    public List<Usuari> getUsuaris() {
        return user;
    }

    public static class Usuari implements Serializable{
        //@SerializedName("id")
        //private int id;

        //@SerializedName("nom")
        //private String nom;

        //@SerializedName("cognoms")
        //private String cognoms;

        @SerializedName("nick")
        private String nick;

        //@SerializedName("contrasenya")
        //private int contrasenya;

        //@SerializedName("dades_targeta")
        //private int dades_targeta;

        /*public int getId(){
            return id;
        }*/

        /*public String getNom() {
            return nom;
        }*/

       /* public String getCognoms(){
            return cognoms;
        }*/

        public String getNick (){
            return nick;
        }

        /*public int getContrasenya(){
            return contrasenya;
        }*/

        /*public int getDades_targeta(){
            return dades_targeta;
        }*/
    }


}
