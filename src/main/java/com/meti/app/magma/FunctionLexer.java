package com.meti.app.magma;

import com.meti.api.option.Option;
import com.meti.app.clang.AbstractProcessor;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;

public class FunctionLexer extends AbstractProcessor<Node> {
    private final Input input;

    public FunctionLexer(Input input) {
        this.input = input;
    }

    @Override
    protected boolean validate() {
        return input.startsWithString("def ") && paramStart().isPresent();
    }

    private Option<Integer> paramStart() {
        return input.firstIndexOfChar('(');
    }

    @Override
    protected Node processDefined() throws CompileException {
        var paramStart = paramStart().orElseThrow(() -> new CompileException("No param start."));
        var name = new Input(input.slice("def ".length(), paramStart));

        var typeSeparator = input.firstIndexOfChar(':').orElse(-1);
        var returnSeparator = input.firstIndexOfChar('=').orElse(-1);

        var type = new Input(input.slice(typeSeparator + 1, returnSeparator));

        return new Node() {
            @Override
            public boolean is(Type type) {
                return type == Type.Function;
            }

            @Override
            public Attribute apply(Attribute.Type type) throws AttributeException {
                if (type == Attribute.Type.Identity) return new InputAttribute(name);
                throw new AttributeException("Unknown type: " + type);
            }
        };
    }
}
