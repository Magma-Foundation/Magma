package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        var source = Paths.get(".", "Main.mgs");
        readImpl(source).match(s -> {
            var target = Paths.get(".", "Main.c");
            var output = "#include <stdio.h>\nint main(){\n\tprintf(\"%s\",\"Hello World!\");\n\treturn 0;\n}";
            writeImpl(target, output).match(() -> System.out.println("Compiled successfully."), e -> {
                System.err.println("Failed to write target.");
                e.printStackTrace();
            });
        }, e -> {
            System.err.println("Failed to read source.");
            e.printStackTrace();
        });
    }

    private static Result<Runnable> writeImpl(Path target, String content) {
        try {
            Files.writeString(target, content);
            return new EmptyOk();
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Result<Consumer<String>> readImpl(Path source) {
        try {
            var input = Files.readString(source);
            return new ValueOk(input);
        } catch (IOException e) {
            return new Err<>(e);
        }
    }
}
