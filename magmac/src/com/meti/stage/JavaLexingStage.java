package com.meti.stage;

import com.meti.lex.Lexer;
import com.meti.lex.NamedLexer;
import com.meti.node.Content;
import com.meti.rule.*;

import static com.meti.rule.AndRule.And;
import static com.meti.rule.OrRule.Or;
import static com.meti.rule.Rules.Optional;
import static com.meti.rule.Rules.SYMBOL;
import static com.meti.rule.WhitespaceRule.PADDING;
import static com.meti.rule.WhitespaceRule.WHITESPACE;

public class JavaLexingStage extends LexingStage {
    public static final Rule STRING = And(new RequireRule("\""),
            new ExtractTextRule("value", AnyRule.Any),
            new RequireRule("\""));

    public static final ExtractTextRule INT = new ExtractTextRule("value", new ListRule(new RangeRule('0', '9')));
    public static final NamedRule IMPORT_RULE = new NamedRule("import", And(
            new RequireRule("import"),
            Optional(And(
                    WHITESPACE,
                    new ExtractTextRule("static", new RequireRule("static"))
            )),
            WHITESPACE,
            new ListDelimitingRule(
                    new RequireRule("."),
                    new StringListRule("segment", Or(Rules.SYMBOL, new RequireRule("*"))))
    ));
    public static final LazyRule VOLATILE_VALUE_RULE = new LazyRule();
    public static final Rule INVOKE = And(
            // new ContentRule("caller", "value", 0),
            new NodeRule("caller", new NamedRule("value", VOLATILE_VALUE_RULE)),
            new RequireRule("("),
            new ListRule(new ExtractNodeElementRule("arguments", "value", 0)),
            new RequireRule(")")
    );
    public static final Rule DEFINITION_RULE = And(
            new ListDelimitingRule(WHITESPACE, new StringListRule("flags", Rules.Enum("public", "final"))),
            PADDING,
            Rules.ExtractSymbol("type"),
            WHITESPACE,
            Rules.ExtractSymbol("name"),
            PADDING,
            new RequireRule("="),
            PADDING,
            new NodeRule("value", VOLATILE_VALUE_RULE)
    );

    public static final Rule METHOD_STATEMENT = new NamedRule("definition", DEFINITION_RULE);
    public static final Rule METHOD_RULE = And(
            new NodeRule("type", new NamedRule("type", new ExtractTextRule("value", Rules.Enum(
                    "String",
                    "void",
                    "long",
                    "int"
            )))),
            WHITESPACE,
            Rules.ExtractSymbol("name"),
            new RequireRule("(){"),
            new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", METHOD_STATEMENT)),
            new RequireRule("}")
    );
    public static final Rule SYMBOL_TOKEN = new ExtractTextRule("value", SYMBOL);
    public static final Rule FIELD = And(
            new ContentRule("parent", "value", 0),
            new RequireRule("."),
            Rules.ExtractSymbol("member")
    );
    public static final Rule VALUE_RULE = OrRule.Or(
            new NamedRule("string", STRING),
            new NamedRule("int", INT),
            new NamedRule("symbol", SYMBOL_TOKEN),
            new NamedRule("field", FIELD),
            new NamedRule("invoke", INVOKE)
    );
    private static final LazyRule VOLATILE_CLASS_RULE = new LazyRule();
    public static final Rule CLASS_MEMBER = OrRule.Or(
            PADDING,
            new NamedRule("method", METHOD_RULE),
            new NamedRule("definition", DEFINITION_RULE),
            new NamedRule("class", VOLATILE_CLASS_RULE)
    );
    public static final Rule CLASS_RULE = And(
            new ListDelimitingRule(WHITESPACE, new StringListRule("flags", Rules.Enum("public", "final"))),
            PADDING,
            new RequireRule("class"),
            WHITESPACE,
            Rules.ExtractSymbol("name"),
            PADDING,
            new RequireRule("{"),
            new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER)),
            new RequireRule("}")
    );
    public static final Rule ROOT_RULE = Or(
            new NamedRule("class", CLASS_RULE),
            new NamedRule("import", IMPORT_RULE)
    );

    static {
        VOLATILE_CLASS_RULE.set(CLASS_RULE);
        VOLATILE_VALUE_RULE.set(VALUE_RULE);
    }

    @Override
    protected Lexer createLexer(Content value) {
        return new NamedLexer(value.value(), ROOT_RULE);
    }
}
