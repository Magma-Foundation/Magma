package magma.compile.lang;

import magma.compile.attribute.Attribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.Optional;

public class JavaToMagmaGenerator {
    public static Optional<Node> generate(Node node) {
        if (node.is("class")) {
            var oldAttributes = node.attributes();
            var oldModifiers = oldAttributes.apply("modifiers")
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

            var newAttributes = oldAttributes.with("modifiers", new StringListAttribute(newModifiers));
            return Optional.of(node.withAttributes(newAttributes));
        }

        if(node.is("import")) return Optional.of(node);

        return Optional.empty();
    }
}