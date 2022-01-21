package com.meti.app.compile.stage;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.common.Declaration;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.Structure;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute1;
import com.meti.app.compile.scope.StructureType;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;
import com.meti.app.source.Packaging;

public class CMagmaPipeline {
    private final CMagmaNodeResolver resolver = new CMagmaNodeResolver();
    private final AfterStreamStage modifier = new CMagmaModifier();
    private final CFormatter formatter;
    private final List<Node> input;
    private final CFlattener flattener = new CFlattener();

    public CMagmaPipeline(Packaging thisPackage, List<Node> input) {
        this.formatter = new CFormatter(thisPackage);
        this.input = input;
    }

    public Stream<Node> apply() throws CompileException {
        try {
            var parsed = input.stream()
                    .foldRight(new State(), this::parse)
                    .stream()
                    .foldRight(List.<Node>createList(), List::add);
            var resolved = perform(resolver, parsed);
            var formatted = perform(formatter, resolved);
            var modified = perform(modifier, formatted);
            return perform(flattener, modified).stream();
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private State parse(State state, Node node) throws CompileException {
        if (node.is(Node.Type.Implementation)) {
            try {
                var oldValue = node.apply(Attribute.Type.Value).asNode();
                var oldChildren = oldValue.apply(Attribute.Type.Children)
                        .asStreamOfNodes1()
                        .foldRight(List.<Node>createList(), List::add);

                var hasSubFunction = oldChildren.stream().anyMatch(child -> child.is(Node.Type.Implementation));
                List<Node> newChildren;
                if (hasSubFunction) {
                    var scoped = node.apply(Attribute.Type.Identity).asNode().apply(Attribute.Type.Name).asInput().toOutput();

                    var formattedName = scoped.appendSlice("_this").compute();
                    var formattedType = scoped.appendSlice("_t").compute();

                    var scopeName = new RootText(formattedName);
                    var structureName = new RootText(formattedType);
                    var type = new StructureType(structureName);

                    var identity = new EmptyField(scopeName, type);
                    newChildren = oldChildren.insert(0, new Declaration(identity));
                    var newValue = oldValue.with(Attribute.Type.Children, new NodesAttribute1(newChildren));
                    var newNode = node.with(Attribute.Type.Value, new NodeAttribute(newValue));

                    return state.add(newNode).add(new Structure(structureName));
                } else {
                    return state.add(node);
                }
            } catch (StreamException e) {
                throw new CompileException(e);
            }
        }

        if (node.is(Node.Type.Declaration)) {
            var identity = node.apply(Attribute.Type.Identity).asNode();
            var name = identity.apply(Attribute.Type.Name).asInput();
            if (state.isDefined(name)) {
                throw new CompileException(name.toOutput().compute() + " is already defined.");
            } else {
                return state.define(identity).add(node);
            }
        }
        if (node.is(Node.Type.Variable)) {
            var value = node.apply(Attribute.Type.Value).asInput();
            if (!state.isDefined(value)) {
                throw new CompileException(value.toOutput().compute() + " is not defined.");
            }
        }
        return state.add(node);
    }

    private List<Node> perform(AbstractStage stage, List<Node> current) throws StreamException {
        return current.stream()
                .map(stage::transformNodeAST)
                .foldRight(List.createList(), List::add);
    }

    record State(List<List<Node>> scope, List<Node> buffer) {
        public State() {
            this(List.createList(), List.createList());
        }

        public State add(Node node) {
            var next = buffer.add(node);
            return new State(scope, next);
        }

        public State define(Node definition) throws CompileException {
            if (definition.is(Node.Type.Declaration) || definition.is(Node.Type.Initialization)) {
                var newScope = scope.ensure(List::createList)
                        .mapLast(nodeList -> nodeList.add(definition));

                return new State(newScope, buffer);
            }

            throw new CompileException(definition + " must be a definition.");
        }

        public boolean isDefined(Input name) {
            try {
                return scope.stream().flatMap(List::stream)
                        .map(value -> value.apply(Attribute.Type.Name))
                        .map(Attribute::asInput)
                        .anyMatch(name::equalsInput);
            } catch (StreamException e) {
                return false;
            }
        }

        public Stream<Node> stream() {
            return buffer.stream();
        }
    }
}
