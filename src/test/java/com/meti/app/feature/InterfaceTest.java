package com.meti.app.feature;

import org.junit.jupiter.api.Test;

public class InterfaceTest extends CompiledTest {
    @Test
    void defaultMethod() {
        assertCompile("interface Test {" +
                      "default void empty(){}" +
                      "}", "trait Test {" +
                           "def empty() : Void;" +
                           "}");
    }

    @Test
    void public_() {
        assertCompile("public interface Test {}", "trait Test {}");
    }

    @Test
    void generic() {
        assertCompile("interface Test<T> {}", "trait Test<T>{}");
    }

    @Test
    void test() {
        assertCompile("interface Test {}", "trait Test {}");
    }
}
