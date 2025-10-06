package com.example.dao;

import com.example.model.Judge;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO  per la gestione dei Giudici.
 * <p>
 * Definisce il contratto per tutte le operazioni di accesso ai dati relative
 * ai giudici, inclusa la loro ricerca, creazione, aggiornamento e
 * associazione agli hackathon.

 * @see com.example.model.Judge
 * @see com.example.daoimp.JudgeDaoImpl
 */
public interface JudgeDAO {

    /**
     * Cerca un giudice nel database utilizzando il suo indirizzo email.
     *
     * @param email L'indirizzo email del giudice da cercare.
     * @return Un {@link Optional} che contiene il giudice se trovato, altrimenti vuoto.
     */
    Optional<Judge> findJudgeByEmail(String email);

    /**
     * Salva un nuovo giudice nel database all'interno di una transazione esistente.
     * <p>
     * Questo metodo è progettato per essere utilizzato in operazioni atomiche
     * gestite dal controller, come la promozione di un partecipante a giudice.
     *
     * @param judge Il giudice da salvare.
     * @param conn La connessione SQL attiva su cui eseguire l'operazione.
     * @return {@code true} se il salvataggio è andato a buon fine, {@code false} altrimenti.
     * @throws SQLException se si verifica un errore di accesso al database.
     */
    boolean saveJudge(Judge judge, Connection conn) throws SQLException;

    /**
     * Salva un nuovo giudice nel database.
     * <p>
     * Questo metodo gestisce autonomamente la connessione al database.
     * Da non usare all'interno di transazioni gestite.
     *
     * @param judge Il giudice da salvare.
     * @return {@code true} se il salvataggio è andato a buon fine, {@code false} altrimenti.
     */
    boolean saveJudge(Judge judge);



    /**
     * Recupera tutti i giudici associati a un specifico hackathon.
     *
     * @param hackathonId L'ID dell'hackathon per cui recuperare i giudici.
     * @return Una lista di giudici assegnati all'hackathon.
     */
    List<Judge> findJudgeByHackathonId(int hackathonId);
}