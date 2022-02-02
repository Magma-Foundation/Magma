package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.core.F1;
import com.meti.api.option.Option;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute;
import com.meti.app.compile.stage.CompileException;

public abstract class AbstractVisitationStage<T extends Visitor> implements VisitationStage<T> {
    protected final List<? extends Node> input;

    public AbstractVisitationStage(List<? extends Node> input) {
        this.input = input;
    }

    protected static State parseDefinitionAttributes(State root) throws CompileException {
        try {
            var current = root.getCurrent();
            return current.apply(Attribute.Group.Definition).foldRight(root, (oldState, type) -> {
                var previous = current.apply(type).asNode();
                var newState = AbstractVisitationStage.parseField(oldState.apply(previous));
                return newState.mapCurrent(value -> current.with(type, new NodeAttribute(value)));
            });
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected static State parseDefinitionsAttributes(State root) throws CompileException {
        try {
            return root.getCurrent().apply(Attribute.Group.Definitions).foldRight(root, (oldState, type) -> {
                try {
                    var current = oldState.getCurrent();
                    var input = current.apply(type).asStreamOfNodes().foldRight(List.<Node>createList(), List::add);
                    var result = input.stream().foldRight(new StateBuffer(oldState),
                            (buffer, next) -> buffer.append(state -> AbstractVisitationStage.parseField(state.apply(next))));
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

    protected static State parseField(State state) throws CompileException {
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

            return state.mapScope(scope -> scope.define(newIdentity)).apply(newIdentity);
        } else {
            return state.mapScope(scope -> scope.define(definition));
        }
    }

    protected abstract State createInitialState();

    @Override
    public List<Node> visit() throws StreamException, CompileException {
        try {
            var initial = createInitialState();
            return input.stream()
                    .foldRight(new StateBuffer(initial), (current, next) -> current.append(state -> parseAST(state.apply(next))))
                    .list;
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected State parseAST(State state) throws CompileException {
        var before = transformAST(state, Visitor::onEnter);
        var parsed = parseImpl(before);
        var withDefinitionAttributes = AbstractVisitationStage.parseDefinitionAttributes(parsed);
        var withDefinitionsAttributes = AbstractVisitationStage.parseDefinitionsAttributes(withDefinitionAttributes);
        var withNodesAttributes = parseNodesAttribute(withDefinitionsAttributes);
        var withNodeAttributes = parseNodeAttributes(withNodesAttributes);
        return transformAST(withNodeAttributes, Visitor::onExit);
    }

    protected State parseImpl(State state) throws CompileException {
        return state;
    }

    private State parseNodeAttributes(State root) throws CompileException {
        try {
            var current = root.getCurrent();
            return current.apply(Attribute.Group.Node).foldRight(root, (oldState, type) -> {
                var previous = current.apply(type).asNode();
                var newState = parseAST(oldState.apply(previous));
                return newState.mapCurrent(value -> current.with(type, new NodeAttribute(value)));
            });
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private State parseNodesAttribute(State root) throws CompileException {
        try {
            return root.getCurrent().apply(Attribute.Group.Nodes).foldRight(root, (oldState, type) -> {
                try {
                    var current = oldState.getCurrent();
                    var input = current.apply(type).asStreamOfNodes().foldRight(List.<Node>createList(), List::add);
                    var result = input.stream().foldRight(new StateBuffer(oldState),
                            (buffer, next) -> buffer.append(state -> AbstractVisitationStage.this.parseAST(state.apply(next))));
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

    protected State transformAST(State state, F1<T, Option<State>, CompileException> mapper) throws CompileException {
        try {
            return streamVisitors(state)
                    .map(mapper)
                    .flatMap(Streams::optionally)
                    .first()
                    .orElse(state);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected static class StateBuffer {
        protected final List<Node> list;
        protected final State state;

        protected StateBuffer(State initial) {
            this(List.createList(), initial);
        }

        private StateBuffer(List<Node> list, State state) {
            this.list = list;
            this.state = state;
        }

        public <E extends Exception> StateBuffer append(F1<State, State, E> mapper) throws E {
            var newState = mapper.apply(state);
            return new StateBuffer(list.add(newState.getCurrent()), newState);
        }
    }
}
