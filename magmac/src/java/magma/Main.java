package magma;

import magma.api.collect.Map;
import magma.api.option.Option;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileException;
import magma.java.JavaMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static magma.java.JavaResults.$;
import static magma.java.JavaResults.$Void;

public class Main {
    public static final Path CONFIG_PATH = Paths.get(".", "config.json");

    public static void main(String[] args) {
        var result = run();
        if (result.isPresent()) {
            //noinspection CallToPrintStackTrace
            result.orElsePanic().printStackTrace();
        }
    }

    private static Option<CompileException> run() {
        return $Void(() -> {
            var configuration = $(buildConfiguration().mapErr(CompileException::new));
            $(new Application(configuration).run());
        });
    }

    private static Result<Configuration, IOException> buildConfiguration() {
        return readConfiguration().mapValue(map -> {
            var sourceDirectory = Paths.get(map.get("sources").orElsePanic());
            var debugDirectory = Paths.get(map.get("debug").orElsePanic());
            var targetDirectory = Paths.get(map.get("targets").orElsePanic());
            return new Configuration(sourceDirectory, targetDirectory, debugDirectory);
        });
    }

    private static Result<Map<String, String>, IOException> readConfiguration() {
        try {
            var absolutePath = CONFIG_PATH.toAbsolutePath();
            if (Files.exists(CONFIG_PATH)) {
                System.out.println("Found configuration file at '" + absolutePath + "'.");
            } else {
                System.out.printf("Configuration file did not exist and will be created at '%s'.%n", absolutePath);
                Files.writeString(CONFIG_PATH, "{}");
            }

            var configurationString = Files.readString(CONFIG_PATH);
            var map = new HashMap<String, String>();
            var stripped = configurationString.strip();
            var lines = stripped
                    .substring(1, stripped.length() - 1)
                    .split(",");

            for (String line : lines) {
                var separator = line.indexOf(":");
                var left = line.substring(0, separator).strip();
                var right = line.substring(separator + 1).strip();
                var propertyName = left.substring(1, left.length() - 1);
                var propertyValue = right.substring(1, right.length() - 1);
                map.put(propertyName, propertyValue);
            }

            System.out.println("Parsed configuration.");
            System.out.println(map);

            return new Ok<>(new JavaMap<>(map));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

}
