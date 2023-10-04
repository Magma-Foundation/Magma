package com.meti.compile;

import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.compile.block.BlockLexer;
import com.meti.compile.clazz.ClassLexer;
import com.meti.compile.function.RecordLexer;
import com.meti.compile.imports.ImportLexer;
import com.meti.compile.node.Node;
import com.meti.compile.package_.PackageLexer;
import com.meti.compile.trait.InterfaceLexer;

public record JavaLexer(JavaString stripped, JavaString type) implements Lexer {
    static Iterator<Lexer> enumerateLexers(JavaString stripped) {
        return Iterators.from(
                new InterfaceLexer(stripped),
                new PackageLexer(stripped),
                new ImportLexer(stripped),
                new BlockLexer(stripped),
                new ClassLexer(stripped),
                new RecordLexer(stripped)
        );
    }

    @Override
    public Option<Node> lex() {
        return enumerateLexers(this.stripped())
                .map(Lexer::lex)
                .flatMap(Iterators::fromOption)
                .head();
    }
}