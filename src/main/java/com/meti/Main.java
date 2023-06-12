package com.meti;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var path = new NativePath(Paths.get(".", "src"));
        var gateway = new DirectoryGateway(path);
        new Application(gateway).run().consume(
                onOk -> System.out.println("Found '" + onOk.size() + "' targets."),
                Throwable::printStackTrace);
    }
}
