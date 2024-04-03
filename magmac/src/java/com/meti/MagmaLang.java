package com.meti;

import static com.meti.Lang.*;

public class MagmaLang {
    public static final String EXPORT_KEYWORD = "export ";

    public static String renderImport(String parent, String child) {
        return renderImportWithChildString(parent, renderImportChildString(child));
    }

    public static String renderImportChildString(String child) {
        return "{ " + child + " }";
    }

    public static String renderImportWithChildString(String parent, String childString) {
        return IMPORT_KEYWORD + childString + " " + parent + ";";
    }

    public static String renderMagmaFunction(String name) {
        return renderMagmaFunction("", name, "", EMPTY_CONTENT);
    }

    public static String renderMagmaFunction(String exportString, String name, String parameters, String content) {
        return exportString + CLASS_KEYWORD + "def " + name + "(" + parameters + ") => " + content;
    }
}
