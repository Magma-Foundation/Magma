package com.meti.compile.rule;

import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Options;
import org.jetbrains.annotations.NotNull;

public abstract class EncapsulatedRuleLexer implements RuleLexer {
    protected final JavaString content;
    protected final char left;
    protected final char right;

    public EncapsulatedRuleLexer(JavaString content, char left, char right) {
        this.content = content;
        this.left = left;
        this.right = right;
    }

    @Override
    public Iterator<Rule> lex() {
        return Iterators.fromOption(Options.$Option(() -> {
            var start = content
                    .firstIndexOfChar(left).$()
                    .next().$();

            var end = content.lastIndexOfChar(right).$();
            var value = content.sliceBetween(start.to(end).$());
            return complete(value);
        }));
    }

    @NotNull
    protected abstract Rule complete(JavaString value);
}
