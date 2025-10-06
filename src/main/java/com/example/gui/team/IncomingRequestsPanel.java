package com.example.gui.team;

import com.example.controller.ControllerGui;
import com.example.model.Participant;
import com.example.model.Request;
import com.example.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Pannello per la visualizzazione e la gestione delle richieste di ingresso nel team.
 * <p>
 * Questa classe mostra al leader di un team la lista delle richieste pendenti
 * inviate da altri partecipanti che desiderano unirsi al suo team.
 * Fornisce i controlli per accettare o rifiutare ciascuna richiesta.

 * @see TeamManagementDashboard
 * @see Request
 * @see ControllerGui
 */
public class IncomingRequestsPanel extends JPanel {

    private JPanel requestsPanel;
    private JScrollPane requestsScrollPane;
    private JLabel statusLabel;
    private final ControllerGui controller;
    private final Participant participant;

    /**
     * Costruisce il pannello delle richieste in arrivo.
     *
     * @param controller Il controller per le operazioni di business.
     * @param participant I dati del partecipante loggato (che è il leader del team).
     */
    public IncomingRequestsPanel(ControllerGui controller, Participant participant) {
        this.controller = controller;
        this.participant = participant;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
        loadRequestsData();
    }

    private void initComponents() {
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        JPanel requestsMainPanel = createRequestsPanel();
        add(requestsMainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                new EmptyBorder(15, 20, 15, 20)));
        headerPanel.setBackground(new Color(248, 249, 250));
        JLabel titleLabel = new JLabel("Richieste di Ingresso nel Team");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        titleLabel.setForeground(new Color(33, 37, 41));
        statusLabel = new JLabel("Richieste pendenti: Caricamento...");
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        statusLabel.setForeground(new Color(108, 117, 125));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(248, 249, 250));
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(statusLabel);
        headerPanel.add(leftPanel, BorderLayout.CENTER);
        JButton refreshButton = new JButton("Aggiorna");
        refreshButton.setPreferredSize(new Dimension(100, 35));
        refreshButton.setBackground(new Color(108, 117, 125));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        refreshButton.addActionListener(e -> refreshData());
        headerPanel.add(refreshButton, BorderLayout.EAST);
        return headerPanel;
    }

    private JPanel createRequestsPanel() {
        JPanel requestsMainPanel = new JPanel(new BorderLayout());
        requestsMainPanel.setBorder(BorderFactory.createTitledBorder("Richieste da Altri Partecipanti"));
        requestsPanel = new JPanel(new GridBagLayout());
        requestsPanel.setBackground(Color.WHITE);
        requestsScrollPane = new JScrollPane(requestsPanel);
        requestsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        requestsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        requestsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        requestsMainPanel.add(requestsScrollPane, BorderLayout.CENTER);
        return requestsMainPanel;
    }


    private JPanel createRequestCard(Request request) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                new EmptyBorder(20, 20, 20, 20)));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(0, 180));

        User sender = controller.getUserByEmail(request.getSender_participant_email());
        String requesterName = (sender != null) ? sender.getUsername() : "Utente Sconosciuto";

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel("Da: " + requesterName);
        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        nameLabel.setForeground(new Color(33, 37, 41));
        JLabel emailLabel = new JLabel("Email: " + request.getSender_participant_email());
        emailLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        emailLabel.setForeground(new Color(108, 117, 125));
        JTextArea messageArea = new JTextArea(request.getMessage());
        messageArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        messageArea.setForeground(new Color(33, 37, 41));
        messageArea.setBackground(new Color(248, 249, 250));
        messageArea.setBorder(BorderFactory.createTitledBorder("Messaggio"));
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setRows(2);
        leftPanel.add(nameLabel);
        leftPanel.add(Box.createVerticalStrut(2));
        leftPanel.add(emailLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(messageArea);
        card.add(leftPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        JButton acceptButton = new JButton("Accetta");
        acceptButton.setPreferredSize(new Dimension(100, 35));
        acceptButton.setMaximumSize(new Dimension(100, 35));
        acceptButton.setBackground(new Color(40, 167, 69));
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setFocusPainted(false);
        acceptButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        JButton rejectButton = new JButton("Rifiuta");
        rejectButton.setPreferredSize(new Dimension(100, 35));
        rejectButton.setMaximumSize(new Dimension(100, 35));
        rejectButton.setBackground(new Color(220, 53, 69));
        rejectButton.setForeground(Color.WHITE);
        rejectButton.setFocusPainted(false);
        rejectButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));


        acceptButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Sei sicuro di voler accettare la richiesta di " + requesterName + "?",
                    "Conferma Accettazione", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {

                controller.assignParticipantToTeam(request.getReceiver_participant_email(), controller.getParticipantByEmail(request.getSender_participant_email()).getTeamID());

                request.setStatus("ACCEPTED");
                controller.updateRequest(request);
                JOptionPane.showMessageDialog(this, requesterName + " Adesso fai parte di un altro Team", "Successo", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
            }
        });


        rejectButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Sei sicuro di voler rifiutare la richiesta di " + requesterName + "?",
                    "Conferma Rifiuto", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                request.setStatus("REJECTED");
                controller.updateRequest(request);
                JOptionPane.showMessageDialog(this, "Richiesta rifiutata.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
            }
        });

        rightPanel.add(acceptButton);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(rejectButton);
        rightPanel.add(Box.createVerticalGlue());
        card.add(rightPanel, BorderLayout.EAST);
        return card;
    }

    private void loadRequestsData() {
        requestsPanel.removeAll();
        if (participant == null) {
            statusLabel.setText("Dati del partecipante non disponibili.");
            requestsPanel.add(new JLabel("Impossibile caricare le richieste."));
            requestsPanel.revalidate();
            requestsPanel.repaint();
            return;
        }

        List<Request> requests = controller.findRequestsForUser(participant.getEmail());
        statusLabel.setText("Richieste pendenti: " + requests.size());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridy = 0;

        if (requests.isEmpty()) {
            JLabel noRequestsLabel = new JLabel("Nessuna richiesta per entrare nel tuo team.");
            noRequestsLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
            noRequestsLabel.setForeground(new Color(108, 117, 125));
            requestsPanel.add(noRequestsLabel);
        } else {
            for (Request request : requests) {

                if ("PENDING".equalsIgnoreCase(request.getStatus())) {
                    JPanel requestCard = createRequestCard(request);
                    requestsPanel.add(requestCard, gbc);
                    gbc.gridy++;
                }
            }
            gbc.weighty = 1.0;
            requestsPanel.add(Box.createVerticalGlue(), gbc);
        }
        requestsPanel.revalidate();
        requestsPanel.repaint();
    }

    /**
     * Ricarica la lista delle richieste dal database e aggiorna la UI.
     */
    public void refreshData() {
        loadRequestsData();
    }
}