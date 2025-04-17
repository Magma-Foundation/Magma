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
	List_char_ref structs_DivideState;
	List_char_ref methods_DivideState;
	List_List_char_ref frames_DivideState;
};
struct Joiner {
};
struct RangeHead {
	int length_CompilerState;
	int counter_CompilerState;
};
struct SingleHead {
	T element_Joiner;
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
struct Streams {
};
struct JavaMap {
};
struct Maps {
};
struct Node {
	Map_char_ref_char_ref strings_Maps;
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
Option_V find_Main(){
}
Map_K_V put_Main(){
}
Map_K_V putAll_Main(){
}
Stream_Tuple_K_V stream_Main(){
}
Stream_T stream_Main(){
}
List_T add_Main(){
}
Option_Tuple_T_List_T pop_Main(){
}
Option_T last_Main(){
}
List_T setLast_Main(){
}
List_T sort_Main(){
}
List_T mapLast_Main(){
}
int size_Main(){
}
Stream_T concat_Map(){
}
C collect_Map(){
}
R fold_Map(){
}
Stream_R map_Map(){
}
Option_T next_Map(){
}
Stream_T filter_Map(){
}
int allMatch_Map(){
}
Stream_R flatMap_Map(){
}
Option_R map_List(){
}
Option_T or_List(){
}
T orElse_List(){
}
int isPresent_List(){
}
T orElseGet_List(){
}
Option_R flatMap_List(){
}
void ifPresent_List(){
}
int isEmpty_List(){
}
C createInitial_Stream(){
}
C fold_Stream(){
}
Option_T next_Option(){
}
R match_Collector(){
}
Result_T_R mapErr_Collector(){
}
Result_R_X mapValue_Collector(){
}
Result_R_X flatMapValue_Collector(){
}
char* display_Head(){
}
Result_Tuple_CompilerState_char_ref_CompileError apply_Result(){
}
Option_R map_Error(){
}
Option_T or_Error(){
}
T orElse_Error(){
}
int isPresent_Error(){
}
T orElseGet_Error(){
}
Option_R flatMap_Error(){
}
void ifPresent_Error(){
}
int isEmpty_Error(){
}
Option_R map_Rule(){
}
Option_T or_Rule(){
}
T orElse_Rule(){
}
int isPresent_Rule(){
}
T orElseGet_Rule(){
}
Option_R flatMap_Rule(){
}
void ifPresent_Rule(){
}
int isEmpty_Rule(){
}
struct DivideState new_Some(){
}
Option_DivideState popAndAppend_Some(){
}
Stream_char_ref stream_Some(){
}
DivideState advance_Some(){
}
DivideState append_Some(){
}
int isLevel_Some(){
}
DivideState enter_Some(){
}
DivideState exit_Some(){
}
int isShallow_Some(){
}
Option_Tuple_Character_DivideState pop_Some(){
}
struct CompilerState new_DivideState(){
}
struct CompilerState new_DivideState(){
}
CompilerState addStruct_DivideState(){
}
CompilerState addMethod_DivideState(){
}
CompilerState defineType_DivideState(){
}
CompilerState enter_DivideState(){
}
Option_CompilerState exit_DivideState(){
}
Option_char_ref createInitial_Tuple(){
}
Option_char_ref fold_Tuple(){
}
struct RangeHead new_CompilerState(){
}
Option_Integer next_CompilerState(){
}
struct SingleHead new_Joiner(){
}
Option_T next_Joiner(){
}
Stream_T concat_RangeHead(){
}
C collect_RangeHead(){
}
R fold_RangeHead(){
}
Stream_R map_RangeHead(){
}
Option_T next_RangeHead(){
}
Stream_T filter_RangeHead(){
}
int allMatch_RangeHead(){
}
Stream_R flatMap_RangeHead(){
}
List_T createInitial_SingleHead(){
}
List_T fold_SingleHead(){
}
Option_Integer createInitial_HeadedStream(){
}
Option_Integer fold_HeadedStream(){
}
struct CompileError new_ListCollector(){
}
char* display_ListCollector(){
}
char* format_ListCollector(){
}
int computeMaxDepth_ListCollector(){
}
R match_Max(){
}
Result_T_R mapErr_Max(){
}
Result_R_X mapValue_Max(){
}
Result_R_X flatMapValue_Max(){
}
R match_CompileError(){
}
Result_T_R mapErr_CompileError(){
}
Result_R_X mapValue_CompileError(){
}
Result_R_X flatMapValue_CompileError(){
}
struct OrState new_Err(){
}
OrState withValue_Err(){
}
OrState withError_Err(){
}
Result_Tuple_CompilerState_char_ref_List_CompileError toResult_Err(){
}
char* display_Ok(){
}
char* display_OrState(){
}
Option_T next_ThrowableError(){
}
Stream_T from_ApplicationError(){
}
Stream_R empty_ApplicationError(){
}
Map_K_V put_EmptyHead(){
}
Map_K_V putAll_EmptyHead(){
}
Stream_Tuple_K_V stream_EmptyHead(){
}
Option_V find_EmptyHead(){
}
Map_K_V empty_Streams(){
}
struct Node new_Maps(){
}
struct Node new_Maps(){
}
Node withString_Maps(){
}
Option_char_ref find_Maps(){
}
Node merge_Maps(){
}
void __main___JavaMap(){
}
Option_ApplicationError compileWithInput_JavaMap(){
}
Result_char_ref_IOException readString_JavaMap(){
}
Option_IOException writeString_JavaMap(){
}
Result_char_ref_CompileError compile_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileStatements_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileAll_JavaMap(){
}
char* mergeStatements_JavaMap(){
}
Stream_char_ref divideStatements_JavaMap(){
}
Stream_char_ref divideAll_JavaMap(){
}
Option_DivideState divideDoubleQuotes_JavaMap(){
}
Option_DivideState divideSingleQuotes_JavaMap(){
}
DivideState foldStatementChar_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileRootSegment_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileOr_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileImport_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compilePackage_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError createPrefixError_JavaMap(){
}
char* generatePlaceholder_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileClass_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileToStruct_JavaMap(){
}
char* removeTypeParams_JavaMap(){
}
Err_Tuple_CompilerState_char_ref_CompileError createSuffixErr_JavaMap(){
}
Result_Tuple_CompilerState_T_CompileError createInfixErr_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileClassSegment_JavaMap(){
}
Rule compileWithType_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileWhitespace_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileInitialization_JavaMap(){
}
char* compileValue_JavaMap(){
}
int isNumber_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileMethod_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileConstructionHead_JavaMap(){
}
int validateModifiers_JavaMap(){
}
int isModifier_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileStatement_JavaMap(){
}
Result_Tuple_CompilerState_Node_CompileError parseDefinition_JavaMap(){
}
char* getLast_JavaMap(){
}
Result_Tuple_CompilerState_Node_CompileError getTupleCompileErrorResult_JavaMap(){
}
Result_char_ref_CompileError generateDefinition_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileDefinitionTypeProperty_JavaMap(){
}
int findTypeSeparator_JavaMap(){
}
int isSymbol_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileType_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileSymbolType_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compileGeneric_JavaMap(){
}
char* mergeValues_JavaMap(){
}
DivideState foldValueChar_JavaMap(){
}
Result_Tuple_CompilerState_char_ref_CompileError compilePrimitive_JavaMap(){
}
Option_char_ref compilePrimitiveText_JavaMap(){
}
int main(){
	return 0;
}
