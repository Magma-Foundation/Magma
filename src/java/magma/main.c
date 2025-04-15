/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.Arrays; */
/* import java.util.Collections; */
/* import java.util.Deque; */
/* import java.util.LinkedList; */
/* import java.util.List; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
/* import java.util.function.Supplier; */
/* import java.util.stream.Collectors; */
/* import java.util.stream.IntStream; */
struct Main {
};
struct Option {
};
struct Some {
};
T Some_orElse(T other){
}
Option_R Some_flatMap(Function_T_Option_R mapper){
}
Option_R Some_map(Function_T_R mapper){
}
Option_T Some_or(Option_T (*other)()){
}
T Some_orElseGet(T (*other)()){
}
/*  */
struct None {
};
T None_orElse(T other){
}
Option_R None_flatMap(Function_T_Option_R mapper){
}
Option_R None_map(Function_T_R mapper){
}
Option_T None_or(Option_T (*other)()){
}
T None_orElseGet(T (*other)()){
}
/*  */
Option_T Option_of(T value){
}
Option_T Option_empty(){
}
T Option_orElse(T other){
}
Option_R Option_flatMap(Function_T_Option_R mapper){
}
Option_R Option_map(Function_T_R mapper){
}
Option_T Option_or(Option_T (*other)()){
}
T Option_orElseGet(T (*other)()){
}
/*  */
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
void __main__(char** args){
}
char* Main_compile(char* input){
}
Option_char* Main_compileStatements(char* input, Function_char*_Option_char* compiler){
}
Option_char* Main_compileAndMergeAll(List_char* segments, Function_char*_Option_char* compiler, BiFunction_StringBuilder_char*_StringBuilder merger){
}
char* Main_mergeAll(List_char* list, BiFunction_StringBuilder_char*_StringBuilder merger){
}
Option_List_char* Main_compileAll(List_char* segments, Function_char*_Option_char* compiler){
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
int Main_isSymbol(char* input){
}
char* Main_compileClassSegment(char* input, char* structName){
}
Option_char* Main_compileMethod(char* input, char* structName){
}
Option_char* Main_compileValues(char* input, Function_char*_Option_char* compileDefinition){
}
List_char* Main_divideValues(char* input){
}
State Main_foldValueChar(State state, char c){
}
StringBuilder Main_mergeValues(StringBuilder builder, char* element){
}
StringBuilder Main_mergeDelimited(StringBuilder builder, char* element, char* delimiter){
}
Option_char* Main_compileDefinition(char* definition, List_char* stack){
}
int Main_findTypeSeparator(char* input){
}
Option_char* Main_compileType(char* input, Option_char* maybeName){
}
char* Main_generateSimpleDefinition(char* type, Option_char* maybeName){
}
char* Main_generatePlaceholder(char* input){
}
/*  */
/*  */
int main(int argc, char **argv){
	__main__(argv);
	return 0;
}