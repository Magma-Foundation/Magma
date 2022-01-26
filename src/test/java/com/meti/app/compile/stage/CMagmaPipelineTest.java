package com.meti.app.compile.stage;

import com.meti.api.collect.java.JavaList;
import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.cache.Cache;
import com.meti.app.compile.common.Fields;
import com.meti.app.compile.common.Implementation;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.node.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class CMagmaPipelineTest {
    /*
    def outer() => {
        def inner() => {
        }
    }

    struct outer_t {
    }

    void inner_outer(void* self) {
        struct outer_t* this = self;
    }

    void outer() {
        struct outer_t this;
    }
     */
    @Test
    void inner_function() throws CompileException, StreamException {
        var identityBuilder = new Fields.Builder();
        var innerIdentity = identityBuilder.withName("inner").build();
        var outerIdentity = identityBuilder.withName("outer").build();

        var inner = new Implementation(innerIdentity, new Block());
        var outer = new Implementation(outerIdentity, new Block(inner));

        var first = new Implementation(outerIdentity, new Block());
        var expected = java.util.List.of(new Cache(first), 3);
        var actual = JavaList.toNativeList(new CMagmaPipeline(outer)
                .pipe()
                .foldRight(List.<Node>createList(), List::add));
        assertIterableEquals(expected, actual);
    }
}