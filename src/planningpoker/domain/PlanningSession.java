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
    private VotingStatus status;

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
        this.status = VotingStatus.VOTING;
    }

    public VotingStatus getStatus() {
        return status;
    }

    public void addParticipant(Participant participant) {
        if (status == VotingStatus.REVEALED) {
            throw new IllegalStateException("Cannot add participant after votes are revealed");
        }

        if (participant == null) {
            throw new IllegalArgumentException("Participant cannot be null");
        }

        participants.put(participant.getId(), participant);
    }

    public void removeParticipant(String participantId) {
        if (status == VotingStatus.REVEALED) {
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
        if (status != VotingStatus.VOTING) {
            throw new IllegalStateException("Voting is closed");
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

       status = VotingStatus.REVEALED;
    }

    public void resetVotes() {
        if(status == VotingStatus.FINISHED) {
            throw new IllegalStateException("Session is finished");
        }
        votes.clear();
        status = VotingStatus.VOTING;
    }

    public Collection<Participant> getParticipants() {
        return new ArrayList<>(participants.values());
    }

    public Collection<Vote> getVotes() {
        return new ArrayList<>(votes.values());
    }

    public boolean isRevealed() {
        return status == VotingStatus.REVEALED;
    }

    public void finishSession() {
        status = VotingStatus.FINISHED;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}