package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OtherApplicationTest {
    private static final Path Root = Paths.get(".");
    private static final String FileName = "main";
    private static final Path Target = Root.resolve(FileName + ".c");
    private static final Path Source = Root.resolve(FileName + ".mgs");
    private static final Path Header = Root.resolve(FileName + ".h");

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Header);
        Files.deleteIfExists(Source);
    }

    @Test
    void test() throws IOException, ApplicationException {
        Files.createFile(Source);
        var target = new Application(Source).run()
                .map(TargetSet::getTarget)
                .orElse(Header);
        assertEquals(Target, target);
    }
}
