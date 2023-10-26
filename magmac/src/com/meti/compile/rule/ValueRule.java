package com.meti.compile.rule;

import com.meti.compile.option.None;
import com.meti.compile.option.Option;
import com.meti.compile.option.Some;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public record ValueRule(String name) implements Rule {
    @Override
    public Option<List<Result>> evaluate(String input) {
        if (input.isEmpty()) {
            return None.apply();
        } else {
            return Some.apply(Collections.singletonList(new Result(Map.of(name, input))));
        }
    }
}