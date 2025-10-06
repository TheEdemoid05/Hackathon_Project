package com.example.model;

/**
 * Rappresenta un organizzatore nel sistema di gestione dell'hackathon.
 * <p>
 * Questa classe estende {@link User} e rappresenta un utente con privilegi speciali
 * per creare, gestire e supervisionare gli eventi hackathon.
 * @see com.example.model.User
 * @see com.example.model.Hackathon
 */
public class Organizer extends User {

    /**
     * L'ID dell'hackathon che questo organizzatore sta attualmente gestendo.
     * Nota: Attualmente è una String, il che potrebbe essere incoerente con altri ID basati su interi.
     */
    private String HackathonID;

    /**
     * Costruisce un nuovo oggetto Organizer con tutti i dettagli richiesti per l'utente e l'organizzatore.
     *
     * @param email L'indirizzo email dell'organizzatore.
     * @param username Lo username univoco dell'organizzatore.
     * @param password La password dell'organizzatore.
     * @param first_name Il nome dell'organizzatore.
     * @param last_name Il cognome dell'organizzatore.
     * @param HackathonID L'ID dell'hackathon a cui questo organizzatore è associato.
     */
    public Organizer(String email, String username, String password, String first_name, String last_name,
                     String HackathonID) {
        super(email, username, password, first_name, last_name);
        this.HackathonID = HackathonID;
    }



}