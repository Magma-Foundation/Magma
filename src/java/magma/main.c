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
struct Some {
};
struct None {
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
struct Option next(){
}
/* <R> */ struct Option map(){
}
struct Option or(){
}
struct T orElse(){
}
int isPresent(){
}
struct T orElseGet(){
}
/* <R> */ struct Option flatMap(){
}
struct C createInitial(){
}
struct C fold(){
}
struct Option next(){
}
/* @Override
        public <R> */ struct Option map(){
}
/* @Override
        public */ struct Option or(){
}
/* @Override
        public */ struct T orElse(){
}
/* @Override
        public */ int isPresent(){
}
/* @Override
        public */ struct T orElseGet(){
}
/* @Override
        public <R> */ struct Option flatMap(){
}
/* @Override
        public <R> */ struct Option map(){
}
/* @Override
        public */ struct Option or(){
}
/* @Override
        public */ struct T orElse(){
}
/* @Override
        public */ int isPresent(){
}
/* @Override
        public */ struct T orElseGet(){
}
/* @Override
        public <R> */ struct Option flatMap(){
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
        public */ struct Option createInitial(){
}
/* @Override
        public */ struct Option fold(){
}
/* @Override
        public */ struct Option next(){
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
        public */ struct Option next(){
}
/* @Override
        public */ struct List createInitial(){
}
/* @Override
        public */ struct List fold(){
}
/* public static */ void __main__(){
}
/* private static */ char* compile(){
}
/* private static */ struct Tuple compileStatements(){
}
/* private static */ struct Tuple foldSegment(){
}
/* private static */ struct Stream divideStatements(){
}
/* private static */ struct Option divideSingleQuotes(){
}
/* private static */ struct DivideState divideStatementChar(){
}
/* private static */ struct Tuple compileRootSegment(){
}
/* private static */ struct Tuple generatePlaceholderToTuple(){
}
/* private static */ char* generatePlaceholder(){
}
/* private static */ struct Option compileClass(){
}
/* private static */ struct Option compileToStruct(){
}
/* private static */ struct Tuple compileClassSegment(){
}
/* private static */ struct Option compileWhitespace(){
}
/* private static */ struct Option compileInitialization(){
}
/* private static */ char* compileValue(){
}
/* private static */ int isNumber(){
}
/* private static */ struct Option compileMethod(){
}
/* private static */ struct Option compileStatement(){
}
/* private static */ struct Option compileDefinition(){
}
/* private static */ struct Option compileTypeProperty(){
}
/* private static */ int findTypeSeparator(){
}
/* private static */ int isSymbol(){
}
/* private static */ struct Option compileType(){
}
int main(){
	return 0;
}
