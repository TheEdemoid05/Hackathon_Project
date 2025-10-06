package com.example.gui.team;

import com.example.controller.ControllerGui;
import com.example.gui.Home;
import com.example.model.Hackathon;
import com.example.model.Participant;
import com.example.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Pannello principale della dashboard per un partecipante.
 * <p>
 * Questa classe funge da contenitore principale per tutte le funzionalità
 * relative alla gestione del team per un partecipante. Utilizza un
 * {@link JTabbedPane} per organizzare le diverse sezioni:
 * </p>
 * <ul>
 * <li>Visualizzazione della sfida dell'hackathon</li>
 * <li>Gestione del proprio team (se esistente)</li>
 * <li>Gestione dei progetti del team</li>
 * <li>Creazione di un nuovo team (se non si fa parte di uno)</li>
 * <li>Gestione delle richieste in arrivo e in uscita</li>
 * </ul>
 * <p>
 * La visibilità delle schede viene gestita dinamicamente in base allo stato
 * del partecipante (se appartiene o meno a un team).
 *
 * @see Home
 * @see Participant
 * @see ControllerGui
 */
public class TeamManagementDashboard extends JPanel {

    private String email;
    private JTabbedPane tabbedPane;
    private MyTeamPanel myTeamPanel;
    private IncomingRequestsPanel incomingRequestsPanel;
    private final ControllerGui controllerGui = new ControllerGui();
    private SendRequestsPanel sendRequestsPanel;
    private CreateTeamPanel createTeamPanel;
    private TeamProjectsPanel teamProjectsPanel;
    private JPanel hackathonProblemPanel;
    private JTextArea problemDescriptionArea;
    private Participant participant;
    private final Home homeFrame;
    private final User user;
    private Hackathon hackathon;

    /**
     * Costruisce la dashboard di gestione del team.
     *
     * @param user L'utente loggato, i cui dati verranno usati per recuperare
     * le informazioni del partecipante.
     * @param homeFrame Un riferimento alla finestra principale {@link Home} per
     * gestire funzionalità globali come il logout.
     */
    public TeamManagementDashboard(User user, Home homeFrame) {
        this.homeFrame = homeFrame;
        this.user = user;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (user != null) {
            this.email = user.getEmail();
            this.participant = controllerGui.getParticipantByEmail(email);
            if (this.participant != null && this.participant.getHackathonID() != null) {
                this.hackathon = controllerGui.getHackathonById(this.participant.getHackathonID());
            }
        }
        initComponents();
    }

    private void initComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        headerPanel.setOpaque(false);

        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.setOpaque(false);

        JLabel titleLabel = new JLabel("Dashboard Partecipante: " + user.getUsername());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleContainer.add(titleLabel);

        if (hackathon != null) {
            JLabel hackathonTitleLabel = new JLabel("Iscritto a: " + hackathon.getTitle());
            hackathonTitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            hackathonTitleLabel.setForeground(new Color(100, 100, 100));
            titleContainer.add(Box.createVerticalStrut(5));
            titleContainer.add(hackathonTitleLabel);
        }

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutButton.setBackground(new Color(220, 53, 69)); // Rosso
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> homeFrame.logout());

        headerPanel.add(titleContainer, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));


        myTeamPanel = new MyTeamPanel(controllerGui, participant);
        teamProjectsPanel = new TeamProjectsPanel(controllerGui, participant);
        createTeamPanel = new CreateTeamPanel(controllerGui, participant, this);
        incomingRequestsPanel = new IncomingRequestsPanel(controllerGui, participant);
        sendRequestsPanel = new SendRequestsPanel(controllerGui, participant);
        hackathonProblemPanel = createHackathonProblemPanel();


        tabbedPane.addTab("Sfida Hackathon", hackathonProblemPanel);
        tabbedPane.addTab("Il Mio Team", myTeamPanel);
        tabbedPane.addTab("Progetti del Team", teamProjectsPanel);
        tabbedPane.addTab("Crea Team", createTeamPanel);
        tabbedPane.addTab("Richieste in Arrivo", incomingRequestsPanel);
        tabbedPane.addTab("Invia Richieste", sendRequestsPanel);

        updateTabStates();

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createHackathonProblemPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Descrizione della Sfida");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        problemDescriptionArea = new JTextArea();
        problemDescriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        problemDescriptionArea.setLineWrap(true);
        problemDescriptionArea.setWrapStyleWord(true);
        problemDescriptionArea.setEditable(false);
        problemDescriptionArea.setMargin(new Insets(10, 10, 10, 10));

        loadProblemDescription();

        JScrollPane scrollPane = new JScrollPane(problemDescriptionArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadProblemDescription() {
        if (hackathon != null && hackathon.getProblemDescription() != null && !hackathon.getProblemDescription().trim().isEmpty()) {
            problemDescriptionArea.setText(hackathon.getProblemDescription());
            problemDescriptionArea.setForeground(Color.BLACK);
        } else {
            problemDescriptionArea.setText("La sfida per questo hackathon non è stata ancora pubblicata.");
            problemDescriptionArea.setForeground(Color.GRAY);
        }
        problemDescriptionArea.setCaretPosition(0);
    }

    private void updateTabStates() {
        tabbedPane.setEnabledAt(0, true);

        if (participant == null || participant.getTeamID() == null) {
            tabbedPane.setEnabledAt(1, false);
            tabbedPane.setEnabledAt(2, false);
            tabbedPane.setEnabledAt(3, true);
            tabbedPane.setEnabledAt(4, false);
            tabbedPane.setEnabledAt(5, false);
            tabbedPane.setSelectedIndex(3);
        } else {
            tabbedPane.setEnabledAt(1, true);
            tabbedPane.setEnabledAt(2, true);
            tabbedPane.setEnabledAt(3, false);
            tabbedPane.setEnabledAt(4, true);
            tabbedPane.setEnabledAt(5, true);
            if (tabbedPane.getSelectedIndex() == 3) {
                tabbedPane.setSelectedIndex(1);
            }
        }
    }

    /**
     * Aggiorna tutti i pannelli contenuti in questa dashboard.
     * <p>
     * Questo metodo ricarica i dati del partecipante e invoca il metodo
     * {@code refreshData()} di ogni pannello figlio per garantire che
     * l'intera interfaccia sia sincronizzata con lo stato corrente del database.
     */
    public void refreshAllPanels() {
        System.out.println("Refresh di tutti i pannelli...");
        if (email != null) {
            this.participant = controllerGui.getParticipantByEmail(email);
            if(this.participant != null && this.participant.getHackathonID() != null) {
                this.hackathon = controllerGui.getHackathonById(this.participant.getHackathonID());
            }
        }

        myTeamPanel.refreshData();
        createTeamPanel.refreshData();
        incomingRequestsPanel.refreshData();
        sendRequestsPanel.refreshData();
        teamProjectsPanel.refreshData();
        loadProblemDescription();
        if (participant != null) {
            updateTabStates();
        }
    }


    /**
     * Cambia la scheda (tab) visualizzata.
     *
     * @param tabIndex L'indice della scheda da visualizzare.
     */
    public void switchToTab(int tabIndex) {
        if (tabIndex >= 0 && tabIndex < tabbedPane.getTabCount()) {
            if (tabbedPane.isEnabledAt(tabIndex)) {
                tabbedPane.setSelectedIndex(tabIndex);
            }
        }
    }
}