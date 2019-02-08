package com.example.alexsalupa97.bloodbank.RecyclerViewOrizontal;

import java.util.ArrayList;

public class SectionModel {

    private String titlu;
    private ArrayList<ItemModel> itemeInSectiune;

    public SectionModel(String titlu, ArrayList<ItemModel> itemeInSectiune) {
        this.titlu = titlu;
        this.itemeInSectiune = itemeInSectiune;
    }

    public SectionModel() {
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public ArrayList<ItemModel> getItemeInSectiune() {
        return itemeInSectiune;
    }

    public void setItemeInSectiune(ArrayList<ItemModel> itemeInSectiune) {
        this.itemeInSectiune = itemeInSectiune;
    }
}
