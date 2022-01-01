package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {
    void run() throws  ApplicationException {
        if (Files.exists(source)) {
            var input = readInput();
            var output = compileImpl(input);
            var fileName = source.getFileName().toString();
            var separator = fileName.indexOf('.');
            var name = fileName.substring(0, separator);
            var target = source.resolveSibling(name + ".c");
            writeOutput(output, target);
        }
    }

    private void writeOutput(String output, Path target) throws ApplicationException {
        try {
            Files.writeString(target, output);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private String readInput() throws ApplicationException {
        String input;
        try {
            input = Files.readString(source);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
        return input;
    }

    private String compileImpl(String input) throws ApplicationException {
        String output;
        try {
            output = new MagmaCCompiler(input).compile();
        } catch (CompileException e) {
            throw new ApplicationException(e);
        }
        return output;
    }
}
