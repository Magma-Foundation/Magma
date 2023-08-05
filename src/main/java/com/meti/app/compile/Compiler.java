package com.meti.app.compile;

import com.meti.app.compile.clazz.ClassTransformer;
import com.meti.app.compile.clazz.RecordTransformer;
import com.meti.app.compile.clazz.StaticTransformer;
import com.meti.app.compile.clazz.Transformer;
import com.meti.core.Result;
import com.meti.core.Tuple;
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
                var transformed = new LexingStage().perform(new Tuple<>(fromSlice("node"), line)).$();
                while (true) {
                    Iterator<? extends Transformer> transformers = Iterators.of(
                            new RecordTransformer(transformed),
                            new StaticTransformer(transformed),
                            new ClassTransformer(transformed));

                    var afterTransform = transformers
                            .map(Transformer::transform)
                            .flatMap(Iterators::fromOption)
                            .head()
                            .toTuple(new MapNode(fromSlice("")));

                    if (afterTransform.a()) {
                        transformed = afterTransform.b();
                    } else {
                        break;
                    }
                }

                return new RenderingStage().perform(transformed).$();
            }
        }).mapErr(err -> {
            var format = "Failed to compile input: '%s'";
            var message = format.formatted(line.unwrap());
            return new CompileException(message, err);
        });
    }
}