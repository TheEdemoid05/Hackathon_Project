package com.example.model;

import java.time.LocalDateTime;

/**
 * Rappresenta un documento sottomesso da un team per il progetto di un hackathon.
 * <p>
 * Questa classe contiene le informazioni relative a un file caricato da un team,
 * includendo il suo ID univoco, il team di appartenenza, il nome del file e la
 * data e ora del caricamento.

 * @see com.example.model.Team
 * @see com.example.dao.DocumentDAO
 */
public class Document {

    /**
     * L'identificatore univoco del documento.
     */
    private int id;

    /**
     * L'ID del team a cui questo documento appartiene.
     */
    private int teamId;

    /**
     * La data e l'ora in cui il documento è stato caricato.
     */
    private LocalDateTime uploadDate;

    /**
     * Il nome del file caricato.
     */
    private String fileName;

    /**
     * Costruisce un nuovo Documento, tipicamente usato prima di salvarlo nel database.
     * La data di caricamento è impostata automaticamente all'ora corrente del sistema.
     *
     * @param teamId L'ID del team che sta caricando il documento.
     * @param fileName Il nome del file che viene caricato.
     */
    public Document(int teamId, String fileName) {
        this.teamId = teamId;
        this.fileName = fileName;
        this.uploadDate = LocalDateTime.now();
    }

    /**
     * Costruisce un Documento con tutti i campi, tipicamente usato per ricostruire
     * un oggetto dal database.
     *
     * @param id L'ID univoco del documento.
     * @param fileName Il nome del file.
     * @param uploadDate La data e l'ora esatte in cui il documento è stato caricato.
     * @param teamId L'ID del team che possiede il documento.
     */
    public Document(int id, String fileName, LocalDateTime uploadDate, int teamId) {
        this.id = id;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.teamId = teamId;
    }

    /**
     * Restituisce la data e l'ora di caricamento del documento.
     *
     * @return Il {@link LocalDateTime} del caricamento.
     */
    public LocalDateTime getUploadDate() {
        return uploadDate;
    }


    /**
     * Restituisce l'identificatore univoco del documento.
     *
     * @return L'ID del documento.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'identificatore univoco del documento.
     *
     * @param id Il nuovo ID per il documento.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce l'ID del team che possiede questo documento.
     *
     * @return L'ID del team.
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * Imposta l'ID del team che possiede questo documento.
     *
     * @param teamId Il nuovo ID del team.
     */
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    /**
     * Restituisce il nome del file.
     *
     * @return Il nome del file.
     */
    public String getFileName() {
        return fileName;
    }


    /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto Document.
     *
     * @return Una stringa contenente i dettagli del documento.
     */
    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", teamId=" + teamId +
                ", fileName='" + fileName + '\'' +
                ", uploadDate=" + uploadDate +
                '}';
    }
}