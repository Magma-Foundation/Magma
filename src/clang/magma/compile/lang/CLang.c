#include "CLang.h"
struct Rule createCRootRule(){return new TypeRule(, CommonLang.createBlockRule(createCRootSegmentRule()));}struct Rule createCRootSegmentRule(){return new OrRule(Lists.of(
                createIncludeRule(),
                new TypeRule(, new PrefixRule(, new SuffixRule(new StringRule(), ))),
                new TypeRule(, new PrefixRule(, new SuffixRule(new StringRule(), ))),
                new TypeRule(, new PrefixRule(, new EmptyRule())),
                createStructRule(),
                createFunctionRule(),
                createExpansionRule()
        ));}struct TypeRule createExpansionRule(){StringRule name = new StringRule();
        InfixRule childRule = new InfixRule(name, , new NodeRule(, createTypeRule()), new FirstLocator());
        return new TypeRule(, new PrefixRule(, new SuffixRule(childRule, )));}struct TypeRule createFunctionRule(){OrRule definitionRule = createDefinitionsRule();
        NodeRule definition = new NodeRule(, definitionRule);
        NodeListRule params = CommonLang.createParamsRule(definitionRule);

        Rule block = CommonLang.createContentRule(new StripRule(new SuffixRule(params, )), createStatementRule());
        Rule block1 = new OptionalNodeRule(,
                block,
                new SuffixRule(params, )
        );

        return new TypeRule(, new InfixRule(definition, , block1, new FirstLocator()));}struct Rule createStatementRule(){return new OrRule(Lists.of(
                createWhitespaceRule(),
                createReturnRule(),
                createIfRule(),
                createInvocationRule(),
                createForRule(),
                createAssignmentRule(),
                createPostfixRule(),
                createElseRule(),
                createWhileRule()
        ));}struct TypeRule createStructRule(){StringRule name = new StringRule();
        InfixRule contentRule = CommonLang.createContentRule(name, createStructMemberRule());
        return new TypeRule(, new PrefixRule(, new SuffixRule(contentRule, )));}struct OrRule createStructMemberRule(){return createDefinitionsRule();}struct OrRule createDefinitionsRule(){return new OrRule(Lists.of(
                new TypeRule(, CommonLang.createDefinitionRule(createTypeRule())),
                createFunctionalDefinitionType()
        ));}struct TypeRule createFunctionalDefinitionType(){NodeRule returns = new NodeRule(, createTypeRule());
        NodeListRule params = new NodeListRule(, new FoldingDivider(new ValueFolder()), createTypeRule());
        InfixRule right = new InfixRule(new PrefixRule(, new StringRule()), , new SuffixRule(params, ), new FirstLocator());
        return new TypeRule(, new InfixRule(returns, , right, new FirstLocator()));}struct Rule createTypeRule(){LazyRule type = new LazyRule();
        type.set(new OrRule(Lists.of(
                new TypeRule(, new PrefixRule(, new StringRule())),
                new TypeRule(, new SuffixRule(new NodeRule(, type), )),
                CommonLang.createSymbolTypeRule(),
                CommonLang.createGenericRule(type)
        )));return type;}struct Rule createIncludeRule(){NodeListRule path = new NodeListRule(, new CharDivider(), new StringRule());
        return new TypeRule(, new PrefixRule(, new SuffixRule(path, )));}