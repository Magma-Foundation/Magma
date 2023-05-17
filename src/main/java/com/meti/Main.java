package com.meti;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var input = Paths.get(".", "Main.java");
        var output = Paths.get(".", "Main.mgs");

        FilesWrapper.readImpl(input).match(content -> {
            var fileName = new JavaString(input.getFileName().toString());
            var realName = fileName.indexOf(".")
                    .map(index -> fileName.slice(0, index))
                    .flatMap(Result::asOk)
                    .unwrapOrElse(fileName);

            var path = input.resolveSibling(realName.add(".mgs").asString());
            FilesWrapper.writeImpl(path, content)
                    .asErr()
                    .ifPresent(value -> {
                        System.err.println("Failed to write output.");
                        value.printStackTrace();
                    });
        }, e -> {
            System.err.println("Failed to read input.");
            e.printStackTrace();
        });
    }
}
