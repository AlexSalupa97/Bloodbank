package ro.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;

public class Donatori {

    @SerializedName("emaildonator")
    private String emailDonator;

    @SerializedName("iddonator")
    private int idDonator;

    @SerializedName("idgrupasanguina")
    private GrupeSanguine grupaSanguina;

    @SerializedName("idoras")
    private Orase orasDonator;

    @SerializedName("numedonator")
    private String numeDonator;

    @SerializedName("paroladonator")
    private String parolaDonator;

    @SerializedName("telefondonator")
    private String telefonDonator;

//    public Donatori(String emailDonator, int idDonator, GrupeSanguine grupaSanguina, Orase orasDonator, String numeDonator, String telefonDonator) {
//        this.emailDonator = emailDonator;
//        this.idDonator = idDonator;
//        this.grupaSanguina = grupaSanguina;
//        this.orasDonator = orasDonator;
//        this.numeDonator = numeDonator;
//        this.telefonDonator = telefonDonator;
//    }


    public Donatori(String emailDonator, int idDonator, GrupeSanguine grupaSanguina, Orase orasDonator, String numeDonator, String parolaDonator, String telefonDonator) {
        this.emailDonator = emailDonator;
        this.idDonator = idDonator;
        this.grupaSanguina = grupaSanguina;
        this.orasDonator = orasDonator;
        this.numeDonator = numeDonator;
        this.parolaDonator = parolaDonator;
        this.telefonDonator = telefonDonator;
    }

    public Donatori() {
    }

    public String getParolaDonator() {
        return parolaDonator;
    }

    public void setParolaDonator(String parolaDonator) {
        this.parolaDonator = parolaDonator;
    }

    public String getEmailDonator() {
        return emailDonator;
    }

    public void setEmailDonator(String emailDonator) {
        this.emailDonator = emailDonator;
    }

    public int getIdDonator() {
        return idDonator;
    }

    public void setIdDonator(int idDonator) {
        this.idDonator = idDonator;
    }

    public GrupeSanguine getGrupaSanguina() {
        return grupaSanguina;
    }

    public void setGrupaSanguina(GrupeSanguine grupaSanguina) {
        this.grupaSanguina = grupaSanguina;
    }

    public Orase getOrasDonator() {
        return orasDonator;
    }

    public void setOrasDonator(Orase orasDonator) {
        this.orasDonator = orasDonator;
    }

    public String getNumeDonator() {
        return numeDonator;
    }

    public void setNumeDonator(String numeDonator) {
        this.numeDonator = numeDonator;
    }

    public String getTelefonDonator() {
        return telefonDonator;
    }

    public void setTelefonDonator(String telefonDonator) {
        this.telefonDonator = telefonDonator;
    }
}
