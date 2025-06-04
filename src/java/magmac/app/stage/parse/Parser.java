package magmac.app.stage.parse;

import magmac.app.stage.unit.UnitSet;
import magmac.app.stage.Stage;

/**
 * Stage that turns tokens into typed AST nodes.
 */
public interface Parser<T, R> extends Stage<UnitSet<T>, UnitSet<R>> {
}
