package magma;

import magma.api.Tuple;
import magma.api.collect.Map;
import magma.api.json.CompoundJSONParser;
import magma.api.json.JSONArrayParser;
import magma.api.json.JSONObjectParser;
import magma.api.json.JSONStringParser;
import magma.api.json.JSONValue;
import magma.api.json.LazyJSONParser;
import magma.api.option.Option;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.compile.CompileException;
import magma.java.JavaList;
import magma.java.JavaMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
        try {
            var absolutePath = CONFIG_PATH.toAbsolutePath();
            if (Files.exists(CONFIG_PATH)) {
                System.out.printf("Found configuration file at '%s'.%n", absolutePath);
            } else {
                System.out.printf("Configuration file did not exist and will be created at '%s'.%n", absolutePath);
                Files.writeString(CONFIG_PATH, "{}");
            }

            var configurationString = Files.readString(CONFIG_PATH);

            var valueParser = new LazyJSONParser();
            valueParser.setValue(new CompoundJSONParser(new JavaList<>(List.of(
                    new JSONStringParser(),
                    new JSONArrayParser(valueParser),
                    new JSONObjectParser(valueParser)
            ))));

            var parsedOption = valueParser.parse(configurationString);
            if (parsedOption.isEmpty()) {
                return new Err<>(new IOException("Failed to parse configuration: " + configurationString));
            }

            var parsed = parsedOption.orElsePanic();

            System.out.println("Parsed configuration.");
            System.out.println(parsed);

            var builds = parsed.find("builds").orElsePanic();
            var map = builds.stream().orElsePanic().map(build -> {
                var sources = Path.of(build.find("sources")
                        .orElsePanic()
                        .findValue()
                        .orElsePanic());

                var debug = Path.of(build.find("debug")
                        .orElsePanic()
                        .findValue()
                        .orElsePanic());

                var targets = Path.of(build.find("debug")
                        .orElsePanic()
                        .findValue()
                        .orElsePanic());

                return new Build(sources, debug, targets);
            }).collect(JavaList.collecting());

            return new Ok<>(new Configuration(map));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }
}
