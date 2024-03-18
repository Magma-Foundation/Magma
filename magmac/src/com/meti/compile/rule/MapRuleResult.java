package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.Tuple;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

import java.util.function.BiFunction;

public record MapRuleResult(JavaMap<JavaString, JavaString> text,
                            JavaMap<JavaString, JavaList<JavaString>> texts) implements RuleResult {
    public MapRuleResult() {
        this(new JavaMap<>(), new JavaMap<>());
    }


    public static RuleResult merge(RuleResult... results) {
        return Streams.from(results).foldRight(new MapRuleResult(), (mapRuleResult, ruleResult) -> new MapRuleResult(
                mapRuleResult.streamTexts().concat(ruleResult.streamTexts()).collect(Collectors.toOverridingMap()),
                mapRuleResult.streamTextLists().concat(ruleResult.streamTextLists()).collect(Collectors.toMap(JavaList::addAll))
        ));
    }

    @Override
    public Option<JavaString> findText(String name) {
        return text.apply(new JavaString(name));
    }

    @Override
    public Stream<Tuple<JavaString, JavaString>> streamTexts() {
        return text.stream();
    }

    @Override
    public Option<JavaList<JavaString>> findTextList(String name) {
        return texts.apply(new JavaString(name));
    }

    @Override
    public Stream<Tuple<JavaString, JavaList<JavaString>>> streamTextLists() {
        return texts.stream();
    }
}
