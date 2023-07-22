package com.meti.app;

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
    public Option<Renderable> lex() {
        return input.firstIndexOfSlice("import ")
                .flatMap(index -> index.nextExclusive("import ".length()))
                .map(input::sliceFrom)
                .map(this::lexValid);
    }

    private Renderable lexValid(String_ withoutPrefix) {
        var withoutStatic = withoutPrefix.firstIndexOfSlice("static ")
                .flatMap(staticIndex -> staticIndex.nextExclusive("static ".length()))
                .map(withoutPrefix::sliceFrom).unwrapOrElse(withoutPrefix);

        return withoutStatic.lastIndexOfChar('.')
                .<Renderable>map(separator -> lexFromProperties(withoutStatic, separator))
                .unwrapOrElse(Content.ofContent(input()));
    }
}