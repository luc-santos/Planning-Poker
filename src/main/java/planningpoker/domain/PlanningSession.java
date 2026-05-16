package planningpoker.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlanningSession {

    private final String id;
    private final String name;
    private final Map<String, Participant> participants;
    private final Map<String, Story> stories;
    private Story currentStory;
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
        this.stories = new LinkedHashMap<>();
        this.currentStory = null;
        this.status = VotingStatus.VOTING;
    }

    public void addParticipant(Participant participant) {
        if (status == VotingStatus.FINISHED) {
            throw new IllegalStateException("Cannot add participant to a finished session");
        }

        if (participant == null) {
            throw new IllegalArgumentException("Participant cannot be null");
        }

        participants.put(participant.getId(), participant);
    }

    public void removeParticipant(String participantId) {
        if (status == VotingStatus.FINISHED) {
            throw new IllegalStateException("Cannot remove participant from a finished session");
        }

        if (participantId == null || participantId.trim().isEmpty()) {
            throw new IllegalArgumentException("Participant id cannot be null or empty");
        }

        participants.remove(participantId.trim());
    }

    public boolean hasParticipant(String participantId) {
        if (participantId == null || participantId.trim().isEmpty()) {
            return false;
        }

        return participants.containsKey(participantId.trim());
    }

    public Participant getParticipant(String participantId) {
        if (participantId == null || participantId.trim().isEmpty()) {
            throw new IllegalArgumentException("Participant id cannot be null or empty");
        }

        Participant participant = participants.get(participantId.trim());

        if (participant == null) {
            throw new IllegalArgumentException("Participant is not in this session");
        }

        return participant;
    }

    public void addStory(Story story) {
        if (status == VotingStatus.FINISHED) {
            throw new IllegalStateException("Cannot add story to a finished session");
        }

        if (story == null) {
            throw new IllegalArgumentException("Story cannot be null");
        }

        stories.put(story.getId(), story);

        if (currentStory == null) {
            currentStory = story;
        }
    }

    public boolean hasStory(String storyId) {
        if (storyId == null || storyId.trim().isEmpty()) {
            return false;
        }

        return stories.containsKey(storyId.trim());
    }

    public Story getStory(String storyId) {
        if (storyId == null || storyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Story id cannot be null or empty");
        }

        Story story = stories.get(storyId.trim());

        if (story == null) {
            throw new IllegalArgumentException("Story is not in this session");
        }

        return story;
    }

    public void selectStory(String storyId) {
        currentStory = getStory(storyId);
        status = VotingStatus.VOTING;
    }

    public void startNewRound() {
        if (currentStory == null) {
            throw new IllegalStateException("No story selected");
        }

        if (status == VotingStatus.FINISHED) {
            throw new IllegalStateException("Cannot start round in a finished session");
        }

        int roundNumber = currentStory.getRounds().size() + 1;
        Round round = new Round(roundNumber);

        currentStory.addRound(round);
        status = VotingStatus.VOTING;
    }

    public Round getCurrentRound() {
        if (currentStory == null) {
            throw new IllegalStateException("No story selected");
        }

        if (currentStory.getRounds().isEmpty()) {
            throw new IllegalStateException("No round started for current story");
        }

        return currentStory.getRounds()
                .get(currentStory.getRounds().size() - 1);
    }

    public void vote(String participantId, PlanningCard card) {
        Participant participant = getParticipant(participantId);

        Round currentRound = getCurrentRound();
        currentRound.vote(participant, card);
    }

    public boolean hasVoted(String participantId) {
        if (!hasParticipant(participantId)) {
            return false;
        }

        try {
            Participant participant = getParticipant(participantId);
            return getCurrentRound().hasVoted(participant);
        } catch (IllegalStateException exception) {
            return false;
        }
    }

    public boolean allParticipantsVoted() {
        if (participants.isEmpty()) {
            return false;
        }

        try {
            return getCurrentRound().getVotes().size() == participants.size();
        } catch (IllegalStateException exception) {
            return false;
        }
    }

    public void revealVotes() {
        if (!allParticipantsVoted()) {
            throw new IllegalStateException("Not all participants voted yet");
        }

        getCurrentRound().reveal();
        status = VotingStatus.REVEALED;
    }

    public void finishSession() {
        status = VotingStatus.FINISHED;
    }

    public Collection<Participant> getParticipants() {
        return new ArrayList<>(participants.values());
    }

    public Collection<Story> getStories() {
        return new ArrayList<>(stories.values());
    }

    public Story getCurrentStory() {
        if (currentStory == null) {
            throw new IllegalStateException("No story selected");
        }

        return currentStory;
    }

    public Collection<Vote> getVotes() {
        return getCurrentRound().getVotes();
    }

    public boolean isRevealed() {
        return status == VotingStatus.REVEALED;
    }

    public VotingStatus getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}