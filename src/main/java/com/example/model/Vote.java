package com.example.model;

/**
 * Rappresenta un voto espresso da un giudice per il progetto di un team.
 * <p>
 * Questa classe incapsula tutti i dettagli di una singola valutazione, includendo
 * il punteggio, un commento testuale e i riferimenti al giudice, al team e
 * all'hackathon coinvolti.
 * @see com.example.model.Judge
 * @see com.example.model.Team
 * @see com.example.model.Document
 * @see com.example.dao.VoteDAO
 */
public class Vote {
    /**
     * L'identificatore univoco del voto.
     */
    private int id;
    /**
     * L'ID dell'hackathon in cui è stato espresso il voto.
     */
    private int hackathonId;
    /**
     * L'email del giudice che ha espresso il voto.
     */
    private String judgeEmail;
    /**
     * L'ID del team che ha ricevuto il voto.
     */
    private int teamId;
    /**
     * Il punteggio numerico assegnato, tipicamente in un intervallo predefinito (es. 1-10).
     */
    private int score;
    /**
     * Un commento testuale o feedback fornito dal giudice insieme al punteggio.
     */
    private String comment;

    /**
     * Costruisce un nuovo Voto, tipicamente usato prima di salvarlo nel database.
     * L'ID è inizializzato a 0 e sarà assegnato dal database.
     *
     * @param hackathonId L'ID dell'hackathon.
     * @param judgeEmail L'email dell'utente giudice.
     * @param teamId L'ID del team che viene votato.
     * @param score Il punteggio numerico.
     * @param comment Il feedback testuale.
     */
    public Vote(int hackathonId, String judgeEmail, int teamId, int score, String comment) {
        this(0, hackathonId, judgeEmail, teamId, score, comment);
    }

    /**
     * Costruisce un Voto con tutti i campi, tipicamente usato per ricostruire
     * un oggetto dal database.
     *
     * @param id L'ID univoco del voto.
     * @param hackathonId L'ID dell'hackathon.
     * @param judgeEmail L'email dell'utente giudice.
     * @param teamId L'ID del team che viene votato.
     * @param score Il punteggio numerico.
     * @param comment Il feedback testuale.
     */
    public Vote(int id, int hackathonId, String judgeEmail, int teamId, int score, String comment) {
        this.id = id;
        this.hackathonId = hackathonId;
        this.judgeEmail = judgeEmail;
        this.teamId = teamId;
        this.score = score;
        this.comment = comment;
    }

    /**
     * Restituisce l'identificatore univoco del voto.
     * @return L'ID del voto.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'identificatore univoco del voto.
     * @param id Il nuovo ID del voto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce l'ID dell'hackathon associato a questo voto.
     * @return L'ID dell'hackathon.
     */
    public int getHackathonId() {
        return hackathonId;
    }

    /**
     * Imposta l'ID dell'hackathon per questo voto.
     * @param hackathonId Il nuovo ID dell'hackathon.
     */
    public void setHackathonId(int hackathonId) {
        this.hackathonId = hackathonId;
    }

    /**
     * Restituisce l'email del giudice che ha espresso questo voto.
     * @return L'email del giudice.
     */
    public String getJudgeEmail() {
        return judgeEmail;
    }



    /**
     * Restituisce l'ID del team che ha ricevuto questo voto.
     * @return L'ID del team.
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * Imposta l'ID del team per questo voto.
     * @param teamId Il nuovo ID del team.
     */
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    /**
     * Restituisce il punteggio numerico di questo voto.
     * @return Il punteggio.
     */
    public int getScore() {
        return score;
    }



    /**
     * Restituisce il commento associato a questo voto.
     * @return La stringa del commento.
     */
    public String getComment() {
        return comment;
    }



    /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto Vote.
     * @return Una stringa contenente i dettagli del voto.
     */
    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", hackathonId=" + hackathonId +
                ", judgeEmail=" + judgeEmail +
                ", teamId=" + teamId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }
}