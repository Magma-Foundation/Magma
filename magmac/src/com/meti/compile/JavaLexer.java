package com.meti.compile;

import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.compile.block.BlockLexer;
import com.meti.compile.clazz.ClassLexer;
import com.meti.compile.declare.DeclarationLexer;
import com.meti.compile.function.RecordLexer;
import com.meti.compile.imports.ImportLexer;
import com.meti.compile.node.Node;
import com.meti.compile.package_.PackageLexer;
import com.meti.compile.trait.InterfaceLexer;

public record JavaLexer(JavaString stripped, JavaString type) implements Lexer {
    Iterator<Lexer> enumerateLexers(JavaString input) {
        return Iterators.from(
                DeclarationLexer.createDeclarationLexer(input, type),
                new InterfaceLexer(input),
                new PackageLexer(input),
                new ImportLexer(input),
                new BlockLexer(input),
                new ClassLexer(input),
                new RecordLexer(input)
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