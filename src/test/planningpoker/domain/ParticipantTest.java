package planningpoker.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {

    @Test
    void shouldCreateParticipantSuccessfully() {
        Participant participant = new Participant("1", "Mariana");

        assertEquals("1", participant.getId());
        assertEquals("Mariana", participant.getName());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Participant(null, "Mariana")
        );
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Participant("1", "")
        );
    }

    @Test
    void shouldConsiderParticipantsWithSameIdAsEqual() {
        Participant participant1 = new Participant("1", "Mariana");
        Participant participant2 = new Participant("1", "Lucas");

        assertEquals(participant1, participant2);
        assertEquals(participant1.hashCode(), participant2.hashCode());
    }

    @Test
    void shouldReturnNameOnToString() {
        Participant participant = new Participant("1", "Mariana");

        assertEquals("Mariana", participant.toString());
    }
}