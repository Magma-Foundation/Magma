package com.meti;

import static com.meti.Lang.IMPORT_KEYWORD;

public class JavaLang {
    public static final String PACKAGE_KEYWORD = "package";

    static String renderJavaImport(String parent, String child) {
        return IMPORT_KEYWORD + parent + "." + child + ";";
    }

    static String renderPackage(String namespace) {
        return PACKAGE_KEYWORD + " " + namespace + ";";
    }

    public static String renderJavaClass(String name) {
        return Lang.CLASS_KEYWORD + name + Lang.CONTENT;
    }
}
