/**
 * Package contenente le implementazioni concrete delle interfacce DAO.
 *
 * <h2>Panoramica delle Implementazioni</h2>
 * <p>Questo package fornisce le implementazioni specifiche per le interfacce
 * definite in {@link com.example.dao}. Tutte le classi utilizzano JDBC per
 * interagire con un database PostgreSQL</p>
 *
 * <h2>Descrizione delle Classi DAO Implementate:</h2>
 * <ul>
 * <li><b>{@link com.example.daoimp.UserDaoImpl}</b>
 * <br>Gestisce le operazioni CRUD per l'entità {@link com.example.model.User}. La sua
 * responsabilità più importante è la determinazione dinamica del ruolo
 * dell'utente ({@link com.example.model.Role}).</li>
 *
 * <li><b>{@link com.example.daoimp.HackathonDaoImpl}</b>
 * <br>Implementa la logica di persistenza per gli {@link com.example.model.Hackathon}.
 * Si occupa della mappatura tra i tipi Java (`LocalDateTime`) e i tipi SQL (`TIMESTAMP`),
 * gestendo correttamente anche i valori `NULL` per le date di registrazione.</li>
 *
 * <li><b>{@link com.example.daoimp.TeamDaoImpl}</b>
 * <br>Specializzato nella gestione dei {@link com.example.model.Team}. Il suo metodo
 * più importante è {@code saveTeam}, progettato per operare all'interno di una
 * transazione JDBC gestita dal controller. Questo garantisce che la creazione
 * di un team e l'assegnazione del suo leader come primo partecipante siano
 * operazioni atomiche.</li>
 *
 * <li><b>{@link com.example.daoimp.ParticipantDaoImpl}</b>
 * <br>Gestisce i dati dei {@link com.example.model.Participant}. Implementa
 * operazioni cruciali per la logica relativa alla manipolazione delle tabella Participant, come l'assegnazione di un
 * partecipante a un team e la sua rimozione, spesso come parte di transazioni
 * più ampie (es. quando un utente viene promosso a giudice).</li>
 *
 * <li><b>{@link com.example.daoimp.JudgeDaoImpl}</b>
 * <br>Implementa le operazioni per l'entità {@link com.example.model.Judge}.
 * Le sue operazioni di salvataggio e cancellazione sono fondamentali per
 * le transazioni gestite dal controller, come la promozione di un partecipante
 * a giudice, che richiede l'inserimento in una tabella e la cancellazione da un'altra.</li>
 *
 * <li><b>{@link com.example.daoimp.DocumentDaoImpl}</b>
 * <br>Gestisce la persistenza dei {@link com.example.model.Document}. Contiene una serie di query relative alla gestione dei documenti, come ad esempio il recupero di file per un team o per un hackathon.
 * </li>
 *
 * <li><b>{@link com.example.daoimp.VoteDaoImpl}</b>
 * <br>Si occupa delle operazioni relative ai {@link com.example.model.Vote}. Una delle sue
 * funzionalità chiave è il metodo {@code hasJudgeVotedForTeam}, che esegue un
 * controllo rapido per verificare se un giudice ha già espresso un voto per
 * un determinato team, prevenendo votazioni multiple.</li>
 *
 * <li><b>{@link com.example.daoimp.RequestDaoImpl}</b>
 * <br>Implementa la logica per le {@link com.example.model.Request} di
 * partecipazione ai team. Fornisce metodi per recuperare le richieste
 * inviate o ricevute da un utente specifico, supportando così la
 * funzionalità di gestione delle richieste nelle dashboard dei partecipanti.</li>
 * </ul>

 */
package com.example.daoimp;