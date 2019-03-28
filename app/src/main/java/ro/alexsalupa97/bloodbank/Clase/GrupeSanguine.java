package ro.alexsalupa97.bloodbank.Clase;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class GrupeSanguine {


    @SerializedName("idgrupasanguina")
    private String grupaSanguina;

    public GrupeSanguine(String grupaSanguina) {
        this.grupaSanguina = grupaSanguina;
    }

    public GrupeSanguine() {

    }

    public String getGrupaSanguina() {
        return grupaSanguina;
    }

    public void setGrupaSanguina(String grupaSanguina) {
        this.grupaSanguina = grupaSanguina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrupeSanguine that = (GrupeSanguine) o;
        return Objects.equals(grupaSanguina, that.grupaSanguina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grupaSanguina);
    }
}
