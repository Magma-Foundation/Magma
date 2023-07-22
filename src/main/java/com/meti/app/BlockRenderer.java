package com.meti.app;

import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.iterate.Iterators;
import com.meti.java.JavaString;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record BlockRenderer(Block block) implements Renderer {
    @Override
    public Option<String_> render() {
        return Some.apply(block().lines().iter()
                .map(Node::value)
                .flatMap(Iterators::fromOption)
                .collect(JavaString.joining(fromSlice("")))
                .unwrapOrElse(fromSlice(""))
                .prepend("{")
                .append("}"));
    }
}