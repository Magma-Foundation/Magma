package com.meti.compile.rule;

import static com.meti.compile.rule.ExtractSymbolRule.Symbol;
import static com.meti.compile.rule.ListRule.List;
import static com.meti.compile.rule.Match.Match;

public class Rules {
    public static Rule Optional(Rule value) {
        /*
        DO NOT CHANGE THIS ORDER, OTHERWISE THE ORDERING OF EVALUATION CHANGES SIGNIFICANTLY

        Conjunctions start with empty strings "",
        and if an Optional is within a Conjunction, then "" will always be passed first,
        thus `value` here would never be parsed. `value` must take precedence.
         */
        return DisjunctionRule.Or(value, EmptyRule.Empty);
    }

    public static Rule SymbolList(String name, String delimiter) {
        return SymbolList(name, Match(delimiter));
    }

    public static ListRule SymbolList(String name, Rule text) {
        return List(Symbol(name), text);
    }
}
