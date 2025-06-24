package net.zine.supmtiproject.Model;

import java.util.Date;

public class RendezVous {
    Date date;
    String Heure;
    String Maladie;
    String medcine;

    public RendezVous(Date date, String heure, String maladie, String medcine) {
        this.date = date;
        Heure = heure;
        Maladie = maladie;
        this.medcine = medcine;
    }
    public Date getDate() { return date; }
    public String getHeure() { return Heure; }
    public String getMaladie() { return Maladie; }
    public String getMedcine() { return medcine; }
}
