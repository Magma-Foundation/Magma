package com.meti.compile.rule;

import com.meti.api.collect.JavaList;
import com.meti.api.collect.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.ResultNode;

import java.util.Map;

public record ValueRule(String name) implements Rule {
    @Override
    public Option<List<Result>> fromString(String input) {
        if (input.isEmpty()) {
            return None.apply();
        } else {
            return Some.apply(JavaList.of(new Result(Map.of(name, input))));
        }
    }

    @Override
    public Option<String> toString(ResultNode node) {
        return node.getString(name);
    }
}