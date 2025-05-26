package magmac.app.io;

import jvm.io.SafeFiles;
import magmac.api.collect.Iters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public record PathTargets(Path root) implements Targets {
    private Optional<IOException> write(Location location, String output) {
        Path targetParent = Iters.fromList(location.namespace())
                .fold(this.root(), Path::resolve);

        if (!Files.exists(targetParent)) {
            Optional<IOException> maybeError = SafeFiles.createDirectories(targetParent);
            if (maybeError.isPresent()) {
                return maybeError;
            }
        }

        Path target = targetParent.resolve(location.name() + ".puml");
        return SafeFiles.writeString(target, output);
    }

    @Override
    public Optional<IOException> writeAll(Map<Location, String> outputs) {
        for (Map.Entry<Location, String> tuple : outputs.entrySet()) {
            Optional<IOException> maybeError = write(tuple.getKey(), tuple.getValue());
            if (maybeError.isPresent()) {
                return maybeError;
            }
        }
        return Optional.empty();
    }
}