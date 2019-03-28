package ro.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;

public class IntrariCTS {

    @SerializedName("cantitateprimitaml")
    private int cantitatePrimitaML;

    @SerializedName("dataprimire")
    private String dataPrimire;

    @SerializedName("idcts")
    private CTS cts;

    @SerializedName("idgrupasanguina")
    private GrupeSanguine grupaSanguina;

    @SerializedName("idistoricintraricts")
    private int idIntrariCTS;

    public IntrariCTS(int cantitatePrimitaML, String dataPrimire, CTS cts, GrupeSanguine grupaSanguina, int idIntrariCTS) {
        this.cantitatePrimitaML = cantitatePrimitaML;
        this.dataPrimire = dataPrimire;
        this.cts = cts;
        this.grupaSanguina = grupaSanguina;
        this.idIntrariCTS = idIntrariCTS;
    }

    public IntrariCTS() {
    }

    public int getCantitatePrimitaML() {
        return cantitatePrimitaML;
    }

    public void setCantitatePrimitaML(int cantitatePrimitaML) {
        this.cantitatePrimitaML = cantitatePrimitaML;
    }

    public String getDataPrimire() {
        return dataPrimire;
    }

    public void setDataPrimire(String dataPrimire) {
        this.dataPrimire = dataPrimire;
    }

    public CTS getCts() {
        return cts;
    }

    public void setCts(CTS cts) {
        this.cts = cts;
    }

    public GrupeSanguine getGrupaSanguina() {
        return grupaSanguina;
    }

    public void setGrupaSanguina(GrupeSanguine grupaSanguina) {
        this.grupaSanguina = grupaSanguina;
    }

    public int getIdIntrariCTS() {
        return idIntrariCTS;
    }

    public void setIdIntrariCTS(int idIntrariCTS) {
        this.idIntrariCTS = idIntrariCTS;
    }
}
