package magma.api.io;

import magma.api.collect.list.List;
import magma.api.collect.Query;
import magma.api.result.Result;
import magma.api.option.Option;

import java.io.IOException;

public interface Path {
    Option<IOError> writeString(String output);

    Result<String, IOError> readString();

    Path resolveSibling(String siblingName);

    Result<List<Path>, IOError> walk();

    String findFileName();

    boolean endsWith(String suffix);

    Path relativize(Path source);

    Path getParent();

    Query<String> query();

    Path resolveChildSegments(List<String> children);

    Path resolveChild(String name);

    boolean exists();

    Option<IOException> createDirectories();
}
