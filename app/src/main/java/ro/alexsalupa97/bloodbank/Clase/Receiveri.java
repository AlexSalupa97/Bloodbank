package ro.alexsalupa97.bloodbank.Clase;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Receiveri implements Parcelable {

    @SerializedName("emailreceiver")
    private String emailReceiver;

    @SerializedName("idcts")
    private CTS cts;

    @SerializedName("idgrupasanguina")
    private GrupeSanguine grupaSanguina;

    @SerializedName("idreceiver")
    private int idReceiver;

    @SerializedName("numereceiver")
    private String numeReceiver;

    @SerializedName("parolareceiver")
    private String parolaReceiver;

    @SerializedName("starereceiver")
    private int stareReceiver;

    @SerializedName("telefonreceiver")
    private String telefonReceiver;

//    public Receiveri(String emailReceiver, CTS cts, GrupeSanguine grupaSanguina, int idReceiver, String numeReceiver, String telefonReceiver) {
//        this.emailReceiver = emailReceiver;
//        this.cts = cts;
//        this.grupaSanguina = grupaSanguina;
//        this.idReceiver = idReceiver;
//        this.numeReceiver = numeReceiver;
//        this.telefonReceiver = telefonReceiver;
//    }

    public Receiveri(String emailReceiver, CTS cts, GrupeSanguine grupaSanguina, int idReceiver, String numeReceiver, String parolaReceiver, int stareReceiver, String telefonReceiver) {
        this.emailReceiver = emailReceiver;
        this.cts = cts;
        this.grupaSanguina = grupaSanguina;
        this.idReceiver = idReceiver;
        this.numeReceiver = numeReceiver;
        this.parolaReceiver = parolaReceiver;
        this.stareReceiver = stareReceiver;
        this.telefonReceiver = telefonReceiver;
    }

    public Receiveri() {
    }

    private Receiveri(Parcel in) {
        emailReceiver = in.readString();
        cts = in.readParcelable(CTS.class.getClassLoader());
        idReceiver = in.readInt();
        numeReceiver = in.readString();
        parolaReceiver=in.readString();
        stareReceiver=in.readInt();
        telefonReceiver = in.readString();
    }

    public String getParolaReceiver() {
        return parolaReceiver;
    }

    public void setParolaReceiver(String parolaReceiver) {
        this.parolaReceiver = parolaReceiver;
    }

    public int getStareReceiver() {
        return stareReceiver;
    }

    public void setStareReceiver(int stareReceiver) {
        this.stareReceiver = stareReceiver;
    }

    public String getEmailReceiver() {
        return emailReceiver;
    }

    public void setEmailReceiver(String emailReceiver) {
        this.emailReceiver = emailReceiver;
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

    public int getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(int idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getNumeReceiver() {
        return numeReceiver;
    }

    public void setNumeReceiver(String numeReceiver) {
        this.numeReceiver = numeReceiver;
    }

    public String getTelefonReceiver() {
        return telefonReceiver;
    }

    public void setTelefonReceiver(String telefonReceiver) {
        this.telefonReceiver = telefonReceiver;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(emailReceiver);
        dest.writeParcelable(cts, 1);
        dest.writeInt(idReceiver);
        dest.writeString(numeReceiver);
        dest.writeString(parolaReceiver);
        dest.writeInt(stareReceiver);
        dest.writeString(telefonReceiver);
    }


    public static final Creator<Receiveri> CREATOR = new Creator<Receiveri>() {
        @Override
        public Receiveri createFromParcel(Parcel in) {
            return new Receiveri(in);
        }

        @Override
        public Receiveri[] newArray(int size) {
            return new Receiveri[size];
        }
    };
}
