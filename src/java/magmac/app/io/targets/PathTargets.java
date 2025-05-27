package magmac.app.io.targets;

import magmac.api.Option;
import magmac.api.collect.map.Map;
import magmac.api.iter.Iters;
import magmac.app.io.Location;
import magmac.app.io.SafeFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record PathTargets(Path root, String extension) implements Targets {
    private Option<IOException> write(Location location, String output) {
        Path targetParent = Iters.fromList(location.namespace())
                .fold(this.root(), Path::resolve);

        if (!Files.exists(targetParent)) {
            Option<IOException> maybeError = SafeFiles.createDirectories(targetParent);
            if (maybeError.isPresent()) {
                return maybeError;
            }
        }

        Path target = targetParent.resolve(location.name() + "." + this.extension);
        return SafeFiles.writeString(target, output);
    }

    @Override
    public Option<IOException> writeAll(Map<Location, String> outputs) {
        return outputs.iterEntries()
                .map(entry -> this.write(entry.left(), entry.right()))
                .flatMap(Iters::fromOption)
                .next();
    }
}