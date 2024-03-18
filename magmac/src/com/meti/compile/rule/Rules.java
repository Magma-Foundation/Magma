package com.meti.compile.rule;

import static com.meti.compile.rule.ElementRule.Element;
import static com.meti.compile.rule.ListRule.List;
import static com.meti.compile.rule.TextRule.Text;

public class Rules {
    public static Rule Optional(Rule value) {
        return DisjunctionRule.Or(value, EmptyRule.Empty);
    }

    public static Rule TextList(String name, String delimiter) {
        return TextList(name, Text(delimiter));
    }

    public static ListRule TextList(String name, Rule text) {
        return List(Element(name), text);
    }
}
