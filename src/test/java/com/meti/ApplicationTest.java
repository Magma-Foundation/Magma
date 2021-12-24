package com.meti;

import com.meti.io.File;
import com.meti.io.PathWrapper;
import com.meti.option.None;
import com.meti.option.Option;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.meti.io.PathWrapper.Root;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private static final PathWrapper Source = Root.resolve("index.mgf");
    private static final PathWrapper Target = Root.resolve("index.c");

    @Test
    void content() throws IOException {
        Source.createAsFile().writeString("def main() : I16 => {return 0;}");
        assertEquals("int main(){return 0;}", run()
                .map(File::readString)
                .orElse(""));
    }

    @Test
    void does_not_create_target() {
        assertFalse(doesCreateTarget());
    }

    @Test
    void creates_proper_target() throws IOException {
        Source.createAsFile();
        assertTrue(run()
                .filter(value -> value.asPath().equals(Target))
                .isPresent());
    }

    @Test
    void creates_target() throws IOException {
        Source.createAsFile();
        assertTrue(doesCreateTarget());
    }

    private boolean doesCreateTarget() {
        return run().isPresent();
    }

    private Option<File> run() {
        try {
            return new Application(Source).run();
        } catch (ApplicationException e) {
            fail(e);
            return new None<>();
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Source.deleteIfExists();
        Target.deleteIfExists();
    }
}
