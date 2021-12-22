package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.HashSet;
import java.util.Set;

public class Scope {
    private final Set<Field> fields;

    public Scope() {
        this(new HashSet<>());
    }

    public Scope(Set<Field> fields) {
        this.fields = fields;
    }

    public Option<Field> apply(String name) {
        return fields.stream()
                .filter(field -> field.isNamed(name))
                .findFirst()
                .<Option<Field>>map(Some::new)
                .orElse(new None<>());
    }

    public void define(Field field) {
        fields.add(field);
    }

    boolean isUndefined(String name) {
        return fields.stream().noneMatch(field -> field.isNamed(name));
    }
}
