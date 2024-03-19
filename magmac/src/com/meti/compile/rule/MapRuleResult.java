package com.meti.compile.rule;

import com.meti.collect.Index;
import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.Tuple;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

public record MapRuleResult(
        Index length, JavaMap<JavaString, JavaList<JavaString>> texts
) implements RuleResult {

    public MapRuleResult(Index length) {
        this(length, new JavaMap<>());
    }


    public static RuleResult merge(RuleResult first, RuleResult... more) {
        var length = Streams.from(first).concat(Streams.from(more))
                .map(RuleResult::length)
                .collect(Collectors.sum())
                .orElse(first.length());

        return Streams.from(first).foldRight(new MapRuleResult(length), (mapRuleResult, ruleResult) -> new MapRuleResult(
                mapRuleResult.length.next(ruleResult.length().value()).orElse(mapRuleResult.length),
                mapRuleResult.streamTextLists().concat(ruleResult.streamTextLists()).collect(Collectors.toMap(JavaList::addAll)
        )));
    }

    @Override
    public Option<JavaString> findText(String name) {
        return texts.apply(new JavaString(name)).flatMap(JavaList::first);
    }

    @Override
    public Stream<Tuple<JavaString, JavaString>> streamTexts() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Option<JavaList<JavaString>> findTextList(String name) {
        return texts.apply(new JavaString(name));
    }

    @Override
    public Stream<Tuple<JavaString, JavaList<JavaString>>> streamTextLists() {
        return texts.stream();
    }

    @Override
    public Index length() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return texts.isEmpty();
    }
}
