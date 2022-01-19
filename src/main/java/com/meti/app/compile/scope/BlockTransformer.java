package com.meti.app.compile.scope;

import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.cache.Cache;
import com.meti.app.compile.common.Line;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.stage.Transformer;

import static com.meti.app.compile.node.EmptyNode.EmptyNode_;

public record BlockTransformer(Node root) implements Transformer {
    @Override
    public Option<Node> transform() throws CompileException {
        if (root.is(Node.Type.Block)) {
            var output = transformBlock(root);
            return new Some<>(output);
        } else {
            return new None<>();
        }
    }

    private static Node transformBlock(Node root) throws CompileException {
        try {
            return root.apply(Attribute.Type.Children)
                    .asStreamOfNodes1()
                    .map(BlockTransformer::transformInContext)
                    .foldRight(new Block.Builder(), Block.Builder::add)
                    .build();
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private static Node transformInContext(Node oldChild) throws AttributeException {
        if (oldChild.is(Node.Type.Implementation)) {
            return new Cache(EmptyNode_, oldChild);
        }
        if (oldChild.is(Node.Type.Binary) || oldChild.is(Node.Type.Invocation)) return new Line(oldChild);
        if (oldChild.is(Node.Type.If)) {
            var value = oldChild.apply(Attribute.Type.Value).asNode();
            if (value.is(Node.Type.Block)) {
                return oldChild;
            } else {
                return oldChild.with(Attribute.Type.Value, new NodeAttribute(new Line(value)));
            }
        }
        return oldChild;
    }
}
