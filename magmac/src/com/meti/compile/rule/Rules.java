package com.meti.compile.rule;

import static com.meti.compile.rule.ExtractSymbolRule.Symbol;
import static com.meti.compile.rule.ListRule.List;
import static com.meti.compile.rule.TextRule.Text;

public class Rules {
    public static Rule Optional(Rule value) {
        return DisjunctionRule.Or(value, EmptyRule.Empty);
    }

    public static Rule SymbolList(String name, String delimiter) {
        return SymbolList(name, Text(delimiter));
    }

    public static ListRule SymbolList(String name, Rule text) {
        return List(Symbol(name), text);
    }
}
