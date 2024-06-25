package magma;

import java.nio.file.Path;

public record Build(BuildSet sourceDirectory, Path targetDirectory, BuildSet debugDirectory) {
}