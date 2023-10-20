package com.meti.compile.function;

import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.compile.NodeLexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;

import static com.meti.api.option.Options.$Option;

public record RecordLexer(JavaString stripped) implements NodeLexer {
    private Option<Node> lex1() {
        return $Option(() -> {
            var nameStart = stripped().firstIndexOfSlice("record ").$()
                    .nextBy("record ".length())
                    .$();

            var anyTypeStart = stripped.firstIndexOfChar('<');

            var paramStart = stripped().firstIndexOfChar('(').$();
            var paramEnd = stripped.firstIndexOfChar(')').$();

            var bodyStart = stripped().firstIndexOfChar('{').$();
            var bodyEnd = stripped().lastIndexOfChar('}').$()
                    .next()
                    .$();

            var extent = anyTypeStart
                    .map(paramStart::min)
                    .unwrapOrElse(paramStart);

            var range1 = nameStart.to(extent).$();
            var name = this.stripped().sliceBetween(range1);
            var range = bodyStart.to(bodyEnd).$();
            var bodySlice = this.stripped().sliceBetween(range);

            var parameters = ImmutableLists.of(new Content(stripped.sliceBetween(paramStart.next().$().to(paramEnd).$()), JavaString.apply("definition")));

            return MapNode.Builder(JavaString.apply("record"))
                    .withString(JavaString.apply("name"), name)
                    .withNodeList("parameters", parameters)
                    .withNode("body", new Content(bodySlice, JavaString.Empty))
                    .complete();
        });
    }

    @Override
    public Iterator<Node> lex() {
        return Iterators.fromOption(lex1());
    }
}