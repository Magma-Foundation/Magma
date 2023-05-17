package com.meti;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var input = Paths.get(".", "Main.java");
        var output = Paths.get(".", "Main.mgs");

        FilesWrapper.readImpl(input).match(content -> FilesWrapper
                .writeImpl(output, content)
                .asErr()
                .ifPresent(value -> {
                    System.err.println("Failed to write output.");
                    value.printStackTrace();
                }), e -> {
            System.err.println("Failed to read input.");
            e.printStackTrace();
        });
    }
}
