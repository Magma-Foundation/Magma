package com.meti.rule;

import com.meti.Tuple;
import com.meti.node.Attribute;
import com.meti.node.MapNode;
import com.meti.node.NodeAttributes;
import com.meti.node.StringListAttribute;
import com.meti.util.Options;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record DelimiterRule(String name, String delimiter) implements Rule {
    public static Rule Delimit(String name, String delimiter) {
        return new DelimiterRule(name, delimiter);
    }

    @Override
    public Optional<Tuple<NodeAttributes, Optional<String>>> fromString(String value) {
        var members = value.split(delimiter);
        if (members.length == 0) return Optional.empty();

        var attribute = new StringListAttribute(List.of(members));
        var attributes = Map.of(name, attribute);
        return Optional.of(new Tuple<>(new NodeAttributes(attributes), Optional.empty()));
    }

    @Override
    public Optional<String> toString(MapNode node) {
        return Options.toNative(node.apply(name)).flatMap(Attribute::asListOfStrings).map(list -> String.join(delimiter, list));
    }
}
