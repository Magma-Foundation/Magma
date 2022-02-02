package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.core.F1;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute;
import com.meti.app.compile.stage.CompileException;

public record MagmaParser(List<? extends Node> input) {
    private State parseAST(State state) throws CompileException {
        var before = beforeAST(state);
        var withDefinitionAttributes = parseDefinitionAttributes(before);
        var withDefinitionsAttributes = parseDefinitionsAttributes(withDefinitionAttributes);
        var withNodesAttributes = parseNodesAttribute(withDefinitionsAttributes);
        var withNodeAttributes = pareNodeAttributes(withNodesAttributes);
        return afterAST(withNodeAttributes);
    }

    private State beforeAST(State state) throws CompileException {
        var element = state.current;
        if (element.is(Node.Type.Variable)) {
            return parseVariable(state);
        } else if (element.is(Node.Type.Block)) {
            return state.mapScope(Scope::enter);
        } else {
            return state;
        }
    }

    private State pareNodeAttributes(State withNodesAttributes) throws CompileException {
        try {
            var current = withNodesAttributes.current;
            return current.apply(Attribute.Group.Node).foldRight(withNodesAttributes, (oldState, type) -> {
                var previous = current.apply(type).asNode();
                var newState = parseAST(oldState.apply(previous));
                return newState.mapCurrent(value -> current.with(type, new NodeAttribute(value)));
            });
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    public List<Node> parse() throws StreamException, CompileException {
        try {
            return input.stream()
                    .foldRight(new StateBuffer(), (current, next) -> current.append(state -> parseAST(state.apply(next))))
                    .list;
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private State parseDefinitionAttributes(State root) throws CompileException {
        try {
            var current = root.current;
            return current.apply(Attribute.Group.Definition).foldRight(root, (oldState, type) -> {
                var previous = current.apply(type).asNode();
                var newState = parseField(oldState.apply(previous));
                return newState.mapCurrent(value -> current.with(type, new NodeAttribute(value)));
            });
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private State parseDefinedImplementation(State state, Node element) throws CompileException {
        var identity = element.apply(Attribute.Type.Identity).asNode();
        var expectedType = identity.apply(Attribute.Type.Type).asNode();
        var value = element.apply(Attribute.Type.Value).asNode();
        var assignableTypes = resolveNodeToMultiple(value, state.scope);

        Node typeToSet;
        if (expectedType.is(Node.Type.Implicit)) {
            typeToSet = assignableTypes.first().orElse(Primitive.Void);
        } else if (assignableTypes.contains(expectedType)) {
            typeToSet = expectedType;
        } else {
            var format = "Expected function to return '%s', but was actually '%s'.";
            var message = format.formatted(expectedType, assignableTypes);
            throw new CompileException(message);
        }
        var newIdentity = identity.with(Attribute.Type.Type, new NodeAttribute(typeToSet));
        var newElement = element.with(Attribute.Type.Identity, new NodeAttribute(newIdentity));
        return state.apply(newElement);
    }

    private State afterAST(State state) throws CompileException {
        var root = state.current;
        if (root.is(Node.Type.Block)) {
            return state.mapScope(Scope::exit);
        } else if (root.is(Node.Type.Implementation)) {
            return parseDefinedImplementation(state, root);
        } else {
            return state;
        }
    }

    private State parseDefinitionsAttributes(State root) throws CompileException {
        try {
            return root.current.apply(Attribute.Group.Definitions).foldRight(root, (oldState, type) -> {
                try {
                    var current = oldState.current;
                    var input = current.apply(type).asStreamOfNodes().foldRight(List.<Node>createList(), List::add);
                    var result = input.stream().foldRight(new StateBuffer(oldState),
                            (buffer, next) -> buffer.append(state -> parseField(state.apply(next))));
                    var attribute = new NodesAttribute(result.list);
                    var newCurrent = current.with(type, attribute);
                    return result.state.apply(newCurrent);
                } catch (StreamException e) {
                    throw new CompileException(e);
                }
            });
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private State parseField(State state) throws CompileException {
        var definition = state.current;
        var name = definition.apply(Attribute.Type.Name).asInput().toOutput().compute();
        if (state.getScope().isDefined(name)) {
            throw new CompileException("'" + name + "' is already defined.");
        } else if (definition.is(Node.Type.Initialization)) {
            var value = definition.apply(Attribute.Type.Value).asNode();
            var actualType = resolveNodeToSingle(value, state.getScope());

            var expectedType = definition.apply(Attribute.Type.Type).asNode();

                /*
                Check for potential implicit conversions here...
                 */
            Node typeToDefine;
            if (expectedType.is(Node.Type.Implicit) || actualType.equals(expectedType)) {
                typeToDefine = actualType;
            } else {
                var format = "Expected a type of '%s' but was actually '%s'.";
                var message = format.formatted(expectedType, actualType);
                throw new CompileException(message);
            }

            var newIdentity = definition.with(Attribute.Type.Type, new NodeAttribute(typeToDefine));
            var withType = state.current.with(Attribute.Type.Identity, new NodeAttribute(newIdentity));

            return state.mapScope(scope -> scope.define(newIdentity)).apply(withType);
        } else {
            return state.mapScope(scope -> scope.define(definition));
        }
    }

    private State parseNodesAttribute(State root) throws CompileException {
        try {
            return root.current.apply(Attribute.Group.Nodes).foldRight(root, (oldState, type) -> {
                try {
                    var current = oldState.current;
                    var input = current.apply(type).asStreamOfNodes().foldRight(List.<Node>createList(), List::add);
                    var result = input.stream().foldRight(new StateBuffer(oldState),
                            (buffer, next) -> buffer.append(state -> MagmaParser.this.parseAST(state.apply(next))));
                    var attribute = new NodesAttribute(result.list);
                    var newCurrent = current.with(type, attribute);
                    return result.state.apply(newCurrent);
                } catch (StreamException e) {
                    throw new CompileException(e);
                }
            });
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private State parseVariable(State state) throws CompileException {
        var value = state.current.apply(Attribute.Type.Value).asInput();
        var format = value.toOutput().compute();
        if (!state.getScope().isDefined(format)) {
            throw new CompileException("'%s' is not defined.".formatted(format));
        } else {
            return state.apply(state.current);
        }
    }

    private List<Node> resolveNodeToMultiple(Node root, Scope scope) throws CompileException {
        if (root.is(Node.Type.Variable)) {
            var variableName = root.apply(Attribute.Type.Value).asInput()
                    .toOutput()
                    .compute();
            return List.apply(scope.lookup(variableName)
                    .map(node -> node.apply(Attribute.Type.Type).asNode())
                    .orElseThrow(() -> {
                        var format = "'%s' is not defined.";
                        var message = format.formatted(variableName);
                        return new CompileException(message);
                    }));
        } else if (root.is(Node.Type.Boolean)) {
            return List.apply(Primitive.Bool);
        } else if (root.is(Node.Type.Integer)) {
            var toReturn = List.<Node>apply(new IntegerType(true, 16));
            var value = root.apply(Attribute.Type.Value).asInteger();
            if (value >= 0) {
                return toReturn.add(new IntegerType(false, 16));
            } else {
                return toReturn;
            }
        } else if (root.is(Node.Type.Return)) {
            var innerValue = root.apply(Attribute.Type.Value).asNode();
            return resolveNodeToMultiple(innerValue, scope);
        } else if (root.is(Node.Type.Block)) {
            try {
                return root.apply(Attribute.Type.Children)
                        .asStreamOfNodes()
                        .foldRight(List.<Node>createList(), List::add)
                        .last()
                        .map(last -> resolveNodeToMultiple(last, scope))
                        .orElse(List.apply(Primitive.Void));
            } catch (StreamException e) {
                throw new CompileException(e);
            }
        } else {
            throw new CompileException("Cannot resolve type of node: " + root);
        }
    }

    private Node resolveNodeToSingle(Node value, Scope scope) throws CompileException {
        return resolveNodeToMultiple(value, scope).first().orElseThrow(() -> {
            var format = "No types exist for node:\n%s";
            var message = format.formatted(value);
            return new CompileException(message);
        });
    }

    private static class StateBuffer {
        private final List<Node> list;
        private final State state;

        private StateBuffer() {
            this(List.createList(), new State());
        }

        private StateBuffer(List<Node> list, State state) {
            this.list = list;
            this.state = state;
        }

        private StateBuffer(State state) {
            this(List.createList(), state);
        }

        public <E extends Exception> StateBuffer append(F1<State, State, E> mapper) throws E {
            var newState = mapper.apply(state);
            return new StateBuffer(list.add(newState.current), newState);
        }
    }
}
