package com.meti.app.compile.magma;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;

public record FunctionTypeLexer(Input text) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        return text.firstIndexOfChar(')')
                .filter(value -> text.startsWithChar('('))
                .flatMap(this::extract);
    }

    private Option<Node> extract(Integer end) {
        try {
            var parameters = Streams.apply(text.slice(1, end)
                    .toOutput()
                    .compute().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .map(RootText::new)
                    .map(InputNode::new)
                    .foldRight(List.<Node>createList(), List::add);

            return text.firstIndexOfSlice("=>").map(separator -> {
                var returnText = text.slice(separator + "=>".length());
                var returnType = new InputNode(returnText);
                return new FunctionType(returnType, parameters);
            });

        } catch (StreamException e) {
            return new None<>();
        }
    }
}
