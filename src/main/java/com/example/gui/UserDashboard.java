package com.example.gui;

import com.example.controller.ControllerGui;
import com.example.model.Role;
import com.example.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Pannello dashboard per utenti generici (con ruolo USER).
 * <p>
 * Questa classe rappresenta la schermata principale per gli utenti che si sono
 * registrati ma non sono ancora iscritti a nessun hackathon. Visualizza una
 * lista di tutti gli hackathon disponibili, permettendo all'utente di
 * visualizzarne i dettagli e di iscriversi, cambiando così il proprio ruolo
 * a {@link com.example.model.Role#PARTICIPANT}.

 * @see com.example.model.User
 * @see com.example.model.Hackathon
 * @see HackathonCard
 */
public class UserDashboard extends JPanel {

    ControllerGui controller = new ControllerGui();
    private JLabel welcomeLabel;
    private JPanel hackathonsPanel;
    private JScrollPane scrollPane;
    private String email;
    private final List<HackathonCard> hackathonCards;
    private final Home homeFrame;

    /**
     * Costruisce la dashboard per l'utente generico.
     *
     * @param homeFrame Un riferimento alla finestra principale {@link Home} per gestire
     * la navigazione e l'aggiornamento dello stato dell'utente.
     */
    public UserDashboard(Home homeFrame) {
        this.homeFrame = homeFrame;
        setLayout(new BorderLayout(10, 10));
        hackathonCards = new ArrayList<>();

        initComponents();
        loadInitialData();
    }

    private void initComponents() {
        welcomeLabel = new JLabel("Welcome, User!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));


        hackathonsPanel = new JPanel();
        hackathonsPanel.setLayout(new BoxLayout(hackathonsPanel, BoxLayout.Y_AXIS));
        hackathonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        scrollPane = new JScrollPane(hackathonsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);


        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Available Hackathons");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(welcomeLabel, BorderLayout.NORTH);
        headerPanel.add(titlePanel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void loadInitialData() {

        controller.getAllHackathons().forEach(hackathon -> {
            createHackathonCard(
                    hackathon.getId(),
                    hackathon.getTitle(),
                    hackathon.getProblemDescription(),
                    hackathon.getStartDate(),
                    hackathon.getEndDate(),
                    hackathon.getLocation(),
                    hackathon.getRegistrationStart(),
                    hackathon.getRegistrationEnd());
        });

    }

    private void createHackathonCard(int hackathonId, String title, String description, LocalDateTime startDate,
                                     LocalDateTime endDate,
                                     String location, LocalDateTime startRegistration, LocalDateTime endRegistration) {
        HackathonCard card = new HackathonCard(
                hackathonId, title, description, startDate, endDate, location, startRegistration,
                endRegistration);
        hackathonCards.add(card);
        hackathonsPanel.add(card);
        hackathonsPanel.add(Box.createVerticalStrut(15));
    }

    /**
     * Carica i dati dell'utente loggato nel pannello.
     * <p>
     * Questo metodo imposta l'email dell'utente e personalizza il messaggio
     * di benvenuto.
     *
     * @param user L'utente attualmente loggato.
     */
    public void loadUserData(User user) {
        if (user != null) {
            this.email = user.getEmail();
            welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
        }
    }


    /**
     * Classe interna che rappresenta una card grafica per un singolo hackathon.
     * <p>
     * Ogni card visualizza le informazioni principali di un hackathon, come titolo,
     * descrizione, date e stato. Fornisce inoltre un pulsante per permettere
     * all'utente di iscriversi all'evento.
     */
    private class HackathonCard extends JPanel {
        private final String hackathonTitle;
        private final int hackathonId;
        private final JButton joinButton;
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;
        private final LocalDateTime startRegistration;
        private final LocalDateTime endRegistration;
        private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        public HackathonCard(int hackathonId, String title, String description, LocalDateTime startDate,
                             LocalDateTime endDate,
                             String location, LocalDateTime startRegistration, LocalDateTime endRegistration) {
            this.hackathonTitle = title;
            this.hackathonId = hackathonId;
            this.startDate = startDate;
            this.endDate = endDate;
            this.startRegistration = startRegistration;
            this.endRegistration = endRegistration;

            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(15, 20, 15, 20)));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
            setBackground(Color.WHITE);


            JPanel titlePanel = new JPanel(new BorderLayout());
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            titleLabel.setForeground(new Color(51, 51, 51));
            titlePanel.add(titleLabel, BorderLayout.WEST);

            add(titlePanel, BorderLayout.NORTH);


            JPanel centerPanel = new JPanel(new BorderLayout());


            JTextArea descriptionArea = new JTextArea(description);
            descriptionArea.setEditable(false);
            descriptionArea.setOpaque(false);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
            descriptionArea.setForeground(new Color(102, 102, 102));
            descriptionArea.setRows(2);
            centerPanel.add(descriptionArea, BorderLayout.NORTH);


            JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            statusPanel.setOpaque(false);


            JPanel registrationStatusPanel = createRegistrationStatusPanel();
            statusPanel.add(registrationStatusPanel);


            JPanel eventStatusPanel = createEventStatusPanel();
            statusPanel.add(eventStatusPanel);

            centerPanel.add(statusPanel, BorderLayout.SOUTH);
            add(centerPanel, BorderLayout.CENTER);


            JPanel bottomPanel = new JPanel(new BorderLayout());


            JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
            infoPanel.setOpaque(false);


            JLabel dateIconLabel = new JLabel(formatDateTimeRange(startDate, endDate));
            dateIconLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            dateIconLabel.setForeground(new Color(102, 102, 102));
            infoPanel.add(dateIconLabel);


            JLabel locationIconLabel = new JLabel(location);
            locationIconLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            locationIconLabel.setForeground(new Color(102, 102, 102));
            infoPanel.add(locationIconLabel);


            JLabel registrationDateLabel = new JLabel(
                    "Registration: " + formatDateTimeRange(startRegistration, endRegistration));
            registrationDateLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            registrationDateLabel.setForeground(new Color(102, 102, 102));
            infoPanel.add(registrationDateLabel);

            bottomPanel.add(infoPanel, BorderLayout.WEST);


            joinButton = new JButton("Join Hackathon");
            updateJoinButtonState();

            joinButton.setFocusPainted(false);
            joinButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            joinButton.setFont(new Font("Arial", Font.BOLD, 12));
            joinButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


            joinButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (joinButton.isEnabled()) {
                        joinButton.setBackground(new Color(25, 118, 210));
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (joinButton.isEnabled()) {
                        joinButton.setBackground(new Color(33, 150, 243));
                    }
                }
            });

            joinButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    joinHackathon(hackathonId);
                }
            });

            bottomPanel.add(joinButton, BorderLayout.EAST);
            add(bottomPanel, BorderLayout.SOUTH);
        }

        private JPanel createRegistrationStatusPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setOpaque(true);
            panel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));

            LocalDateTime now = LocalDateTime.now();
            JLabel statusLabel = new JLabel();
            statusLabel.setFont(new Font("Arial", Font.BOLD, 10));
            statusLabel.setForeground(Color.WHITE);

            if (startRegistration == null || endRegistration == null) {
                statusLabel.setText("REGISTRATION INFO UNAVAILABLE");
                panel.setBackground(new Color(158, 158, 158));
            } else if (now.isBefore(startRegistration)) {
                statusLabel.setText("REGISTRATIONS NOT OPEN");
                panel.setBackground(new Color(158, 158, 158));
            } else if (now.isAfter(endRegistration)) {
                statusLabel.setText("REGISTRATIONS CLOSED");
                panel.setBackground(new Color(244, 67, 54));
            } else {
                statusLabel.setText("REGISTRATIONS OPEN");
                panel.setBackground(new Color(76, 175, 80));
            }

            panel.add(statusLabel);
            return panel;
        }

        private JPanel createEventStatusPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setOpaque(true);
            panel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));

            LocalDateTime now = LocalDateTime.now();
            JLabel statusLabel = new JLabel();
            statusLabel.setFont(new Font("Arial", Font.BOLD, 10));
            statusLabel.setForeground(Color.WHITE);

            if (now.isBefore(startDate)) {
                statusLabel.setText("UPCOMING");
                panel.setBackground(new Color(33, 150, 243));
            } else if (now.isAfter(endDate)) {
                statusLabel.setText("FINISHED");
                panel.setBackground(new Color(96, 125, 139));
            } else {
                statusLabel.setText("IN PROGRESS");
                panel.setBackground(new Color(255, 152, 0));
            }

            panel.add(statusLabel);
            return panel;
        }

        private String formatDateTimeRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
            if (startDateTime == null || endDateTime == null) {
                return "TBD";
            }

            String startDateStr = startDateTime.format(dateFormatter);
            String endDateStr = endDateTime.format(dateFormatter);

            if (startDateTime.toLocalDate().equals(endDateTime.toLocalDate())) {
                return startDateStr + " (" + startDateTime.format(timeFormatter) +
                        " - " + endDateTime.format(timeFormatter) + ")";
            } else {
                return startDateStr + " " + startDateTime.format(timeFormatter) +
                        " - " + endDateStr + " " + endDateTime.format(timeFormatter);
            }
        }

        private void updateJoinButtonState() {
            LocalDateTime now = LocalDateTime.now();

            if (startRegistration == null || endRegistration == null ||
                    now.isBefore(startRegistration) || now.isAfter(endRegistration)) {
                joinButton.setEnabled(false);
                joinButton.setText("Registration Closed");
                joinButton.setBackground(new Color(189, 189, 189));
                joinButton.setForeground(new Color(117, 117, 117));
            } else {
                joinButton.setEnabled(true);
                joinButton.setText("Join Hackathon");
                joinButton.setBackground(new Color(33, 150, 243));
                joinButton.setForeground(Color.WHITE);
            }
        }

        private void joinHackathon(int hackathonId) {
            User user = controller.getUserByEmail(email);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Errore: utente non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to join '" + hackathonTitle + "'?",
                    "Confirm Registration", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                String joinResult = controller.joinHackathon(user, hackathonId);

                switch (joinResult) {
                    case "SUCCESS":
                        homeFrame.getLoggedUser().setRole(Role.PARTICIPANT);
                        homeFrame.showTeamManagementDashboard();

                        joinButton.setText("Joined!");
                        joinButton.setForeground(Color.WHITE);
                        joinButton.setBackground(new Color(76, 175, 80));
                        joinButton.setEnabled(false);

                        JOptionPane.showMessageDialog(this,
                                "Successfully joined '" + hackathonTitle + "'!",
                                "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "ALREADY_PARTICIPANT":
                        JOptionPane.showMessageDialog(this,
                                "Sei già iscritto a un hackathon. Non puoi partecipare a più eventi contemporaneamente.",
                                "Info", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "ERROR":
                        JOptionPane.showMessageDialog(this, "Si è verificato un errore durante l'iscrizione.",
                                "Errore", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        }
    }
}