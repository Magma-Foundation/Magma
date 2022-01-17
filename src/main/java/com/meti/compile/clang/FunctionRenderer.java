package com.meti.compile.clang;

import com.meti.collect.StreamException;
import com.meti.compile.CompileException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.compile.render.Renderer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.ArrayList;

public record FunctionRenderer(Node node) implements Renderer {
    @Override
    public Option<Text> render() throws CompileException {
        if (node.is(Node.Type.Abstraction) || node.is(Node.Type.Implementation)) {
            var identity = node.apply(Attribute.Type.Identity).asNode();
            var renderedIdentity = identity.apply(Attribute.Type.Value).asText();

            ArrayList<String> parameters;
            try {
                parameters = node.apply(Attribute.Type.Parameters)
                        .asStreamOfNodes1()
                        .map(value -> value.apply(Attribute.Type.Value))
                        .map(Attribute::asText)
                        .map(Text::computeTrimmed)
                        .foldRight(new ArrayList<>(), (strings, s) -> {
                            strings.add(s);
                            return strings;
                        });
            } catch (StreamException e) {
                throw new CompileException(e);
            }

            parameters.sort(String::compareTo);
            var renderedParameters = String.join(",", parameters);
            var withIdentity = renderedIdentity.append("(" + renderedParameters + ")");
            var withValue = attachValue(withIdentity);
            return new Some<>(withValue);
        }
        return new None<>();
    }

    private Text attachValue(Text withIdentity) throws AttributeException {
        Text withValue;
        if (node.is(Node.Type.Implementation)) {
            var value = node.apply(Attribute.Type.Value).asNode();
            var renderedValue = value.apply(Attribute.Type.Value).asText();
            withValue = withIdentity.append(renderedValue);
        } else {
            withValue = withIdentity.append(";");
        }
        return withValue;
    }
}
