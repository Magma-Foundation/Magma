package com.meti;

import java.util.*;
import java.util.stream.Collectors;

public class Compiler {
    public static final String IMPORT_KEYWORD_WITH_SPACE = "import ";
    public static final String STATEMENT_END = ";";
    public static final String CLASS_KEYWORD_WITH_SPACE = "class ";
    public static final String CLASS_CONTENT = " {}";
    public static final String PUBLIC_KEYWORD_WITH_SPACE = "public ";
    public static final String EXPORT_KEYWORD_WITH_SPACE = "export ";

    static String renderImport(String importString) {
        return IMPORT_KEYWORD_WITH_SPACE + importString + STATEMENT_END;
    }

    static String renderMagmaFunction(String modifierString, String name) {
        return modifierString + CLASS_KEYWORD_WITH_SPACE + "def " + name + "() =>" + CLASS_CONTENT;
    }

    record ImportChildren(Optional<String> defaultValue, List<String> children) {
        private ImportChildren withDefault(String defaultValue) {
            return new ImportChildren(Optional.of(defaultValue), this.children);
        }
    }

    static String compile(String input) {
        var lines = input.split(";");

        var roots = new HashMap<String, ImportChildren>();

        for (int i = 0; i < lines.length - 1; i++) {
            String line = lines[i];
            var option = compileStatement(line);
            if (option.isEmpty()) continue;

            var value = option.get();
            if (value instanceof MagmaImport node) {
                var parent = node.parent();
                var children = node.children();

                ensureKey(roots, parent);

                roots.get(parent).children.addAll(children);
            } else if (value instanceof Import node) {
                ensureKey(roots, node.name());

                roots.put(node.name(), roots.get(node.name()).withDefault(node.name()));
            } else {
                throw new UnsupportedOperationException("Unknown node type: " + value.getClass());
            }
        }


        var combinedLines = roots.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new MagmaImport(entry.getKey(), entry.getValue().defaultValue, entry.getValue().children()))
                .map(MagmaImport::render)
                .collect(Collectors.joining());

        return combinedLines + compileClass(lines[lines.length - 1]);
    }

    private static void ensureKey(HashMap<String, ImportChildren> map, String key) {
        if (!map.containsKey(key)) {
            map.put(key, new ImportChildren(Optional.empty(), new ArrayList<>()));
        }
    }

    private static Optional<Node> compileStatement(String before) {
        if (!before.startsWith(IMPORT_KEYWORD_WITH_SPACE)) return Optional.empty();

        var total = before.substring(IMPORT_KEYWORD_WITH_SPACE.length());
        var parentSeparator = total.lastIndexOf('.');
        if (parentSeparator == -1) {
            return Optional.of(new Import(total));
        } else {
            var parent = total.substring(0, parentSeparator);
            var child = total.substring(parentSeparator + 1);
            return Optional.of(new MagmaImport(parent, child));
        }
    }

    private static String compileClass(String input) {
        var nameStart = input.indexOf(CLASS_KEYWORD_WITH_SPACE) + CLASS_KEYWORD_WITH_SPACE.length();
        var nameEnd = input.indexOf(CLASS_CONTENT);
        var name = input.substring(nameStart, nameEnd);

        var modifierString = input.startsWith(PUBLIC_KEYWORD_WITH_SPACE) ? EXPORT_KEYWORD_WITH_SPACE : "";
        return renderMagmaFunction(modifierString, name);
    }

    public static String renderMagmaFunction(String name) {
        return renderMagmaFunction("", name);
    }
}