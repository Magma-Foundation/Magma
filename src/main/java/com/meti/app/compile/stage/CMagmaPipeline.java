package com.meti.app.compile.stage;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.parse.MagmaParser;
import com.meti.app.source.Packaging;

public class CMagmaPipeline {
    private final CMagmaNodeResolver resolver = new CMagmaNodeResolver();
    private final CFormatter formatter;
    private final List<Node> input;
    private final CFlattener flattener = new CFlattener();

    public CMagmaPipeline(Node... input) {
        this(List.apply(input));
    }

    public CMagmaPipeline(List<Node> input) {
        this(new Packaging(""), input);
    }

    public CMagmaPipeline(Packaging thisPackage, List<Node> input) {
        this.formatter = new CFormatter(thisPackage);
        this.input = input;
    }

    public Stream<Node> pipe() throws CompileException {
        try {
            var parsed = new MagmaParser(this.input).parse();
            var resolved = perform(resolver, parsed);
            var formatted = perform(formatter, resolved);
            return perform(flattener, formatted).stream();
        } catch (StreamException | CompileException e) {
            try {
                var separator = "\n-----\n";
                var content = input.stream()
                        .map(Object::toString)
                        .foldRight("", (current, next) -> current + separator + next);
                throw new CompileException("Failed to pipe values:" + separator + content + separator, e);
            } catch (StreamException streamException) {
                throw new CompileException(e);
            }
        }
    }

    private List<Node> perform(AbstractStage stage, List<Node> current) throws StreamException {
        return current.stream()
                .map(stage::transformNodeAST)
                .foldRight(List.createList(), List::add);
    }
}
