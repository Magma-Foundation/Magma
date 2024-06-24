package magma;

import java.nio.file.Path;
import java.util.List;

public record PathSource(Path path, List<String> namespace, String name) {
}