package com.meti.app.compile.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Output;
import com.meti.app.compile.text.RootText;
import com.meti.app.compile.render.Renderer;

public record ExternRenderer(Node node) implements Renderer {
    @Override
    public Option<Output> render() throws AttributeException {
        if (node.is(Node.Type.Extern)) {
            return new Some<>(new RootText("#include <" + node.apply(Attribute.Type.Value)
                    .asText().computeTrimmed() + ".h>\n"));
        } else {
            return new None<>();
        }
    }
}
