package ro.alexsalupa97.bloodbank.Clase;

public class CantitatiCTS {
    private CTS cts;
    private GrupeSanguine grupaSanguina;
    private int cantitateDisponibilaML;
    private int cantitateLimitaML;

    public CantitatiCTS(CTS cts, GrupeSanguine grupaSanguina, int cantitateDisponibilaML, int cantitateLimitaML) {
        this.cts = cts;
        this.grupaSanguina = grupaSanguina;
        this.cantitateDisponibilaML = cantitateDisponibilaML;
        this.cantitateLimitaML = cantitateLimitaML;
    }

    public CantitatiCTS() {
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

    public int getCantitateDisponibilaML() {
        return cantitateDisponibilaML;
    }

    public void setCantitateDisponibilaML(int cantitateDisponibilaML) {
        this.cantitateDisponibilaML = cantitateDisponibilaML;
    }

    public int getCantitateLimitaML() {
        return cantitateLimitaML;
    }

    public void setCantitateLimitaML(int cantitateLimitaML) {
        this.cantitateLimitaML = cantitateLimitaML;
    }
}
