package com.meti.nio;

import com.meti.core.Err;
import com.meti.core.Ok;
import com.meti.core.Result;
import com.meti.java.JavaSet;
import com.meti.java.Set;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class NIODirectory extends NIOLocation {
    public NIODirectory(Path location) {
        super(location);
    }

    public Result<Set<NIOPath>, IOException> walk() {
        try (var stream = Files.walk(value)) {
            var set = stream
                    .map(NIOPath::new)
                    .collect(Collectors.toSet());

            return Ok.apply(new JavaSet<>(set));
        } catch (IOException e) {
            return Err.apply(e);
        }
    }
}
