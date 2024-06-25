package magma;

import java.nio.file.Path;

public record Build(Path sourceDirectory, Path targetDirectory, Path debugDirectory) {
}