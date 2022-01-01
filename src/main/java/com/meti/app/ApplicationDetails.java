package com.meti.app;

import com.meti.api.io.File;

public class ApplicationDetails {
    private final ArrayList<File> value;

    public ApplicationDetails(ArrayList<File> value) {
        this.value = value;
    }

    public int count() {
        return value.size();
    }

    public long elapsed() {
        return 0;
    }

    public ArrayList<File> getValue() {
        return value;
    }
}
