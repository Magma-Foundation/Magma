package magma.compile.lang;

import magma.compile.rule.Rule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.MembersSplitter;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

public class Lang {
    static Rule createBlock(Rule child) {
        return new TypeRule("block", new SplitMultipleRule(new MembersSplitter(), "", "children", new StripRule(child)));
    }

    static TypeRule createImportRule(TypeRule namespace) {
        return new TypeRule("import", new LeftRule("import ", new RightRule(new ExtractNodeRule("external", namespace), ";")));
    }

    static TypeRule createNamespaceRule() {
        return new TypeRule("namespace", new SimpleExtractStringListRule("namespace", "."));
    }

    static TypeRule createCatchRule(Rule definition, Rule statement) {
        var declaration = new StripRule(new LeftRule("(", new RightRule(definition, ")")));
        var value = new ExtractNodeRule("child", createBlock(statement));

        var afterKeyword = new FirstRule(new ExtractNodeRule("condition", new TypeRule("declaration", declaration)), "{", new RightRule(value, "}"));
        return new TypeRule("catch", new LeftRule("catch ", afterKeyword));
    }
}
