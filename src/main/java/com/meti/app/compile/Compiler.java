package com.meti.app.compile;

import com.meti.app.compile.clazz.ClassTransformer;
import com.meti.app.compile.clazz.StaticTransformer;
import com.meti.app.compile.clazz.Transformer;
import com.meti.core.Result;
import com.meti.iterate.Iterator;
import com.meti.iterate.Iterators;
import com.meti.iterate.ResultIterator;
import com.meti.java.String_;

import static com.meti.core.Results.$Result;
import static com.meti.java.JavaString.*;

public record Compiler(String_ input) {
    public Result<String_, CompileException> compile() {
        return new Splitter(input).split()
                .filter(line -> !line.strip().startsWith("package "))
                .map(this::compileNode)
                .into(ResultIterator::new)
                .collectAsResult(joining(Empty))
                .mapValue(inner -> inner.unwrapOrElse(Empty));
    }

    private Result<String_, CompileException> compileNode(String_ line) {
        return $Result(CompileException.class, () -> {
            if (line.isEmpty()) {
                return Empty;
            } else {
                var root = new LexingStage().perform(line).$();

                while (true) {
                    Iterator<? extends Transformer> transformers = Iterators.of(new StaticTransformer(root),
                            new ClassTransformer(root));

                    var transformed = transformers
                            .map(Transformer::transform)
                            .flatMap(Iterators::fromOption)
                            .head().toTuple(new MapNode(fromSlice("")));
                    if (transformed.a()) {
                        root = transformed.b();
                    } else {
                        break;
                    }
                }

                return new RenderingStage().perform(root).$();
            }
        }).mapErr(err -> new CompileException("Failed to compile input: '" + line.unwrap() + "'", err));
    }
}