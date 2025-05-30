package magmac.app.lang.node;

import magmac.app.lang.Serializable;

public sealed interface JavaStructureMember extends Serializable permits EnumValues, MethodNode, StructureStatement, Whitespace {
}
