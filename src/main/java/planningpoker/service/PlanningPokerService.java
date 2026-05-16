package planningpoker.service;

import planningpoker.domain.Participant;
import planningpoker.domain.PlanningCard;
import planningpoker.domain.PlanningSession;
import planningpoker.domain.Story;
import planningpoker.domain.VotingResult;

public class PlanningPokerService {

    private PlanningSession session;
    private int participantIdCounter = 1;
    private int storyIdCounter = 1;

    public PlanningSession createSession(String name) {
        session = new PlanningSession("session-1", name);
        participantIdCounter = 1;
        storyIdCounter = 1;

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

    public Story addStory(String title, String description) {
        ensureSessionExists();

        Story story = new Story(
                String.valueOf(storyIdCounter),
                title,
                description
        );

        session.addStory(story);
        storyIdCounter++;

        return story;
    }

    public void selectStory(String storyId) {
        ensureSessionExists();
        session.selectStory(storyId);
    }

    public void startNewRound() {
        ensureSessionExists();
        session.startNewRound();
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

        return session.getCurrentRound().calculateResult();
    }

    public void resetRound() {
        ensureSessionExists();

        if (!session.isRevealed()) {
            throw new IllegalStateException("Current round must be revealed before starting a new one");
        }

        session.startNewRound();
    }

    public void defineFinalEstimate(PlanningCard card) {
        ensureSessionExists();
        session.getCurrentStory().defineFinalEstimate(card);
    }

    public void finishSession() {
        ensureSessionExists();
        session.finishSession();
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