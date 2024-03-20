package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.Range;
import com.meti.collect.Tuple;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

import java.util.function.Function;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Options.$Option;
import static com.meti.collect.option.Some.Some;

public class ListRule implements Rule {
    private final Rule value;
    private final Rule delimiter;

    private ListRule(Rule value, Rule delimiter) {
        this.value = value;
        this.delimiter = delimiter;
    }

    public static ListRule List(Rule value, Rule delimiter) {
        return new ListRule(value, delimiter);
    }

    public static Option<Range> findFirstInstance(JavaString value, Rule rule) {
        /*
        TODO: figure out a more intuitive way to express this
         */
        var totalLength = value.length();
        for (int sliceLength = totalLength; sliceLength > 0; sliceLength--) {
            for (int offset = 0; offset < (totalLength - sliceLength); offset++) {
                var rangeOption = value.indexAt(offset)
                        .and(value.indexAt(sliceLength + offset))
                        .flatMap(tuple -> tuple.a().to(tuple.b()))
                        .flatMap(range -> rule.apply(value.sliceBetween(range)).isPresent()
                                ? Some(range)
                                : None());

                if (rangeOption.isPresent()) return rangeOption;
            }
        }

        return None();
    }

    public static JavaList<Range> findInstances(JavaString value, Rule rule) {
        var list = new JavaList<Range>();
        var remaining = value;
        while (true) {
            JavaList<Range> finalList = list;

            var resolvedTuple = findFirstInstance(remaining, rule)
                    .flatMap(element -> $Option(() -> {
                        int offset = value.length() - element.length();
                        var nextList = finalList.add(element.offsetRight(offset).$());
                        var nextRemaining = value.sliceFrom(element.endIndex());
                        return new Tuple<>(nextList, nextRemaining);
                    }))
                    .toResolvedTuple(new Tuple<>(list, remaining));

            if (resolvedTuple.a()) {
                list = resolvedTuple.b().a();
                remaining = resolvedTuple.b().b();
            } else {
                break;
            }
        }

        return list;
    }

    @Override
    public Option<RuleResult> apply(JavaString input) {
        return input.exclude(findInstances(input, delimiter))
                .map(input::sliceBetween)
                .map(value::apply)
                .flatMap(Streams::fromOption)
                .foldRight(Function.identity(), MapRuleResult::merge);
    }
}
