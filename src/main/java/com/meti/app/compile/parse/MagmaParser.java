package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.core.F1;
import com.meti.api.option.Option;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute;
import com.meti.app.compile.stage.CompileException;

public record MagmaParser(List<? extends Node> input) {
    private static State parseDefinitionAttributes(State root) throws CompileException {
        try {
            var current = root.getCurrent();
            return current.apply(Attribute.Group.Definition).foldRight(root, (oldState, type) -> {
                var previous = current.apply(type).asNode();
                var newState = parseField(oldState.apply(previous));
                return newState.mapCurrent(value -> current.with(type, new NodeAttribute(value)));
            });
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private static State parseDefinitionsAttributes(State root) throws CompileException {
        try {
            return root.getCurrent().apply(Attribute.Group.Definitions).foldRight(root, (oldState, type) -> {
                try {
                    var current = oldState.getCurrent();
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

    private static State parseField(State state) throws CompileException {
        var definition = state.getCurrent();
        var name = definition.apply(Attribute.Type.Name).asInput().toOutput().compute();
        if (state.getScope().isDefined(name)) {
            throw new CompileException("'" + name + "' is already defined.");
        } else if (definition.is(Node.Type.Initialization)) {
            var value = definition.apply(Attribute.Type.Value).asNode();
            var actualType = new MagmaResolver(value, state.getScope()).resolveNodeToSingle(value);

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
            var withType = state.getCurrent().with(Attribute.Type.Identity, new NodeAttribute(newIdentity));

            return state.mapScope(scope -> scope.define(newIdentity)).apply(withType);
        } else {
            return state.mapScope(scope -> scope.define(definition));
        }
    }

    private static Stream<Parser> streamParsers(State state) {
        return Streams.apply(
                new BlockParser(state),
                new ImplementationParser(state),
                new VariableParser(state)
        );
    }

    private static State transformAST(State state, F1<Parser, Option<State>, CompileException> mapper) throws CompileException {
        try {
            return streamParsers(state)
                    .map(mapper)
                    .flatMap(Streams::optionally)
                    .first()
                    .orElse(state);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private State pareNodeAttributes(State withNodesAttributes) throws CompileException {
        try {
            var current = withNodesAttributes.getCurrent();
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

    private State parseAST(State state) throws CompileException {
        var before = transformAST(state, Parser::onEnter);
        var withDefinitionAttributes = parseDefinitionAttributes(before);
        var withDefinitionsAttributes = parseDefinitionsAttributes(withDefinitionAttributes);
        var withNodesAttributes = parseNodesAttribute(withDefinitionsAttributes);
        var withNodeAttributes = pareNodeAttributes(withNodesAttributes);
        return transformAST(withNodeAttributes, Parser::onExit);
    }

    private State parseNodesAttribute(State root) throws CompileException {
        try {
            return root.getCurrent().apply(Attribute.Group.Nodes).foldRight(root, (oldState, type) -> {
                try {
                    var current = oldState.getCurrent();
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
            return new StateBuffer(list.add(newState.getCurrent()), newState);
        }
    }
}
