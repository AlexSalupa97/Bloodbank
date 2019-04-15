package ro.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;



public class IstoricReceiver {

    @SerializedName("cantitateprimitaml")
    private int cantitatePrimitaML;

    @SerializedName("dataprimire")
    private String dataPrimire;

    @SerializedName("idistoricreceiver")
    private int idIstoricReceiver;

    @SerializedName("idreceiver")
    private Receiveri receiver;

    public IstoricReceiver(int cantitatePrimitaML, String dataPrimire, int idIstoricReceiver, Receiveri receiver) {
        this.cantitatePrimitaML = cantitatePrimitaML;
        this.dataPrimire = dataPrimire;
        this.idIstoricReceiver = idIstoricReceiver;
        this.receiver = receiver;
    }

    public IstoricReceiver() {
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

    public int getIdIstoricReceiver() {
        return idIstoricReceiver;
    }

    public void setIdIstoricReceiver(int idIstoricReceiver) {
        this.idIstoricReceiver = idIstoricReceiver;
    }

    public Receiveri getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiveri receiver) {
        this.receiver = receiver;
    }
}
