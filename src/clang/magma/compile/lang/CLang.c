#include "CLang.h"
struct Rule createCRootRule(){return TypeRule("root", CommonLang.createBlockRule(createCRootSegmentRule()));
}
struct Rule createCRootSegmentRule(){return OrRule(Lists.of(createIncludeRule(), TypeRule("ifndef", PrefixRule("#ifndef ", SuffixRule(StringRule("value"), "\n"))), TypeRule("define", PrefixRule("#define ", SuffixRule(StringRule("value"), "\n"))), TypeRule("endif", PrefixRule("#endif\n", EmptyRule())), createStructRule(), createFunctionRule(), createExpansionRule()));
}
struct TypeRule createExpansionRule(){StringRule name = new StringRule("name");
        InfixRule childRule = new InfixRule(name, " = ", new NodeRule("type", createTypeRule()), new FirstLocator());return TypeRule("expansion", PrefixRule("// expand ", SuffixRule(childRule, "\n")));
}
struct Rule createFunctionRule(){Rule definitionRule = createDefinitionsRule();
        Rule definition = new NodeRule("definition", definitionRule);
        Rule params = CommonLang.createParamsRule(definitionRule);

        Rule block = CommonLang.createContentRule(new StripRule(new SuffixRule(params, ")")), createStatementRule());
        Rule block1 = new OptionalNodeRule("content",
                block,
                new SuffixRule(params, ");\n")
        );return TypeRule("function", InfixRule(definition, "(", block1, FirstLocator()));
}
struct Rule createStatementRule(){return OrRule(Lists.of(createWhitespaceRule(), createReturnRule(createValueRule()), createIfRule(), SuffixRule(createInvocationRule(createValueRule()), ";"), createForRule(), createAssignmentRule(), createPostfixRule(), createElseRule(), createWhileRule()));
}
struct Rule createValueRule(){LazyRule value = new LazyRule();value.set(OrRule(Lists.of(createSymbolValueRule(), createAddRule(value), createInvocationRule(value), createAccessRule("data-access", ".", value), createStringRule(), createTernaryRule(value), createNumberRule(), createNotRule(value))));return value;
}
struct Rule createStructRule(){Rule name = CommonLang.createNamedWithTypeParams();
        Rule contentRule = CommonLang.createContentRule(name, createStructMemberRule());return TypeRule("struct", PrefixRule("struct ", SuffixRule(contentRule, ";\n")));
}
struct OrRule createStructMemberRule(){return createDefinitionsRule();
}
struct OrRule createDefinitionsRule(){return OrRule(Lists.of(TypeRule("definition", CommonLang.createDefinitionRule(createTypeRule())), createFunctionalDefinitionType()));
}
struct Rule createFunctionalDefinitionType(){Rule returns = new NodeRule("return", createTypeRule());
        Rule params = new NodeListRule("params", new FoldingDivider(new ValueFolder()), createTypeRule());
        Rule maybeParams = new OptionalNodeListRule("params", params, new EmptyRule());
        Rule right = new InfixRule(new PrefixRule("*", new StringRule("name")), ")(", new SuffixRule(maybeParams, ")"), new FirstLocator());return TypeRule("functional-definition", InfixRule(returns, "(", right, FirstLocator()));
}
struct Rule createTypeRule(){LazyRule type = new LazyRule();type.set(OrRule(Lists.of(TypeRule("struct-type", PrefixRule("struct ", StringRule("value"))), TypeRule("ref", SuffixRule(NodeRule("child", type), "*")), CommonLang.createSymbolTypeRule(), CommonLang.createGenericRule(type))));return type;
}
struct Rule createIncludeRule(){NodeListRule path = new NodeListRule("path", new CharDivider('/'), new StringRule("value"));return TypeRule("include", PrefixRule("#include \"", SuffixRule(path, ".h\"\n")));
}

