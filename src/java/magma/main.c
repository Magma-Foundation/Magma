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
Option_struct V find_Main(){
}
Map_struct K_struct V put_Main(){
}
Map_struct K_struct V putAll_Main(){
}
Stream_Tuple_struct K_struct V stream_Main(){
}
Stream_struct T stream_Main(){
}
List_struct T add_Main(){
}
Option_Tuple_struct T_List_struct T pop_Main(){
}
Option_struct T last_Main(){
}
List_struct T setLast_Main(){
}
List_struct T sort_Main(){
}
List_struct T mapLast_Main(){
}
int size_Main(){
}
Stream_struct T concat_Map(){
}
struct C collect_Map(){
}
struct R fold_Map(){
}
Stream_struct R map_Map(){
}
Option_struct T next_Map(){
}
Stream_struct T filter_Map(){
}
int allMatch_Map(){
}
Stream_struct R flatMap_Map(){
}
Option_struct R map_List(){
}
Option_struct T or_List(){
}
struct T orElse_List(){
}
int isPresent_List(){
}
struct T orElseGet_List(){
}
Option_struct R flatMap_List(){
}
void ifPresent_List(){
}
int isEmpty_List(){
}
struct C createInitial_Stream(){
}
struct C fold_Stream(){
}
Option_struct T next_Option(){
}
struct R match_Collector(){
}
Result_struct T_struct R mapErr_Collector(){
}
Result_struct R_struct X mapValue_Collector(){
}
Result_struct R_struct X flatMapValue_Collector(){
}
char* display_Head(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError apply_Result(){
}
Option_struct R map_Error(){
}
Option_struct T or_Error(){
}
struct T orElse_Error(){
}
int isPresent_Error(){
}
struct T orElseGet_Error(){
}
Option_struct R flatMap_Error(){
}
void ifPresent_Error(){
}
int isEmpty_Error(){
}
Option_struct R map_Rule(){
}
Option_struct T or_Rule(){
}
struct T orElse_Rule(){
}
int isPresent_Rule(){
}
struct T orElseGet_Rule(){
}
Option_struct R flatMap_Rule(){
}
void ifPresent_Rule(){
}
int isEmpty_Rule(){
}
struct DivideState new_Some(){
}
Option_struct DivideState popAndAppend_Some(){
}
Stream_char_ref stream_Some(){
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
Option_Tuple_struct Character_struct DivideState pop_Some(){
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
Option_struct CompilerState exit_DivideState(){
}
Option_char_ref createInitial_Tuple(){
}
Option_char_ref fold_Tuple(){
}
struct RangeHead new_CompilerState(){
}
Option_struct Integer next_CompilerState(){
}
struct SingleHead new_Joiner(){
}
Option_struct T next_Joiner(){
}
Stream_struct T concat_RangeHead(){
}
struct C collect_RangeHead(){
}
struct R fold_RangeHead(){
}
Stream_struct R map_RangeHead(){
}
Option_struct T next_RangeHead(){
}
Stream_struct T filter_RangeHead(){
}
int allMatch_RangeHead(){
}
Stream_struct R flatMap_RangeHead(){
}
List_struct T createInitial_SingleHead(){
}
List_struct T fold_SingleHead(){
}
Option_struct Integer createInitial_HeadedStream(){
}
Option_struct Integer fold_HeadedStream(){
}
struct CompileError new_ListCollector(){
}
char* display_ListCollector(){
}
char* format_ListCollector(){
}
int computeMaxDepth_ListCollector(){
}
struct R match_Max(){
}
Result_struct T_struct R mapErr_Max(){
}
Result_struct R_struct X mapValue_Max(){
}
Result_struct R_struct X flatMapValue_Max(){
}
struct R match_CompileError(){
}
Result_struct T_struct R mapErr_CompileError(){
}
Result_struct R_struct X mapValue_CompileError(){
}
Result_struct R_struct X flatMapValue_CompileError(){
}
struct OrState new_Err(){
}
struct OrState withValue_Err(){
}
struct OrState withError_Err(){
}
Result_Tuple_struct CompilerState_char_ref_List_struct CompileError toResult_Err(){
}
char* display_Ok(){
}
char* display_OrState(){
}
Option_struct T next_ThrowableError(){
}
Stream_struct T from_ApplicationError(){
}
Stream_struct R empty_ApplicationError(){
}
Map_struct K_struct V put_EmptyHead(){
}
Map_struct K_struct V putAll_EmptyHead(){
}
Stream_Tuple_struct K_struct V stream_EmptyHead(){
}
Option_struct V find_EmptyHead(){
}
Map_struct K_struct V empty_Streams(){
}
struct Node new_Maps(){
}
struct Node new_Maps(){
}
struct Node withString_Maps(){
}
Option_char_ref find_Maps(){
}
struct Node merge_Maps(){
}
void __main___JavaMap(){
}
Option_struct ApplicationError compileWithInput_JavaMap(){
}
Result_char_ref_struct IOException readString_JavaMap(){
}
Option_struct IOException writeString_JavaMap(){
}
Result_char_ref_struct CompileError compile_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileStatements_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileAll_JavaMap(){
}
char* mergeStatements_JavaMap(){
}
Stream_char_ref divideStatements_JavaMap(){
}
Stream_char_ref divideAll_JavaMap(){
}
Option_struct DivideState divideDoubleQuotes_JavaMap(){
}
Option_struct DivideState divideSingleQuotes_JavaMap(){
}
struct DivideState foldStatementChar_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileRootSegment_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileOr_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileImport_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compilePackage_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError createPrefixError_JavaMap(){
}
char* generatePlaceholder_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileClass_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileToStruct_JavaMap(){
}
char* removeTypeParams_JavaMap(){
}
Err_Tuple_struct CompilerState_char_ref_struct CompileError createSuffixErr_JavaMap(){
}
Result_Tuple_struct CompilerState_struct T_struct CompileError createInfixErr_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileClassSegment_JavaMap(){
}
struct Rule compileWithType_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileWhitespace_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileInitialization_JavaMap(){
}
char* compileValue_JavaMap(){
}
int isNumber_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileMethod_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileConstructionHead_JavaMap(){
}
int validateModifiers_JavaMap(){
}
int isModifier_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileStatement_JavaMap(){
}
Result_Tuple_struct CompilerState_struct Node_struct CompileError parseDefinition_JavaMap(){
}
char* getLast_JavaMap(){
}
Result_Tuple_struct CompilerState_struct Node_struct CompileError getTupleCompileErrorResult_JavaMap(){
}
Result_char_ref_struct CompileError generateDefinition_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileDefinitionTypeProperty_JavaMap(){
}
int findTypeSeparator_JavaMap(){
}
int isSymbol_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileType_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileSymbolType_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compileGeneric_JavaMap(){
}
char* mergeValues_JavaMap(){
}
struct DivideState foldValueChar_JavaMap(){
}
Result_Tuple_struct CompilerState_char_ref_struct CompileError compilePrimitive_JavaMap(){
}
Option_char_ref compilePrimitiveText_JavaMap(){
}
int main(){
	return 0;
}
