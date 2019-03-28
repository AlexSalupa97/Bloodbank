package ro.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;

public class IesiriCTS {
    @SerializedName("cantitateiesitaml")
    private int cantitateIesitaML;

    @SerializedName("dataiesire")
    private String dataIesire;

    @SerializedName("idcts")
    private CTS cts;

    @SerializedName("idgrupasanguina")
    private GrupeSanguine grupaSanguina;

    @SerializedName("idistoriciesiricts")
    private int idIesiriCTS;

    public IesiriCTS(int cantitateIesitaML, String dataIesire, CTS cts, GrupeSanguine grupaSanguina, int idIesiriCTS) {
        this.cantitateIesitaML = cantitateIesitaML;
        this.dataIesire = dataIesire;
        this.cts = cts;
        this.grupaSanguina = grupaSanguina;
        this.idIesiriCTS = idIesiriCTS;
    }

    public IesiriCTS() {
    }

    public int getCantitateIesitaML() {
        return cantitateIesitaML;
    }

    public void setCantitateIesitaML(int cantitateIesitaML) {
        this.cantitateIesitaML = cantitateIesitaML;
    }

    public String getDataIesire() {
        return dataIesire;
    }

    public void setDataIesire(String dataIesire) {
        this.dataIesire = dataIesire;
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

    public int getIdIesiriCTS() {
        return idIesiriCTS;
    }

    public void setIdIesiriCTS(int idIesiriCTS) {
        this.idIesiriCTS = idIesiriCTS;
    }
}
