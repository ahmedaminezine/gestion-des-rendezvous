package net.zine.supmtiproject.Model;

import java.time.LocalDate;

public class Appointment {
    private int id;
    private final String patientName;
    private final LocalDate date;
    private final String hour;
    private String reason;
    private String CompteRendu;

    public Appointment(int id, String patientName, LocalDate date, String hour, String reason, String CompteRendu) {
        this.id = id;
        this.patientName = patientName;
        this.date = date;
        this.hour = hour;
        this.reason = reason;
        this.CompteRendu = CompteRendu;
    }
    public int getId() {return id;}
    public String getPatientName() { return patientName; }
    public LocalDate getDate() { return date; }
    public String getHour() { return hour; }
    public String getReason() { return reason; }

    public String getCompteRendu() {return CompteRendu;}
}

