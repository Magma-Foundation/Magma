package com.meti.app.compile.declare;

import com.meti.app.compile.Compiler;
import com.meti.app.compile.Lexer;
import com.meti.app.compile.Node;
import com.meti.core.Option;
import com.meti.core.Options;
import com.meti.java.JavaList;
import com.meti.java.NonEmptyJavaList;
import com.meti.java.String_;

import static com.meti.core.Options.$Option;

public record DeclarationLexer(String_ line) implements Lexer {
    @Override
    public Option<Node> lex() {
        return line().lastIndexOfChar(' ').flatMap(index -> $Option(() -> {
            var args = line().sliceTo(index).strip();
            var name = line().sliceFrom(index.nextExclusive().$()).strip();
            var isValid = name.iter().allMatch(Character::isLetter);
            if (!isValid) return Options.$$();

            var list = args.split(" ").collect(JavaList.asList())
                    .into(NonEmptyJavaList::from).$();

            var type = list.last();
            var map = Compiler.resolveType(type);

            return new Declaration(name, map);
        }));
    }
}