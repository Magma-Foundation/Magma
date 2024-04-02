package com.meti;

import com.meti.java.JavaInterfaceNode;
import com.meti.node.MapNode;
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
        assertEquals(MapNode.Builder("trait")
                .string("prefix", "")
                .string("name", name)
                .string("content", "{\n}")
                .build().render(), compile(new JavaInterfaceNode("", name).renderJavaInterface()));
    }

    @Test
    void testPublic() {
        assertEquals(MapNode.Builder("trait")
                .string("prefix", Lang.EXPORT_KEYWORD_WITH_SPACE)
                .string("name", TEST_SYMBOL)
                .string("content", "{\n}")
                .build().render(), compile(new JavaInterfaceNode(Lang.PUBLIC_KEYWORD, TEST_SYMBOL).renderJavaInterface()));
    }
}
