package com.meti;

public class Output {
    private final String headerContent;
    private final String targetContent;

    public Output(String headerContent, String targetContent) {
        this.headerContent = headerContent;
        this.targetContent = targetContent;
    }

    public String getHeaderContent() {
        return headerContent;
    }

    public String getTargetContent() {
        return targetContent;
    }
}
