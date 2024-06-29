package magma.compile.lang.java;

import magma.api.Tuple;
import magma.api.contain.List;
import magma.api.contain.collect.ExceptionalCollector;
import magma.api.contain.stream.ResultStream;
import magma.api.contain.stream.Stream;
import magma.api.contain.stream.Streams;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.Error_;
import magma.compile.annotate.State;
import magma.compile.lang.Visitor;
import magma.compile.rule.Node;
import magma.java.JavaList;

public class BlockNormalizer implements Visitor {
    private static List<Node> removeFromChildren(List<Node> children) {
        return children.stream()
                .filter(child -> !child.is("package"))
                .collect(JavaList.collecting());
    }

    private static Result<Flattening, Error_> flattenChild(Flattening flattening, Node node, String outerName) {
        if (node.is("function") || node.is("declaration")) {
            var definitionOptional = node.findNode("definition");
            if (definitionOptional.isEmpty()) return new Ok<>(flattening.withInstanceMember(node));

            var definition = definitionOptional.orElsePanic();
            var modifiers = definition.findStringList("modifiers").orElse(JavaList.empty());

            var cleanedOptional = cleanup(node, outerName);
            if (cleanedOptional.isEmpty()) return new Ok<>(flattening);
            var cleaned = cleanedOptional.orElsePanic();

            if (modifiers.contains("static")) {
                return new Ok<>(flattening.withStaticMember(cleaned));
            } else {
                return new Ok<>(flattening.withInstanceMember(cleaned));
            }
        } else {
            return new Ok<>(flattening.withInstanceMember(node));
        }
    }

    private static Stream<Node> splitIntoObject(Node node, Flattening flattening, String name, List<String> modifiers) {
        var staticMembers = flattening.staticMembers;
        var instanceMembers = flattening.instanceMembers;

        if (staticMembers.isEmpty()) {
            return Streams.of(createFunction(node, instanceMembers));
        } else if (instanceMembers.isEmpty()) {
            return Streams.of(createObject(node, modifiers, name, staticMembers));
        } else {
            var object = createObject(node, modifiers, name, staticMembers);
            var withChild = createFunction(node, instanceMembers);
            return Streams.of(object, withChild);
        }
    }

    private static Node createFunction(Node node, List<Node> instanceMembers) {
        var instanceBlock = node.clear("block").withNodeList("children", instanceMembers);
        return node.withNode("child", instanceBlock);
    }

    private static Node createObject(Node node, List<String> oldModifiers, String name, List<Node> staticMembers) {
        var staticBlock = node.clear("block").withNodeList("children", staticMembers);
        var newModifiers = oldModifiers.remove("class").remove("def");

        return node.clear("object")
                .withStringList("modifiers", newModifiers)
                .withString("name", name)
                .withNode("child", staticBlock);
    }

    static Option<Node> cleanup(Node node, String outerName) {
        if (!node.is("function") && !node.is("declaration")) return new Some<>(node);

        var definitionOptional = node.findNode("definition");
        if (definitionOptional.isEmpty()) return new Some<>(node);

        var definition = definitionOptional.orElsePanic();
        var nameOptional = definition.findString("name");
        if (nameOptional.isEmpty()) return new Some<>(node);
        if (nameOptional.orElsePanic().equals(outerName)) {
            return new None<>();
        }

        var modifiers = definition.findStringList("modifiers").orElse(JavaList.empty());

        var withNewModifiers = definition.withStringList("modifiers", modifiers.remove("static"));
        var withNewDefinition = node.withNode("definition", withNewModifiers);
        return new Some<>(withNewDefinition);
    }

    @Override
    public Result<Tuple<Node, State>, Error_> postVisit(Node node, State state) {
        var childrenOptional = node.findNodeList("children");
        if (childrenOptional.isEmpty()) return new Ok<>(new Tuple<>(node, state));

        return flattenChildren(childrenOptional.orElsePanic())
                .mapValue(children -> new Tuple<>(node.withNodeList("children", children), state));
    }

    private Result<List<Node>, Error_> flattenChildren(List<Node> nodeList) {
        return nodeList.stream()
                .map(this::flattenChild)
                .into(ResultStream::new)
                .flatMapValue(value -> value)
                .collect(new ExceptionalCollector<>(JavaList.collecting()));
    }

    private Result<Stream<Node>, Error_> flattenChild(Node node) {
        if (!node.is("function")) return new Ok<>(Streams.of(node));

        var definitionOptional = node.findNode("definition");
        if (definitionOptional.isEmpty()) return new Ok<>(Streams.of(node));

        var definition = definitionOptional.orElsePanic();

        var modifiers = definition.findStringList("modifiers").orElse(JavaList.empty());
        if(!modifiers.contains("class")) return new Ok<>(Streams.of(node));

        var nameOptional = definition.findString("name");
        if (nameOptional.isEmpty()) return new Ok<>(Streams.of(node));
        var name = nameOptional.orElsePanic();

        var childOptional = node.findNode("child");
        if (childOptional.isEmpty()) return new Ok<>(Streams.of(node));

        var child = childOptional.orElsePanic();
        if (!child.is("block")) return new Ok<>(Streams.of(node));

        var oldChildren = child.findNodeList("children").orElse(JavaList.empty());

        if (hasFactory(oldChildren, name)) {
            var children1 = oldChildren.stream()
                    .map((Node oldChild) -> cleanup(oldChild, name))
                    .flatMap(Streams::fromOption)
                    .collect(JavaList.collecting());

            return new Ok<>(Streams.of(createObject(node, modifiers, name, children1)));
        }

        return oldChildren
                .stream()
                .foldLeftToResult(new Flattening(JavaList.empty(), JavaList.empty()), (flattening1, node1) -> flattenChild(flattening1, node1, name))
                .mapValue(flattening -> splitIntoObject(node, flattening, name, modifiers));
    }

    private boolean hasFactory(List<Node> children, String outerName) {
        return children.stream().anyMatch(child -> {
            if (!child.is("function")) return false;

            var definition = child.findNode("definition");
            if (definition.isEmpty()) return false;

            var name = definition.orElsePanic().findString("name");
            if (name.isEmpty()) return false;

            return name.orElsePanic().equals(outerName);
        });
    }

    @Override
    public Result<Tuple<Node, State>, Error_> preVisit(Node node, State state) {
        return new Ok<>(new Tuple<>(node.mapNodes("children", BlockNormalizer::removeFromChildren), state));
    }

    private record Flattening(List<Node> instanceMembers, List<Node> staticMembers) {

        public Flattening withStaticMember(Node staticMember) {
            return new Flattening(instanceMembers, staticMembers.addLast(staticMember));
        }

        public Flattening withInstanceMember(Node instanceMember) {
            return new Flattening(instanceMembers.addLast(instanceMember), staticMembers);
        }
    }
}
