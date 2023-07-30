package com.meti.app;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.Compiler;
import com.meti.app.io.NIOSource;
import com.meti.app.io.NIOTarget;
import com.meti.app.io.Sources;
import com.meti.app.io.Targets;
import com.meti.core.Result;
import com.meti.core.Results;
import com.meti.core.ThrowableResult;
import com.meti.iterate.ResultIterator;
import com.meti.java.JavaSet;
import com.meti.java.Set;

import static com.meti.core.Results.$Result;

public final class Application {
    private final Sources sources;
    private final Targets targets;

    public Application(Sources sources, Targets targets) {
        this.sources = sources;
        this.targets = targets;
    }

    Result<Set<NIOTarget>, CompileException> compileAll() {
        return sources.collect()
                .peekValue(value -> System.out.printf("Found '%s' sources.%n", value.size().value()))
                .mapErr(CompileException::new)
                .mapValueToResult(nioSourceJavaSet -> nioSourceJavaSet.iter()
                        .map(this::compile)
                        .into(ResultIterator::new)
                        .collectAsResult(JavaSet.fromSet()));
    }

    private Result<NIOTarget, CompileException> compile(NIOSource source) {
        return $Result(CompileException.class, () -> {
            var package_ = source.computePackage();
            var other = source.computeName().append(".mgs");
            var input = source.read()
                    .mapErr(CompileException::new)
                    .into(ThrowableResult::new).$();

            var output = new Compiler(input)
                    .compile()
                    .into(ThrowableResult::new)
                    .$();

            var target = targets.resolve(package_, other)
                    .mapErr(CompileException::new)
                    .into(ThrowableResult::new)
                    .$();

            Results.throwOption(target.write(output).map(CompileException::new));
            return target;
        });
    }
}