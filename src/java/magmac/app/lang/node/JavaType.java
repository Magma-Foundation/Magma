package magmac.app.lang.node;

import magmac.app.lang.Serializable;

public sealed interface JavaType extends Serializable permits ArrayType, Symbol, TemplateType, VariadicType {
}
