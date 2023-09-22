import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
                    var content = Files.readString(file);
                    var relative = source.relativize(file);
                    var outputFile = dist.resolve(relative);
                    Files.writeString(outputFile, content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}