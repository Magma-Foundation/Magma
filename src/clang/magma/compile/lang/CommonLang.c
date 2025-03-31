#include "CommonLang.h"
magma.compile.rule.text.InfixRule createContentRule(magma.compile.rule.Rule beforeContent, magma.compile.rule.Rule childRule){Rule children1 = createBlockRule(childRule);
        Rule right = new StripRule("", new SuffixRule(children1, "}"), "after-braces");return InfixRule(beforeContent, "{", right, FirstLocator());
}
magma.compile.rule.Rule createBlockRule(magma.compile.rule.Rule childRule){Rule children = new NodeListRule("children", new FoldingDivider(new DecoratedFolder(new StatementFolder())), childRule);return NodeRule("content", TypeRule("block", StripRule("", children, "after-children")));
}
magma.compile.rule.Rule createSymbolRule(String propertyKey){return StripRule(FilterRule(SymbolFilter(), StringRule(propertyKey)));
}
magma.compile.rule.Rule createDefinitionRule(magma.compile.rule.Rule type){NodeListRule modifiers = new NodeListRule("modifiers", new CharDivider(' '), new StringRule("modifier"));

        NodeRule typeProperty = new NodeRule("type", type);
        Rule beforeName = new OptionalNodeRule("modifiers",
                new InfixRule(modifiers, " ", typeProperty, new TypeSeparatorLocator()),
                typeProperty
        );return StripRule(InfixRule(beforeName, " ", createSymbolRule("name"), LastLocator()));
}
magma.compile.rule.Rule createParamsRule(magma.compile.rule.Rule definition){return OptionalNodeListRule("params", NodeListRule("params", FoldingDivider(ValueFolder()), OrRule(Lists.of(createWhitespaceRule(), definition))), EmptyRule());
}
magma.compile.rule.tree.TypeRule createWhitespaceRule(){return TypeRule("whitespace", StripRule(EmptyRule()));
}
magma.compile.rule.tree.TypeRule createAssignmentRule(){return new TypeRule("assignment", new SuffixRule(new InfixRule(new StringRule("destination"), "=", new StringRule("source"), new FirstLocator()), ";"));
}
magma.compile.rule.tree.TypeRule createElseRule(){return TypeRule("else", StripRule(PrefixRule("else", StringRule("content"))));
}
magma.compile.rule.tree.TypeRule createPostfixRule(){return TypeRule("postfix", SuffixRule(StringRule("value"), "++;"));
}
magma.compile.rule.tree.TypeRule createForRule(){return TypeRule("for", StripRule(PrefixRule("for ", StringRule("content"))));
}
magma.compile.rule.tree.TypeRule createIfRule(){return TypeRule("if", StripRule(PrefixRule("if", StringRule("content"))));
}
magma.compile.rule.tree.TypeRule createReturnRule(magma.compile.rule.Rule value){PrefixRule rule = new PrefixRule("return ", new SuffixRule(new NodeRule("value", value), ";"));return TypeRule("return", StripRule(rule));
}
magma.compile.rule.tree.TypeRule createWhileRule(){return TypeRule("while", StripRule(PrefixRule("while", StringRule("content"))));
}
magma.compile.rule.Rule createGenericRule(magma.compile.rule.Rule type){Rule typeArguments = new NodeListRule("arguments", new FoldingDivider(new ValueFolder()), new OrRule(Lists.of(
                createWhitespaceRule(),
                type
        )));

        Rule base = new NodeRule("base", createQualifiedRule());return TypeRule("generic", StripRule(SuffixRule(InfixRule(base, "<", typeArguments, FirstLocator()), ">")));
}
magma.compile.rule.Rule createQualifiedRule(){return TypeRule("qualified", NodeListRule("segments", CharDivider('.'), createSymbolRule("value")));
}
magma.compile.rule.Rule createNamedWithTypeParams(){Rule name = createSymbolRule("name");
        Rule typeParams = new NodeListRule("type-params", new FoldingDivider(new ValueFolder()), createSymbolRule("value"));return OptionalNodeListRule("type-params", StripRule(InfixRule(name, "<", SuffixRule(typeParams, ">"), FirstLocator())), name);
}
magma.compile.rule.tree.TypeRule createSymbolValueRule(){return TypeRule("symbol-value", createSymbolRule("value"));
}
magma.compile.rule.tree.TypeRule createOperatorRule(magma.compile.rule.LazyRule value, String type, String infix){NodeRule left = new NodeRule("left", value);
        NodeRule right = new NodeRule("right", value);return TypeRule(type, InfixRule(left, infix, right, FirstLocator()));
}
magma.compile.rule.tree.TypeRule createInvocationRule(magma.compile.rule.Rule value){return TypeRule("invocation", StripRule(SuffixRule(InfixRule(NodeRule("caller", value), "(", createArgumentsRule(value), InvocationStartLocator()), ")")));
}
magma.compile.rule.tree.NodeListRule createArgumentsRule(magma.compile.rule.Rule value){return NodeListRule("arguments", FoldingDivider(DecoratedFolder(ValueFolder())), value);
}
magma.compile.rule.tree.TypeRule createAccessRule(String type, String infix, magma.compile.rule.Rule value){return createDataAccess(type, infix, value, createSymbolRule("property"));
}
magma.compile.rule.tree.TypeRule createDataAccess(String type, String infix, magma.compile.rule.Rule value, magma.compile.rule.Rule property){return TypeRule(type, InfixRule(NodeRule("child", value), infix, property, LastLocator()));
}
magma.compile.rule.tree.TypeRule createStringRule(){return TypeRule("string", StripRule(PrefixRule("\"", SuffixRule(StringRule("value"), "\""))));
}
magma.compile.rule.tree.TypeRule createTernaryRule(magma.compile.rule.LazyRule value){NodeRule condition = new NodeRule("condition", value);
        NodeRule whenTrue = new NodeRule("when-true", value);
        NodeRule whenFalse = new NodeRule("when-false", value);return TypeRule("ternary", InfixRule(condition, "?", InfixRule(whenTrue, ":", whenFalse, FirstLocator()), FirstLocator()));
}
magma.compile.rule.tree.TypeRule createNumberRule(){return TypeRule("number", StripRule(FilterRule(NumberFilter(), StringRule("value"))));
}
magma.compile.rule.tree.TypeRule createNotRule(magma.compile.rule.LazyRule value){return TypeRule("not", PrefixRule("!", NodeRule("child", value)));
}
magma.compile.rule.tree.TypeRule createCharRule(){return TypeRule("char", PrefixRule("'", SuffixRule(StringRule("value"), "'")));
}

