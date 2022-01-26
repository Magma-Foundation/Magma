package com.meti.app.compile.common;

import com.meti.api.json.JSONException;
import com.meti.api.json.JSONFormatter;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImplementationTest {
    @Test
    void toJSON() throws JSONException {
        var expected = """
                {
                \t"body" : {
                \t\t"children" : []
                \t},
                \t"parameters" : [],
                \t"identity" : {
                \t\t"name" : "test",
                \t\t"type" : {
                \t\t\t"value" : "Void"
                \t\t},
                \t\t"flags" : [
                \t\t\t"Def"
                \t\t]
                \t}
                }""";
        var identity = new EmptyField(new RootText("test"), Primitive.Void, Field.Flag.Def);
        var root = new Implementation(identity, new Block());
        var actual = new JSONFormatter(root.toJSON()).toString();
        assertEquals(expected, actual);
    }
}