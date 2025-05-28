package magmac.app.io.targets;

import magmac.api.Option;
import magmac.api.Tuple2;
import magmac.api.collect.map.Map;
import magmac.api.iter.Iters;
import magmac.app.io.Location;
import magmac.app.io.SafeFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record PathTargets(Path root, String extension) implements Targets {
    private Option<IOException> write(Location location, String output) {
        Path targetParent = location.namespace()
                .iter()
                .fold(this.root(), (Path path, String other) -> path.resolve(other));

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
                .map((Tuple2<Location, String> entry) -> this.write(entry.left(), entry.right()))
                .flatMap((Option<IOException> option) -> Iters.fromOption(option))
                .next();
    }
}