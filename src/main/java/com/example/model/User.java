package com.example.model;

/**
 * Classe che rappresenta un utente generico del sistema di gestione hackathon.
 *
 * <p>Questa classe serve come classe base per tutti gli utenti del sistema e contiene
 * le informazioni comuni a tutti i tipi di utente. Gli utenti possono avere diversi
 * ruoli definiti dall'enumerazione {@link Role}.</p>
 *
 * <p>La classe supporta diversi costruttori per facilitare la creazione di istanze
 * in contesti differenti, dalla registrazione all'autenticazione.</p>
 *
 * @see Role
 * @see com.example.model.Participant
 * @see com.example.model.Organizer
 * @see com.example.model.Judge
 */
public class User {

    /**
     * Indirizzo email dell'utente, utilizzato come identificatore unico.
     */
    protected String email;

    /**
     * Nome utente scelto dall'utente per il login.
     */
    protected String username;

    /**
     * Password dell'utente
     */
    protected String password;

    /**
     * Nome proprio dell'utente.
     */
    protected String first_name;

    /**
     * Cognome dell'utente.
     */
    protected String last_name;

    /**
     * Ruolo dell'utente nel sistema.
     *
     * @see Role
     */
    protected Role role;

    /**
     * Costruttore di default per creare un utente vuoto.
     * Utilizzato principalmente per la deserializzazione o l'inizializzazione posticipata.
     */
    public User() {
    }

    /**
     * Costruttore per creare un utente con solo l'email.
     * Utilizzato principalmente per operazioni di ricerca o autenticazione.
     *
     * @param email Indirizzo email dell'utente
     */
    public User(String email) {
        this.email = email;
    }

    /**
     * Costruttore completo per creare un nuovo utente con ruolo specifico.
     *
     * @param email      Indirizzo email dell'utente (identificatore unico)
     * @param username   Nome utente per il login
     * @param password   Password dell'utente
     * @param first_name Nome proprio dell'utente
     * @param last_name  Cognome dell'utente
     * @param role       Ruolo specifico dell'utente nel sistema
     */
    public User(String email, String username, String password, String first_name, String last_name, Role role) {
        this(email, username, password, first_name, last_name);
        this.role = role;
    }


    /**
     * Costruttore completo per creare un nuovo utente con ruolo di default USER.
     *
     * @param email      Indirizzo email dell'utente (identificatore unico)
     * @param username   Nome utente per il login
     * @param password   Password dell'utente
     * @param first_name Nome proprio dell'utente
     * @param last_name  Cognome dell'utente
     */
    public User(String email, String username, String password, String first_name, String last_name) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = Role.USER;
    }

    /**
     * Restituisce il ruolo dell'utente nel sistema.
     *
     * @return Il ruolo dell'utente
     */
    public Role getRole() {
        return role;
    }

    /**
     * Imposta il ruolo dell'utente nel sistema.
     *
     * @param role Il nuovo ruolo da assegnare all'utente
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Restituisce l'indirizzo email dell'utente.
     *
     * @return L'indirizzo email dell'utente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'indirizzo email dell'utente.
     *
     * @param email Il nuovo indirizzo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce il nome utente per il login.
     *
     * @return Il nome utente
     */
    public String getUsername() {
        return username;
    }


    /**
     * Restituisce la password dell'utente.
     *
     * @return La password dell'utente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dell'utente.
     *
     * @param password La nuova password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce il nome proprio dell'utente.
     *
     * @return Il nome proprio dell'utente
     */
    public String getFirst_name() {
        return first_name;
    }


    /**
     * Restituisce il cognome dell'utente.
     *
     * @return Il cognome dell'utente
     */
    public String getLast_name() {
        return last_name;
    }


}