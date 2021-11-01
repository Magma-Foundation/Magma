package com.meti.app.compile.feature;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.feature.FeatureTest.assertCompile;

public class FunctionTest {
    @Test
    void empty() {
        assertCompile("def empty() : Void => {}", "void empty(){}");
    }

    @Test
    void name() {
        assertCompile("def test() : Void => {}", "void test(){}");
    }

    @Test
    void parameter() {
        assertCompile("def wrapper(value : I16) : I16 => {return value;}", "int wrapper(int value){return value;}");
    }

    @Test
    void body() {
        assertCompile("def wrapper() : I16 => {return 420;}", "int wrapper(){return 420;}");
    }

    @Test
    void return_type() {
        assertCompile("def wrapper() : U16 => {return 420;}", "unsigned int(){return 420;}");
    }
}
