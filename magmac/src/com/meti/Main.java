package com.meti;

import com.meti.api.collect.ImmutableList;
import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.CompileException;
import com.meti.compile.Compiler;
import com.meti.compile.FileNodeLexerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        FileNodeLexerFactory factory;
        try {
            var content = Files.readString(Paths.get(".", "lang", "java.lag"));
            factory = new FileNodeLexerFactory(new JavaString(content));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        var root = Paths.get(".", "magmac");
        var source = root.resolve("src");
        var dist = root.resolve("dist");

        if (!Files.exists(dist)) {
            try {
                Files.createDirectories(dist);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (var list = Files.walk(source)) {
            var collect = list.collect(Collectors.toList());
            var lines = new ImmutableList<>(collect)
                    .iter()
                    .filter(Files::isRegularFile)
                    .collect(ImmutableLists.into());

            lines.iter()
                    .map(file -> compileFile(source, dist, file, factory))
                    .flatMap(Iterators::fromOption)
                    .head()
                    .ifPresent(Throwable::printStackTrace);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Option<CompileException> compileFile(Path source, Path dist, Path file, FileNodeLexerFactory factory) {
        try {
            var input = Files.readString(file);
            return new Compiler(new JavaString(input.strip()), factory).compile().match(output -> {
                var relative = source.relativize(file);
                var parent = relative.getParent();

                var fileName = relative.getFileName().toString();
                var separator = fileName.indexOf('.');
                var withoutSeparator = fileName.substring(0, separator);

                try {
                    var resolvedParent = dist.resolve(parent);
                    if (!Files.exists(resolvedParent)) {
                        Files.createDirectories(resolvedParent);
                    }

                    var outputFile = resolvedParent.resolve(withoutSeparator + ".mgs");
                    Files.writeString(outputFile, output.value());
                    return None.apply();
                } catch (IOException e) {
                    return Some.apply(new CompileException("", e));
                }
            }, Some::apply);


        } catch (IOException e) {
            return Some.apply(new CompileException("Failed to compile file.", e));
        }
    }
}