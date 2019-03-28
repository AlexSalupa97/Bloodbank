package ro.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;

public class IstoricDonatii {

    @SerializedName("cantitatedonatieml")
    private int cantitateDonataML;

    @SerializedName("datadonatie")
    private String dataDonatie;

    @SerializedName("iddonator")
    private Donatori donator;

    @SerializedName("idistoricdonatie")
    private int idIstoricDonatie;

    public IstoricDonatii(int cantitateDonataML, String dataDonatie, Donatori donator, int idIstoricDonatie) {
        this.cantitateDonataML = cantitateDonataML;
        this.dataDonatie = dataDonatie;
        this.donator = donator;
        this.idIstoricDonatie = idIstoricDonatie;
    }

    public IstoricDonatii() {
    }

    public int getCantitateDonataML() {
        return cantitateDonataML;
    }

    public void setCantitateDonataML(int cantitateDonataML) {
        this.cantitateDonataML = cantitateDonataML;
    }

    public String getDataDonatie() {
        return dataDonatie;
    }

    public void setDataDonatie(String dataDonatie) {
        this.dataDonatie = dataDonatie;
    }

    public Donatori getDonator() {
        return donator;
    }

    public void setDonator(Donatori donator) {
        this.donator = donator;
    }

    public int getIdIstoricDonatie() {
        return idIstoricDonatie;
    }

    public void setIdIstoricDonatie(int idIstoricDonatie) {
        this.idIstoricDonatie = idIstoricDonatie;
    }
}
