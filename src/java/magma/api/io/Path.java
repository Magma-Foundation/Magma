package magma.api.io;

import magma.api.collect.List;
import magma.api.result.Result;
import magma.api.option.Option;
import magma.app.Main;

public interface Path {
    Option<IOError> writeString(String output);

    Result<String, IOError> readString();

    Path resolveSibling(String siblingName);

    Result<List<Path>, IOError> walk();

    String findFileName();

    boolean endsWith(String suffix);

    Path relativize(Path source);

    Path getParent();

    Main.Query<String> query();
}
