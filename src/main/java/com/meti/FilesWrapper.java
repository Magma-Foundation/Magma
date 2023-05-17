package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesWrapper {
    static Result<Void> writeImpl(Path output, String s) {
        try {
            Files.writeString(output, s);
            return new EmptyOk();
        } catch (IOException e) {
            return new ErrorResult<>(e);
        }
    }

    static Result<String> readImpl(Path input) {
        try {
            return new ValueOk(Files.readString(input));
        } catch (IOException e) {
            return new ErrorResult<>(e);
        }
    }
}
