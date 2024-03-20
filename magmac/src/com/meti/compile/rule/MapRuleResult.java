package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.Pair;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

public record MapRuleResult(
        JavaMap<JavaString, JavaList<JavaString>> texts
) implements RuleResult {

    public MapRuleResult() {
        this(new JavaMap<>());
    }


    public static RuleResult merge(RuleResult first, RuleResult... more) {
        return Streams.from(more).foldRightFromInitial(first, (mapRuleResult, ruleResult) -> new MapRuleResult(mapRuleResult.streamTextLists().concat(ruleResult.streamTextLists()).collect(Collectors.toMap(JavaList::addAll))));
    }

    @Override
    public Option<JavaString> findText(String name) {
        return texts.apply(JavaString.from(name)).flatMap(JavaList::first);
    }

    @Override
    public Stream<Pair<JavaString, JavaString>> streamTexts() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Option<JavaList<JavaString>> findTextList(String name) {
        return texts.apply(JavaString.from(name));
    }

    @Override
    public Stream<Pair<JavaString, JavaList<JavaString>>> streamTextLists() {
        return texts.stream();
    }

    @Override
    public boolean isEmpty() {
        return texts.isEmpty();
    }
}
