package com.meti.compile.rule;

import com.meti.api.collect.JavaList;
import com.meti.api.collect.List;
import com.meti.api.iterator.Iterators;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.ResultNode;

public record ConjunctionRule(Rule left, Rule right) implements Rule {
    public static Rule join(Rule first, Rule... more) {
        var current = first;
        for (Rule rule : more) {
            current = new ConjunctionRule(current, rule);
        }
        return current;
    }

    @Override
    public Option<List<Result>> fromString(String input) {
        var results = Iterators.to(input.length()).flatMap(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index);

            var leftResults = this.left.fromString(left);
            var rightResults = this.right.fromString(right);
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

    @Override
    public Option<String> toString(ResultNode node) {
        return left.toString(node).and(right.toString(node)).map(tuple -> tuple.a() + tuple.b());
    }
}
