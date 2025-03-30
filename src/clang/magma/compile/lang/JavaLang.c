#include "JavaLang.h"
struct Rule createJavaRootRule(){return new TypeRule(, CommonLang.createBlockRule(createJavaRootSegmentRule()));}struct OrRule createJavaRootSegmentRule(){return new OrRule(Lists.of(
                createWhitespaceRule(),
                createImportRule(, ),
                createImportRule(, ),
                createClassRule(),
                createInterfaceRule(),
                createRecordRule()
        ));}struct TypeRule createRecordRule(){Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule stripRule = new StripRule(new SuffixRule(createParamsRule(createDefinitionRule(createTypeRule())), ));
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(stripRule, , new NodeRule(, createTypeRule()), new FirstLocator()),
                stripRule
        ));

        Rule right1 = createContentRule(beforeContent, createClassMemberRule());
        Rule right = new InfixRule(namedWithTypeParams, , right1, new FirstLocator());
        return new TypeRule(, new InfixRule(new StringRule(), , right, new FirstLocator()));}struct TypeRule createInterfaceRule(){Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, , new StringRule(), new FirstLocator()),
                namedWithTypeParams
        ));

        InfixRule contentRule = createContentRule(beforeContent, createClassMemberRule());
        return new TypeRule(, new InfixRule(new StringRule(), , contentRule, new FirstLocator()));}struct TypeRule createClassRule(){Rule namedWithTypeParams = createNamedWithTypeParams();
        Rule beforeContent = new OrRule(Lists.of(
                new InfixRule(namedWithTypeParams, , new StringRule(), new FirstLocator()),
                namedWithTypeParams
        ));

        Rule right1 = createContentRule(beforeContent, createClassMemberRule());
        return new TypeRule(, new InfixRule(new StringRule(), , right1, new FirstLocator()));}struct Rule createClassMemberRule(){return new OrRule(Lists.of(
                createWhitespaceRule(),
                createMethodRule(),
                createDefinitionStatementRule(),
                createInitializationRule()
        ));}struct TypeRule createInitializationRule(){Rule definitionRule = createDefinitionRule(createTypeRule());
        TypeRule definition = new TypeRule(, definitionRule);
        return new TypeRule(, new SuffixRule(new InfixRule(new NodeRule(, definition), , new StringRule(), new FirstLocator()), ));}struct TypeRule createDefinitionStatementRule(){return new TypeRule(, new SuffixRule(createDefinitionRule(createTypeRule()), ));}struct Rule createMethodRule(){Rule definition = new NodeRule(, createTypedDefinitionRule());
        Rule params = createParamsRule(createTypedDefinitionRule());

        Rule withParams = new OrRule(Lists.of(
                createContentRule(new StripRule(new SuffixRule(params, )), createStatementRule()),
                new SuffixRule(params, )
        ));

        return new TypeRule(, new InfixRule(definition, , withParams, new FirstLocator()));}struct TypeRule createTypedDefinitionRule(){return new TypeRule(, createDefinitionRule(createTypeRule()));}struct Rule createStatementRule(){return new OrRule(Lists.of(
                createWhitespaceRule(),
                createReturnRule(),
                createIfRule(),
                createInvocationRule(),
                createForRule(),
                createAssignmentRule(),
                createPostfixRule(),
                createElseRule(),
                createWhileRule()
        ));}struct Rule createTypeRule(){LazyRule type = new LazyRule();
        type.set(new OrRule(Lists.of(
                createArrayRule(type),
                createGenericRule(type),
                createSymbolTypeRule()
        )));return type;}struct TypeRule createArrayRule(struct LazyRule type){return new TypeRule(, new SuffixRule(new NodeRule(, type), ));}struct Rule createNamedWithTypeParams(){Rule name = createSymbolRule();
        Rule typeParams = new NodeListRule(, new FoldingDivider(new ValueFolder()), createSymbolRule());

        return new OrRule(Lists.of(
                new StripRule(new InfixRule(name, , new SuffixRule(typeParams, ), new FirstLocator())),
                name
        ));}struct Rule createImportRule(struct String prefix, struct String type){NodeListRule namespace = new NodeListRule(, new CharDivider(), new StringRule());
        return new TypeRule(type, new StripRule(new PrefixRule(prefix, new SuffixRule(namespace, ))));}