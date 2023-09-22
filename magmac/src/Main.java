import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        var root = Paths.get(".", "magmac");
        var source = root.resolve("src");
        var dist = root.resolve("dist");

        if (!Files.exists(dist)) {
            try {
                Files.createDirectories(dist);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (var list = Files.list(source)) {
            list.forEach(file -> {
                try {
                    var input = Files.readString(file);
                    var output = Stream.of(input.split(";"))
                            .map(line -> {
                                var stripped = line.strip();
                                if (stripped.startsWith("import ")) {
                                    var name = stripped.substring("import ".length());
                                    var separator = name.lastIndexOf('.');
                                    var parent = name.substring(0, separator);
                                    var child = name.substring(separator + 1);
                                    return "import { " + child + " } from " + parent + ";\n";
                                } else {
                                    return line + ";";
                                }
                            })
                            .collect(Collectors.joining());

                    var relative = source.relativize(file);
                    var outputFile = dist.resolve(relative);
                    Files.writeString(outputFile, output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}