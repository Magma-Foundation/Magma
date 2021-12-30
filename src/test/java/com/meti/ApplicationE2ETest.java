package com.meti;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.meti.NIOPath.Root;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationE2ETest {
    private static final Path Target = Root.resolveChild("index.c");
    private static final Path Source = Root.resolveChild("index.mg");

    @Test
    void no_writes_target() throws ApplicationException {
        new Application(Source).run();
        assertFalse(Target.exists());
    }

    @AfterEach
    void tearDown() throws com.meti.IOException {
        Source.deleteIfExists();
        Target.deleteIfExists();
    }

    @Test
    void writes_content() throws IOException, ApplicationException {
        Source.create().writeAsString("def main() : I16 => {return 0;}");
        new Application(Source).run();
        assertEquals("int main(){return 0;}", Target.existing()
                .map(File::readAsString)
                .orElse(""));
    }

    @Test
    void writes_target() throws ApplicationException, com.meti.IOException {
        Source.create();
        new Application(Source).run();
        assertTrue(Target.exists());
    }
}
