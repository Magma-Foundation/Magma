package magmac.app.lang.node;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.lang.web.TypescriptLang;

public record StructureValue<T, M>(
        String name,
        List<Modifier> modifiers,
        List<M> members,
        Option<List<TypescriptLang.TypeParam>> maybeTypeParams,
        Option<List<T>> maybeExtended,
        Option<List<T>> maybeImplemented
) {
}