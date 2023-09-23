package com.meti.compile.imports;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.api.option.Options;
import com.meti.compile.Lexer;
import com.meti.compile.Node;

public record ImportLexer(JavaString stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        return Options.$Option(() -> {
            var index = stripped
                    .firstIndexOfSlice("import ").$()
                    .nextBy("import ".length()).$();

            var javaString = this.stripped().sliceFrom(index);
            var name = javaString.strip();
            var separator = name.lastIndexOfChar('.').$();
            var parent = name.sliceTo1(separator).strip();
            var child = name.sliceFrom(separator.next().$()).strip();
            return new ImportNode(parent.value(), child.value());
        });
    }
}