package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;

import java.util.List;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record ObjectLexer(String stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (!stripped().startsWith("public class ")) return None();
        var start = stripped().indexOf("{");

        var name = stripped().substring("public class ".length(), start).strip();
        var content = stripped().substring(start);
        var contentOutput = new Content(content, 0);

        var flags = List.of("export");
        return Some(new ObjectNode(flags, name, contentOutput));
    }
}