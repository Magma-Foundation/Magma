package magmac.app.stage.after;

import magmac.app.compile.node.Node;
import magmac.app.stage.unit.UnitSet;

/**
 * Hook invoked once code generation is complete.
 */
public interface AfterAll {
    /**
     * Runs after all compilation units have been generated.
     */
    UnitSet<Node> afterAll(UnitSet<Node> roots);
}
