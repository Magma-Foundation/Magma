#ifndef magma_compile_lang_CommonLang
#define magma_compile_lang_CommonLang
#include "../../../windows/collect/list/Lists.h"
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
struct CommonLang{};
struct InfixRule createContentRule(struct Rule beforeContent, struct Rule childRule);
struct Rule createBlockRule(struct Rule childRule);
struct TypeRule createSymbolTypeRule();
struct StripRule createSymbolRule(struct String propertyKey);
struct Rule createDefinitionRule(struct Rule type);
struct NodeListRule createParamsRule(struct Rule definition);
struct TypeRule createWhitespaceRule();
struct TypeRule createAssignmentRule();
struct TypeRule createInvocationRule();
struct TypeRule createElseRule();
struct TypeRule createPostfixRule();
struct TypeRule createForRule();
struct TypeRule createIfRule();
struct TypeRule createReturnRule();
struct TypeRule createWhileRule();
struct Rule createGenericRule(struct Rule type);
#endif
