package com.meti.app.compile;

import com.meti.api.collect.CollectionException;
import com.meti.api.collect.java.List;
import com.meti.app.compile.clang.CFormat;
import com.meti.app.compile.common.integer.IntegerNode;
import com.meti.app.compile.stage.CDivider;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.source.Packaging;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MappedDividerTest {

    @Test
    void divide() throws CollectionException, CompileException {
        var list = new CDivider(new Packaging(""))
                .divide(new IntegerNode(10))
                .apply(CFormat.Source)
                .foldRight(List.createList(), List::add);
        assertEquals(2, list.size());
    }
}