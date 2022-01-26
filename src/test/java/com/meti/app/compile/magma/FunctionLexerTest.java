package com.meti.app.compile.magma;

import com.meti.api.collect.java.List;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.Field;
import com.meti.app.compile.common.Fields;
import com.meti.app.compile.common.Implementation;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.meti.app.compile.node.EmptyNode.EmptyNode_;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FunctionLexerTest {
    @Test
    void body() throws CompileException {
        var input = new RootText("def main() : I16 => {return 0;}");
        var output = new FunctionLexer(input)
                .process()
                .orElse(EmptyNode_);
        var expected = new InputNode(new RootText("{return 0;}"));
        var actual = output.apply(Attribute.Type.Value).asNode();
        assertEquals(expected, actual);
    }

    @Test
    void invalid() throws CompileException {
        assertFalse(new FunctionLexer(new RootText("test(() => {})"))
                .process()
                .isPresent());
    }

    @Test
    void lambda() throws CompileException {
        var node = new FunctionLexer(new RootText("() => {}"))
                .process();
        var identity = new EmptyField(new RootText(""), ImplicitType.ImplicitType_, List.createList());
        var expected = new Implementation(identity, new InputNode(new RootText("{}")), List.createList());
        var actual = node.orElse(EmptyNode_);
        assertEquals(expected, actual);
    }

    @Test
    void simple() throws CompileException {
        var identity = new Fields.Builder()
                .withName("main")
                .withType("I16")
                .withFlag(Field.Flag.Def)
                .build();
        var body = new InputNode(new RootText("{return 0;}"));
        var expected = new Implementation(identity, body);
        new FunctionLexer(new RootText("def main() : I16 => {return 0;}"))
                .process()
                .ifPresentOrElse(actual -> assertEquals(expected, actual), Assertions::fail);
    }
}