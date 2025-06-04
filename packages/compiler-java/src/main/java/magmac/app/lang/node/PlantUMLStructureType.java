package magmac.app.lang.node;

/**
 * The kinds of structures supported in PlantUML diagrams.
 */
public enum PlantUMLStructureType {
    Class("class"), Interface("interface"), Enum("enum");

    private final String text;

    PlantUMLStructureType(String text) {
        this.text = text;
    }

    public String text() {
        return this.text;
    }
}
