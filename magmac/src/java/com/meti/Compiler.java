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

    static String compile(String input) {
        var lines = input.split(";");

        var rootImportsWithChildren = new HashMap<String, List<String>>();
        var rootImports = new HashSet<String>();

        for (int i = 0; i < lines.length - 1; i++) {
            String line = lines[i];
            var option = compileStatement(line);
            if (option.isEmpty()) continue;

            var value = option.get();
            if (value instanceof MagmaImport node) {
                var parent = node.parent();
                var child = node.child();

                ensureKey(rootImportsWithChildren, parent);

                rootImportsWithChildren.get(parent).addAll(child);
            } else if (value instanceof Import node) {
                rootImports.add(node.name());
            } else {
                throw new UnsupportedOperationException("Unknown node type: " + value.getClass());
            }
        }

        var parentSet = new HashSet<>(rootImportsWithChildren.keySet());
        parentSet.addAll(rootImports);

        var combinedLines = parentSet.stream()
                .sorted()
                .map(parent -> createImports(parent, rootImports, rootImportsWithChildren))
                .flatMap(Collection::stream)
                .map(Node::render)
                .collect(Collectors.joining());

        return combinedLines + compileClass(lines[lines.length - 1]);
    }

    private static void ensureKey(Map<String, List<String>> map, String key) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
    }

    private static List<Node> createImports(String parent, HashSet<String> rootImports, HashMap<String, List<String>> rootImportsWithChildren) {
        var outputImports = new ArrayList<Node>();
        if (rootImports.contains(parent)) outputImports.add(new Import(parent));
        if (rootImportsWithChildren.containsKey(parent))
            outputImports.add(new MagmaImport(parent, rootImportsWithChildren.get(parent)));

        return outputImports;
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