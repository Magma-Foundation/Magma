package com.meti.compile;

import com.meti.collect.CollectionException;
import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.common.Line;
import com.meti.compile.common.block.Block;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record BlockTransformer(Node root) implements Transformer {
    private static Cache.Prototype<Block.Builder> transformChild(Node oldChild) throws CollectionException, AttributeException {
        if (oldChild.is(Node.Type.Cache)) {
            return Cache.Prototype.<Block.Builder, RuntimeException>fromCache(oldChild).apply(value -> new Block.Builder().add(value));
        }
        if (!oldChild.is(Node.Type.Implementation)) {
            var newChild = shouldBeAsLine(oldChild) ? new Line(oldChild) : oldChild;
            return new Cache.Prototype<>(new Block.Builder().add(newChild));
        }
        return new Cache.Prototype<>(new Block.Builder(), oldChild);
    }

    private static Node transformBlock(Node root) throws CompileException {
        try {
            return root.apply(Attribute.Type.Children)
                    .asStreamOfNodes1()
                    .map(BlockTransformer::transformChild)
                    .foldRight(new Cache.Prototype<>(new Block.Builder()), Cache.Prototype::merge)
                    .toCache();
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    @Override
    public Option<Node> transform() throws CompileException {
        if (root.is(Node.Type.Block)) {
            var output = transformBlock(root);
            return new Some<>(output);
        } else {
            return new None<>();
        }
    }

    private static boolean shouldBeAsLine(Node child) {
        return child.is(Node.Type.Binary) || child.is(Node.Type.Invocation);
    }
}
