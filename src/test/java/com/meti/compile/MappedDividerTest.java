package com.meti.compile;

import com.meti.collect.CollectionException;
import com.meti.collect.JavaList;
import com.meti.compile.clang.CFormat;
import com.meti.compile.common.integer.IntegerNode;
import com.meti.compile.node.Node;
import com.meti.source.Packaging;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MappedDividerTest {

    @Test
    void divide() throws CollectionException, CompileException {
        var list = new CDivider(new Packaging(""))
                .divide(new IntegerNode(10))
                .apply(CFormat.Source)
                .foldRight(new JavaList<Node>(), JavaList::add);
        assertEquals(2, list.size());
    }
}