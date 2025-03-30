#include "/jvm/collect/list/Lists.h"
#include "/magma/compile/lang/r/SymbolRule.h"
#include "/magma/compile/rule/Rule.h"
#include "/magma/compile/rule/divide/CharDivider.h"
#include "/magma/compile/rule/divide/DecoratedFolder.h"
#include "/magma/compile/rule/divide/FoldingDivider.h"
#include "/magma/compile/rule/divide/StatementFolder.h"
#include "/magma/compile/rule/divide/ValueFolder.h"
#include "/magma/compile/rule/locate/FirstLocator.h"
#include "/magma/compile/rule/locate/LastLocator.h"
#include "/magma/compile/rule/text/EmptyRule.h"
#include "/magma/compile/rule/text/InfixRule.h"
#include "/magma/compile/rule/text/StringRule.h"
#include "/magma/compile/rule/text/StripRule.h"
#include "/magma/compile/rule/text/SuffixRule.h"
#include "/magma/compile/rule/tree/NodeListRule.h"
#include "/magma/compile/rule/tree/NodeRule.h"
#include "/magma/compile/rule/tree/OrRule.h"
#include "/magma/compile/rule/tree/TypeRule.h"
struct CommonLang{};
InfixRule createContentRule(Rule beforeContent, Rule childRule){
}
Rule createBlockRule(Rule childRule){
}
TypeRule createSymbolTypeRule(){
}
StripRule createSymbolRule(String propertyKey){
}
Rule createDefinitionRule(Rule type){
}
NodeListRule createParamsRule(Rule definition){
}
TypeRule createWhitespaceRule(){
}
