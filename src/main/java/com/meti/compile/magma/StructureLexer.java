package com.meti.compile.magma;

import com.meti.compile.common.Structure;
import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;

import java.util.Arrays;
import java.util.stream.Collectors;

public record StructureLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        return text.startsWithSlice("struct ") &&
               text.containsChar('{') &&
               text.endsWithChar('}')
                ? text.firstIndexOfChar('{').map(this::extract)
                : new None<>();
    }

    private Structure extract(int fieldsStart) {
        var name = text.slice("struct ".length(), fieldsStart);
        var fields = text.slice(fieldsStart + 1, text.size() - 1);
        var fieldsList = Arrays.stream(fields.compute().split(";"))
                .filter(value -> !value.isBlank())
                .map(Text::new)
                .map(Content::new)
                .collect(Collectors.<Node>toList());
        return new Structure(name, fieldsList);
    }
}
