/**
 * Package contenente i pannelli specializzati per la gestione dei team.
 *
 * <h2>Scopo e Funzionalità</h2>
 * <p>
 * Questo package raggruppa tutti i componenti dell'interfaccia utente (GUI)
 * che fanno parte della {@link com.example.gui.team.TeamManagementDashboard}.
 * Ogni classe in questo package rappresenta una specifica "scheda" (tab) o
 * funzionalità a disposizione del partecipante per interagire con il proprio
 * team o con altri partecipanti.
 * </p>
 *
 * <h3>Componenti Principali:</h3>
 * <ul>
 * <li>
 * <b>{@link com.example.gui.team.TeamManagementDashboard}</b>:
 * La classe contenitore principale che utilizza un {@link javax.swing.JTabbedPane}
 * per orchestrare e visualizzare i vari pannelli descritti di seguito. Gestisce
 * la visibilità dei pannelli in base al fatto che l'utente sia o meno in un team.
 * </li>
 * <li>
 * <b>{@link com.example.gui.team.MyTeamPanel}</b>:
 * Visualizza le informazioni del team a cui l'utente appartiene, inclusa la
 * lista dei membri. È la schermata principale per chi ha già un team.
 * </li>
 * <li>
 * <b>{@link com.example.gui.team.CreateTeamPanel}</b>:
 * Fornisce un modulo per i partecipanti senza team per creare una nuova squadra.
 * Questa scheda è visibile solo se l'utente non fa parte di alcun team.
 * </li>
 * <li>
 * <b>{@link com.example.gui.team.TeamProjectsPanel}</b>:
 * Permette ai membri di un team di caricare e visualizzare i documenti di progetto
 * relativi all'hackathon. Questa scheda è accessibile solo ai membri di un team.
 * </li>
 * <li>
 * <b>{@link com.example.gui.team.SendRequestsPanel}</b>:
 * Offre una doppia funzionalità: permette di cercare altri partecipanti
 * all'hackathon per inviare loro una richiesta di adesione al proprio team e
 * di visualizzare lo stato delle richieste già inviate.
 * </li>
 * <li>
 * <b>{@link com.example.gui.team.IncomingRequestsPanel}</b>:
 * Permette al leader di un team di visualizzare e gestire le richieste di
 * partecipazione ricevute da altri utenti, con la possibilità di accettarle o
 * rifiutarle.
 * </li>
 * </ul>
 *

 */
package com.example.gui.team;