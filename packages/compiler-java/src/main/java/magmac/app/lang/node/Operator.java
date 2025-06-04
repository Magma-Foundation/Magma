package magmac.app.lang.node;

/**
 * Enumeration of supported binary operators like {@code +} or {@code ==}.
 */
public enum Operator {
    Add("add", "+"),
    And("and", "&&"),
    Equals("equals", "=="),
    GreaterThan("greater-than", ">"),
    GreaterThanEquals("greater-than-equals", ">="),
    LessThan("less-than", "<"),
    NotEquals("not-equals", "!="),
    Or("or", "||"),
    Subtract("subtract", "-");

    private final String type;
    private final String text;

    Operator(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public String type() {
        return this.type;
    }

    public String text() {
        return this.text;
    }
}
