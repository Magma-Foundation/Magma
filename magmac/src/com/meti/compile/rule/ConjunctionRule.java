package com.meti.compile.rule;

import com.meti.compile.collect.JavaList;
import com.meti.compile.collect.List;
import com.meti.compile.iterator.Iterators;
import com.meti.compile.option.None;
import com.meti.compile.option.Option;
import com.meti.compile.option.Some;

public record ConjunctionRule(Rule left, Rule right) implements Rule {
    public static Rule join(Rule first, Rule... more) {
        var current = first;
        for (Rule rule : more) {
            current = new ConjunctionRule(current, rule);
        }
        return current;
    }

    @Override
    public Option<List<Result>> evaluate(String input) {
        var results = Iterators.to(input.length()).flatMap(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index);

            var leftResults = this.left.evaluate(left);
            var rightResults = this.right.evaluate(right);
            return leftResults.and(rightResults).map(tuple -> {
                var leftArray = tuple.a();
                var rightArray = tuple.b();
                return leftArray.iter().flatMap(leftElement -> rightArray.iter().map(leftElement::add));
            }).unwrapOrElse(Iterators.empty());
        }).collect(JavaList.collect());

        if (results.isEmpty()) {
            return None.apply();
        } else {
            return Some.apply(results);
        }
    }
}
