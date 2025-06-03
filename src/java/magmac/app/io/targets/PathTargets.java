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
                .map(this::write)
                .flatMap(Iters::fromOption)
                .next();
    }

    private Option<IOException> write(Unit<String> entry) {
        return entry.destruct((Location location, String output) -> {
            var targetParent = location.namespace()
                    .iter()
                    .fold(this.root, Path::resolve);

            if (!Files.exists(targetParent)) {
                var maybeError = SafeFiles.createDirectories(targetParent);
                if (maybeError.isPresent()) {
                    return maybeError;
                }
            }

            var target = targetParent.resolve(location.name() + "." + this.extension);
            return SafeFiles.writeString(target, output);
        });
    }
}