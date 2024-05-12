package com.meti;

import java.util.Optional;

public class MagmaCompiler {
    static String compileMagmaRoot(String line, String targetExtension) throws CompileException {
        return compileMagmaImport(line, targetExtension)
                .or(() -> compileMagmaFunction(line, targetExtension))
                .orElseThrow(() -> new CompileException(line));
    }

    static Optional<String> compileMagmaFunction(String line, String targetExtension) {
        var stripped = line.strip();
        var def = stripped.indexOf("def ");
        if (def == -1) return Optional.empty();

        var isExported = stripped.startsWith("export ");
        var name = stripped.substring(def + "def ".length(), stripped.indexOf('(')).strip();
        var output = renderMagmaFunction(targetExtension, isExported, name);
        return Optional.of(output);
    }

    static String renderMagmaFunction(String targetExtension, boolean isExported, String name) {
        if (targetExtension.equals("js")) {
            var exportedString = isExported ? "module.exports = {\n\t" + name + "\n}\n" : "";
            return "function " + name + "(){\n\treturn {};\n}\n" + exportedString;
        } else if (targetExtension.equals("d.ts")) {
            return isExported ? "export function " + name + "() : {};" : "";
        } else {
            return transformMagmaFunctionToC(targetExtension, name, isExported);
        }
    }

    static String transformMagmaFunctionToC(String targetExtension, String name, boolean isExported) {
        var structType = "struct " + name + "_t";
        var structString = structType + " {\n}\n";
        if (targetExtension.equals("c")) {
            var structString1 = isExported ? "" : structString;
            return structString1 + structType + " " + name + "(){\n\t" + structType + " this;\n\treturn this;\n}";
        } else {
            return isExported ? structString + structType + " " + name + "();\n" : "";
        }
    }

    static Optional<String> compileMagmaImport(String line, String targetExtension) {
        var stripped = line.strip();
        if (!stripped.startsWith("import ")) return Optional.empty();

        var childStart = stripped.indexOf('{');
        var childEnd = stripped.indexOf('}');
        var child = stripped.substring(childStart + 1, childEnd).strip();
        var parent = stripped.substring(stripped.indexOf("from") + "from".length()).strip();
        var output = renderMagmaImport(targetExtension, child, parent);
        return Optional.of(output);
    }

    static String renderMagmaImport(String targetExtension, String child, String parent) {
        if (targetExtension.equals("js") || targetExtension.equals("d.ts")) {
            return "import { " + child + " } from \"" + parent + "\";\n";
        } else if (targetExtension.equals("h")) {
            return "#include <" + parent + "/" + child + ".h>\n";
        } else {
            return "";
        }
    }
}