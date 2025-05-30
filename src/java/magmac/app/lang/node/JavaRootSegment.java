package magmac.app.lang.node;

import magmac.app.lang.Serializable;

public sealed interface JavaRootSegment extends Serializable permits Whitespace, Namespaced, StructureNode {
}
