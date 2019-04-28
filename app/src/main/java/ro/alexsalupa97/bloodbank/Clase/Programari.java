package ro.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;

public class Programari {

    @SerializedName("dataProgramare")
    private String dataProgramare;

    @SerializedName("idcts")
    private CTS cts;

    @SerializedName("iddonator")
    private Donatori donator;

    @SerializedName("idprogramare")
    private int idProgramare;

    public Programari(String dataProgramare, CTS cts, Donatori donator, int idProgramare) {
        this.dataProgramare = dataProgramare;
        this.cts = cts;
        this.donator = donator;
        this.idProgramare = idProgramare;
    }

    public Programari() {
    }

    public String getDataProgramare() {
        return dataProgramare;
    }

    public void setDataProgramare(String dataProgramare) {
        this.dataProgramare = dataProgramare;
    }

    public CTS getCts() {
        return cts;
    }

    public void setCts(CTS cts) {
        this.cts = cts;
    }

    public Donatori getDonator() {
        return donator;
    }

    public void setDonator(Donatori donator) {
        this.donator = donator;
    }

    public int getIdProgramare() {
        return idProgramare;
    }

    public void setIdProgramare(int idProgramare) {
        this.idProgramare = idProgramare;
    }
}
