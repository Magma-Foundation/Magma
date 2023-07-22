package com.meti.app.compile.imports;

import com.meti.app.compile.Content;
import com.meti.app.compile.Lexer;
import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.iterate.Index;
import com.meti.java.String_;

import static com.meti.java.JavaString.Empty;

public record ImportLexer(String_ input) implements Lexer {
    private static Import lexFromProperties(String_ withoutStatic, Index separator) {
        var parent = withoutStatic.sliceTo(separator);
        var child = separator.nextExclusive()
                .map(withoutStatic::sliceFrom)
                .unwrapOrElse(Empty);

        return new Import(parent, child);
    }

    @Override
    public Option<Node> lex() {
        return input.firstIndexOfSlice("import ")
                .flatMap(index -> index.nextExclusive("import ".length()))
                .map(input::sliceFrom)
                .map(this::lexValid);
    }

    private Node lexValid(String_ withoutPrefix) {
        var withoutStatic = withoutPrefix.firstIndexOfSlice("static ")
                .flatMap(staticIndex -> staticIndex.nextExclusive("static ".length()))
                .map(withoutPrefix::sliceFrom).unwrapOrElse(withoutPrefix);

        return withoutStatic.lastIndexOfChar('.')
                .<Node>map(separator -> lexFromProperties(withoutStatic, separator))
                .unwrapOrElse(Content.ofContent(input()));
    }
}