package magma.compile.lang;

import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.List;

public class JavaDefinitionHeaderFactory {
    static Rule createDefinitionHeaderRule() {
        var type = new ExtractNodeRule("type", Lang.createTypeRule());
        var name = new ExtractStringRule("name");

        var modifiers = new SimpleExtractStringListRule("modifiers", " ");
        var withModifiers = new LastRule(modifiers, " " , type);
        var maybeModifiers = new OrRule(List.of(withModifiers, type));

        return new TypeRule("definition", new LastRule(maybeModifiers, " ", name));
    }
}
