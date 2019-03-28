package ro.alexsalupa97.bloodbank.Clase;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Intrebari implements Parcelable
{
    @SerializedName("idintrebare")
    private int idIntrebare;

    @SerializedName("textintrebare")
    private String textIntrebare;
    
    @SerializedName("raspunsintrebare")
    private String raspunsIntrebare;

    public Intrebari(int idIntrebare, String textIntrebare, String raspunsIntrebare) {
        this.idIntrebare = idIntrebare;
        this.textIntrebare = textIntrebare;
        this.raspunsIntrebare = raspunsIntrebare;
    }

    public Intrebari() {
    }

    public int getIdIntrebare() {
        return idIntrebare;
    }

    public void setIdIntrebare(int idIntrebare) {
        this.idIntrebare = idIntrebare;
    }

    public String getTextIntrebare() {
        return textIntrebare;
    }

    public void setTextIntrebare(String textIntrebare) {
        this.textIntrebare = textIntrebare;
    }

    public String getRaspunsIntrebare() {
        return raspunsIntrebare;
    }

    public void setRaspunsIntrebare(String raspunsIntrebare) {
        this.raspunsIntrebare = raspunsIntrebare;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(idIntrebare);
        dest.writeString(textIntrebare);
        dest.writeString(raspunsIntrebare);
    }

    public Intrebari(Parcel in) {
        idIntrebare = in.readInt();
        textIntrebare = in.readString();
        raspunsIntrebare = in.readString();
    }

    public static final Creator<Intrebari> CREATOR = new Creator<Intrebari>() {
        @Override
        public Intrebari createFromParcel(Parcel in) {
            return new Intrebari(in);
        }

        @Override
        public Intrebari[] newArray(int size) {
            return new Intrebari[size];
        }
    };
}
