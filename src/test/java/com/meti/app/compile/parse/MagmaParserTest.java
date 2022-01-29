package com.meti.app.compile.parse;

import com.meti.api.collect.java.JavaList;
import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.common.Fields;
import com.meti.app.compile.common.Implementation;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.stage.CompileException;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class MagmaParserTest {
    @Test
    void test() throws CompileException, StreamException {
        var identityBuilder = new Fields.Builder();
        var innerIdentity = identityBuilder.withName("inner").build();
        var outerIdentity = identityBuilder.withName("outer").build();

        var inner = new Implementation(innerIdentity, new Block());
        var outer = new Implementation(outerIdentity, new Block(inner));

        var expected = Collections.emptyList();
        var actual = JavaList.toNativeList(new MagmaParser(List.apply(outer)).parse());

        assertIterableEquals(expected, actual);
    }
}