#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
struct Main {
};
struct Main new(){
}
struct State {
};
int temp;
int temp;
int temp;
int temp;
struct State new(){
}
struct State new(){
}
struct State State_popAndAppend(){
	return temp;
}
struct char State_pop(){
	return temp;
}
struct boolean State_isEmpty(){
	return temp;
}
struct State State_append(){buffer.append(c)
	return temp;
}
struct State new(){
}
struct State new(){
}
struct State new(){
}
struct boolean State_isShallow(){
	return temp;
}
struct boolean State_isLevel(){
	return temp;
}
struct Temp State_segments(){
	return temp;
}
struct Main new(){
}
struct Main new(){
}
struct record Main_Node(){
}
struct Main new(){
}
int temp;
struct void __main__(){
	struct Path source = Paths.get(".", "src", "java", "magma", "Main.java");magma.Files.readString(source)
                .match(input -> runWithSource(source, input), Optional::of)
                .ifPresent(__lambda0__)
}
struct Temp Main_runWithSource(){
	struct String string = compileStatements(input, __lambda1__).orElse("");
	struct Path target = source.resolveSibling("main.c");
	return temp;
}
struct Temp Main_compileStatements(){
	return temp;
}
struct Temp Main_compile(){
	struct Temp maybeOutput = Optional.of(new StringBuilder());
	for(;;){
	}
	return temp;
}
struct StringBuilder Main_merge(){
	return temp;
}
struct Temp Main_divideStatements(){
	struct Temp queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(__lambda2__);
	struct State state = Temp();
	while(1) {
	}

	return temp;
}
struct Temp Main_divideSingleQuotes(){
	if (1) {
	}
	struct State appended = state.append(c);
	struct char maybeSlash = appended.pop();
	struct State withMaybeSlash = appended.append(maybeSlash);
	struct State withEscape = condition ? whenTrue : whenFalse;
	return temp;
}
struct Temp Main_divideDoubleQuotes(){
	if (1) {
	}
	struct State current = state.append(c);
	while(1) {
	}

	return temp;
}
struct State Main_processStatementChar(){
	struct State appended = state.append(c);
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	return temp;
}
struct Temp Main_compileRootSegment(){
	struct Temp maybeWhitespace = compileWhitespace(input);
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	struct Temp maybeClass = compileClass(input);
	if (1) {
	}
	return temp;
}
struct Temp Main_compileClass(){
	struct int classIndex = input.indexOf("class ");
	if (1) {
	}
	struct String afterKeyword = input.substring(classIndex + "class ".length());
	struct int contentStart = afterKeyword.indexOf("{");
	if (1) {
	}
	struct String name = afterKeyword.substring(0, contentStart).strip();
	if (1) {
	}
	struct String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
	if (1) {
	}
	struct String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
	return temp;
}
struct Temp Main_invalidate(){System.err.println("Invalid " + type + ": " + input)
	return temp;
}
struct Temp Main_compileClassSegment(){
	struct Temp maybeWhitespace = compileWhitespace(input);
	if (1) {
	}
	struct Temp maybeClass = compileClass(input);
	if (1) {
	}
	struct Temp inputType = compileMethod(input, structName);
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	return temp;
}
struct Temp Main_compileMethod(){
	struct int paramStart = input.indexOf("(");
	if (1) {
	}
	struct String header = input.substring(0, paramStart).strip();
	return temp;
}
struct Temp Main_compileDefinition(){
	return temp;
}
struct Temp Main_parseDefinition(){
	struct int nameSeparator = header.lastIndexOf(" ");
	if (1) {
	}
	struct String beforeName = header.substring(0, nameSeparator).strip();
	struct String oldName = header.substring(nameSeparator + " ".length()).strip();
	struct int typeSeparator = beforeName.lastIndexOf(" ");
	struct String inputType = condition ? whenTrue : whenFalse;
	return temp;
}
struct Node Main_modifyDefinition(){
	struct String newName = condition ? whenTrue : whenFalse;
	return temp;
}
struct Temp Main_compileStatement(){
	struct Temp maybeWhitespace = compileWhitespace(input);
	if (1) {
	}
	struct String stripped = input.strip();
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	return temp;
}
struct Temp Main_compileValue(){
	struct String stripped = value.strip();
	if (1) {
	}
	struct Temp maybeInvocation = compileInvocation(value);
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	struct int accessSeparator = value.indexOf(".");
	if (1) {
	}
	if (1) {
	}
	return temp;
}
struct Temp Main_compileOperator(){
	struct int operatorIndex = value.indexOf(operator);
	if (1) {
	}
	struct String leftString = value.substring(0, operatorIndex).strip();
	struct String rightString = value.substring(operatorIndex + 1).strip();
	return temp;
}
struct Temp Main_compileInvocation(){
	if (1) {
	}
	struct String withoutEnd = value.substring(0, value.length() - ")".length());
	struct int argsStart = withoutEnd.lastIndexOf("(");
	if (1) {
	}
	struct String inputCaller = withoutEnd.substring(0, argsStart);
	struct String inputArguments = withoutEnd.substring(argsStart + "(".length());
	struct Temp arguments = divideStatements(inputArguments, __lambda3__);
	return temp;
}
struct State Main_divideValues(){
	if (1) {
	}
	struct State append = state.append(c);
	if (1) {
	}
	if (1) {
	}
	return temp;
}
struct StringBuilder Main_mergeValues(){
	if (1) {
	}
	return temp;
}
struct boolean Main_isNumber(){
	for(;;){
	}
	return temp;
}
struct String Main_generateMethod(){
	return temp;
}
struct Temp Main_generateDefinition(){
	return temp;
}
struct Temp Main_compileType(){
	struct String stripped = type.strip();
	if (1) {
	}
	if (1) {
	}
	return temp;
}
struct Temp Main_generateStructType(){
	return temp;
}
struct boolean Main_isSymbol(){
	for(;;){
	}
	return temp;
}
struct Temp Main_compileWhitespace(){
	if (1) {
	}
	return temp;
}
int main(){
	__main__();
	return 0;
}
