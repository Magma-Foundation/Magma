package com.meti.compile;

import com.meti.compile.option.None;
import com.meti.compile.option.Option;
import com.meti.compile.option.Some;
import com.meti.compile.rule.Rule;

public record ResultNode(Rule.Result evaluated, String type) {
    public Option<String> getString(String name) {
        var values = evaluated().values();
        if (values.containsKey(name)) {
            return Some.apply(values.get(name));
        } else {
            return None.apply();
        }
    }
}