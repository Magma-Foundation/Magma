package com.meti.compile;

import com.meti.collect.option.Option;
import com.meti.java.JavaString;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.*;

public record TypeCompiler(String type) {
    public TypeCompiler(JavaString value) {
        this(value.inner());
    }

    public Option<JavaString> compile() {
        if(type.equals("new")) return None();

        if (type.equals("void")) {
            return Some(JavaString.from("Void"));
        } else {
            var start = type().indexOf('<');
            var end = type().lastIndexOf('>');
            if (start != -1 && end != -1 && start < end) {
                var name = type().substring(0, start).strip();
                var subType = type().substring(start + 1, end).strip();
                TypeCompiler typeCompiler = new TypeCompiler(subType);
                return Some(JavaString.from(name + "[" + typeCompiler.compile().map(JavaString::inner) + "]"));
            } else {
                return Some(JavaString.from(type));
            }
        }
    }
}