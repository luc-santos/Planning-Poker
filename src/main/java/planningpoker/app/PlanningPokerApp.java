package planningpoker.app;

import planningpoker.domain.Participant;
import planningpoker.domain.PlanningCard;
import planningpoker.domain.Vote;
import planningpoker.domain.VotingResult;
import planningpoker.service.PlanningPokerService;

import java.util.Scanner;

public class PlanningPokerApp {

    private final Scanner scanner = new Scanner(System.in);
    private final PlanningPokerService service = new PlanningPokerService();

    public void run() {
        createSession();
        addParticipants();

        if (service.getSession().getParticipants().isEmpty()) {
            System.out.println("No participants added. Ending application.");
            return;
        }

        boolean consensus = false;

        while (!consensus) {
            showCards();
            collectVotes();
            revealAndShowResult();

            VotingResult result = service.getResult();
            consensus = result.hasConsensus();

            if (!consensus) {
                System.out.println("\nNo consensus reached.");
                System.out.println("Starting a new round...\n");

                service.resetRound();
            }
        }

        System.out.println("\nConsensus reached. Session finished.");
    }

    private void createSession() {
        System.out.println("=== Planning Poker ===");

        System.out.print("Session name: ");
        String sessionName = scanner.nextLine();

        service.createSession(sessionName);
    }

    private void addParticipants() {
        System.out.println("\nAdd participants");
        System.out.println("Type an empty name to finish.");

        while (true) {
            System.out.print("Participant name: ");
            String name = scanner.nextLine();

            if (name.trim().isEmpty()) {
                break;
            }

            service.addParticipant(name);
        }
    }

    private void showCards() {
        System.out.println("\nAvailable cards:");

        for (PlanningCard card : PlanningCard.values()) {
            System.out.print(card.getLabel() + " ");
        }

        System.out.println();
    }

    private void collectVotes() {
        System.out.println("\nVoting started");

        for (Participant participant : service.getSession().getParticipants()) {
            PlanningCard card = askCard(participant);
            service.vote(participant.getId(), card);
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

        System.out.println("\n=== Votes ===");

        for (Vote vote : service.getSession().getVotes()) {
            System.out.println(vote);
        }

        VotingResult result = service.getResult();

        System.out.println("\n=== Result ===");
        System.out.println(result);
    }
}