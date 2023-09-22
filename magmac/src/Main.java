import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            list.forEach(file -> compileFile(source, dist, file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compileFile(Path source, Path dist, Path file) {
        try {
            var input = Files.readString(file);
            var lines = split(input);

            var output = lines.stream()
                    .map(Main::compileLine)
                    .collect(Collectors.joining());

            var relative = source.relativize(file);
            var fileName = relative.getFileName().toString();
            var separator = fileName.indexOf('.');
            var withoutSeparator = fileName.substring(0, separator);

            var outputFile = dist.resolve(withoutSeparator + ".mgs");
            Files.writeString(outputFile, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> split(String input) {
        var lines = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;

        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                buffer.append(c);
            }
        }
        lines.add(buffer.toString());
        return lines;
    }

    private static String compileLine(String line) {
        var stripped = line.strip();
        if (stripped.startsWith("import ")) {
            var name = stripped.substring("import ".length());
            var separator = name.lastIndexOf('.');
            var parent = name.substring(0, separator);
            var child = name.substring(separator + 1);
            return "import { " + child + " } from " + parent + ";\n";
        } else if (stripped.contains("class")) {
            var bodyStart = stripped.indexOf('{');
            var bodyEnd = stripped.lastIndexOf('}');
            var body = stripped.substring(bodyStart, bodyEnd + 1);
            var keys = stripped.substring(0, bodyStart).strip();
            var separator = keys.lastIndexOf(' ');
            var name = keys.substring(separator + 1);
            return "export class def " + name + "() => " + body;
        } else {
            return line + ";";
        }
    }
}