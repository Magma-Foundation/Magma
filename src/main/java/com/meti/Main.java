package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        var path = Paths.get(".", "Main.java");
        readString(path).match(value -> {
            var target = Paths.get(".", "Main.mgs");
            writeString(value, target).match(() -> {
                System.out.println("File compiled.");
            }, error -> {
                System.err.println("Failed to write target.");
                error.printStackTrace();
            });
        }, error -> {
            System.err.println("Failed to read source.");
            error.printStackTrace();
        });
    }

    private static Result<Runnable> writeString(String value, Path target) {
        try {
            Files.writeString(target, value);
            return new EmptyOk();
        } catch (IOException e) {
            return Err.forEmpty(e);
        }
    }

    private static Result<Consumer<String>> readString(Path path) {
        try {
            return new Ok(Files.readString(path));
        } catch (IOException e) {
            return Err.forValued(e);
        }
    }
}
