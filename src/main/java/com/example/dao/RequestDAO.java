package com.example.dao;

import com.example.model.Request;

import java.util.List;

/**
 * Interfaccia DAO per la gestione delle Richieste.
 * <p>
 * Definisce il contratto per tutte le operazioni di accesso ai dati relative
 * alle richieste di partecipazione inviate tra i partecipanti per unirsi a un team.
 * @see com.example.model.Request
 * @see com.example.daoimp.RequestDaoImpl
 */
public interface RequestDAO {

    /**
     * Salva una nuova richiesta di partecipazione nel database.
     *
     * @param request L'oggetto {@link Request} da salvare.
     * @return {@code true} se la richiesta è stata salvata con successo, {@code false} altrimenti.
     */
    boolean saveRequest(Request request);

    /**
     * Recupera tutte le richieste di partecipazione ricevute da un utente specifico.
     *
     * @param receiverEmail L'email dell'utente che ha ricevuto le richieste.
     * @return Una lista di {@link Request} indirizzate all'utente specificato.
     * La lista sarà vuota se l'utente non ha ricevuto richieste.
     */
    List<Request> findRequestsReceivedBy(String receiverEmail);

    /**
     * Aggiorna una richiesta esistente nel database.
     * <p>
     * Questo metodo è utilizzato principalmente per modificare lo stato di una richiesta
     * (es. da 'PENDING' a 'ACCEPTED' o 'REJECTED').
     *
     * @param request L'oggetto {@link Request} con i dati aggiornati.
     * @return {@code true} se l'aggiornamento è andato a buon fine, {@code false} altrimenti.
     */
    boolean updateRequest(Request request);

    /**
     * Recupera tutte le richieste di partecipazione inviate da un utente specifico.
     *
     * @param senderEmail L'email dell'utente che ha inviato le richieste.
     * @return Una lista di {@link Request} inviate dall'utente specificato.
     * La lista sarà vuota se l'utente non ha inviato richieste.
     */
    List<Request> findRequestsSentBy(String senderEmail);

}