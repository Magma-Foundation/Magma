package com.meti.app;

import com.meti.api.io.Directory;
import com.meti.api.io.Path;
import com.meti.app.module.DirectoryModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.meti.api.io.NIOPath.Root;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectoryApplicationTest {
    private static final Path Parent = Root.resolveChild("parent");
    private static final Path Out = Root.resolveChild("out");

    @AfterEach
    void tearDown() throws IOException {
        Parent.existingAsDirectory().ifPresent(Directory::deleteAsDirectory);
        Out.existingAsDirectory().ifPresent(Directory::deleteAsDirectory);
    }

    @Test
    void test() throws IOException, ApplicationException {
        var parent = Parent.ensureAsDirectory();
        var child = Parent.resolveChild("value.mg");
        if (!child.exists()) child.createAsFile().writeAsString("def main() : I16 => {return 0;}");

        var module = new DirectoryModule(parent);
        new Application(module).run();

        assertTrue(Out.resolveChild("value.c").exists());
    }
}
