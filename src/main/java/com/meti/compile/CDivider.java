package com.meti.compile;

import com.meti.collect.*;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.clang.CFormat;
import com.meti.compile.common.Import;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;
import com.meti.source.Packaging;

import static com.meti.compile.clang.CFormat.Header;
import static com.meti.compile.clang.CFormat.Source;

public class CDivider extends MappedDivider {
    private final Packaging thisPackage;

    public CDivider(Packaging thisPackage, JavaMap<CFormat, JavaList<Node>> map) {
        super(map);
        this.thisPackage = thisPackage;
    }

    public CDivider(Packaging thisPackage) {
        super(new JavaMap<>());
        this.thisPackage = thisPackage;
    }

    @Override
    protected CFormat apply(Node node) {
        return node.is(Node.Type.Import) || node.is(Node.Type.Extern) || node.is(Node.Type.Structure)
                ? Header
                : Source;
    }

    @Override
    public JavaList<Node> generate(CFormat format) throws StreamException {
        if (format == Header) {
            var header = thisPackage.formatDeclared();
            return Streams.apply("\n#ifndef " + header + "\n",
                    "\n#define " + header + "\n",
                    "\n#endif\n")
                    .map(Text::new)
                    .map(Content::new)
                    .foldRight(new JavaList<>(), JavaList::add);
        }
        return JavaList.apply(new Import(new Packaging(thisPackage.computeName())));
    }

    @Override
    protected Option<MappedDivider> decompose(Node node) throws CompileException {
        if (node.is(Node.Type.Cache)) {
            try {
                var value = node.apply(Attribute.Type.Value).asNode();
                return new Some<>(node.apply(Attribute.Type.Children)
                        .asStreamOfNodes1()
                        .foldRight(this, MappedDivider::divide)
                        .divide(value));
            } catch (AttributeException | StreamException e) {
                throw new CompileException(e);
            }
        }
        return new None<>();
    }

    @Override
    protected JavaList<Node> modify(CFormat format, Node node, JavaList<Node> value) {
        if (format == Header) return value.insert(value.size() - 1, node);
        return value.add(node);
    }

    @Override
    protected MappedDivider complete(JavaMap<CFormat, JavaList<Node>> map) {
        return new CDivider(thisPackage, map);
    }

    @Override
    public Stream<CFormat> stream() {
        return Streams.apply(Header, Source);
    }
}
