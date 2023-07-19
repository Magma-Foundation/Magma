package com.meti.app;

import com.meti.core.Results;
import com.meti.java.JavaString;
import com.meti.java.String_;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CompilerTest {
    private static void assertEmpty(String_ input) {
        try {
            assertTrue(compileImpl(input).isEmpty());
        } catch (CompileException e) {
            fail(e);
        }
    }

    private static String_ compileImpl(String_ input) throws CompileException {
        try {
            return Results.unwrap(new Compiler(input).compile());
        } catch (CompileException e) {
            return fail(e);
        }
    }

    private static void assertCompile(String value) {
        assertCompile(value, value);
    }

    private static void assertCompile(String input, String output) {
        try {
            var actual = compileImpl(JavaString.from(input)).unwrap();
            assertEquals(output, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void importCousins() {
        assertCompile("import grand.parent.Child;import grand.pibling.Cousin",
                "import {{ Child } from parent, { Cousin } from pibling } from grand");
    }

    @Test
    void importSameGrandParent() {
        assertCompile("import parent.Child;import grand.parent.Sibling",
                "import { Child, Sibling } from grand.parent");
    }

    @Test
    void importSameParent() {
        assertCompile("import parent.Child;import parent.Sibling",
                "import { Child, Sibling } from parent");
    }

    @Test
    void multiple() {
        assertCompile("import first;import second", "import first;import second");
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void importWithGrandParent(String_ parent) {
        assertCompile("import " + parent + ".foo.test", "import { test } from " + parent + ".foo");
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void importWithParent(String_ parent) {
        assertCompile("import " + parent + ".test", "import { test } from " + parent);
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testImport(String_ name) {
        assertCompile("import " + name);
    }

    @Test
    void empty() {
        assertEmpty(JavaString.Empty);
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testPackage(String_ name) {
        assertEmpty(JavaString.from("package %s;".formatted(name)));
    }
}