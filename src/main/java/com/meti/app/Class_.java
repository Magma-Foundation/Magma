package com.meti.app;

import com.meti.core.Option;
import com.meti.java.JavaList;
import com.meti.java.JavaSet;
import com.meti.java.Objects;
import com.meti.java.String_;

import java.util.ArrayList;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record Class_(String_ name, Renderable compiledBody) implements Transformable {
    @Override
    public Option<Renderable> transform() {
        return $Option(() -> {
            var block = Objects.cast(Block.class, compiledBody()).$();

            var parameters = new ArrayList<Renderable>();
            var value = new ArrayList<Renderable>();
            var unwrappedLines = block.lines().unwrap();

            for (var instance : unwrappedLines) {
                Objects.cast(Declaration.class, instance).consumeOrElse(parameters::add, () -> value.add(instance));
            }

            return new Function(JavaSet.of(fromSlice("class")), name(), new JavaList<>(parameters), new Block(new JavaList<>(value)));
        });
    }
}