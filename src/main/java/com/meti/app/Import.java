package com.meti.app;

import com.meti.java.*;

import java.util.ArrayList;

public record Import(String_ value, Set<Import> children) {
    public Import(String_ value) {
        this(value, JavaSet.empty());
    }

    String_ render() {
        if (children.isEmpty()) {
            return value;
        } else {
            var sorted = new JavaList<>(new ArrayList<>(children.unwrap()))
                    .sort((o1, o2) -> o1.value.compareTo(o2.value));

            var unwrap = sorted.iter()
                    .map(Import::render)
                    .collect(JavaString.joining(JavaString.from(", ")))
                    .unwrapOrElse(JavaString.Empty)
                    .unwrap();

            return JavaString.from("{ " + unwrap + " } from " + value.unwrap());
        }
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

