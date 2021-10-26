package com.meti;

import com.meti.feature.Import;
import com.meti.feature.IntegerNode;
import com.meti.feature.Node;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Compiler(String input) {
    public static final String ImportNativePrefix = "import native ";

    public static String renderNativeImport(final String value) {
        return ImportNativePrefix + value;
    }

    public String compile() {
        if (input.isBlank()) return "";
        var lines = input.split(";");
        return Arrays.stream(lines)
                .map(this::compileLine)
                .collect(Collectors.joining());
    }

    private static Output renderTree(Node tree) {
        return new CRenderer(tree).render().mapNodes(Compiler::renderTree);
    }

    private String compileLine(String line) {
        return renderTree(lexLine(line))
                .asString()
                .orElse("");
    }

    private Node lexLine(String line) {
        if (line.startsWith(ImportNativePrefix)) {
            var value = slice(line, ImportNativePrefix, line.length());
            return new Import(value);
        } else if (line.startsWith("return ")) {
            var value = slice(line, "return ", line.length());
            return new Return(new IntegerNode(Integer.parseInt(value)));
        } else {
            return new IntegerNode(Integer.parseInt(line));
        }
    }

    private String slice(String line, String prefix, int end) {
        var prefixLength = prefix.length();
        var slice = line.substring(prefixLength, end);
        return slice.trim();
    }
}