package ro.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;

public class LimiteCTS {

    @SerializedName("idcts")
    private CTS cts;

    @SerializedName("idoras")
    private Orase oras;

    @SerializedName("idgrupasanguina")
    private GrupeSanguine grupaSanguina;

    @SerializedName("idlimitacts")
    private int idLimitaCTS;

    @SerializedName("limitaml")
    private int limitaML;

    public LimiteCTS(CTS cts, Orase oras, GrupeSanguine grupaSanguina, int idLimitaCTS, int limitaML) {
        this.cts = cts;
        this.oras = oras;
        this.grupaSanguina = grupaSanguina;
        this.idLimitaCTS = idLimitaCTS;
        this.limitaML = limitaML;
    }

    public LimiteCTS() {
    }

    public CTS getCts() {
        return cts;
    }

    public void setCts(CTS cts) {
        this.cts = cts;
    }

    public Orase getOras() {
        return oras;
    }

    public void setOras(Orase oras) {
        this.oras = oras;
    }

    public GrupeSanguine getGrupaSanguina() {
        return grupaSanguina;
    }

    public void setGrupaSanguina(GrupeSanguine grupaSanguina) {
        this.grupaSanguina = grupaSanguina;
    }

    public int getIdLimitaCTS() {
        return idLimitaCTS;
    }

    public void setIdLimitaCTS(int idLimitaCTS) {
        this.idLimitaCTS = idLimitaCTS;
    }

    public int getLimitaML() {
        return limitaML;
    }

    public void setLimitaML(int limitaML) {
        this.limitaML = limitaML;
    }
}
