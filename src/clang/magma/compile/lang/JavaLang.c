#include "JavaLang.h"
Rule createJavaRootRule(){return TypeRule("root", CommonLang.createBlockRule(createJavaRootSegmentRule()));
}
OrRule createJavaRootSegmentRule(){return OrRule(Lists.of(createWhitespaceRule(), createImportRule("package ", "package"), createImportRule("import ", "import"), createClassRule(), createInterfaceRule(), createRecordRule()));
}
TypeRule createRecordRule(){Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule stripRule = new StripRule(new SuffixRule(createParamsRule(createDefinitionRule(createTypeRule())), ")"));
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(stripRule, "implements ", new NodeRule("supertype", createTypeRule()), new FirstLocator()),
                stripRule
        ));

        Rule right1 = createContentRule(beforeContent, createClassMemberRule());
        Rule right = new InfixRule(namedWithTypeParams, "(", right1, new FirstLocator());return TypeRule("record", InfixRule(StringRule("modifiers"), "record ", right, FirstLocator()));
}
TypeRule createInterfaceRule(){Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, "extends ", new StringRule("supertype"), new FirstLocator()),
                namedWithTypeParams
        ));

        InfixRule contentRule = createContentRule(beforeContent, createClassMemberRule());return TypeRule("interface", InfixRule(StringRule("modifiers"), "interface ", contentRule, FirstLocator()));
}
TypeRule createClassRule(){Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, "implements ", new StringRule("supertype"), new FirstLocator()),
                namedWithTypeParams
        ));

        Rule right1 = createContentRule(beforeContent, createClassMemberRule());return TypeRule("class", InfixRule(StringRule("modifiers"), "class ", right1, FirstLocator()));
}
Rule createClassMemberRule(){return OrRule(Lists.of(createWhitespaceRule(), createMethodRule(), createDefinitionStatementRule(), createInitializationRule()));
}
TypeRule createInitializationRule(){Rule definitionRule = createDefinitionRule(createTypeRule());
        TypeRule definition = new TypeRule("definition", definitionRule);
        return new TypeRule("initialization", new SuffixRule(new InfixRule(new NodeRule("definition", definition), "=", new StringRule("value"), new FirstLocator()), ";"));
}
TypeRule createDefinitionStatementRule(){return TypeRule("definition", SuffixRule(createDefinitionRule(createTypeRule()), ";"));
}
Rule createMethodRule(){Rule definition = new NodeRule("definition", createTypedDefinitionRule());
        Rule params = createParamsRule(createTypedDefinitionRule());

        Rule withParams = new OrRule(Lists.of(
                createContentRule(new StripRule(new SuffixRule(params, ")")), createStatementRule()),
                new SuffixRule(params, ");")
        ));return TypeRule("method", InfixRule(definition, "(", withParams, FirstLocator()));
}
TypeRule createTypedDefinitionRule(){return TypeRule("definition", createDefinitionRule(createTypeRule()));
}
Rule createStatementRule(){LazyRule value = new LazyRule();
        LazyRule statement = new LazyRule();statement.set(OrRule(Lists.of(createWhitespaceRule(), createReturnRule(createValueRule(value, statement)), createIfRule(), SuffixRule(createInvocationRule(value), ";"), createForRule(), createAssignmentRule(), createPostfixRule(), createElseRule(), createWhileRule())));return statement;
}
Rule createValueRule(LazyRule value, Rule statement){value.set(OrRule(Lists.of(createSymbolValueRule(), createLambdaRule(value, statement), createOperatorRule(value, "add", "+"), createOperatorRule(value, "subtract", "-"), createOperatorRule(value, "and", "&&"), createOperatorRule(value, "or", "||"), createConstructionRule(value), createInvocationRule(value), createDataAccessRule(value), createAccessRule("method-access", "::", value), createStringRule(), createNumberRule(), createTernaryRule(value), createNotRule(value), createQuantityRule(value), createCharRule())));return value;
}
TypeRule createQuantityRule(LazyRule value){return TypeRule("quantity", PrefixRule("(", SuffixRule(NodeRule("child", value), ")")));
}
TypeRule createDataAccessRule(LazyRule value){Rule property = createSymbolRule("property");
        Rule afterSeparator = new OrRule(Lists.of(
                new PrefixRule("<", new InfixRule(new NodeListRule("type-arguments", new FoldingDivider(new ValueFolder()), createTypeRule()), ">", property, new LastLocator())),
                property
        ));return createDataAccess("data-access", ".", value, afterSeparator);
}
Rule createLambdaRule(Rule value, Rule statement){Rule left = createSymbolRule("param-name");
        Rule param = createSymbolRule("value");
        Rule params = new NodeListRule("params", new FoldingDivider(new DecoratedFolder(new ValueFolder())), new OrRule(Lists.of(
                createWhitespaceRule(),
                param
        )));

        Rule beforeArrow = new OrRule(Lists.of(
                left,
                new StripRule(new PrefixRule("(", new SuffixRule(params, ")")))
        ));
        InfixRule withValue = new InfixRule(beforeArrow, "->", new NodeRule("child", value), new FirstLocator());return TypeRule("lambda", OrRule(Lists.of(createContentRule(StripRule(SuffixRule(beforeArrow, "->")), statement), withValue)));
}
TypeRule createConstructionRule(LazyRule value){return TypeRule("construction", StripRule(PrefixRule("new ", SuffixRule(InfixRule(NodeRule("type", createTypeRule()), "(", createArgumentsRule(value), InvocationStartLocator()), ")"))));
}
Rule createTypeRule(){LazyRule type = new LazyRule();type.set(OrRule(Lists.of(createArrayRule(type), createGenericRule(type), createQualifiedRule())));return type;
}
TypeRule createArrayRule(LazyRule type){return TypeRule("array", SuffixRule(NodeRule("child", type), "[]"));
}
Rule createImportRule(String prefix, String type){NodeListRule namespace = new NodeListRule("namespace", new CharDivider('.'), new StringRule("value"));return TypeRule(type, StripRule(PrefixRule(prefix, SuffixRule(namespace, ";"))));
}
