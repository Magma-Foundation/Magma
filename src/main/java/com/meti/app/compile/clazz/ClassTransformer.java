package com.meti.app.compile.clazz;

import com.meti.app.compile.Declaration;
import com.meti.app.compile.Node;
import com.meti.app.compile.block.Block;
import com.meti.app.compile.function.Function;
import com.meti.core.Option;
import com.meti.java.JavaList;
import com.meti.java.JavaSet;
import com.meti.java.Objects;

import java.util.ArrayList;

import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record ClassTransformer(Class_ aClass) implements Transformer {
    @Override
    public Option<Node> transform() {
        return $Option(() -> {
            var block = Objects.cast(Block.class, aClass().body()).$();

            var parameters = new ArrayList<Node>();
            var value = new ArrayList<Node>();
            var unwrappedLines = block.values().unwrap();

            for (var instance : unwrappedLines) {
                Objects.cast(Declaration.class, instance).consumeOrElse(parameters::add, () -> value.add(instance));
            }

            return new Function(JavaSet.of(fromSlice("class")), aClass().name(), new JavaList<>(parameters), new Block(new JavaList<>(value)));
        });
    }
}