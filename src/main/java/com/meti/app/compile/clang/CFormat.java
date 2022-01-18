package com.meti.app.compile.clang;

public enum CFormat {
    Source("c"),
    Header("h");

    private final String extension;

    public String getExtension() {
        return extension;
    }

    CFormat(String extension) {
        this.extension = extension;
    }
}
