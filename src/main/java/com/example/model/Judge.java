package com.example.model;

/**
 * Rappresenta un giudice nel sistema di gestione dell'hackathon.
 * <p>
 * Questa classe estende {@link User}, ereditandone le proprietà di base,
 * e aggiunge informazioni specifiche per il ruolo di giudice, come l'hackathon
 * a cui è assegnato per la valutazione.
 * @see com.example.model.User
 * @see com.example.model.Hackathon
 * @see com.example.dao.JudgeDAO
 */
public class Judge extends User {

    /**
     * L'ID dell'hackathon a cui questo giudice è assegnato per la valutazione.
     */
    private int hackathonId;

    /**
     * Costruisce un nuovo oggetto Giudice partendo da un oggetto Utente esistente.
     * Questo costruttore è tipicamente utilizzato quando si promuove un partecipante
     * o un utente generico al ruolo di giudice.
     *
     * @param user L'oggetto Utente esistente che contiene le informazioni di base.
     * @param hackathonId L'ID dell'hackathon a cui questo giudice viene assegnato.
     */
    public Judge(User user, int hackathonId) {
        super(user.email, user.username, user.password, user.first_name, user.last_name);
        this.hackathonId = hackathonId;
    }

    /**
     * Costruisce un nuovo oggetto Giudice con le informazioni minime.
     * Questo costruttore è utile per creare un'istanza di Giudice quando
     * sono noti solo l'email e l'hackathon a cui è associato.
     *
     * @param email L'email dell'utente che ricopre il ruolo di giudice.
     * @param hackathonId L'ID dell'hackathon a cui questo giudice è assegnato.
     */
    public Judge(String email, int hackathonId) {
        super(email, "", "", "", "");
        this.hackathonId = hackathonId;
    }

    /**
     * Restituisce l'ID dell'hackathon a cui questo giudice è assegnato.
     *
     * @return L'ID dell'hackathon.
     */
    public int getHackathonId() {
        return hackathonId;
    }

    /**
     * Imposta l'ID dell'hackathon a cui questo giudice è assegnato.
     *
     * @param hackathonId Il nuovo ID dell'hackathon.
     */
    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }
}