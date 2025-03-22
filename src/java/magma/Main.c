#include <magma/locate/FirstLocator.h>
#include <magma/locate/LastLocator.h>
#include <magma/locate/TypeSeparatorLocator.h>
#include <magma/result/Result.h>
#include <magma/result/Results.h>
#include <magma/split/IndexSplitter.h>
#include <magma/split/Splitter.h>
#include <java/io/IOException.h>
#include <java/nio/file/Files.h>
#include <java/nio/file/Path.h>
#include <java/nio/file/Paths.h>
#include <java/util/ArrayList.h>
#include <java/util/Collections.h>
#include <java/util/LinkedList.h>
#include <java/util/List.h>
#include <java/util/Optional.h>
#include <java/util/function/BiFunction.h>
#include <java/util/function/Function.h>
#include <java/util/regex/Pattern.h>
#include <java/util/stream/Collectors.h>
#include <java/util/stream/IntStream.h>
struct Main {
};
void main(String* args){
	auto source = temp();
	auto output = temp();
}
Optional_IOException writeSafe(Path target, String output){
	return temp;
}
Result_String_IOException readSafe(Path source){
	return temp;
}
String compileRoot(String input){
	return temp;
}
Optional_String compileAllStatements(String input, Optional_String (*compiler)(String)){
	return temp;
}
Optional_String compileAll(List_String segments, Optional_String (*compiler)(String), StringBuilder (*merger)(StringBuilder, String)){
	return temp;
}
Optional_String generateAll(List_String output, StringBuilder (*merger)(StringBuilder, String)){
	auto reduced = temp();
	return temp;
}
Optional_List_String parseAll(List_String segments, Optional_String (*compiler)(String)){
	return temp;
}
StringBuilder mergeStatements(StringBuilder buffer, String element){
	return temp;
}
List_String divide(String input, State (*applier)(State, Character)){
	auto queue = temp();
	auto state = temp();
	auto current = state;
	while (temp) {
	}
	return temp;
}
Optional_State divideWithEscapes(State current, State (*applicator)(State, Character)){
	auto maybeNext = temp();
	if (temp) {
	}
	char next = temp();
	return temp;
}
Optional_State divideAtDoubleQuotes(State initial, char c){
	if (temp) {
	}
	auto current = temp();
	while (temp) {
	}
	return temp;
}
Optional_State divideAtSingleQuotes(State current, char next){
	if (temp) {
	}
	return temp;
}
State divideAtStatementChar(State current, char next){
	auto appended = temp();
	if (temp) {
	}
	if (temp) {
	}
	if (temp) {
	}
	if (temp) {
	}
	return temp;
}
Optional_String compileRootSegment(String input){
	auto stripped = temp();
	if (temp) {
	}
	if (temp) {
	}
	if (temp) {
	}
	return temp;
}
Optional_String compileClass(String input){
	return temp;
}
Optional_T invalidate(String type, String input){
	return temp;
}
Optional_T printError(String message){
	temp();
	return temp;
}
Optional_String compileClassSegment(String input){
	if (temp) {
	}
	return temp;
}
Optional_String compileMethod(String input){
	return temp;
}
Optional_String compileStatement(String input){
	auto stripped = temp();
	if (temp) {
	}
	if (temp) {
	}
	if (temp) {
	}
	if (temp) {
	}
	auto maybeInitialization = temp();
	if (temp) {
	}
	if (temp) {
	}
	return temp;
}
Optional_String computeValue(String input){
	if (temp) {
	}
	auto stripped = temp();
	if (temp) {
	}
	return temp;
}
Optional_String truncateLeft(String input, String prefix, Optional_String (*compiler)(String)){
	return temp;
}
Optional_String compileAllValues(String input, Optional_String (*compiler)(String)){
	return temp;
}
Optional_String compileAllValues(String input, Optional_String (*compiler)(String), StringBuilder (*merger)(StringBuilder, String)){
	return temp;
}
StringBuilder mergeDelimited(StringBuilder buffer, String element, String delimiter){
	if (temp) {
	}
	return temp;
}
List_String divideByValues(String input){
	return temp;
}
Optional_String compileDefinition(String input){
	auto stripped = temp();
	return temp;
}
Optional_String generateType(MapNode node){
	return temp;
}
Optional_MapNode parseType(String input){
	auto maybeArray = temp();
	if (temp) {
	}
	auto maybeGeneric = temp();
	if (temp) {
	}
	auto stripped = temp();
	if (temp) {
	}
	return temp;
}
Optional_String generateSymbol(MapNode node){
	return temp;
}
Optional_MapNode modifyGeneric(String name, List_String paramTypes){
	if (temp) {
	}
	if (temp) {
	}
	return temp;
}
Optional_MapNode generateGeneric(String name, List_String paramTypes){
	return temp;
}
MapNode wrapAsSymbol(String value){
	return temp;
}
Optional_String generateFunctionalType(MapNode mapNode, String name){
	auto returns = temp();
	auto params = temp();
	auto joinedParams = temp();
	return temp;
}
boolean isSymbol(String input){
	return temp;
}
Optional_T truncateRight(String input, String suffix, Optional_T (*mapper)(String)){
	return temp;
}
Optional_T split(String input, Splitter splitter, Optional_T (*mapper)(Tuple_String_String)){
	return temp;
}
int main(){
	return 0;
}
