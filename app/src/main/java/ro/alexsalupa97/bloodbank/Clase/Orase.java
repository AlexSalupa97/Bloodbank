package ro.alexsalupa97.bloodbank.Clase;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Orase implements Parcelable,Comparable {

    @SerializedName("idoras")
    private int idOras;

    @SerializedName("judet")
    private String judet;

    @SerializedName("numeoras")
    private String oras;

    public Orase(int idOras, String judet, String oras) {
        this.idOras = idOras;
        this.judet = judet;
        this.oras = oras;
    }

    public Orase() {
    }

    public int getIdOras() {
        return idOras;
    }

    public void setIdOras(int idOras) {
        this.idOras = idOras;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(idOras);
        dest.writeString(judet);
        dest.writeString(oras);
    }

    public Orase(Parcel in) {
        idOras = in.readInt();
        judet = in.readString();
        oras = in.readString();
    }

    public static final Creator<Orase> CREATOR = new Creator<Orase>() {
        @Override
        public Orase createFromParcel(Parcel in) {
            return new Orase(in);
        }

        @Override
        public Orase[] newArray(int size) {
            return new Orase[size];
        }
    };

    @Override
    public int compareTo(Object o) {
        Orase orase=(Orase)o;
        return this.oras.compareTo(orase.oras);
    }

    @Override
    public boolean equals(Object obj) {
        Orase orase=(Orase)obj;
        return this.oras.compareTo(orase.oras)==0;
    }
}
