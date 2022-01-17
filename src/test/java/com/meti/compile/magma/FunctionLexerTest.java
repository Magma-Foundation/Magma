package com.meti.compile.magma;

import com.meti.collect.JavaList;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Implementation;
import com.meti.compile.common.block.Block;
import com.meti.compile.node.EmptyNode;
import com.meti.compile.node.Text;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionLexerTest {

    @Test
    void lambda() {
        var node = new FunctionLexer(new Text("() => {}"))
                .lex();
        var identity = new EmptyField(Collections.emptySet(), new Text(""), new ImplicitType());
        var expected = new Implementation(identity, Collections.emptySet(), new Block(new JavaList<>()));
        var actual = node.orElse(new EmptyNode());
        assertEquals(expected, actual);
    }
}