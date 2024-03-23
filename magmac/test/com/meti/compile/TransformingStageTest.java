package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.compile.node.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static com.meti.java.JavaString.from;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransformingStageTest {
    @Test
    void test() throws CompileException {
        var input = new MapNode(from("class"), new JavaMap<>(Map.of(
                from("flags"), new StringListAttribute(JavaList.from()),
                from("name"), new StringAttribute(from("IntentionalException")),
                from("body"), new NodeAttribute(new MapNode(from("block"), new JavaMap<>(Collections.emptyMap()))),
                from("superclass"), new StringAttribute(from("Exception")))));


        var expected = new MapNode(from("class"), new JavaMap<>(Map.of(
                from("flags"), new StringListAttribute(JavaList.from()),
                from("name"), new StringAttribute(from("IntentionalException")),
                from("body"), new NodeAttribute(new Content("{}", 0)),
                from("superclass"), new StringAttribute(from("Exception")))));
        var actual = TransformingStage.transformAST(input).$();
        assertEquals(expected, actual);
    }
}