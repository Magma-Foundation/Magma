package com.meti;

public class JavaToMagmaCompiler {
    static String compileJavaToMagma(String javaInput) throws CompileException {
        if (!javaInput.startsWith(JavaLang.CLASS_KEYWORD)) throw Compiler.createUnknownInput(javaInput);

        var contentStart = javaInput.indexOf('{');
        var contentEnd = javaInput.lastIndexOf('}');
        var contentString = javaInput.substring(contentStart + 1, contentEnd).strip();
        var separator = contentString.indexOf(';');
        if (separator == -1) return "";

        var fieldString = contentString.substring(0, separator).strip();
        if (fieldString.isEmpty()) return "";

        var valueSeparator = fieldString.indexOf('=');
        var before = fieldString.substring(0, valueSeparator).strip();
        var nameSeparator = before.lastIndexOf(' ');

        var type = before.substring(0, nameSeparator).strip();
        var name = before.substring(nameSeparator + 1).strip();

        var value = fieldString.substring(valueSeparator + 1).strip();

        if (type.equals(JavaLang.INT_TYPE)) {
            return MagmaLang.renderMagmaDefinitionWithTypeString(name, "", value);
        } else {
            return MagmaLang.renderMagmaDefinitionWithTypeString(name, " : " + MagmaLang.I64, value);
        }
    }
}