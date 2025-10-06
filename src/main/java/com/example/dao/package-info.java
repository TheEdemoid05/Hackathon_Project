/**
 * Package contenente le interfacce Data Access Object (DAO).
 * 
 * <p>Questo package definisce i contratti per l'accesso ai dati del sistema.
 * Ogni interfaccia DAO corrisponde a un'entità del modello di dominio e 
 * definisce le operazioni CRUD (Create, Read, Update, Delete) specifiche.</p>
 * 
 * <h2>Interfacce DAO:</h2>
 * <ul>
 *   <li>{@link com.example.dao.UserDAO} - Operazioni per gli utenti</li>
 *   <li>{@link com.example.dao.ParticipantDAO} - Operazioni per i partecipanti</li>
 *
 *   <li>{@link com.example.dao.JudgeDAO} - Operazioni per i giudici</li>
 *   <li>{@link com.example.dao.HackathonDAO} - Operazioni per gli hackathon</li>
 *   <li>{@link com.example.dao.TeamDAO} - Operazioni per i team</li>
 *   <li>{@link com.example.dao.RequestDAO} - Operazioni per le richieste</li>
 *   <li>{@link com.example.dao.VoteDAO} - Operazioni per i voti</li>
 *   <li>{@link com.example.dao.DocumentDAO} - Operazioni per i documenti</li>
 * </ul>
 * 
 * <p>Le implementazioni concrete sono fornite nel package 
 * {@link com.example.daoimp}.</p>
 *

 */
package com.example.dao;
