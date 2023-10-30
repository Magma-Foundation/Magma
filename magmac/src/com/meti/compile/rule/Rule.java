package com.meti.compile.rule;

import com.meti.api.collect.List;
import com.meti.api.option.Option;
import com.meti.compile.Node;

import java.util.HashMap;
import java.util.Map;

public interface Rule {
    Option<List<Result>> fromString(String input);

    Option<String> toString(Node node);

    record Result(Map<String, String> text, Map<String, String> nodes) {
        public Result add(Result other) {
            var textCopy = new HashMap<>(text);
            textCopy.putAll(other.text);

            var nodesCopy = new HashMap<>(nodes);
            nodesCopy.putAll(other.nodes);
            return new Result(textCopy, nodesCopy);
        }
    }
}
