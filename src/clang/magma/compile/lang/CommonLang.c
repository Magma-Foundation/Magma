#include "CommonLang.h"
struct InfixRule createContentRule(struct Rule beforeContent, struct Rule childRule){Rule children1 = createBlockRule(childRule);
        Rule right = new StripRule("", new SuffixRule(children1, "}"), "after-braces");return InfixRule(beforeContent, "{", right, FirstLocator());
}
struct Rule createBlockRule(struct Rule childRule){Rule children = new NodeListRule("children", new FoldingDivider(new DecoratedFolder(new StatementFolder())), childRule);return NodeRule("content", TypeRule("block", StripRule("", children, "after-children")));
}
struct TypeRule createSymbolTypeRule(){return TypeRule("symbol-type", createSymbolRule("value"));
}
struct Rule createSymbolRule(struct String propertyKey){return StripRule(FilterRule(SymbolFilter(), StringRule(propertyKey)));
}
struct Rule createDefinitionRule(struct Rule type){NodeListRule modifiers = new NodeListRule("modifiers", new CharDivider(' '), new StringRule("modifier"));

        NodeRule typeProperty = new NodeRule("type", type);
        Rule beforeName = new OptionalNodeRule("modifiers",
                new InfixRule(modifiers, " ", typeProperty, new TypeSeparatorLocator()),
                typeProperty
        );return StripRule(InfixRule(beforeName, " ", createSymbolRule("name"), LastLocator()));
}
struct Rule createParamsRule(struct Rule definition){return OptionalNodeListRule("params", NodeListRule("params", FoldingDivider(ValueFolder()), OrRule(Lists.of(createWhitespaceRule(), definition))), EmptyRule());
}
struct TypeRule createWhitespaceRule(){return TypeRule("whitespace", StripRule(EmptyRule()));
}
struct TypeRule createAssignmentRule(){return new TypeRule("assignment", new SuffixRule(new InfixRule(new StringRule("destination"), "=", new StringRule("source"), new FirstLocator()), ";"));
}
struct TypeRule createElseRule(){return TypeRule("else", StripRule(PrefixRule("else", StringRule("content"))));
}
struct TypeRule createPostfixRule(){return TypeRule("postfix", SuffixRule(StringRule("value"), "++;"));
}
struct TypeRule createForRule(){return TypeRule("for", StripRule(PrefixRule("for ", StringRule("content"))));
}
struct TypeRule createIfRule(){return TypeRule("if", StripRule(PrefixRule("if", StringRule("content"))));
}
struct TypeRule createReturnRule(struct Rule value){PrefixRule rule = new PrefixRule("return ", new SuffixRule(new NodeRule("value", value), ";"));return TypeRule("return", StripRule(rule));
}
struct TypeRule createWhileRule(){return TypeRule("while", StripRule(PrefixRule("while", StringRule("content"))));
}
struct Rule createGenericRule(struct Rule type){Rule typeArguments = new NodeListRule("arguments", new FoldingDivider(new ValueFolder()), type);
        Rule base = new NodeListRule("base", new CharDivider('.'), createSymbolRule("value"));return TypeRule("generic", StripRule(SuffixRule(InfixRule(base, "<", typeArguments, FirstLocator()), ">")));
}
struct Rule createNamedWithTypeParams(){Rule name = createSymbolRule("name");
        Rule typeParams = new NodeListRule("type-params", new FoldingDivider(new ValueFolder()), createSymbolRule("value"));return OptionalNodeListRule("type-params", StripRule(InfixRule(name, "<", SuffixRule(typeParams, ">"), FirstLocator())), name);
}
struct TypeRule createSymbolValueRule(){return TypeRule("symbol-value", createSymbolRule("value"));
}
struct TypeRule createAddRule(struct LazyRule value){return TypeRule("add", InfixRule(NodeRule("left", value), "+", NodeRule("right", value), FirstLocator()));
}
struct TypeRule createInvocationRule(struct Rule value){return TypeRule("invocation", StripRule(SuffixRule(InfixRule(NodeRule("caller", value), "(", createArgumentsRule(value), InvocationStartLocator()), ")")));
}
struct NodeListRule createArgumentsRule(struct Rule value){return NodeListRule("arguments", FoldingDivider(DecoratedFolder(ValueFolder())), value);
}
struct TypeRule createAccessRule(struct String type, struct String infix, struct Rule value){return createDataAccess(type, infix, value, createSymbolRule("property"));
}
struct TypeRule createDataAccess(struct String type, struct String infix, struct Rule value, struct Rule property){return TypeRule(type, InfixRule(NodeRule("child", value), infix, property, LastLocator()));
}
struct TypeRule createStringRule(){return TypeRule("string", StripRule(PrefixRule("\"", SuffixRule(StringRule("value"), "\""))));
}
struct TypeRule createTernaryRule(struct LazyRule value){NodeRule condition = new NodeRule("condition", value);
        NodeRule whenTrue = new NodeRule("when-true", value);
        NodeRule whenFalse = new NodeRule("when-false", value);return TypeRule("ternary", InfixRule(condition, "?", InfixRule(whenTrue, ":", whenFalse, FirstLocator()), FirstLocator()));
}
struct TypeRule createNumberRule(){return TypeRule("number", StripRule(FilterRule(NumberFilter(), StringRule("value"))));
}
struct TypeRule createNotRule(struct LazyRule value){return TypeRule("not", PrefixRule("!", NodeRule("child", value)));
}

