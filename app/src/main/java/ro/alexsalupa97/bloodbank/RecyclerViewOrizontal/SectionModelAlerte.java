package ro.alexsalupa97.bloodbank.RecyclerViewOrizontal;

import java.util.ArrayList;

public class SectionModelAlerte {

    private String titlu;
    private ArrayList<ItemModelAlerte> itemeInSectiune;

    public SectionModelAlerte(String titlu, ArrayList<ItemModelAlerte> itemeInSectiune) {
        this.titlu = titlu;
        this.itemeInSectiune = itemeInSectiune;
    }

    public SectionModelAlerte() {
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public ArrayList<ItemModelAlerte> getItemeInSectiune() {
        return itemeInSectiune;
    }

    public void setItemeInSectiune(ArrayList<ItemModelAlerte> itemeInSectiune) {
        this.itemeInSectiune = itemeInSectiune;
    }
}
