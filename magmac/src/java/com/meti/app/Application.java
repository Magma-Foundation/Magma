package com.meti.app;

import com.meti.io.IOException;
import com.meti.java.JavaPath;
import com.meti.core.None;
import com.meti.core.Option;

import static com.meti.java.JavaPath.CURRENT_WORKING_DIRECTORY;

public class Application {
    public static final String NAME = "ApplicationTest";
    public static final JavaPath TARGET = new JavaPath(CURRENT_WORKING_DIRECTORY.resolveChild(NAME + ".mgs"));
    public static final JavaPath SOURCE = new JavaPath(CURRENT_WORKING_DIRECTORY.resolveChild(NAME + ".java"));

    public static Option<IOException> run() {
        if (SOURCE.exists()) {
            return TARGET.createFile();
        } else {
            return new None<>();
        }
    }
}