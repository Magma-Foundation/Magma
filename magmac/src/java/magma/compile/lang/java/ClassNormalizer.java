package magma.compile.lang.java;

import magma.api.Tuple;
import magma.api.collect.List;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.lang.Visitor;
import magma.compile.rule.Node;
import magma.java.JavaList;

public class ClassNormalizer implements Visitor {
    private static List<String> computeNewModifiers(Node node) {
        var oldModifiers = node.findStringList("modifiers").orElse(JavaList.empty());
        var newModifiers = JavaList.<String>empty();
        return oldModifiers.contains("public") ? newModifiers.add("export") : newModifiers;
    }

    private static Result<Tuple<Node, State>, Error_> generateDefinition(String name, Node node, State state) {
        var classModifiers = JavaList.of("default", "class", "def");
        var stringList = computeNewModifiers(node).addAll(classModifiers);

        var definition = node.clear("definition")
                .withString("name", name)
                .withStringList("modifiers", stringList)
                .withNodeList("params", JavaList.empty());

        var function = node.retype("function").withNode("definition", definition);
        var withImplements = function.findNode("interface")
                .map(interfaceType -> moveImplements(function, interfaceType))
                .orElse(function);

        var tuple = new Tuple<>(withImplements, state);
        return new Ok<>(tuple);
    }

    private static Node moveImplements(Node function, Node interfaceType) {
        var implementsStatement = function.clear("implements").withNode("type", interfaceType);

        return function.withNode("child", function.findNode("child")
                .map(child -> child.mapNodes("children", children -> children.add(implementsStatement)))
                .orElse(implementsStatement));
    }

    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        return node.findString("name")
                .map(name -> generateDefinition(name, node, state))
                .orElseGet(() -> new Err<>(new CompileError("No name present.", node.toString())));
    }
}
