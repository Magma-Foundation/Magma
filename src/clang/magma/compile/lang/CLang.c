#include "CLang.h"
struct Rule createCRootRule(}{return new TypeRule(, CommonLang.createBlockRule(createCRootSegmentRule()));}struct Rule createCRootSegmentRule(}{return new OrRule(Lists.of(
                createIncludeRule(),
                new TypeRule(, new PrefixRule(, new SuffixRule(new StringRule(), ))),
                new TypeRule(, new PrefixRule(, new SuffixRule(new StringRule(), ))),
                new TypeRule(, new PrefixRule(, new EmptyRule())),
                createStructRule(),
                createFunctionRule()
        ));}struct TypeRule createFunctionRule(}{Rule definitionRule = CommonLang.createDefinitionRule(createTypeRule());
        NodeRule definition = new NodeRule(, definitionRule);
        NodeListRule params = CommonLang.createParamsRule(definitionRule);

        Rule block = CommonLang.createContentRule(new StripRule(new SuffixRule(params, )), createStatementRule());
        Rule block1 = new OptionalNodeRule(,
                block,
                new SuffixRule(params, )
        );

        return new TypeRule(, new InfixRule(definition, , block1, new FirstLocator()));}struct Rule createStatementRule(}{return new OrRule(Lists.of(
                createWhitespaceRule(),
                createReturnRule(),
                createIfRule(),
                createInvocationRule(),
                createForRule(),
                createAssignmentRule(),
                createPostfixRule(),
                createElseRule(),
                createWhileRule()
        ));}struct TypeRule createStructRule(}{StringRule name = new StringRule();
        InfixRule contentRule = CommonLang.createContentRule(name, createStructMemberRule());
        return new TypeRule(, new PrefixRule(, new SuffixRule(contentRule, )));}struct OrRule createStructMemberRule(}{return new OrRule(Lists.of(
                new TypeRule(, CommonLang.createDefinitionRule(createTypeRule())),
                new TypeRule(, new NodeRule(, createTypeRule()))
        ));}struct Rule createTypeRule(}{LazyRule type = new LazyRule();
        type.set(new OrRule(Lists.of(
                new TypeRule(, new PrefixRule(, new StringRule())),
                new TypeRule(, new SuffixRule(new NodeRule(, type), )),
                CommonLang.createSymbolTypeRule(),
                CommonLang.createGenericRule(type)
        )));return type;}struct Rule createIncludeRule(}{NodeListRule path = new NodeListRule(, new CharDivider(), new StringRule());
        return new TypeRule(, new PrefixRule(, new SuffixRule(path, )));}