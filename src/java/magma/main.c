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
struct Rule extends BiFunction {
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
	struct List structs_None;
	struct List methods_None;
	struct List frames_None;
};
struct Joiner {
};
struct RangeHead {
	struct int length_Tuple;
	struct int counter_Tuple = 0;
};
struct SingleHead {
	struct T element_CompilerState;
	int retrieved_CompilerState = /* false */;
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
	struct Map strings_CompileError;
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
struct List add_Main(){
}
struct Option pop_Main(){
}
struct Option last_Main(){
}
struct List setLast_Main(){
}
struct List sort_Main(){
}
struct List mapLast_Main(){
}
struct int size_Main(){
}
struct Stream concat_Main(){
}
struct C collect_Main(){
}
struct R fold_Main(){
}
struct Stream map_Main(){
}
struct Option next_Main(){
}
struct Stream filter_Main(){
}
int allMatch_Main(){
}
struct Stream flatMap_Main(){
}
struct Option map_List(){
}
struct Option or_List(){
}
struct T orElse_List(){
}
int isPresent_List(){
}
struct T orElseGet_List(){
}
struct Option flatMap_List(){
}
void ifPresent_List(){
}
int isEmpty_List(){
}
struct C createInitial_Stream(){
}
struct C fold_Stream(){
}
struct Option next_Option(){
}
struct R match_Collector(){
}
struct Result mapErr_Collector(){
}
struct Result mapValue_Collector(){
}
struct Result flatMapValue_Collector(){
}
struct String display_Head(){
}
struct Option map_Error(){
}
struct Option or_Error(){
}
struct T orElse_Error(){
}
int isPresent_Error(){
}
struct T orElseGet_Error(){
}
struct Option flatMap_Error(){
}
void ifPresent_Error(){
}
int isEmpty_Error(){
}
struct Option map_Rule extends BiFunction(){
}
struct Option or_Rule extends BiFunction(){
}
struct T orElse_Rule extends BiFunction(){
}
int isPresent_Rule extends BiFunction(){
}
struct T orElseGet_Rule extends BiFunction(){
}
struct Option flatMap_Rule extends BiFunction(){
}
void ifPresent_Rule extends BiFunction(){
}
int isEmpty_Rule extends BiFunction(){
}
DivideState new(){
}
struct Option popAndAppend_Some(){
}
struct Stream stream_Some(){
}
struct DivideState advance_Some(){
}
struct DivideState append_Some(){
}
int isLevel_Some(){
}
struct DivideState enter_Some(){
}
struct DivideState exit_Some(){
}
int isShallow_Some(){
}
struct Option pop_Some(){
}
struct record DivideState_Some(){
}
CompilerState new(){
}
CompilerState new(){
}
struct CompilerState addStruct_None(){
}
struct CompilerState addMethod_None(){
}
struct CompilerState defineType_None(){
}
struct CompilerState enter_None(){
}
struct Option exit_None(){
}
struct Option createInitial_DivideState(){
}
struct Option fold_DivideState(){
}
RangeHead new(){
}
struct Option next_Tuple(){
}
SingleHead new(){
}
struct Option next_CompilerState(){
}
struct Stream concat_Joiner(){
}
struct C collect_Joiner(){
}
struct R fold_Joiner(){
}
struct Stream map_Joiner(){
}
struct Option next_Joiner(){
}
struct Stream filter_Joiner(){
}
int allMatch_Joiner(){
}
struct Stream flatMap_Joiner(){
}
struct List createInitial_RangeHead(){
}
struct List fold_RangeHead(){
}
struct Option createInitial_SingleHead(){
}
struct Option fold_SingleHead(){
}
CompileError new(){
}
struct String display_HeadedStream(){
}
struct String format_HeadedStream(){
}
struct int computeMaxDepth_HeadedStream(){
}
struct record CompileError_HeadedStream(){
}
struct R match_HeadedStream(){
}
struct Result mapErr_HeadedStream(){
}
struct Result mapValue_HeadedStream(){
}
struct Result flatMapValue_HeadedStream(){
}
struct R match_ListCollector(){
}
struct Result mapErr_ListCollector(){
}
struct Result mapValue_ListCollector(){
}
struct Result flatMapValue_ListCollector(){
}
OrState new(){
}
struct OrState withValue_Max(){
}
struct OrState withError_Max(){
}
struct Result toResult_Max(){
}
struct record OrState_Max(){
}
struct String display_Max(){
}
struct record ThrowableError_Max(){
}
struct String display_Max(){
}
struct record ApplicationError_Max(){
}
struct Option next_Max(){
}
struct Stream from_CompileError(){
}
struct Stream empty_CompileError(){
}
struct Stream from_CompileError(){
}
Node new(){
}
Node new(){
}
struct Node withString_CompileError(){
}
struct Option find_CompileError(){
}
struct Node merge_CompileError(){
}
void __main___Err(){
}
struct Option compileWithInput_Err(){
}
struct Result readString_Err(){
}
struct Option writeString_Err(){
}
struct Result compile_Err(){
}
struct Result compileStatements_Err(){
}
struct Result foldSegment_Err(){
}
struct Stream divideStatements_Err(){
}
struct Option divideDoubleQuotes_Err(){
}
struct Option divideSingleQuotes_Err(){
}
struct DivideState divideStatementChar_Err(){
}
struct Result compileRootSegment_Err(){
}
struct Result compileOr_Err(){
}
struct Result compileImport_Err(){
}
struct Result compilePackage_Err(){
}
struct Result createPrefixError_Err(){
}
struct String generatePlaceholder_Err(){
}
struct Result compileClass_Err(){
}
struct Result compileToStruct_Err(){
}
struct String removeTypeParams_Err(){
}
struct Err createSuffixErr_Err(){
}
struct Result createInfixErr_Err(){
}
struct return generateDefinition_Err(){
}
struct return generateDefinition_Err(){
}
struct Result compileClassSegment_Err(){
}
struct Rule compileWithType_Err(){
}
struct Result compileWhitespace_Err(){
}
struct Result compileInitialization_Err(){
}
struct String compileValue_Err(){
}
int isNumber_Err(){
}
struct Result compileMethod_Err(){
}
struct Result compileConstructionHead_Err(){
}
int validateModifiers_Err(){
}
int isModifier_Err(){
}
struct Result compileStatement_Err(){
}
struct Result parseDefinition_Err(){
}
struct Result getTupleCompileErrorResult_Err(){
}
struct Result generateDefinition_Err(){
}
struct Result compileDefinitionTypeProperty_Err(){
}
struct int findTypeSeparator_Err(){
}
int isSymbol_Err(){
}
struct Result compileType_Err(){
}
struct Result compileSymbolType_Err(){
}
struct Result compileGeneric_Err(){
}
struct Result compilePrimitive_Err(){
}
struct Option compilePrimitiveText_Err(){
}
int main(){
	return 0;
}
