package com.meti.app;

import com.meti.java.JavaList;
import com.meti.java.List;
import com.meti.java.NonEmptyJavaList;
import com.meti.java.String_;

record Import(String_ value, List<Import> children) {
    public Import(String_ value) {
        this(value, JavaList.empty());
    }

    public Import addChild(Import child) {
        return new Import(value, children.add(child));
    }

    public Import addPath(List<String_> path) {
        return path.into(NonEmptyJavaList::from)
                .map(list -> {
                    var currentValue = list.first();
                    var existingChild = children.iter()
                            .filter(node -> node.value().equals(currentValue))
                            .head();

                    var newNode = existingChild
                            .unwrapOrElseGet(() -> new Import(currentValue))
                            .addPath(list.sliceWithoutFirst());

                    return addChild(newNode);
                })
                .unwrapOrElseGet(() -> {
                    return this;
                });
    }
}

