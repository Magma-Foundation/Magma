package com.meti;

public class MagmaLang {
    public static final String EXPORT_KEYWORD = "export ";

    public static String renderImport(String parent, String child) {
        return renderImportWithChildString(parent, renderImportChildString(child));
    }

    public static String renderImportChildString(String child) {
        return "{ " + child + " }";
    }

    public static String renderImportWithChildString(String parent, String childString) {
        return Lang.IMPORT_KEYWORD + childString + " " + parent + ";";
    }

    public static String renderMagmaFunction(String name) {
        return renderMagmaFunction("", name);
    }

    public static String renderMagmaFunction(String exportString, String name) {
        return exportString + Lang.CLASS_KEYWORD + "def " + name + "() =>" + Lang.CONTENT;
    }
}
