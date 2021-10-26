package com.meti.magma;

import com.meti.Input;
import com.meti.clang.Processor;
import com.meti.feature.Import;
import com.meti.feature.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record ImportLexer(Input root) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        if (root.startsWithString("import native ")) {
            var value = root.slice("import native ", root.length());
            return new Some<>(new Import(value));
        }
        return new None<>();
    }
}
