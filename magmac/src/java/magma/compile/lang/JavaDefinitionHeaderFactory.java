package magma.compile.lang;

import magma.compile.rule.EmptyRule;
import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.extract.ExtractStringRule;

public class JavaDefinitionHeaderFactory {
    static Rule createDefinitionHeaderRule() {
        return new TypeRule("definition", new LeftRule("var ", new ExtractStringRule("name")));
    }
}
