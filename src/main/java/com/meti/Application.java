package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Application(Path source) {
    Option run() throws IOException {
        var fileName = source.getFileName().toString();
        var separator = fileName.indexOf('.');
        var name = fileName.substring(0, separator);
        var target = source.resolveSibling(name + ".c");
        if (Files.exists(source)) {
            var input = Files.readString(source);
            String output;
            if (input.isBlank()) {
                output = "";
            } else {
                output = "int main(){return 0;}";
            }
            Files.writeString(target, output);
            return new Some(target);
        } else {
            return new None();
        }
    }
}
