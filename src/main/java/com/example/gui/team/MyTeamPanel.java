package com.example.gui.team;

import com.example.controller.ControllerGui;
import com.example.model.Hackathon;
import com.example.model.Participant;
import com.example.model.Team;
import com.example.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Pannello che visualizza i dettagli del team di appartenenza del partecipante.
 * <p>
 * Questa classe mostra il nome del team, il numero di membri e una lista
 * dettagliata di tutti i partecipanti che ne fanno parte. Se l'utente non
 * appartiene a nessun team, viene mostrato un messaggio appropriato.
 *

 * @see TeamManagementDashboard
 * @see Participant
 * @see Team
 */
public class MyTeamPanel extends JPanel {

    private String userEmail;
    private JLabel teamNameLabel;
    private JLabel memberCountLabel;
    private JPanel membersPanel;
    private JScrollPane membersScrollPane;
    private JButton leaveTeamButton;
    private JButton inviteMemberButton;
    private final ControllerGui controller;
    private Participant participant;
    private final Hackathon hackathon;

    /**
     * Costruisce il pannello "Il Mio Team".
     *
     * @param controller Il controller per le operazioni di business.
     * @param participant I dati del partecipante attualmente loggato.
     */
    public MyTeamPanel(ControllerGui controller, Participant participant) {
        this.controller = controller;
        this.participant = participant;
        this.hackathon = controller.getHackathonById(participant.getHackathonID());

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();

        if (participant != null) {
            this.userEmail = participant.getEmail();
            loadTeamData();
        }
    }

    private void initComponents() {
        JPanel headerPanel = createTeamInfoPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel membersMainPanel = createMembersPanel();
        add(membersMainPanel, BorderLayout.CENTER);
    }

    /**
     * Carica e visualizza i dati del team a cui il partecipante appartiene.
     * <p>
     * Se il partecipante non è in un team, mostra un messaggio informativo.
     * Altrimenti, recupera i dettagli del team e la lista dei suoi membri,
     * creando una card grafica per ogni membro.
     */
    private void loadTeamData() {
        membersPanel.removeAll();
        if (participant == null || participant.getTeamID() == null) {
            displayNoTeamMessage();
            return;
        }
        Team team = controller.getTeamById(participant.getTeamID());
        if (team == null) {
            displayNoTeamMessage();
            return;
        }
        List<Participant> members = controller.findByTeamId(team.getId());

        int memberCount = members.size();
        teamNameLabel.setText("Nome Team: " + team.getName());
        memberCountLabel.setText("Membri: " + memberCount + "/" + hackathon.getMaxTeamSize());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridy = 0;

        for (Participant m : members) {

            User memberUser = controller.getUserByEmail(m.getEmail());
            if (memberUser != null) {
                JPanel memberCard = createMemberCard(
                        memberUser.getFirst_name() + " " + memberUser.getLast_name(),
                        m.getEmail(),
                        m.getEmail().equals(this.userEmail));
                membersPanel.add(memberCard, gbc);
                gbc.gridy++;
            }
        }

        gbc.weighty = 1.0;
        membersPanel.add(Box.createVerticalGlue(), gbc);
        membersPanel.revalidate();
        membersPanel.repaint();
    }

    private JPanel createTeamInfoPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        headerPanel.setBackground(new Color(248, 249, 250));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(248, 249, 250));


        teamNameLabel = new JLabel("Nome Team: Caricamento...");
        teamNameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        teamNameLabel.setForeground(new Color(33, 37, 41));

        memberCountLabel = new JLabel("Membri: Caricamento...");
        memberCountLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        memberCountLabel.setForeground(new Color(40, 167, 69));

        infoPanel.add(teamNameLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(memberCountLabel);

        headerPanel.add(infoPanel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createMembersPanel() {
        JPanel membersMainPanel = new JPanel(new BorderLayout());
        membersMainPanel.setBorder(BorderFactory.createTitledBorder("Membri del Team"));
        membersPanel = new JPanel(new GridBagLayout());
        membersPanel.setBackground(Color.WHITE);
        membersScrollPane = new JScrollPane(membersPanel);
        membersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        membersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        membersScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        membersMainPanel.add(membersScrollPane, BorderLayout.CENTER);
        return membersMainPanel;
    }

    private void displayNoTeamMessage() {
        teamNameLabel.setText("Nessun Team");
        memberCountLabel.setText("Membri: 0/0");
        JLabel noTeamLabel = new JLabel("Non sei ancora in un team.");
        noTeamLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        noTeamLabel.setForeground(new Color(33, 37, 41));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        membersPanel.add(noTeamLabel, gbc);
        membersPanel.revalidate();
        membersPanel.repaint();
    }

    private JPanel createMemberCard(String memberName, String memberEmail, boolean isCurrentUser) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(0, 80));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(memberName + (isCurrentUser ? " (Tu)" : ""));
        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        nameLabel.setForeground(new Color(33, 37, 41));

        JLabel emailLabel = new JLabel(memberEmail);
        emailLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        emailLabel.setForeground(new Color(108, 117, 125));

        leftPanel.add(nameLabel);
        leftPanel.add(emailLabel);

        card.add(leftPanel, BorderLayout.CENTER);

        if (!isCurrentUser) {
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
            rightPanel.setBackground(Color.WHITE);


            card.add(rightPanel, BorderLayout.EAST);
        }

        return card;
    }

    /**
     * Aggiorna e ricarica i dati del team visualizzati nel pannello.
     * <p>
     * Questo metodo viene chiamato per assicurarsi che le informazioni mostrate
     * siano sempre sincronizzate con lo stato attuale del database.
     */
    public void refreshData() {
        if (this.userEmail != null) {
            this.participant = controller.getParticipantByEmail(this.userEmail);
        }
        loadTeamData();
    }
}