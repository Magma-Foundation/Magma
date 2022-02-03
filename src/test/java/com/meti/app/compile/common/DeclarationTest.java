package com.meti.app.compile.common;

import com.meti.api.json.JSONException;
import com.meti.api.json.JSONFormatter;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.feature.scope.Declaration;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeclarationTest {
    @Test
    void toJSON() throws JSONException {
        var expected = """
                {
                \t"name" : "test",
                \t"category" : {
                \t\t"bits" : 16,
                \t\t"signed" : true
                \t},
                \t"flags" : [
                \t\t"Const"
                \t]
                }""";

        var json = new Declaration(
                new RootText("test"),
                new IntegerType(true, 16),
                Definition.Flag.Const).toJSON();
        var actual = new JSONFormatter(json).toString();
        assertEquals(expected, actual);
    }
}