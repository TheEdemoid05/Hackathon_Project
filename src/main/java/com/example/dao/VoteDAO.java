package com.example.dao;

import com.example.model.Vote;

import java.util.List;

/**
 * Interfaccia DAO per la gestione dei Voti.
 * <p>
 * Definisce il contratto per tutte le operazioni di accesso ai dati relative
 * ai voti assegnati dai giudici ai progetti dei team, inclusa la verifica
 * di voti esistenti, il recupero e il salvataggio.
 *
 * @see com.example.model.Vote
 * @see com.example.daoimp.VoteDaoImpl
 */
public interface VoteDAO {

    /**
     * Controlla se un giudice ha già espresso un voto per un team specifico in un dato hackathon.
     * <p>
     * Metodo fondamentale per prevenire votazioni multiple da parte dello stesso
     * giudice sullo stesso progetto.
     *
     * @param hackathonId L'ID dell'hackathon in cui è avvenuta la votazione.
     * @param judgeEmail L'email del giudice che ha espresso il voto.
     * @param teamId L'ID del team che ha ricevuto il voto.
     * @return {@code true} se un voto corrispondente a questi criteri esiste già, {@code false} altrimenti.
     */
    boolean hasJudgeVotedForTeam(int hackathonId, String judgeEmail, int teamId);

    /**
     * Recupera tutti i voti assegnati a un team specifico.
     * <p>
     * Questo metodo è utile per calcolare il punteggio medio o totale di un team.
     *
     * @param teamId L'ID del team di cui recuperare i voti.
     * @return Una lista di {@link Vote} assegnati al team. La lista sarà
     * vuota se il team non ha ancora ricevuto voti.
     */
    List<Vote> findVoteByTeamId(int teamId);

    /**
     * Salva un nuovo voto nel database, associandolo a un documento specifico.
     * <p>
     * Dopo il salvataggio, l'ID generato automaticamente dal database
     * verrà impostato sull'oggetto {@code vote} passato come parametro.
     *
     * @param vote L'oggetto {@link Vote} da salvare, contenente punteggio, commento e riferimenti.
     * @param documentID L'ID del {@link com.example.model.Document} a cui questo voto si riferisce.
     */
    void saveVote(Vote vote, int documentID);
}