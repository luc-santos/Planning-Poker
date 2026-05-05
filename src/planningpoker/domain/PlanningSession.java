package planningpoker.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlanningSession {

    private final String id;
    private final String name;
    private final Map<String, Participant> participants;
    private final Map<Participant, Vote> votes;
    private boolean revealed;

    public PlanningSession(String id, String name) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Session id cannot be null or empty");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Session name cannot be null or empty");
        }

        this.id = id.trim();
        this.name = name.trim();
        this.participants = new LinkedHashMap<>();
        this.votes = new LinkedHashMap<>();
        this.revealed = false;
    }

    public void addParticipant(Participant participant) {
        if (revealed) {
            throw new IllegalStateException("Cannot add participant after votes are revealed");
        }

        if (participant == null) {
            throw new IllegalArgumentException("Participant cannot be null");
        }

        participants.put(participant.getId(), participant);
    }

    public void removeParticipant(String participantId) {
        if (revealed) {
            throw new IllegalStateException("Cannot remove participant after votes are revealed");
        }

        if (participantId == null || participantId.trim().isEmpty()) {
            throw new IllegalArgumentException("Participant id cannot be null or empty");
        }

        Participant participant = participants.remove(participantId.trim());

        if (participant != null) {
            votes.remove(participant);
        }
    }

    public void vote(String participantId, PlanningCard card) {
        if (revealed) {
            throw new IllegalStateException("Cannot vote after votes are revealed");
        }

        if (participantId == null || participantId.trim().isEmpty()) {
            throw new IllegalArgumentException("Participant id cannot be null or empty");
        }

        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null");
        }

        Participant participant = participants.get(participantId.trim());

        if (participant == null) {
            throw new IllegalArgumentException("Participant is not in this session");
        }

        Vote vote = new Vote(participant, card);
        votes.put(participant, vote);
    }

    public boolean hasParticipant(String participantId) {
        if (participantId == null || participantId.trim().isEmpty()) {
            return false;
        }

        return participants.containsKey(participantId.trim());
    }

    public boolean hasVoted(String participantId) {
        if (participantId == null || participantId.trim().isEmpty()) {
            return false;
        }

        Participant participant = participants.get(participantId.trim());

        if (participant == null) {
            return false;
        }

        return votes.containsKey(participant);
    }

    public boolean allParticipantsVoted() {
        return !participants.isEmpty() && votes.size() == participants.size();
    }

    public void revealVotes() {
        if (!allParticipantsVoted()) {
            throw new IllegalStateException("Not all participants voted yet");
        }

        revealed = true;
    }

    public void resetVotes() {
        votes.clear();
        revealed = false;
    }

    public Collection<Participant> getParticipants() {
        return new ArrayList<>(participants.values());
    }

    public Collection<Vote> getVotes() {
        return new ArrayList<>(votes.values());
    }

    public boolean isRevealed() {
        return revealed;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}