struct List {
};
struct Stream {
};
struct Collector {
};
struct Head {
};
struct DivideState {
	/* private final */ struct List queue;
	/* private */ struct List segments;
	/* private */ int depth;
	/* private */ char* buffer;/* private DivideState(List<String> segments, String buffer, int depth, List<Character> queue) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.queue = queue;
        } *//* public DivideState(List<Character> queue) {
            this(Lists.empty(), "", 0, queue);
        } */
};
struct Tuple {
};
struct CompilerState {/* public CompilerState() {
            this(Lists.empty(), Lists.empty());
        } */
};
struct Joiner {
};
struct RangeHead {
	/* private final */ int length;
	/* private */ int counter = 0;/* public RangeHead(int length) {
            this.length = length;
        } */
};
struct HeadedStream {
};
struct ListCollector {
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
struct Stream stream(){
}
struct List add(){
}
int isEmpty(){
}
struct T pop(){
}
struct Stream concat(){
}
/* <C> */ struct C collect(){
}
/* <R> */ struct R fold(){
}
/* <R> */ struct Stream map(){
}
struct Optional next(){
}
struct C createInitial(){
}
struct C fold(){
}
struct Optional next(){
}
/* private */ struct DivideState popAndAppend(){
}
/* private */ struct Stream stream(){
}
/* private */ struct DivideState advance(){
}
/* private */ struct DivideState append(){
}
/* public */ int isLevel(){
}
/* public */ struct DivideState enter(){
}
/* public */ struct DivideState exit(){
}
/* public */ int isShallow(){
}
/* public */ int hasNext(){
}
/* public */ char pop(){
}
/* public */ struct CompilerState addStruct(){
}
/* public */ struct CompilerState addMethod(){
}
/* @Override
        public */ struct Optional createInitial(){
}
/* @Override
        public */ struct Optional fold(){
}
/* @Override
        public */ struct Optional next(){
}
/* @Override
        public */ struct Stream concat(){
}
/* @Override
        public <C> */ struct C collect(){
}
/* @Override
        public <R> */ struct R fold(){
}
/* @Override
        public <R> */ struct Stream map(){
}
/* @Override
        public */ struct Optional next(){
}
/* @Override
        public */ struct List createInitial(){
}
/* @Override
        public */ struct List fold(){
}
/* public static */ void main(){
}
/* private static */ char* compile(){
}
/* private static Tuple<CompilerState, */ /* String> */ compileStatements(){
}
/* private static Tuple<CompilerState, */ /* String> */ foldSegment(){
}
/* private static */ struct Stream divideStatements(){
}
/* private static */ struct Optional divideSingleQuotes(){
}
/* private static */ struct DivideState divideStatementChar(){
}
/* private static Tuple<CompilerState, */ /* String> */ compileRootSegment(){
}
/* private static Tuple<CompilerState, */ /* String> */ generatePlaceholderToTuple(){
}
/* private static */ char* generatePlaceholder(){
}
/* private static Optional<Tuple<CompilerState, */ /* String>> */ compileClass(){
}
/* private static Optional<Tuple<CompilerState, */ /* String>> */ compileToStruct(){
}
/* private static Tuple<CompilerState, */ /* String> */ compileClassSegment(){
}
/* private static Optional<Tuple<CompilerState, */ /* String>> */ compileWhitespace(){
}
/* private static Optional<Tuple<CompilerState, */ /* String>> */ compileInitialization(){
}
/* private static */ char* compileValue(){
}
/* private static */ int isNumber(){
}
/* private static Optional<Tuple<CompilerState, */ /* String>> */ compileMethod(){
}
/* private static Optional<Tuple<CompilerState, */ /* String>> */ compileStatement(){
}
/* private static Optional<Tuple<CompilerState, */ /* String>> */ compileDefinition(){
}
/* private static */ struct Optional compileType(){
}
/* private static */ int isSymbol(){
}
/* private static */ struct Optional compileType(){
}
int main(){
	return 0;
}
