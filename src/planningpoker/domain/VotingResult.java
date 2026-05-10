package planningpoker.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class VotingResult {

    private final Collection<Vote> votes;

    public VotingResult(Collection<Vote> votes) {
        if (votes == null) {
            throw new IllegalArgumentException("Votes cannot be null");
        }

        this.votes = votes;
    }

    public int getTotalVotes() {
        return votes.size();
    }

    public int getNumericVotesCount() {
        int count = 0;

        for (Vote vote : votes) {
            if (vote.isNumericVote()) {
                count++;
            }
        }

        return count;
    }

    public boolean hasQuestion() {
        return hasCard(PlanningCard.QUESTION);
    }

    public boolean hasCoffee() {
        return hasCard(PlanningCard.COFFEE);
    }

    public boolean hasInfinity() {
        return hasCard(PlanningCard.INFINITY);
    }

    private boolean hasCard(PlanningCard targetCard) {
        for (Vote vote : votes) {
            if (vote.getCard() == targetCard) {
                return true;
            }
        }

        return false;
    }

    public double getAverage() {
        double sum = 0;
        int count = 0;

        for (Vote vote : votes) {
            if (vote.isNumericVote()) {
                sum += vote.getCard().getValue();
                count++;
            }
        }

        if (count == 0) {
            return 0;
        }

        return sum / count;
    }

    public Double getMinValue() {
        Double min = null;

        for (Vote vote : votes) {
            if (vote.isNumericVote()) {
                Double value = vote.getCard().getValue();

                if (min == null || value < min) {
                    min = value;
                }
            }
        }

        return min;
    }

    public Double getMaxValue() {
        Double max = null;

        for (Vote vote : votes) {
            if (vote.isNumericVote()) {
                Double value = vote.getCard().getValue();

                if (max == null || value > max) {
                    max = value;
                }
            }
        }

        return max;
    }

    public boolean hasConsensus() {
        PlanningCard firstCard = null;

        for (Vote vote : votes) {
            if (vote.isNumericVote()) {
                if (firstCard == null) {
                    firstCard = vote.getCard();
                } else if (vote.getCard() != firstCard) {
                    return false;
                }
            }
        }

        return firstCard != null;
    }

    public PlanningCard getMostVotedCard() {
        Map<PlanningCard, Integer> countByCard = new LinkedHashMap<>();

        for (Vote vote : votes) {
            PlanningCard card = vote.getCard();
            countByCard.put(card, countByCard.getOrDefault(card, 0) + 1);
        }

        PlanningCard mostVoted = null;
        int highestCount = 0;

        for (PlanningCard card : countByCard.keySet()) {
            int count = countByCard.get(card);

            if (count > highestCount) {
                highestCount = count;
                mostVoted = card;
            }
        }

        return mostVoted;
    }

    public Map<PlanningCard, Integer> getVoteDistribution() {
        Map<PlanningCard, Integer> distribution = new LinkedHashMap<>();

        for (Vote vote : votes) {
            PlanningCard card = vote.getCard();
            distribution.put(card, distribution.getOrDefault(card, 0) + 1);
        }

        return distribution;
    }

    public VotingOutcome getStatus() {
        if (getNumericVotesCount() == 0 && hasCoffee()) {
            return VotingOutcome.ONLY_COFFEE;
        }

        if (getNumericVotesCount() == 0) {
            return VotingOutcome.NO_NUMERIC_VOTES;
        }

        if (hasInfinity()) {
            return VotingOutcome.TOO_LARGE;
        }

        if (hasQuestion()) {
            return VotingOutcome.NEEDS_DISCUSSION;
        }

        if (hasConsensus()) {
            return VotingOutcome.CONSENSUS;
        }

        return VotingOutcome.NEEDS_DISCUSSION;
    }

    @Override
    public String toString() {
        return "Total votes: " + getTotalVotes()
                + "\nNumeric votes: " + getNumericVotesCount()
                + "\nAverage: " + getAverage()
                + "\nMin: " + getMinValue()
                + "\nMax: " + getMaxValue()
                + "\nMost voted: " + getMostVotedCard()
                + "\nHas question: " + hasQuestion()
                + "\nHas infinity: " + hasInfinity()
                + "\nHas coffee: " + hasCoffee()
                + "\nConsensus: " + hasConsensus()
                + "\nStatus: " + getStatus();
    }
}