package com.meti.compile.declare;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;

import static com.meti.api.option.Options.$Option;

public class DeclarationLexer implements Lexer {
    private final JavaString input;
    private final JavaString type;

    public DeclarationLexer(JavaString input, JavaString type) {
        this.input = input;
        this.type = type;
    }

    @Override
    public Option<Node> lex() {
        if (!type.equalsToSlice("definition")) {
            return None.apply();
        }

        return $Option(() -> {
            var separator = input.firstIndexOfChar(' ').$();
            var left = input.sliceTo1(separator);
            var right = input.sliceFrom(separator);
            return MapNode.Builder("type")
                    .withString("name", right)
                    .withNode("type", new Content(left, JavaString.apply("type")))
                    .complete();
        });
    }
}
