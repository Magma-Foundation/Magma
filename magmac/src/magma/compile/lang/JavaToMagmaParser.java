package magma.compile.lang;

import magma.compile.Node;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.StringListAttribute;

import java.util.ArrayList;

public class JavaToMagmaParser {
    public static Node parse(Node node) {
        var oldModifiers = node.apply("modifiers")
                .flatMap(Attribute::asStringList)
                .orElseThrow();

        var newModifiers = new ArrayList<String>();
        for (var oldModifier : oldModifiers) {
            if (oldModifier.equals("public")) {
                newModifiers.add("export");
            }
        }

        newModifiers.add("class");
        newModifiers.add("def");

        return node.with("modifiers", new StringListAttribute(newModifiers));
    }
}