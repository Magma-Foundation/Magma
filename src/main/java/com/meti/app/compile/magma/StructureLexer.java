package com.meti.app.compile.magma;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.common.Structure;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.RootText;
import com.meti.app.compile.text.Text;

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
        var fieldsList = Arrays.stream(fields.computeTrimmed().split(";"))
                .filter(value -> !value.isBlank())
                .map(RootText::new)
                .map(Content::new)
                .collect(Collectors.<Node>toList());
        return new Structure(name, fieldsList);
    }
}
