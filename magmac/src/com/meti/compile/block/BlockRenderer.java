package com.meti.compile.block;

import com.meti.api.collect.Collectors;
import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.compile.Node;
import com.meti.compile.Renderer;

public record BlockRenderer(Node node) implements Renderer {

    @Override
    public Option<JavaString> render() {
        return this.node()
                .getLines()
                .map(lines -> lines.iter()
                        .collect(Collectors.joining(new JavaString("\t")))
                        .unwrapOrElse(JavaString.Empty)
                        .prepend("{\n")
                        .append("\n}"));
    }
}