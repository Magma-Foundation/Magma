package com.meti.compile;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.*;

public record TypeCompiler(String type) {
    public Option<String> compile() {
        if(type.equals("new")) return None();

        if (type.equals("void")) {
            return Some("Void");
        } else {
            var start = type().indexOf('<');
            var end = type().lastIndexOf('>');
            if (start != -1 && end != -1 && start < end) {
                var name = type().substring(0, start).strip();
                var subType = type().substring(start + 1, end).strip();
                return Some(name + "[" + new TypeCompiler(subType).compile() + "]");
            } else {
                return Some(type());
            }
        }
    }
}