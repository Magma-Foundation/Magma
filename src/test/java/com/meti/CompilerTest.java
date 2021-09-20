package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {

    @Test
    void test_function() throws ApplicationException {
        assertCompiles("def empty() : Void => {}", "void empty(void* __self__){" +
                "struct _index_* this=(struct _index_*) self;}" +
                "struct _index_ __index__(){" +
                "struct _index_ this={};" +
                "return this;}");
    }

    private void assertCompiles(String input, String output) throws ApplicationException {
        var compiler = new Compiler();
        var compiled = compiler.compile("index", input);
        var source = compiled.getSourceContent();
        assertEquals(output, source);
    }

    @Test
    void test_another_function() throws ApplicationException {
        assertCompiles("def test() : Void => {}", "void test(void* __self__){" +
                "struct _index_* this=(struct _index_*) self;}" +
                "struct _index_ __index__(){" +
                "struct _index_ this={};" +
                "return this;}");
    }
}