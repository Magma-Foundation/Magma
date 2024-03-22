package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.Pair;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.java.JavaString;

public interface RuleResult {
    Option<JavaString> findText(String name);

    Stream<Pair<JavaString, JavaString>> streamTexts();

    Option<JavaList<JavaString>> findTextList(String name);

    Stream<Pair<JavaString, JavaList<JavaString>>> streamTextLists();

    boolean isEmpty();
}
