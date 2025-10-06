package com.example.model;

/**
 * Rappresenta una richiesta inviata da un partecipante a un altro per unirsi a un team.
 * <p>
 * Questa classe incapsula tutte le informazioni relative a un invito di team,
 * includendo il mittente, il destinatario, il team in questione e lo stato
 * della richiesta (es. PENDING, ACCEPTED, REJECTED).
 *
 * @see com.example.model.Participant
 * @see com.example.model.Team
 * @see com.example.dao.RequestDAO
 */
public class Request {
    /**
     * L'identificatore univoco per la richiesta.
     */
    int id;
    /**
     * Il messaggio personalizzato inviato con l'invito.
     */
    String message;
    /**
     * Lo stato attuale della richiesta (es. "PENDING", "ACCEPTED", "REJECTED").
     */
    String status;
    /**
     * L'ID del team per il quale viene inviato l'invito.
     */
    int teamId;
    /**
     * L'email del partecipante che ha inviato la richiesta.
     */
    String sender_participant_email;
    /**
     * L'email del partecipante che ha ricevuto la richiesta.
     */
    String receiver_participant_email;

    /**
     * Costruisce una nuova Richiesta, tipicamente usata prima di salvarla nel database.
     * L'ID è inizializzato a 0 e sarà assegnato dal database.
     *
     * @param message                    Il contenuto del messaggio della richiesta.
     * @param status                     Lo stato iniziale della richiesta (es. "PENDING").
     * @param teamId                     L'ID del team associato alla richiesta.
     * @param sender_participant_email   L'email del mittente.
     * @param receiver_participant_email L'email del destinatario.
     */
    public Request(String message, String status, int teamId, String sender_participant_email, String receiver_participant_email) {
        this(0, message, status, teamId, sender_participant_email, receiver_participant_email);
    }


    /**
     * Costruisce una Richiesta con tutti i campi, tipicamente usato per ricostruire
     * un oggetto dal database.
     *
     * @param id                         L'ID univoco della richiesta.
     * @param message                    Il contenuto del messaggio della richiesta.
     * @param status                     Lo stato attuale della richiesta.
     * @param teamId                     L'ID del team associato alla richiesta.
     * @param sender_participant_email   L'email del mittente.
     * @param receiver_participant_email L'email del destinatario.
     */
    public Request(int id, String message, String status, int teamId, String sender_participant_email,
                   String receiver_participant_email) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.teamId = teamId;
        this.sender_participant_email = sender_participant_email;
        this.receiver_participant_email = receiver_participant_email;
    }

    /**
     * Restituisce l'identificatore univoco della richiesta.
     *
     * @return L'ID della richiesta.
     */
    public int getId() {
        return id;
    }

    /**
     * Restituisce il contenuto del messaggio della richiesta.
     *
     * @return La stringa del messaggio.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Restituisce lo stato attuale della richiesta.
     *
     * @return La stringa di stato (es. "PENDING").
     */
    public String getStatus() {
        return status;
    }

    /**
     * Imposta lo stato della richiesta.
     *
     * @param status La nuova stringa di stato.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Restituisce l'ID del team associato a questa richiesta.
     *
     * @return L'ID del team.
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * Imposta l'ID del team per questa richiesta.
     *
     * @param teamId Il nuovo ID del team.
     */
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    /**
     * Restituisce l'email del partecipante che ha inviato la richiesta.
     *
     * @return L'email del mittente.
     */
    public String getSender_participant_email() {
        return sender_participant_email;
    }


    /**
     * Restituisce l'email del partecipante che ha ricevuto la richiesta.
     *
     * @return L'email del destinatario.
     */
    public String getReceiver_participant_email() {
        return receiver_participant_email;
    }

}