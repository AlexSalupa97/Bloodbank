package com.example.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;

public class GrupeSanguine {


    @SerializedName("idgrupasanguina")
    private String grupaSanguina;

    public GrupeSanguine(String grupaSanguina) {
        this.grupaSanguina = grupaSanguina;
    }

    public GrupeSanguine() {

    }

    public String getGrupaSanguina() {
        return grupaSanguina;
    }

    public void setGrupaSanguina(String grupaSanguina) {
        this.grupaSanguina = grupaSanguina;
    }
}
