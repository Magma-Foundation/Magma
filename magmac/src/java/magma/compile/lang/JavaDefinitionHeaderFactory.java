package magma.compile.lang;

import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.BackwardsRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringListRule;
import magma.compile.rule.text.extract.ExtractStringRule;

import java.util.List;
import java.util.Optional;

public class JavaDefinitionHeaderFactory {
    static Rule createDefinitionHeaderRule() {
        var type = new ExtractNodeRule("type", Lang.createTypeRule());
        var name = new ExtractStringRule("name");

        var generics = new LeftRule("<", new RightRule(new ExtractStringRule("type-params"), ">"));
        var withGenerics = new LastRule(generics, " ", type);
        var maybeGenerics = new OrRule(List.of(withGenerics, type));

        var modifiers = new ModifiersRule();
        var withModifiers = new BackwardsRule(modifiers, " ", maybeGenerics);
        var maybeModifiers = new OrRule(List.of(withModifiers, maybeGenerics));

        return new TypeRule("definition", new LastRule(maybeModifiers, " ", name));
    }

    private static class ModifiersRule extends ExtractStringListRule {
        public static final List<String> MODIFIERS = List.of("public", "static");

        public ModifiersRule() {
            super("modifiers", " ");
        }

        @Override
        protected Optional<Error_> qualify(String child) {
            if (MODIFIERS.contains(child)) {
                return Optional.empty();
            } else {
                return Optional.of(new CompileError("Invalid modifier.", child));
            }
        }
    }
}
