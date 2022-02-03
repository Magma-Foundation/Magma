package com.meti.app.compile.common;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.InputType;
import com.meti.app.compile.node.Type;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;

public record ReferenceTypeLexer(Input text) implements Processor<Type> {
    @Override
    public Option<Type> process() {
        if (text.startsWithChar('&')) {
            var valueText = text.slice(1);
            var node = new InputType(valueText);
            return new Some<>(new ReferenceType(node));
        } else {
            return new None<>();
        }
    }
}
