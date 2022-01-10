package com.meti.compile.magma;

import com.meti.compile.common.Extern;
import com.meti.compile.common.Import;
import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;
import com.meti.source.Packaging;

public record ImportLexer(Text text) implements Lexer {
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
