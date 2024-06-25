package magma;

import java.nio.file.Path;

public record Build(BuildSet sourceDirectory, BuildSet targetDirectory, Path debugDirectory) {
}