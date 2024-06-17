package magma.compile.lang;

import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;

public class JavaDefinitionHeaderFactory {
    static Rule createDefinitionHeaderRule() {
        var type = new ExtractNodeRule("type", Lang.createTypeRule());
        var name = new ExtractStringRule("name");

        return new TypeRule("definition", new LastRule(type, " ", name));
    }
}
