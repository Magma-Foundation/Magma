package com.meti;

import com.meti.node.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class ClassTest extends FeatureTest {
    @Test
    void simple() {
        assertCompile(new MapNode("class", Map.of(
                        "name", new StringAttribute("Test"),
                        "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{}))))),
                        "flags", new ObjectListAttribute(List.of(new Flag[]{}))
                )),
                new MapNode("implementation", Map.of(
                        "name", new StringAttribute("Test"),
                        "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{}))))),
                        "flags", new ObjectListAttribute(List.of(Flag.Class))
                )));
    }

    @Test
    void simpleAnother() {
        assertCompile(new MapNode("class", Map.of(
                        "name", new StringAttribute("Bar"),
                        "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{}))))),
                        "flags", new ObjectListAttribute(List.of(new Flag[]{}))
                )),
                new MapNode("implementation", Map.of(
                        "name", new StringAttribute("Bar"),
                        "body", new NodeAttribute(new MapNode("block", Map.of("children", new NodeListAttribute(List.of(new Node[]{}))))),
                        "flags", new ObjectListAttribute(List.of(Flag.Class))
                )));
    }

}
