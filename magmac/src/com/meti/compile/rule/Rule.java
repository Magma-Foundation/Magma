package com.meti.compile.rule;

import com.meti.compile.collect.List;
import com.meti.compile.option.Option;

import java.util.HashMap;
import java.util.Map;

public interface Rule {
    Option<List<Result>> evaluate(String input);

    record Result(Map<String, String> values) {
        public Result add(Result other) {
            var copy = new HashMap<>(values);
            copy.putAll(other.values);
            return new Result(copy);
        }
    }
}
