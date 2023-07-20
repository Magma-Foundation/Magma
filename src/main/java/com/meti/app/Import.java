package com.meti.app;

import com.meti.core.Option;
import com.meti.core.Tuple;
import com.meti.iterate.Collectors;
import com.meti.iterate.Iterator;
import com.meti.iterate.TupleIterator;
import com.meti.java.*;

import java.util.ArrayList;
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
        return this.ensureChild(value, anImport -> path.sliceWithoutFirst()
                .into(NonEmptyJavaList::from)
                .map(anImport::addPath)
                .unwrapOrElse(anImport));
    }

    private Import ensureChild(String_ name, Function<Import, Import> mapper) {
        var tuple = this.children.iter()
                .<String_, Tuple<Boolean, Import>>unzip(child -> child.name)
                .on(value -> value.equalsTo(name),
                        iterator -> iterator.map(mapper).map(
                                value -> new Tuple<>(true, value)))
                .onDefault(iterator -> iterator.map(value -> new Tuple<>(false, value)))
                .fold(Iterator::then)
                .into(TupleIterator::new)
                .collectTuple(Collectors.or(), JavaSet.asSet());

        var wasModified = tuple.a();
        var newChildren = tuple.b();
        if (wasModified) {
            return new Import(this.name, newChildren);
        } else {
            return new Import(this.name, newChildren.add(mapper.apply(new Import(name))));
        }
    }
}

