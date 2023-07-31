package com.meti.app.compile.imports;

import com.meti.app.compile.*;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.StringAttribute;
import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.iterate.Index;
import com.meti.java.JavaMap;
import com.meti.java.String_;

import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.fromSlice;

public record ImportLexer(String_ input) implements Lexer {
    private static Node lexFromProperties(String_ withoutStatic, Index separator) {
        var parent = withoutStatic.sliceTo(separator);
        var child = separator.nextExclusive()
                .map(withoutStatic::sliceFrom)
                .unwrapOrElse(Empty);

        return new MapNode(fromSlice("import"), JavaMap.<String_, Attribute>empty()
                .insert(fromSlice("parent"), new StringAttribute(parent))
                .insert(fromSlice("child"), new StringAttribute(child)));
    }

    private Option<Node> lex1() {
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
                .map(separator -> lexFromProperties(withoutStatic, separator))
                .unwrapOrElse(Content.ofContent(input()));
    }

    @Override
    public Option<Result<Node, CompileException>> lex() {
        return lex1().map(Ok::apply);
    }
}