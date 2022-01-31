package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.stage.CompileException;

public class MagmaParser {
    private final List<Node> input;
    private List<Node> frame = List.createList();

    public MagmaParser(List<Node> input) {
        this.input = input;
    }

    public List<Node> parse() throws StreamException, CompileException {
        var output = List.<Node>createList();
        for (int i = 0; i < input.size(); i++) {
            var element = input.apply(i);
            if (element.is(Node.Type.Declaration)) {
                var oldIdentity = element.apply(Attribute.Type.Identity).asNode();
                var name = oldIdentity.apply(Attribute.Type.Name).asInput().toOutput().compute();
                if (isDefined(name)) {
                    throw new CompileException("'" + name + "' is already defined.");
                } else {
                    if (oldIdentity.is(Node.Type.Initialization)) {
                        var value = oldIdentity.apply(Attribute.Type.Value).asNode();
                        Node actualType;
                        if (value.is(Node.Type.Boolean)) {
                            actualType = Primitive.Bool;
                        } else if (value.is(Node.Type.Integer)) {
                            actualType = new IntegerType(true, 16);
                        } else {
                            throw new CompileException("Cannot resolve type of node: " + value);
                        }

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
                        frame = frame.add(newIdentity);
                        output = output.add(element.with(Attribute.Type.Identity, new NodeAttribute(newIdentity)));
                    } else {
                        frame = frame.add(oldIdentity);
                        output = output.add(element);
                    }
                }
            } else if (element.is(Node.Type.Variable)) {
                var value = element.apply(Attribute.Type.Value).asInput();
                var format = value.toOutput().compute();
                if (!isDefined(format)) {
                    throw new CompileException("'%s' is not defined.".formatted(format));
                } else {
                    output = output.add(element);
                }
            } else {
                output = output.add(element);
            }
        }
        return output;
    }

    private boolean isDefined(String name) throws StreamException, AttributeException {
        return frame.stream().anyMatch(declaration -> declaration.apply(Attribute.Type.Name).asInput().equalsSlice(name));
    }
}
