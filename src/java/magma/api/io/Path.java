package magma.api.io;

import magma.app.Main;

public interface Path {
    Main.Option<Main.IOError> writeString(String output);

    Main.Result<String, Main.IOError> readString();

    Path resolveSibling(String siblingName);

    Main.Result<Main.List<Path>, Main.IOError> walk();

    String findFileName();

    boolean endsWith(String suffix);
}
