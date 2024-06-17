package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JavaNormalizer extends Generator {
    private static Node getNode(Optional<Attribute> name, Node withType) {
        if (name.isEmpty()) return withType;

        var paramName = name
                .flatMap(Attribute::asString)
                .orElseThrow();

        var param = new Node("definition")
                .withString("name", paramName);

        var definition = new Node("definition")
                .withNodeList("params", List.of(param))
                .withStringList("modifiers", Collections.emptyList());

        return withType.withNode("definition", definition);
    }

    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        if (node.is("method-reference")) {
            return new Tuple<>(node.retype("access"), depth);
        }

        if (node.is("lambda")) {
            var withType = node.retype("function");

            var name = node.attributes().apply("param-name");
            var withDefinition = getNode(name, withType);

            return new Tuple<>(withDefinition, depth);
        }

        if (node.is("method")) {
            var attributes = node.attributes();
            var definition = attributes.apply("definition")
                    .flatMap(Attribute::asNode)
                    .orElseThrow();

            var params = attributes
                    .apply("params")
                    .flatMap(Attribute::asNodeList)
                    .orElse(Collections.emptyList());

            var withParams = definition.withNodeList("params", params);
            var function = node.retype("function")
                    .remove("params")
                    .withNode("definition", withParams);

            return new Tuple<>(function, depth);
        }

        return new Tuple<>(node, depth);
    }
}
