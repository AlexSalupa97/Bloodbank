package com.example.alexsalupa97.bloodbank.RecyclerViewOrizontal;

public class ItemModelIstoric {

    private String dataDonare;
    private String cantitateDonata;

    public ItemModelIstoric(String dataDonare, String cantitateDonata) {
        this.dataDonare = dataDonare;
        this.cantitateDonata = cantitateDonata;
    }

    public ItemModelIstoric() {
    }

    public String getDataDonare() {
        return dataDonare;
    }

    public void setDataDonare(String dataDonare) {
        this.dataDonare = dataDonare;
    }

    public String getCantitateDonata() {
        return cantitateDonata;
    }

    public void setCantitateDonata(String cantitateDonata) {
        this.cantitateDonata = cantitateDonata;
    }
}
