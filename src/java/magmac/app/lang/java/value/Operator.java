package magmac.app.lang.java.value;

public enum Operator {
    Add("add"),
    LessThan("less-than"),
    Subtract("subtract"),
    Or("or"),
    And("and"),
    NotEquals("!=");

    private final String text;

    Operator(String text) {
        this.text = text;
    }

    public String text() {
        return this.text;
    }
}
