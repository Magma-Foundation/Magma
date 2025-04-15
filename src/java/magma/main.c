/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.Arrays; */
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
struct T orElse(struct T other){
}
struct Option<struct R> flatMap(struct Function<struct T, struct Option<struct R>> mapper){
}
struct Option<struct R> map(struct Function<struct T, struct R> mapper){
}
struct Option<struct T> or(struct Option<struct T> (*other)()){
}
struct T orElseGet(struct T (*other)()){
}
/*  */
struct None {
};
struct T orElse(struct T other){
}
struct Option<struct R> flatMap(struct Function<struct T, struct Option<struct R>> mapper){
}
struct Option<struct R> map(struct Function<struct T, struct R> mapper){
}
struct Option<struct T> or(struct Option<struct T> (*other)()){
}
struct T orElseGet(struct T (*other)()){
}
/*  */
struct Option<struct T> of(struct T value){
}
struct Option<struct T> empty(){
}
struct T orElse(struct T other){
}
struct Option<struct R> flatMap(struct Function<struct T, struct Option<struct R>> mapper){
}
struct Option<struct R> map(struct Function<struct T, struct R> mapper){
}
struct Option<struct T> or(struct Option<struct T> (*other)()){
}
struct T orElseGet(struct T (*other)()){
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
struct public State(struct Deque<char> queue){
}
int isShallow(){
}
struct State exit(){
}
struct State enter(){
}
struct State advance(){
}
int isLevel(){
}
struct State append(char c){
}
int hasNext(){
}
char pop(){
}
/*  */
void __main__(char** args){
}
char* compile(char* input){
}
struct Option<char*> compileStatements(char* input, struct Function<char*, struct Option<char*>> compiler){
}
struct Option<char*> compileAndMergeAll(struct List<char*> segments, struct Function<char*, struct Option<char*>> compiler, struct BiFunction<struct StringBuilder, char*, struct StringBuilder> merger){
}
char* mergeAll(struct List<char*> list, struct BiFunction<struct StringBuilder, char*, struct StringBuilder> merger){
}
struct Option<struct List<char*>> compileAll(struct List<char*> segments, struct Function<char*, struct Option<char*>> compiler){
}
struct StringBuilder mergeStatements(struct StringBuilder output, char* compiled){
}
struct List<char*> divideAll(char* input, struct BiFunction<struct State, char, struct State> folder){
}
struct Option<struct State> foldDoubleQuotes(struct State current, char c){
}
struct Option<struct State> foldSingleQuotes(struct State current, char c){
}
struct State foldStatementChar(struct State state, char c){
}
char* compileRootSegment(char* input){
}
struct Option<char*> compileClass(char* input){
}
struct Option<char*> compileToStruct(char* input, char* infix){
}
int isSymbol(char* input){
}
char* compileClassSegment(char* input){
}
struct Option<char*> compileMethod(char* input){
}
struct Option<char*> compileValues(char* input, struct Function<char*, struct Option<char*>> compileDefinition){
}
struct List<char*> divideValues(char* input){
}
struct State foldValueChar(struct State state, char c){
}
struct StringBuilder mergeValues(struct StringBuilder builder, char* s){
}
struct Option<char*> compileDefinition(char* definition){
}
int findTypeSeparator(char* input){
}
struct Option<char*> compileType(char* input, struct Option<char*> maybeName){
}
char* generateSimpleDefinition(char* type, struct Option<char*> maybeName){
}
char* generatePlaceholder(char* input){
}
/*  */
/*  */
int main(int argc, char **argv){
	__main__(argv);
	return 0;
}