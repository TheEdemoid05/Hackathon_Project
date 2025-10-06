package com.example.dao;

import com.example.model.Hackathon;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per la gestione degli Hackathon.
 * <p>
 * Definisce il contratto per tutte le operazioni di accesso ai dati relative
 * agli eventi hackathon, inclusa la creazione, ricerca, aggiornamento e
 * cancellazione degli eventi.
 *
 * @see com.example.model.Hackathon
 * @see com.example.daoimp.HackathonDaoImpl
 */
public interface HackathonDAO {

    /**
     * Cerca un hackathon nel database utilizzando il suo ID.
     *
     * @param id L'identificatore univoco dell'hackathon.
     * @return Un {@link Optional} che contiene l'hackathon se trovato, altrimenti vuoto.
     */
    Optional<Hackathon> findHackathonById(int id);

    /**
     * Recupera tutti gli hackathon presenti nel database.
     *
     * @return Una lista di tutti gli {@link Hackathon} registrati nel sistema.
     * La lista sarà vuota se non ci sono hackathon.
     */
    List<Hackathon> findAllHackathon();

    /**
     * Cerca un hackathon nel database utilizzando il suo titolo.
     * Poiché il titolo è univoco, questo metodo restituirà al massimo un risultato.
     *
     * @param title Il titolo dell'hackathon da cercare.
     * @return Un {@link Optional} che contiene l'hackathon se trovato, altrimenti vuoto.
     */
    Optional<Hackathon> findHackathonByTitle(String title);

    /**
     * Salva un nuovo hackathon nel database.
     * <p>
     * Dopo il salvataggio, l'ID generato automaticamente dal database
     * verrà impostato sull'oggetto {@code hackathon} passato come parametro.
     *
     * @param hackathon L'oggetto {@link Hackathon} da salvare, completo di tutti i dati.
     */
    void saveHackathon(Hackathon hackathon);

    /**
     * Aggiorna un hackathon esistente nel database.
     *
     * @param hackathon L'oggetto {@link Hackathon} con i dati aggiornati.
     */
    void updateHackathon(Hackathon hackathon);

    /**
     * Aggiorna esclusivamente la descrizione del problema di un hackathon.
     *
     * @param hackathonId L'ID dell'hackathon da aggiornare.
     * @param problemDescription La nuova descrizione del problema.
     * @return {@code true} se l'aggiornamento è andato a buon fine, {@code false} altrimenti.
     */
    boolean updateProblemDescription(int hackathonId, String problemDescription);

    /**
     * Elimina un hackathon dal database tramite il suo ID.

     * @param id L'ID dell'hackathon da eliminare.
     */
    void deleteHackathon(int id);
}