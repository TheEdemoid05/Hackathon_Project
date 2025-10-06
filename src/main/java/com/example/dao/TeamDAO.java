package com.example.dao;

import com.example.model.Team;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per la gestione dei Team.
 * <p>
 * Definisce il contratto per tutte le operazioni di accesso ai dati
 * relative ai team, inclusa la ricerca, la creazione e la gestione
 * dei membri all'interno di una transazione.
 *
 * @see com.example.model.Team
 * @see com.example.daoimp.TeamDaoImpl
 */
public interface TeamDAO {

    /**
     * Cerca un team nel database utilizzando il suo ID.
     *
     * @param id L'identificatore univoco del team.
     * @return Un {@link Optional} contenente il team se trovato, altrimenti vuoto.
     */
    Optional<Team> findTeamById(int id);

    /**
     * Recupera tutti i team associati a un specifico hackathon.
     *
     * @param hackathonId L'ID dell'hackathon per cui recuperare i team.
     * @return Una lista di team partecipanti all'hackathon.
     */
    List<Team> findTeamByHackathonId(int hackathonId);

    /**
     * Salva un nuovo team nel database all'interno di una transazione esistente.
     * <p>
     * Questo metodo è progettato per essere utilizzato all'interno di una
     * transazione gestita dal {@link com.example.controller.ControllerGui}
     * per garantire che la creazione del team e l'assegnazione del leader
     * siano operazioni atomiche.
     *
     * @param team Il team da salvare, completo di tutti i dati necessari.
     * @param conn La connessione SQL attiva su cui eseguire l'operazione.
     * @return {@code true} se il team è stato salvato con successo.
     * @throws SQLException se si verifica un errore di accesso al database durante l'inserimento.
     */
    boolean saveTeam(Team team, Connection conn) throws SQLException;


    /**
     * Verifica se un nome di team esiste già all'interno di un hackathon.
     *
     * @param teamName Il nome del team da verificare.
     * @param hackathonId L'ID dell'hackathon in cui effettuare la verifica.
     * @return {@code true} se il nome del team è già in uso, {@code false} altrimenti.
     */
    boolean teamNameExistsInHackathon(String teamName, int hackathonId);

    /**
     * Calcola e restituisce il numero di membri attuali in un team.
     *
     * @param teamId L'ID del team di cui contare i membri.
     * @return Il numero di partecipanti nel team.
     */
    int getTeamMemberCount(int teamId);
}