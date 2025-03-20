package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static final Path SOURCE_DIRECTORY = Paths.get(".", "@magma", "app", "src", "magma");

    public static void main(String[] args) {
        System.out.println(Paths.get(".").toAbsolutePath());

        try (Stream<Path> stream = Files.walk(SOURCE_DIRECTORY)) {
            final var sources = stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".mgs"))
                    .collect(Collectors.toSet());

            for (var source : sources) {
                final var relative = SOURCE_DIRECTORY.relativize(source);
                final var parent = relative.getParent();
                final var nameWithExt = relative.getFileName().toString();
                final var name = nameWithExt.substring(0, nameWithExt.lastIndexOf('.'));

                final var TARGET_DIRECTORY = Paths.get(".", "@magma", "app", "src", "ts");
                final var targetParent = parent == null
                        ? TARGET_DIRECTORY
                        : TARGET_DIRECTORY.resolve(parent);

                if (Files.exists(targetParent)) Files.createDirectories(targetParent);

                final var target = targetParent.resolve(name + ".ts");
                final var input = Files.readString(source);
                Files.writeString(target, compile(input));
            }
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return input;
    }
}
