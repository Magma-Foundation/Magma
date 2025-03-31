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
magma.compile.rule.Rule createCRootRule();
magma.compile.rule.Rule createCRootSegmentRule();
magma.compile.rule.tree.TypeRule createExpansionRule();
magma.compile.rule.Rule createFunctionRule();
magma.compile.rule.Rule createStatementRule();
magma.compile.rule.Rule createValueRule();
magma.compile.rule.Rule createStructRule();
magma.compile.rule.Rule createStructMemberRule();
magma.compile.rule.tree.OrRule createDefinitionsRule();
magma.compile.rule.Rule createFunctionalDefinitionType();
magma.compile.rule.Rule createTypeRule();
magma.compile.rule.Rule createIncludeRule();
#endif

