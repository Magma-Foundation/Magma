package com.meti.compile.rule;

import com.meti.compile.ResultNode;
import com.meti.compile.collect.JavaList;
import com.meti.compile.collect.List;
import com.meti.compile.option.None;
import com.meti.compile.option.Option;
import com.meti.compile.option.Some;

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