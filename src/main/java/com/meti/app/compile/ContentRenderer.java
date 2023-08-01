package com.meti.app.compile;

import com.meti.app.compile.attribute.Attribute;
import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record ContentRenderer(Node node) implements Renderer {
    @Override
    public Option<Result<String_, CompileException>> render() {
        return node.applyOptionally(fromSlice("value"))
                .flatMap(Attribute::asString)
                .map(Ok::apply);
    }
}
