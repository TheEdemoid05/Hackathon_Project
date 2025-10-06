/**
 * Package contenente il controller principale dell'applicazione.
 *
 * <h2>Architettura e Pattern MVC</h2>
 * <p>
 * Questo package implementa il cuore logico dell'applicazione secondo il pattern
 * <b>Model-View-Controller</b>. La sua unica classe,
 * {@link com.example.controller.ControllerGui}, agisce come un intermediario
 * tra il livello di presentazione ({@link com.example.gui}) e i
 * livelli di accesso ai dati ({@link com.example.dao}) e di dominio
 * ({@link com.example.model}).
 * </p>
 *
 * <h2>Responsabilità del Controller</h2>
 * <p>
 * Il {@code ControllerGui} ha le seguenti responsabilità chiave:
 * </p>
 * <ul>
 * <li>
 * <b>Centralizzazione della Logica relativa ai metodi delle implementazioni DAO:</b> Riceve le richieste dall'interfaccia
 * utente (es. "registra un nuovo utente", "crea un team") e sfrutta le
 * operazioni necessarie per soddisfarle, invocando i metodi appropriati
 * dei vari DAO.
 * </li>
 * <li>
 * <b>Gestione delle Transazioni:</b> Per le operazioni che richiedono la modifica
 * di più tabelle in modo atomico (come la promozione di un utente a giudice o la
 * creazione di un team con il suo leader), il controller gestisce esplicitamente
 * le transazioni del database. Apre una connessione, esegue le operazioni DAO e, solo in caso di successo di tutte, esegue il
 * commit. In caso di fallimento, esegue il rollback per mantenere la consistenza
 * dei dati.
 * </li>
 * <li>
 * <b>Disaccoppiamento dei Livelli:</b> Garantisce che il livello GUI non
 * comunichi mai direttamente con il livello di accesso ai dati.
 * </li>
 * </ul>
 */
package com.example.controller;