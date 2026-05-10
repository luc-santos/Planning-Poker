# Planning Poker

CLI application developed in Java to simulate Agile Planning Poker sessions, focused on object-oriented design, clean architecture, and domain-driven modeling.

---

## Features

- Create planning sessions
- Add participants
- Register votes
- Reveal voting results
- Detect consensus
- Support special cards (?, ☕, ∞)
- Reset rounds
- Finish sessions
- Calculate averages and voting statistics
- Unit tests with JUnit 5

---

## Architecture

The project was designed following object-oriented design principles and responsibility-driven modeling using CRC Cards.

### Main Layers

- `app` → CLI interaction and application flow
- `service` → application orchestration and coordination
- `domain` → business rules and entities

- <img width="1024" height="1536" alt="image" src="https://github.com/user-attachments/assets/b94d5815-d1be-472b-8de5-4ed779878c01" />


---

## Project Structure

```text
src
├── main
│   └── java
│       └── planningpoker
│           ├── app
│           ├── domain
│           ├── service
│           └── Main.java
│
└── test
    └── java
        └── planningpoker
            └── domain
````

## Domain Concepts

PlanningSession

Central entity responsible for:


<li>Managing participants</li>
<li>Controlling voting flow</li>
<li>Registering votes</li>
<li>Revealing votes</li>
<li>Resetting rounds</li>
<li>Controlling session state</li>
<li>Participant</li>


Represents a participant in the planning session.

<h2>Responsibilities</h2>

<li>Store participant identity</li>
<li>Provide equality comparison through unique ID</li>
<li>Prevent duplicated logical participants</li>
<li>Vote</li>

Represents the relationship between a participant and a selected planning card.

<h2>Responsibilities</h2>

<li>Link participant and card</li>
<li>Prevent invalid votes</li>
<li>Support voting statistics</li>
<li>PlanningCard</li>

Represents available estimation cards.

<h2>Responsibilities</h2>


<li>Store estimation values</li>
<li>Support special cards (?, ☕, ∞)</li>
<li>Validate card values</li>
<li>Support comparisons and statistics</li>
<li>VotingResult</li>


Represents the final result of a voting round.

<h2>Responsibilities</h2>


<li>Calculate average values</li>
<li>Detect consensus</li>
<li>Detect discussion scenarios</li>
<li>Detect special voting situations</li>
<li>Provide final statistics</li>
<li>VotingStatus</li>


Represents the current state of the session.

<h2>Possible states</h2>


<li>VOTING</li>
<li>REVEALED</li>
<li>FINISHED</li>


Voting Flow

Create Session  
↓  
Add Participants  
↓  
Start Voting  
↓  
Reveal Votes  
↓  
Calculate Result  
↓  
Reset Round / Finish Session  

<h2>CRC Cards</h2>

The system design was modeled using CRC (Class-Responsibility-Collaborator) cards.

<h2>Tests</h2>

The project includes unit tests covering:

<li>Participant validation</li>
<li>Vote validation</li>
<li>Voting flow</li>
<li>Session rules</li>
<li>Voting state transitions</li>
<li>Planning card behavior</li>
<li>Equality and hashCode consistency</li>
<li>Consensus detection</li>
<li>Special card handling</li>

<h2>Running the Application</h2>

Clone repository

``git clone https://github.com/luc-santos/Planning-Poker.git``

Open with IntelliJ IDEA  

Open the project root folder in IntelliJ IDEA.  

Run  

Execute:  

``Main.java``

## CLI Preview  

```bash
=== Planning Poker ===

Session name: Backend API

Participant name: Mariana
Participant name: Lucas

Available cards:
0 1/2 1 2 3 5 8 13 20 40 100 ? ∞ ☕

Voting started

Mariana, choose a card:
Lucas, choose a card:

All participants have voted.

Reveal votes? yes

=== Votes ===

Mariana voted 5
Lucas voted 8
```

## Technologies

<li>Java</li>
<li>JUnit 5</li>
<li>IntelliJ IDEA</li>

## Future Improvements

<li>Multiple voting rounds</li>
<li>Story/task estimation support</li>
<li>REST API with Spring Boot</li>
<li>PostgreSQL persistence</li>
<li>Docker support</li>
<li>Real-time voting with WebSockets</li>
<li>Web interface</li>
<li>Authentication system</li>

## Design Goals

This project was developed to practice:  

<li>Object-oriented design</li>
<li>Responsibility-driven development</li>
<li>Domain modeling</li>
<li>Clean architecture principles</li>
<li>Encapsulation</li>
<li>Equality and identity handling</li>
<li>Session state management</li>
<li>Unit testing</li>
<li>Java backend fundamentals</li>

## Author

Lucas Santos Cunha

linkedin: https://www.linkedin.com/in/lucas-santos-cunha-00673734a/
