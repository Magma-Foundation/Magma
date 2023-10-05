package com.meti.compile.rule;

import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.api.option.Options;

public record ConjunctionRuleLexer(JavaString content) implements RuleLexer {
    @Override
    public Iterator<Rule> lex() {
        return content.indicesOfChar(' ').<Option<Rule>>map(separator -> Options.$Option(() -> {
            var left = content.sliceTo(separator).strip();
            var right = content.sliceFrom(separator.next().$()).strip();
            return new ConjunctionRule(new UnrealizedRule(left), new UnrealizedRule(right));
        })).flatMap(Iterators::fromOption);
    }
}
