#ifndef magma_compile_lang_JavaLang
#define magma_compile_lang_JavaLang
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/compile/rule/LazyRule.h"
#include "../../../magma/compile/rule/Rule.h"
#include "../../../magma/compile/rule/divide/CharDivider.h"
#include "../../../magma/compile/rule/divide/DecoratedFolder.h"
#include "../../../magma/compile/rule/divide/FoldingDivider.h"
#include "../../../magma/compile/rule/divide/ValueFolder.h"
#include "../../../magma/compile/rule/locate/FirstLocator.h"
#include "../../../magma/compile/rule/locate/LastLocator.h"
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
struct JavaLang{
};
magma.compile.rule.Rule createJavaRootRule();
magma.compile.rule.tree.OrRule createJavaRootSegmentRule();
magma.compile.rule.tree.TypeRule createRecordRule();
magma.compile.rule.tree.TypeRule createInterfaceRule();
magma.compile.rule.tree.TypeRule createClassRule();
magma.compile.rule.Rule createClassMemberRule();
magma.compile.rule.tree.TypeRule createInitializationRule();
magma.compile.rule.tree.TypeRule createDefinitionStatementRule();
magma.compile.rule.Rule createMethodRule();
magma.compile.rule.tree.TypeRule createTypedDefinitionRule();
magma.compile.rule.Rule createStatementRule();
magma.compile.rule.Rule createValueRule(magma.compile.rule.LazyRule value, magma.compile.rule.Rule statement);
magma.compile.rule.tree.TypeRule createQuantityRule(magma.compile.rule.LazyRule value);
magma.compile.rule.tree.TypeRule createDataAccessRule(magma.compile.rule.LazyRule value);
magma.compile.rule.Rule createLambdaRule(magma.compile.rule.Rule value, magma.compile.rule.Rule statement);
magma.compile.rule.tree.TypeRule createConstructionRule(magma.compile.rule.LazyRule value);
magma.compile.rule.Rule createTypeRule();
magma.compile.rule.tree.TypeRule createArrayRule(magma.compile.rule.LazyRule type);
magma.compile.rule.Rule createImportRule(String prefix, String type);
#endif
