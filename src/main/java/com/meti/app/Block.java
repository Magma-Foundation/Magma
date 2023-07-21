package com.meti.app;

import com.meti.java.JavaString;
import com.meti.java.List;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record Block(List<Renderable> lines) implements Renderable {
    @Override
    public String_ render() {
        return lines.iter()
                .map(Renderable::render)
                .collect(JavaString.joining(fromSlice(";")))
                .unwrapOrElse(fromSlice(""))
                .prepend("{")
                .append("}");
    }
}
