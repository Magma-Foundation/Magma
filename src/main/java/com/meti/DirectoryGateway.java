package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class DirectoryGateway implements Gateway {
    private final NativePath root;

    public DirectoryGateway(NativePath root) {
        this.root = root;
    }

    private static boolean filterByExtension(NativePath filter) {
        return filter.getFileName()
                .asString()
                .endsWith(NativeString.fromNative(".java"));
    }

    @Override
    public Result<NativeSet<NativePath>, IOException> collectSources() {
        return collectAllSources().mapValue(value -> value.iter()
                .filter(DirectoryGateway::filterByExtension)
                .collect(Iterables.toSet()));
    }

    private Result<NativeSet<NativePath>, IOException> collectAllSources() {
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
