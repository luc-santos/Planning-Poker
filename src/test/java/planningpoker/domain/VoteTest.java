package planningpoker.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoteTest {

    @Test
    void shouldCreateVoteSuccessfully() {
        Participant participant = new Participant("1", "Mariana");
        Vote vote = new Vote(participant, PlanningCard.FIVE);

        assertEquals(participant, vote.getParticipant());
        assertEquals(PlanningCard.FIVE, vote.getCard());
    }

    @Test
    void shouldThrowExceptionWhenParticipantIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Vote(null, PlanningCard.FIVE)
        );
    }

    @Test
    void shouldThrowExceptionWhenCardIsNull() {
        Participant participant = new Participant("1", "Mariana");

        assertThrows(
                IllegalArgumentException.class,
                () -> new Vote(participant, null)
        );
    }

    @Test
    void shouldIdentifyNumericVote() {
        Participant participant = new Participant("1", "Mariana");
        Vote vote = new Vote(participant, PlanningCard.FIVE);

        assertTrue(vote.isNumericVote());
    }

    @Test
    void shouldIdentifyNonNumericVote() {
        Participant participant = new Participant("1", "Mariana");
        Vote vote = new Vote(participant, PlanningCard.COFFEE);

        assertFalse(vote.isNumericVote());
    }

    @Test
    void shouldConsiderVotesFromSameParticipantAsEqual() {
        Participant participant = new Participant("1", "Mariana");

        Vote vote1 = new Vote(participant, PlanningCard.FIVE);
        Vote vote2 = new Vote(participant, PlanningCard.EIGHT);

        assertEquals(vote1, vote2);
    }

    @Test
    void shouldReturnSameHashCodeForVotesFromSameParticipant() {
        Participant participant = new Participant("1", "Mariana");

        Vote vote1 = new Vote(participant, PlanningCard.FIVE);
        Vote vote2 = new Vote(participant, PlanningCard.EIGHT);

        assertEquals(vote1.hashCode(), vote2.hashCode());
    }
}