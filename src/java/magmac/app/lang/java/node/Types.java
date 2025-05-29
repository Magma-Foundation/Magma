package magmac.app.lang.java.node;

import magmac.api.collect.list.Lists;
import magmac.app.compile.error.CompileResult;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.OrRule;
import magmac.app.compile.rule.Rule;
import magmac.app.lang.LazyRule;
import magmac.app.lang.MutableLazyRule;
import magmac.app.lang.java.Deserializers;

public class Types {
    public static CompileResult<Type> deserialize(Node node) {
        return Deserializers.orError("type", node, Lists.of(
                Deserializers.wrap(Symbols::deserialize),
                Deserializers.wrap(TemplateType::deserialize),
                Deserializers.wrap(VariadicType::deserialize),
                Deserializers.wrap(ArrayType::deserialize)
        ));
    }

    public static Rule createTypeRule() {
        LazyRule type = new MutableLazyRule();
        return type.set(new OrRule(Lists.of(
                VariadicType.createVariadicRule(type),
                ArrayType.createArrayRule(type),
                TemplateType.createTemplateRule(type),
                Symbols.createSymbolRule(),
                QualifiedType.createQualifiedRule()
        )));
    }
}
