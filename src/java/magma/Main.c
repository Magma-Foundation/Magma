#include "magma/result/Err.h"
#include "magma/result/Ok.h"
#include "magma/result/Result.h"
#include "magma/result/Results.h"
#include "java/io/IOException.h"
#include "java/nio/file/Files.h"
#include "java/nio/file/Path.h"
#include "java/nio/file/Paths.h"
#include "java/util/LinkedList.h"
#include "java/util/List.h"
#include "java/util/Optional.h"
#include "java/util/function/BiFunction.h"
#include "java/util/function/Function.h"
#include "java/util/regex/Pattern.h"
#include "java/util/stream/Collectors.h"
#include "java/util/stream/IntStream.h"
struct Main {
};
void main(Array_String args){
}
Result__String_CompileException__ compileRoot(String input){
}
Result__String_CompileException__ divideAndCompile(String input, Function__String_Result__String_CompileException____ compiler){
}
Result__String_CompileException__ compileAll(List__String__ segments, Function__String_Result__String_CompileException____ compiler, Function__Tuple__StringBuilder_String___StringBuilder__ merger){
}
StringBuilder mergeStatements(Tuple__StringBuilder_String__ tuple){
}
List__String__ divideByStatements(String input){
}
List__String__ divide(String input, BiFunction__State_Character_State__ divider){
}
State divideStatementChar(State state, char c){
}
Result__String_CompileException__ compileRootSegment(String input){
}
Result__String_CompileException__ invalidate(String input, String type){
}
Result__String_CompileException__ compileClassSegment(String input){
}
Result__String_CompileException__ compileValues(String input, Function__String_Result__String_CompileException____ compiler){
}
State divideValueChar(State state, Character c){
}
StringBuilder mergeValues(Tuple__StringBuilder_String__ tuple){
}
StringBuilder mergeDelimited(Tuple__StringBuilder_String__ tuple, String delimiter){
}
Result__String_CompileException__ compileDefinition(String definition){
}
Result__String_CompileException__ compileType(String input){
}
boolean isSymbol(String input){
}
Result__String_CompileException__ createInfixError(String input, String infix){
}
Result__String_CompileException__ compileWhitespace(String input){
}
