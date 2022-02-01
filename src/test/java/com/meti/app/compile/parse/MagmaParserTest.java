package com.meti.app.compile.parse;

import com.meti.api.collect.java.JavaList;
import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.common.ValuedField;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.common.integer.IntegerNode;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.stage.CompileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class MagmaParserTest {
    @Test
    void redefined_blocks() throws CompileException, StreamException {
        var type = new IntegerType(true, 16);
        var value = new IntegerNode(100);
        var identity = new ValuedField("x", type, value);

        var input = List.apply(new Block(identity), identity);
        var output = new MagmaParser(input).parse();

        var expected = JavaList.toNativeList(input);
        var actual = JavaList.toNativeList(output);

        assertIterableEquals(expected, actual);
    }
}