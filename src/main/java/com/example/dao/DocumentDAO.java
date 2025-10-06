package com.example.dao;

import com.example.model.Document;
import java.util.List;

/**
 * Interfaccia DAO per la gestione dei documenti dei team.
 * <p>
 * Definisce il contratto per tutte le operazioni di accesso ai dati relative
 * ai documenti dei progetti, come il recupero di file per un team o per un
 * hackathon e il salvataggio di nuovi documenti.
 *
 * @see com.example.model.Document
 * @see com.example.daoimp.DocumentDaoImpl
 */
public interface DocumentDAO {

    /**
     * Recupera tutti i documenti associati a un team specifico.
     *
     * @param teamId L'ID del team di cui si vogliono recuperare i documenti.
     * @return Una lista di {@link Document} appartenenti al team specificato.
     * La lista sarà vuota se il team non ha caricato documenti.
     */
    List<Document> findDocumentByTeamId(int teamId);

    /**
     * Recupera i documenti di un hackathon che non sono ancora stati valutati da un giudice specifico.
     * <p>
     * Questa è una query utilizzata principalmente nella dashboard
     * dei giudici per mostrare solo i progetti che richiedono una valutazione.
     *
     * @param hackathonId L'ID dell'hackathon in cui cercare i documenti.
     * @param judgeEmail L'email del giudice per cui escludere i documenti già votati.
     * @return Una lista di {@link Document} che il giudice deve ancora valutare.
     */
    List<Document> findDocumentByHackathonId(int hackathonId, String judgeEmail);

    /**
     * Salva un nuovo documento nel database.
     * <p>
     * Dopo il salvataggio, l'ID generato automaticamente dal database
     * verrà impostato sull'oggetto {@code document} passato come parametro.
     *
     * @param document L'oggetto {@link Document} da salvare, contenente
     * il nome del file, l'ID del team e la data di caricamento.
     */
    void saveDocument(Document document);
}