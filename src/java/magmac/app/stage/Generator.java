package magmac.app.stage;

import magmac.app.compile.lang.PlantUMLRoots;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;
import magmac.app.io.Targets;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class Generator {
    public static Optional<IOException> generateSegments(Map<Location, Node> entries, Targets targets) {
        for (Map.Entry<Location, Node> tuple : entries.entrySet()) {
            String output = PlantUMLRoots.generate(tuple.getValue());
            Optional<IOException> maybeError = targets.write(tuple.getKey(), output);
            if (maybeError.isPresent()) {
                return maybeError;
            }
        }

        return Optional.empty();
    }
}