package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;

import java.util.List;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record DefinitionLexer(String stripped, int indent) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (!stripped().startsWith("public static final Path ")) return None();
        var content = stripped().substring("public static final Path ".length());
        var equals = content.indexOf('=');
        var name = content.substring(0, equals).strip();
        var compiledValue = new Content(content.substring(equals + 1), 0);
        var flags = List.of("pub", "const");
        return Some(new DefinitionNode(indent(), flags, name, compiledValue));
    }
}