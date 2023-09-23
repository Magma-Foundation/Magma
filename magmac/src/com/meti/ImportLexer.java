package com.meti;

public record ImportLexer(JavaString stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        return stripped().firstIndexOfSlice("import ")
                .flatMap(option -> option.nextBy("import ".length()))
                .map(index -> {
                    var name = stripped().slice(index).strip();
                    var separator = name.lastIndexOf('.');
                    var parent = name.substring(0, separator).strip();
                    var child = name.substring(separator + 1).strip();
                    return new ImportNode(parent, child);
                });
    }
}