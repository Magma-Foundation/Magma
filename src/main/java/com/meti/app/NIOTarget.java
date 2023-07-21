package com.meti.app;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.java.String_;
import com.meti.nio.NIOFile;

import java.io.IOException;
import java.nio.file.Files;

public record NIOTarget(NIOFile path) {

    public Option<IOException> write(String_ value) {
        try {
            Files.writeString(path.unwrap(), value.unwrap());
            return None.apply();
        } catch (IOException e) {
            return Some.apply(e);
        }
    }
}
