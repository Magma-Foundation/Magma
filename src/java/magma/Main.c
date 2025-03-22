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
	var source = temp();
	var output = temp();
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
	var reduced = temp();
	return temp;
}
Optional_List_String parseAll(List_String segments, Optional_String (*compiler)(String)){
	return temp;
}
StringBuilder mergeStatements(StringBuilder buffer, String element){
	return temp;
}
List_String divide(String input, State (*applier)(State, Character)){
	var queue = temp();
	var state = temp();
	var current = state;
	while (temp) {
	}
	return temp;
}
Optional_State divideWithEscapes(State current, State (*applicator)(State, Character)){
	var maybeNext = temp();
	if (temp) {
	}
	char next = temp();
	return temp;
}
Optional_State divideAtDoubleQuotes(State initial, char c){
	if (temp) {
	}
	var current = temp();
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
	var appended = temp();
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
	var stripped = temp();
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
	var stripped = temp();
	if (temp) {
	}
	if (temp) {
	}
	if (temp) {
	}
	if (temp) {
	}
	var maybeInitialization = temp();
	if (temp) {
	}
	if (temp) {
	}
	return temp;
}
Optional_String computeValue(String input){
	if (temp) {
	}
	var stripped = temp();
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
	var stripped = temp();
	return temp;
}
Optional_String generateType(MapNode node){
	return temp;
}
Optional_MapNode parseType(String input){
	var maybeArray = temp();
	if (temp) {
	}
	var maybeGeneric = temp();
	if (temp) {
	}
	var stripped = temp();
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
	var returns = temp();
	var params = temp();
	var joinedParams = temp();
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
