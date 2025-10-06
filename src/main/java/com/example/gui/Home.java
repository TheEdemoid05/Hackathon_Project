package com.example.gui;

import com.example.controller.ControllerGui;
import com.example.gui.team.TeamManagementDashboard;
import com.example.model.Role;
import com.example.model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Classe principale dell'interfaccia grafica (GUI) che funge da finestra contenitore.
 * <p>
 * Questa classe estende {@link JFrame} e agisce come il punto centrale
 * dell'applicazione, gestendo la navigazione tra i diversi pannelli
 * (Login, Registrazione, Dashboard, etc.) utilizzando un {@link CardLayout}.
 * Mantiene lo stato dell'utente attualmente autenticato e orchestra la
 * visualizzazione delle schermate appropriate in base al ruolo dell'utente.
 * @see com.example.controller.ControllerGui
 */
public class Home extends JFrame {

    /** Identificatore univoco per il pannello di login nel CardLayout. */
    public static final String LOGIN_PANEL = "LoginPanel";
    /** Identificatore univoco per il pannello di registrazione nel CardLayout. */
    public static final String REGISTRATION_PANEL = "RegistrationPanel";
    /** Identificatore univoco per la dashboard del partecipante nel CardLayout. */
    public static final String PARTICIPANT_DASHBOARD = "ParticipantDashboard";
    /** Identificatore univoco per la dashboard dell'organizzatore nel CardLayout. */
    public static final String ORGANIZER_DASHBOARD = "OrganizerDashboard";
    /** Identificatore univoco per la dashboard del giudice nel CardLayout. */
    public static final String JUDGE_DASHBOARD = "JudgeDashboard";
    /** Identificatore univoco per la dashboard dell'utente generico nel CardLayout. */
    public static final String USER_DASHBOARD = "UserDashboard";

    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private User loggedUser;
    private TeamManagementDashboard participantDashboard;
    private final OrganizerDashboard organizerDashboard;
    private final JudgeDashboard judgeDashboard;
    private final UserDashboard userDashboard;
    private final LoginPage loginPage;
    private final ControllerGui controller;

    /**
     * Costruisce la finestra principale dell'applicazione.
     * <p>
     * Inizializza il frame, il CardLayout e tutti i pannelli principali
     * (login, registrazione e le varie dashboard), impostando la schermata
     * di login come pannello iniziale visibile.
     */
    public Home() {
        setTitle("Hackathon App");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controller = new ControllerGui();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPage = new LoginPage(mainPanel, this, controller);
        mainPanel.add(loginPage, LOGIN_PANEL);

        RegistrationPage registrationPage = new RegistrationPage(mainPanel, this);
        mainPanel.add(registrationPage, REGISTRATION_PANEL);

        userDashboard = new UserDashboard(this);
        mainPanel.add(userDashboard, USER_DASHBOARD);

        organizerDashboard = new OrganizerDashboard(this);
        mainPanel.add(organizerDashboard, ORGANIZER_DASHBOARD);

        judgeDashboard = new JudgeDashboard(this);
        mainPanel.add(judgeDashboard, JUDGE_DASHBOARD);

        add(mainPanel);
        cardLayout.show(mainPanel, LOGIN_PANEL);
    }

    /**
     * Esegue il logout dell'utente corrente.
     * <p>
     * Resetta l'utente loggato, pulisce i campi del form di login e
     * mostra nuovamente la schermata di login.
     */
    public void logout() {
        setLoggedUser(null);
        if (loginPage != null) {
            loginPage.clearForm();
        }
        cardLayout.show(mainPanel, LOGIN_PANEL);
    }

    /**
     * Restituisce l'utente attualmente autenticato nel sistema.
     *
     * @return L'oggetto {@link User} loggato, o {@code null} se nessun utente è loggato.
     */
    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Imposta l'utente attualmente autenticato.
     *
     * @param user L'utente che ha effettuato il login.
     */
    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    /**
     * Mostra la dashboard appropriata in base al ruolo dell'utente.
     * <p>
     * Questo metodo carica i dati specifici dell'utente nella dashboard corretta
     * e la visualizza utilizzando il CardLayout.
     *
     * @param role Il ruolo dell'utente ({@link Role}) che determina quale dashboard mostrare.
     */
    public void showDashboard(Role role) {
        switch (role) {
            case ORGANIZER:
                organizerDashboard.loadUserData(loggedUser);
                cardLayout.show(mainPanel, ORGANIZER_DASHBOARD);
                break;
            case JUDGE:
                judgeDashboard.loadUserData(loggedUser);
                cardLayout.show(mainPanel, JUDGE_DASHBOARD);
                break;
            case PARTICIPANT:
                ensureParticipantDashboard();
                cardLayout.show(mainPanel, PARTICIPANT_DASHBOARD);
                break;
            case USER:
                userDashboard.loadUserData(loggedUser);
                cardLayout.show(mainPanel, USER_DASHBOARD);
                break;
            default:
                cardLayout.show(mainPanel, LOGIN_PANEL);
                break;
        }
    }

    /**
     * Metodo privato per creare o aggiornare la dashboard del partecipante.
     * <p>
     * Questo metodo garantisce che la dashboard del partecipante sia sempre
     * aggiornata con i dati dell'utente loggato. Rimuove la vecchia istanza
     * e ne crea una nuova ogni volta che viene richiamata per riflettere
     * eventuali cambiamenti di stato (es. creazione di un team).
     */
    private void ensureParticipantDashboard() {
        if (loggedUser == null) {
            System.err.println("ERRORE: Non posso creare ParticipantDashboard senza utente loggato");
            return;
        }
        if (participantDashboard != null) {
            mainPanel.remove(participantDashboard);
        }
        participantDashboard = new TeamManagementDashboard(loggedUser, this);
        mainPanel.add(participantDashboard, PARTICIPANT_DASHBOARD);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Mostra specificamente la dashboard di gestione del team per un partecipante.
     * <p>
     * Metodo di convenienza per navigare direttamente alla dashboard del team,
     * assicurandosi che venga prima creata o aggiornata.
     */
    public void showTeamManagementDashboard() {
        if (loggedUser == null) {
            cardLayout.show(mainPanel, LOGIN_PANEL);
            return;
        }
        ensureParticipantDashboard();
        cardLayout.show(mainPanel, PARTICIPANT_DASHBOARD);
    }
}