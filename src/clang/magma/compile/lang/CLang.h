#ifndef magma_compile_lang_CLang
#define magma_compile_lang_CLang
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/compile/rule/LazyRule.h"
#include "../../../magma/compile/rule/OptionalNodeListRule.h"
#include "../../../magma/compile/rule/OptionalNodeRule.h"
#include "../../../magma/compile/rule/Rule.h"
#include "../../../magma/compile/rule/divide/CharDivider.h"
#include "../../../magma/compile/rule/divide/FoldingDivider.h"
#include "../../../magma/compile/rule/divide/ValueFolder.h"
#include "../../../magma/compile/rule/locate/FirstLocator.h"
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
#include "../../../static magma/compile/lang/CommonLang/*.h"
struct CLang{
};
Rule createCRootRule();
Rule createCRootSegmentRule();
TypeRule createExpansionRule();
Rule createFunctionRule();
Rule createStatementRule();
Rule createValueRule();
Rule createStructRule();
Rule createStructMemberRule();
OrRule createDefinitionsRule();
Rule createFunctionalDefinitionType();
Rule createTypeRule();
Rule createIncludeRule();
#endif
