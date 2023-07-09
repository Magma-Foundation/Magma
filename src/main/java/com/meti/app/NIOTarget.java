package com.meti.app;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.JavaString;
import com.meti.nio.NIOFile;

import java.io.IOException;
import java.nio.file.Files;

public record NIOTarget(NIOFile path) {

    public Option<IOException> write(JavaString value) {
        try {
            Files.writeString(path.unwrap(), value.unwrap());
            return new None<>();
        } catch (IOException e) {
            return Some.apply(e);
        }
    }
}
