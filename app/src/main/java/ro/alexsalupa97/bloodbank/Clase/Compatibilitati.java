package ro.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;

public class Compatibilitati {

    @SerializedName("idcompatibilitate")
    private int idCompatibilitate;

    @SerializedName("iddoneaza")
    private GrupeSanguine grupaSanguinaDonatoare;

    @SerializedName("idprimeste")
    private GrupeSanguine grupaSanguinaReceiver;

    public Compatibilitati(int idCompatibilitate, GrupeSanguine grupaSanguinaDonatoare, GrupeSanguine grupaSanguinaReceiver) {
        this.idCompatibilitate = idCompatibilitate;
        this.grupaSanguinaDonatoare = grupaSanguinaDonatoare;
        this.grupaSanguinaReceiver = grupaSanguinaReceiver;
    }

    public Compatibilitati() {
    }

    public int getIdCompatibilitate() {
        return idCompatibilitate;
    }

    public void setIdCompatibilitate(int idCompatibilitate) {
        this.idCompatibilitate = idCompatibilitate;
    }

    public GrupeSanguine getGrupaSanguinaDonatoare() {
        return grupaSanguinaDonatoare;
    }

    public void setGrupaSanguinaDonatoare(GrupeSanguine grupaSanguinaDonatoare) {
        this.grupaSanguinaDonatoare = grupaSanguinaDonatoare;
    }

    public GrupeSanguine getGrupaSanguinaReceiver() {
        return grupaSanguinaReceiver;
    }

    public void setGrupaSanguinaReceiver(GrupeSanguine grupaSanguinaReceiver) {
        this.grupaSanguinaReceiver = grupaSanguinaReceiver;
    }
}
