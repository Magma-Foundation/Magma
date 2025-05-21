package magma.api.io;

import magma.api.collect.list.Iterable;
import magma.api.collect.Iter;
import magma.api.result.Result;
import magma.api.option.Option;

public interface Path {
    Option<IOError> writeString(String output);

    Result<String, IOError> readString();

    Path resolveSibling(String siblingName);

    Result<Iterable<Path>, IOError> walk();

    String findFileName();

    boolean endsWith(String suffix);

    Path relativize(Path source);

    Path getParent();

    Iter<String> query();

    Path resolveChildSegments(Iterable<String> children);

    Path resolveChild(String name);

    boolean exists();

    Option<IOError> createDirectories();
}
