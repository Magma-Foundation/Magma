package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Set;

public class MethodFeatureTest extends FeatureTest {
    @Test
    void test() {
        var method = new Method("empty");
        var function = new Implementation("empty", new Block());

        assertCompile(new JavaClass("Test", new Block(method)),
                new Implementation("Test", new Block(function), Definition.Flag.Class));
    }
}
