package com.meti.compile.rule;

import com.meti.collect.Index;
import com.meti.collect.JavaList;
import com.meti.collect.Tuple;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.java.JavaString;

public interface RuleResult {
    Option<JavaString> findText(String name);

    Stream<Tuple<JavaString, JavaString>> streamTexts();

    Option<JavaList<JavaString>> findTextList(String name);

    Stream<Tuple<JavaString, JavaList<JavaString>>> streamTextLists();

    Index length();

    boolean isEmpty();
}
