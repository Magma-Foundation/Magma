package com.meti.app.compile.transform;

import com.meti.app.compile.MapNode;
import com.meti.app.compile.Node;
import com.meti.app.compile.attribute.Attribute;
import com.meti.core.Option;
import com.meti.java.Map;
import com.meti.java.String_;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtractorTest {
    private static Option<Map<String_, Attribute>> extractImpl(Node left, Node right) {
        return new Extractor(left, right).extract();
    }

    @Test
    void invalid() {
        var left = MapNode.create("a").complete();
        var right = MapNode.create("b").complete();

        var extract = extractImpl(left, right);
        assertFalse(extract.isPresent());
    }

    @Test
    void sameType() {
        var left = MapNode.create("a").complete();
        var right = MapNode.create("a").complete();

        var extract = extractImpl(left, right);
        assertTrue(extract.isPresent());
    }
}