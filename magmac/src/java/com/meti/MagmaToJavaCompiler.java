package com.meti;

import java.util.Optional;

import static com.meti.Compiler.createUnknownInput;

public class MagmaToJavaCompiler {
    static String compileMagmaToJava(String namespace, String magmaInput) throws CompileException {
        if (magmaInput.isEmpty()) {
            return JavaLang.renderJavaClass(namespace);
        }

        var namespace1 = compileMagmaLet(namespace, magmaInput);
        if (namespace1.isPresent()) return namespace1.get();

        throw createUnknownInput(magmaInput);
    }

    static Optional<String> compileMagmaLet(String namespace, String magmaInput) throws CompileException {
        if (!magmaInput.startsWith(MagmaLang.LET_KEYWORD)) return Optional.empty();

        var before = magmaInput.substring(MagmaLang.LET_KEYWORD.length(), magmaInput.indexOf('=')).strip();
        var separator = before.indexOf(':');

        String name;
        String outputType;
        if (separator == -1) {
            name = before;
            outputType = JavaLang.INT_TYPE;
        } else {
            var inputType = before.substring(separator + 1).strip();

            name = before.substring(0, separator).strip();
            outputType = compileMagmaType(inputType);
        }

        return Optional.of(JavaLang.renderJavaClass(namespace,
                Lang.renderBlock(
                        JavaLang.renderJavaDefinition(name, outputType)
                )
        ));
    }

    static String compileMagmaType(String inputType) throws CompileException {
        String outputType;
        if (inputType.equals(MagmaLang.I64)) {
            outputType = JavaLang.LONG_TYPE;
        } else {
            throw new CompileException("Unknown Magma type: " + inputType);
        }
        return outputType;
    }
}