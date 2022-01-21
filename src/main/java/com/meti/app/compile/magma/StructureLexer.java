package com.meti.app.compile.magma;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.common.Structure;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;

public record StructureLexer(Input text) implements Processor<Node> {
    @Override
    public Option<Node> process() throws CompileException {
        return text.startsWithSlice("struct ") &&
               text.containsChar('{') &&
               text.endsWithChar('}')
                ? text.firstIndexOfChar('{').map(this::extract)
                : new None<>();
    }

    private Structure extract(int fieldsStart) throws CompileException {
        try {
            var name = text.slice("struct ".length(), fieldsStart);
            var fields = text.slice(fieldsStart + 1, text.size() - 1);
            var fieldsList = Streams.apply(fields.toOutput().compute().split(";"))
                    .filter(value -> !value.isBlank())
                    .map(RootText::new)
                    .map(InputNode::new)
                    .foldRight(List.<Node>createList(), List::add);
            return new Structure(name, fieldsList);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }
}
