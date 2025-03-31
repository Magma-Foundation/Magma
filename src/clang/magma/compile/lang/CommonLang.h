#ifndef magma_compile_lang_CommonLang
#define magma_compile_lang_CommonLang
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/compile/rule/LazyRule.h"
#include "../../../magma/compile/rule/OptionalNodeListRule.h"
#include "../../../magma/compile/rule/OptionalNodeRule.h"
#include "../../../magma/compile/rule/Rule.h"
#include "../../../magma/compile/rule/divide/CharDivider.h"
#include "../../../magma/compile/rule/divide/DecoratedFolder.h"
#include "../../../magma/compile/rule/divide/FoldingDivider.h"
#include "../../../magma/compile/rule/divide/StatementFolder.h"
#include "../../../magma/compile/rule/divide/ValueFolder.h"
#include "../../../magma/compile/rule/locate/FirstLocator.h"
#include "../../../magma/compile/rule/locate/LastLocator.h"
#include "../../../magma/compile/rule/text/EmptyRule.h"
#include "../../../magma/compile/rule/text/InfixRule.h"
#include "../../../magma/compile/rule/text/PrefixRule.h"
#include "../../../magma/compile/rule/text/StringRule.h"
#include "../../../magma/compile/rule/text/StripRule.h"
#include "../../../magma/compile/rule/text/SuffixRule.h"
#include "../../../magma/compile/rule/tree/NodeListRule.h"
#include "../../../magma/compile/rule/tree/NodeRule.h"
#include "../../../magma/compile/rule/tree/OrRule.h"
#include "../../../magma/compile/rule/tree/TypeRule.h"
struct CommonLang{
};
magma.compile.rule.text.InfixRule createContentRule(magma.compile.rule.Rule beforeContent, magma.compile.rule.Rule childRule);
magma.compile.rule.Rule createBlockRule(magma.compile.rule.Rule childRule);
magma.compile.rule.Rule createSymbolRule(String propertyKey);
magma.compile.rule.Rule createDefinitionRule(magma.compile.rule.Rule type);
magma.compile.rule.Rule createParamsRule(magma.compile.rule.Rule definition);
magma.compile.rule.tree.TypeRule createWhitespaceRule();
magma.compile.rule.tree.TypeRule createAssignmentRule();
magma.compile.rule.tree.TypeRule createElseRule();
magma.compile.rule.tree.TypeRule createPostfixRule();
magma.compile.rule.tree.TypeRule createForRule();
magma.compile.rule.tree.TypeRule createIfRule();
magma.compile.rule.tree.TypeRule createReturnRule(magma.compile.rule.Rule value);
magma.compile.rule.tree.TypeRule createWhileRule();
magma.compile.rule.Rule createGenericRule(magma.compile.rule.Rule type);
magma.compile.rule.Rule createQualifiedRule();
magma.compile.rule.Rule createNamedWithTypeParams();
magma.compile.rule.tree.TypeRule createSymbolValueRule();
magma.compile.rule.tree.TypeRule createOperatorRule(magma.compile.rule.LazyRule value, String type, String infix);
magma.compile.rule.tree.TypeRule createInvocationRule(magma.compile.rule.Rule value);
magma.compile.rule.tree.NodeListRule createArgumentsRule(magma.compile.rule.Rule value);
magma.compile.rule.tree.TypeRule createAccessRule(String type, String infix, magma.compile.rule.Rule value);
magma.compile.rule.tree.TypeRule createDataAccess(String type, String infix, magma.compile.rule.Rule value, magma.compile.rule.Rule property);
magma.compile.rule.tree.TypeRule createStringRule();
magma.compile.rule.tree.TypeRule createTernaryRule(magma.compile.rule.LazyRule value);
magma.compile.rule.tree.TypeRule createNumberRule();
magma.compile.rule.tree.TypeRule createNotRule(magma.compile.rule.LazyRule value);
magma.compile.rule.tree.TypeRule createCharRule();
#endif
