package planningpoker.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Round {

    private final int number;

    private final Map<Participant, Vote> votes;

    private VotingStatus status;

    public Round(int number) {

        if (number <= 0) {
            throw new IllegalArgumentException("Round number must be greater than zero");
        }

        this.number = number;

        this.votes = new LinkedHashMap<>();

        this.status = VotingStatus.VOTING;
    }

    public void vote(Participant participant, PlanningCard card) {

        if (status != VotingStatus.VOTING) {
            throw new IllegalStateException("Round is not accepting votes");
        }

        Vote vote = new Vote(participant, card);

        votes.put(participant, vote);
    }

    public boolean hasVoted(Participant participant) {
        if (participant == null) {
            return false;
        }

        return votes.containsKey(participant);
    }

    public void reveal() {

        if (votes.isEmpty()) {
            throw new IllegalStateException("Cannot reveal empty round");
        }

        status = VotingStatus.REVEALED;
    }

    public void finish() {
        status = VotingStatus.FINISHED;
    }

    public VotingResult calculateResult() {

        if (status == VotingStatus.VOTING) {
            throw new IllegalStateException("Votes must be revealed first");
        }

        return new VotingResult(votes.values());
    }

    public Collection<Vote> getVotes() {
        return votes.values();
    }

    public int getNumber() {
        return number;
    }

    public VotingStatus getStatus() {
        return status;
    }
}