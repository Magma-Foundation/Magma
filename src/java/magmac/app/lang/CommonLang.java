package magmac.app.lang;

import magmac.api.Option;
import magmac.api.collect.list.List;
import magmac.app.compile.rule.NodeListRule;
import magmac.app.compile.rule.Rule;
import magmac.app.compile.rule.fold.StatementFolder;
import magmac.app.lang.common.Annotation;
import magmac.app.lang.node.Modifier;
import magmac.app.lang.web.TypescriptLang;

import java.util.Objects;

public class CommonLang {
    public static class Definition<T> {
        protected final String name;
        protected final T type;
        private final Option<List<Annotation>> maybeAnnotations;
        private final List<Modifier> modifiers;
        private final Option<List<TypescriptLang.TypeParam>> maybeTypeParams;

        public Definition(
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

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (null == obj || obj.getClass() != this.getClass()) {
                return false;
            }
            var that = (Definition) obj;
            return Objects.equals(this.maybeAnnotations, that.maybeAnnotations) &&
                    Objects.equals(this.modifiers, that.modifiers) &&
                    Objects.equals(this.name, that.name) &&
                    Objects.equals(this.maybeTypeParams, that.maybeTypeParams) &&
                    Objects.equals(this.type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.maybeAnnotations, this.modifiers, this.name, this.maybeTypeParams, this.type);
        }

        @Override
        public String toString() {
            return "Definition[" +
                    "maybeAnnotations=" + this.maybeAnnotations + ", " +
                    "modifiers=" + this.modifiers + ", " +
                    "name=" + this.name + ", " +
                    "maybeTypeParams=" + this.maybeTypeParams + ", " +
                    "type=" + this.type + ']';
        }

    }

    public static Rule Statements(String key, Rule childRule) {
        return NodeListRule.createNodeListRule(key, new StatementFolder(), childRule);
    }
}
