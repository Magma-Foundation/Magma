package magmac.app.lang.node;

import magmac.app.lang.Serializable;

public sealed interface Type extends Serializable permits ArrayType, Symbol, TemplateType, VariadicType {
}
