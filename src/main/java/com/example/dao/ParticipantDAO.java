package com.example.dao;

import com.example.model.Participant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per la gestione dei Partecipanti.
 * <p>
 * Definisce il contratto per tutte le operazioni di accesso ai dati relative
 * ai partecipanti, come la loro iscrizione a un hackathon, l'assegnazione
 * a un team e la loro rimozione.
 * @see com.example.model.Participant
 * @see com.example.daoimp.ParticipantDaoImpl
 */
public interface ParticipantDAO {

    /**
     * Cerca un partecipante nel database utilizzando il suo indirizzo email.
     *
     * @param email L'indirizzo email del partecipante da cercare.
     * @return Un {@link Optional} che contiene il partecipante se trovato, altrimenti vuoto.
     */
    Optional<Participant> findParteicipantByEmail(String email);

    /**
     * Recupera tutti i partecipanti iscritti a un specifico hackathon.
     *
     * @param hackathonId L'ID dell'hackathon per cui recuperare i partecipanti.
     * @return Una lista di partecipanti iscritti a quell'hackathon.
     */
    List<Participant> findParticipantByHackathonId(int hackathonId);

    /**
     * Recupera tutti i partecipanti che appartengono a un team specifico.
     *
     * @param teamId L'ID del team per cui recuperare i membri.
     * @return Una lista dei partecipanti del team.
     */
    List<Participant> findParticipantByTeamId(int teamId);

    /**
     * Salva un nuovo partecipante nel database.
     *
     * @param participant L'oggetto Participant da salvare.
     * @return {@code true} se il salvataggio è andato a buon fine, {@code false} altrimenti.
     */
    boolean saveParticipant(Participant participant);



    /**
     * Elimina un partecipante dal database all'interno di una transazione esistente.
     * <p>
     * Progettato per essere usato in operazioni atomiche, come la promozione di un
     * utente a giudice, che richiede la rimozione del suo record di partecipante.
     *
     * @param email L'email del partecipante da eliminare.
     * @param con La connessione SQL attiva su cui eseguire l'operazione.
     * @return {@code true} se l'eliminazione ha avuto successo.
     * @throws SQLException se si verifica un errore di accesso al database.
     */
    boolean deleteParticipantByEmail(String email, Connection con) throws SQLException;

    /**
     * Assegna un partecipante a un team all'interno di una transazione esistente.
     * <p>
     * Cruciale per operazioni transazionali come la creazione di un nuovo team,
     * dove il leader viene immediatamente assegnato al team appena creato.
     *
     * @param email L'email del partecipante da assegnare.
     * @param teamId L'ID del team a cui assegnare il partecipante.
     * @param con La connessione SQL attiva su cui eseguire l'operazione.
     * @return {@code true} se l'assegnazione ha avuto successo.
     * @throws SQLException se si verifica un errore di accesso al database.
     */
    boolean assignParticipantToTeam(String email, int teamId, Connection con) throws SQLException;

    /**
     * Assegna un partecipante a un team.
     * <p>
     * Questo metodo gestisce autonomamente l'apertura e la chiusura della
     * connessione al database e non deve essere usato all'interno di una
     * transazione gestita esternamente.
     *
     * @param email L'email del partecipante da assegnare.
     * @param teamId L'ID del team a cui assegnare il partecipante.
     * @return {@code true} se l'assegnazione ha avuto successo, {@code false} altrimenti.
     */
    boolean assignParticipantToTeam(String email, int teamId);
}