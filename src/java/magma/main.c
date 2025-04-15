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
struct T Some_orElse(struct T other){
}
struct Option<struct R> Some_flatMap(struct Function<struct T, struct Option<struct R>> mapper){
}
struct Option<struct R> Some_map(struct Function<struct T, struct R> mapper){
}
struct Option<struct T> Some_or(struct Option<struct T> (*other)()){
}
struct T Some_orElseGet(struct T (*other)()){
}
/*  */
struct None {
};
struct T None_orElse(struct T other){
}
struct Option<struct R> None_flatMap(struct Function<struct T, struct Option<struct R>> mapper){
}
struct Option<struct R> None_map(struct Function<struct T, struct R> mapper){
}
struct Option<struct T> None_or(struct Option<struct T> (*other)()){
}
struct T None_orElseGet(struct T (*other)()){
}
/*  */
struct Option<struct T> Option_of(struct T value){
}
struct Option<struct T> Option_empty(){
}
struct T Option_orElse(struct T other){
}
struct Option<struct R> Option_flatMap(struct Function<struct T, struct Option<struct R>> mapper){
}
struct Option<struct R> Option_map(struct Function<struct T, struct R> mapper){
}
struct Option<struct T> Option_or(struct Option<struct T> (*other)()){
}
struct T Option_orElseGet(struct T (*other)()){
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
struct public State_State(struct Deque<char> queue){
}
int State_isShallow(){
}
struct State State_exit(){
}
struct State State_enter(){
}
struct State State_advance(){
}
int State_isLevel(){
}
struct State State_append(char c){
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
struct Option<char*> Main_compileStatements(char* input, struct Function<char*, struct Option<char*>> compiler){
}
struct Option<char*> Main_compileAndMergeAll(struct List<char*> segments, struct Function<char*, struct Option<char*>> compiler, struct BiFunction<struct StringBuilder, char*, struct StringBuilder> merger){
}
char* Main_mergeAll(struct List<char*> list, struct BiFunction<struct StringBuilder, char*, struct StringBuilder> merger){
}
struct Option<struct List<char*>> Main_compileAll(struct List<char*> segments, struct Function<char*, struct Option<char*>> compiler){
}
struct StringBuilder Main_mergeStatements(struct StringBuilder output, char* compiled){
}
struct List<char*> Main_divideAll(char* input, struct BiFunction<struct State, char, struct State> folder){
}
struct Option<struct State> Main_foldDoubleQuotes(struct State current, char c){
}
struct Option<struct State> Main_foldSingleQuotes(struct State current, char c){
}
struct State Main_foldStatementChar(struct State state, char c){
}
char* Main_compileRootSegment(char* input){
}
struct Option<char*> Main_compileClass(char* input){
}
struct Option<char*> Main_compileToStruct(char* input, char* infix){
}
int Main_isSymbol(char* input){
}
char* Main_compileClassSegment(char* input, char* structName){
}
struct Option<char*> Main_compileMethod(char* input, char* structName){
}
struct Option<char*> Main_compileValues(char* input, struct Function<char*, struct Option<char*>> compileDefinition){
}
struct List<char*> Main_divideValues(char* input){
}
struct State Main_foldValueChar(struct State state, char c){
}
struct StringBuilder Main_mergeValues(struct StringBuilder builder, char* element){
}
struct Option<char*> Main_compileDefinition(char* definition, struct List<char*> stack){
}
int Main_findTypeSeparator(char* input){
}
struct Option<char*> Main_compileType(char* input, struct Option<char*> maybeName){
}
char* Main_generateSimpleDefinition(char* type, struct Option<char*> maybeName){
}
char* Main_generatePlaceholder(char* input){
}
/*  */
/*  */
int main(int argc, char **argv){
	__main__(argv);
	return 0;
}