package com.meti;

import java.util.Collections;

public class XMLParser {
    public static Node parse(String input) {
        return new MapNode(input.substring(1, input.length() - 2), Collections.emptyMap());
    }
}
