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

public class InterfaceNormalizer implements Visitor {
    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        var struct = node.retype("struct").mapStringList("modifiers", InterfaceNormalizer::normalizeModifiers);

        return new Ok<>(new Tuple<>(struct, state));
    }

    private static List<String> normalizeModifiers(List<String> oldModifiers) {
        return oldModifiers.contains("public")
                ? JavaList.of("export")
                : JavaList.empty();
    }
}
