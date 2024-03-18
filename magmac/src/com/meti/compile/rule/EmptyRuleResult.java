package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.Tuple;
import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.java.JavaString;

public class EmptyRuleResult implements RuleResult {
    public static RuleResult Empty = new EmptyRuleResult();

    @Override
    public Option<JavaString> findText(String name) {
        return None.None();
    }

    @Override
    public Stream<Tuple<JavaString, JavaString>> streamTexts() {
        return Streams.empty();
    }

    @Override
    public Option<JavaList<JavaString>> findTextList(String name) {
        return None.None();
    }

    @Override
    public Stream<Tuple<JavaString, JavaList<JavaString>>> streamTextLists() {
        return Streams.empty();
    }
}
