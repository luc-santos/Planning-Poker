package planningpoker.app;

import planningpoker.domain.Participant;
import planningpoker.domain.PlanningCard;
import planningpoker.domain.Story;
import planningpoker.domain.Vote;
import planningpoker.domain.VotingResult;
import planningpoker.service.PlanningPokerService;

import java.util.Scanner;

public class PlanningPokerApp {

    private final Scanner scanner = new Scanner(System.in);
    private final PlanningPokerService service = new PlanningPokerService();

    public void run() {
        printHeader();

        createSession();
        addParticipants();

        if (service.getSession().getParticipants().isEmpty()) {
            System.out.println("\nNo participants added. Ending application.");
            return;
        }

        createStory();
        service.startNewRound();

        boolean consensus = false;

        while (!consensus) {
            printRoundHeader();
            showCards();
            collectVotes();
            revealAndShowResult();

            VotingResult result = service.getResult();
            consensus = result.hasConsensus();

            if (!consensus && shouldStartNewRound()) {
                service.resetRound();
            } else {
                break;
            }
        }

        finishSession();
    }

    private void printHeader() {
        System.out.println("==================================");
        System.out.println("          PLANNING POKER          ");
        System.out.println("==================================");
    }
    private void createSession() {
        System.out.print("\nSession name: ");
        String sessionName = scanner.nextLine();

        service.createSession(sessionName);

        System.out.println("Session created: " + service.getSession().getName());
    }

    private void addParticipants() {
        System.out.println("\n--- Add Participants ---");
        System.out.println("Type an empty name to finish.");

        while (true) {
            System.out.print("Participant name: ");
            String name = scanner.nextLine();

            if (name.trim().isEmpty()) {
                break;
            }

            Participant participant = service.addParticipant(name);
        }

        System.out.println("Total participants: " + service.getSession().getParticipants().size());
    }

    private void createStory() {
        System.out.println("\n--- Create Story ---");

        System.out.print("Story title: ");
        String title = scanner.nextLine();

        System.out.print("Story description: ");
        String description = scanner.nextLine();

        Story story = service.addStory(title, description);

        System.out.println("Story created: " + story.getTitle());
    }

    private void printRoundHeader() {
        System.out.println("\n==================================");
        System.out.println("Session: " + service.getSession().getName());
        System.out.println("Story: " + service.getSession().getCurrentStory().getTitle());
        System.out.println("Round: " + service.getSession().getCurrentRound().getNumber());
        System.out.println("==================================");
    }

    private void showCards() {
        System.out.println("\nAvailable cards:");

        for (PlanningCard card : PlanningCard.values()) {
            System.out.print("[" + card.getLabel() + "] ");
        }

        System.out.println();
    }

    private void collectVotes() {
        System.out.println("\n--- Voting Started ---");

        for (Participant participant : service.getSession().getParticipants()) {
            System.out.println("\nParticipant: " + participant.getName());

            PlanningCard card = askCard(participant);

            service.vote(participant.getId(), card);

            System.out.println("Vote registered successfully.");
            System.out.println("----------------------------------");
        }
    }

    private PlanningCard askCard(Participant participant) {
        while (true) {
            System.out.print(participant.getName() + ", choose a card: ");
            String input = scanner.nextLine();

            PlanningCard card = PlanningCard.fromString(input);

            if (card != null) {
                return card;
            }

            System.out.println("Invalid card. Try again.");
        }
    }

    private void revealAndShowResult() {
        service.revealVotes();

        System.out.println("\n--- Votes Revealed ---");

        for (Vote vote : service.getSession().getVotes()) {
            System.out.println("- " + vote);
        }

        VotingResult result = service.getResult();

        System.out.println("\n--- Result ---");
        System.out.println(result);
    }

    private boolean shouldStartNewRound() {
        System.out.println("\nNo consensus reached.");
        System.out.print("Start a new round? (y/n): ");

        String answer = scanner.nextLine();

        return answer.equalsIgnoreCase("y");
    }

    private void finishSession() {
        service.finishSession();

        System.out.println("\n==================================");
        System.out.println("Session finished.");
        System.out.println("Final status: " + service.getSession().getStatus());
        System.out.println("==================================");
    }
}