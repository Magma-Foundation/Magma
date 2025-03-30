#include "CommonLang.h"
struct InfixRule createContentRule(struct Rule beforeContent, struct Rule childRule}{Rule children1 = createBlockRule(childRule);
        Rule right = new StripRule(new SuffixRule(children1, ));
        return new InfixRule(beforeContent, , right, new FirstLocator());}struct Rule createBlockRule(struct Rule childRule}{Rule children = new NodeListRule(, new FoldingDivider(new DecoratedFolder(new StatementFolder())), childRule);
        return new NodeRule(, new TypeRule(, children));}struct TypeRule createSymbolTypeRule(}{return new TypeRule(, createSymbolRule());}struct StripRule createSymbolRule(struct String propertyKey}{return new StripRule(new SymbolRule(new StringRule(propertyKey)));}struct Rule createDefinitionRule(struct Rule type}{NodeListRule modifiers = new NodeListRule(, new CharDivider(), new StringRule());
        NodeRule typeProperty = new NodeRule(, type);
        Rule beforeName = new OrRule(Lists.of(
                new InfixRule(modifiers, , typeProperty, new TypeSeparatorLocator()),
                typeProperty
        ));

        return new StripRule(new InfixRule(beforeName, , createSymbolRule(), new LastLocator()));}struct NodeListRule createParamsRule(struct Rule definition}{return new NodeListRule(, new FoldingDivider(new ValueFolder()), new OrRule(Lists.of(
                createWhitespaceRule(),
                definition
        )));}struct TypeRule createWhitespaceRule(}{return new TypeRule(, new StripRule(new EmptyRule()));}struct TypeRule createAssignmentRule(}{return new TypeRule(, new SuffixRule(new InfixRule(new StringRule(), , new StringRule(), new FirstLocator()), ));}struct TypeRule createInvocationRule(}{return new TypeRule(, new SuffixRule(new StringRule(), ));}struct TypeRule createElseRule(}{return new TypeRule(, new StripRule(new PrefixRule(, new StringRule())));}struct TypeRule createPostfixRule(}{return new TypeRule(, new SuffixRule(new StringRule(), ));}struct TypeRule createForRule(}{return new TypeRule(, new StripRule(new PrefixRule(, new StringRule())));}struct TypeRule createIfRule(}{return new TypeRule(, new StripRule(new PrefixRule(, new StringRule())));}struct TypeRule createReturnRule(}{PrefixRule rule = new PrefixRule(, new SuffixRule(new StringRule(), ));
        return new TypeRule(, new StripRule(rule));}struct TypeRule createWhileRule(}{return new TypeRule(, new StripRule(new PrefixRule(, new StringRule())));}