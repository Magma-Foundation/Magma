package com.meti.app.compile.magma;

import com.meti.api.option.Option;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;

import java.util.Arrays;
import java.util.stream.Collectors;

public record FunctionTypeLexer(Input text) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        return text.firstIndexOfChar(')')
                .filter(value -> text.startsWithChar('('))
                .flatMap(this::extract);
    }

    private Option<Node> extract(Integer end) {
        var parameters = Arrays.stream(text.slice(1, end).compute().split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(RootText::new)
                .map(InputNode::new)
                .collect(Collectors.<Node>toList());
        return text.firstIndexOfSlice("=>").map(separator -> {
            var returnText = text.slice(separator + "=>".length());
            var returnType = new InputNode(returnText);
            return new FunctionType(returnType, parameters);
        });
    }
}
