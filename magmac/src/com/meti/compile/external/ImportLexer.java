package com.meti.compile.external;

import com.meti.collect.Index;
import com.meti.collect.option.Option;
import com.meti.collect.option.Options;
import com.meti.compile.Lexer;
import com.meti.compile.external.ImportAllNode;
import com.meti.compile.external.ImportChildNode;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;
import static com.meti.collect.option.Some.Some;

public record ImportLexer(JavaString stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var prefix = stripped.firstIndexOfSlice("import ").$();
            var staticIndex = stripped.firstIndexOfSlice("import static ");

            var content = stripped.sliceFrom(staticIndex.orElse(prefix));

            var last = content.lastIndexOfChar('.').$();
            var child = content.sliceFrom(last.next().$()).strip();
            var parent = content.sliceTo(last).strip();

            return child.equalsToSlice("*")
                    ? new ImportAllNode(parent)
                    : new ImportChildNode(child, parent);
        });
    }
}