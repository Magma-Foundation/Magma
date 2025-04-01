#include "CommonLang.h"
InfixRule createContentRule(Rule beforeContent, Rule childRule){Rule children1 = createBlockRule(childRule);
        Rule right = new StripRule("", new SuffixRule(children1, "}"), "after-braces");return InfixRule(beforeContent, "{", right, FirstLocator());
}
Rule createBlockRule(Rule childRule){Rule children = new NodeListRule("children", new FoldingDivider(new DecoratedFolder(new StatementFolder())), childRule);return NodeRule("content", TypeRule("block", StripRule("", children, "after-children")));
}
Rule createSymbolRule(String propertyKey){return StripRule(FilterRule(SymbolFilter(), StringRule(propertyKey)));
}
Rule createDefinitionRule(Rule type){NodeListRule modifiers = new NodeListRule("modifiers", new CharDivider(' '), new StringRule("modifier"));

        NodeRule typeProperty = new NodeRule("type", type);
        Rule beforeName = new OptionalNodeRule("modifiers",
                new InfixRule(modifiers, " ", typeProperty, new TypeSeparatorLocator()),
                typeProperty
        );return StripRule(InfixRule(beforeName, " ", createSymbolRule("name"), LastLocator()));
}
Rule createParamsRule(Rule definition){return OptionalNodeListRule("params", NodeListRule("params", FoldingDivider(ValueFolder()), OrRule(Lists.of(createWhitespaceRule(), definition))), EmptyRule());
}
TypeRule createWhitespaceRule(){return TypeRule("whitespace", StripRule(EmptyRule()));
}
TypeRule createAssignmentRule(){return new TypeRule("assignment", new SuffixRule(new InfixRule(new StringRule("destination"), "=", new StringRule("source"), new FirstLocator()), ";"));
}
TypeRule createElseRule(){return TypeRule("else", StripRule(PrefixRule("else", StringRule("content"))));
}
TypeRule createPostfixRule(){return TypeRule("postfix", SuffixRule(StringRule("value"), "++;"));
}
TypeRule createForRule(){return TypeRule("for", StripRule(PrefixRule("for ", StringRule("content"))));
}
TypeRule createIfRule(){return TypeRule("if", StripRule(PrefixRule("if", StringRule("content"))));
}
TypeRule createReturnRule(Rule value){PrefixRule rule = new PrefixRule("return ", new SuffixRule(new NodeRule("child", value), ";"));return TypeRule("return", StripRule(rule));
}
TypeRule createWhileRule(){return TypeRule("while", StripRule(PrefixRule("while", StringRule("content"))));
}
Rule createGenericRule(Rule type){Rule typeArguments = new NodeListRule("arguments", new FoldingDivider(new ValueFolder()), new OrRule(Lists.of(
                createWhitespaceRule(),
                type
        )));

        Rule base = new NodeRule("base", createQualifiedRule());return TypeRule("generic", StripRule(SuffixRule(InfixRule(base, "<", typeArguments, FirstLocator()), ">")));
}
Rule createQualifiedRule(){return TypeRule("qualified", NodeListRule("segments", CharDivider('.'), createSymbolRule("value")));
}
Rule createNamedWithTypeParams(){Rule name = createSymbolRule("name");
        Rule typeParams = new NodeListRule("type-params", new FoldingDivider(new ValueFolder()), createSymbolRule("value"));return OptionalNodeListRule("type-params", StripRule(InfixRule(name, "<", SuffixRule(typeParams, ">"), FirstLocator())), name);
}
TypeRule createSymbolValueRule(){return TypeRule("symbol-value", createSymbolRule("value"));
}
TypeRule createOperatorRule(LazyRule value, String type, String infix){NodeRule left = new NodeRule("left", value);
        NodeRule right = new NodeRule("right", value);return TypeRule(type, InfixRule(left, infix, right, FirstLocator()));
}
TypeRule createInvocationRule(Rule value){return TypeRule("invocation", StripRule(SuffixRule(InfixRule(NodeRule("caller", value), "(", createArgumentsRule(value), InvocationStartLocator()), ")")));
}
NodeListRule createArgumentsRule(Rule value){return NodeListRule("arguments", FoldingDivider(DecoratedFolder(ValueFolder())), value);
}
TypeRule createAccessRule(String type, String infix, Rule value){return createDataAccess(type, infix, value, createSymbolRule("property"));
}
TypeRule createDataAccess(String type, String infix, Rule value, Rule property){return TypeRule(type, InfixRule(NodeRule("child", value), infix, property, LastLocator()));
}
TypeRule createStringRule(){return TypeRule("string", StripRule(PrefixRule("\"", SuffixRule(StringRule("value"), "\""))));
}
TypeRule createTernaryRule(LazyRule value){NodeRule condition = new NodeRule("condition", value);
        NodeRule whenTrue = new NodeRule("when-true", value);
        NodeRule whenFalse = new NodeRule("when-false", value);return TypeRule("ternary", InfixRule(condition, "?", InfixRule(whenTrue, ":", whenFalse, FirstLocator()), FirstLocator()));
}
TypeRule createNumberRule(){return TypeRule("number", StripRule(FilterRule(NumberFilter(), StringRule("value"))));
}
TypeRule createNotRule(LazyRule value){return TypeRule("not", PrefixRule("!", NodeRule("child", value)));
}
TypeRule createCharRule(){return TypeRule("char", PrefixRule("'", SuffixRule(StringRule("value"), "'")));
}
