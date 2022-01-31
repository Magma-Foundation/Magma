package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.stage.CompileException;

public class MagmaParser {
    private final List<Node> input;
    private List<String> names = List.createList();

    public MagmaParser(List<Node> input) {
        this.input = input;
    }

    public List<Node> parse() throws StreamException, CompileException {
        var output = List.<Node>createList();
        for (int i = 0; i < input.size(); i++) {
            var element = input.apply(i);
            if (element.is(Node.Type.Declaration)) {
                var identity = element.apply(Attribute.Type.Identity).asNode();
                var name = identity.apply(Attribute.Type.Name).asInput().toOutput().compute();
                if (names.contains(name)) {
                    throw new CompileException("'" + name + "' is already defined.");
                } else {
                    names = names.add(name);
                    output = output.add(element);
                }
            } else if (element.is(Node.Type.Variable)) {
                var value = element.apply(Attribute.Type.Value).asInput();
                var format = value.toOutput().compute();
                if (!names.contains(format)) {
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
}
