package planningpoker.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Story {

    private final String id;
    private final String title;
    private final String description;

    private final List<Round> rounds;

    private PlanningCard finalEstimate;

    public Story(String id, String title, String description) {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Story id cannot be null or empty");
        }

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Story title cannot be null or empty");
        }

        this.id = id.trim();
        this.title = title.trim();
        this.description = description == null ? "" : description.trim();

        this.rounds = new ArrayList<>();
    }

    public void addRound(Round round) {

        if (round == null) {
            throw new IllegalArgumentException("Round cannot be null");
        }

        rounds.add(round);
    }

    public void defineFinalEstimate(PlanningCard card) {

        if (card == null) {
            throw new IllegalArgumentException("Final estimate cannot be null");
        }

        this.finalEstimate = card;
    }

    public List<Round> getRounds() {
        return Collections.unmodifiableList(rounds);
    }

    public PlanningCard getFinalEstimate() {
        return finalEstimate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}