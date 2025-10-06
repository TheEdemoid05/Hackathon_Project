package com.example.model;

/**
 * Classe che rappresenta un partecipante a un hackathon.
 *
 * <p>Questa classe estende {@link User} e aggiunge le informazioni specifiche
 * per un utente che partecipa a un hackathon. Un partecipante può essere
 * associato a uno specifico hackathon e opzionalmente a un team.</p>
 *
 * <p><strong>Caratteristiche principali:</strong></p>
 * <ul>
 *   <li>Eredita tutte le proprietà base di un utente</li>
 *   <li>Associazione con un hackathon specifico</li>
 *   <li>Possibile appartenenza a un team</li>
 *   <li>Supporto per partecipanti senza team (individuale)</li>
 * </ul>
 *
 * <p><strong>Stati del partecipante:</strong></p>
 * <ul>
 *   <li><strong>Registrato:</strong> Ha un HackathonID ma TeamID è null</li>
 *   <li><strong>In team:</strong> Ha sia HackathonID che TeamID validi</li>
 * </ul>
 *
 * @see com.example.model.User
 * @see com.example.model.Hackathon
 * @see com.example.model.Team
 */
public class Participant extends User {

    /**
     * ID dell'hackathon a cui partecipa questo utente.
     * Deve essere sempre valorizzato per un partecipante valido.
     */
    private final Integer HackathonID;

    /**
     * ID del team a cui appartiene il partecipante.
     * Può essere null se il partecipante non ha ancora un team.
     */
    private final Integer TeamID;

    /**
     * Costruttore completo per creare un partecipante con tutti i dati utente.
     *
     * @param email       Indirizzo email del partecipante
     * @param username    Nome utente del partecipante
     * @param password    Password del partecipante
     * @param first_name  Nome proprio del partecipante
     * @param last_name   Cognome del partecipante
     * @param HackathonID ID dell'hackathon a cui partecipa
     * @param TeamID      ID del team (può essere null se non ha ancora un team)
     */
    public Participant(String email, String username, String password, String first_name, String last_name,
                       Integer HackathonID, Integer TeamID) {
        super(email, username, password, first_name, last_name);
        this.HackathonID = HackathonID;
        this.TeamID = TeamID;
    }

    /**
     * Costruttore per creare un partecipante a partire da un utente esistente.
     * Utile per convertire un utente generico in partecipante.
     *
     * @param user        L'utente da convertire in partecipante
     * @param HackathonID ID dell'hackathon a cui partecipa
     * @param TeamID      ID del team (può essere null se non ha ancora un team)
     */
    public Participant(User user,
                       Integer HackathonID, Integer TeamID) {
        super(user.email, user.username, user.password, user.first_name, user.last_name);
        this.HackathonID = HackathonID;
        this.TeamID = TeamID;
    }

    /**
     * Costruttore minimo per creare un partecipante con solo email e IDs.
     * Utilizzato principalmente per operazioni di ricerca o caricamento parziale.
     *
     * @param email       Indirizzo email del partecipante
     * @param hackathonID ID dell'hackathon a cui partecipa
     * @param teamID      ID del team (può essere null se non ha ancora un team)
     */
    public Participant(String email, Integer hackathonID, Integer teamID) {
        super();
        this.email = email;
        this.HackathonID = hackathonID;
        this.TeamID = teamID;
    }

    /**
     * Restituisce l'ID dell'hackathon a cui partecipa l'utente.
     *
     * @return L'ID dell'hackathon
     */
    public Integer getHackathonID() {
        return HackathonID;
    }


    /**
     * Restituisce l'ID del team a cui appartiene il partecipante.
     *
     * @return L'ID del team o null se non appartiene a nessun team
     */
    public Integer getTeamID() {
        return TeamID;
    }


}