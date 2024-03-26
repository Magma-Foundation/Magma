package com.meti.java;

import com.meti.TypeCompiler;
import com.meti.node.*;
import com.meti.rule.*;
import com.meti.stage.JavaLexingStage;

import java.util.*;
import java.util.stream.Collectors;

import static com.meti.rule.WhitespaceRule.PADDING;
import static com.meti.rule.WhitespaceRule.WHITESPACE;

public final class DefinitionLexer implements Lexer {
    public static final String ID = "definition";
    public static final String INDENT = "indent";
    public static final String FLAGS = "flags";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String VALUE = "value";
    private static Rule RULE;
    private final String body;
    private final int indent;

    private DefinitionLexer(String body, int indent) {
        this.body = body;
        this.indent = indent;
    }

    public static Lexer createDefinitionLexer(String value, int indent) {
        RULE = AndRule.And(
                new ListDelimitingRule(WHITESPACE, new StringListRule("flags", Rules.Enum("public", "final"))),
                PADDING,
                Rules.ExtractSymbol(TYPE),
                WHITESPACE,
                Rules.ExtractSymbol(NAME),
                PADDING,
                new RequireRule("="),
                PADDING,
                new NodeRule("value", JavaLexingStage.VALUE_NODE)
        );

        return new RuleLexer(ID, value, RULE);
    }

    @Override
    public Optional<Node> lex() {
        var valueSeparator = body().indexOf('=');
        if (valueSeparator == -1) return Optional.empty();

        var before = body().substring(0, valueSeparator).strip();
        var after = body().substring(valueSeparator + 1).strip();

        var nameSeparator = before.lastIndexOf(' ');
        if (nameSeparator == -1) return Optional.empty();

        var keys = before.substring(0, nameSeparator).strip();
        var typeSeparator = keys.lastIndexOf(' ');
        var type = typeSeparator == -1 ? keys : keys.substring(typeSeparator + 1).strip();

        Set<String> inputFlags;
        if (typeSeparator == -1) {
            inputFlags = Collections.emptySet();
        } else {
            inputFlags = Arrays.stream(keys.substring(0, typeSeparator).strip().split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .collect(Collectors.toSet());
        }

        var outputFlags = new ArrayList<String>();
        if (inputFlags.contains("public")) {
            outputFlags.add("pub");
        }

        if (inputFlags.contains("final")) {
            outputFlags.add("const");
        } else {
            outputFlags.add("let");
        }

        var outputType = new TypeCompiler(type).compile();

        var name = before.substring(nameSeparator + 1).strip();
        var value = new Content(VALUE, after, 0);

        return Optional.of(new MapNode("definition", Map.of(
                INDENT, new IntAttribute(indent),
                FLAGS, new StringListAttribute(outputFlags),
                NAME, new StringAttribute(name),
                TYPE, new StringAttribute(outputType),
                VALUE, new NodeAttribute(value)
        )));
    }

    public String body() {
        return body;
    }

    public int indent() {
        return indent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DefinitionLexer) obj;
        return Objects.equals(this.body, that.body) &&
               this.indent == that.indent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, indent);
    }

    @Override
    public String toString() {
        return "DefinitionLexer[" +
               "body=" + body + ", " +
               "indent=" + indent + ']';
    }

}