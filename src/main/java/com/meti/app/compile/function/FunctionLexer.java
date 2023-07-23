package com.meti.app.compile.function;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.Content;
import com.meti.app.compile.Lexer;
import com.meti.app.compile.Node;
import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.java.JavaSet;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;

public record FunctionLexer(String_ line) implements Lexer {
    private Option<Node> lex1() {
        return $Option(() -> {
            var paramStart = line().firstIndexOfChar('(').$();
            var paramEnd = line().firstIndexOfChar(')').$();

            var beforeParams = line().sliceTo(paramStart);
            var nameSeparator = beforeParams.firstIndexOfChar(' ').$();
            var type = beforeParams.sliceTo(nameSeparator);

            var paramString = line().sliceBetween(paramStart.nextExclusive().$().to(paramEnd).$());
            var parameters = paramString.split(",")
                    .map(String_::strip)
                    .filter(value -> !value.isEmpty())
                    .map(Content::new)
                    .collect(JavaSet.asSet());

            var name = beforeParams.sliceFrom(nameSeparator.nextExclusive().$());

            var bodyStart = line().firstIndexOfChar('{').$();
            var body = line().sliceFrom(bodyStart);
            var node = new Content(body);

            return new ExplicitImplementation(JavaSet.empty(), name, parameters, node, new Content(type));
        });
    }

    @Override
    public Option<Result<Node, CompileException>> lex() {
        return lex1().map(Ok::apply);
    }
}