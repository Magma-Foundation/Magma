package com.meti;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var sourceGateway = new DirectoryGateway(Paths.get(".", "src"));
        var targetGateway = new DirectoryGateway(Paths.get(".", "target"));

        try {
            new Application(sourceGateway, targetGateway).run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
