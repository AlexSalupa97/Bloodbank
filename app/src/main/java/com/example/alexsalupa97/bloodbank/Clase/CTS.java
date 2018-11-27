package com.example.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;

public class CTS {

    @SerializedName("idcts")
    private int idCTS;

    @SerializedName("adresacts")
    private String adresaCTS;

    @SerializedName("coordonataxcts")
    private double coordonataXCTS;

    @SerializedName("coordonataycts")
    private double coordonataYCTS;

    @SerializedName("emailcts")
    private String emailCTS;

    @SerializedName("numects")
    private String numeCTS;

    @SerializedName("parolacts")
    private String parolaCTS;

    @SerializedName("starects")
    private String stareCTS;

    @SerializedName("telefoncts")
    private String telefonCTS;

    @SerializedName("idoras")
    private Orase oras;


    public CTS(int idCTS, String adresaCTS, double coordonataXCTS, double coordonataYCTS, String emailCTS, String numeCTS, String parolaCTS, String stareCTS, String telefonCTS, Orase oras) {
        this.idCTS = idCTS;
        this.adresaCTS = adresaCTS;
        this.coordonataXCTS = coordonataXCTS;
        this.coordonataYCTS = coordonataYCTS;
        this.emailCTS = emailCTS;
        this.numeCTS = numeCTS;
        this.parolaCTS = parolaCTS;
        this.stareCTS = stareCTS;
        this.telefonCTS = telefonCTS;
        this.oras = oras;
    }

    public CTS() {
    }

    public int getIdCTS() {
        return idCTS;
    }

    public void setIdCTS(int idCTS) {
        this.idCTS = idCTS;
    }

    public String getAdresaCTS() {
        return adresaCTS;
    }

    public void setAdresaCTS(String adresaCTS) {
        this.adresaCTS = adresaCTS;
    }

    public double getCoordonataXCTS() {
        return coordonataXCTS;
    }

    public void setCoordonataXCTS(double coordonataXCTS) {
        this.coordonataXCTS = coordonataXCTS;
    }

    public double getCoordonataYCTS() {
        return coordonataYCTS;
    }

    public void setCoordonataYCTS(double coordonataYCTS) {
        this.coordonataYCTS = coordonataYCTS;
    }

    public String getEmailCTS() {
        return emailCTS;
    }

    public void setEmailCTS(String emailCTS) {
        this.emailCTS = emailCTS;
    }

    public String getNumeCTS() {
        return numeCTS;
    }

    public void setNumeCTS(String numeCTS) {
        this.numeCTS = numeCTS;
    }

    public String getParolaCTS() {
        return parolaCTS;
    }

    public void setParolaCTS(String parolaCTS) {
        this.parolaCTS = parolaCTS;
    }

    public String getStareCTS() {
        return stareCTS;
    }

    public void setStareCTS(String stareCTS) {
        this.stareCTS = stareCTS;
    }

    public String getTelefonCTS() {
        return telefonCTS;
    }

    public void setTelefonCTS(String telefonCTS) {
        this.telefonCTS = telefonCTS;
    }

    public Orase getOras() {
        return oras;
    }

    public void setOras(Orase oras) {
        this.oras = oras;
    }
}
