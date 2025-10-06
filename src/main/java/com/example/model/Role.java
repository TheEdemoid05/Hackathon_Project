package com.example.model;

/**
 * Enumerazione che definisce i ruoli disponibili nel sistema di gestione hackathon.
 * 
 * <p>Ogni utente del sistema deve avere uno specifico ruolo che determina
 * le sue autorizzazioni e le funzionalità a cui può accedere.</p>
 * 
 * <p><strong>Ruoli disponibili:</strong></p>
 * <ul>
 *   <li><strong>USER:</strong> Ruolo base per utenti generici senza privilegi speciali</li>
 *   <li><strong>ORGANIZER:</strong> Può creare e gestire hackathon</li>
 *   <li><strong>JUDGE:</strong> Può valutare i progetti e assegnare voti</li>
 *   <li><strong>PARTICIPANT:</strong> Può partecipare agli hackathon e formare team</li>
 * </ul>
 * @see com.example.model.User
 * @see com.example.model.Organizer
 * @see com.example.model.Judge
 * @see com.example.model.Participant
 */
public enum Role {
    
    /**
     * Ruolo base per utenti generici del sistema.
     * Gli utenti con questo ruolo hanno accesso limitato alle funzionalità.
     */
    USER, 
    
    /**
     * Ruolo per gli organizzatori di hackathon.
     * Permette di creare, modificare e gestire hackathon.
     */
    ORGANIZER, 
    
    /**
     * Ruolo per i giudici che valutano i progetti.
     * Permette di visualizzare i progetti e assegnare voti.
     */
    JUDGE, 
    
    /**
     * Ruolo per i partecipanti agli hackathon.
     * Permette di registrarsi agli hackathon, formare team e sottomettere progetti.
     */
    PARTICIPANT
}