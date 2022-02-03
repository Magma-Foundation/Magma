package com.meti.app.compile.feature.scope;

import com.meti.app.compile.parse.Scope;
import com.meti.app.compile.parse.State;
import com.meti.app.compile.primitive.Primitive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VariableParserTest {
    @Test
    void undefined() {
        var root = new Variable("test");
        var state = new State(root);

        var visitor = new VariableParser(state);
        assertThrows(UndefinedException.class, visitor::modifyBeforeASTImpl);
    }

    @Test
    void defined() {
        var definition = new Declaration("test", Primitive.Bool);
        var scope = new Scope().define(definition);

        var root = new Variable("test");
        var state = new State(root, scope);

        var visitor = new VariableParser(state);
        assertDoesNotThrow(visitor::modifyBeforeASTImpl);
    }
}