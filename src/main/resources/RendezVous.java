package models;

import java.time.LocalDateTime;

public class RendezVous {
    private int id;
    private int utilisateurId;
    private LocalDateTime dateHeure;
    private String description;
    private String statut;

    public RendezVous(int id, int utilisateurId, LocalDateTime dateHeure, String description, String statut) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.dateHeure = dateHeure;
        this.description = description;
        this.statut = statut;
    }

    public int getId() { return id; }
    public int getUtilisateurId() { return utilisateurId; }
    public LocalDateTime getDateHeure() { return dateHeure; }
    public String getDescription() { return description; }
    public String getStatut() { return statut; }

    public void setId(int id) { this.id = id; }
    public void setUtilisateurId(int utilisateurId) { this.utilisateurId = utilisateurId; }
    public void setDateHeure(LocalDateTime dateHeure) { this.dateHeure = dateHeure; }
    public void setDescription(String description) { this.description = description; }
    public void setStatut(String statut) { this.statut = statut; }
}
