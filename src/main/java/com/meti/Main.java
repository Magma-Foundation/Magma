package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var source = create("src");
        var target = create("target");

        var sourceGateway = new DirectoryGateway(source);
        var targetGateway = new DirectoryGateway(target);

        var err = new Application(sourceGateway, targetGateway).run().asErr();
        err.ifPresent(Throwable::printStackTrace);
    }

    private static Path create(String directory) {
        var source = Paths.get(".", directory);
        if (!Files.exists(source)) {
            try {
                Files.createDirectories(source);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return source;
    }
}
