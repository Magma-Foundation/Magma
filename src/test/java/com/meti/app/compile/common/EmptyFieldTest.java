package com.meti.app.compile.common;

import com.meti.api.json.JSONFormatter;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmptyFieldTest {
    @Test
    void toJSON() {
        var expected = """
                {
                \t"name" : test,
                \t"type" : {},
                \t"flags" : [
                \t\t"Const"
                \t]
                }""";

        var json = new EmptyField(
                new RootText("test"),
                new IntegerType(true, 16),
                Field.Flag.Const).toJSON();
        var actual = new JSONFormatter(json).toString();
        assertEquals(expected, actual);
    }
}