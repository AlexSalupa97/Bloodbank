package com.example.alexsalupa97.bloodbank.RecyclerViewOrizontal;

import com.example.alexsalupa97.bloodbank.Clase.CantitatiCTS;

public class ItemModelAlerte {

    private CantitatiCTS cantitatiCTS;

    public ItemModelAlerte(CantitatiCTS cantitatiCTS) {
        this.cantitatiCTS = cantitatiCTS;
    }

    public ItemModelAlerte() {
    }

    public CantitatiCTS getCantitatiCTS() {
        return cantitatiCTS;
    }

    public void setCantitatiCTS(CantitatiCTS cantitatiCTS) {
        this.cantitatiCTS = cantitatiCTS;
    }
}
