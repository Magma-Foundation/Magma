package com.meti;

import static com.meti.None.None;
import static com.meti.Some.Some;

public record ImportLexer(String stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (stripped().startsWith("import ")) {
            var isStatic = stripped().startsWith("import static ");
            var content = stripped().substring(isStatic ? "import static ".length() : "import ".length());

            var last = content.lastIndexOf('.');
            var child = content.substring(last + 1).strip();
            var parent = content.substring(0, last).strip();

            return Some(child.equals("*") ? new ImportAllNode(parent) : new ImportChildNode(child, parent));
        }
        return None();
    }
}