package com.example.model;

/**
 * Classe che rappresenta un team di partecipanti in un hackathon.
 * 
 * <p>Un team è formato da un gruppo di partecipanti che collaborano insieme
 * per sviluppare una soluzione durante un hackathon. Ogni team deve avere
 * un leader e può contenere un numero massimo di partecipanti definito.</p>
 * 
 * <p><strong>Caratteristiche principali:</strong></p>
 * <ul>
 *   <li>Identificazione univoca tramite ID</li>
 *   <li>Nome distintivo del team</li>
 *   <li>Leader responsabile del team</li>
 *   <li>Limite massimo di partecipanti</li>
 *   <li>Associazione con uno specifico hackathon</li>
 * </ul>

 * @see com.example.model.Hackathon
 * @see com.example.model.Participant
 * @see com.example.model.Request
 */
public class Team {
    
    /**
     * Identificatore univoco del team nel database.
     */
    private int id;
    
    /**
     * Nome del team scelto dal leader.
     */
    private String name;
    
    /**
     * ID dell'hackathon a cui partecipa questo team.
     */
    private int hackathonId;
    
    /**
     * Email del leader del team.
     * Il leader è responsabile della gestione del team e delle richieste.
     */
    private String leader_email;
    
    /**
     * Numero massimo di partecipanti ammessi nel team.
     */
    private int max_participants;

    /**
     * Costruttore completo per creare un team con ID specificato.
     * Utilizzato principalmente per caricare team esistenti dal database.
     * 
     * @param id Identificatore univoco del team
     * @param name Nome del team
     * @param max_participants Numero massimo di partecipanti ammessi
     * @param leader_email Email del leader del team
     * @param hackathonId ID dell'hackathon di appartenenza
     */
    public Team(int id, String name, int max_participants, String leader_email, int hackathonId) {
        this.id = id;
        this.name = name;
        this.max_participants = max_participants;
        this.leader_email = leader_email;
        this.hackathonId = hackathonId;
    }

    /**
     * Costruttore per creare un nuovo team senza ID specificato.
     * L'ID verrà assegnato automaticamente dal database.
     * 
     * @param name Nome del team
     * @param max_participants Numero massimo di partecipanti ammessi
     * @param leader_email Email del leader del team
     * @param hackathonId ID dell'hackathon di appartenenza
     */
    public Team(String name, int max_participants, String leader_email, int hackathonId) {
        this(0, name, max_participants, leader_email, hackathonId);
    }

    /**
     * Restituisce l'identificatore univoco del team.
     * 
     * @return L'ID del team
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'identificatore univoco del team.
     * 
     * @param id Il nuovo ID del team
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getLeader_email() {
        return leader_email;
    }



    public int getMax_participants() {
        return max_participants;
    }


    /**
     * Restituisce il nome del team.
     * 
     * @return Il nome del team
     */
    public String getName() {
        return name;
    }



    public int getHackathonId() {
        return hackathonId;
    }

    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }

    /**
     * Restituisce una rappresentazione in formato stringa del team.
     * 
     * @return Una stringa contenente ID, nome e hackathon ID del team
     */
    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hackathonId=" + hackathonId +
                '}';
    }
}