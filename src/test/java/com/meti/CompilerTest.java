package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CompilerTest {
    @Test
    void default_function() {
        assertCompiles("def empty() : Void => {}", "void empty(void* __self__){" +
                "struct _index_* this=(struct _index_*) self;}" +
                "struct _index_ __index__(){" +
                "struct _index_ this={};" +
                "return this;}");
    }

    @Test
    void another_type() {
        assertCompiles("def test() : I16 => {}", "int test(void* __self__){" +
                "struct _index_* this=(struct _index_*) self;}" +
                "struct _index_ __index__(){" +
                "struct _index_ this={};" +
                "return this;}");
    }

    private void assertCompiles(String input, String output) {
        try {
            var compiler = new Compiler();
            var compiled = compiler.compile("index", input);
            var source = compiled.getSourceContent();
            assertEquals(output, source);
        } catch (ApplicationException e) {
            fail(e);
        }
    }

    @Test
    void another_name() {
        assertCompiles("def test() : Void => {}", "void test(void* __self__){" +
                "struct _index_* this=(struct _index_*) self;}" +
                "struct _index_ __index__(){" +
                "struct _index_ this={};" +
                "return this;}");
    }
}