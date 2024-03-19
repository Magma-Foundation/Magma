package com.meti.compile.rule;

import com.meti.collect.JavaMap;
import com.meti.collect.Tuple;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.java.JavaString;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public class ExtractSymbolRule implements Rule {
    private final JavaString name;

    private ExtractSymbolRule(String name) {
        this.name = new JavaString(name);
    }

    public static ExtractSymbolRule Symbol(String name) {
        return new ExtractSymbolRule(name);
    }

    @Override
    public Option<RuleResult> apply(JavaString input) {
        return isSymbol(input)
                ? Some(new MapRuleResult(new JavaMap<JavaString, JavaString>().put(name, input), new JavaMap<>()))
                : None();
    }

    private boolean isSymbol(JavaString input) {
        return input.popFirst().map(this::validatePair).orElse(false);
    }

    private boolean validatePair(Tuple<Character, JavaString> tuple) {
        return Character.isAlphabetic(tuple.a()) && tuple.b()
                .stream()
                .map(this::isAlphaNumeric)
                .collect(Collectors.allTrue());
    }

    private boolean isAlphaNumeric(char value) {
        return Character.isAlphabetic(value) && Character.isDigit(value);
    }
}
