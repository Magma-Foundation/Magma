package magma.api.io;

import magma.api.collect.Query;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.result.Result;

public interface Path {
    String asString();

    Option<IOError> writeString(String output);

    Result<String, IOError> readString();

    Result<List<Path>, IOError> walk();

    String findFileName();

    boolean endsWith(String suffix);

    Path relativize(Path source);

    Path getParent();

    Query<String> query();

    Path resolveChildSegments(List<String> children);

    Path resolveChild(String name);

    boolean exists();

    Option<IOError> createDirectories();
}
