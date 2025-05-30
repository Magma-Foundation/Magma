package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;

public record StructureValue(String name, List<Modifier> modifiers, Option<List<TypeParam>> typeParams,
                             Option<List<Type>> extended, Option<List<Type>> implemented,
                             List<StructureMember> members) {
}