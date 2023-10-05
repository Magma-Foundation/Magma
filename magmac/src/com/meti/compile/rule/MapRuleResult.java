package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import com.meti.api.collect.List;
import com.meti.api.collect.map.Map;

public record MapRuleResult(
        Map<String, JavaString> strings,
        Map<String, JavaString> content,
        Map<String, List<JavaString>> stringLists
) implements RuleResult {
    @Override
    public Map<String, JavaString> getText() {
        return strings;
    }

    @Override
    public Map<String, JavaString> getContent() {
        return content;
    }

    @Override
    public Map<String, List<JavaString>> getListOfStrings() {
        return stringLists;
    }

    @Override
    public RuleResult add(RuleResult other) {
        return new MapRuleResult(
                strings.putAll(other.getText()),
                content.putAll(other.getContent()),
                stringLists.putAll(other.getListOfStrings())
        );
    }
}
