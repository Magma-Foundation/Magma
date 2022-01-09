package com.meti.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.meti.io.NIOPath.Root;
import static org.junit.jupiter.api.Assertions.*;

class NIOPathTest {
    private static final NIOPath Parent = Root.resolveChild("parent");

    @Test
    void hasExtensionOf() {
        var path = Root.resolveChild("test.ing");
        assertTrue(path.hasExtensionOf("ing"));
    }

    @Test
    void relativize() {
        var parent = Root.resolveChild("first");
        var child = parent.resolveChild("second").resolveChild("third.txt");
        var relativized = parent.relativize(child);
        var expected = Paths.get("second", "third.txt").toString();
        var actual = relativized.asString();
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() throws IOException {
        Parent.deleteAsDirectory();
    }

    @Test
    void walk() throws IOException {
        Parent.createAsDirectory();

        var child = Parent.resolveChild("child");
        child.createAsDirectory();

        var grandChild = child.resolveChild("grandChild");
        grandChild.createAsFile();

        var expected = List.of(Parent, child, grandChild);
        var actual = Parent.walk().collect(Collectors.toList());
        assertIterableEquals(expected, actual);
    }
}