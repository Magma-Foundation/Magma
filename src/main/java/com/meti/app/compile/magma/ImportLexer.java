package com.meti.app.compile.magma;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.common.Extern;
import com.meti.app.compile.common.Import;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;
import com.meti.app.source.Packaging;

public record ImportLexer(Input text) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (text.startsWithSlice("extern ")) {
            var slice = text.slice("extern ".length());
            if (slice.startsWithSlice("import ")) {
                var name = slice.slice("import ".length());
                return new Some<>(new Extern(name));
            }
        }
        if (text.startsWithSlice("import ")) {
            var root = text.slice("import ".length());
            var package_ = root.lastIndexOfChar('.').map(nameSeparator -> {
                var args = root.slice(0, nameSeparator).computeTrimmed()
                        .split("\\.");
                var name = root.slice(nameSeparator + 1).computeTrimmed();
                return new Packaging(name, args);
            }).orElse(new Packaging(root.computeTrimmed()));
            return new Some<>(new Import(package_));
        }
        return new None<>();
    }
}
