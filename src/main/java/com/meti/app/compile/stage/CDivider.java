package com.meti.app.compile.stage;

import com.meti.api.collect.java.JavaMap;
import com.meti.api.collect.java.List;
import com.meti.api.collect.java.Map;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.clang.CFormat;
import com.meti.app.compile.common.Abstraction;
import com.meti.app.compile.common.Import;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.text.RootText;
import com.meti.app.source.Packaging;

import static com.meti.app.compile.clang.CFormat.Header;
import static com.meti.app.compile.clang.CFormat.Source;

public class CDivider extends MappedDivider {
    private final Packaging thisPackage;

    public CDivider(Packaging thisPackage, Map<CFormat, List<Node>> map) {
        super(map);
        this.thisPackage = thisPackage;
    }

    public CDivider(Packaging thisPackage) {
        super(new JavaMap<>());
        this.thisPackage = thisPackage;
    }

    @Override
    protected Option<List<Node>> decompose(Node node) throws CompileException {
        if (node.is(Node.Role.Implementation)) {
            return new Some<>(decomposeImplementation(node));
        }
        if (node.is(Node.Role.Cache)) {
            return new Some<>(decomposeCache(node));
        }
        return new None<>();
    }

    @Override
    protected CFormat apply(Node node) {
        return node.is(Node.Role.Import)
               || node.is(Node.Role.Extern)
               || node.is(Node.Role.Structure)
               || node.is(Node.Role.Abstraction)
                ? Header
                : Source;
    }

    @Override
    public List<Node> generate(CFormat format) throws StreamException {
        if (format == Header) {
            var header = thisPackage.formatDeclared();
            return Streams.apply("#ifndef " + header + "\n",
                    "\n#define " + header + "\n",
                    "\n#endif\n")
                    .map(RootText::new)
                    .map(InputNode::new)
                    .foldRight(List.createList(), List::add);
        }
        return List.apply(new Import(new Packaging(thisPackage.computeName())));
    }

    @Override
    protected List<Node> modify(CFormat format, Node node, List<Node> value) {
        if (format == Header) return value.insert(value.size() - 1, node);
        return value.add(node);
    }

    @Override
    protected MappedDivider complete(Map<CFormat, List<Node>> map) {
        return new CDivider(thisPackage, map);
    }

    private List<Node> decomposeCache(Node node) throws CompileException {
        try {
            var value = node.apply(Attribute.Type.Value).asNode();
            return node.apply(Attribute.Type.Children)
                    .asStreamOfNodes()
                    .map(this::decompose)
                    .map(option -> option.orElse(List.createList()))
                    .foldRight(List.<Node>createList(), List::addAll)
                    .add(value);
        } catch (AttributeException | StreamException e) {
            throw new CompileException(e);
        }
    }

    private List<Node> decomposeImplementation(Node node) throws CompileException {
        try {
            var identity = node.apply(Attribute.Type.Identity).asNode();
            var parameters = node.apply(Attribute.Type.Parameters)
                    .asStreamOfNodes()
                    .foldRight(List.<Node>createList(), List::add);
            var abstraction = new Abstraction(identity, parameters);
            return List.apply(abstraction, node);
        } catch (AttributeException | StreamException e) {
            throw new CompileException(e);
        }
    }

    @Override
    public Stream<CFormat> stream() {
        return Streams.apply(Header, Source);
    }
}
