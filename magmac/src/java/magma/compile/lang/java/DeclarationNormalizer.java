package magma.compile.lang.java;

import magma.api.Tuple;
import magma.api.collect.List;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.lang.Visitor;
import magma.compile.rule.Node;
import magma.java.JavaList;

public class DeclarationNormalizer implements Visitor {
    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        var newDeclaration = node.mapNode("definition", oldDefinition -> {
            var withoutType = removeType(oldDefinition);
            var oldModifiers = withoutType.findStringList("modifiers").orElse(JavaList.empty());
            var withStatic = attach(oldModifiers, JavaList.empty(), "static", "static");
            var withPublic = attach(oldModifiers, withStatic, "public", "public").addLast("let");
            var newModifiers = oldModifiers.contains("final") ? withPublic : withPublic.addLast("mut");
            return withoutType.withStringList("modifiers", newModifiers);
        });

        return new Ok<>(new Tuple<>(newDeclaration, state));
    }

    private Node removeType(Node oldDefinition) {
        var typeOption = oldDefinition.findNode("type");
        if (typeOption.isPresent()) {
            var type = typeOption.orElsePanic();
            if (type.is("reference")) {
                var valueOption = type.findString("value");
                if (valueOption.isPresent()) {
                    var value = valueOption.orElsePanic();
                    if (value.equals("var")) {
                        return oldDefinition.remove("type");
                    }
                }
            }
        }
        return oldDefinition;
    }

    private List<String> attach(List<String> list, List<String> initial, String input, String output) {
        return list.contains(input) ? initial.addLast(output) : initial;
    }
}
