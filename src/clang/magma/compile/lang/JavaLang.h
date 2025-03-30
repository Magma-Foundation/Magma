#ifndef magma_compile_lang_JavaLang
#define magma_compile_lang_JavaLang
#include "../../../windows/collect/list/Lists.h"
#include "../../../magma/compile/rule/LazyRule.h"
#include "../../../magma/compile/rule/Rule.h"
#include "../../../magma/compile/rule/divide/CharDivider.h"
#include "../../../magma/compile/rule/divide/FoldingDivider.h"
#include "../../../magma/compile/rule/divide/ValueFolder.h"
#include "../../../magma/compile/rule/locate/FirstLocator.h"
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
struct JavaLang{};
struct Rule createJavaRootRule();
struct OrRule createJavaRootSegmentRule();
struct TypeRule createRecordRule();
struct TypeRule createInterfaceRule();
struct TypeRule createClassRule();
struct Rule createClassMemberRule();
struct TypeRule createInitializationRule();
struct TypeRule createDefinitionStatementRule();
struct Rule createMethodRule();
struct TypeRule createTypedDefinitionRule();
struct Rule createStatementRule();
struct Rule createTypeRule();
struct TypeRule createArrayRule(struct LazyRule type);
struct Rule createNamedWithTypeParams();
struct Rule createImportRule(struct String prefix, struct String type);
#endif
