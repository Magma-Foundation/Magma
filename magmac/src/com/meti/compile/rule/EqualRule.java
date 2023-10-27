package com.meti.compile.rule;

import com.meti.compile.collect.JavaList;
import com.meti.compile.collect.List;
import com.meti.compile.option.None;
import com.meti.compile.option.Option;
import com.meti.compile.option.Some;

import java.util.Collections;

public record EqualRule(String value) implements Rule {
    @Override
    public Option<List<Result>> evaluate(String input) {
        if(value.equals(input)) {
            return Some.apply(JavaList.of(new Result(Collections.emptyMap())));
        } else {
            return None.apply();
        }
    }
}
