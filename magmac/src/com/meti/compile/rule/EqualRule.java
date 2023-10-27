package com.meti.compile.rule;

import com.meti.api.collect.JavaList;
import com.meti.api.collect.List;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.ResultNode;

import java.util.Collections;

public record EqualRule(String value) implements Rule {
    @Override
    public Option<List<Result>> fromString(String input) {
        if(value.equals(input)) {
            return Some.apply(JavaList.of(new Result(Collections.emptyMap())));
        } else {
            return None.apply();
        }
    }

    @Override
    public Option<String> toString(ResultNode node) {
        return Some.apply(value);
    }
}
