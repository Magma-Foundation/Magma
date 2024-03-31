package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Compiler.*;
import static com.meti.FeatureTest.TEST_SYMBOL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterfaceFeatureTest {
    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleInterface(String name) {
        assertEquals(new MagmaTraitNode("", name, "{\n}").render(), compile(new JavaInterfaceNode("", name).renderJavaInterface()));
    }

    @Test
    void testPublic() {
        assertEquals(new MagmaTraitNode(Lang.EXPORT_KEYWORD, TEST_SYMBOL, "{\n}").render(), compile(new JavaInterfaceNode(Lang.PUBLIC_KEYWORD, TEST_SYMBOL).renderJavaInterface()));
    }
}
