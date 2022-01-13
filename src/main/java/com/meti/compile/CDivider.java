package com.meti.compile;

import com.meti.collect.*;
import com.meti.compile.clang.CFormat;
import com.meti.compile.common.Import;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
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
    public Stream<CFormat> stream() {
        return Streams.apply(Header, Source);
    }

    @Override
    public JavaList<Node> generate(CFormat format, Node node) throws StreamException {
        if (format == Header) {
            var header = thisPackage.formatDeclared();
            return Streams.apply("\n#ifndef " + header + "\n",
                    "\n#define " + header + "\n",
                    "\n#endif\n")
                    .map(Text::new)
                    .map(Content::new)
                    .foldRight(new JavaList<>(), JavaList::add);
        }
        return JavaList.apply(new Import(new Packaging(thisPackage.computeName())), node);
    }

    @Override
    protected JavaList<Node> modify(CFormat format, Node node, JavaList<Node> value) {
        return format == Header ? value.insert(value.size() - 1, node) : value.add(node);
    }

    @Override
    protected Divider complete(JavaMap<CFormat, JavaList<Node>> map) {
        return new CDivider(thisPackage, map);
    }
}
