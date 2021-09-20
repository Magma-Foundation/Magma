package com.meti;

public class Output {
    private final String headerContent;
    private final String sourceContent;

    public Output() {
        this("", "");
    }

    public Output(String headerContent, String sourceContent) {
        this.headerContent = headerContent;
        this.sourceContent = sourceContent;
    }

    public String getHeaderContent() {
        return headerContent;
    }

    public String getSourceContent() {
        return sourceContent;
    }

    public Output concat(Output other) {
        return new Output(headerContent + other.headerContent, sourceContent + other.sourceContent);
    }
}
