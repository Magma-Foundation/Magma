package magmac.app.lang.node;

import magmac.app.lang.Serializable;

public sealed interface Base extends Serializable permits QualifiedType, Symbol {
}
