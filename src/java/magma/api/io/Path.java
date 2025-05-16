package magma.api.io;

import magma.api.collect.List;
import magma.api.collect.query.Query;
import magma.api.result.Result;
import magma.api.option.Option;

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
}
