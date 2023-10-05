package com.meti.compile.lex;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.collect.map.Map;

public interface RuleResult {
    Map<String, JavaString> getText();

    Map<String, JavaString> getContent();

    Map<String, List<JavaString>> getListOfStrings();

    RuleResult add(RuleResult other);
}
