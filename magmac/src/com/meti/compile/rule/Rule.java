package com.meti.compile.rule;

import com.meti.api.collect.List;
import com.meti.api.option.Option;
import com.meti.compile.Node;

public interface Rule {
    Option<List<Result>> fromString(String input);

    Option<String> toString(Node node);

    record Result(com.meti.api.collect.Map<String, String> text, com.meti.api.collect.Map<String, String> nodes) {
        public Result add(Result other) {
            var textCopy = text.putAll(other.text);
            var nodesCopy = nodes.putAll(other.nodes);
            return new Result(textCopy, nodesCopy);
        }
    }
}
