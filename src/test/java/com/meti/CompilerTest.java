package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {

    @Test
    void test_function() throws ApplicationException {
        var compiler = new Compiler();
        var output = compiler.compile("index", "def empty() : Void => {}");
        var source = output.getSourceContent();
        assertEquals("void empty(void* __self__){" +
                "struct _index_* this=(struct _index_*) self;}" +
                "struct _index_ __index__(){" +
                "struct _index_ this={};" +
                "return this;}", source);
    }

    @Test
    void test_another_function() throws ApplicationException {
        var compiler = new Compiler();
        var output = compiler.compile("index", "def test() : Void => {}");
        var source = output.getSourceContent();
        assertEquals("void test(void* __self__){" +
                "struct _index_* this=(struct _index_*) self;}" +
                "struct _index_ __index__(){" +
                "struct _index_ this={};" +
                "return this;}", source);
    }
}