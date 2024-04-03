package com.meti;

import static com.meti.MagmaCompiler.IMPORT_KEYWORD;
import static com.meti.MagmaCompiler.PACKAGE_KEYWORD;

public class JavaLang {
    static String renderJavaImport(String parent, String child) {
        return IMPORT_KEYWORD + parent + "." + child + ";";
    }

    static String renderPackage(String namespace) {
        return PACKAGE_KEYWORD + " " + namespace + ";";
    }
}
