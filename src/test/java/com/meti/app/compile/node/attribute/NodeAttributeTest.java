package com.meti.app.compile.node.attribute;

import com.meti.app.compile.feature.function.Return;
import com.meti.app.compile.feature.scope.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeAttributeTest {
    @Test
    void test_toString() {
        var inner = new Variable("test");
        var outer = new Return(inner);

        var expected = """
                {
                \t"node":{
                \t\t"returns" : {
                \t\t\t"value" : "test"
                \t\t}
                \t}
                }""";
        var actual = new NodeAttribute(outer).toString();

        assertEquals(expected, actual);
    }
}