struct List {
};
struct Stream {
};
struct Option {
};
struct Collector {
};
struct Head {
};
struct Result {
};
struct Error {
};
struct Some {
};
struct None {
};
struct DivideState {
};
struct Tuple {
};
struct CompilerState {
};
struct Joiner {
};
struct RangeHead {
	struct int length;
	struct int counter = 0;
};
struct SingleHead {
	struct T element;
	int retrieved = /* false */;
};
struct HeadedStream {
};
struct ListCollector {
};
struct Max {
};
struct CompileError {
};
struct Err {
};
struct Ok {
};
struct OrState {
};
struct ThrowableError {
};
struct ApplicationError {
};
struct EmptyHead {
};
struct Streams {
};
struct Main {
};
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
struct Stream stream(){
}
struct List add(){
}
struct Option pop(){
}
struct T last(){
}
struct List setLast(){
}
struct List sort(){
}
struct Stream concat(){
}
struct C collect(){
}
struct R fold(){
}
struct Stream map(){
}
struct Option next(){
}
struct Stream filter(){
}
int allMatch(){
}
struct Option map(){
}
struct Option or(){
}
struct T orElse(){
}
int isPresent(){
}
struct T orElseGet(){
}
struct Option flatMap(){
}
void ifPresent(){
}
int isEmpty(){
}
struct C createInitial(){
}
struct C fold(){
}
struct Option next(){
}
struct R match(){
}
struct Result mapErr(){
}
struct Result mapValue(){
}
struct Result flatMapValue(){
}
struct String display(){
}
struct Option map(){
}
struct Option or(){
}
struct T orElse(){
}
int isPresent(){
}
struct T orElseGet(){
}
struct Option flatMap(){
}
void ifPresent(){
}
int isEmpty(){
}
struct Option map(){
}
struct Option or(){
}
struct T orElse(){
}
int isPresent(){
}
struct T orElseGet(){
}
struct Option flatMap(){
}
void ifPresent(){
}
int isEmpty(){
}
DivideState new(){
}
struct Option popAndAppend(){
}
struct Stream stream(){
}
struct DivideState advance(){
}
struct DivideState append(){
}
int isLevel(){
}
struct DivideState enter(){
}
struct DivideState exit(){
}
int isShallow(){
}
struct Option pop(){
}
struct record DivideState(){
}
CompilerState new(){
}
struct CompilerState addStruct(){
}
struct CompilerState addMethod(){
}
struct CompilerState defineType(){
}
struct CompilerState enter(){
}
struct CompilerState exit(){
}
struct record CompilerState(){
}
struct Option createInitial(){
}
struct Option fold(){
}
RangeHead new(){
}
struct Option next(){
}
SingleHead new(){
}
struct Option next(){
}
struct Stream concat(){
}
struct C collect(){
}
struct R fold(){
}
struct Stream map(){
}
struct Option next(){
}
struct Stream filter(){
}
int allMatch(){
}
struct Stream flatMap(){
}
struct List createInitial(){
}
struct List fold(){
}
struct Option createInitial(){
}
struct Option fold(){
}
CompileError new(){
}
struct String display(){
}
struct String format(){
}
struct int computeMaxDepth(){
}
struct record CompileError(){
}
struct R match(){
}
struct Result mapErr(){
}
struct Result mapValue(){
}
struct Result flatMapValue(){
}
struct R match(){
}
struct Result mapErr(){
}
struct Result mapValue(){
}
struct Result flatMapValue(){
}
OrState new(){
}
struct OrState withValue(){
}
struct OrState withError(){
}
struct Result toResult(){
}
struct record OrState(){
}
struct String display(){
}
struct record ThrowableError(){
}
struct String display(){
}
struct record ApplicationError(){
}
struct Option next(){
}
struct Stream from(){
}
struct Stream empty(){
}
struct Stream from(){
}
void __main__(){
}
struct Option compileWithInput(){
}
struct Result readString(){
}
struct Option writeString(){
}
struct Result compile(){
}
struct Result compileStatements(){
}
struct Result foldSegment(){
}
struct Stream divideStatements(){
}
struct Option divideDoubleQuotes(){
}
struct Option divideSingleQuotes(){
}
struct DivideState divideStatementChar(){
}
struct Result compileRootSegment(){
}
struct Result compileOr(){
}
struct Result compileImport(){
}
struct Result compilePackage(){
}
struct Result createPrefixError(){
}
struct String generatePlaceholder(){
}
struct Result compileClass(){
}
struct Result compileToStruct(){
}
struct String removeTypeParams(){
}
struct Err createSuffixErr(){
}
struct Err createInfixErr(){
}
struct Result compileClassSegment(){
}
struct Result compileWhitespace(){
}
struct Result compileInitialization(){
}
struct String compileValue(){
}
int isNumber(){
}
struct Result compileMethod(){
}
struct Result compileConstructionHead(){
}
int validateModifiers(){
}
int isModifier(){
}
struct Result compileStatement(){
}
struct Result compileDefinition(){
}
struct Result compileDefinitionTypeProperty(){
}
struct int findTypeSeparator(){
}
int isSymbol(){
}
struct Result compileType(){
}
struct Result compileSymbolType(){
}
struct Result compileGeneric(){
}
struct Result compilePrimitive(){
}
struct Option compilePrimitiveText(){
}
int main(){
	return 0;
}
