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
    public static class AbstractDefinition<T> {
        protected final String name;
        protected final T type;
        protected final Option<List<Annotation>> maybeAnnotations;
        protected final List<Modifier> modifiers;
        protected final Option<List<TypescriptLang.TypeParam>> maybeTypeParams;

        public AbstractDefinition(
                Option<List<Annotation>> maybeAnnotations,
                List<Modifier> modifiers,
                String name,
                Option<List<TypescriptLang.TypeParam>> maybeTypeParams,
                T type
        ) {
            this.maybeAnnotations = maybeAnnotations;
            this.modifiers = modifiers;
            this.name = name;
            this.maybeTypeParams = maybeTypeParams;
            this.type = type;
        }

        public Option<List<Annotation>> maybeAnnotations() {
            return this.maybeAnnotations;
        }

        public List<Modifier> modifiers() {
            return this.modifiers;
        }

        public String name() {
            return this.name;
        }

        public Option<List<TypescriptLang.TypeParam>> maybeTypeParams() {
            return this.maybeTypeParams;
        }

        public T type() {
            return this.type;
        }
    }

    public static Rule Statements(String key, Rule childRule) {
        return NodeListRule.createNodeListRule(key, new StatementFolder(), childRule);
    }
}
