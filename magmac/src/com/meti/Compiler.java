package com.meti;

import com.meti.compile.iterator.ArrayIterator;
import com.meti.compile.iterator.Collectors;
import com.meti.compile.result.Err;
import com.meti.compile.result.Ok;
import com.meti.compile.result.Result;
import org.jetbrains.annotations.NotNull;

public record Compiler(String input) {
    static Result<String, CompileException> compileImport(String input) {
        var separator = input.lastIndexOf('.');
        if (separator == -1) {
            return new Err<>(new CompileException("Invalid import syntax."));
        } else {
            var parent = input.substring("import ".length(), separator).strip();
            var child = input.substring(separator + 1).strip();
            return Ok.apply("import { %s } from %s;".formatted(child, parent));
        }
    }

    String compile() throws CompileException {
        var args = input.split(";");
        return foldRight(args).unwrap();
    }

    @NotNull
    private Result<String, CompileException> foldRight(String[] args) {
        return new ArrayIterator(args)
                .map(this::compileLine)
                .collect(Collectors.exceptionally(Collectors.joining()));
    }

    private Result<String, CompileException> compileLine(String line) {
        if (line.startsWith("import ")) {
            return compileImport(line);
        }
        if (line.startsWith("record ")) {
            var paramStart = line.indexOf('(');
            if (paramStart != -1) {
                var name = line.substring("record ".length(), paramStart).strip();
                return Ok.apply("class def " + name + "() => {}");
            }
        }
        return Ok.apply("");
    }
}