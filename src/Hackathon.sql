-- ============================
-- 1. UTENTI
-- ============================
CREATE TABLE users
(
    email      VARCHAR(255) PRIMARY KEY,
    username   VARCHAR(255) UNIQUE NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    first_name VARCHAR(255)        NOT NULL,
    last_name  VARCHAR(255)        NOT NULL
);

-- ============================
-- 2. ORGANIZZATORI
-- ============================
CREATE TABLE organizers
(
    user_email VARCHAR(255) PRIMARY KEY,
    CONSTRAINT fk_organizer_user FOREIGN KEY (user_email) REFERENCES users (email) ON DELETE CASCADE
);

-- ============================
-- 3. HACKATHON
-- ============================
CREATE TABLE hackathons
(
    id                   SERIAL PRIMARY KEY,
    title                VARCHAR(255) UNIQUE NOT NULL,
    location             VARCHAR(255)        NOT NULL,
    start_date           TIMESTAMP           NOT NULL,
    end_date             TIMESTAMP           NOT NULL,
    max_participants     INT                 NOT NULL,
    max_team_size        INT                 NOT NULL,
    registration_start   TIMESTAMP,
    registration_end     TIMESTAMP,
    problem_description  TEXT,
    organizer_user_email VARCHAR(255)        NOT NULL,
    CONSTRAINT fk_hackathon_organizer FOREIGN KEY (organizer_user_email) REFERENCES organizers (user_email) ON DELETE CASCADE
);

-- ============================
-- 4. GIUDICI
-- ============================
CREATE TABLE judges
(
    user_email   VARCHAR(255) PRIMARY KEY,
    hackathon_id INT NOT NULL,
    CONSTRAINT fk_judge_user FOREIGN KEY (user_email) REFERENCES users (email) ON DELETE CASCADE,
    CONSTRAINT fk_judge_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons (id) ON DELETE CASCADE
);

-- ============================
-- 5. TEAM & PARTECIPANTI
-- ============================
CREATE TABLE teams
(
    id           SERIAL PRIMARY KEY,
    team_name    VARCHAR(255) NOT NULL,
    hackathon_id INT          NOT NULL,
    max_members  INT          NOT NULL,
    leader_email VARCHAR(255) NOT NULL,
    CONSTRAINT fk_team_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons (id) ON DELETE CASCADE,
    CONSTRAINT fk_team_leader FOREIGN KEY (leader_email) REFERENCES users (email) ON DELETE SET NULL,
    UNIQUE (team_name, hackathon_id)
);

CREATE TABLE participants
(
    user_email   VARCHAR(255) PRIMARY KEY,
    hackathon_id INT NOT NULL,
    team_id      INT,
    CONSTRAINT fk_participant_user FOREIGN KEY (user_email) REFERENCES users (email) ON DELETE CASCADE,
    CONSTRAINT fk_participant_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons (id) ON DELETE CASCADE,
    CONSTRAINT fk_participant_team FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE SET NULL
);

-- ============================
-- 6. DOCUMENTI
-- ============================
CREATE TABLE documents
(
    id            SERIAL PRIMARY KEY,
    filename      VARCHAR(255) NOT NULL,
    upload_date   TIMESTAMP    NOT NULL DEFAULT NOW(),
    team_id       INT          NOT NULL,
    CONSTRAINT fk_document_team FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE CASCADE
);


-- ============================
-- 7. RICHIESTE DI PARTECIPAZIONE
-- ============================
CREATE TABLE requests
(
    id                         SERIAL PRIMARY KEY,
    message                    TEXT                          NOT NULL,
    status                     VARCHAR(50) DEFAULT 'PENDING' NOT NULL,
    team_id                    INT                           NOT NULL,
    sender_participant_email   VARCHAR(255)                  NOT NULL,
    receiver_participant_email VARCHAR(255)                  NOT NULL,
    CONSTRAINT fk_request_team FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE CASCADE,
    CONSTRAINT fk_request_sender FOREIGN KEY (sender_participant_email) REFERENCES participants (user_email) ON DELETE CASCADE,
    CONSTRAINT fk_request_receiver FOREIGN KEY (receiver_participant_email) REFERENCES participants (user_email) ON DELETE CASCADE
);

-- ============================
-- 8. VOTI
-- ============================
CREATE TABLE votes
(
    id               SERIAL PRIMARY KEY,
    score            INT          NOT NULL CHECK (score >= 0 AND score <= 10),
    team_id          INT          NOT NULL,
    judge_user_email VARCHAR(255) NOT NULL,
    hackathon_id     INT          NOT NULL,
    comment          TEXT,
    document_id      INT,
    CONSTRAINT fk_vote_team FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE CASCADE,
    CONSTRAINT fk_vote_judge FOREIGN KEY (judge_user_email) REFERENCES judges (user_email) ON DELETE CASCADE,
    CONSTRAINT fk_vote_hackathon FOREIGN KEY (hackathon_id) REFERENCES hackathons (id) ON DELETE CASCADE,
    CONSTRAINT fk_vote_document FOREIGN KEY (document_id) REFERENCES documents (id) ON DELETE SET NULL
);

-- ============================
-- INSERIMENTO DATI DI ESEMPIO
-- ============================

INSERT INTO users (email, username, password, first_name, last_name)
VALUES
    -- Organizzatori
    ('diomede.mazzone@example.com', 'diomede_organizer', 'pass_diomede', 'Diomede', 'Mazzone'),
    ('simone.martino@example.com', 'simone_organizer', 'pass_simone', 'Simone', 'Martino'),
    ('valeria.larco@example.com', 'valeria_organizer', 'pass_valeria', 'Valeria', 'L''arco'),
    -- Giudici
    ('hideo.kojima@example.com', 'hideo_kojima', 'pass_kojima', 'Hideo', 'Kojima'),
    ('hidetaka.miyazaki@example.com', 'hidetaka_miyazaki', 'pass_miyazaki', 'Hidetaka', 'Miyazaki'),
    ('yoko.taro@example.com', 'yoko_taro', 'pass_yoko', 'Yoko', 'Taro'),
    -- Tutti i Partecipanti
    ('christian.cappabianca@example.com', 'chris_cappa', 'pass_chris', 'Christian', 'Cappabianca'),
    ('manuel.fava@example.com', 'manuel_fava', 'pass_manuel', 'Manuel', 'Fava'),
    ('davide.lugli@example.com', 'davide_lugli', 'pass_davide', 'Davide', 'Lugli'),
    ('federica.lubrano@example.com', 'fede_lubrano', 'pass_federica', 'Federica', 'Lubrano'),
    ('claudio.caianiello@example.com', 'claudio_caia', 'pass_claudio', 'Claudio', 'Caianiello'),
    ('fabio.fascia@example.com', 'fabio_fascia', 'pass_fabio', 'Fabio', 'Fascia'),
    ('francesca.mazzone@example.com', 'francesca_mazzone', 'pass_francesca', 'Francesca', 'Mazzone'),
    ('pasquale.napolitano@example.com', 'pasquale_napo', 'pass_pasquale', 'Pasquale', 'Napolitano'),
    ('federica.festa@example.com', 'fede_festa', 'pass_fede_festa', 'Federica', 'Festa'),
    ('sara.festa@example.com', 'sara_festa', 'pass_sara', 'Sara', 'Festa'),
    ('joshua.diroberto@example.com', 'joshua_diro', 'pass_joshua', 'Joshua', 'Di Roberto'),
    ('michele.poggi@example.com', 'michele_poggi', 'pass_michele', 'Michele', 'Poggi'),
    ('myriam.sorrentino@example.com', 'myriam_sorrentino', 'pass_myriam', 'Myriam', 'Sorrentino'),
    ('raffaele.ruggiero@example.com', 'raffaele_ruggiero', 'pass_raffaele', 'Raffaele', 'Ruggiero'),
    ('alessio.paduano@example.com', 'alessio_paduano', 'pass_alessio', 'Alessio', 'Paduano'),
    ('carmine.sgariglia@example.com', 'carmine_sgariglia', 'pass_carmine', 'Carmine', 'Sgariglia'),
    ('mattia.lemma@example.com', 'mattia_lemma', 'pass_mattia', 'Mattia', 'Lemma'),
    ('emanuele.palmieri@example.com', 'emanuele_palmieri', 'pass_emanuele_gp', 'Emanuele Gerardo', 'Palmieri'),
    ('matteo.tufano@example.com', 'matteo_tufano', 'pass_matteo', 'Matteo', 'Tufano'),
    ('james.sunderland@example.com', 'james_sh', 'pass_james', 'James', 'Sunderland'),
    ('clive.rosfield@example.com', 'clive_ff', 'pass_clive', 'Clive', 'Rosfield'),
    ('maria.sheperd@example.com', 'maria_sh', 'pass_maria', 'Maria', 'Sheperd'),
    ('angela.orosco@example.com', 'angela_sh', 'pass_angela', 'Angela', 'Orosco'),
    ('alan.wake@example.com', 'alan_writer', 'pass_alan', 'Alan', 'Wake'),
    ('jesse.faden@example.com', 'jesse_faden', 'pass_jesse', 'Jesse', 'Faden'),
    ('leon.kennedy@example.com', 'leon_re', 'pass_leon', 'Leon', 'Kennedy'),
    ('johan.liebert@example.com', 'johan_monster', 'pass_johan', 'Johan', 'Liebert'),
    ('alberto.angela@example.com', 'alberto_divulgatore', 'pass_alberto', 'Alberto', 'Angela'),
    ('akira.yamaoka@example.com', 'akira_sound', 'pass_akira', 'Akira', 'Yamaoka'),
    ('masahiro.ito@example.com', 'masahiro_art', 'pass_masahiro', 'Masahiro', 'Ito'),
    ('chris.redfield@example.com', 'chris_re', 'pass_chris_r', 'Chris', 'Redfield'),
    ('murphy.pendleton@example.com', 'murphy_sh', 'pass_murphy', 'Murphy', 'Pendleton');

-- Inserimento degli Organizzatori
INSERT INTO organizers (user_email)
VALUES
    ('diomede.mazzone@example.com'),
    ('simone.martino@example.com'),
    ('valeria.larco@example.com');

-- Creazione di 4 Hackathon di esempio (questi avranno ID 1, 2, 3, 4)
INSERT INTO hackathons (title, location, start_date, end_date, max_participants, max_team_size, registration_start, registration_end, problem_description, organizer_user_email)
VALUES
    ('Cyberpunk Challenge', 'Online', '2025-11-15 09:00:00', '2025-11-17 18:00:00', 100, 5, '2025-10-01 00:00:00', '2025-11-10 23:59:59', 'Sviluppare un prototipo di gioco ambientato in un futuro distopico.', 'diomede.mazzone@example.com'),
    ('Fantasy Coders Quest', 'Milano', '2025-12-05 09:00:00', '2025-12-07 18:00:00', 80, 4, '2025-11-01 00:00:00', '2025-11-30 23:59:59', 'Creare un''applicazione per la gestione di campagne di giochi di ruolo.', 'simone.martino@example.com'),
    ('Survival Horror Dev Jam', 'Roma', '2026-01-20 09:00:00', '2026-01-22 18:00:00', 120, 3, '2025-12-15 00:00:00', '2026-01-15 23:59:59', 'Progettare una meccanica di gioco innovativa per il genere survival horror.', 'valeria.larco@example.com'),
    ('Retro Gaming Revival', 'Online', '2026-02-10 09:00:00', '2026-02-12 18:00:00', 200, 10, '2026-01-01 00:00:00', '2026-02-05 23:59:59', 'Ricreare un classico del passato con tecnologie moderne.', 'diomede.mazzone@example.com');

-- Creazione dei Team (i leader sono alcuni dei partecipanti)
INSERT INTO teams (team_name, hackathon_id, max_members, leader_email)
VALUES
    ('Team Silent Hill', 3, 3, 'james.sunderland@example.com'),    -- Per Survival Horror
    ('Final Fantasy Crew', 2, 4, 'clive.rosfield@example.com'),   -- Per Fantasy Coders
    ('The Agency', 1, 5, 'jesse.faden@example.com'),             -- Per Cyberpunk
    ('Raccoon City Survivors', 3, 3, 'leon.kennedy@example.com'); -- Per Survival Horror

-- Assegnazione dei Giudici agli Hackathon (usando i nuovi ID 1, 2, 3)
INSERT INTO judges (user_email, hackathon_id)
VALUES
    ('hideo.kojima@example.com', 1),        -- Giudice per Cyberpunk Challenge
    ('hidetaka.miyazaki@example.com', 3),   -- Giudice per Survival Horror
    ('yoko.taro@example.com', 2);           -- Giudice per Fantasy Coders

-- Iscrizione e assegnazione di TUTTI i Partecipanti
INSERT INTO participants (user_email, hackathon_id, team_id)
VALUES
    -- Membri del Team Silent Hill (Hackathon 3)
    ('james.sunderland@example.com', 3, 1),
    ('maria.sheperd@example.com', 3, 1),
    ('angela.orosco@example.com', 3, 1),
    -- Membri del Final Fantasy Crew (Hackathon 2)
    ('clive.rosfield@example.com', 2, 2),
    ('alberto.angela@example.com', 2, 2),
    -- Membri del The Agency (Hackathon 1)
    ('jesse.faden@example.com', 1, 3),
    ('alan.wake@example.com', 1, 3),
    -- Membri del Raccoon City Survivors (Hackathon 3)
    ('leon.kennedy@example.com', 3, 4),
    ('chris.redfield@example.com', 3, 4),
    -- Altri partecipanti sparsi e senza team
    ('johan.liebert@example.com', 1, NULL),
    ('akira.yamaoka@example.com', 3, NULL),
    ('masahiro.ito@example.com', 3, NULL),
    ('murphy.pendleton@example.com', 4, NULL),
    ('christian.cappabianca@example.com', 1, NULL),
    ('manuel.fava@example.com', 1, NULL),
    ('davide.lugli@example.com', 2, NULL),
    ('federica.lubrano@example.com', 2, NULL),
    ('claudio.caianiello@example.com', 4, NULL),
    ('fabio.fascia@example.com', 4, NULL),
    ('francesca.mazzone@example.com', 1, NULL),
    ('pasquale.napolitano@example.com', 2, NULL),
    ('federica.festa@example.com', 3, NULL),
    ('sara.festa@example.com', 4, NULL),
    ('joshua.diroberto@example.com', 1, NULL),
    ('michele.poggi@example.com', 2, NULL),
    ('myriam.sorrentino@example.com', 4, NULL),
    ('raffaele.ruggiero@example.com', 1, NULL),
    ('alessio.paduano@example.com', 2, NULL),
    ('carmine.sgariglia@example.com', 3, NULL),
    ('mattia.lemma@example.com', 4, NULL),
    ('emanuele.palmieri@example.com', 1, NULL),
    ('matteo.tufano@example.com', 2, NULL);
