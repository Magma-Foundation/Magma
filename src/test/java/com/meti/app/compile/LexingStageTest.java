package com.meti.app.compile;

import org.junit.jupiter.api.Test;

import static com.meti.java.JavaString.fromSlice;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LexingStageTest {
    @Test
    void process() throws CompileException {
        var actual = new LexingStage().perform(fromSlice("void test(){}")).$();
        assertEquals("{name: 'method', attributes: {keywords: [], name: 'test', returns: 'void', parameters: [], body: {name: 'block', attributes: {lines: []}}}}",
                actual.toString());
    }
}