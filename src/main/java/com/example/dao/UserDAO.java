package com.example.dao;

import com.example.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO per la gestione degli utenti.
 * 
 * <p>Questa interfaccia definisce i contratti per tutte le operazioni di accesso ai dati
 * relative agli utenti del sistema.</p>
 * 
 * <p>Questa classe permette:</p>
 * <ul>
 *   <li>Operazioni CRUD (Create, Read, Update, Delete) sugli utenti</li>
 *   <li>Ricerca e filtraggio degli utenti</li>
 *   <li>Gestione delle connessioni al database</li>
 *   <li>Mapping tra oggetti Java e record del database</li>
 * </ul>
 *
 * @see com.example.model.User
 * @see com.example.daoimp.UserDaoImpl
 */
public interface UserDAO {

    /**
     * Cerca un utente nel database utilizzando l'indirizzo email.
     * 
     * <p>L'email è utilizzata come identificatore univoco per gli utenti
     * nel sistema.</p>
     * 
     * @param email L'indirizzo email dell'utente da cercare
     * @return Un {@link Optional} contenente l'utente se trovato, altrimenti vuoto
     * @throws IllegalArgumentException se l'email è null o vuota
     */
    Optional<User> findUserByEmail(String email);


    /**
     * Salva un nuovo utente nel database.
     * 
     * <p>Questo metodo inserisce un nuovo record utente nel database.
     * L'email dell'utente deve essere unica nel sistema.</p>
     * 
     * @param user L'utente da salvare nel database
     * @return {@code true} se l'operazione è riuscita, {@code false} altrimenti
     * @throws IllegalArgumentException se l'utente è null o ha dati non validi
     */
    boolean saveUser(User user);





}
