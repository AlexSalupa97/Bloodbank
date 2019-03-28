package ro.alexsalupa97.bloodbank.RecyclerViewOrizontal;

import java.util.ArrayList;

public class SectionModelIstoric {

    private String titlu;
    private ArrayList<ItemModelIstoric> itemeInSectiune;

    public SectionModelIstoric(String titlu, ArrayList<ItemModelIstoric> itemeInSectiune) {
        this.titlu = titlu;
        this.itemeInSectiune = itemeInSectiune;
    }

    public SectionModelIstoric() {
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public ArrayList<ItemModelIstoric> getItemeInSectiune() {
        return itemeInSectiune;
    }

    public void setItemeInSectiune(ArrayList<ItemModelIstoric> itemeInSectiune) {
        this.itemeInSectiune = itemeInSectiune;
    }
}
