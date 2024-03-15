package com.meti.compile;

import com.meti.collect.result.Err;
import com.meti.collect.result.Result;
import com.meti.collect.result.Results;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public final class Application {
    private final SourceSet source;
    private final Path root;

    public Application(SourceSet source1, Path root) {
        this.source = source1;
        this.root = root;
    }

    public Result<List<Path>, CompileException> run() {
        Set<Source> sources;
        try {
            sources = source.collectSources();
        } catch (IOException e) {
            return new Err<>(new CompileException(e));
        }

        return Streams.fromSet(sources).map(source -> Results.$Result(() -> {
            var input = source.read()
                    .mapErr(CompileException::new)
                    .$();

            String output;
            try {
                output = new Compiler(input).compile();
            } catch (CompileException e) {
                throw new CompileException("Failed to compile file: '" + source + "'", e);
            }

            var name = source.findName();
            var package1 = source.findPackage();
            var target = Streams.fromList(package1)
                    .foldRight(root, Path::resolve)
                    .resolve(name + ".mgs");

            try {
                var parent = target.getParent();
                if (parent != null) {
                    if(!Files.exists(parent)) {
                        Files.createDirectories(parent);
                    }
                }

                Files.writeString(target, output);
            } catch (IOException e) {
                throw new CompileException(e);
            }
            return target;
        })).collect(Collectors.exceptionally(Collectors.toList()));
    }
}