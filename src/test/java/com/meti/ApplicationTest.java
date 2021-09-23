package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static final Path Root = Paths.get(".");
    private static final String Package = "index";
    private static final Path Source = Root.resolve(Package + ".mgs");
    private static final Path Header = Source.resolveSibling(Package + ".h");
    private static final Path Target = Source.resolveSibling(Package + ".c");

    @Test
    void generated() throws IOException {
        assertTrue(runPresent());
    }

    private boolean runPresent() throws IOException {
        return runWithEmptySource().isPresent();
    }

    private Option<TargetSet> runWithEmptySource() throws IOException {
        return runWithSource("");
    }

    private void assertContains(Path path, String input, String output) throws IOException {
        runWithSource(input);
        var actual = Files.readString(path);
        assertEquals(output, actual);
    }

    private Option<TargetSet> runWithSource(String input) throws IOException {
        Files.writeString(Source, input);
        try {
            return runApplication();
        } catch (ApplicationException e) {
            fail(e);
            return new None<>();
        }
    }

    @Test
    void generated_header() throws IOException {
        assertEquals(runWithEmptySource()
                .map(TargetSet::getHeader)
                .orElse(Target), Header);
    }

    @Test
    void generated_target() throws IOException {
        assertEquals(runWithEmptySource()
                .map(TargetSet::getTarget)
                .orElse(Header), Target);
    }

    @Test
    void generated_target_header() throws IOException {
        runPresent();
        assertTrue(Files.exists(Header));
    }

    @Test
    void generated_target_source() throws IOException {
        runPresent();
        assertTrue(Files.exists(Target));
    }

    @Test
    void invalid() {
        assertThrows(ApplicationException.class, () -> {
            Files.writeString(Source, "test");
            runApplication();
        });
    }

    private Option<TargetSet> runApplication() throws IOException, ApplicationException {
        return new Application(ApplicationTest.Source).run();
    }

    @Test
    void target_header_content() throws IOException {
        assertContains(Header, "", "");
    }

    @Test
    void not_generated() throws IOException, ApplicationException {
        assertFalse(runApplication().isPresent());
    }

    @Test
    void target_source_content() throws IOException {
        assertContains(Target, "", new Function("").render());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Target);
        Files.deleteIfExists(Header);
        Files.deleteIfExists(Source);
    }
}
