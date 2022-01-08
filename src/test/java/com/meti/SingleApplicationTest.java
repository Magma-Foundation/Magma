package com.meti;

import com.meti.io.NIOPath;
import com.meti.module.SingleModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.meti.Application.Out;
import static org.junit.jupiter.api.Assertions.*;

public class SingleApplicationTest {
    private static final NIOPath Source = NIOPath.Root.resolveChild("index.mg");
    private static final NIOPath Target = Out.resolveChild("index.c");

    @Test
    void creates_target() throws IOException {
        Source.createAsFile();
        runImpl();
        assertTrue(Target.exists());
    }

    private void runImpl() {
        try {
            new BuildingApplication(new SingleModule(Source)).run();
        } catch (ApplicationException e) {
            fail(e);
        }
    }

    @Test
    void does_not_create_target() {
        runImpl();
        assertFalse(Target.exists());
    }

    @Test
    void empty() throws IOException {
        Source.createAsFile();
        runImpl();
        assertTrue(Target.existing()
                .map(NIOPath::readAsString)
                .map(String::isBlank)
                .orElse(false));
    }

    @AfterEach
    void tearDown() throws IOException {
        Source.delete();
        Out.deleteAsDirectory();
    }

    @Test
    void test_main() throws IOException {
        Source.createAsFile().writeAsString("def main() : I16 => {return 0;}");
        runImpl();
        assertEquals("int main(){return 0;}", Target.existing()
                .map(NIOPath::readAsString)
                .orElse(""));
    }
}
