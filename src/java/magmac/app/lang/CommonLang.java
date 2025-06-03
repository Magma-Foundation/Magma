package magmac.app.lang;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.fold.StatementFolder;
import magmac.app.lang.common.Annotation;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.web.TypescriptLang;

public final class CommonLang {
    public static Rule Statements(String key, Rule childRule) {
        return NodeListRule.createNodeListRule(key, new StatementFolder(), childRule);
    }

    public static record Definition<T>(
            Option<List<Annotation>> maybeAnnotations,
            List<Modifier> modifiers,
            String name,
            Option<List<TypescriptLang.TypeParam>> maybeTypeParams,
            T type
    ) {

        public <T extends Serializable> Definition<T> withType(T newType) {
            return new Definition<T>(this.maybeAnnotations, this.modifiers, this.name, this.maybeTypeParams, newType);
        }

        public Definition<T> withName(String name) {
            return new Definition<>(this.maybeAnnotations, this.modifiers, name, this.maybeTypeParams, this.type);
        }
    }
}
