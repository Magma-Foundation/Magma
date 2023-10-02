package com.meti.compile;

import com.meti.api.collect.Index;
import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.api.option.Options;
import com.meti.compile.block.BlockLexer;
import com.meti.compile.clazz.ClassLexer;
import com.meti.compile.function.RecordLexer;
import com.meti.compile.imports.ImportLexer;
import com.meti.compile.package_.PackageLexer;

public record JavaLexer(JavaString stripped) implements Lexer {
    static Iterator<Lexer> enumerateLexers(JavaString stripped) {
        return Iterators.from(
                new Lexer() {
                    @Override
                    public Option<Node> lex() {
                        return Options.$Option(() -> {
                            var nameStart = stripped.firstIndexOfChar('<').$();
                            var keywordEnd = stripped.firstIndexOfSlice("interface ").$()
                                    .nextBy("interface ".length()).$();
                            var name = stripped.sliceBetween(keywordEnd.to(nameStart).$()).strip();
                            return new MapNode(new JavaString("interface"));
                        });
                    }
                },
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