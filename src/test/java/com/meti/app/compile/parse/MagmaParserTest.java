package com.meti.app.compile.parse;

import com.meti.api.collect.java.JavaList;
import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.common.Definition;
import com.meti.app.compile.common.ValuedField;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.common.integer.IntegerNode;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.stage.CompileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MagmaParserTest {
    @Test
    void redefined_after_blocks() throws CompileException, StreamException {
        var type = new IntegerType(true, 16);
        var value = new IntegerNode(100);
        var identity = new ValuedField("x", type, value);
        var definition = new Definition(identity);

        var input = List.apply(new Block(definition), definition);
        var output = new MagmaParsingStage(input).visit();

        var expected = JavaList.toNativeList(input);
        var actual = JavaList.toNativeList(output);

        assertIterableEquals(expected, actual);
    }

    @Test
    void redefined_inside_blocks() {
        var type = new IntegerType(true, 16);
        var value = new IntegerNode(100);
        var identity = new ValuedField("x", type, value);
        var definition = new Definition(identity);

        var input = List.apply(definition, new Block(definition));
        var parser = new MagmaParsingStage(input);

        assertThrows(CompileException.class, parser::visit);
    }
}