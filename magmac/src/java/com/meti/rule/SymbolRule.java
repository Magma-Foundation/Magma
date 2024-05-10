package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.MapNode;
import com.meti.util.Options;

import java.util.Optional;

import static com.meti.node.NodeAttributes.NodeAttributesBuilder;

public record SymbolRule(String key) implements Rule {

    public static Rule Symbol(String parent) {
        return new SymbolRule(parent);
    }

    private boolean isSymbol(String value) {
        if (value.isEmpty()) return false;
        if (!Character.isLetter(value.charAt(0))) return false;

        for (int i = 1; i < value.length(); i++) {
            var c = value.charAt(i);
            if (!Character.isDigit(c) || !Character.isLetter(c)) {
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
        if (!isSymbol(value)) return new ErrorRuleResult("Value is not a symbol.", value);

        var attributes = NodeAttributesBuilder().withString(key, value).complete();
        return new NodeRuleResult(new Tuple<>(attributes, Optional.empty()));
    }
}