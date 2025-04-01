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
InfixRule createContentRule(Rule beforeContent, Rule childRule);
Rule createBlockRule(Rule childRule);
Rule createSymbolRule(String propertyKey);
Rule createDefinitionRule(Rule type);
Rule createParamsRule(Rule definition);
TypeRule createWhitespaceRule();
TypeRule createAssignmentRule();
TypeRule createElseRule();
TypeRule createPostfixRule();
TypeRule createForRule();
TypeRule createIfRule();
TypeRule createReturnRule(Rule value);
TypeRule createWhileRule();
Rule createGenericRule(Rule type);
Rule createQualifiedRule();
Rule createNamedWithTypeParams();
TypeRule createSymbolValueRule();
TypeRule createOperatorRule(LazyRule value, String type, String infix);
TypeRule createInvocationRule(Rule value);
NodeListRule createArgumentsRule(Rule value);
TypeRule createAccessRule(String type, String infix, Rule value);
TypeRule createDataAccess(String type, String infix, Rule value, Rule property);
TypeRule createStringRule();
TypeRule createTernaryRule(LazyRule value);
TypeRule createNumberRule();
TypeRule createNotRule(LazyRule value);
TypeRule createCharRule();
#endif
