package planningpoker.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanningSessionTest {

    @Test
    void shouldCreateSessionSuccessfully() {
        PlanningSession session = new PlanningSession("1", "Sprint Planning");

        assertEquals("1", session.getId());
        assertEquals("Sprint Planning", session.getName());
        assertEquals(VotingStatus.VOTING, session.getStatus());
    }

    @Test
    void shouldAddParticipant() {
        PlanningSession session = new PlanningSession("1", "Sprint Planning");
        Participant participant = new Participant("1", "Mariana");

        session.addParticipant(participant);

        assertTrue(session.hasParticipant("1"));
        assertEquals(1, session.getParticipants().size());
    }

    @Test
    void shouldRemoveParticipant() {
        PlanningSession session = new PlanningSession("1", "Sprint Planning");
        Participant participant = new Participant("1", "Mariana");

        session.addParticipant(participant);
        session.removeParticipant("1");

        assertFalse(session.hasParticipant("1"));
    }

    @Test
    void shouldRegisterVote() {
        PlanningSession session = new PlanningSession("1", "Sprint Planning");
        Participant participant = new Participant("1", "Mariana");

        session.addParticipant(participant);
        session.vote("1", PlanningCard.FIVE);

        assertTrue(session.hasVoted("1"));
    }

    @Test
    void shouldReplacePreviousVote() {
        PlanningSession session = new PlanningSession("1", "Sprint Planning");
        Participant participant = new Participant("1", "Mariana");

        session.addParticipant(participant);

        session.vote("1", PlanningCard.FIVE);
        session.vote("1", PlanningCard.EIGHT);

        Vote vote = session.getVotes().iterator().next();

        assertEquals(1, session.getVotes().size());
        assertEquals(PlanningCard.EIGHT, vote.getCard());
    }

    @Test
    void shouldReturnTrueWhenAllParticipantsVoted() {
        PlanningSession session = new PlanningSession("1", "Sprint Planning");

        Participant participant1 = new Participant("1", "Mariana");
        Participant participant2 = new Participant("2", "Lucas");

        session.addParticipant(participant1);
        session.addParticipant(participant2);

        session.vote("1", PlanningCard.FIVE);
        session.vote("2", PlanningCard.EIGHT);

        assertTrue(session.allParticipantsVoted());
    }

    @Test
    void shouldNotRevealVotesIfNotAllParticipantsVoted() {
        PlanningSession session = new PlanningSession("1", "Sprint Planning");

        Participant participant1 = new Participant("1", "Mariana");
        Participant participant2 = new Participant("2", "Lucas");

        session.addParticipant(participant1);
        session.addParticipant(participant2);

        session.vote("1", PlanningCard.FIVE);

        assertThrows(
                IllegalStateException.class,
                session::revealVotes
        );
    }

    @Test
    void shouldRevealVotes() {
        PlanningSession session = new PlanningSession("1", "Sprint Planning");

        Participant participant1 = new Participant("1", "Mariana");
        Participant participant2 = new Participant("2", "Lucas");

        session.addParticipant(participant1);
        session.addParticipant(participant2);

        session.vote("1", PlanningCard.FIVE);
        session.vote("2", PlanningCard.EIGHT);

        session.revealVotes();

        assertEquals(VotingStatus.REVEALED, session.getStatus());
    }

    @Test
    void shouldNotAllowVoteAfterReveal() {
        PlanningSession session = new PlanningSession("1", "Sprint Planning");
        Participant participant = new Participant("1", "Mariana");

        session.addParticipant(participant);

        session.vote("1", PlanningCard.FIVE);
        session.revealVotes();

        assertThrows(
                IllegalStateException.class,
                () -> session.vote("1", PlanningCard.EIGHT)
        );
    }

    @Test
    void shouldResetVotes() {
        PlanningSession session = new PlanningSession("1", "Sprint Planning");
        Participant participant = new Participant("1", "Mariana");

        session.addParticipant(participant);

        session.vote("1", PlanningCard.FIVE);
        session.revealVotes();

        session.resetVotes();

        assertEquals(VotingStatus.VOTING, session.getStatus());
        assertEquals(0, session.getVotes().size());
    }

    @Test
    void shouldFinishSession() {
        PlanningSession session = new PlanningSession("1", "Sprint Planning");

        session.finishSession();

        assertEquals(VotingStatus.FINISHED, session.getStatus());
    }
}