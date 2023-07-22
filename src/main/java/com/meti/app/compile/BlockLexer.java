package com.meti.app.compile;

import com.meti.app.compile.block.Block;
import com.meti.core.Option;
import com.meti.java.JavaList;
import com.meti.java.String_;

import static com.meti.core.Options.$$;
import static com.meti.core.Options.$Option;

public record BlockLexer(String_ line) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var stripped = line().strip();
            var bodyStart = stripped.firstIndexOfChar('{').$();
            var bodyEnd = stripped.lastIndexOfChar('}').$();
            if (!bodyStart.isStart() || !bodyEnd.isEnd()) {
                return $$();
            }

            var range = bodyStart.nextExclusive().$().to(bodyEnd).$();
            var content = stripped.sliceBetween(range);
            var map = new Splitter(content).split()
                    .map(String_::strip)
                    .filter(value -> !value.isEmpty())
                    .map(Content::new)
                    .collect(JavaList.asList());
            return new Block(map);
        });
    }
}