package com.meti.app.compile.magma;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.Option;
import com.meti.app.compile.common.Definition;
import com.meti.app.compile.common.DefinitionNode;
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
                .map(DefinitionNode::new);
    }

    private boolean hasFlags(Node field) {
        try {
            var list = field.apply(Attribute.Category.Flags).asStreamOfFlags()
                    .foldRight(List.<Definition.Flag>createList(), List::add);
            return list.contains(Definition.Flag.Let) || list.contains(Definition.Flag.Const);
        } catch (StreamException | RuntimeException | AttributeException e) {
            return false;
        }
    }
}
