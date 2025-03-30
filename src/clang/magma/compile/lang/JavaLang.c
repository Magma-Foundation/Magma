#include "JavaLang.h"
struct Rule createJavaRootRule(){return TypeRule("root", CommonLang.createBlockRule(createJavaRootSegmentRule()));
}
struct OrRule createJavaRootSegmentRule(){return OrRule(Lists.of(createWhitespaceRule(), createImportRule("package ", "package"), createImportRule("import ", "import"), createClassRule(), createInterfaceRule(), createRecordRule()));
}
struct TypeRule createRecordRule(){Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule stripRule = new StripRule(new SuffixRule(createParamsRule(createDefinitionRule(createTypeRule())), ")"));
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(stripRule, "implements ", new NodeRule("supertype", createTypeRule()), new FirstLocator()),
                stripRule
        ));

        Rule right1 = createContentRule(beforeContent, createClassMemberRule());
        Rule right = new InfixRule(namedWithTypeParams, "(", right1, new FirstLocator());return TypeRule("record", InfixRule(StringRule("modifiers"), "record ", right, FirstLocator()));
}
struct TypeRule createInterfaceRule(){Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, "extends ", new StringRule("supertype"), new FirstLocator()),
                namedWithTypeParams
        ));

        InfixRule contentRule = createContentRule(beforeContent, createClassMemberRule());return TypeRule("interface", InfixRule(StringRule("modifiers"), "interface ", contentRule, FirstLocator()));
}
struct TypeRule createClassRule(){Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, "implements ", new StringRule("supertype"), new FirstLocator()),
                namedWithTypeParams
        ));

        Rule right1 = createContentRule(beforeContent, createClassMemberRule());return TypeRule("class", InfixRule(StringRule("modifiers"), "class ", right1, FirstLocator()));
}
struct Rule createClassMemberRule(){return OrRule(Lists.of(createWhitespaceRule(), createMethodRule(), createDefinitionStatementRule(), createInitializationRule()));
}
struct TypeRule createInitializationRule(){Rule definitionRule = createDefinitionRule(createTypeRule());
        TypeRule definition = new TypeRule("definition", definitionRule);
        return new TypeRule("initialization", new SuffixRule(new InfixRule(new NodeRule("definition", definition), "=", new StringRule("value"), new FirstLocator()), ";"));
}
struct TypeRule createDefinitionStatementRule(){return TypeRule("definition", SuffixRule(createDefinitionRule(createTypeRule()), ";"));
}
struct Rule createMethodRule(){Rule definition = new NodeRule("definition", createTypedDefinitionRule());
        Rule params = createParamsRule(createTypedDefinitionRule());

        Rule withParams = new OrRule(Lists.of(
                createContentRule(new StripRule(new SuffixRule(params, ")")), createStatementRule()),
                new SuffixRule(params, ");")
        ));return TypeRule("method", InfixRule(definition, "(", withParams, FirstLocator()));
}
struct TypeRule createTypedDefinitionRule(){return TypeRule("definition", createDefinitionRule(createTypeRule()));
}
struct Rule createStatementRule(){LazyRule value = new LazyRule();
        LazyRule statement = new LazyRule();statement.set(OrRule(Lists.of(createWhitespaceRule(), createReturnRule(createValueRule(value, statement)), createIfRule(), SuffixRule(createInvocationRule(value), ";"), createForRule(), createAssignmentRule(), createPostfixRule(), createElseRule(), createWhileRule())));return statement;
}
struct Rule createValueRule(struct LazyRule value, struct Rule statement){value.set(OrRule(Lists.of(createSymbolValueRule(), createAddRule(value), createConstructionRule(value), createInvocationRule(value), createDataAccessRule(value), createAccessRule("method-access", "::", value), createStringRule(), createLambdaRule(value, statement), createNumberRule(), createTernaryRule(value), createNotRule(value))));return value;
}
struct TypeRule createDataAccessRule(struct LazyRule value){Rule property = createSymbolRule("property");
        Rule afterSeparator = new OrRule(Lists.of(
                new PrefixRule("<", new InfixRule(new NodeListRule("type-arguments", new FoldingDivider(new ValueFolder()), createTypeRule()), ">", property, new LastLocator())),
                property
        ));return createDataAccess("data-access", ".", value, afterSeparator);
}
struct Rule createLambdaRule(struct Rule value, struct Rule statement){Rule left = createSymbolRule("param-name");
        Rule params = new NodeListRule("params", new FoldingDivider(new DecoratedFolder(new ValueFolder())), createSymbolRule("value"));
        Rule beforeArrow = new OrRule(Lists.of(
                left,
                new StripRule(new PrefixRule("(", new SuffixRule(params, ")")))
        ));
        InfixRule withValue = new InfixRule(beforeArrow, "->", new NodeRule("child", value), new FirstLocator());return TypeRule("lambda", OrRule(Lists.of(createContentRule(StripRule(SuffixRule(beforeArrow, "->")), statement), withValue)));
}
struct TypeRule createConstructionRule(struct LazyRule value){return TypeRule("construction", StripRule(PrefixRule("new ", SuffixRule(InfixRule(NodeRule("type", createTypeRule()), "(", createArgumentsRule(value), InvocationStartLocator()), ")"))));
}
struct Rule createTypeRule(){LazyRule type = new LazyRule();type.set(OrRule(Lists.of(createArrayRule(type), createGenericRule(type), createSymbolTypeRule())));return type;
}
struct TypeRule createArrayRule(struct LazyRule type){return TypeRule("array", SuffixRule(NodeRule("child", type), "[]"));
}
struct Rule createImportRule(struct String prefix, struct String type){NodeListRule namespace = new NodeListRule("namespace", new CharDivider('.'), new StringRule("value"));return TypeRule(type, StripRule(PrefixRule(prefix, SuffixRule(namespace, ";"))));
}

