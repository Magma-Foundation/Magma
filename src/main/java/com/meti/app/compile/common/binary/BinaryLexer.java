package com.meti.app.compile.common.binary;

import com.meti.api.option.Option;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;

public record BinaryLexer(Input text) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        return text.firstIndexOfChar(' ')
                .flatMap(firstSeparator -> text.firstIndexOfCharWithOffset(' ', firstSeparator + 1)
                        .map(secondSeparator -> extract(firstSeparator, secondSeparator)));
    }

    private BinaryOperation extract(Integer firstSeparator, Integer secondSeparator) {
        var firstText = text.slice(0, firstSeparator);
        var operatorText = text.slice(firstSeparator + 1, secondSeparator);
        var secondText = text.slice(secondSeparator + 1);

        var first = new InputNode(firstText);
        var operator = new InputNode(operatorText);
        var second = new InputNode(secondText);

        return new BinaryOperation(operator, first, second);
    }
}
