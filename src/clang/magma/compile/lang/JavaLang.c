#include "/jvm/collect/list/Lists.h"
#include "/magma/compile/rule/LazyRule.h"
#include "/magma/compile/rule/Rule.h"
#include "/magma/compile/rule/divide/CharDivider.h"
#include "/magma/compile/rule/divide/FoldingDivider.h"
#include "/magma/compile/rule/divide/ValueFolder.h"
#include "/magma/compile/rule/locate/FirstLocator.h"
#include "/magma/compile/rule/text/InfixRule.h"
#include "/magma/compile/rule/text/PrefixRule.h"
#include "/magma/compile/rule/text/StringRule.h"
#include "/magma/compile/rule/text/StripRule.h"
#include "/magma/compile/rule/text/SuffixRule.h"
#include "/magma/compile/rule/tree/NodeListRule.h"
#include "/magma/compile/rule/tree/NodeRule.h"
#include "/magma/compile/rule/tree/OrRule.h"
#include "/magma/compile/rule/tree/TypeRule.h"
#include "/static magma/compile/lang/CommonLang/*.h"
struct JavaLang{};
Rule createJavaRootRule(){
}
OrRule createJavaRootSegmentRule(){
}
TypeRule createRecordRule(){
}
TypeRule createInterfaceRule(){
}
TypeRule createClassRule(){
}
Rule createClassMemberRule(){
}
TypeRule createInitializationRule(){
}
TypeRule createDefinitionStatementRule(){
}
Rule createMethodRule(){
}
Rule createStatementRule(){
}
TypeRule createElseRule(){
}
TypeRule createPostfixRule(){
}
TypeRule createAssignmentRule(){
}
TypeRule createForRule(){
}
TypeRule createInvocationRule(){
}
TypeRule createIfRule(){
}
TypeRule createReturnRule(){
}
Rule createTypeRule(){
}
TypeRule createArrayRule(LazyRule type){
}
Rule createGenericRule(Rule type){
}
Rule createNamedWithTypeParams(){
}
Rule createImportRule(String prefix, String type){
}
