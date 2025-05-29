package magmac.app.lang.java.type;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.lang.CommonLang;
import magmac.app.lang.LazyRule;
import magmac.app.lang.MutableLazyRule;
import magmac.app.lang.java.Deserializers;
import magmac.app.lang.java.Type;
import magmac.app.lang.java.value.Symbols;

public class Types {
    public static CompileResult<Type> deserialize(Node node) {
        return Deserializers.orError("type", node, Lists.of(
                Deserializers.wrap(Symbols::deserializeSymbol),
                Deserializers.wrap(TemplateType::deserialize),
                Deserializers.wrap(Variadic::deserialize)
        ));
    }

    public static Rule createTypeRule() {
        LazyRule type = new MutableLazyRule();
        return type.set(new OrRule(Lists.of(
                Variadic.createVariadicRule(type),
                CommonLang.createArrayRule(type),
                TemplateType.createTemplateRule(type),
                Symbols.createSymbolTypeRule(),
                Symbols.createQualifiedRule()
        )));
    }
}
