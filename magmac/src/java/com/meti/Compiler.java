package com.meti;

import java.util.ArrayList;
import java.util.Optional;

public class Compiler {
    public static final String IMPORT_KEYWORD = "import ";
    public static final String STATIC_KEYWORD = "static ";
    public static final String IMPORT_STATIC = IMPORT_KEYWORD + STATIC_KEYWORD;
    public static final String PUBLIC_KEYWORD = "public ";
    public static final String EXPORT_KEYWORD = "export ";
    public static final String CLASS_KEYWORD = "class ";

    static String compile(String input) {
        var args = input.split(";");
        var list = new ArrayList<String>();
        for (String arg : args) {
            list.add(compileRootStatement(arg.strip()));
        }
        return String.join("", list);
    }

    private static String compileRootStatement(String input) {
        return compileImport(input)
                .or(() -> compileClass(input))
                .orElse("");
    }

    private static Optional<String> compileClass(String input) {
        if (!input.contains(CLASS_KEYWORD)) return Optional.empty();
        var isPublic = input.startsWith(PUBLIC_KEYWORD);
        var nameStart = input.indexOf(CLASS_KEYWORD) + CLASS_KEYWORD.length();

        var bodyStart = input.indexOf('{');
        var bodyEnd = input.lastIndexOf('}');

        var name = input.substring(nameStart, bodyStart).strip();
        var content = input.substring(bodyStart + 1, bodyEnd);
        return Optional.of(isPublic
                ? renderExportedMagmaClass(name, content)
                : renderMagmaClass(name, content));
    }

    private static Optional<String> compileImport(String input) {
        if (!input.startsWith(IMPORT_KEYWORD)) return Optional.empty();
        var isStatic = input.startsWith(IMPORT_STATIC);
        var importKeyword = isStatic ? IMPORT_STATIC : IMPORT_KEYWORD;
        var segmentsString = input.substring(importKeyword.length());

        var separator = segmentsString.lastIndexOf('.');
        var parent = segmentsString.substring(0, separator);
        var child = segmentsString.substring(separator + 1);

        return Optional.of(child.equals("*")
                ? renderMagmaImportForAllChildren(parent)
                : renderMagmaImport(parent, child));
    }

    static String renderMagmaImport(String parent, String child) {
        return renderMagmaImportWithChildString(parent, "{ " + child + " } from ");
    }

    static String renderMagmaImportForAllChildren(String parent) {
        return renderMagmaImportWithChildString(parent, "");
    }

    private static String renderMagmaImportWithChildString(String parent, String childString) {
        return "extern " + IMPORT_KEYWORD + childString + parent + ";\n";
    }

    static String renderMagmaClass(String name) {
        return renderMagmaClass(name, "");
    }

    static String renderMagmaClass(String name, String content) {
        return renderMagmaClass("", name, content);
    }

    static String renderExportedMagmaClass(String name) {
        return renderExportedMagmaClass(name, "");
    }

    static String renderExportedMagmaClass(String name, String content) {
        return renderMagmaClass(EXPORT_KEYWORD, name, content);
    }

    private static String renderMagmaClass(String prefix, String name, String content) {
        return prefix + CLASS_KEYWORD + "def " + name + "() => {" + content + "}";
    }

    static String renderJavaClass(String prefix, String name, String content) {
        return prefix + CLASS_KEYWORD + name + " {" + content + "}";
    }
}