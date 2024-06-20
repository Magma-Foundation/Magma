package magma.compile.lang;

import magma.api.Tuple;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.rule.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JavaToMagmaGenerator extends Generator {
    private static Optional<Result<Node, Error_>> preVisitBlock(Node node) {
        if (!node.is("block")) return Optional.empty();

        var newBlock = node.mapNodes("children", JavaToMagmaGenerator::removePackagesFromList);
        return Optional.of(new Ok<>(newBlock));
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

    private static Result<Node, Error_> replaceConcreteWithFunction(Node node) {
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

        return new Ok<>(function);
    }

    private static ArrayList<String> computeAccess(List<String> oldModifiers) {
        var newModifiers = new ArrayList<String>();
        if (oldModifiers.contains("public")) {
            newModifiers.add("export");
        }
        return newModifiers;
    }

    private static Optional<Result<Node, Error_>> replaceAbstractClassWithDefinition(Node node) {
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

        return Optional.of(new Ok<>(declaration));
    }

    @Override
    protected Result<Tuple<Node, State>, Error_> postVisit(Node node, State depth) {
        return postVisitFunction(node)
                .or(() -> postVisitDeclaration(node))
                .map(result -> result.mapValue(newNode -> new Tuple<>(newNode, depth)))
                .orElse(new Ok<>(new Tuple<>(node, depth)));
    }

    private Optional<Result<Node, Error_>> postVisitDeclaration(Node node) {
        if (!node.is("declaration")) return Optional.empty();

        var withMutator = attachMutator(node);
        return Optional.of(new Ok<>(withMutator
                .withString("after-definition", " ")
                .withString("after-value-separator", " ")));
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

    private Optional<Result<Node, Error_>> postVisitFunction(Node node) {
        if (!node.is("function")) return Optional.empty();
        var oldDefinition = node.findNode("definition");

        if (oldDefinition.isEmpty()) return Optional.empty();
        var params = node.findNodeList("params").orElse(Collections.emptyList());

        if (!node.has("child")) return Optional.of(new Ok<>(oldDefinition.get()));

        var withParams = oldDefinition.get().withNodeList("params", params);
        var newDefinition = withParams.has("name") ? attachModifiers(withParams) : withParams;

        return Optional.of(new Ok<>(node.withNode("definition", newDefinition)));
    }

    @Override
    protected Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        return preVisitBlock(node)
                .or(() -> replaceClassWithFunction(node))
                .or(() -> replaceMethodWithFunction(node))
                .or(() -> replaceLambdaWithFunction(node))
                .or(() -> replaceRecordWithFunction(node))
                .or(() -> replaceConstructorsWithInvocation(node))
                .or(() -> replaceInterfaceWithStruct(node))
                .or(() -> replaceMethodReferenceWithAccess(node))
                .or(() -> replaceGenericWithFunctionType(node))
                .or(() -> preVisitDeclaration(node))
                .map(result -> result.mapValue(value -> new Tuple<>(value, state)))
                .orElse(new Ok<>(new Tuple<>(node, state)));
    }

    private Optional<Result<Node, Error_>> preVisitDeclaration(Node node) {
        if (!node.is("declaration")) return Optional.empty();

        var removed = removeType(node);
        return Optional.of(new Ok<>(removed));
    }

    private Optional<Result<Node, Error_>> replaceGenericWithFunctionType(Node node) {
        if (!node.is("generic")) return Optional.empty();

        var parent = node.findString("parent").orElseThrow();
        var children = node.findNodeList("children").orElseThrow();

        if (parent.equals("Function")) {
            if (children.size() != 2) {
                return Optional.empty();
            }

            var from = children.get(0);
            var to = children.get(1);

            return Optional.of(new Ok<>(node.clear("function-type")
                    .withNodeList("params", List.of(from))
                    .withNode("returns", to)));

        } else if (parent.equals("Supplier")) {
            var returns = children.get(0);
            var returns1 = node.clear("function-type").withNode("returns", returns);
            return Optional.of(new Ok<>(returns1));
        }

        return Optional.of(new Ok<>(node));
    }

    private Optional<Result<Node, Error_>> replaceMethodReferenceWithAccess(Node node) {
        if (!node.is("method-reference")) return Optional.empty();

        return Optional.of(new Ok<>(node.retype("access")));
    }

    private Optional<Result<Node, Error_>> replaceRecordWithFunction(Node node) {
        if (!node.is("record")) return Optional.empty();


        return Optional.of(replaceConcreteWithFunction(node));
    }

    private Optional<Result<Node, Error_>> replaceInterfaceWithStruct(Node node) {
        if (!node.is("interface")) return Optional.empty();

        return Optional.of(new Ok<>(node.retype("struct")));
    }

    private Optional<Result<Node, Error_>> replaceLambdaWithFunction(Node node) {
        if (!node.is("lambda")) return Optional.empty();

        return Optional.of(new Ok<>(node.retype("function")));
    }

    private Optional<Result<Node, Error_>> replaceConstructorsWithInvocation(Node node) {
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

            return Optional.of(new Ok<>(node.clear("invocation")
                    .withNode("caller", classCreator)
                    .withNodeList("arguments", originalArguments)));
        } else {
            return Optional.of(new Ok<>(node.retype("invocation")));
        }
    }

    private Optional<Result<Node, Error_>> replaceMethodWithFunction(Node node) {
        if (!node.is("method")) return Optional.empty();

        var params = node.findNodeList("params").orElse(Collections.emptyList());
        var copy = new ArrayList<Node>();

        var thisDefinition = node.clear("definition")
                .withString("name", "this");

        copy.add(thisDefinition);
        copy.addAll(params);

        var newFunction = node.retype("function").withNodeList("params", copy);
        return Optional.of(new Ok<>(newFunction));
    }

    private Optional<Result<Node, Error_>> replaceClassWithFunction(Node node) {
        if (!node.is("class")) return Optional.empty();

        var oldModifiers = node.findStringList("modifiers").orElseThrow();
        if (oldModifiers.contains("abstract")) {
            return replaceAbstractClassWithDefinition(node);
        } else {
            return Optional.of(replaceConcreteWithFunction(node));
        }
    }

}