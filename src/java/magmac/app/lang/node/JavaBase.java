package magmac.app.lang.node;

import magmac.app.lang.Serializable;

public sealed interface JavaBase extends Serializable permits QualifiedType, Symbol {
}
