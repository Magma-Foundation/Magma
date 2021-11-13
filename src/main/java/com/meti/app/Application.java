package com.meti.app;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {
    Option<Path> run() throws ApplicationException {
        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var name = fileName.substring(0, separator);
        if (Files.exists(source)) {
            String input = readSource();
            var output = new Compiler(input).compile();
            return writeTarget(name, output);
        } else {
            return new None<>();
        }
    }

    private String readSource() throws ApplicationException {
        try {
            return Files.readString(source);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private Option<Path> writeTarget(String name, String output) throws ApplicationException {
        try {
            var target = source.resolveSibling(name + ".c");
            Files.writeString(target, output);
            return new Some<>(target);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
