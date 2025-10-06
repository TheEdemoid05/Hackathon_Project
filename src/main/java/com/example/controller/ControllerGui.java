package com.example.controller;

import com.example.dao.HackathonDAO;
import com.example.dao.UserDAO;
import com.example.daoimp.*;
import com.example.database.DBConnection;
import com.example.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Controller principale per la gestione delle operazioni dell'interfaccia grafica.
 *
 * <p>Questa classe funge da intermediario tra l'interfaccia utente e il livello di accesso ai dati.
 * Gestisce le transazioni del database per garantire l'integrità dei dati.</p>
 *
 * <p><strong>Responsabilità principali:</strong></p>
 * <ul>
 * <li>Gestione degli utenti (registrazione, login, autenticazione)</li>
 * <li>Operazioni sui partecipanti e la loro partecipazione agli hackathon</li>
 * <li>Creazione e gestione degli hackathon</li>
 * <li>Gestione dei team e della loro formazione</li>
 * <li>Operazioni sui giudici e le valutazioni</li>
 * <li>Gestione dei documenti e dei voti</li>
 * <li>Gestione delle richieste di partecipazione ai team</li>
 * </ul>
 *
 * <p><strong>Gestione delle transazioni:</strong><br>
 * Alcune operazioni critiche utilizzano transazioni del database per garantire
 * la consistenza dei dati, come la creazione dei team e l'assegnazione dei leader.</p>
 *
 * @see com.example.gui
 * @see com.example.dao
 * @see com.example.model
 */
public class ControllerGui {

    /**
     * DAO per la gestione degli utenti
     */
    private final UserDAO userDAO = new UserDaoImpl();

    /**
     * DAO per la gestione dei partecipanti
     */
    private final ParticipantDaoImpl ParticipantDAO = new ParticipantDaoImpl();

    /**
     * DAO per la gestione degli hackathon
     */
    private final HackathonDAO hackathonDAO = new HackathonDaoImpl();

    /**
     * DAO per la gestione dei documenti
     */
    private final DocumentDaoImpl documentDAO = new DocumentDaoImpl();

    /**
     * DAO per la gestione dei team
     */
    private final TeamDaoImpl teamDAO = new TeamDaoImpl();

    /**
     * DAO per la gestione dei giudici
     */
    private final JudgeDaoImpl judgeDAO = new JudgeDaoImpl();

    /**
     * DAO per la gestione dei voti
     */
    private final VoteDaoImpl voteDAO = new VoteDaoImpl();

    /**
     * DAO per la gestione delle richieste
     */
    private final RequestDaoImpl requestDAO = new RequestDaoImpl();


    // ================ GESTIONE UTENTI ================

    /**
     * Recupera un utente dal database utilizzando l'indirizzo email.
     *
     * @param email L'indirizzo email dell'utente da cercare
     * @return L'utente trovato o null se non esiste
     */
    public User getUserByEmail(String email) {
        return userDAO.findUserByEmail(email).orElse(null);
    }

    /**
     * Permette l'accesso ad un utente esistente nel sistema.
     *
     * <p>Questo metodo verifica le credenziali dell'utente confrontando
     * la password fornita con quella memorizzata nel database.</p>
     *
     * @param email    L'indirizzo email dell'utente
     * @param password La password dell'utente
     * @return L'utente autenticato se le credenziali sono corrette, null altrimenti
     */
    public User login(String email, String password) {
        Optional<User> userOpt = userDAO.findUserByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                System.out.println("Login riuscito per " + email);
                return user;
            }
        }
        System.out.println("Login fallito per " + email);
        return null;
    }


    /**
     * Registra un nuovo utente nel sistema.
     *
     * <p>Questo metodo crea un nuovo account utente controllando se l'email sia univoca. L'utente viene creato con il ruolo
     * di default USER.</p>
     *
     * @param email     L'indirizzo email dell'utente (deve essere unico)
     * @param username  Il nome utente scelto
     * @param password  La password dell'utente
     * @param firstName Il nome proprio dell'utente
     * @param lastName  Il cognome dell'utente
     * @return true se la registrazione è avvenuta con successo, false altrimenti
     */
    public boolean register(String email, String username, String password, String firstName, String lastName) {
        if (userDAO.findUserByEmail(email).isPresent()) {
            System.out.println("Email già registrata");
            return false;
        }

        User newUser = new User(email, username, password, firstName, lastName);
        boolean saved = userDAO.saveUser(newUser);

        if (saved) {
            System.out.println("Registrazione riuscita");
        } else {
            System.out.println("Registrazione fallita");
        }

        return saved;
    }


    /**
     * Permette a un utente di partecipare a un hackathon.
     *
     * <p>Questo metodo crea un nuovo partecipante associato all'hackathon specificato.
     * Verifica che l'utente non sia già partecipante prima di procedere.</p>
     *
     * @param user        L'utente che vuole partecipare
     * @param hackathonId L'ID dell'hackathon a cui partecipare
     * @return "ALREADY_PARTICIPANT" se l'utente è già partecipante,
     * "SUCCESS" se l'operazione è riuscita,
     * "ERROR" se si è verificato un errore
     */
    public String joinHackathon(User user, int hackathonId) {
        if (ParticipantDAO.findParteicipantByEmail(user.getEmail()).isPresent()) {
            return "ALREADY_PARTICIPANT";
        }

        Participant participant = new Participant(user, hackathonId, null);
        boolean success = ParticipantDAO.saveParticipant(participant);

        return success ? "SUCCESS" : "ERROR";
    }

    /**
     * Recupera un partecipante dal database utilizzando l'indirizzo email.
     *
     * @param email L'email del partecipante da cercare.
     * @return Il partecipante trovato, o null se non esiste.
     */
    public Participant getParticipantByEmail(String email) {
        return ParticipantDAO.findParteicipantByEmail(email).orElse(null);
    }

    /**
     * Trova tutti i partecipanti appartenenti a un team specifico.
     *
     * @param teamID L'ID del team per cui cercare i partecipanti.
     * @return Una lista di partecipanti appartenenti al team.
     */
    public List<Participant> findByTeamId(Integer teamID) {
        return ParticipantDAO.findParticipantByTeamId(teamID);
    }

    /**
     * Recupera tutti i partecipanti iscritti a un hackathon specifico.
     *
     * @param hackathonId L'ID dell'hackathon.
     * @return Una lista di partecipanti iscritti all'hackathon.
     */
    public List<Participant> getParticipantsByHackathon(int hackathonId) {
        return ParticipantDAO.findParticipantByHackathonId(hackathonId);
    }

    // ================ GESTIONE HACKATHON ================

    /**
     * Recupera tutti gli hackathon presenti nel sistema.
     *
     * @return Una lista contenente tutti gli hackathon
     */
    public List<Hackathon> getAllHackathons() {
        return hackathonDAO.findAllHackathon();
    }

    /**
     * Cerca un hackathon per titolo.
     *
     * @param title Il titolo dell'hackathon da cercare.
     * @return Un {@link Optional} contenente l'hackathon se trovato.
     */
    public Optional<Hackathon> getHackathonByTitle(String title) {
        return hackathonDAO.findHackathonByTitle(title);
    }

    /**
     * Recupera un hackathon tramite il suo ID.
     *
     * @param id L'ID dell'hackathon.
     * @return L'oggetto Hackathon, o null se non trovato.
     */
    public Hackathon getHackathonById(int id) {
        return hackathonDAO.findHackathonById(id).orElse(null);
    }

    /**
     * Aggiorna i dati di un hackathon esistente.
     *
     * @param hackathon L'oggetto Hackathon con i dati aggiornati.
     */
    public void updateHackathon(Hackathon hackathon) {
        hackathonDAO.updateHackathon(hackathon);
    }

    /**
     * Aggiorna la descrizione del problema di un hackathon.
     *
     * @param hackathonId        L'ID dell'hackathon da aggiornare.
     * @param problemDescription La nuova descrizione del problema.
     * @return {@code true} se l'aggiornamento ha avuto successo, {@code false} altrimenti.
     */
    public boolean updateHackathonProblem(int hackathonId, String problemDescription) {
        return hackathonDAO.updateProblemDescription(hackathonId, problemDescription);
    }

    /**
     * Elimina un hackathon dal sistema.
     *
     * @param hackathon L'hackathon da eliminare.
     */
    public void deleteHackathon(Hackathon hackathon) {
        hackathonDAO.deleteHackathon(hackathon.getId());
    }

    /**
     * Crea un nuovo hackathon nel sistema.
     *
     * @param hackathon L'hackathon da creare
     */
    public void createHackathon(Hackathon hackathon) {
        hackathonDAO.saveHackathon(hackathon);
    }

    // ================ GESTIONE TEAM ================

    /**
     * Recupera un team dal database utilizzando il suo ID.
     *
     * @param teamId L'ID del team da cercare
     * @return Il team trovato o null se non esiste
     */
    public Team getTeamById(int teamId) {
        return teamDAO.findTeamById(teamId).orElse(null);
    }

    /**
     * Salva un nuovo team nel database utilizzando una transazione.
     *
     * <p>Questo metodo esegue due operazioni atomiche:</p>
     * <ol>
     * <li>Salva il team nel database</li>
     * <li>Assegna automaticamente il leader al team</li>
     * </ol>
     *
     * <p>Se una delle due operazioni fallisce, viene eseguito il rollback
     * per mantenere la consistenza dei dati.</p>
     *
     * @param team Il team da salvare (deve avere un leader valido)
     * @return true se entrambe le operazioni sono riuscite, false altrimenti
     * @throws RuntimeException se si verifica un errore nella gestione della connessione
     */
    public boolean saveTeam(Team team) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            boolean teamSaved = teamDAO.saveTeam(team, conn);
            if (teamSaved) {
                boolean assigned = ParticipantDAO.assignParticipantToTeam(team.getLeader_email(), team.getId(), conn);
                if (assigned) {
                    conn.commit();
                    System.out.println("Team e leader assegnato con successo in una transazione.");
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }

            conn.rollback();
            return false;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Restituisce il numero di membri di un team.
     *
     * @param teamId L'ID del team.
     * @return Il numero di membri.
     */
    public int getTeamMemberNumber(int teamId) {
        return teamDAO.getTeamMemberCount(teamId);
    }

    /**
     * Recupera tutti i team di un determinato hackathon.
     *
     * @param hackathonId L'ID dell'hackathon.
     * @return Una lista di team.
     */
    public List<Team> getTeamsByHackathon(int hackathonId) {
        return teamDAO.findTeamByHackathonId(hackathonId);
    }

    /**
     * Verifica se un nome di team è già in uso in un determinato hackathon.
     *
     * @param name        Il nome del team da verificare.
     * @param hackathonId L'ID dell'hackathon.
     * @return {@code true} se il nome esiste già, {@code false} altrimenti.
     */
    public boolean teamNameExists(String name, int hackathonId) {
        return teamDAO.teamNameExistsInHackathon(name, hackathonId);
    }


    // ---------------- DOCUMENT ----------------

    /**
     * Recupera i documenti di un hackathon che non sono ancora stati valutati da un giudice.
     *
     * @param hackathonId L'ID dell'hackathon.
     * @param judgeEmail  L'email del giudice.
     * @return Una lista di documenti.
     */
    public List<Document> getDocumentsByHackathonId(int hackathonId, String judgeEmail) {
        return documentDAO.findDocumentByHackathonId(hackathonId, judgeEmail);
    }

    /**
     * Recupera tutti i documenti caricati da un team.
     *
     * @param teamId L'ID del team.
     * @return Una lista di documenti.
     */
    public List<Document> getDocumentsByTeamId(int teamId) {
        return documentDAO.findDocumentByTeamId(teamId);
    }

    /**
     * Salva un nuovo documento nel sistema.
     *
     * @param document Il documento da salvare.
     */
    public void saveDocument(Document document) {
        documentDAO.saveDocument(document);
    }

    // ---------------- JUDGE ----------------

    /**
     * Recupera un giudice tramite email.
     *
     * @param email L'email del giudice.
     * @return L'oggetto Judge, o null se non trovato.
     */
    public Judge getJudgeByEmail(String email) {
        return judgeDAO.findJudgeByEmail(email).orElse(null);
    }

    /**
     * Recupera la lista di giudici per un hackathon.
     *
     * @param hackathonId L'ID dell'hackathon.
     * @return Una lista di giudici.
     */
    public List<Judge> getJudgesForHackathon(int hackathonId) {
        return judgeDAO.findJudgeByHackathonId(hackathonId);
    }

    /**
     * Promuove un partecipante al ruolo di giudice in una transazione.
     *
     * @param user        L'utente da promuovere.
     * @param hackathonId L'ID dell'hackathon a cui assegnare il giudice.
     * @return {@code true} se l'operazione ha successo, {@code false} altrimenti, portando il database allo stato precedente, grazie al rollback.
     */
    public boolean promoteToJudge(User user, int hackathonId) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            Judge newJudge = new Judge(user, hackathonId);
            boolean judgeSaved = judgeDAO.saveJudge(newJudge, conn);

            if (judgeSaved) {
                boolean participantDeleted = ParticipantDAO.deleteParticipantByEmail(user.getEmail(), conn);
                if (participantDeleted) {
                    conn.commit();
                    return true;
                }
            }

            conn.rollback();
            return false;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Salva un nuovo giudice.
     *
     * @param judge Il giudice da salvare.
     * @return {@code true} se il salvataggio ha successo, {@code false} altrimenti.
     */
    public boolean saveJudge(Judge judge) {
        if (judgeDAO.findJudgeByEmail(judge.getEmail()).isPresent()) {
            System.out.println("L'utente è già un giudice.");
            return false;
        }
        return judgeDAO.saveJudge(judge);
    }


    // ---------------- VOTE ----------------

    /**
     * Salva un nuovo voto nel sistema.
     *
     * @param vote       Il voto da salvare.
     * @param documentID L'ID del documento a cui si riferisce il voto.
     */
    public void saveVote(Vote vote, int documentID) {
        voteDAO.saveVote(vote, documentID);
    }

    /**
     * Recupera tutti i voti assegnati a un team.
     *
     * @param teamId L'ID del team.
     * @return Una lista di voti.
     */
    public List<Vote> getVotesForTeam(int teamId) {
        return voteDAO.findVoteByTeamId(teamId);
    }

    /**
     * Controlla se un giudice ha già votato per un team in un hackathon.
     *
     * @param hackathonId L'ID dell'hackathon.
     * @param judgeEmail  L'email del giudice.
     * @param teamId      L'ID del team.
     * @return {@code true} se il giudice ha già votato, {@code false} altrimenti.
     */
    public boolean hasJudgeVotedForTeam(int hackathonId, String judgeEmail, int teamId) {
        return voteDAO.hasJudgeVotedForTeam(hackathonId, judgeEmail, teamId);
    }

    // ---------------- REQUEST ----------------

    /**
     * Trova tutte le richieste ricevute da un utente.
     *
     * @param email L'email del destinatario delle richieste.
     * @return Una lista di richieste.
     */
    public List<Request> findRequestsForUser(String email) {
        return requestDAO.findRequestsReceivedBy(email);
    }

    /**
     * Salva una nuova richiesta di partecipazione.
     *
     * @param request La richiesta da salvare.
     * @return {@code true} se il salvataggio ha successo, {@code false} altrimenti.
     */
    public boolean saveRequest(Request request) {
        return requestDAO.saveRequest(request);
    }

    /**
     * Trova tutte le richieste inviate da un utente.
     *
     * @param email L'email del mittente delle richieste.
     * @return Una lista di richieste.
     */
    public List<Request> findSentRequests(String email) {
        return requestDAO.findRequestsSentBy(email);
    }

    /**
     * Aggiorna lo stato di una richiesta.
     *
     * @param request La richiesta con lo stato aggiornato.
     * @return {@code true} se l'aggiornamento ha successo, {@code false} altrimenti.
     */
    public boolean updateRequest(Request request) {
        return requestDAO.updateRequest(request);
    }

    /**
     * Assegna un partecipante a un team.
     *
     * @param email  L'email del partecipante.
     * @param teamId L'ID del team.
     * @return {@code true} se l'assegnazione ha successo, {@code false} altrimenti.
     */
    public boolean assignParticipantToTeam(String email, int teamId) {
        return ParticipantDAO.assignParticipantToTeam(email, teamId);
    }
}