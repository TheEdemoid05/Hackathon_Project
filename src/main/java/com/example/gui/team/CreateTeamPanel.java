package com.example.gui.team;

import com.example.controller.ControllerGui;
import com.example.model.Hackathon;
import com.example.model.Participant;
import com.example.model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pannello dedicato alla creazione di un nuovo team.
 * <p>
 * Questa interfaccia è visibile solo ai partecipanti che non fanno ancora parte
 * di un team. Permette all'utente di inserire un nome per il nuovo team
 * e di avviare il processo di creazione, che include la validazione del nome
 * e il salvataggio tramite il {@link ControllerGui}.

 * @see TeamManagementDashboard
 * @see ControllerGui
 */
public class CreateTeamPanel extends JPanel {

    private String userEmail;
    private final ControllerGui controllerGui;
    private JTextField teamNameField;
    private JButton createTeamButton;
    private JButton clearFormButton;
    private JLabel statusLabel;
    private Hackathon hackathon;
    private final TeamManagementDashboard teamManagementDashboard;

    /**
     * Costruisce il pannello per la creazione di un team.
     *
     * @param controllerGui Il controller per le operazioni di business.
     * @param participant I dati del partecipante loggato.
     * @param teamManagementDashboard Un riferimento alla dashboard principale per
     * la navigazione e l'aggiornamento.
     */
    public CreateTeamPanel(ControllerGui controllerGui, Participant participant, TeamManagementDashboard teamManagementDashboard) {
        this.controllerGui = controllerGui;
        this.teamManagementDashboard = teamManagementDashboard;

        if (participant != null) {
            this.userEmail = participant.getEmail();
            this.hackathon = controllerGui.getHackathonById(participant.getHackathonID());
            System.out.println("CreateTeamPanel - Email: " + userEmail);
            System.out.println("CreateTeamPanel - Hackathon: " + hackathon);
        } else {
            System.err.println("ERRORE: Participant è null nel costruttore CreateTeamPanel");
        }

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initComponents();
        addListeners();
    }

    private void initComponents() {
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.CENTER);


        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        createTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTeam();
            }
        });

        clearFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });


        teamNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTeam();
            }
        });
    }

    private JPanel createFormPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));


        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);


        JPanel fieldsPanel = createFieldsPanel();
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);


        JPanel buttonsPanel = createButtonsPanel();
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private void createTeam() {
        System.out.println("=== INIZIO createTeam ===");

        if (hackathon == null || userEmail == null) {
            System.err.println("ERRORE CRITICO: Impossibile creare un team senza dati validi di hackathon o utente.");
            showStatus("Errore critico: Dati non disponibili. Riprovare o contattare l'assistenza.", Color.RED);
            createTeamButton.setEnabled(true);
            return;
        }

        String teamName = teamNameField.getText().trim();


        if (teamName.isEmpty()) {
            showStatus("Il nome del team è obbligatorio!", Color.RED);
            teamNameField.requestFocus();
            return;
        }


        if (controllerGui.teamNameExists(teamName, hackathon.getId())) {
            showStatus("Questo nome è già stato scelto. Scegline un altro.", Color.RED);
            teamNameField.requestFocus();
            return;
        }


        createTeamButton.setEnabled(false);
        showStatus("Creazione team in corso...", new Color(0, 123, 255));

        try {
            Team team = new Team(teamName, hackathon.getMaxParticipants(), userEmail, hackathon.getId());

            boolean success = controllerGui.saveTeam(team);

            if (success) {
                showStatus("Team creato con successo!", new Color(40, 167, 69));
                clearForm();


                JOptionPane.showMessageDialog(this,
                        "Il team '" + teamName + "' è stato creato con successo!\n" +
                                "Ora puoi invitare altri membri al tuo team.",
                        "Team Creato", JOptionPane.INFORMATION_MESSAGE);

                teamManagementDashboard.refreshAllPanels();
                teamManagementDashboard.switchToTab(0);


            } else {
                showStatus("Errore durante la creazione del team. Riprova.", Color.RED);
            }
        } catch (Exception ex) {
            System.err.println("Eccezione durante creazione team: " + ex.getMessage());
            ex.printStackTrace();
            showStatus("Errore: " + ex.getMessage(), Color.RED);
        } finally {
            createTeamButton.setEnabled(true);
        }
        System.out.println("=== FINE createTeam ===");
    }

    private void clearForm() {
        teamNameField.setText("");
        statusLabel.setText(" ");
        teamNameField.requestFocus();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        headerPanel.setBackground(new Color(248, 249, 250));

        JLabel titleLabel = new JLabel("Crea il Tuo Team");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        titleLabel.setForeground(new Color(33, 37, 41));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionLabel = new JLabel("Completa i campi sottostanti per creare un nuovo team");
        instructionLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        instructionLabel.setForeground(new Color(108, 117, 125));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(instructionLabel);

        return headerPanel;
    }

    private JPanel createFieldsPanel() {
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel nameLabel = new JLabel("Nome del Team:");
        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        fieldsPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        teamNameField = new JTextField(25);
        teamNameField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        teamNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        fieldsPanel.add(teamNameField, gbc);

        return fieldsPanel;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        clearFormButton = new JButton("Cancella");
        clearFormButton.setPreferredSize(new Dimension(120, 40));
        clearFormButton.setBackground(new Color(108, 117, 125));
        clearFormButton.setForeground(Color.WHITE);
        clearFormButton.setFocusPainted(false);
        clearFormButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        createTeamButton = new JButton("Crea Team");
        createTeamButton.setPreferredSize(new Dimension(120, 40));
        createTeamButton.setBackground(new Color(40, 167, 69));
        createTeamButton.setForeground(Color.WHITE);
        createTeamButton.setFocusPainted(false);
        createTeamButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        buttonsPanel.add(clearFormButton);
        buttonsPanel.add(createTeamButton);

        return buttonsPanel;
    }

    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    /**
     * Aggiorna lo stato del pannello. In questo caso, si limita a pulire
     * il form per prepararlo a un nuovo inserimento.
     */
    public void refreshData() {
        clearForm();
    }
}