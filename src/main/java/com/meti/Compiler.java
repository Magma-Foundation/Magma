package com.meti;

import com.meti.feature.Import;
import com.meti.feature.IntegerNode;
import com.meti.feature.Node;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Compiler(String input) {
    public static final String ImportNativePrefix = "import native ";

    public static Output renderC(Node node) {
        var value = node.apply(Attribute.Type.Value);
        if (node.is(Import.Type.Import)) {
            var format = "#include <%s.h>\n";
            var formatted = format.formatted(value.asString());
            return new StringOutput(formatted);
        } else if (node.is(Node.Type.Return)) {
            var stringOutput = renderC(value.asNode());
            var format = "return %s;";
            var formatted = format.formatted(stringOutput.compute());
            return new StringOutput(formatted);
        } else {
            return new StringOutput(String.valueOf(value.asInteger()));
        }
    }

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

    private String compileLine(String line) {
        return renderC(lexLine(line)).compute();
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