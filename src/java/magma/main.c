struct Map {
};
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
struct Rule {
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
	struct List structs_DivideState;
	struct List methods_DivideState;
	struct List frames_DivideState;
};
struct Joiner {
};
struct RangeHead {
	int length_CompilerState;
	int counter_CompilerState;
};
struct SingleHead {
	struct T element_Joiner;
	int retrieved_Joiner;
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
struct JavaMap {
};
struct Maps {
};
struct Node {
	struct Map strings_Maps;
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
struct Option find_Main(){
}
struct Map put_Main(){
}
struct Map putAll_Main(){
}
struct Stream stream_Main(){
}
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
int size_Main(){
}
struct Stream concat_Map(){
}
struct C collect_Map(){
}
struct R fold_Map(){
}
struct Stream map_Map(){
}
struct Option next_Map(){
}
struct Stream filter_Map(){
}
int allMatch_Map(){
}
struct Stream flatMap_Map(){
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
struct Result apply_Result(){
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
struct Option map_Rule(){
}
struct Option or_Rule(){
}
struct T orElse_Rule(){
}
int isPresent_Rule(){
}
struct T orElseGet_Rule(){
}
struct Option flatMap_Rule(){
}
void ifPresent_Rule(){
}
int isEmpty_Rule(){
}
struct DivideState new_Some(){
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
struct CompilerState new_DivideState(){
}
struct CompilerState new_DivideState(){
}
struct CompilerState addStruct_DivideState(){
}
struct CompilerState addMethod_DivideState(){
}
struct CompilerState defineType_DivideState(){
}
struct CompilerState enter_DivideState(){
}
struct Option exit_DivideState(){
}
struct Option createInitial_Tuple(){
}
struct Option fold_Tuple(){
}
struct RangeHead new_CompilerState(){
}
struct Option next_CompilerState(){
}
struct SingleHead new_Joiner(){
}
struct Option next_Joiner(){
}
struct Stream concat_RangeHead(){
}
struct C collect_RangeHead(){
}
struct R fold_RangeHead(){
}
struct Stream map_RangeHead(){
}
struct Option next_RangeHead(){
}
struct Stream filter_RangeHead(){
}
int allMatch_RangeHead(){
}
struct Stream flatMap_RangeHead(){
}
struct List createInitial_SingleHead(){
}
struct List fold_SingleHead(){
}
struct Option createInitial_HeadedStream(){
}
struct Option fold_HeadedStream(){
}
struct CompileError new_ListCollector(){
}
struct String display_ListCollector(){
}
struct String format_ListCollector(){
}
int computeMaxDepth_ListCollector(){
}
struct R match_Max(){
}
struct Result mapErr_Max(){
}
struct Result mapValue_Max(){
}
struct Result flatMapValue_Max(){
}
struct R match_CompileError(){
}
struct Result mapErr_CompileError(){
}
struct Result mapValue_CompileError(){
}
struct Result flatMapValue_CompileError(){
}
struct OrState new_Err(){
}
struct OrState withValue_Err(){
}
struct OrState withError_Err(){
}
struct Result toResult_Err(){
}
struct String display_Ok(){
}
struct String display_OrState(){
}
struct Option next_ThrowableError(){
}
struct Stream from_ApplicationError(){
}
struct Map put_ApplicationError(){
}
struct Map putAll_ApplicationError(){
}
struct Stream stream_ApplicationError(){
}
struct Option find_ApplicationError(){
}
struct Map empty_EmptyHead(){
}
struct Node new_Maps(){
}
struct Node new_Maps(){
}
struct Node withString_Maps(){
}
struct Option find_Maps(){
}
struct Node merge_Maps(){
}
void __main___JavaMap(){
}
struct Option compileWithInput_JavaMap(){
}
struct Result readString_JavaMap(){
}
struct Option writeString_JavaMap(){
}
struct Result compile_JavaMap(){
}
struct Result compileStatements_JavaMap(){
}
struct Result foldSegment_JavaMap(){
}
struct Stream divideStatements_JavaMap(){
}
struct Option divideDoubleQuotes_JavaMap(){
}
struct Option divideSingleQuotes_JavaMap(){
}
struct DivideState divideStatementChar_JavaMap(){
}
struct Result compileRootSegment_JavaMap(){
}
struct Result compileOr_JavaMap(){
}
struct Result compileImport_JavaMap(){
}
struct Result compilePackage_JavaMap(){
}
struct Result createPrefixError_JavaMap(){
}
struct String generatePlaceholder_JavaMap(){
}
struct Result compileClass_JavaMap(){
}
struct Result compileToStruct_JavaMap(){
}
struct String removeTypeParams_JavaMap(){
}
struct Err createSuffixErr_JavaMap(){
}
struct Result createInfixErr_JavaMap(){
}
struct Result compileClassSegment_JavaMap(){
}
struct Rule compileWithType_JavaMap(){
}
struct Result compileWhitespace_JavaMap(){
}
struct Result compileInitialization_JavaMap(){
}
struct String compileValue_JavaMap(){
}
int isNumber_JavaMap(){
}
struct Result compileMethod_JavaMap(){
}
struct Result compileConstructionHead_JavaMap(){
}
int validateModifiers_JavaMap(){
}
int isModifier_JavaMap(){
}
struct Result compileStatement_JavaMap(){
}
struct Result parseDefinition_JavaMap(){
}
struct String getLast_JavaMap(){
}
struct Result getTupleCompileErrorResult_JavaMap(){
}
struct Result generateDefinition_JavaMap(){
}
struct Result compileDefinitionTypeProperty_JavaMap(){
}
int findTypeSeparator_JavaMap(){
}
int isSymbol_JavaMap(){
}
struct Result compileType_JavaMap(){
}
struct Result compileSymbolType_JavaMap(){
}
struct Result compileGeneric_JavaMap(){
}
struct Result compilePrimitive_JavaMap(){
}
struct Option compilePrimitiveText_JavaMap(){
}
int main(){
	return 0;
}
