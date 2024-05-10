package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;
import com.meti.util.Options;

import java.util.Map;
import java.util.Optional;

public record SymbolRule(String key) implements Rule {

    public static Rule Symbol(String parent) {
        return new SymbolRule(parent);
    }

    private Optional<Tuple<NodeAttributes, Optional<String>>> fromString1(String value) {
        if(!isSymbol(value)) return Optional.empty();

        return Optional.of(Map.of(key, value))
                .<Tuple<Map<String, String>, Optional<String>>>map(map -> new Tuple<>(map, Optional.empty()))
                .map(tuple -> tuple.mapLeft(NodeAttributes::fromStrings));
    }

    private boolean isSymbol(String value) {
        if(value.isEmpty()) return false;
        if(!Character.isLetter(value.charAt(0))) return false;

        for (int i = 1; i < value.length(); i++) {
            var c = value.charAt(i);
            if(!Character.isDigit(c) || !Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return Options.toNative(node.apply(key)).flatMap(Attribute::asString);
    }

    @Override
    public RuleResult fromString(String value) {
        return fromString1(value).<RuleResult>map(NodeRuleResult::new).orElseGet(() -> new ErrorRuleResult("", ""));
    }
}