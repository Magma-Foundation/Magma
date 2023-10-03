package com.meti.compile.clazz;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.Range;
import com.meti.api.option.Option;
import com.meti.api.option.Options;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;

import static com.meti.api.option.Options.$Option;

public record ClassLexer(JavaString stripped) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            if (!stripped().contains("class ")) {
                return Options.$$();
            }

            var bodyStart = stripped().firstIndexOfChar('{').$();
            var bodyEnd = stripped()
                    .lastIndexOfChar('}').$()
                    .next().$();

            var extendsIndex = stripped().firstIndexOfSlice("extends ");
            var name = extendsIndex.map(extendsIndex1 -> {
                var keys = this.stripped().sliceTo1(extendsIndex1).value().strip();
                var separator = keys.lastIndexOf(' ');
                return keys.substring(separator + 1).strip();
            }).unwrapOrElseGet(() -> {
                var keys = this.stripped().sliceTo1(bodyStart).value().strip();
                var separator = keys.lastIndexOf(' ');
                return keys.substring(separator + 1).strip();
            });

            Range range = bodyStart.to(bodyEnd).$();
            var body = this.stripped().sliceBetween(range).value().strip();
            return MapNode.Builder("class")
                    .withString("name", new JavaString(name))
                    .withNode("body", Content.from(body))
                    .complete();
        });
    }
}