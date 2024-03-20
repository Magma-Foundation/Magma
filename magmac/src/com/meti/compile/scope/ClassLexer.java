package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.TypeCompiler;
import com.meti.compile.node.Content;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;
import com.meti.compile.rule.*;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$Option;
import static com.meti.compile.rule.ConjunctionRule.Join;
import static com.meti.compile.rule.DisjunctionRule.Or;
import static com.meti.compile.rule.ExtractRule.Extract;
import static com.meti.compile.rule.ExtractSymbolRule.Symbol;
import static com.meti.compile.rule.Rules.Optional;
import static com.meti.compile.rule.TextRule.Text;
import static com.meti.compile.rule.WhitespaceRule.Padding;
import static com.meti.compile.rule.WhitespaceRule.Whitespace;

public record ClassLexer(JavaString stripped) implements Lexer {

    public static final Rule FLAG_RULE = Rules.SymbolList("flags", Whitespace);
    public static final Rule EXTENDS_RULE = Join(
            Text("extends"),
            Whitespace,
            Symbol("superclass"));

    public static final Rule PREFIX = Or(Join(FLAG_RULE, Whitespace), Padding);
    public static final Rule RULE = Join(
            Symbol("name"),
            Whitespace,
            Extract("content")
    );

    /* public static final Rule RULE = Join(
            PREFIX,
            Text("class"),
            Whitespace,
            Extract("name"),
            Whitespace,
            Optional(EXTENDS_RULE),
            Whitespace,
            Extract("content"),
            Padding);*/

    private static Option<Node> createToken(RuleResult result) {
        return $Option(() -> {
            var flags = result.findTextList("flags").$();
            var name = result.findText("name").$();

            var content = result.findText("content").$();
            var contentOutput = new Content(content, 0);

            var builder = MapNode.Builder(JavaString.from("class"))
                    .withListOfStrings("flags", flags)
                    .withString("name", name)
                    .withNode("value", contentOutput);

            var withSuperClass = result.findText("extends")
                    .flatMap(superClassString -> new TypeCompiler(superClassString).compile())
                    .map(superClassName -> builder.withString("extends", superClassName))
                    .orElse(builder);

            return withSuperClass.complete();
        });
    }

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption(RULE.apply(stripped)
                .flatMap(ClassLexer::createToken));
    }
}