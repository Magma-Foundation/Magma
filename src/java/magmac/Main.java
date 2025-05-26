package magmac;

import magmac.app.Application;
import magmac.app.io.PathSources;
import magmac.app.io.Sources;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main() {
        Sources sources = new PathSources(Paths.get(".", "src", "java"));
        new Application(sources).run().ifPresent(error -> Main.handleError(error));
    }

    private static void handleError(IOException error) {
        //noinspection CallToPrintStackTrace
        error.printStackTrace();
    }
}
