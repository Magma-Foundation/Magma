package com.meti.compile.rule;

import static com.meti.compile.rule.ExtractRule.Extract;
import static com.meti.compile.rule.TextRule.Text;

public class Rules {
    public static Rule Optional(Rule value) {
        return DisjunctionRule.Or(value, EmptyRule.Empty);
    }

    public static Rule TextList(String name, String delimiter) {
        return new ListRule(Extract(name), Text(delimiter));
    }
}
