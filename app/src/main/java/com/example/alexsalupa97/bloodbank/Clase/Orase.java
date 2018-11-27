package com.example.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;

public class Orase {

    @SerializedName("idoras")
    private int idOras;

    @SerializedName("judet")
    private String judet;

    @SerializedName("numeoras")
    private String oras;

    public Orase(int idOras, String judet, String oras) {
        this.idOras = idOras;
        this.judet = judet;
        this.oras = oras;
    }

    public Orase() {
    }

    public int getIdOras() {
        return idOras;
    }

    public void setIdOras(int idOras) {
        this.idOras = idOras;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }
}
