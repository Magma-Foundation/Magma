package com.meti;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var src = new DirectoryGateway(Paths.get(".", "src"));
        var target = new DirectoryGateway(Paths.get(".", "target"));

        var err = new Application(src, target).run().asErr();
        err.ifPresent(Throwable::printStackTrace);
    }
}
