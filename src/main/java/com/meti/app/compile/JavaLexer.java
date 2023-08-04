package com.meti.app.compile;

import com.meti.app.compile.block.BlockLexer;
import com.meti.app.compile.clazz.ClassLexer;
import com.meti.app.compile.declare.DeclarationLexer;
import com.meti.app.compile.function.MethodLexer;
import com.meti.app.compile.imports.ImportLexer;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.iterate.Iterators;
import com.meti.iterate.ResultIterator;
import com.meti.java.JavaList;
import com.meti.java.List;
import com.meti.java.String_;

public record JavaLexer(String_ line) implements Lexer {
    @Override
    public Option<Result<Node, CompileException>> lex() {
        return listLexers()
                .iter()
                .map(Lexer::lex)
                .flatMap(Iterators::fromOption)
                .into(ResultIterator::new)
                .head();
    }

    private List<Lexer> listLexers() {
        return JavaList.of(
                new ClassLexer(line),
                new BlockLexer(line),
                new MethodLexer(line),
                new DeclarationLexer(line),
                new ImportLexer(line));
    }
}