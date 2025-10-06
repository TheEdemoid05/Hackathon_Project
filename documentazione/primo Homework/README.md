# 📘 Relazioni sul diagramma delle Classi - Sistema di gestione Hackathon

<br>

>

# 🎯 Obiettivo del progetto

L'obiettivo di questo homework è la realizzazione di un diagramma delle classi che
rappresenti un sistema di gestione per una piattaforma dedicata all’organizzazione di un
hackathon, in cui utenti registrati possono partecipare a una competizione, formando team e
lavorando insieme allo sviluppo di soluzioni innovative. Il diagramma è stato progettato per
rappresentare in modo chiaro ed efficace le principali entità coinvolte e le relative attività
all’interno della piattaforma, con un focus sui loro ruoli, sulle funzionalità chiave e sulla
gestione completa dell’evento.

Per fare ciò, abbiamo realizzato il seguente UML:

# 📊 UML

<img src="image.png" alt="Diagramma UML" width="700"/>
<br>

# 💡 Motivazione per ogni classe scelta

- ### 🏁 hackathon:

  La classe Hackathon rappresenta tutti gli eventi che sono svolti sulla piattaforma, a cui
  l'utente può iscriversi, con un periodo di svolgimento (data di inizio e fine) e con un periodo
  di iscrizione(inizio e fine). Ha un titolo che lo identifica, un limite massimo di partecipanti ed
  è gestito da un organizzatore.

- ### 👤 UtenteRegistrato:

  Rappresenta tutti gli utenti che si sono registrati alla piattaforma e che possono partecipare
  all' hackathon. È una classe fondamentale, dato che da essa derivano diversi ruoli come
  partecipante, organizzatore e giudice. Si è deciso di realizzare questa classe come punto di
  partenza comune per tutti gli utenti nella piattaforma. Questa classe ha tutte le informazioni
  generiche degli utenti, ossia le credenziali d'accesso e i loro dati identificativi (es: nome,
  username e password), e avrà modo di specializzarsi nelle tre sottoclassi sopracitate.

- ### 🙋 Partecipante:

  Rappresenta tutti gli utenti che prendono parte a un hackathon con il ruolo attivo di
  concorrenti. Questa specializzazione può creare dei Team e mandare i documenti con la
  possibile soluzione al problema fornito dai giudici. Siccome è un’estensione di
  UtenteRegistrato, Partecipante eredita tutti i metodi della classe generale, in modo tale che
  solo gli utenti registrati possano scegliere di partecipare all’evento. Inoltre, un partecipante
  può appartenere ad un team oppure unirsi successivamente tramite invito. Tale invito può
  essere inviato ad un altro partecipante con la possibilità di accettare o meno la richiesta.

- ### 🧑‍⚖️ Giudici:

  E’ un’altra specializzazione della classe UtenteRegistrato. Questo ruolo ha due responsabilità
  principali: pubblicare il problema dell’evento e dare giudizi e voti (in un range da 1 a 10) ai
  documenti caricati dai partecipanti.

- ### 🧑‍💼 Organizzatore:

  E’ l’ultima specializzazione di UtenteRegistrato. Questo ruolo ha la responsabilità di gestire
  l’hackathon sotto il profilo amministrativo.
  Si occupa di selezionare e invitare i giudici tra gli utenti registrati alla piattaforma e di aprire
  il periodo di iscrizione all’hackathon.

- ### 👨‍👩‍👧‍👦 Team:

  Rappresenta un gruppo di partecipanti, che collaborano durante l'hackathon per risolvere il
  problema fornito dai giudici. Ogni team ha un massimo di membri che possono comporlo e i
  vari team potranno inviare dei documenti, i quali verranno valutati con commenti e voti dai
  giudici dell'evento.

- ### 📄 Documento:

  Il documento verrà creato dai team per i giudici e verrà aggiornato. Conterrà la possibile
  risoluzione al problema posto dai giudici.

- ### 🏆 Voto:

  Il voto assegnato ad ogni team in base al documento finale è deciso dai giudici. Il voto può
  essere in un range da 1 a 10 e il risultato delle votazioni finali verrà decretato dalla somma
  dei voti dei tre giudici

- ### 📬 Richiesta:
  La richiesta può essere inviata solo da un partecipante già facente parte di un team non
  completo verso un altro partecipante. Nell’invito dovrà includere un messaggio
  motivazionale per il possibile nuovo compagno (“possibile” perché la richiesta può essere
  anche rifiutata).
