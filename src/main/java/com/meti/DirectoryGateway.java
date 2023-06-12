package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class DirectoryGateway implements Gateway {
    private final NativePath root;

    public DirectoryGateway(NativePath root) {
        this.root = root;
    }

    @Override
    public Result<NativeSet<NativePath>, IOException> collectSources() {
        try (var stream = Files.walk(root.unwrap())) {
            return Results.Ok(new NativeSet<>(stream.collect(Collectors.toSet()))
                    .iter()
                    .map(NativePath::new)
                    .collect(Iterables.toSet()));
        } catch (IOException e) {
            return Results.Err(e);
        }
    }
}
