package com.meti.magma;

import com.meti.clang.Processor;
import com.meti.compile.feature.Import;
import com.meti.compile.node.Input;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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
