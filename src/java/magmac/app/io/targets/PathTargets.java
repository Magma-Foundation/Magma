package magmac.app.io.targets;

import magmac.api.Option;
import magmac.api.iter.Iters;
import magmac.app.io.Location;
import magmac.app.io.SafeFiles;
import magmac.app.stage.unit.Unit;
import magmac.app.stage.unit.UnitSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record PathTargets(Path root, String extension) implements Targets {

    @Override
    public Option<IOException> writeAll(UnitSet<String> outputs) {
        return outputs.iter()
                .map((Unit<String> entry) -> this.write(entry))
                .flatMap((Option<IOException> option) -> Iters.fromOption(option))
                .next();
    }

    private Option<IOException> write(Unit<String> entry) {
        return entry.deconstruct((Location location, String output) -> {
            Path targetParent = location.namespace()
                    .iter()
                    .fold(this.root, (Path path, String other) -> path.resolve(other));

            if (!Files.exists(targetParent)) {
                Option<IOException> maybeError = SafeFiles.createDirectories(targetParent);
                if (maybeError.isPresent()) {
                    return maybeError;
                }
            }

            Path target = targetParent.resolve(location.name() + "." + this.extension);
            return SafeFiles.writeString(target, output);
        });
    }
}