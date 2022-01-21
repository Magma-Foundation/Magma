package com.meti.app.compile.magma;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.Option;
import com.meti.app.compile.common.Declaration;
import com.meti.app.compile.common.Field;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;

public record DefinitionLexer(Input input) implements Processor<Node> {
    @Override
    public Option<Node> process() throws CompileException {
        return new FieldLexer(input)
                .process()
                .filter(this::hasFlags)
                .map(Declaration::new);
    }

    private boolean hasFlags(Node field) {
        try {
            var list = field.apply(Attribute.Type.Flags).asStreamOfFlags1()
                    .foldRight(List.<Field.Flag>createList(), List::add);
            return list.contains(Field.Flag.Let) || list.contains(Field.Flag.Const);
        } catch (StreamException | RuntimeException | AttributeException e) {
            return false;
        }
    }
}
