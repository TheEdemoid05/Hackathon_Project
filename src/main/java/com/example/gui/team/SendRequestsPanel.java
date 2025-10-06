package com.example.gui.team;

import com.example.controller.ControllerGui;
import com.example.model.Participant;
import com.example.model.Request;
import com.example.model.Team;
import com.example.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Pannello per l'invio e la visualizzazione delle richieste di partecipazione a un team.
 * <p>
 * Questa classe fornisce un'interfaccia a schede (tab) che permette a un
 * partecipante di un team di:
 * </p>
 * <ol>
 * <li><b>Invitare nel Team:</b> Visualizzare una lista di partecipanti idonei.</li>
 * <li><b>Richieste Inviate:</b> Controllare lo stato (In attesa, Accettata, Rifiutata)
 * di tutte le richieste che ha inviato.</li>
 * </ol>
 * <p>
 * Il pannello gestisce la logica per filtrare gli utenti che possono essere invitati
 * e per visualizzare lo stato aggiornato delle richieste inviate.

 * @see TeamManagementDashboard
 * @see Request
 * @see ControllerGui
 */
public class SendRequestsPanel extends JPanel {

    private JPanel resultsPanel;
    private JScrollPane resultsScrollPane;
    private final ControllerGui controller;
    private final Participant currentUserParticipant;

    private JPanel sentRequestsPanel;
    private JScrollPane sentRequestsScrollPane;
    private JTabbedPane mainTabbedPane;

    /**
     * Costruisce il pannello per l'invio delle richieste.
     *
     * @param controller Il controller per le operazioni di business.
     * @param currentUserParticipant I dati del partecipante loggato che invia le richieste.
     */
    public SendRequestsPanel(ControllerGui controller, Participant currentUserParticipant) {
        this.controller = controller;
        this.currentUserParticipant = currentUserParticipant;
        this.setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
        refreshData();
    }

    private void initComponents() {
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel searchTab = createSearchTab();
        mainTabbedPane.addTab("Invita nel Team", searchTab);

        JPanel sentTab = createSentRequestsTab();
        mainTabbedPane.addTab("Richieste Inviate", sentTab);

        add(mainTabbedPane, BorderLayout.CENTER);
    }

    private JPanel createSearchTab() {
        JPanel searchTab = new JPanel(new BorderLayout());
        searchTab.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchTab.add(createResultsPanel(), BorderLayout.CENTER);
        return searchTab;
    }

    private JPanel createResultsPanel() {
        JPanel resultsMainPanel = new JPanel(new BorderLayout());
        resultsMainPanel.setBorder(BorderFactory.createTitledBorder("Elenco Partecipanti dell'Hackathon"));
        resultsPanel = new JPanel(new GridBagLayout());
        resultsPanel.setBackground(Color.WHITE);
        resultsScrollPane = new JScrollPane(resultsPanel);
        resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        resultsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        resultsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        resultsMainPanel.add(resultsScrollPane, BorderLayout.CENTER);
        return resultsMainPanel;
    }

    private JPanel createSentRequestsTab() {
        JPanel sentTab = new JPanel(new BorderLayout());
        sentTab.setBorder(new EmptyBorder(10, 10, 10, 10));
        sentRequestsPanel = new JPanel(new GridBagLayout());
        sentRequestsPanel.setBackground(Color.WHITE);
        sentRequestsScrollPane = new JScrollPane(sentRequestsPanel);
        sentRequestsScrollPane.setBorder(BorderFactory.createTitledBorder("Stato delle Richieste Inviate"));
        sentRequestsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sentTab.add(sentRequestsScrollPane, BorderLayout.CENTER);
        return sentTab;
    }

    private void loadParticipants() {
        resultsPanel.removeAll();
        if (currentUserParticipant == null || currentUserParticipant.getHackathonID() == null) {
            showNoResultsMessage("Dati utente o hackathon non disponibili.");
            return;
        }
        List<Participant> allParticipants = controller.getParticipantsByHackathon(currentUserParticipant.getHackathonID());
        List<Request> sentRequests = controller.findSentRequests(currentUserParticipant.getEmail());
        List<String> recipients = sentRequests.stream().map(Request::getReceiver_participant_email).collect(Collectors.toList());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridy = 0;

        boolean hasResults = false;
        for (Participant p : allParticipants) {

            if (p.getEmail().equals(currentUserParticipant.getEmail())) {
                continue;
            }


            if (currentUserParticipant.getTeamID() != null && currentUserParticipant.getTeamID().equals(p.getTeamID())) {
                continue;
            }


            if (recipients.contains(p.getEmail())) {
                continue;
            }

            User user = controller.getUserByEmail(p.getEmail());
            if (user != null) {
                JPanel userCard = createUserResultCard(user, p);
                resultsPanel.add(userCard, gbc);
                gbc.gridy++;
                hasResults = true;
            }
        }

        if (!hasResults) {
            showNoResultsMessage("Nessun altro partecipante disponibile per l'invito.");
        } else {
            gbc.weighty = 1.0;
            resultsPanel.add(Box.createVerticalGlue(), gbc);
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private JPanel createUserResultCard(User user, Participant participant) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                new EmptyBorder(20, 20, 20, 20)));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(0, 120));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(user.getFirst_name() + " " + user.getLast_name());
        nameLabel.setFont(new Font("Font.SANS_SERIF", Font.BOLD, 16));
        nameLabel.setForeground(new Color(33, 37, 41));

        JLabel emailLabel = new JLabel("Email: " + user.getEmail());
        emailLabel.setFont(new Font("Font.SANS_SERIF", Font.PLAIN, 12));
        emailLabel.setForeground(new Color(108, 117, 125));

        leftPanel.add(nameLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(emailLabel);
        leftPanel.add(Box.createVerticalStrut(8));

        JLabel teamLabel;
        boolean canBeInvited = (participant.getTeamID() != null);

        if (canBeInvited) {
            Team team = controller.getTeamById(participant.getTeamID());
            teamLabel = new JLabel("👥 Team: " + (team != null ? team.getName() : "Sconosciuto"));
            teamLabel.setForeground(new Color(0, 123, 255));
        } else {
            teamLabel = new JLabel("Nessun team");
            teamLabel.setForeground(Color.GRAY);
        }
        teamLabel.setFont(new Font("Font.SANS_SERIF", Font.ITALIC, 12));
        leftPanel.add(teamLabel);

        card.add(leftPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rightPanel.setBackground(Color.WHITE);
        JButton sendRequestButton = new JButton("Invia Richiesta");
        sendRequestButton.setPreferredSize(new Dimension(140, 35));
        sendRequestButton.setFont(new Font("Font.SANS_SERIF", Font.BOLD, 12));
        sendRequestButton.setFocusPainted(false);

        if (canBeInvited) {
            sendRequestButton.setBackground(new Color(0, 123, 255));
            sendRequestButton.setForeground(Color.WHITE);
            sendRequestButton.addActionListener(e -> showSendRequestDialog(user, card));
        } else {
            sendRequestButton.setBackground(Color.LIGHT_GRAY);
            sendRequestButton.setForeground(Color.DARK_GRAY);
            sendRequestButton.setEnabled(false);
        }

        rightPanel.add(sendRequestButton);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    private void showSendRequestDialog(User recipient, Component cardComponent) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Invia Richiesta a " + recipient.getUsername(), true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(new EmptyBorder(15, 15, 10, 15));
        JLabel titleLabel = new JLabel("Invia richiesta di ingresso nel team");
        titleLabel.setFont(new Font("Font.SANS_SERIF", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);
        JPanel messagePanel = new JPanel(new BorderLayout(10, 10));
        messagePanel.setBorder(new EmptyBorder(0, 15, 10, 15));
        JLabel messageLabel = new JLabel("Messaggio motivazionale:");
        messageLabel.setFont(new Font("Font.SANS_SERIF", Font.BOLD, 14));
        JTextArea messageArea = new JTextArea(8, 30);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("Font.SANS_SERIF", Font.PLAIN, 14));
        messageArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 5, 5, 5)));
        messageArea.setText("Ciao " + recipient.getUsername() + ",\n\nSono interessato/a a unirmi al tuo team per questo hackathon. ");
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messagePanel.add(messageLabel, BorderLayout.NORTH);
        messagePanel.add(messageScrollPane, BorderLayout.CENTER);
        dialog.add(messagePanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(0, 15, 15, 15));
        JButton cancelButton = new JButton("Annulla");
        cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.addActionListener(e -> dialog.dispose());
        JButton sendButton = new JButton("Invia");
        sendButton.setPreferredSize(new Dimension(100, 35));
        sendButton.setBackground(new Color(0, 123, 255));
        sendButton.setForeground(Color.WHITE);

        sendButton.addActionListener(e -> {
            String message = messageArea.getText().trim();
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Il messaggio non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Integer teamId = currentUserParticipant.getTeamID();
            if (teamId == null) {
                JOptionPane.showMessageDialog(dialog, "Errore: Non fai parte di nessun team per poter invitare.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Request newRequest = new Request(message, "PENDING",
                    teamId,
                    currentUserParticipant.getEmail(), recipient.getEmail());

            boolean success = controller.saveRequest(newRequest);
            if (success) {
                JOptionPane.showMessageDialog(dialog, "Richiesta inviata con successo a " + recipient.getUsername() + "!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                refreshData();
            } else {
                JOptionPane.showMessageDialog(dialog, "Errore durante l'invio della richiesta.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(sendButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showNoResultsMessage(String message) {
        resultsPanel.removeAll();
        JLabel noResultsLabel = new JLabel(message);
        noResultsLabel.setFont(new Font("Font.SANS_SERIF", Font.ITALIC, 14));
        noResultsLabel.setForeground(new Color(108, 117, 125));
        noResultsLabel.setHorizontalAlignment(JLabel.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        resultsPanel.add(noResultsLabel, gbc);
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void loadSentRequestsData() {
        sentRequestsPanel.removeAll();
        if (currentUserParticipant == null) return;
        List<Request> sentRequests = controller.findSentRequests(currentUserParticipant.getEmail());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridy = 0;
        if (sentRequests.isEmpty()) {
            JLabel noRequestsLabel = new JLabel("Nessuna richiesta inviata.");
            noRequestsLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            sentRequestsPanel.add(noRequestsLabel);
        } else {
            for (Request request : sentRequests) {
                User recipient = controller.getUserByEmail(request.getReceiver_participant_email());
                if (recipient != null) {
                    JPanel card = createSentRequestCard(recipient.getUsername(), recipient.getEmail(), request.getMessage(), request.getStatus());
                    sentRequestsPanel.add(card, gbc);
                    gbc.gridy++;
                }
            }
        }
        gbc.weighty = 1.0;
        sentRequestsPanel.add(Box.createVerticalGlue(), gbc);
        sentRequestsPanel.revalidate();
        sentRequestsPanel.repaint();
    }

    private JPanel createSentRequestCard(String recipientName, String recipientEmail, String message, String status) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        JLabel nameLabel = new JLabel("A: " + recipientName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoPanel.add(nameLabel);
        JLabel emailLabel = new JLabel(recipientEmail);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        emailLabel.setForeground(Color.GRAY);
        infoPanel.add(emailLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        JTextArea messageArea = new JTextArea(message);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        messageArea.setEditable(false);
        messageArea.setOpaque(false);
        infoPanel.add(messageArea);
        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setOpaque(true);
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        statusLabel.setForeground(Color.WHITE);
        switch (status.toUpperCase()) {
            case "ACCEPTED":
                statusLabel.setText("ACCETTATA");
                statusLabel.setBackground(new Color(40, 167, 69));
                break;
            case "REJECTED":
                statusLabel.setText("RIFIUTATA");
                statusLabel.setBackground(new Color(220, 53, 69));
                break;
            default:
                statusLabel.setText("IN ATTESA");
                statusLabel.setBackground(new Color(255, 193, 7));
                statusLabel.setForeground(Color.BLACK);
                break;
        }
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(statusLabel, BorderLayout.EAST);
        return card;
    }

    /**
     * Aggiorna entrambi i pannelli (ricerca e richieste inviate) per
     * riflettere lo stato più recente del database.
     */
    public void refreshData() {
        loadParticipants();
        loadSentRequestsData();
    }
}