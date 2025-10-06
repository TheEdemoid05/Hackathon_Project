package com.example.gui.team;

import com.example.controller.ControllerGui;
import com.example.model.Document;
import com.example.model.Participant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Pannello per la gestione dei progetti del team.
 * <p>
 * Questa classe fornisce l'interfaccia utente per i membri di un team per
 * caricare i file del loro progetto e visualizzare i documenti già sottomessi.
 * <p>
 * Se il partecipante non fa parte di un team, il pannello mostrerà un
 * messaggio informativo anziché i controlli di upload e la lista dei file.
 * @see TeamManagementDashboard
 * @see Document
 * @see ControllerGui
 */
public class TeamProjectsPanel extends JPanel {

    private final ControllerGui controller;
    private final Participant participant;
    private JTextField fileNameField;
    private JButton uploadButton;
    private JPanel filesPanel;

    /**
     * Costruisce il pannello dei progetti del team.
     *
     * @param controller Il controller per le operazioni di business.
     * @param participant I dati del partecipante loggato.
     */
    public TeamProjectsPanel(ControllerGui controller, Participant participant) {
        this.controller = controller;
        this.participant = participant;
        setLayout(new BorderLayout());
        refreshData();
    }

    private void setupUIForTeamMember() {

        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.NORTH);


        JPanel filesSection = createFilesSection();
        add(filesSection, BorderLayout.CENTER);
        loadTeamDocuments();
    }

    private void setupUIForNoTeam() {

        JLabel noTeamLabel = new JLabel("Devi far parte di un team per gestire i progetti.", SwingConstants.CENTER);
        noTeamLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        add(noTeamLabel, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();


        JLabel titleLabel = new JLabel("Progetti del Team", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 30, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);


        JLabel fileNameLabel = new JLabel("Nome del file:");
        fileNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(fileNameLabel, gbc);


        fileNameField = new JTextField(20);
        fileNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fileNameField.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(fileNameField, gbc);


        uploadButton = new JButton("Carica File");
        uploadButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        uploadButton.setPreferredSize(new Dimension(120, 35));
        uploadButton.setBackground(new Color(70, 130, 180));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFocusPainted(false);
        uploadButton.setBorder(BorderFactory.createRaisedBevelBorder());
        uploadButton.addActionListener(e -> {
            String fileName = fileNameField.getText().trim();
            if (!fileName.isEmpty()) {
                controller.saveDocument(new Document(participant.getTeamID(), fileName));
                JOptionPane.showMessageDialog(null, "Document saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a file name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(uploadButton, gbc);

        return formPanel;
    }

    private JPanel createFilesSection() {
        JPanel filesSection = new JPanel(new BorderLayout());
        filesSection.setBorder(new EmptyBorder(0, 20, 20, 20));

        JLabel filesTitle = new JLabel("File Caricati", SwingConstants.LEFT);
        filesTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        filesTitle.setBorder(new EmptyBorder(10, 0, 15, 0));
        filesSection.add(filesTitle, BorderLayout.NORTH);

        filesPanel = new JPanel();
        filesPanel.setLayout(new BoxLayout(filesPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(filesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        filesSection.add(scrollPane, BorderLayout.CENTER);

        return filesSection;
    }

    private void loadTeamDocuments() {
        filesPanel.removeAll();
        List<Document> documents = controller.getDocumentsByTeamId(participant.getTeamID());
        if (documents.isEmpty()) {
            JLabel noFilesLabel = new JLabel("Nessun file caricato per questo team.");
            noFilesLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            filesPanel.add(noFilesLabel);
        } else {
            documents.forEach(doc -> {
                JPanel card = createFileCard(doc.getFileName(), doc.getUploadDate());
                filesPanel.add(card);
                filesPanel.add(Box.createVerticalStrut(10));
            });
        }
        filesPanel.revalidate();
        filesPanel.repaint();
    }


    private JPanel createFileCard(String fileName, LocalDateTime uploadDate) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JLabel fileIcon = new JLabel("📄");
        fileIcon.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        fileIcon.setPreferredSize(new Dimension(40, 40));
        card.add(fileIcon, BorderLayout.WEST);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(new EmptyBorder(0, 15, 0, 15));

        JLabel nameLabel = new JLabel(fileName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(new Color(51, 51, 51));
        infoPanel.add(nameLabel, BorderLayout.NORTH);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        JLabel dateLabel = new JLabel("Caricato il: " + uploadDate.format(formatter));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLabel.setForeground(Color.GRAY);
        infoPanel.add(dateLabel, BorderLayout.SOUTH);

        card.add(infoPanel, BorderLayout.CENTER);

        JButton actionButton = new JButton("⋮");
        actionButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        actionButton.setPreferredSize(new Dimension(30, 30));
        actionButton.setBackground(Color.WHITE);
        actionButton.setBorder(BorderFactory.createEmptyBorder());
        actionButton.setFocusPainted(false);
        actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.add(actionButton, BorderLayout.EAST);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBackground(new Color(248, 249, 250));
                card.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.repaint();
            }
        });

        return card;
    }

    /**
     * Ricarica e aggiorna l'intero pannello dei progetti.
     * <p>
     * Questo metodo determina se l'utente fa parte di un team. Se sì, mostra
     * l'interfaccia di gestione dei file; altrimenti, mostra un messaggio
     * informativo. Viene chiamato all'inizializzazione e ogni volta che
     * è necessario un aggiornamento dei dati (es. dopo un upload).
     */
    public void refreshData() {
        System.out.println("Aggiornamento pannello progetti del team...");
        this.removeAll();

        if (participant != null && participant.getTeamID() != null) {
            setupUIForTeamMember();
        } else {
            setupUIForNoTeam();
        }

        revalidate();
        repaint();
    }
}