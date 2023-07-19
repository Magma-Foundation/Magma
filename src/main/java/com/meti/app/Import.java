package com.meti.app;

import com.meti.java.*;

public record Import(String_ value, List<Import> children) {
    public Import(String_ value) {
        this(value, JavaList.empty());
    }

    String_ render() {
        var unwrap = children.iter()
                .map(Import::render)
                .collect(JavaString.joining(JavaString.from(", ")))
                .unwrap();

        return JavaString.from("{" + unwrap + "} from " + value);
    }

    public Import addChild(Import child) {
        return new Import(value, children.add(child));
    }

    public Import addPath(List<String_> path) {
        return path.into(NonEmptyJavaList::from).map(list -> {
            var currentValue = list.first();
            var existingChild = children.iter()
                    .filter(node -> node.value().equals(currentValue))
                    .head();

            var newNode = existingChild
                    .unwrapOrElseGet(() -> new Import(currentValue))
                    .addPath(list.sliceWithoutFirst());

            return addChild(newNode);
        }).unwrapOrElseGet(() -> this);
    }
}

