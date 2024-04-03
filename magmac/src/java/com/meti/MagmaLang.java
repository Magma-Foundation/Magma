package com.meti;

public class MagmaLang {
    public static String renderMagmaImport(String parent, String child) {
        return Lang.IMPORT_KEYWORD + "{ " + child + " } " + parent + ";";
    }

    public static String renderMagmaFunction(String name) {
        return Lang.CLASS_KEYWORD + " def " + name + "() =>" + Lang.CONTENT;
    }
}
