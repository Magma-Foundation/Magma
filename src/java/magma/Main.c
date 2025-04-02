// expand Result__String_CompileException__ from Result<String, CompileException>
// expand ArrayList__String__ from ArrayList<String>
// expand Function__Tuple__StringBuilder_String___StringBuilder__ from Function<Tuple__StringBuilder_String__, StringBuilder>
// expand BiFunction__State_Character_State__ from BiFunction<State, Character, State>
// expand Tuple__StringBuilder_String__ from Tuple<StringBuilder, String>
// expand Result__List__String___CompileException__ from Result<List__String__, CompileException>
// expand List__String__ from List<String>
// expand Function__String_Result__String_CompileException____ from Function<String, Result__String_CompileException__>
#include "magma/result/Err.h"
#include "magma/result/Ok.h"
#include "magma/result/Result.h"
#include "magma/result/Results.h"
#include "java/io/IOException.h"
#include "java/nio/file/Files.h"
#include "java/nio/file/Path.h"
#include "java/nio/file/Paths.h"
#include "java/util/ArrayList.h"
#include "java/util/HashMap.h"
#include "java/util/LinkedList.h"
#include "java/util/List.h"
#include "java/util/Map.h"
#include "java/util/Optional.h"
#include "java/util/function/BiFunction.h"
#include "java/util/function/Function.h"
#include "java/util/regex/Pattern.h"
#include "java/util/stream/Collectors.h"
#include "java/util/stream/IntStream.h"
struct Main {
};
int value = temp;
void main(Array_String args){
}
Result__String_CompileException__ compileRoot(String input){
}
ArrayList__String__ addExpansions(List__String__ list){
}
Result__String_CompileException__ divideAndCompile(String input, Function__String_Result__String_CompileException____ compiler){
}
Result__String_CompileException__ compileAll(List__String__ segments, Function__String_Result__String_CompileException____ compiler, Function__Tuple__StringBuilder_String___StringBuilder__ merger){
}
String mergeSegmentsAll(Function__Tuple__StringBuilder_String___StringBuilder__ merger, List__String__ compiled){
}
Result__List__String___CompileException__ compileAllToList(List__String__ segments, Function__String_Result__String_CompileException____ compiler){
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
Result__String_CompileException__ compileMethod(String input){
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
