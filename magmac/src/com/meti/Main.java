package com.meti;

import com.meti.compile.Application;
import com.meti.compile.DirectorySourceSet;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        extracted(
                Paths.get(".", "Personal", "Magma", "magmac", "src"),
                Paths.get(".", "Personal", "Magma", "magmac", "build", "src")
        );

        extracted(
                Paths.get(".", "Personal", "Magma", "magmac", "test"),
                Paths.get(".", "Personal", "Magma", "magmac", "build", "test")
        );
    }

    private static void extracted(Path source, Path output) {
        var option = new Application(new DirectorySourceSet(source), output).run();
        option.consume(values -> {
            System.out.println(values);
        }, err -> {
            err.printStackTrace();
        });
    }
}
