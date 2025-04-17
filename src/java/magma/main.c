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
	struct int length_RangeHead;
	struct int counter_RangeHead = 0;
};
struct SingleHead {
	struct T element_SingleHead;
	int retrieved_SingleHead = /* false */;
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
struct Node {
	struct Map strings_Node;
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
// #include <temp.h>
// #include <temp.h>
struct Stream stream_Main(){
}
struct List add_(){
}
struct Option pop_(){
}
struct Option last_(){
}
struct List setLast_(){
}
struct List sort_(){
}
struct List mapLast_(){
}
struct Stream concat_Stream(){
}
struct C collect_(){
}
struct R fold_(){
}
struct Stream map_(){
}
struct Option next_(){
}
struct Stream filter_(){
}
int allMatch_(){
}
struct Stream flatMap_(){
}
struct Option map_Option(){
}
struct Option or_(){
}
struct T orElse_(){
}
int isPresent_(){
}
struct T orElseGet_(){
}
struct Option flatMap_(){
}
void ifPresent_(){
}
int isEmpty_(){
}
struct C createInitial_Collector(){
}
struct C fold_(){
}
struct Option next_Head(){
}
struct R match_Result(){
}
struct Result mapErr_(){
}
struct Result mapValue_(){
}
struct Result flatMapValue_(){
}
struct String display_Error(){
}
struct Option map_Some(){
}
struct Option or_(){
}
struct T orElse_(){
}
int isPresent_(){
}
struct T orElseGet_(){
}
struct Option flatMap_(){
}
void ifPresent_(){
}
int isEmpty_(){
}
struct Option map_None(){
}
struct Option or_(){
}
struct T orElse_(){
}
int isPresent_(){
}
struct T orElseGet_(){
}
struct Option flatMap_(){
}
void ifPresent_(){
}
int isEmpty_(){
}
DivideState new(){
}
struct Option popAndAppend_(){
}
struct Stream stream_(){
}
struct DivideState advance_(){
}
struct DivideState append_(){
}
int isLevel_(){
}
struct DivideState enter_(){
}
struct DivideState exit_(){
}
int isShallow_(){
}
struct Option pop_(){
}
struct record DivideState_DivideState(){
}
CompilerState new(){
}
struct CompilerState addStruct_(){
}
struct CompilerState addMethod_(){
}
struct CompilerState defineType_(){
}
struct CompilerState enter_(){
}
struct CompilerState exit_(){
}
struct record CompilerState_CompilerState(){
}
struct Option createInitial_Joiner(){
}
struct Option fold_(){
}
RangeHead new(){
}
struct Option next_(){
}
SingleHead new(){
}
struct Option next_(){
}
struct Stream concat_HeadedStream(){
}
struct C collect_(){
}
struct R fold_(){
}
struct Stream map_(){
}
struct Option next_(){
}
struct Stream filter_(){
}
int allMatch_(){
}
struct Stream flatMap_(){
}
struct List createInitial_ListCollector(){
}
struct List fold_(){
}
struct Option createInitial_Max(){
}
struct Option fold_(){
}
CompileError new(){
}
struct String display_(){
}
struct String format_(){
}
struct int computeMaxDepth_(){
}
struct record CompileError_CompileError(){
}
struct R match_Err(){
}
struct Result mapErr_(){
}
struct Result mapValue_(){
}
struct Result flatMapValue_(){
}
struct R match_Ok(){
}
struct Result mapErr_(){
}
struct Result mapValue_(){
}
struct Result flatMapValue_(){
}
OrState new(){
}
struct OrState withValue_(){
}
struct OrState withError_(){
}
struct Result toResult_(){
}
struct record OrState_OrState(){
}
struct String display_ThrowableError(){
}
struct record ThrowableError_ThrowableError(){
}
struct String display_ApplicationError(){
}
struct record ApplicationError_ApplicationError(){
}
struct Option next_EmptyHead(){
}
struct Stream from_Streams(){
}
struct Stream empty_(){
}
struct Stream from_Streams(){
}
Node new(){
}
Node new(){
}
struct Node withString_(){
}
struct Option find_(){
}
struct Node merge_(){
}
void __main___(){
}
struct Option compileWithInput_(){
}
struct Result readString_(){
}
struct Option writeString_(){
}
struct Result compile_(){
}
struct Result compileStatements_(){
}
struct Result foldSegment_(){
}
struct Stream divideStatements_(){
}
struct Option divideDoubleQuotes_(){
}
struct Option divideSingleQuotes_(){
}
struct DivideState divideStatementChar_(){
}
struct Result compileRootSegment_(){
}
struct Result compileOr_(){
}
struct Result compileImport_(){
}
struct Result compilePackage_(){
}
struct Result createPrefixError_(){
}
struct String generatePlaceholder_(){
}
struct Result compileClass_(){
}
struct Result compileToStruct_(){
}
struct String removeTypeParams_(){
}
struct Err createSuffixErr_(){
}
struct Result createInfixErr_(){
}
struct Result compileClassSegment_(){
}
struct Result compileWhitespace_(){
}
struct Result compileInitialization_(){
}
struct String compileValue_(){
}
int isNumber_(){
}
struct Result compileMethod_(){
}
struct Result compileConstructionHead_(){
}
int validateModifiers_(){
}
int isModifier_(){
}
struct Result compileStatement_(){
}
struct Result parseDefinition_(){
}
struct Result getTupleCompileErrorResult_(){
}
struct Result generateDefinition_(){
}
struct Result compileDefinitionTypeProperty_(){
}
struct int findTypeSeparator_(){
}
int isSymbol_(){
}
struct Result compileType_(){
}
struct Result compileSymbolType_(){
}
struct Result compileGeneric_(){
}
struct Result compilePrimitive_(){
}
struct Option compilePrimitiveText_(){
}
int main(){
	return 0;
}
