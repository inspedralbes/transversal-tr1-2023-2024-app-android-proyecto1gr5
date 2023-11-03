package com.example.proj1;

public class DatosEnviar { //Clase para enviar los datos de los EditText del usuario al sevidor
    private String nomUsuari;
    private String contrasenya;

    public DatosEnviar(String nomUsuari, String contrasenya){
        this.nomUsuari = nomUsuari;
        this.contrasenya = contrasenya;
    }
}
