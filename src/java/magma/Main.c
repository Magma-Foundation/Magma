#include "magma/result/Err.h"
#include "magma/result/Ok.h"
#include "magma/result/Result.h"
#include "magma/result/Results.h"
#include "java/io/IOException.h"
#include "java/nio/file/Files.h"
#include "java/nio/file/Path.h"
#include "java/nio/file/Paths.h"
#include "java/util/Arrays.h"
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
void main(String[] args){
}
Result<String, CompileException> compileRoot(String input){
}
Result<String, CompileException> divideAndCompile(String input, Function<String, Result<String, CompileException>> compiler){
}
Result<String, CompileException> compileAll(List<String> segments, Function<String, Result<String, CompileException>> compiler, Function<Tuple<StringBuilder, String>, StringBuilder> merger){
}
StringBuilder mergeStatements(Tuple<StringBuilder, String> tuple){
}
List<String> divideByStatements(String input){
}
List<String> divide(String input, BiFunction<State, Character, State> divider){
}
State divideStatementChar(State state, char c){
}
Result<String, CompileException> compileRootSegment(String input){
}
Result<String, CompileException> invalidate(String input, String type){
}
Result<String, CompileException> compileClassSegment(String input){
}
Result<String, CompileException> compileValues(String input){
}
State divideValueChar(State state, Character c){
}
StringBuilder mergeValues(Tuple<StringBuilder, String> tuple){
}
Result<String, CompileException> compileDefinition(String definition){
}
Err<String, CompileException> createInfixError(String input, String infix){
}
Result<String, CompileException> compileWhitespace(String input){
}
