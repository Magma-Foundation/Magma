package magma.compile.lang;

import magma.compile.rule.EmptyRule;
import magma.compile.rule.LazyRule;
import magma.compile.rule.OrRule;
import magma.compile.rule.Rule;
import magma.compile.rule.SymbolRule;
import magma.compile.rule.TypeRule;
import magma.compile.rule.split.FirstRule;
import magma.compile.rule.split.LastRule;
import magma.compile.rule.split.MembersSplitter;
import magma.compile.rule.split.ParamSplitter;
import magma.compile.rule.split.SplitMultipleRule;
import magma.compile.rule.text.LeftRule;
import magma.compile.rule.text.RightRule;
import magma.compile.rule.text.StripRule;
import magma.compile.rule.text.extract.ExtractNodeRule;
import magma.compile.rule.text.extract.ExtractStringRule;
import magma.compile.rule.text.extract.SimpleExtractStringListRule;

import java.util.List;

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

    static LazyRule createTypeRule() {
        var type = new LazyRule();
        type.setRule(new OrRule(List.of(
                new TypeRule("array", new RightRule(new ExtractNodeRule("child", type), "[]")),
                new TypeRule("symbol", new ExtractStringRule("value"))
        )));
        return type;
    }

    static TypeRule createTryRule(Rule statement) {
        return new TypeRule("try", new LeftRule("try ", new StripRule(new LeftRule("{", new RightRule(new ExtractNodeRule("child", createBlock(statement)), "}")))));
    }

    static StripRule createModifiersRule() {
        return new StripRule(new SimpleExtractStringListRule("modifiers", " "));
    }

    static Rule createSplitter(Rule value) {
        var arguments = new OrRule(List.of(
                new EmptyRule(),
                new SplitMultipleRule(new ParamSplitter(), "", "arguments", new StripRule(value)))
        );

        var caller = new ExtractNodeRule("caller", value);
        return new InvocationStart(caller, arguments);
    }

    static TypeRule createInvocationRule(Rule value) {
        return new TypeRule("invocation", new RightRule(createSplitter(value), ")"));
    }

    static TypeRule createCommentRule() {
        return new TypeRule("comment", new LeftRule("//", new ExtractStringRule("value")));
    }

    static TypeRule createIfRule(Rule value, Rule statement) {
        var condition = new LeftRule("(", new RightRule(new ExtractNodeRule("condition", value), ")"));
        var child = new RightRule(new ExtractNodeRule("child", createBlock(statement)), "}");
        return new TypeRule("if", new LeftRule("if", new FirstRule(new StripRule(condition), "{", child)));
    }

    static Rule createReturnRule(Rule value) {
        var maybeChild = new OrRule(List.of(new EmptyRule(), new ExtractNodeRule("child", value)));
        var after = new RightRule(new StripRule(maybeChild), ";");
        return new TypeRule("return", new LeftRule("return", after));
    }

    static Rule createForRule(Rule definition, Rule value, Rule statement, String delimiter) {
        var collection = new StripRule(new ExtractNodeRule("collection", value));
        var condition = new RightRule(new LastRule(new StripRule(definition), delimiter, collection), ")");
        var content = new RightRule(new ExtractNodeRule("child", createBlock(statement)), "}");
        var after = new FirstRule(new StripRule(new LeftRule("(", condition)), "{", content);
        return new TypeRule("for", new LeftRule("for", after));
    }

    static Rule createElseRule(Rule statement) {
        var child = new RightRule(new ExtractNodeRule("child", createBlock(statement)), "}");
        return new TypeRule("else", new LeftRule("else", new StripRule(new LeftRule("{", child))));
    }

    static Rule createAssignmentRule(Rule value) {
        var left = new StripRule(new SymbolRule(new ExtractStringRule("reference")));
        var right = new RightRule(new StripRule(new ExtractNodeRule("value", value)), ";");
        return new TypeRule("assignment", new FirstRule(left, "=", right));
    }

    static TypeRule createDeclarationRule(Rule definition, Rule value) {
        return new TypeRule("declaration", new FirstRule(new StripRule(definition), "=", new RightRule(new StripRule(new ExtractNodeRule("value", value)), ";")));
    }

    static Rule createParamsRule(Rule definition) {
        return new SplitMultipleRule(new ParamSplitter(), ", ", "params", new StripRule(new TypeRule("definition", definition)));
    }

    static TypeRule createStringRule() {
        return new TypeRule("string", new LeftRule("\"", new RightRule(new ExtractStringRule("value"), "\"")));
    }

    static TypeRule createAccessRule(LazyRule value) {
        return new TypeRule("access", new LastRule(new ExtractNodeRule("parent", value), ".", new StripRule(new SymbolRule(new ExtractStringRule("child")))));
    }
}
