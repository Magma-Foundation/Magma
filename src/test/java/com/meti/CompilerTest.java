package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {

    @Test
    void compile() throws ApplicationException {
        var compiler = new Compiler();
        var output = compiler.compile("index", "def empty() : Void => {}");
        var source = output.getSourceContent();
        assertEquals("void empty(void* __self__){" +
                "struct _index_* this=(struct _index_*) self;}" +
                "struct _index_ __index__(){" +
                "struct _index_ this={};" +
                "return this;}", source);
    }
}