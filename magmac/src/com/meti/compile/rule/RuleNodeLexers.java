package com.meti.compile.rule;

import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.ThrowableOption;
import com.meti.api.result.Ok;
import com.meti.api.result.Result;
import com.meti.compile.RuleLexerFactory;

import static com.meti.api.result.Results.$Result;

public class RuleNodeLexers {
    public static Result<RuleLexerFactory, RuleException> parse(JavaString content) {
        return $Result(() -> {
            var separator = content.firstIndexOfChar('=')
                    .into(ThrowableOption::new)
                    .unwrapOrThrow(new RuleException("No assignment present."))
                    .$();

            var afterSeparator = separator.next()
                    .into(ThrowableOption::new)
                    .unwrapOrThrow(new RuleException("No values present after assignment."))
                    .$();

            var name = content.sliceTo(separator).strip();
            var value = content.sliceFrom(afterSeparator).strip();
            var rule = parseValue(value).$();
            return new RuleLexerFactory(name.value(), rule);
        });
    }

    private static Result<Rule, RuleException> parseValue(JavaString value) {
        var collect = lexRuleString(value)
                .map(RuleNodeLexers::parseValueTree)
                .collect(ImmutableLists.into());
        var head = collect
                .iter()
                .filter(value1 -> value1.isOk())
                .head();
        return head
                .into(ThrowableOption::new)
                .unwrapOrThrow(new RuleException("Invalid syntax: '" + value + "'."))
                .flatMapValue(value1 -> value1);
    }

    private static Result<Rule, RuleException> parseValueTree(Rule potentialRule) {
        return $Result(() -> {
            var withLeft = potentialRule.left().map(leftRule -> $Result(() -> {
                var leftValue = leftRule.asString()
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new RuleException("A left value was present, but is not a string."))
                        .$();
                var parsedLeft = parseValue(leftValue).$();
                return potentialRule.withLeft(parsedLeft);
            })).unwrapOrElse(Ok.apply(potentialRule)).$();

            return withLeft.right().map(rightRule -> $Result(() -> {
                var rightValue = rightRule.asString()
                        .into(ThrowableOption::new)
                        .unwrapOrThrow(new RuleException("A right value was present, but is not a string."))
                        .$();
                var parsedRight = parseValue(rightValue).$();
                return withLeft.withRight(parsedRight);
            })).unwrapOrElse(Ok.apply(withLeft)).$();
        });
    }

    private static Iterator<Rule> lexRuleString(JavaString value) {
        return Iterators.from(
                        new ConjunctionRuleLexer(value),
                        new ContentRuleLexer(value),
                        new TextRuleLexer(value),
                        new ValueRuleLexer(value)
                )
                .flatMap(RuleLexer::lex);
    }

    public static RuleNodeLexer createDeclarationLexer(String line, JavaString input, JavaString type) {
        try {
            var parse = parse(JavaString.apply(line));
            return parse.$().create(input, type);
        } catch (RuleException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
