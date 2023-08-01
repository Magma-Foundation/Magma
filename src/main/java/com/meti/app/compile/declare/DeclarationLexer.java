package com.meti.app.compile.declare;

import com.meti.app.compile.*;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.StringAttribute;
import com.meti.core.Option;
import com.meti.core.Options;
import com.meti.core.Result;
import com.meti.java.JavaList;
import com.meti.java.JavaMap;
import com.meti.java.NonEmptyJavaList;
import com.meti.java.String_;

import static com.meti.core.Results.$Result;
import static com.meti.java.JavaString.fromSlice;

public record DeclarationLexer(String_ line) implements Lexer {

    @Override
    public Option<Result<Node, CompileException>> lex() {
        return this.line().lastIndexOfChar(' ').flatMap(index -> Options.$Option(() -> {
            var args = this.line().sliceTo(index).strip();
            var name = this.line().sliceFrom(index.nextExclusive().$()).strip();
            var isValid = name.iter().allMatch(Character::isLetter);
            if (!isValid) return Options.$$();

            var list = args.split(" ").collect(JavaList.intoList())
                    .into(NonEmptyJavaList::from).$();

            var type = list.last();

            return $Result(CompileException.class, () -> {
                var map = new Resolver(type).resolve().$();
                return new MapNode(fromSlice("declaration"), JavaMap.<String_, Attribute>empty()
                        .insert(fromSlice("name"), new StringAttribute(name))
                        .insert(fromSlice("type"), new NodeAttribute(fromSlice("any"), new Content(map))));
            });
        }));
    }
}