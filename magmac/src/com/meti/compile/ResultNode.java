package com.meti.compile;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.rule.Rule;

public record ResultNode(String type, Rule.Result evaluated) implements Node {
    @Override
    public boolean is(String type) {
        return this.type.equals(type);
    }

    @Override
    public Option<String> getString(String name) {
        var values = evaluated().text();
        if (values.containsKey(name)) {
            return Some.apply(values.get(name));
        } else {
            return None.apply();
        }
    }
}