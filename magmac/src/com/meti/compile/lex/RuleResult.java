package com.meti.compile.lex;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.collect.map.Map;

public interface RuleResult {
    Map<String, JavaString> getStrings();

    Map<String, List<JavaString>> getListOfStrings();

    RuleResult add(RuleResult other);
}
