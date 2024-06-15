package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.attribute.Attribute;
import magma.compile.attribute.StringListAttribute;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaNormalizer extends Generator {
    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        if (node.is("constructor")) {
            return new Tuple<>(node.retype("invocation"), depth);
        }

        if (node.is("lambda")) {
            var paramName = node.attributes()
                    .apply("param-name")
                    .flatMap(Attribute::asString)
                    .orElseThrow();

            var param = new Node("definition")
                    .withString("name", paramName);

            var definition = new Node("definition")
                    .withNodeList("params", List.of(param))
                    .withStringList("modifiers", Collections.emptyList());

            var function = node.retype("function").withNode("definition", definition);
            return new Tuple<>(function, depth);
        }

        if (node.is("declaration")) {
            var withModifiers = node.mapAttributes(attributes -> {
                var list = attributes.apply("modifiers")
                        .flatMap(Attribute::asStringList)
                        .orElse(Collections.emptyList());

                var copy = new ArrayList<String>();
                if (list.contains("final")) {
                    copy.add("const");
                } else {
                    copy.add("let");
                }

                return attributes.with("modifiers", new StringListAttribute(copy));
            }).mapAttributes(attributes -> {
                var type = attributes.apply("type").flatMap(Attribute::asNode);
                if (type.isEmpty()) return attributes;

                var inner = type.get();
                if (inner.is("symbol")) {
                    var value = inner.attributes().apply("value")
                            .flatMap(Attribute::asString)
                            .orElseThrow();

                    if (value.equals("var")) {
                        return attributes.remove("type");
                    } else {
                        return attributes;
                    }
                } else {
                    return attributes;
                }
            });

            return new Tuple<>(withModifiers, depth);
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
