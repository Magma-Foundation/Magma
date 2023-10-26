package com.meti.compile.rule;

import com.meti.compile.iterator.Iterators;
import com.meti.compile.option.None;
import com.meti.compile.option.Option;
import com.meti.compile.option.Some;

import java.util.Collections;
import java.util.List;

public record EqualRule(String value) implements Rule {
    @Override
    public Option<List<Result>> evaluate(String input) {
        if(value.equals(input)) {
            return Some.apply(Collections.singletonList(new Result(Collections.emptyMap())));
        } else {
            return None.apply();
        }
    }
}
