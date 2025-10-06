package com.example.gui;

import com.example.controller.ControllerGui;
import com.example.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Pannello principale per la dashboard del Giudice.
 * <p>
 * Questa classe fornisce l'interfaccia utente per le due funzioni principali
 * di un giudice:
 * </p>
 * <ol>
 * <li><b>Definire la Sfida:</b> Un'area dove il giudice può scrivere e
 * pubblicare (o aggiornare) la descrizione del problema dell'hackathon.</li>
 * <li><b>Valutare i Progetti:</b> Un'area dove vengono visualizzati i documenti
 * caricati dai team, con la possibilità di assegnare un voto e un commento.</li>
 * </ol>
 * <p>
 * La dashboard carica dinamicamente i dati relativi all'hackathon a cui il
 * giudice è assegnato e gestisce l'interazione con il controller per salvare
 * le valutazioni e la descrizione della sfida.

 * @see com.example.model.Judge
 * @see com.example.model.Hackathon
 * @see com.example.model.Document
 * @see com.example.model.Vote
 */
public class JudgeDashboard extends JPanel {

    private static final Color PRIMARY_COLOR = new Color(44, 62, 80);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color LIGHT_GRAY = new Color(245, 245, 245);
    private static final Color CARD_BACKGROUND = new Color(255, 255, 255);
    private static final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font PLAIN_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    private String email;
    private JTextArea challengeTextArea;
    private JTextField challengeTitleField;
    private JButton submitChallengeButton;
    private JPanel submissionsPanel;
    private JScrollPane submissionsScrollPane;
    private final ControllerGui controllerGui = new ControllerGui();
    private Judge judge;
    private final Home homeFrame;
    private JLabel hackathonTitleLabel;
    private JLabel welcomeLabel;

    /**
     * Costruisce la dashboard per un giudice.
     *
     * @param homeFrame Un riferimento alla finestra principale {@link Home} per gestire
     * funzionalità globali come il logout.
     */
    public JudgeDashboard(Home homeFrame) {
        this.homeFrame = homeFrame;
        setLayout(new BorderLayout(15, 15));
        setBackground(LIGHT_GRAY);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        initComponents();
        addListeners();
    }

    private void initComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.setOpaque(false);

        JLabel titleLabel = new JLabel("Dashboard Giudice");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);

        welcomeLabel = new JLabel("Benvenuto!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeLabel.setForeground(new Color(50, 50, 50));

        hackathonTitleLabel = new JLabel("Nessun hackathon assegnato");
        hackathonTitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        hackathonTitleLabel.setForeground(new Color(100, 100, 100));

        titleContainer.add(titleLabel);
        titleContainer.add(Box.createVerticalStrut(5));
        titleContainer.add(welcomeLabel);
        titleContainer.add(Box.createVerticalStrut(5));
        titleContainer.add(hackathonTitleLabel);

        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton, DANGER_COLOR);
        logoutButton.addActionListener(e -> homeFrame.logout());

        headerPanel.add(titleContainer, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        JPanel mainContentPanel = new JPanel(new BorderLayout(15, 15));
        mainContentPanel.setOpaque(false);
        mainContentPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPanel challengePanel = createChallengePanel();
        mainContentPanel.add(challengePanel, BorderLayout.WEST);

        JPanel submissionsMainPanel = createSubmissionsPanel();
        mainContentPanel.add(submissionsMainPanel, BorderLayout.CENTER);

        add(mainContentPanel, BorderLayout.CENTER);
    }

    private void addListeners() {
        submitChallengeButton.addActionListener(e -> {
            if (challengeTitleField.getText().isEmpty() || challengeTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(JudgeDashboard.this, "Compila tutti i campi!", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (judge != null) {
                boolean success = controllerGui.updateHackathonProblem(judge.getHackathonId(), challengeTextArea.getText());
                if (success) {
                    JOptionPane.showMessageDialog(JudgeDashboard.this, "Sfida salvata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    loadUserData(homeFrame.getLoggedUser());
                } else {
                    JOptionPane.showMessageDialog(JudgeDashboard.this, "Errore nel salvataggio della sfida.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setFont(BOLD_FONT);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
    }

    private JPanel createChallengePanel() {
        JPanel section = new JPanel(new BorderLayout(0, 15));
        section.setOpaque(false);
        section.setPreferredSize(new Dimension(400, 0));
        JLabel sectionTitle = new JLabel(" Definisci la Sfida dell'Hackathon");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(PRIMARY_COLOR);
        section.add(sectionTitle, BorderLayout.NORTH);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(CARD_BACKGROUND);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(25, 25, 25, 25)));
        formPanel.add(createFormField("Titolo Sfida", challengeTitleField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(15));
        challengeTextArea = new JTextArea(10, 30);
        challengeTextArea.setFont(PLAIN_FONT);
        challengeTextArea.setLineWrap(true);
        challengeTextArea.setWrapStyleWord(true);
        JScrollPane challengeScrollPane = new JScrollPane(challengeTextArea);
        formPanel.add(createFormField("Descrizione Sfida", challengeScrollPane));
        formPanel.add(Box.createVerticalStrut(20));
        submitChallengeButton = new JButton("Pubblica Sfida");
        styleButton(submitChallengeButton, SECONDARY_COLOR);
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(submitChallengeButton);
        formPanel.add(buttonWrapper);
        section.add(formPanel, BorderLayout.CENTER);
        return section;
    }

    private JPanel createSubmissionsPanel() {
        JPanel section = new JPanel(new BorderLayout(0, 15));
        section.setOpaque(false);
        JLabel sectionTitle = new JLabel(" Valuta i Progetti dei Team");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(PRIMARY_COLOR);
        section.add(sectionTitle, BorderLayout.NORTH);
        submissionsPanel = new JPanel();
        submissionsPanel.setLayout(new BoxLayout(submissionsPanel, BoxLayout.Y_AXIS));
        submissionsPanel.setBackground(LIGHT_GRAY);
        submissionsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        submissionsScrollPane = new JScrollPane(submissionsPanel);
        submissionsScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        submissionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        submissionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        submissionsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        section.add(submissionsScrollPane, BorderLayout.CENTER);
        return section;
    }

    private JPanel createFormField(String labelText, JComponent field) {
        JPanel fieldPanel = new JPanel(new BorderLayout(0, 8));
        fieldPanel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(BOLD_FONT);
        label.setForeground(PRIMARY_COLOR);
        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(field, BorderLayout.CENTER);
        return fieldPanel;
    }

    /**
     * Carica e visualizza i dati specifici del giudice loggato.
     * <p>
     * Questo metodo popola l'header con il nome utente e l'hackathon
     * associato, e pre-compila il pannello della sfida con la descrizione
     * del problema, se già esistente.
     *
     * @param user L'utente giudice attualmente loggato.
     */
    public void loadUserData(User user) {
        if (user != null) {
            this.email = user.getEmail();
            this.judge = controllerGui.getJudgeByEmail(email);

            welcomeLabel.setText("Benvenuto, " + user.getUsername() + "!");

            if (this.judge != null) {
                Hackathon hackathon = controllerGui.getHackathonById(this.judge.getHackathonId());
                if (hackathon != null) {
                    hackathonTitleLabel.setText("Hackathon: " + hackathon.getTitle());
                    challengeTitleField.setText(hackathon.getTitle());
                    challengeTitleField.setEditable(false);

                    String existingDescription = hackathon.getProblemDescription();
                    if (existingDescription != null && !existingDescription.trim().isEmpty()) {
                        challengeTextArea.setText(existingDescription);
                        submitChallengeButton.setText("Aggiorna Sfida");
                    } else {
                        challengeTextArea.setText("");
                        submitChallengeButton.setText("Pubblica Sfida");
                    }

                } else {
                    hackathonTitleLabel.setText("Hackathon non trovato (ID: " + this.judge.getHackathonId() + ")");
                    challengeTitleField.setText("");
                    challengeTextArea.setText("");
                }
            } else {
                hackathonTitleLabel.setText("Nessun hackathon assegnato");
                challengeTitleField.setText("");
                challengeTextArea.setText("");
            }

            refreshSubmissions();
        }
    }

    private void refreshSubmissions() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                loadSubmissions();
                return null;
            }
        };
        worker.execute();
    }

    private void loadSubmissions() {
        submissionsPanel.removeAll();
        if (judge == null) {
            SwingUtilities.invokeLater(() -> {
                submissionsPanel.add(createCenteredLabel("Nessun giudice trovato. Assicurati di essere loggato."));
                submissionsPanel.revalidate();
                submissionsPanel.repaint();
            });
            return;
        }
        List<Document> documents = controllerGui.getDocumentsByHackathonId(judge.getHackathonId(), judge.getEmail());
        SwingUtilities.invokeLater(() -> {
            if (documents.isEmpty()) {
                submissionsPanel.add(createCenteredLabel("Nessuna submission disponibile da valutare."));
            } else {
                for (Document document : documents) {
                    Team team = controllerGui.getTeamById(document.getTeamId());
                    if (team != null) {
                        String submissionTime = new SimpleDateFormat("dd/MM/yyyy HH:mm")
                                .format(Date.from(document.getUploadDate().atZone(java.time.ZoneId.systemDefault()).toInstant()));
                        submissionsPanel.add(createSubmissionCard(team, submissionTime, document));
                        submissionsPanel.add(Box.createVerticalStrut(15));
                    }
                }
            }
            submissionsPanel.add(Box.createVerticalGlue());
            submissionsPanel.revalidate();
            submissionsPanel.repaint();
        });
    }

    private JPanel createSubmissionCard(Team team, String submissionTime, Document document) {
        JPanel card = new JPanel(new BorderLayout(20, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(20, 20, 20, 20)));
        card.setBackground(CARD_BACKGROUND);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        JLabel teamLabel = new JLabel(team.getName());
        teamLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        teamLabel.setForeground(PRIMARY_COLOR);
        JLabel documentNameLabel = new JLabel(document.getFileName());
        documentNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        documentNameLabel.setForeground(new Color(100, 100, 100));
        JLabel timeLabel = new JLabel("Inviato il: " + submissionTime);
        timeLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        timeLabel.setForeground(new Color(150, 150, 150));
        leftPanel.add(teamLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(documentNameLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(timeLabel);
        leftPanel.add(Box.createVerticalGlue());

        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(250, 0));

        boolean hasVoted = controllerGui.hasJudgeVotedForTeam(judge.getHackathonId(), judge.getEmail(), team.getId());

        if (hasVoted) {
            JLabel alreadyVotedLabel = new JLabel("Voto già inviato", SwingConstants.CENTER);
            alreadyVotedLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            alreadyVotedLabel.setForeground(SUCCESS_COLOR);
            rightPanel.add(alreadyVotedLabel, BorderLayout.CENTER);
        } else {
            JSpinner voteSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
            voteSpinner.setFont(new Font("Segoe UI", Font.BOLD, 16));
            JPanel voteSpinnerPanel = new JPanel();
            voteSpinnerPanel.setOpaque(false);
            voteSpinnerPanel.add(new JLabel("Voto:"));
            voteSpinnerPanel.add(voteSpinner);

            JTextArea commentArea = new JTextArea(3, 20);
            commentArea.setFont(PLAIN_FONT);
            commentArea.setLineWrap(true);
            commentArea.setWrapStyleWord(true);
            JScrollPane commentScrollPane = new JScrollPane(commentArea);
            commentScrollPane.setBorder(BorderFactory.createTitledBorder("Commento"));

            JButton voteButton = new JButton("Invia Voto");
            styleButton(voteButton, SUCCESS_COLOR);
            voteButton.addActionListener(e -> {
                int vote = (int) voteSpinner.getValue();
                String commento = commentArea.getText();
                Vote newVote = new Vote(judge.getHackathonId(), judge.getEmail(), team.getId(), vote, commento);
                controllerGui.saveVote(newVote, document.getId());
                JOptionPane.showMessageDialog(this, "Voto inviato con successo!", "Successo",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshSubmissions();
            });
            JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonWrapper.setOpaque(false);
            buttonWrapper.add(voteButton);

            rightPanel.add(voteSpinnerPanel, BorderLayout.NORTH);
            rightPanel.add(commentScrollPane, BorderLayout.CENTER);
            rightPanel.add(buttonWrapper, BorderLayout.SOUTH);
        }

        card.add(leftPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);
        return card;
    }

    private JLabel createCenteredLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        label.setForeground(new Color(108, 117, 125));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
}