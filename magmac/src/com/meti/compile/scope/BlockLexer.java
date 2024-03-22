package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.Splitter;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.rule.ExtractRule;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;
import static com.meti.compile.rule.ConjunctionRule.Join;
import static com.meti.compile.rule.Match.Match;
import static com.meti.compile.rule.WhitespaceRule.Padding;
import static com.meti.compile.rule.WhitespaceRule.Whitespace;

public record BlockLexer(String stripped, int indent) implements Lexer {
    public static final com.meti.compile.rule.Rule Rule = Join(
            Match("{"),
            Padding,
            Match("}")
    );

    private Option<Node> lex0() {
        if (!stripped().startsWith("{") || !stripped().endsWith("}")) return None();

        var split = new Splitter(stripped().substring(1, stripped().length() - 1).strip()).split();
        var content = split
                .map(line1 -> new Content(line1, indent + 1))
                .collect(Collectors.toNativeList());

        return Some(new BlockNode(indent(), content));
    }

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption(lex0());
    }
}