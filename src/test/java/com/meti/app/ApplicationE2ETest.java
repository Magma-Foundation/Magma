package com.meti.app;

import com.meti.api.io.File;
import com.meti.api.io.IOException;
import com.meti.api.io.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.meti.api.io.NIOFile.Root;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationE2ETest {
    private static final Path Target = Root.resolveChild("index.c");
    private static final Path Source = Root.resolveChild("index.mg");

    @Test
    void no_writes_target() throws ApplicationException {
        new Application(new SingleSource(Source)).run();
        assertFalse(Target.exists());
    }

    @AfterEach
    void tearDown() throws IOException {
        Source.deleteIfExists();
        Target.deleteIfExists();
    }

    @Test
    void writes_content() throws IOException, ApplicationException {
        Source.create().writeAsString("def main() : I16 => {return 0;}");
        new Application(new SingleSource(Source)).run();
        assertEquals("int main(){return 0;}", Target.existing()
                .map(File::readAsString)
                .orElse(""));
    }

    @Test
    void writes_empty() throws IOException, ApplicationException {
        Source.create().writeAsString("");
        new Application(new SingleSource(Source)).run();
        assertEquals("", Target.existing()
                .map(File::readAsString)
                .orElse(""));
    }

    @Test
    void writes_target() throws ApplicationException, IOException {
        Source.create();
        new Application(new SingleSource(Source)).run();
        assertTrue(Target.exists());
    }
}
