package com.example.gui;

import com.example.controller.ControllerGui;
import com.example.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Pannello principale per la dashboard dell'Organizzatore.
 * <p>
 * Questa classe fornisce l'interfaccia utente (GUI) per tutte le funzionalità
 * a disposizione di un organizzatore. È suddivisa in due sezioni principali:
 * </p>
 * <ul>
 * <li><b>Creazione Hackathon:</b> Un modulo per inserire i dettagli e creare nuovi eventi.</li>
 * <li><b>Gestione Hackathon:</b> Una lista di hackathon esistenti con opzioni per
 * gestire i giudici, aprire le iscrizioni, eliminare eventi e visualizzare le classifiche finali.</li>
 * </ul>
 * <p>
 * La dashboard interagisce con il {@link ControllerGui} per tutte le operazioni di
 * backend, come il salvataggio dei dati e la gestione delle promozioni.
 * @see com.example.model.Organizer
 * @see com.example.model.Hackathon
 */
public class OrganizerDashboard extends JPanel {

    private static final Color PRIMARY_COLOR = new Color(44, 62, 80);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color WARNING_COLOR = new Color(243, 156, 18);
    private static final Color LIGHT_GRAY = new Color(245, 245, 245);
    private static final Color CARD_BACKGROUND = new Color(255, 255, 255);
    private static final Color JUDGE_COLOR = new Color(142, 68, 173);
    private final ControllerGui controller = new ControllerGui();
    private final Home homeFrame;
    private JTextField hackathonNameField;
    private JTextField locationField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField maxParticipantsField;
    private JTextField maxTeamSizeField;
    private JButton createHackathonButton;
    private JPanel hackathonListPanel;
    private JScrollPane hackathonScrollPane;
    private JButton refreshButton;
    private String email;

    /**
     * Costruisce la dashboard per un organizzatore.
     *
     * @param homeFrame Un riferimento alla finestra principale {@link Home} per gestire
     * funzionalità globali come il logout.
     */
    public OrganizerDashboard(Home homeFrame) {
        this.homeFrame = homeFrame;
        setLayout(new BorderLayout(15, 15));
        setBackground(LIGHT_GRAY);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        initComponents();
        addListeners();
        refreshHackathonList();
    }

    private void initComponents() {
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        mainPanel.setOpaque(false);
        JPanel creationSection = createCreationSection();
        mainPanel.add(creationSection);
        JPanel managementSection = createManagementSection();
        mainPanel.add(managementSection);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("Hackathon Organizer Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        refreshButton = new JButton("Aggiorna");
        refreshButton.setFocusPainted(false);
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        JButton logoutButton = new JButton("Logout");
        logoutButton.setFocusPainted(false);
        logoutButton.setBackground(DANGER_COLOR);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> homeFrame.logout());

        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        return headerPanel;
    }


    private JPanel createCreationSection() {
        JPanel section = new JPanel(new BorderLayout(0, 15));
        section.setOpaque(false);
        JLabel sectionTitle = new JLabel(" Crea Nuovo Hackathon");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(PRIMARY_COLOR);
        section.add(sectionTitle, BorderLayout.NORTH);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(CARD_BACKGROUND);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(25, 25, 25, 25)));
        formPanel.add(createFormField("Nome Hackathon", hackathonNameField = createStyledTextField()));
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(createFormField("Località", locationField = createStyledTextField()));
        formPanel.add(Box.createVerticalStrut(5));
        JPanel datePanel = new JPanel(new GridLayout(1, 2, 15, 0));
        datePanel.setOpaque(false);
        JPanel startDatePanel = createFormField("Data Inizio (YYYY-MM-DD HH:mm)",
                startDateField = createStyledTextField());
        JPanel endDatePanel = createFormField("Data Fine (YYYY-MM-DD HH:mm)",
                endDateField = createStyledTextField());
        datePanel.add(startDatePanel);
        datePanel.add(endDatePanel);
        formPanel.add(datePanel);
        formPanel.add(Box.createVerticalStrut(15));
        JPanel participantsPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        participantsPanel.setOpaque(false);
        JPanel maxParticipantsPanel = createFormField("Max Partecipanti",
                maxParticipantsField = createStyledTextField());
        JPanel maxTeamSizePanel = createFormField("Dimensione Max Team",
                maxTeamSizeField = createStyledTextField());
        participantsPanel.add(maxParticipantsPanel);
        participantsPanel.add(maxTeamSizePanel);
        formPanel.add(participantsPanel);
        formPanel.add(Box.createVerticalStrut(25));
        createHackathonButton = new JButton("Crea Hackathon");
        styleButton(createHackathonButton, SUCCESS_COLOR);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(createHackathonButton);
        formPanel.add(buttonPanel);
        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setBorder(null);
        formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        formScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        section.add(formScrollPane, BorderLayout.CENTER);
        return section;
    }

    private JPanel createManagementSection() {
        JPanel section = new JPanel(new BorderLayout(0, 15));
        section.setOpaque(false);
        JLabel sectionTitle = new JLabel(" Gestisci Hackathon Esistenti");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(PRIMARY_COLOR);
        section.add(sectionTitle, BorderLayout.NORTH);
        hackathonListPanel = new JPanel();
        hackathonListPanel.setLayout(new BoxLayout(hackathonListPanel, BoxLayout.Y_AXIS));
        hackathonListPanel.setBackground(LIGHT_GRAY);
        hackathonScrollPane = new JScrollPane(hackathonListPanel);
        hackathonScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        hackathonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        hackathonScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        hackathonScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        section.add(hackathonScrollPane, BorderLayout.CENTER);
        return section;
    }

    private JPanel createFormField(String labelText, JTextField textField) {
        JPanel fieldPanel = new JPanel(new BorderLayout(0, 8));
        fieldPanel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(PRIMARY_COLOR);
        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(textField, BorderLayout.CENTER);
        return fieldPanel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(10, 10, 10, 10)));
        field.setBackground(new Color(250, 250, 250));
        return field;
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(12, 25, 12, 25));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            final Color originalColor = backgroundColor;

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }

    private JPanel createHackathonCard(Hackathon hackathon, String status) {
        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        JPanel infoPanel = new JPanel(new BorderLayout(0, 8));
        infoPanel.setOpaque(false);
        JLabel nameLabel = new JLabel(hackathon.getTitle());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(PRIMARY_COLOR);
        JPanel detailInfoPanel = new JPanel(new BorderLayout(0, 3));
        detailInfoPanel.setOpaque(false);
        JLabel locationLabel = new JLabel(hackathon.getLocation());
        locationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        locationLabel.setForeground(new Color(120, 120, 120));
        JLabel hackathonLabel = new JLabel(hackathon.getStartDate().toLocalDate()
                + " → " + hackathon.getEndDate().toLocalDate());
        hackathonLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hackathonLabel.setForeground(new Color(120, 120, 120));
        JLabel participantsLabel = new JLabel(hackathon.getMaxParticipants()
                + " partecipanti | Team max " + hackathon.getMaxTeamSize());
        participantsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        participantsLabel.setForeground(SECONDARY_COLOR);
        detailInfoPanel.add(locationLabel, BorderLayout.NORTH);
        detailInfoPanel.add(hackathonLabel, BorderLayout.CENTER);
        detailInfoPanel.add(participantsLabel, BorderLayout.SOUTH);
        infoPanel.add(nameLabel, BorderLayout.NORTH);
        infoPanel.add(detailInfoPanel, BorderLayout.SOUTH);
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setOpaque(false);
        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBorder(new EmptyBorder(4, 12, 4, 12));
        if (status.equals("Attivo")) {
            statusLabel.setBackground(SUCCESS_COLOR);
            statusLabel.setForeground(Color.WHITE);
        } else if (status.equals("Terminato")) {
            statusLabel.setBackground(new Color(149, 165, 166));
            statusLabel.setForeground(Color.WHITE);
        } else {
            statusLabel.setBackground(SECONDARY_COLOR);
            statusLabel.setForeground(Color.WHITE);
        }
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 0, 8));
        buttonPanel.setOpaque(false);

        JButton manageButton = new JButton("Gestisci");
        styleButton(manageButton, SECONDARY_COLOR);
        manageButton.addActionListener(e -> showManagementDialog(hackathon.getId(), hackathon.getTitle()));
        buttonPanel.add(manageButton);

        JButton rankingsButton = new JButton("Classifica");
        styleButton(rankingsButton, new Color(23, 162, 184)); // Teal color
        rankingsButton.addActionListener(e -> showRankingsDialog(hackathon.getId(), hackathon.getTitle()));
        buttonPanel.add(rankingsButton);


        if (status.equals("Attivo")
                && hackathon.getRegistrationStart() == null
                && hackathon.getRegistrationEnd() == null) {
            JButton openRegistrationButton = new JButton("Apri Iscrizioni");
            styleButton(openRegistrationButton, WARNING_COLOR);
            openRegistrationButton.addActionListener(e -> {
                openRegistrations(hackathon.getTitle());
                refreshHackathonList();
            });
            buttonPanel.add(openRegistrationButton);
        }
        if (status.equals("Non iniziato") || status.equals("Terminato")) {
            JButton deleteButton = new JButton("Elimina");
            styleButton(deleteButton, DANGER_COLOR);
            deleteButton.addActionListener(e -> {
                deleteHackathon(hackathon.getTitle());
                refreshHackathonList();
            });
            buttonPanel.add(deleteButton);
        }
        rightPanel.add(statusLabel, BorderLayout.NORTH);
        rightPanel.add(buttonPanel, BorderLayout.CENTER);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);
        return card;
    }

    private void refreshHackathonList() {
        hackathonListPanel.removeAll();
        List<Hackathon> hackathons = controller.getAllHackathons();
        if (hackathons.isEmpty()) {
            JLabel noHackathonsLabel = new JLabel("Nessun hackathon da visualizzare.");
            noHackathonsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            noHackathonsLabel.setForeground(PRIMARY_COLOR);
            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setOpaque(false);
            centerPanel.add(noHackathonsLabel);
            hackathonListPanel.add(centerPanel);
        } else {
            for (Hackathon hackathon : hackathons) {
                String status;
                LocalDateTime now = LocalDateTime.now();
                if (now.isBefore(hackathon.getStartDate())) {
                    status = "Non iniziato";
                } else if (now.isAfter(hackathon.getEndDate())) {
                    status = "Terminato";
                } else {
                    status = "Attivo";
                }
                hackathonListPanel.add(createHackathonCard(hackathon, status));
                hackathonListPanel.add(Box.createVerticalStrut(15));
            }
        }
        hackathonListPanel.add(Box.createVerticalGlue());
        hackathonListPanel.revalidate();
        hackathonListPanel.repaint();
    }

    private void addListeners() {
        createHackathonButton.addActionListener(e -> createHackathon());
        refreshButton.addActionListener(e -> refreshHackathonList());
    }

    private void createHackathon() {
        String title = hackathonNameField.getText().trim();
        String location = locationField.getText().trim();
        String startDateStr = startDateField.getText().trim();
        String endDateStr = endDateField.getText().trim();
        String maxParticipantsStr = maxParticipantsField.getText().trim();
        String maxTeamSizeStr = maxTeamSizeField.getText().trim();
        if (title.isEmpty() || location.isEmpty() || startDateStr.isEmpty()
                || endDateStr.isEmpty() || maxParticipantsStr.isEmpty() || maxTeamSizeStr.isEmpty()) {
            showStyledMessage("Errore", "Per favore compila tutti i campi.", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(startDateStr, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endDateStr, formatter);
            if (endDateTime.isBefore(startDateTime)) {
                showStyledMessage("Errore", "La data di fine non può essere precedente alla data di inizio.",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int maxParticipants = Integer.parseInt(maxParticipantsStr);
            int maxTeamSize = Integer.parseInt(maxTeamSizeStr);
            if (maxParticipants <= 0 || maxTeamSize <= 0) {
                showStyledMessage("Errore",
                        "Il numero massimo di partecipanti e la dimensione massima del team devono essere numeri positivi.",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Hackathon hackathon = new Hackathon(
                    title,
                    location,
                    startDateTime,
                    endDateTime,
                    maxParticipants,
                    maxTeamSize,
                    null,
                    null,
                    email);
            hackathon.setRegistrationEnd(null);
            controller.createHackathon(hackathon);
            showStyledMessage("Successo", "Hackathon '" + title + "' creato con successo!",
                    JOptionPane.INFORMATION_MESSAGE);
            hackathonNameField.setText("");
            locationField.setText("");
            startDateField.setText("");
            endDateField.setText("");
            maxParticipantsField.setText("");
            maxTeamSizeField.setText("");
            refreshHackathonList();
        } catch (java.time.format.DateTimeParseException e) {
            showStyledMessage("Errore di Formato Data", "Il formato della data e ora deve essere 'YYYY-MM-DD HH:MM'.",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            showStyledMessage("Errore di Formato Numero",
                    "Il numero massimo di partecipanti e la dimensione massima del team devono essere numeri validi.",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            showStyledMessage("Errore di Creazione",
                    "Si è verificato un errore durante la creazione dell'hackathon: " + e.getMessage(),
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void openRegistrations(String hackathonName) {
        Hackathon hackathon = controller.getHackathonByTitle(hackathonName).orElse(null);

        if (hackathon == null) {
            showStyledMessage("Errore", "Hackathon non trovato.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        hackathon.setRegistrationStart(LocalDateTime.now());
        hackathon.setRegistrationEnd(LocalDateTime.now().plusDays(3));
        controller.updateHackathon(hackathon);
        showStyledMessage("Successo", "Iscrizioni aperte per '" + hackathonName + "'!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteHackathon(String hackathonName) {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Sei sicuro di voler eliminare l'hackathon '" + hackathonName + "'?",
                "Conferma Eliminazione",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            controller.getHackathonByTitle(hackathonName).ifPresent(controller::deleteHackathon);
            showStyledMessage("Successo", "Hackathon eliminato con successo!", JOptionPane.INFORMATION_MESSAGE);
            refreshHackathonList();
        }
    }

    private void showStyledMessage(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void showManagementDialog(int hackathonId, String hackathonTitle) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Gestione: " + hackathonTitle, true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        Component promotionPanel = createPromotionPanel(hackathonId, dialog);
        tabbedPane.addTab("Promuovi a Giudice", promotionPanel);

        Component judgesPanel = createJudgesListPanel(hackathonId);
        tabbedPane.addTab("Elenco Giudici", judgesPanel);

        dialog.add(tabbedPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Chiudi");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }


    private Component createPromotionPanel(int hackathonId, JDialog parentDialog) {
        List<Participant> participants = controller.getParticipantsByHackathon(hackathonId);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        if (participants.isEmpty()) {
            contentPanel.add(new JLabel("Nessun partecipante da promuovere.", SwingConstants.CENTER));
        } else {
            for (Participant p : participants) {
                User user = controller.getUserByEmail(p.getEmail());
                if (user != null) {
                    JPanel participantPanel = new JPanel(new BorderLayout(10, 0));
                    participantPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
                    JLabel userLabel = new JLabel(user.getFirst_name() + " " + user.getLast_name() + " (" + user.getEmail() + ")");
                    JButton promoteButton = new JButton("Promuovi");
                    styleButton(promoteButton, JUDGE_COLOR);
                    promoteButton.setBorder(new EmptyBorder(8, 15, 8, 15));
                    promoteButton.addActionListener(e -> {
                        int confirm = JOptionPane.showConfirmDialog(parentDialog,
                                "Sei sicuro di voler promuovere " + user.getUsername() + " a giudice?\nQuesta azione lo rimuoverà dai partecipanti.",
                                "Conferma Promozione", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            boolean success = controller.promoteToJudge(user, hackathonId);
                            if (success) {
                                showStyledMessage("Successo", user.getUsername() + " è ora un giudice!", JOptionPane.INFORMATION_MESSAGE);
                                promoteButton.setText("Promosso");
                                promoteButton.setEnabled(false);
                            } else {
                                showStyledMessage("Errore", "Impossibile promuovere " + user.getUsername(), JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                    participantPanel.add(userLabel, BorderLayout.CENTER);
                    participantPanel.add(promoteButton, BorderLayout.EAST);
                    contentPanel.add(participantPanel);
                }
            }
        }
        return new JScrollPane(contentPanel);
    }


    private Component createJudgesListPanel(int hackathonId) {
        List<Judge> judges = controller.getJudgesForHackathon(hackathonId);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        if (judges.isEmpty()) {
            contentPanel.add(new JLabel("Nessun giudice assegnato a questo hackathon.", SwingConstants.CENTER));
        } else {
            for (Judge j : judges) {
                User user = controller.getUserByEmail(j.getEmail());
                if (user != null) {
                    JPanel judgePanel = new JPanel(new BorderLayout(10, 0));
                    judgePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
                    JLabel userLabel = new JLabel("• " + user.getFirst_name() + " " + user.getLast_name() + " (" + user.getEmail() + ")");
                    judgePanel.add(userLabel, BorderLayout.CENTER);
                    contentPanel.add(judgePanel);
                }
            }
        }
        return new JScrollPane(contentPanel);
    }

    private void showRankingsDialog(int hackathonId, String hackathonTitle) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Classifica: " + hackathonTitle, true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        List<Team> teams = controller.getTeamsByHackathon(hackathonId);
        List<TeamScore> teamScores = teams.stream()
                .map(team -> {
                    List<Vote> votes = controller.getVotesForTeam(team.getId());
                    double averageScore = votes.stream()
                            .mapToInt(Vote::getScore)
                            .average()
                            .orElse(0.0);
                    return new TeamScore(team.getName(), averageScore, votes.size());
                })
                .sorted(Comparator.comparingDouble(TeamScore::averageScore).reversed())
                .collect(Collectors.toList());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        if (teamScores.isEmpty()) {
            contentPanel.add(new JLabel("Nessun team o nessun voto per questo hackathon.", SwingConstants.CENTER));
        } else {
            int rank = 1;
            for (TeamScore ts : teamScores) {
                JPanel teamPanel = new JPanel(new BorderLayout());
                teamPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
                JLabel rankLabel = new JLabel(String.format("%d.", rank++));
                rankLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

                JLabel nameLabel = new JLabel(ts.teamName());
                nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

                JLabel scoreLabel = new JLabel(String.format("%.2f (su %d voti)", ts.averageScore(), ts.voteCount()));
                scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

                teamPanel.add(rankLabel, BorderLayout.WEST);
                teamPanel.add(nameLabel, BorderLayout.CENTER);
                teamPanel.add(scoreLabel, BorderLayout.EAST);
                contentPanel.add(teamPanel);
            }
        }

        dialog.add(new JScrollPane(contentPanel), BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private record TeamScore(String teamName, double averageScore, int voteCount) {
    }


    public void loadUserData(User user) {
        if (user != null) {
            this.email = user.getEmail();
        }
    }
}