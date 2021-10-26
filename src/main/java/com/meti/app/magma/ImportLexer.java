package com.meti.app.magma;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.clang.Processor;
import com.meti.app.compile.feature.Import;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;

public record ImportLexer(Input root) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        if (root.startsWithString("import native ")) {
            var value = root.truncate("import native ");
            return new Some<>(new Import(value));
        }
        return new None<>();
    }
}
