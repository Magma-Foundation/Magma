package com.meti.app.compile.cache;

import com.meti.api.json.JSONException;
import com.meti.api.json.JSONFormatter;
import com.meti.app.compile.common.integer.IntegerNode;
import com.meti.app.compile.feature.scope.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CacheTest {
    @Test
    void toJSON() throws JSONException {
        var expected = """
                {
                \t"value" : {
                \t\t"value" : "first"
                \t},
                \t"children" : [
                \t\t{
                \t\t\t"value" : 10
                \t\t}
                \t]
                }""";
        var root = new Cache(new Variable("first"), new IntegerNode(10));
        var actual = new JSONFormatter(root.toJSON()).toString();
        assertEquals(expected, actual);
    }
}