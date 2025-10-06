package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe che garantisce la connessione al database.
 * 
 * <p>Questa classe fornisce metodi statici per ottenere connessioni al database PostgreSQL
 * utilizzato dal sistema di gestione hackathon. </p>
 * 
 * <p><strong>Caratteristiche:</strong></p>
 * <ul>
 *   <li>Gestione centralizzata delle connessioni al database</li>
 *   <li>Configurazione statica dei parametri di connessione</li>
 *   <li>Caricamento automatico del driver PostgreSQL</li>
 *   <li>Gestione delle eccezioni di connessione</li>
 * </ul>
 * 
 * <p><strong>Nota di sicurezza:</strong> In un ambiente di produzione, le credenziali
 * del database dovrebbero essere esternalizzate e non hardcoded nel codice.</p>
 */
public class DBConnection {
    
    /** 
     * URL JDBC per la connessione al database PostgreSQL.
     * Punta al database locale 'Hackathon' sulla porta standard 5432.
     */
    private static final String URL = "jdbc:postgresql://localhost:5432/Hackathon";
    
    /** 
     * Nome utente per l'accesso al database.
     * In produzione dovrebbe essere letto da configurazione esterna.
     */
    private static final String USER = "postgres";

    /** 
     * Password per l'accesso al database.
     * In produzione dovrebbe essere letta da configurazione esterna e criptata.
     */
    private static final String PASSWORD = "Diomede05";

    /**
     * Blocco di inizializzazione statico che carica il driver JDBC PostgreSQL.
     * 
     * <p>Questo caricamento esplicito è opzionale per JDBC 4.0+ ma può essere
     * utile in alcuni casi specifici o per retrocompatibilità.</p>
     */
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC PostgreSQL non trovato!");
            e.printStackTrace();
        }
    }

    /**
     * Ottiene una nuova connessione al database PostgreSQL.
     * 
     * <p>Crea una nuova connessione ogni volta che il metodo viene chiamato.
     * È responsabilità del chiamante chiudere la connessione quando non più necessaria
     * per evitare memory leak.</p>
     * 
     * @return Una nuova istanza di {@link Connection} connessa al database
     * @throws SQLException Se si verifica un errore di accesso al database o
     *                     se le credenziali non sono valide
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
