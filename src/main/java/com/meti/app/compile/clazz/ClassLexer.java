package com.meti.app.compile.clazz;

import com.meti.app.compile.Content;
import com.meti.app.compile.Lexer;
import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;

public record ClassLexer(String_ line) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var classIndex = line
                    .firstIndexOfSlice("class ").$()
                    .nextExclusive("class ".length()).$();

            var contentStart = line.firstIndexOfChar('{').$();

            var name = line.sliceBetween(classIndex.to(contentStart).$()).strip();
            var body = line.sliceFrom(contentStart);

            return new Class_(name, new Content(body));
        });
    }
}