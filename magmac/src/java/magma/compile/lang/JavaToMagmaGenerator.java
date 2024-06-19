package magma.compile.lang;

import magma.api.Tuple;
import magma.compile.rule.Node;
import magma.compile.rule.text.StripRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JavaToMagmaGenerator extends Generator {
    private static Optional<Tuple<Node, State>> preVisitBlock(Node node, State state) {
        if (!node.is("block")) return Optional.empty();

        var newBlock = node.mapNodes("children", JavaToMagmaGenerator::removePackagesFromList);
        return Optional.of(new Tuple<>(newBlock, state.enter()));
    }

    private static List<Node> removePackagesFromList(List<Node> children) {
        return children.stream()
                .filter(child -> !child.is("package"))
                .toList();
    }

    private static Node removeType(Node node) {
        var definition = node.findNode("definition").orElseThrow();
        var type = definition.findNode("type").orElseThrow();

        if (!type.is("symbol")) return node;

        var value = type.findString("value").orElseThrow();
        if (value.equals("var")) {
            return node.withNode("definition", definition.remove("type"));
        }
        return node;
    }

    private static List<Node> attachFormatting(List<Node> children, String prefix) {
        return children.stream()
                .filter(child -> !child.is("empty"))
                .map(child -> child.withString(StripRule.DEFAULT_LEFT, prefix))
                .toList();
    }

    private static List<String> computeMutator(List<String> modifiers) {
        if (modifiers.contains("final")) {
            return List.of("let");
        } else {
            return List.of("let", "mut");
        }
    }

    private static Node attachModifiers(Node withParams) {
        return withParams.mapOrSetStringList("modifiers", list -> {
            var copy = new ArrayList<>(list);
            copy.add("def");
            return copy;
        }, () -> List.of("def"));
    }

    private static Optional<Tuple<Node, State>> replaceConcreteWithFunction(Node node, State depth) {
        var name = node.findString("name").orElseThrow(() -> new RuntimeException("No name present: " + node));
        var oldModifiers = node.findStringList("modifiers").orElseThrow();
        var newModifiers = computeAccess(oldModifiers);
        newModifiers.add("class");

        var definition = node.clear("definition")
                .withString("name", name)
                .withStringList("modifiers", newModifiers);

        var withMaybeTypeParams = node.findNodeList("type-params")
                .map(nodes -> definition.withNodeList("type-params", nodes))
                .orElse(definition);

        var function = node.retype("function")
                .remove("name")
                .remove("modifiers")
                .remove("type-params")
                .withNode("definition", withMaybeTypeParams);

        return Optional.of(new Tuple<>(function, depth));
    }

    private static ArrayList<String> computeAccess(List<String> oldModifiers) {
        var newModifiers = new ArrayList<String>();
        if (oldModifiers.contains("public")) {
            newModifiers.add("export");
        }
        return newModifiers;
    }

    private static Optional<Tuple<Node, State>> replaceAbstractClassWithDefinition(Node node, State depth) {
        var name = node.findString("name").orElseThrow(() -> new RuntimeException("No name present: " + node));
        var oldModifiers = node.findStringList("modifiers").orElseThrow();
        var newModifiers = computeAccess(oldModifiers);
        var withMaybeTypeParams = node.clear("definition").withStringList("modifiers", Collections.singletonList("class"));

        var function = node.retype("function")
                .remove("name")
                .remove("type-params")
                .withNode("definition", withMaybeTypeParams);

        var factoryDefinition = node.clear("definition");

        var assigneeValue = node.clear("function")
                .withNode("definition", factoryDefinition)
                .withNode("child", function);

        var assignee = node.clear("definition")
                .withString("name", name)
                .withStringList("modifiers", newModifiers);

        var withTypeParams = node.findNodeList("type-params")
                .map(params -> assignee.withNodeList("type-params", params))
                .orElse(assignee);

        var declaration = node.clear("declaration")
                .withNode("definition", withTypeParams)
                .withNode("value", assigneeValue);

        return Optional.of(new Tuple<>(declaration, depth));
    }

    @Override
    protected Tuple<Node, State> postVisit(Node node, State depth) {
        return postVisitFunction(node, depth)
                .or(() -> postVisitBlock(node, depth))
                .or(() -> postVisitDeclaration(node, depth))
                .orElse(new Tuple<>(node, depth));
    }

    private Optional<? extends Tuple<Node, State>> postVisitDeclaration(Node node, State depth) {
        if (!node.is("declaration")) return Optional.empty();

        var withMutator = attachMutator(node);

        return Optional.of(new Tuple<>(withMutator
                .withString("after-definition", " ")
                .withString("after-value-separator", " "), depth));
    }

    private Node attachMutator(Node node) {
        return node.mapNode("definition", definition -> {
            return definition.mapOrSetStringList("modifiers", modifiers -> {
                var oldModifiers = new ArrayList<>(modifiers);
                oldModifiers.addAll(computeMutator(modifiers));
                return oldModifiers;
            }, () -> List.of("let", "mut"));
        });
    }

    private Optional<Tuple<Node, State>> postVisitBlock(Node node, State state) {
        if (!node.is("block")) return Optional.empty();

        var depth = state.depth();
        var prefix = "\n" + "\t".repeat(depth);
        var newBlock = node
                .mapNodes("children", children -> attachFormatting(children, prefix))
                .withString("after-content", "\n" + "\t".repeat(depth == 0 ? 0 : depth - 1));

        return Optional.of(new Tuple<>(newBlock, state.exit()));
    }

    private Optional<Tuple<Node, State>> postVisitFunction(Node node, State depth) {
        if (!node.is("function")) return Optional.empty();
        var oldDefinition = node.findNode("definition");

        if (oldDefinition.isEmpty()) return Optional.empty();
        var params = node.findNodeList("params").orElse(Collections.emptyList());

        if (!node.has("child")) return Optional.of(new Tuple<>(oldDefinition.get(), depth));

        var withParams = oldDefinition.get().withNodeList("params", params);
        var newDefinition = withParams.has("name") ? attachModifiers(withParams) : withParams;

        return Optional.of(new Tuple<>(node.withNode("definition", newDefinition), depth));
    }

    @Override
    protected Tuple<Node, State> preVisit(Node node, State depth) {
        return preVisitBlock(node, depth)
                .or(() -> replaceClassWithFunction(node, depth))
                .or(() -> replaceMethodWithFunction(node, depth))
                .or(() -> replaceLambdaWithFunction(node, depth))
                .or(() -> replaceRecordWithFunction(node, depth))
                .or(() -> replaceConstructorsWithInvocation(node, depth))
                .or(() -> replaceInterfaceWithStruct(node, depth))
                .or(() -> replaceMethodReferenceWithAccess(node, depth))
                .or(() -> replaceGenericWithFunctionType(node, depth))
                .or(() -> preVisitDeclaration(node, depth))
                .orElse(new Tuple<>(node, depth));
    }

    private Optional<? extends Tuple<Node, State>> preVisitDeclaration(Node node, State depth) {
        if (!node.is("declaration")) return Optional.empty();

        var removed = removeType(node);
        return Optional.of(new Tuple<>(removed, depth));
    }

    private Optional<Tuple<Node, State>> replaceGenericWithFunctionType(Node node, State depth) {
        if (!node.is("generic")) return Optional.empty();

        var parent = node.findString("parent").orElseThrow();
        var children = node.findNodeList("children").orElseThrow();

        if (parent.equals("Function")) {
            if (children.size() != 2) {
                return Optional.empty();
            }

            var from = children.get(0);
            var to = children.get(1);

            return Optional.of(new Tuple<>(node.clear("function-type")
                    .withNodeList("params", List.of(from))
                    .withNode("returns", to), depth));
        } else if(parent.equals("Supplier")) {
            var returns = children.get(0);
            var returns1 = node.clear("function-type").withNode("returns", returns);
            return Optional.of(new Tuple<>(returns1, depth));
        }

        return Optional.of(new Tuple<>(node, depth));
    }

    private Optional<Tuple<Node, State>> replaceMethodReferenceWithAccess(Node node, State depth) {
        if (!node.is("method-reference")) return Optional.empty();

        return Optional.of(new Tuple<>(node.retype("access"), depth));
    }

    private Optional<Tuple<Node, State>> replaceRecordWithFunction(Node node, State depth) {
        if (!node.is("record")) return Optional.empty();

        return replaceConcreteWithFunction(node, depth);
    }

    private Optional<Tuple<Node, State>> replaceInterfaceWithStruct(Node node, State depth) {
        if (!node.is("interface")) return Optional.empty();

        return Optional.of(new Tuple<>(node.retype("struct"), depth));
    }

    private Optional<Tuple<Node, State>> replaceLambdaWithFunction(Node node, State depth) {
        if (!node.is("lambda")) return Optional.empty();

        return Optional.of(new Tuple<>(node.retype("function"), depth));
    }

    private Optional<Tuple<Node, State>> replaceConstructorsWithInvocation(Node node, State depth) {
        if (!node.is("constructor")) return Optional.empty();

        var oldChildrenOptional = node.findNodeList("children");
        if (oldChildrenOptional.isPresent()) {
            var originalArguments = node.findNodeList("arguments").orElse(Collections.emptyList());
            var oldChildren = oldChildrenOptional.get();

            var nonFunctions = new ArrayList<Node>();
            var functions = new ArrayList<Node>();
            for (Node oldChild : oldChildren) {
                if (oldChild.is("method")) {
                    functions.add(oldChild);
                } else {
                    nonFunctions.add(oldChild);
                }
            }

            var construction = node.clear("construction")
                    .withNode("child", node.clear("block").withNodeList("children", functions));

            var newChildren = new ArrayList<>(nonFunctions);
            newChildren.add(new Node("return").withNode("child", construction));

            var lambdaBody = node.clear("block").withNodeList("children", newChildren);
            var lambda = node.clear("function").withNode("child", lambdaBody);

            var lambda1 = node.clear("invocation")
                    .withNode("caller", node.clear("quantity").withNode("value", lambda))
                    .withNodeList("arguments", Collections.emptyList());

            var classCreator = node.retype("invocation")
                    .remove("children")
                    .withNodeList("arguments", Collections.singletonList(lambda1));

            return Optional.of(new Tuple<>(node.clear("invocation")
                    .withNode("caller", classCreator)
                    .withNodeList("arguments", originalArguments), depth));
        } else {
            return Optional.of(new Tuple<>(node.retype("invocation"), depth));
        }
    }

    private Optional<Tuple<Node, State>> replaceMethodWithFunction(Node node, State depth) {
        if (!node.is("method")) return Optional.empty();

        var newFunction = node.retype("function").mapNodes("params", params -> {
            var copy = new ArrayList<Node>();

            var thisDefinition = node.clear("definition")
                    .withString("name", "this");

            copy.add(thisDefinition);
            copy.addAll(params);
            return copy;
        });

        return Optional.of(new Tuple<>(newFunction, depth));
    }

    private Optional<Tuple<Node, State>> replaceClassWithFunction(Node node, State depth) {
        if (!node.is("class")) return Optional.empty();

        var oldModifiers = node.findStringList("modifiers").orElseThrow();
        if (oldModifiers.contains("abstract")) {
            return replaceAbstractClassWithDefinition(node, depth);
        } else {
            return replaceConcreteWithFunction(node, depth);
        }
    }

}
