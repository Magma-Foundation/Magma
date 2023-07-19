package com.meti.app;

import com.meti.core.Option;
import com.meti.java.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Function;

public record Import(String_ name, Set<Import> children) {
    public Import(String_ value) {
        this(value, JavaSet.empty());
    }

    String_ render() {
        if (children.isEmpty()) {
            return name;
        } else {
            var sorted = new JavaList<>(new ArrayList<>(children.unwrap()))
                    .sort((o1, o2) -> o1.name.compareTo(o2.name));

            var unwrap = sorted.iter()
                    .map(Import::render)
                    .collect(JavaString.joining(JavaString.from(", ")))
                    .unwrapOrElse(JavaString.Empty)
                    .unwrap();

            return JavaString.from("{ " + unwrap + " } from " + name.unwrap());
        }
    }

    public Option<Import> findChild(String_ name) {
        return this.children.iter()
                .filter(child -> child.name.equalsTo(name))
                .head();
    }

    public Import addPath(NonEmptyList<String_> path) {
        var value = path.first();
        return this.ensureChild(value, new Function<Import, Import>() {
            @Override
            public Import apply(Import anImport) {
                return null;
            }
        });
    }

    private Import ensureChild(String_ name, Function<Import, Import> mapper) {
        var list = new ArrayList<>(this.children.unwrap());
        var index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).name.equalsTo(name)) {
                index = i;
            }
        }

        if (index == -1) {
            var next = mapper.apply(list.get(index));
            list.set(index, next);
            return new Import(this.name, new JavaSet<>(new HashSet<>(list)));
        } else {
            var child = new Import(name);
            var newChild = mapper.apply(child);
            this.children.add(newChild);
            return new Import(this.name);
        }
    }
}

