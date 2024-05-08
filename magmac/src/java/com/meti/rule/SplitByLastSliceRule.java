package com.meti.rule;

import com.meti.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record SplitByLastSliceRule(ExtractRule leftRule, String slice, ExtractRule rightRule) implements Rule {
    public static SplitByLastSliceRule Last(ExtractRule parent, String slice, ExtractRule child) {
        return new SplitByLastSliceRule(parent, slice, child);
    }

    private Optional<Map<String, String>> apply1(String segments) {
        var separator = segments.lastIndexOf(slice());
        if (separator == -1) return Optional.empty();

        var parent = segments.substring(0, separator);
        var firstMap = this.leftRule().apply(parent).map(tuple1 -> tuple1.a());
        if (firstMap.isEmpty()) return Optional.empty();

        var child = segments.substring(separator + slice().length());
        var secondMap = this.rightRule().apply(child).map(tuple -> tuple.a());
        if (secondMap.isEmpty()) return Optional.empty();

        var attributes = new HashMap<String, String>();
        attributes.putAll(firstMap.orElseThrow());
        attributes.putAll(secondMap.orElseThrow());
        return Optional.of(attributes);
    }

    @Override
    public Optional<Tuple<Map<String, String>, Optional<String>>> apply(String value) {
        return apply1(value).map(map -> new Tuple<>(map, Optional.empty()));
    }
}