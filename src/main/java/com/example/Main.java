package com.example;

import com.example.gui.Home;
import javax.swing.SwingUtilities;

/**
 * Classe principale per l'avvio del Sistema di Gestione Hackathon.
 * 
 * <p>Questa classe contiene il metodo main che inizializza l'applicazione
 * e avvia l'interfaccia grafica utente. L'applicazione utilizza Swing .</p>
 * <p>Il sistema consente la gestione completa di hackathon, inclusa:</p>
 * <ul>
 *   <li>Registrazione e autenticazione utenti</li>
 *   <li>Creazione e gestione di hackathon</li>
 *   <li>Formazione e gestione dei team</li>
 *   <li>Sistema di valutazione e votazione</li>
 * </ul>
 */
public class Main {

    /**
     * Metodo principale per l'avvio dell'applicazione.
     * 
     * <p>Utilizza {@link SwingUtilities#invokeLater(Runnable)} per garantire
     * che l'interfaccia grafica venga inizializzata nel thread EDT (Event Dispatch Thread)
     * come raccomandato dalle best practice di Swing.</p>
     * 
     * @param args Argomenti della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Home homeInstance = new Home();
            homeInstance.setVisible(true);
        });
    }
}