package com.meti.compile;

import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;

public record StructureLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        return text.startsWithSlice("struct ")
                ? text.firstIndexOfChar('{').map(this::extract)
                : new None<>();
    }

    private Structure extract(Integer index) {
        var name = text.slice("struct ".length(), index);
        return new Structure(name);
    }
}
