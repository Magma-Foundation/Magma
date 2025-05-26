package magmac.app.stage;

import magmac.app.compile.lang.PlantUMLRoots;
import magmac.app.compile.node.Node;
import magmac.app.io.Location;

import java.util.HashMap;
import java.util.Map;

public class Generator {
    public static Map<Location, String> generateAll(Map<Location, Node> entries) {
        Map<Location, String> outputs = new HashMap<>();
        for (Map.Entry<Location, Node> tuple : entries.entrySet()) {
            String output = PlantUMLRoots.generate(tuple.getValue());
            outputs.put(tuple.getKey(), output);
        }
        return outputs;
    }
}