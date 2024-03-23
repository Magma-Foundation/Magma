package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.option.IntentionalException;
import com.meti.compile.node.*;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LexingStageTest {
    @Test
    void test() throws CompileException, IntentionalException {
        var actual = new LexingStage()
                .lexExpression(JavaString.from("class IntentionalException extends Exception {}"), 0)
                .$()
                .first()
                .$();

        var expected = new MapNode(JavaString.from("class"), new JavaMap<>(Map.of(
                JavaString.from("flags"), new StringListAttribute(JavaList.from()),
                JavaString.from("name"), new StringAttribute(JavaString.from("IntentionalException")),
                JavaString.from("body"), new NodeAttribute(new Content("{}", 0)),
                JavaString.from("superclass"), new StringAttribute(JavaString.from("Exception")))));
        assertEquals(expected, actual);
    }
}