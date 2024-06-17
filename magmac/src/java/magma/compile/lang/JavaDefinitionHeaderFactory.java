package magma.compile.lang;

import magma.compile.CompileError;
import magma.compile.Error_;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.BackwardsRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.split.Splitter;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringListRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.List;
import java.util.Optional;

public class JavaDefinitionHeaderFactory {
    static Rule createDefinitionHeaderRule() {
        var type = new ExtractNodeRule("type", Lang.createTypeRule());
        var name = new ExtractStringRule("name");

        var generics = new LeftRule("<", new RightRule(new SimpleExtractStringListRule("type-params", ","), ">"));
        var withGenerics = new BackwardsRule(generics, " ", type);
        var maybeGenerics = new OrRule(List.of(withGenerics, type));

        var modifiers = new ModifiersRule();
        var withModifiers = new BackwardsRule(modifiers, " ", maybeGenerics);
        var maybeModifiers = new OrRule(List.of(withModifiers, maybeGenerics));

        var annotation = new TypeRule("annotation", new LeftRule("@", new ExtractStringRule("value")));
        var annotations = new SplitMultipleRule(new SimpleSplitter(), ", ", "annotations", annotation);
        var withAnnotations = new LastRule(annotations, "\n", maybeModifiers);
        var maybeAnnotations = new OrRule(List.of(withAnnotations, maybeModifiers));

        return new TypeRule("definition", new LastRule(maybeAnnotations, " ", name));
    }

    private static class ModifiersRule extends ExtractStringListRule {
        public static final List<String> MODIFIERS = List.of(
                "public",
                "static",
                "final",
                "private",
                "default"
        );

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

    private static class SimpleSplitter implements Splitter {
        @Override
        public List<String> split(String input) {
            return List.of(input.split("\n"));
        }
    }
}
