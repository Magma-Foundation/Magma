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

import static com.meti.app.compile.node.EmptyNode.EmptyNode_;

public record MagmaParser(List<? extends Node> input) {
    private State afterAST(State state) {
        if (state.current.is(Node.Type.Block)) {
            return state.mapScope(Scope::exit);
        } else {
            return state;
        }
    }

    private State beforeAST(State state) throws CompileException {
        var element = state.current;
        if (element.is(Node.Type.Declaration)) {
            return parseDefinition(state);
        } else if (element.is(Node.Type.Variable)) {
            return parseVariable(state);
        } else if (element.is(Node.Type.Block)) {
            return state.mapScope(Scope::enter);
        } else {
            return state;
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

    private State parseAST(State state) throws CompileException {
        var before = beforeAST(state);
        var withNodesAttributes = parseNodesAttribute(before);
        var withNodeAttributes = pareNodeAttributes(withNodesAttributes);
        return afterAST(withNodeAttributes);
    }

    private State parseDefinition(State state) throws CompileException {
        var oldIdentity = state.current.apply(Attribute.Type.Identity).asNode();
        var name = oldIdentity.apply(Attribute.Type.Name).asInput().toOutput().compute();
        if (state.getScope().isDefined(name)) {
            throw new CompileException("'" + name + "' is already defined.");
        } else {
            if (oldIdentity.is(Node.Type.Initialization)) {
                var value = oldIdentity.apply(Attribute.Type.Value).asNode();
                var actualType = resolveNode(value, state.getScope());
                var expectedType = oldIdentity.apply(Attribute.Type.Type).asNode();

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

                var newIdentity = oldIdentity.with(Attribute.Type.Type, new NodeAttribute(typeToDefine));
                var withType = state.current.with(Attribute.Type.Identity, new NodeAttribute(newIdentity));

                return state.mapScope(scope -> scope.define(newIdentity)).apply(withType);
            } else {
                return state.mapScope(scope -> scope.define(oldIdentity));
            }
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

    private Node resolveNode(Node value, Scope scope) throws CompileException {
        if (value.is(Node.Type.Variable)) {
            var variableName = value.apply(Attribute.Type.Value).asInput()
                    .toOutput()
                    .compute();
            return scope.lookup(variableName)
                    .map(node -> node.apply(Attribute.Type.Type).asNode())
                    .orElseThrow(() -> {
                        var format = "'%s' is not defined.";
                        var message = format.formatted(variableName);
                        return new CompileException(message);
                    });
        } else if (value.is(Node.Type.Boolean)) {
            return Primitive.Bool;
        } else if (value.is(Node.Type.Integer)) {
            return new IntegerType(true, 16);
        } else {
            throw new CompileException("Cannot resolve type of node: " + value);
        }
    }

    private static class State {
        private final Node current;
        private Scope scope;

        public State() {
            this(EmptyNode_);
        }

        public State(Node current) {
            this(current, new Scope());
        }

        public State(Node current, Scope scope) {
            this.current = current;
            this.scope = scope;
        }

        public State apply(Node element) {
            return new State(element, scope);
        }

        public <E extends Exception> State mapCurrent(F1<Node, Node, E> mapper) throws E {
            return new State(mapper.apply(current), scope);
        }

        private <E extends Exception> State mapScope(F1<Scope, Scope, E> mapper) throws E {
            var oldScope = getScope();
            var newScope = mapper.apply(oldScope);
            return setScope(newScope);
        }

        public Scope getScope() {
            return scope;
        }

        public State setScope(Scope scope) {
            this.scope = scope;
            return this;
        }
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
