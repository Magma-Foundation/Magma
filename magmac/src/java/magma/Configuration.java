package magma;

import java.nio.file.Path;

public record Configuration(Path sourceDirectory, Path targetDirectory, Path debugDirectory) {
}