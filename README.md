# Sistema di Gestione Hackathon

Un sistema completo per la gestione di hackathon sviluppato in Java con interfaccia grafica Swing e database PostgreSQL.

## 📋 Indice

- [Panoramica](#panoramica)
- [Caratteristiche](#caratteristiche)
- [Architettura](#architettura)
- [Tecnologie Utilizzate](#tecnologie-utilizzate)
- [Prerequisiti](#prerequisiti)
- [Installazione](#installazione)
- [Uso](#uso)
- [Documentazione Javadoc](#documentazione-javadoc)
- [Struttura del Progetto](#struttura-del-progetto)
- [Autori](#autori)

## 🎯 Panoramica

Il Sistema di Gestione Hackathon è un'applicazione desktop che consente di organizzare, gestire e partecipare a hackathon. Il sistema supporta diversi tipi di utenti (partecipanti, organizzatori, giudici) con funzionalità specifiche per ciascun ruolo.

## ✨ Caratteristiche

### 👥 Gestione Utenti
- **Registrazione e autenticazione** degli utenti
- **Ruoli multipli**: USER, PARTICIPANT, ORGANIZER, JUDGE
- **Profili utente** personalizzabili

### 🏆 Gestione Hackathon
- **Creazione di hackathon** con date, location e descrizione problemi
- **Registrazioni** con periodi definiti
- **Limiti di partecipazione** configurabili
- **Gestione completa del ciclo di vita** degli eventi

### 👨‍💻 Gestione Team
- **Formazione team** con leader e membri
- **Richieste di partecipazione** ai team
- **Gestione membri** e dimensioni massime
- **Progetti team** con documentazione

### ⚖️ Sistema di Valutazione
- **Giudici specializzati** per hackathon
- **Sistema di voti** per progetti
- **Valutazione trasparente** dei risultati

## 🏗️ Architettura

Il sistema segue un'architettura a strati ben definita:

```
┌─────────────────────┐
│   Presentation      │  ← GUI (Swing)
│     Layer           │
├─────────────────────┤
│   Controller        │  ← ControllerGui
│     Layer           │
├─────────────────────┤
│   Business          │  ← Model Classes
│     Layer           │
├─────────────────────┤
│   Data Access       │  ← DAO Pattern
│     Layer           │
├─────────────────────┤
│   Database          │  ← PostgreSQL
│     Layer           │
└─────────────────────┘
```

### Componenti Principali

- **`com.example.gui`**: Interfacce grafiche utente
- **`com.example.controller`**: Logica di controllo (MVC)
- **`com.example.model`**: Modelli di dominio
- **`com.example.dao`**: Interfacce Data Access Object
- **`com.example.daoimp`**: Implementazioni DAO
- **`com.example.database`**: Gestione connessioni database

## 🛠️ Tecnologie Utilizzate

- **Java 17** - Linguaggio di programmazione principale
- **Maven** - Gestione dipendenze e build
- **PostgreSQL** - Database relazionale
- **JDBC** - Connettività database
- **Swing** - Interfaccia grafica utente
- **Javadoc** - Documentazione del codice

## 📋 Prerequisiti

Prima di installare il sistema, assicurati di avere:

- **Java JDK 17** o superiore
- **Maven 3.6** o superiore
- **PostgreSQL 12** o superiore
- **Git** (per clonare il repository)

## 🚀 Installazione

### 1. Clona il Repository
```bash
git clone [URL-del-repository]
cd ProgettoOO\ pulito
```

### 2. Configura il Database
```sql
-- Crea il database PostgreSQL
CREATE DATABASE Hackathon;
-- Configura l'utente (modifica le credenziali se necessario)
```

### 3. Configura la Connessione
Modifica le credenziali in `src/main/java/com/example/database/DBConnection.java`:
```java
private static final String URL = "jdbc:postgresql://localhost:5432/Hackathon";
private static final String USER = "postgres";
private static final String PASSWORD = "TuaPassword";
```

### 4. Compila il Progetto
```bash
mvn clean compile
```

## 🎮 Uso

### Avvio dell'Applicazione
```bash
mvn exec:java
```

Oppure utilizza il tuo IDE per eseguire la classe `com.example.Main`.

### Funzionalità Principali

1. **Registrazione/Login**: Crea un account o accedi
2. **Partecipazione**: Iscriviti agli hackathon disponibili
3. **Team Management**: Crea o unisciti a un team
4. **Gestione Progetti**: Carica documenti e progetti
5. **Valutazione**: (Per i giudici) Valuta i progetti

## 📚 Documentazione Javadoc

Questo progetto include una documentazione Javadoc completa e professionale.

### Generazione Automatica

#### Opzione 1: Script Batch (Windows)
```bash
./generate-javadoc.bat
```

#### Opzione 2: Maven Command
```bash
mvn javadoc:javadoc
```

#### Opzione 3: Maven con Configurazioni Personalizzate
```bash
mvn clean javadoc:javadoc -Dmaven.javadoc.failOnError=false
```

### Accesso alla Documentazione

Dopo la generazione, la documentazione sarà disponibile in:
- **Maven**: `target/site/apidocs/index.html`
- **Script**: `docs/javadoc/index.html`

### Caratteristiche della Documentazione

- **Documentazione completa** per tutte le classi pubbliche
- **Commenti in italiano** per migliore comprensione
- **Cross-references** tra classi correlate
- **Esempi d'uso** dove appropriato
- **Informazioni su autori e versioni**
- **Package overview** dettagliati

## 📁 Struttura del Progetto

```
src/main/java/com/example/
├── Main.java                    # Classe principale
├── controller/
│   └── ControllerGui.java       # Controller MVC
├── dao/                         # Interfacce DAO
│   ├── UserDAO.java
│   ├── HackathonDAO.java
│   └── ...
├── daoimp/                      # Implementazioni DAO
│   ├── UserDaoImpl.java
│   └── ...
├── database/
│   └── DBConnection.java        # Gestione connessioni DB
├── gui/                         # Interfacce grafiche
│   ├── Home.java
│   ├── LoginPage.java
│   ├── team/                    # GUI gestione team
│   └── ...
└── model/                       # Modelli di dominio
    ├── User.java
    ├── Hackathon.java
    ├── Team.java
    └── ...
```

## 🔧 Configurazione Maven

Il progetto include configurazioni Maven ottimizzate per:

### Plugin Javadoc
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>3.6.3</version>
    <configuration>
        <windowTitle>Hackathon Management System - Javadoc</windowTitle>
        <doctitle>Hackathon Management System API Documentation</doctitle>
        <show>private</show>
        <failOnError>false</failOnError>
    </configuration>
</plugin>
```

### Dipendenze
- **PostgreSQL JDBC Driver** (42.7.5)
- **Java 17** compatibility

## 📝 Note per lo Sviluppo

### Best Practices Implementate
- **Pattern DAO** per l'accesso ai dati
- **Pattern MVC** per l'architettura
- **Gestione transazioni** JDBC
- **Documentazione Javadoc** completa
- **Separazione delle responsabilità**

### Sicurezza
⚠️ **Nota**: In produzione, implementare:
- Hash delle password
- Configurazione esterna delle credenziali DB
- Validazione avanzata degli input
- Logging delle operazioni

## 👨‍💻 Autori

**Team di Sviluppo** - Progetto Programmazione Orientata agli Oggetti

---

## 📞 Supporto

Per problemi o domande:
1. Consulta la **documentazione Javadoc**
2. Verifica la **configurazione del database**
3. Controlla i **log dell'applicazione**

---

*Sviluppato con ❤️ utilizzando Java e le migliori pratiche di sviluppo software.*# Hackathon_Project
