package com.meti.app.compile;

import com.meti.app.compile.cache.Cache;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.Implementation;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.common.variable.Variable;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.node.RootText;
import org.junit.jupiter.api.Test;

import static com.meti.app.compile.node.EmptyNode.EmptyNode_;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CFlattenerTest {
    @Test
    void five() throws CompileException {
        var innerIdentity = new EmptyField(new RootText("inner"), Primitive.Void);
        var innerBody = new Block();
        var inner = new Implementation(innerIdentity, innerBody);

        var outerIdentity = new EmptyField(new RootText("outer"), Primitive.Void);
        var outerBody = new Block(new Cache(EmptyNode_, inner));
        var outer = new Implementation(outerIdentity, outerBody);
        var input = new Cache(EmptyNode_, outer);

        var outerCopy = new Implementation(outerIdentity, new Block(EmptyNode_));

        var expected = new Cache(EmptyNode_, outerCopy, inner);
        var actual = new CFlattener().transformNodeAST(input);
        assertEquals(expected, actual);
    }

    @Test
    void four() throws CompileException {
        var innerIdentity = new EmptyField(new RootText("inner"), Primitive.Void);
        var innerBody = new Block();
        var inner = new Implementation(innerIdentity, innerBody);

        var outerIdentity = new EmptyField(new RootText("outer"), Primitive.Void);
        var outerBody = new Block(new Cache(EmptyNode_, inner));
        var outer = new Implementation(outerIdentity, outerBody);

        var expectedBody = new Block(EmptyNode_);
        var expectedImpl = new Implementation(outerIdentity, expectedBody);
        var expected = new Cache(expectedImpl, inner);
        var actual = new CFlattener().transformNodeAST(outer);
        assertEquals(expected, actual);
    }

    @Test
    void inner_cache() throws CompileException {
        var inner = new Cache(new Variable("first"), new Variable("second"));
        var outer = new Cache(inner, new Variable("third"));

        var actual = new CFlattener().transformNodeAST(outer);
        var expected = new Cache(new Variable("first"),
                new Variable("third"),
                new Variable("second"));
        assertEquals(expected, actual);
    }

    @Test
    void once() throws CompileException {
        var input = new Block(new Cache(EmptyNode_, new Variable("test")));
        var actual = new CFlattener().transformNodeAST(input);
        var expected = new Cache(new Block(EmptyNode_), new Variable("test"));
        assertEquals(expected, actual);
    }

    @Test
    void three() throws CompileException {
        var body = new Block(new Variable("test"));
        var identity = new EmptyField(new RootText("wrapper"), Primitive.Void);
        var cached = new Variable("cached");
        var impl = new Implementation(identity, body);

        var input = new Block(new Cache(impl, cached));
        var actual = new CFlattener().transformNodeAST(input);

        var output = new Block(impl);
        var expected = new Cache(output, cached);
        assertEquals(expected, actual);
    }

    @Test
    void twice() throws CompileException {
        var body = new Block(new Variable("test"));
        var identity = new EmptyField(new RootText("wrapper"), Primitive.Void);
        var cached = new Variable("cached");

        var input = new Implementation(identity, new Cache(body, cached));
        var actual = new CFlattener().transformNodeAST(input);

        var output = new Implementation(identity, body);
        var expected = new Cache(output, cached);
        assertEquals(expected, actual);
    }
}