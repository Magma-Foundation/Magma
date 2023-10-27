package com.meti.compile.rule;

import com.meti.api.collect.List;
import com.meti.api.option.Option;
import com.meti.compile.ResultNode;

import java.util.HashMap;
import java.util.Map;

public interface Rule {
    Option<List<Result>> fromString(String input);

    Option<String> toString(ResultNode node);

    record Result(Map<String, String> values) {
        public Result add(Result other) {
            var copy = new HashMap<>(values);
            copy.putAll(other.values);
            return new Result(copy);
        }
    }
}
