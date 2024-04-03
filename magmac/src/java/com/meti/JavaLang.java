package com.meti;

import static com.meti.Lang.IMPORT_KEYWORD;

public class JavaLang {
    public static final String PACKAGE_KEYWORD = "package";
    public static final String STATIC_KEYWORD = "static ";
    public static final String PUBLIC_KEYWORD = "public ";

    static String renderJavaImport(String parent, String child) {
        return renderJavaImport("", parent, child);
    }

    static String renderJavaImport(String staticSlice, String parent, String child) {
        return IMPORT_KEYWORD + staticSlice + parent + "." + child + ";";
    }

    static String renderPackage(String namespace) {
        return PACKAGE_KEYWORD + " " + namespace + ";";
    }

    public static String renderJavaClass(String name) {
        return renderJavaClass("", name);
    }

    public static String renderJavaClass(String publicString, String name) {
        return publicString + Lang.CLASS_KEYWORD + name + Lang.CONTENT;
    }
}
