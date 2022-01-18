package com.meti.app.compile.common.condition;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Text;

public record ConditionLexer(Text text) implements Lexer {
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
        var condition = new Content(callerText);
        var body = new Content(calleeText);
        return new Condition(Node.Type.If, condition, body);
    }
}
