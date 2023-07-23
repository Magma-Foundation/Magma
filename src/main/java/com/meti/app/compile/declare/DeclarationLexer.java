package com.meti.app.compile.declare;

import com.meti.app.compile.Compiler;
import com.meti.app.compile.*;
import com.meti.core.Option;
import com.meti.core.Options;
import com.meti.core.Result;
import com.meti.java.JavaList;
import com.meti.java.NonEmptyJavaList;
import com.meti.java.String_;

import static com.meti.core.Results.$Result;

public record DeclarationLexer(String_ line) implements Lexer {

    @Override
    public Option<Result<Node, CompileException>> lex() {
        return this.line().lastIndexOfChar(' ').flatMap(index -> Options.$Option(() -> {
            var args = this.line().sliceTo(index).strip();
            var name = this.line().sliceFrom(index.nextExclusive().$()).strip();
            var isValid = name.iter().allMatch(Character::isLetter);
            if (!isValid) return Options.$$();

            var list = args.split(" ").collect(JavaList.asList())
                    .into(NonEmptyJavaList::from).$();

            var type = list.last();

            return $Result(CompileException.class, () -> {
                var map = Compiler.resolveType(type).$();
                return new Declaration(name, new Content(map));
            });
        }));
    }
}