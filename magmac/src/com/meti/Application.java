package com.meti;

import com.meti.compile.Compiler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public record Application(Path source) {

    private static Optional<Path> writeTarget(String output, Path target) throws CompileException {
        try {
            Files.writeString(target, output);
            return Optional.of(target);
        } catch (IOException e) {
            throw new CompileException("Failed to write target.", e);
        }
    }

    Optional<Path> run() throws CompileException {
        if (Files.exists(source())) {
            return compileSource();
        }
        return Optional.empty();
    }

    private Optional<Path> compileSource() throws CompileException {
        var input = readSource();
        var output = new Compiler(input).compile();
        var target = resolveTarget();
        return writeTarget(output, target);
    }

    @NotNull
    private Path resolveTarget() {
        var fileName = source().getFileName().toString();
        var separator = fileName.indexOf('.');
        var fileNameWithoutExtension = separator == -1 ? fileName : fileName.substring(0, separator);
        return this.source().resolveSibling(fileNameWithoutExtension + ".mgs");
    }

    private String readSource() throws CompileException {
        String input;
        try {
            input = Files.readString(source);
        } catch (IOException e) {
            throw new CompileException("Failed to read source.", e);
        }
        return input;
    }
}