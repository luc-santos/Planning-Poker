package planningpoker.domain;

public enum PlanningCard {
    ZERO("0", 0.0),
    HALF("½", 0.5),
    ONE("1", 1.0),
    TWO("2", 2.0),
    THREE("3", 3.0),
    FIVE("5", 5.0),
    EIGHT("8", 8.0),
    THIRTEEN("13", 13.0),
    TWENTY("20", 20.0),
    FORTY("40", 40.0),
    ONE_HUNDRED("100", 100.0),
    QUESTION("?", null),
    INFINITY("∞", null),
    COFFEE("☕", null);

    private final String label;
    private final Double value;

    PlanningCard(String label, Double value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public Double getValue() {
        return value;
    }

    public boolean isNumeric(){
        return value != null;
    }

    public static PlanningCard fromString(String label){
        if (label == null) {
            return null;
        }
        for (PlanningCard card : PlanningCard.values()){
            if (card.label.equals(label.trim())){
                return card;
            }
        }
        return null;
    }
    @Override
    public String toString(){
        return label;
    }
}
