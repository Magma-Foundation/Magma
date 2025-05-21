package magma.app;

import magma.api.io.IOError;
import magma.api.io.Path;
import magma.api.option.None;
import magma.api.option.Option;

public record PathTargets(Path root) implements Targets {
    private static Option<IOError> writeTarget(Path target, String output) {
        return PathTargets.ensureTargetParent(target).or(() -> target.writeString(output));
    }

    private static Option<IOError> ensureTargetParent(Path target) {
        var parent = target.getParent();
        if (parent.exists()) {
            return new None<IOError>();
        }

        return parent.createDirectories();
    }

    @Override
    public Option<IOError> writeSource(Location location, String output) {
        var target = this.root
                .resolveChildSegments(location.namespace())
                .resolveChild(location.name() + ".ts");

        return PathTargets.writeTarget(target, output);
    }
}