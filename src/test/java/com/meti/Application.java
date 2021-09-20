package com.meti;

import java.io.IOException;
import java.nio.file.Files;

public class Application {
    void run() throws IOException {
        if (Files.exists(ApplicationTest.Source)) {
            Files.createFile(ApplicationTest.TargetHeader);
            Files.createFile(ApplicationTest.TargetSource);
        }
    }
}