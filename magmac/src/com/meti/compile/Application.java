package com.meti.compile;

import com.meti.collect.option.Option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record Application(Path source) {

    public Option<Path> run() throws IOException, CompileException {
        if (!Files.exists(source)) return None();

        var input = Files.readString(source);

        var root = new Compiler(input).compile();

        var fileName = source().getFileName().toString();
        var index = fileName.indexOf(".");
        var name = fileName.substring(0, index);
        var target = source().resolveSibling(name + ".mgs");
        Files.writeString(target, root);
        return Some(target);
    }

}