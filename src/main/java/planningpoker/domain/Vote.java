package planningpoker.domain;

public class Vote {

    private final Participant participant;
    private final PlanningCard card;

    public Vote(Participant participant, PlanningCard card) {
        if (participant == null) {
            throw new IllegalArgumentException("Participant cannot be null");
        }

        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null");
        }

        this.participant = participant;
        this.card = card;
    }

    public Participant getParticipant() {
        return participant;
    }

    public PlanningCard getCard() {
        return card;
    }

    public boolean isNumericVote() {
        return card.isNumeric();
    }

    @Override
    public String toString() {
        return participant + " voted " + card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;

        Vote that = (Vote) o;
        return participant.equals(that.participant);
    }

    @Override
    public int hashCode() {
        return participant.hashCode();
    }
}
