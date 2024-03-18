package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.TypeCompiler;
import com.meti.compile.node.Content;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;
import com.meti.compile.rule.Rule;
import com.meti.compile.rule.RuleResult;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$Option;
import static com.meti.compile.rule.ConjunctionRule.Join;
import static com.meti.compile.rule.ExtractRule.Extract;
import static com.meti.compile.rule.Rules.Optional;
import static com.meti.compile.rule.Rules.TextList;
import static com.meti.compile.rule.TextRule.Text;

public record ClassLexer(JavaString stripped) implements Lexer {

    private static Option<Node> createToken(RuleResult result) {
        return $Option(() -> {
            var flags = result.findTextList("flags").$();
            var name = result.findText("name").$();

            var content = result.findText("content").$();
            var contentOutput = new Content(content, 0);

            var builder = MapNode.Builder(new JavaString("class"))
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

    private static Rule createRule() {
        return Join(
                Optional(Join(TextList("flags", " "), Text(" "))),
                Text("class "),
                Optional(Join(
                        Text("extends "),
                        Extract("extends"))),
                Text("content"));
    }

    @Override
    public Stream<Node> lex() {
        return createRule()
                .apply(stripped)
                .map(ClassLexer::createToken)
                .flatMap(Streams::fromOption);
    }
}