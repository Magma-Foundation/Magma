package com.meti.compile;

import com.meti.api.collect.Index;
import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.option.ThrowableOption;
import com.meti.api.result.Result;
import com.meti.api.result.Results;
import com.meti.compile.rule.ContentRule;
import com.meti.compile.rule.Rule;
import com.meti.compile.rule.RuleException;
import com.meti.compile.rule.RuleLexer;

public record RuleLexerFactory(String requiredType, Rule rule) {
    public static List<Lexer> parse(JavaString content) {
        content.split("\n").iter().map(line -> {
            return Results.$Result(() -> {
                var separator = line.firstIndexOfChar('=')
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new RuleException("No assignment present: " + line))
                        .$();

                var name = line.sliceTo(separator).strip();
                var next = separator.next()
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new RuleException("No value present to be assigned: " + line))
                        .$();

                var ruleString = line.sliceFrom(next).strip();
                parseRule(ruleString);

            });
        });
    }

    private static Result<Rule, RuleException> parseRule(JavaString ruleString) {
        return ruleString.firstIndexOfChar('<').filter(Index::isStart)
                .and(ruleString.lastIndexOfChar('>').filter(Index::isEnd))
                .map(obj -> {
                    var name = ruleString.sliceBetween(obj.a().to(obj.b()).$()).strip();
                    return new ContentRule(name);
                })
                .unwrapOrElse();
    }

    public Lexer createDeclarationLexer(JavaString input, JavaString type) {
        return new RuleLexer(input, type, rule(), requiredType());
    }
}