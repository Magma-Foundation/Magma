package com.meti.app.compile.common.condition;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;

public record ConditionLexer(Input text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.startsWithSlice("if")) {
            return text.firstIndexOfChar('(')
                    .flatMap(start -> text.firstIndexOfChar(')')
                            .map(end -> lexImpl(start, end)));
        }
        return new None<>();
    }

    private Condition lexImpl(Integer start, Integer end) {
        var callerText = text.slice(start + 1, end);
        var calleeText = text.slice(end + 1);
        var condition = new InputNode(callerText);
        var body = new InputNode(calleeText);
        return new Condition(Node.Type.If, condition, body);
    }
}
