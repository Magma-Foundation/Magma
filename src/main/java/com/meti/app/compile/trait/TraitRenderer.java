package com.meti.app.compile.trait;

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

public record TraitRenderer(Node value) implements Renderer {
    @Override
    public Option<Result<String_, CompileException>> render() {
        return $Option(() -> {
            if (!value.is(fromSlice("interface"))) return $$();

            var name = value.applyOptionally(fromSlice("name")).$().asString().$();
            var body = value.applyOptionally(fromSlice("body")).$().asNode().$()
                    .b()
                    .applyOptionally(fromSlice("value")).$()
                    .asString().$();

            var genericSlice = value.applyOptionally(fromSlice("generic"))
                    .flatMap(Attribute::asString)
                    .map(value -> value.prepend("<").append(">"))
                    .unwrapOrElse(fromSlice(" "));

            return Ok.apply(fromSlice("trait ")
                    .appendOwned(name)
                    .appendOwned(genericSlice)
                    .appendOwned(body));
        });
    }
}
