package com.meti.compile.rule;

import com.meti.api.collect.JavaList;
import com.meti.api.collect.JavaMap;
import com.meti.api.collect.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Node;

import java.util.Collections;
import java.util.Map;

public record ValueRule(String name) implements Rule {
    @Override
    public Option<List<Result>> fromString(String input) {
        if (input.isEmpty()) {
            return None.apply();
        } else {
            return Some.apply(JavaList.of(new Result(new JavaMap<>(Map.of(name, input)), new JavaMap<>(Collections.emptyMap()))));
        }
    }

    @Override
    public Option<String> toString(Node node) {
        return node.getString(name);
    }
}