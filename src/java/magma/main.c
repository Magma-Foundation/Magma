/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.Arrays; */
/* import java.util.Collections; */
/* import java.util.Deque; */
/* import java.util.HashMap; */
/* import java.util.LinkedList; */
/* import java.util.List; */
/* import java.util.Map; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Consumer; */
/* import java.util.function.Function; */
/* import java.util.function.Supplier; */
/* import java.util.stream.Collectors; */
/* import java.util.stream.IntStream; */
/* import java.util.stream.Stream; */
struct Main {
};
struct State {
};
/* private final Deque<Character> queue; */
/* private final List<String> segments; */
/* private StringBuilder buffer; */
/* private int depth; */
/* private State(Deque<Character> queue, List<String> segments, StringBuilder buffer, int depth) {
            this.queue = queue;
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } */
public State_State(Deque_char queue){
}
int State_isShallow(){
}
State State_exit(){
}
State State_enter(){
}
State State_advance(){
}
int State_isLevel(){
}
State State_append(char c){
}
int State_hasNext(){
}
char State_pop(){
}
/*  */
/* private record Tuple<A, B>(A left, B right) {
    } */
/* private static final Map<String, Function<List<String>, Option<String>>> expandables = new HashMap<>(); */
/* private static List<Tuple<String, List<String>>> toExpand = new ArrayList<>(); */
/* private static final List<Tuple<String, List<String>>> visited = new ArrayList<>(); */
void __main__(char** args){
}
char* Main_compile(char* input){
}
Option_char* Main_expand(Tuple_char*_List_char* expansion){
}
Option_char* Main_compileStatements(char* input, Option_char* (*compiler)(char*)){
}
Option_char* Main_compileAndMergeAll(List_char* segments, Option_char* (*compiler)(char*), BiFunction_StringBuilder_char*_StringBuilder merger){
}
char* Main_mergeAll(List_char* list, BiFunction_StringBuilder_char*_StringBuilder merger){
}
Option_List_char* Main_compileAll(List_char* segments, Option_char* (*compiler)(char*)){
}
StringBuilder Main_mergeStatements(StringBuilder output, char* compiled){
}
List_char* Main_divideAll(char* input, BiFunction_State_char_State folder){
}
Option_State Main_foldDoubleQuotes(State current, char c){
}
Option_State Main_foldSingleQuotes(State current, char c){
}
State Main_foldStatementChar(State state, char c){
}
char* Main_compileRootSegment(char* input){
}
Option_char* Main_compileClass(char* input){
}
Option_char* Main_compileToStruct(char* input, char* infix){
}
Option_char* Main_assembleStruct(char* name, char* inputContent, List_char* typeParams, List_char* typeArguments){
}
int Main_isSymbol(char* input){
}
char* Main_compileClassSegment(char* input, char* structName, List_char* typeParams, List_char* typeArguments){
}
Option_char* Main_compileMethod(char* input, char* structName, List_char* typeParams, List_char* typeArguments){
}
Option_char* Main_compileValues(char* input, Option_char* (*compileDefinition)(char*)){
}
List_char* Main_divideValues(char* input){
}
State Main_foldValueChar(State state, char c){
}
StringBuilder Main_mergeValues(StringBuilder builder, char* element){
}
StringBuilder Main_mergeDelimited(StringBuilder builder, char* element, char* delimiter){
}
Option_char* Main_compileDefinition(char* definition, List_char* stack, List_char* typeParams, List_char* typeArguments){
}
int Main_findTypeSeparator(char* input){
}
Option_char* Main_compileType(char* input, Option_char* maybeName, List_char* typeParams, List_char* typeArguments){
}
char* Main_generateFunctionalDefinition(Option_char* name, List_char* paramTypes, char* returnType){
}
char* Main_generateSimpleDefinition(char* type, Option_char* maybeName){
}
char* Main_generatePlaceholder(char* input){
}
/*  */
/*  */
struct Option_String {
};
Option_String Option_String_of(String value){
}
Option_String Option_String_empty(){
}
Stream_String Option_String_stream(Option_String option){
}
String Option_String_orElse(String other){
}
Option_R Option_String_flatMap(Option_R (*mapper)(String)){
}
Option_R Option_String_map(R (*mapper)(String)){
}
Option_String Option_String_or(Option_String (*other)()){
}
String Option_String_orElseGet(String (*other)()){
}
void Option_String_ifPresent(Consumer_String consumer){
}
/*  */
struct Option_List<String> {
};
Option_List<String> Option_List<String>_of(List<String> value){
}
Option_List<String> Option_List<String>_empty(){
}
Stream_List<String> Option_List<String>_stream(Option_List<String> option){
}
List<String> Option_List<String>_orElse(List<String> other){
}
Option_R Option_List<String>_flatMap(Option_R (*mapper)(List<String>)){
}
Option_R Option_List<String>_map(R (*mapper)(List<String>)){
}
Option_List<String> Option_List<String>_or(Option_List<String> (*other)()){
}
List<String> Option_List<String>_orElseGet(List<String> (*other)()){
}
void Option_List<String>_ifPresent(Consumer_List<String> consumer){
}
/*  */
struct Option_State {
};
Option_State Option_State_of(State value){
}
Option_State Option_State_empty(){
}
Stream_State Option_State_stream(Option_State option){
}
State Option_State_orElse(State other){
}
Option_R Option_State_flatMap(Option_R (*mapper)(State)){
}
Option_R Option_State_map(R (*mapper)(State)){
}
Option_State Option_State_or(Option_State (*other)()){
}
State Option_State_orElseGet(State (*other)()){
}
void Option_State_ifPresent(Consumer_State consumer){
}
/*  */
struct Option_T {
};
Option_T Option_T_of(T value){
}
Option_T Option_T_empty(){
}
Stream_T Option_T_stream(Option_T option){
}
T Option_T_orElse(T other){
}
Option_R Option_T_flatMap(Option_R (*mapper)(T)){
}
Option_R Option_T_map(R (*mapper)(T)){
}
Option_T Option_T_or(Option_T (*other)()){
}
T Option_T_orElseGet(T (*other)()){
}
void Option_T_ifPresent(Consumer_T consumer){
}
/*  */
struct Option_R {
};
Option_R Option_R_of(R value){
}
Option_R Option_R_empty(){
}
Stream_R Option_R_stream(Option_R option){
}
R Option_R_orElse(R other){
}
Option_R Option_R_flatMap(Option_R (*mapper)(R)){
}
Option_R Option_R_map(R (*mapper)(R)){
}
Option_R Option_R_or(Option_R (*other)()){
}
R Option_R_orElseGet(R (*other)()){
}
void Option_R_ifPresent(Consumer_R consumer){
}
/*  */
int main(int argc, char **argv){
	__main__(argv);
	return 0;
}