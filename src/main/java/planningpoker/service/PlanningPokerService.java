package planningpoker.service;

import planningpoker.domain.Participant;
import planningpoker.domain.PlanningCard;
import planningpoker.domain.PlanningSession;
import planningpoker.domain.VotingResult;

public class PlanningPokerService {

    private PlanningSession session;
    private int participantIdCounter = 1;

    public PlanningSession createSession(String name) {
        session = new PlanningSession("session-1", name);
        return session;
    }

    public Participant addParticipant(String name) {
        ensureSessionExists();

        Participant participant = new Participant(
                String.valueOf(participantIdCounter),
                name
        );

        session.addParticipant(participant);
        participantIdCounter++;

        return participant;
    }

    public void vote(String participantId, PlanningCard card) {
        ensureSessionExists();
        session.vote(participantId, card);
    }

    public void revealVotes() {
        ensureSessionExists();
        session.revealVotes();
    }

    public VotingResult getResult() {
        ensureSessionExists();

        if (!session.isRevealed()) {
            throw new IllegalStateException("Votes must be revealed before getting result");
        }

        return new VotingResult(session.getVotes());
    }

    public void resetRound() {
        ensureSessionExists();
        session.resetVotes();
    }

    public PlanningSession getSession() {
        ensureSessionExists();
        return session;
    }

    private void ensureSessionExists() {
        if (session == null) {
            throw new IllegalStateException("Session has not been created yet");
        }
    }
}