package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.stage.CompileException;

public class MagmaParser {
    private final List<Node> input;

    public MagmaParser(List<Node> input) {
        this.input = input;
    }

    public List<Node> parse() throws StreamException, CompileException {
        var state = new State();

        for (int i = 0; i < input.size(); i++) {
            var element = input.apply(i);
            if (element.is(Node.Type.Declaration)) {
                var oldIdentity = element.apply(Attribute.Type.Identity).asNode();
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

                        state.setScope(state.getScope().define(newIdentity));
                        state.setOutput(state.getOutput().add(element.with(Attribute.Type.Identity, new NodeAttribute(newIdentity))));
                    } else {
                        state.setScope(state.getScope().define(oldIdentity));
                        state.setOutput(state.getOutput().add(element));
                    }
                }
            } else if (element.is(Node.Type.Variable)) {
                var value = element.apply(Attribute.Type.Value).asInput();
                var format = value.toOutput().compute();
                if (!state.getScope().isDefined(format)) {
                    throw new CompileException("'%s' is not defined.".formatted(format));
                } else {
                    state.setOutput(state.getOutput().add(element));
                }
            } else {
                state.setOutput(state.getOutput().add(element));
            }
        }
        return state.getOutput();
    }

    private Node resolveNode(Node value, Scope scope) throws CompileException, StreamException {
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
        private Scope scope;
        private List<Node> output;

        private State() {
            this(new Scope(), List.createList());
        }

        private State(Scope scope, List<Node> output) {
            this.scope = scope;
            this.output = output;
        }

        public List<Node> getOutput() {
            return output;
        }

        public void setOutput(List<Node> output) {
            this.output = output;
        }

        public Scope getScope() {
            return scope;
        }

        public void setScope(Scope scope) {
            this.scope = scope;
        }
    }
}
