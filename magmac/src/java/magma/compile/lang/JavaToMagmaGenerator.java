package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.rule.Node;
import magma.compile.rule.text.StripRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JavaToMagmaGenerator extends Generator {
    private static Optional<Tuple<Node, Integer>> preVisitBlock(Node node, int depth) {
        if (!node.is("block")) return Optional.empty();

        var newBlock = node.mapNodes("children", JavaToMagmaGenerator::removePackagesFromList);
        return Optional.of(new Tuple<>(newBlock, depth + 1));
    }

    private static List<Node> removePackagesFromList(List<Node> children) {
        return children.stream()
                .filter(child -> !child.is("package"))
                .toList();
    }

    @Override
    protected Tuple<Node, Integer> postVisit(Node node, int depth) {
        return postVisitFunction(node, depth)
                .or(() -> postVisitBlock(node, depth))
                .orElse(new Tuple<>(node, depth));
    }

    private Optional<Tuple<Node, Integer>> postVisitBlock(Node node, int depth) {
        if (!node.is("block")) return Optional.empty();

        var prefix = "\n" + "\t".repeat(depth);
        var newBlock = node.mapNodes("children", children -> {
            return children.stream()
                    .map(child -> child.withString(StripRule.DEFAULT_LEFT, prefix))
                    .toList();
        });

        return Optional.of(new Tuple<>(newBlock, depth - 1));
    }

    private Optional<Tuple<Node, Integer>> postVisitFunction(Node node, int depth) {
        if (node.is("function")) {
            var oldDefinition = node.findNode("definition");

            if (oldDefinition.isPresent()) {
                if (node.has("child")) {
                    var newDefinition = oldDefinition.get()
                            .mapOrSetNodeList("params", params -> params, () -> Collections.emptyList())
                            .mapOrSetStringList("modifiers", list -> {
                                var copy = new ArrayList<>(list);
                                copy.add("def");
                                return copy;
                            }, () -> List.of("def"));

                    return Optional.of(new Tuple<>(node.withNode("definition", newDefinition), depth));
                } else {
                    return Optional.of(new Tuple<>(oldDefinition.get(), depth));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    protected Tuple<Node, Integer> preVisit(Node node, int depth) {
        return preVisitBlock(node, depth)
                .or(() -> replaceClassWithFunction(node, depth))
                .or(() -> replaceMethodWithFunction(node, depth))
                .or(() -> replaceLambdaWithFunction(node, depth))
                .or(() -> replaceRecordWithFunction(node, depth))
                .or(() -> replaceConstructorsWithInvocation(node, depth))
                .or(() -> replaceInterfaceWithStruct(node, depth))
                .or(() -> replaceMethodReferenceWithAccess(node, depth))
                .orElse(new Tuple<>(node, depth));
    }

    private Optional<Tuple<Node, Integer>> replaceMethodReferenceWithAccess(Node node, int depth) {
        if (!node.is("method-reference")) return Optional.empty();

        return Optional.of(new Tuple<>(node.retype("access"), depth));
    }

    private Optional<Tuple<Node, Integer>> replaceRecordWithFunction(Node node, int depth) {
        if (!node.is("record")) return Optional.empty();

        return Optional.of(new Tuple<>(node.retype("function"), depth));
    }

    private Optional<Tuple<Node, Integer>> replaceInterfaceWithStruct(Node node, int depth) {
        if (!node.is("interface")) return Optional.empty();

        return Optional.of(new Tuple<>(node.retype("struct"), depth));
    }

    private Optional<Tuple<Node, Integer>> replaceLambdaWithFunction(Node node, int depth) {
        if (!node.is("lambda")) return Optional.empty();

        return Optional.of(new Tuple<>(node.retype("function"), depth));
    }

    private Optional<Tuple<Node, Integer>> replaceConstructorsWithInvocation(Node node, int depth) {
        if (!node.is("constructor")) return Optional.empty();

        return Optional.of(new Tuple<>(node.retype("invocation"), depth));
    }

    private Optional<Tuple<Node, Integer>> replaceMethodWithFunction(Node node, int depth) {
        if (!node.is("method")) return Optional.empty();

        return Optional.of(new Tuple<>(node.retype("function"), depth));
    }

    private Optional<Tuple<Node, Integer>> replaceClassWithFunction(Node node, int depth) {
        if (!node.is("class")) return Optional.empty();

        var name = node.findString("name").orElseThrow(() -> new RuntimeException("No name present: " + node));

        var modifiers = node.findStringList("modifiers")
                .orElseThrow();

        var newModifiers = new ArrayList<>(modifiers);
        newModifiers.add("class");

        var definition = node.clear("definition")
                .withString("name", name)
                .withStringList("modifiers", newModifiers);

        var function = node.retype("function").withNode("definition", definition);

        return Optional.of(new Tuple<>(function, depth));
    }
}
