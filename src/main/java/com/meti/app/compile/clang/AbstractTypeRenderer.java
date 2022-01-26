package com.meti.app.compile.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.OutputNode;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;

public abstract class AbstractTypeRenderer implements Processor<Node> {
    protected final Input name;
    protected final Node type;
    protected final Node.Type nodeType;

    public AbstractTypeRenderer(Input name, Node type, Node.Type nodeType) {
        this.name = name;
        this.type = type;
        this.nodeType = nodeType;
    }

    @Override
    public Option<Node> process() throws CompileException {
        return type.is(nodeType) ? new Some<>(new OutputNode((processValid()))) : new None<>();
    }

    protected abstract Output processValid() throws CompileException;
}
