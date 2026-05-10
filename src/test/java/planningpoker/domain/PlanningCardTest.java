package planningpoker.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanningCardTest {

    @Test
    void shouldReturnCardFromValidLabel() {
        assertEquals(PlanningCard.FIVE, PlanningCard.fromString("5"));
        assertEquals(PlanningCard.COFFEE, PlanningCard.fromString("☕"));
        assertEquals(PlanningCard.QUESTION, PlanningCard.fromString("?"));
    }

    @Test
    void shouldTrimLabelBeforeConverting() {
        assertEquals(PlanningCard.EIGHT, PlanningCard.fromString(" 8 "));
    }

    @Test
    void shouldReturnNullForInvalidLabel() {
        assertNull(PlanningCard.fromString("999"));
    }

    @Test
    void shouldReturnNullForNullLabel() {
        assertNull(PlanningCard.fromString(null));
    }

    @Test
    void shouldIdentifyNumericCards() {
        assertTrue(PlanningCard.FIVE.isNumeric());
        assertTrue(PlanningCard.HALF.isNumeric());
    }

    @Test
    void shouldIdentifySpecialCardsAsNonNumeric() {
        assertFalse(PlanningCard.COFFEE.isNumeric());
        assertFalse(PlanningCard.QUESTION.isNumeric());
        assertFalse(PlanningCard.INFINITY.isNumeric());
    }

    @Test
    void shouldReturnLabelOnToString() {
        assertEquals("5", PlanningCard.FIVE.toString());
        assertEquals("☕", PlanningCard.COFFEE.toString());
    }
}