package com.meti.app.compile.clazz;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.Node;
import com.meti.app.compile.Renderer;
import com.meti.app.compile.attribute.Attribute;
import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.java.String_;

import static com.meti.core.Options.$$;
import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record ObjectRenderer(Node root) implements Renderer {
    @Override
    public Option<Result<String_, CompileException>> render() {
        return $Option(() -> {
            if (!root.is(fromSlice("object"))) return $$();

            var name = root.applyOptionally(fromSlice("name")).flatMap(Attribute::asString).$();
            var body = root
                    .applyOptionally(fromSlice("body")).flatMap(Attribute::asNode).$()
                    .applyOptionally(fromSlice("value")).flatMap(Attribute::asString).$();
            return fromSlice("object ")
                    .appendOwned(name)
                    .append(" ")
                    .appendOwned(body);
        }).map(Ok::apply);
    }
}
