package com.example.model;

import java.time.LocalDateTime;

/**
 * Classe che rappresenta un hackathon nel sistema di gestione.
 *
 * <p>Un hackathon è un evento di programmazione collaborativa dove team di sviluppatori
 * lavorano insieme per creare soluzioni innovative in un tempo limitato. Questa classe
 * contiene tutte le informazioni necessarie per definire e gestire un hackathon.</p>
 *
 * <p>La classe include informazioni sui limiti di partecipazione, date di registrazione,
 * date dell'evento e descrizione del problema da risolvere.</p>
 *
 * <p><strong>Caratteristiche principali:</strong></p>
 * <ul>
 *   <li>Gestione completa delle date (inizio, fine, registrazione)</li>
 *   <li>Controllo dei limiti di partecipazione</li>
 *   <li>Associazione con l'organizzatore</li>
 *   <li>Descrizione del problema da risolvere</li>
 * </ul>
 *
 * @see com.example.model.Organizer
 * @see com.example.model.Team
 * @see com.example.model.Participant
 */
public class Hackathon {

    /**
     * Identificatore univoco dell'hackathon nel database.
     */
    private int id;

    /**
     * Titolo dell'hackathon.
     */
    private String title;

    /**
     * Luogo fisico o virtuale dove si svolge l'hackathon.
     */
    private final String location;

    /**
     * Data e ora di inizio dell'hackathon.
     */
    private final LocalDateTime startDate;

    /**
     * Data e ora di fine dell'hackathon.
     */
    private final LocalDateTime endDate;

    /**
     * Numero massimo di partecipanti ammessi all'hackathon.
     */
    private final int maxParticipants;

    /**
     * Dimensione massima consentita per ogni team.
     */
    private final int maxTeamSize;

    /**
     * Data e ora di inizio delle registrazioni.
     */
    private LocalDateTime registrationStart;

    /**
     * Data e ora di fine delle registrazioni.
     * Viene calcolata automaticamente come 3 giorni dopo l'inizio registrazioni.
     */
    private LocalDateTime registrationEnd;

    /**
     * Descrizione dettagliata del problema da risolvere durante l'hackathon.
     */
    private final String problemDescription;

    /**
     * Email dell'organizzatore che ha creato questo hackathon.
     */
    private final String organizerUserEmail;


    /**
     * Costruttore per creare un nuovo hackathon senza ID specificato.
     * L'ID verrà assegnato automaticamente dal database.
     *
     * @param title              Titolo dell'hackathon
     * @param location           Luogo dove si svolge l'hackathon
     * @param startDate          Data e ora di inizio dell'hackathon
     * @param endDate            Data e ora di fine dell'hackathon
     * @param maxParticipants    Numero massimo di partecipanti
     * @param maxTeamSize        Dimensione massima dei team
     * @param registrationStart  Data e ora di inizio registrazioni
     * @param problemDescription Descrizione del problema da risolvere
     * @param organizerUserEmail Email dell'organizzatore
     */
    public Hackathon(String title, String location, LocalDateTime startDate, LocalDateTime endDate,
                     int maxParticipants, int maxTeamSize, LocalDateTime registrationStart,
                     String problemDescription, String organizerUserEmail) {
        this(0, title, location, startDate, endDate, maxParticipants, maxTeamSize, registrationStart,
                problemDescription, organizerUserEmail);

    }

    /**
     * Costruttore completo per creare un hackathon con ID specificato.
     * Utilizzato principalmente per caricare hackathon esistenti dal database.
     *
     * <p>La data di fine registrazione viene calcolata automaticamente
     * come 3 giorni dopo l'inizio delle registrazioni.</p>
     *
     * @param id                 Identificatore univoco dell'hackathon
     * @param title              Titolo dell'hackathon
     * @param location           Luogo dove si svolge l'hackathon
     * @param startDate          Data e ora di inizio dell'hackathon
     * @param endDate            Data e ora di fine dell'hackathon
     * @param maxParticipants    Numero massimo di partecipanti
     * @param maxTeamSize        Dimensione massima dei team
     * @param registrationStart  Data e ora di inizio registrazioni
     * @param problemDescription Descrizione del problema da risolvere
     * @param organizerUserEmail Email dell'organizzatore
     */
    public Hackathon(int id, String title, String location, LocalDateTime startDate, LocalDateTime endDate,
                     int maxParticipants, int maxTeamSize, LocalDateTime registrationStart,
                     String problemDescription, String organizerUserEmail) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxParticipants = maxParticipants;
        this.maxTeamSize = maxTeamSize;
        this.registrationStart = registrationStart;
        this.problemDescription = problemDescription;
        this.registrationEnd = registrationStart != null ? registrationStart.plusDays(3) : null;
        this.organizerUserEmail = organizerUserEmail;
    }

    /**
     * Restituisce l'identificatore univoco dell'hackathon.
     *
     * @return L'ID dell'hackathon
     */
    public Integer getId() {
        return id;
    }

    /**
     * Imposta l'identificatore univoco dell'hackathon.
     *
     * @param id Il nuovo ID dell'hackathon
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce il titolo dell'hackathon.
     *
     * @return Il titolo dell'hackathon
     */
    public String getTitle() {
        return title;
    }

    /**
     * Imposta il titolo dell'hackathon.
     *
     * @param title Il nuovo titolo dell'hackathon
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Restituisce il luogo dove si svolge l'hackathon.
     *
     * @return La location dell'hackathon
     */
    public String getLocation() {
        return location;
    }


    public LocalDateTime getStartDate() {
        return startDate;
    }


    public LocalDateTime getEndDate() {
        return endDate;
    }


    public int getMaxParticipants() {
        return maxParticipants;
    }


    public int getMaxTeamSize() {
        return maxTeamSize;
    }


    public LocalDateTime getRegistrationStart() {
        return registrationStart;
    }

    public void setRegistrationStart(LocalDateTime registrationStart) {
        this.registrationStart = registrationStart;
    }

    public LocalDateTime getRegistrationEnd() {
        return registrationEnd;
    }

    public void setRegistrationEnd(LocalDateTime registrationEnd) {
        this.registrationEnd = registrationEnd;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public String getOrganizerUserEmail() {
        return organizerUserEmail;
    }


    @Override
    public String toString() {
        return "Hackathon{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", maxParticipants=" + maxParticipants +
                ", maxTeamSize=" + maxTeamSize +
                ", registrationStart=" + registrationStart +
                ", registrationEnd=" + registrationEnd +
                ", problemDescription='" + problemDescription + '\'' +
                ", organizerUserEmail='" + organizerUserEmail + '\'' +
                '}';
    }
}
