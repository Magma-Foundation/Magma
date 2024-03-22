package com.meti.compile.rule;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.java.JavaString;

import java.util.Objects;

public final class NodeRule implements Rule {
    private final JavaString name;
    private final Rule rule;

    private NodeRule(JavaString name, Rule rule) {
        this.name = name;
        this.rule = rule;
    }

    public static NodeRule Node(JavaString name, Rule rule) {
        return new NodeRule(name, rule);
    }

    @Override
    public Option<RuleResult> apply(JavaString input) {
        if (rule.apply(input).isPresent()) {
            return Some.Some(new MapRuleResult(new JavaMap<JavaString, JavaList<JavaString>>()
                    .put(name, JavaList.from(input))));
        } else {
            return None.None();
        }
    }

    public JavaString name() {
        return name;
    }

    public Rule rule() {
        return rule;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (NodeRule) obj;
        return Objects.equals(this.name, that.name) &&
               Objects.equals(this.rule, that.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rule);
    }

    @Override
    public String toString() {
        return "NodeRule[" +
               "name=" + name + ", " +
               "rule=" + rule + ']';
    }

}
