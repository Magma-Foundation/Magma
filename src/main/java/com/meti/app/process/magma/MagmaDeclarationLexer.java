package com.meti.app.process.magma;

import com.meti.app.CompileException;
import com.meti.app.Input;
import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;
import com.meti.app.attribute.NodeAttribute;
import com.meti.app.node.Content;
import com.meti.app.node.Node;
import com.meti.app.process.FilteredLexer;

import java.util.stream.Stream;

public class MagmaDeclarationLexer extends FilteredLexer {
    public MagmaDeclarationLexer(Input input) {
        super(input);
    }

    @Override
    protected boolean isValid() {
        return input.containsChar(':') || input.containsChar('=');
    }

    @Override
    protected Node processValid() throws CompileException {
        var value = new Content(input.value());
        return new Declaration(value);
    }

    private record Declaration(Node value) implements Node {
        @Override
        public Attribute apply(Attribute.Type type) throws AttributeException {
            if (type == Attribute.Type.Value) return new NodeAttribute(value);
            else throw new AttributeException(type);
        }

        @Override
        public boolean is(Type type) {
            return type == Type.Declaration;
        }

        @Override
        public Stream<Attribute.Type> stream(Attribute.Group group) {
            return group == Attribute.Group.Field
                    ? Stream.of(Attribute.Type.Value)
                    : Stream.empty();
        }

        @Override
        public Node with(Attribute.Type type, Attribute value) throws AttributeException {
            return type == Attribute.Type.Value
                    ? new Declaration(value.asNode())
                    : this;
        }
    }
}
