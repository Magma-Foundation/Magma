#include "./java/util/function/BiFunction.h"
#include "./java/util/function/Consumer.h"
#include "./java/util/function/Function.h"
#include "./java/util/function/Predicate.h"
#include "./java/util/function/Supplier.h"
/* private */ typedef struct Rule {
	Result_String_CompileError compile(String input);
} Rule;
/* public */ typedef struct Error {
	String display();
} Error;
/* public */ typedef struct Path_ {
	Option_IOError writeString(String output);
	Result_String_IOError readString();
	Path_ resolveSibling(String sibling);
} Path_;
/* private static */ typedef struct State {
	/* private final */List__Character queue;
	/* private final */List__String segments;
	/* private final */StringBuilder buffer;
	/* private final */int depth;
} State;
/* private static */ typedef struct Streams {
} Streams;
/* private */ typedef struct Joiner {
} Joiner;
/* private static */ typedef struct Tuples {
} Tuples;
/* private */ typedef struct CompileError {
} CompileError;
/* private */ typedef struct OrState {
} OrState;
/* private static final */ typedef struct Node {
	/* private final */Map__String_String strings;
} Node;
/* private */ typedef struct ParseState {
} ParseState;
/* private */ typedef struct ApplicationError {
} ApplicationError;
/* public */ typedef struct Main {
} Main;
/* public */ typedef struct List__T {
	List__T add(T element);
	List__T addAll(List__T elements);
	Stream__T stream();
	T popFirst();
	int hasElements();
	Option_T apply(int index);
	int size();
	T last();
	Stream__Tuple_Integer_T streamWithIndices();
	T first();
	List__T sort(Integer (*)(T, T) comparator);
} List__T;
/* public */ typedef struct Stream__R {
	/* <R> */Stream__R map(R (*)(T) mapper);
	/* <R> */R foldWithInitial(R initial, R (*)(R, T) folder);
	/* <C> */C collect(Collector_T_C collector);
	int anyMatch(Predicate_T predicate);
	/* <R> */Stream__R flatMap(Stream__R (*)(T) mapper);
	Stream__T concat(Stream__T other);
	Option_T next();
	int allMatch(Predicate_T predicate);
	Stream__T filter(Predicate_T predicate);
	/* <R, X> */Result_R_X foldToResult(R initial, Result_R_X (*)(R, T) folder);
} Stream__R;
/* sealed public */ typedef struct Option_T {
	void ifPresent(void (*)(T) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(T) mapper);
	/* <R> */Option_R map(R (*)(T) mapper);
	T orElse(T other);
	int isPresent();
	Tuple_int_T toTuple(T other);
	T orElseGet(T (*)() other);
	Option_T or(Option_T (*)() other);
	int isEmpty();
	/* <R> */R match(R (*)(T) whenPresent, R (*)() whenEmpty);
} Option_T;
/* sealed public */ typedef struct Result_String_CompileError {
	/* <R> */R match(R (*)(T) whenOk, R (*)(X) whenErr);
	/* <R> */Result_R_X mapValue(R (*)(T) mapper);
	/* <R> */Result_R_X flatMapValue(Result_R_X (*)(T) mapper);
	Option_X findError();
	/* <R> */Result_T_R mapErr(R (*)(X) mapper);
	/* <R> */Result_Tuple_T_R_X and(Result_R_X (*)() mapper);
} Result_String_CompileError;
/* public */ typedef struct Map__K_V {
	Map__K_V with(K key, V value);
	Option_V find(K key);
} Map__K_V;
/* sealed public */ typedef struct Option_IOError {
	void ifPresent(void (*)(T) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(T) mapper);
	/* <R> */Option_R map(R (*)(T) mapper);
	T orElse(T other);
	int isPresent();
	Tuple_int_T toTuple(T other);
	T orElseGet(T (*)() other);
	Option_T or(Option_T (*)() other);
	int isEmpty();
	/* <R> */R match(R (*)(T) whenPresent, R (*)() whenEmpty);
} Option_IOError;
/* sealed public */ typedef struct Result_String_IOError {
	/* <R> */R match(R (*)(T) whenOk, R (*)(X) whenErr);
	/* <R> */Result_R_X mapValue(R (*)(T) mapper);
	/* <R> */Result_R_X flatMapValue(Result_R_X (*)(T) mapper);
	Option_X findError();
	/* <R> */Result_T_R mapErr(R (*)(X) mapper);
	/* <R> */Result_Tuple_T_R_X and(Result_R_X (*)() mapper);
} Result_String_IOError;
/* private */ typedef struct Head_T {
	Option_T next();
} Head_T;
/* public */ typedef struct List__Character {
	List__T add(T element);
	List__T addAll(List__T elements);
	Stream__T stream();
	T popFirst();
	int hasElements();
	Option_T apply(int index);
	int size();
	T last();
	Stream__Tuple_Integer_T streamWithIndices();
	T first();
	List__T sort(Integer (*)(T, T) comparator);
} List__Character;
/* public */ typedef struct List__String {
	List__T add(T element);
	List__T addAll(List__T elements);
	Stream__T stream();
	T popFirst();
	int hasElements();
	Option_T apply(int index);
	int size();
	T last();
	Stream__Tuple_Integer_T streamWithIndices();
	T first();
	List__T sort(Integer (*)(T, T) comparator);
} List__String;
/* public */ typedef struct Stream__Character {
	/* <R> */Stream__R map(R (*)(T) mapper);
	/* <R> */R foldWithInitial(R initial, R (*)(R, T) folder);
	/* <C> */C collect(Collector_T_C collector);
	int anyMatch(Predicate_T predicate);
	/* <R> */Stream__R flatMap(Stream__R (*)(T) mapper);
	Stream__T concat(Stream__T other);
	Option_T next();
	int allMatch(Predicate_T predicate);
	Stream__T filter(Predicate_T predicate);
	/* <R, X> */Result_R_X foldToResult(R initial, Result_R_X (*)(R, T) folder);
} Stream__Character;
/* public */ typedef struct Stream__T {
	/* <R> */Stream__R map(R (*)(T) mapper);
	/* <R> */R foldWithInitial(R initial, R (*)(R, T) folder);
	/* <C> */C collect(Collector_T_C collector);
	int anyMatch(Predicate_T predicate);
	/* <R> */Stream__R flatMap(Stream__R (*)(T) mapper);
	Stream__T concat(Stream__T other);
	Option_T next();
	int allMatch(Predicate_T predicate);
	Stream__T filter(Predicate_T predicate);
	/* <R, X> */Result_R_X foldToResult(R initial, Result_R_X (*)(R, T) folder);
} Stream__T;
/* sealed public */ typedef struct Option_String {
	void ifPresent(void (*)(T) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(T) mapper);
	/* <R> */Option_R map(R (*)(T) mapper);
	T orElse(T other);
	int isPresent();
	Tuple_int_T toTuple(T other);
	T orElseGet(T (*)() other);
	Option_T or(Option_T (*)() other);
	int isEmpty();
	/* <R> */R match(R (*)(T) whenPresent, R (*)() whenEmpty);
} Option_String;
/* public */ typedef struct Tuple_A_B {
} Tuple_A_B;
/* public */ typedef struct List__CompileError {
	List__T add(T element);
	List__T addAll(List__T elements);
	Stream__T stream();
	T popFirst();
	int hasElements();
	Option_T apply(int index);
	int size();
	T last();
	Stream__Tuple_Integer_T streamWithIndices();
	T first();
	List__T sort(Integer (*)(T, T) comparator);
} List__CompileError;
/* sealed public */ typedef struct Result_String_List__CompileError {
	/* <R> */R match(R (*)(T) whenOk, R (*)(X) whenErr);
	/* <R> */Result_R_X mapValue(R (*)(T) mapper);
	/* <R> */Result_R_X flatMapValue(Result_R_X (*)(T) mapper);
	Option_X findError();
	/* <R> */Result_T_R mapErr(R (*)(X) mapper);
	/* <R> */Result_Tuple_T_R_X and(Result_R_X (*)() mapper);
} Result_String_List__CompileError;
/* sealed public */ typedef struct Option_Integer {
	void ifPresent(void (*)(T) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(T) mapper);
	/* <R> */Option_R map(R (*)(T) mapper);
	T orElse(T other);
	int isPresent();
	Tuple_int_T toTuple(T other);
	T orElseGet(T (*)() other);
	Option_T or(Option_T (*)() other);
	int isEmpty();
	/* <R> */R match(R (*)(T) whenPresent, R (*)() whenEmpty);
} Option_Integer;
/* public */ typedef struct Collector_T_C {
	C createInitial();
	C fold(C current, T element);
} Collector_T_C;
/* public */ typedef struct Map__String_String {
	Map__K_V with(K key, V value);
	Option_V find(K key);
} Map__String_String;
/* public */ typedef struct List__List__String {
	List__T add(T element);
	List__T addAll(List__T elements);
	Stream__T stream();
	T popFirst();
	int hasElements();
	Option_T apply(int index);
	int size();
	T last();
	Stream__Tuple_Integer_T streamWithIndices();
	T first();
	List__T sort(Integer (*)(T, T) comparator);
} List__List__String;
/* public */ typedef struct Tuple_String_List__String {
} Tuple_String_List__String;
/* public */ typedef struct List__Tuple_String_List__String {
	List__T add(T element);
	List__T addAll(List__T elements);
	Stream__T stream();
	T popFirst();
	int hasElements();
	Option_T apply(int index);
	int size();
	T last();
	Stream__Tuple_Integer_T streamWithIndices();
	T first();
	List__T sort(Integer (*)(T, T) comparator);
} List__Tuple_String_List__String;
/* public */ typedef struct Map__String_Result_String_CompileError (*)(List__String) {
	Map__K_V with(K key, V value);
	Option_V find(K key);
} Map__String_Result_String_CompileError (*)(List__String);
/* sealed public */ typedef struct Option_ApplicationError {
	void ifPresent(void (*)(T) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(T) mapper);
	/* <R> */Option_R map(R (*)(T) mapper);
	T orElse(T other);
	int isPresent();
	Tuple_int_T toTuple(T other);
	T orElseGet(T (*)() other);
	Option_T or(Option_T (*)() other);
	int isEmpty();
	/* <R> */R match(R (*)(T) whenPresent, R (*)() whenEmpty);
} Option_ApplicationError;
/* sealed public */ typedef struct Result_List__String_CompileError {
	/* <R> */R match(R (*)(T) whenOk, R (*)(X) whenErr);
	/* <R> */Result_R_X mapValue(R (*)(T) mapper);
	/* <R> */Result_R_X flatMapValue(Result_R_X (*)(T) mapper);
	Option_X findError();
	/* <R> */Result_T_R mapErr(R (*)(X) mapper);
	/* <R> */Result_Tuple_T_R_X and(Result_R_X (*)() mapper);
} Result_List__String_CompileError;
/* sealed public */ typedef struct Option_CompileError {
	void ifPresent(void (*)(T) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(T) mapper);
	/* <R> */Option_R map(R (*)(T) mapper);
	T orElse(T other);
	int isPresent();
	Tuple_int_T toTuple(T other);
	T orElseGet(T (*)() other);
	Option_T or(Option_T (*)() other);
	int isEmpty();
	/* <R> */R match(R (*)(T) whenPresent, R (*)() whenEmpty);
} Option_CompileError;
/* sealed public */ typedef struct Option_State {
	void ifPresent(void (*)(T) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(T) mapper);
	/* <R> */Option_R map(R (*)(T) mapper);
	T orElse(T other);
	int isPresent();
	Tuple_int_T toTuple(T other);
	T orElseGet(T (*)() other);
	Option_T or(Option_T (*)() other);
	int isEmpty();
	/* <R> */R match(R (*)(T) whenPresent, R (*)() whenEmpty);
} Option_State;
/* sealed public */ typedef struct Option_Result_String_CompileError {
	void ifPresent(void (*)(T) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(T) mapper);
	/* <R> */Option_R map(R (*)(T) mapper);
	T orElse(T other);
	int isPresent();
	Tuple_int_T toTuple(T other);
	T orElseGet(T (*)() other);
	Option_T or(Option_T (*)() other);
	int isEmpty();
	/* <R> */R match(R (*)(T) whenPresent, R (*)() whenEmpty);
} Option_Result_String_CompileError;
/* public */ typedef struct Err_String_CompileError {
} Err_String_CompileError;
/* sealed public */ typedef struct Option_List__String {
	void ifPresent(void (*)(T) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(T) mapper);
	/* <R> */Option_R map(R (*)(T) mapper);
	T orElse(T other);
	int isPresent();
	Tuple_int_T toTuple(T other);
	T orElseGet(T (*)() other);
	Option_T or(Option_T (*)() other);
	int isEmpty();
	/* <R> */R match(R (*)(T) whenPresent, R (*)() whenEmpty);
} Option_List__String;
/* sealed public */ typedef struct Result_Node_CompileError {
	/* <R> */R match(R (*)(T) whenOk, R (*)(X) whenErr);
	/* <R> */Result_R_X mapValue(R (*)(T) mapper);
	/* <R> */Result_R_X flatMapValue(Result_R_X (*)(T) mapper);
	Option_X findError();
	/* <R> */Result_T_R mapErr(R (*)(X) mapper);
	/* <R> */Result_Tuple_T_R_X and(Result_R_X (*)() mapper);
} Result_Node_CompileError;
/* public */ typedef struct List__Rule {
	List__T add(T element);
	List__T addAll(List__T elements);
	Stream__T stream();
	T popFirst();
	int hasElements();
	Option_T apply(int index);
	int size();
	T last();
	Stream__Tuple_Integer_T streamWithIndices();
	T first();
	List__T sort(Integer (*)(T, T) comparator);
} List__Rule;

	/* private static */List__Tuple_String_List__String expanded = Lists.empty();;

	/* private static */Map__String_Result_String_CompileError (*)(List__String) generators = Maps.empty();;

	/* private static */List__String imports = Lists.empty();;

	/* private static */List__String structs = Lists.empty();;

	/* private static */List__String functions = Lists.empty();;

	/* private static */List__Tuple_String_List__String toExpand = Lists.empty();;

	/* private static */List__String globals = Lists.empty();;

	/* private static */int lambdaCounter = 0;;
private State(List__Character queue){
	this(queue, Lists.empty(), StringBuilder(), 0);
}
private State(List__Character queue, List__String segments, StringBuilder buffer, int depth){
	this.queue = queue;
	this.segments = segments;
	this.buffer = buffer;
	this.depth = depth;
}
/* private */State popAndAppend(){
	return append(pop());
}
/* private */int hasNext(){
	return queue.hasElements();
}
/* private */State enter(){
	return State(queue, segments, buffer, depth + 1);
}
/* private */State exit(){
	return State(queue, segments, buffer, depth - 1);
}
/* private */State append(char c){
	return State(queue, segments, buffer.append(c), depth);
}
/* private */State advance(){
	return State(queue, segments.add(buffer.toString()), StringBuilder(), depth);
}
/* private */int isLevel(){
	return depth == 0;
}
/* private */char pop(){
	return queue.popFirst();
}
/* private */int isShallow(){
	return depth == 1;
}
/* public */List__String segments(){
	return segments;
}
/* public */char peek(){
	return queue.first();
}
auto __lambda0__(auto param){
	return RangeHead(value.length())).map(value.charAt(param);
}
auto __lambda1__(auto value){
	return value.charAt(value);
}
auto __lambda2__(auto param){
	return HeadedStream(RangeHead(value.length())).map(value.charAt)(param);
}
auto __lambda3__(auto value){
	return value.charAt(value);
}
/* public static */Stream__Character from(String value){
	return HeadedStream(RangeHead(value.length())).map(value::charAt);
}
/* public static <T> */Stream__T empty(){
	return HeadedStream(EmptyHead());
}
auto __lambda4__(auto param){
	return RangeHead(value.length())).map(value.charAt(param);
}
auto __lambda5__(auto value){
	return value.charAt(value);
}
auto __lambda6__(auto param){
	return HeadedStream(RangeHead(value.length())).map(value.charAt)(param);
}
auto __lambda7__(auto value){
	return value.charAt(value);
}
public Joiner(){
	this("");
}
/* @Override
        public */Option_String createInitial(){
	return None();
}
auto __lambda8__(auto inner){
	return inner + delimiter + element;
}
auto __lambda9__(auto inner){
	return inner;
}
auto __lambda10__(auto inner){
	return inner + delimiter + element;
}
auto __lambda11__(auto inner){
	return inner;
}
auto __lambda12__(auto inner){
	return inner + delimiter + element;
}
auto __lambda13__(auto inner){
	return inner;
}
auto __lambda14__(auto inner){
	return inner + delimiter + element;
}
auto __lambda15__(auto inner){
	return inner;
}
auto __lambda16__(auto inner){
	return inner + delimiter + element;
}
auto __lambda17__(auto inner){
	return inner;
}
auto __lambda18__(auto inner){
	return inner + delimiter + element;
}
auto __lambda19__(auto inner){
	return inner;
}
auto __lambda20__(auto inner){
	return inner + delimiter + element;
}
auto __lambda21__(auto inner){
	return inner;
}
/* @Override
        public */Option_String fold(Option_String current, String element){
	return Some(current.map(__lambda8__).orElse(element));
}
/* public static <A, B> */int equalsTo(Tuple_A_B left, Tuple_A_B right, int (*)(A, A) leftEquator, int (*)(B, B) rightEquator){
	return leftEquator.apply(left.left, right.left) &&
                    rightEquator.apply(left.right, right.right);
}
public CompileError(String message, String context){
	this(message, context, Lists.empty());
}
auto __lambda22__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda23__(auto param){
	return 1 + children.stream().map(CompileError.depth)(param);
}
auto __lambda24__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda25__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda26__(auto param){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(param);
}
auto __lambda27__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda28__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda29__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(param);
}
auto __lambda30__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda31__(auto param){
	return 1 + children.stream().map(CompileError.depth)(param);
}
auto __lambda32__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda33__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda34__(auto param){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(new Max())(param);
}
auto __lambda35__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda36__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda37__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(param);
}
auto __lambda38__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda39__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda40__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())(param);
}
auto __lambda41__(auto param){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(new Max())
                    .orElse(param);
}
auto __lambda42__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda43__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda44__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(param);
}
auto __lambda45__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda46__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda47__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())(param);
}
auto __lambda48__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())
                    .orElse(param);
}
auto __lambda49__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda50__(auto param){
	return 1 + children.stream().map(CompileError.depth)(param);
}
auto __lambda51__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda52__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda53__(auto param){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(param);
}
auto __lambda54__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda55__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda56__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(param);
}
auto __lambda57__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda58__(auto param){
	return 1 + children.stream().map(CompileError.depth)(param);
}
auto __lambda59__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda60__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda61__(auto param){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(new Max())(param);
}
auto __lambda62__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda63__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda64__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(param);
}
auto __lambda65__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda66__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda67__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())(param);
}
auto __lambda68__(auto param){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(new Max())
                    .orElse(0)(param);
}
auto __lambda69__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda70__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda71__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(param);
}
auto __lambda72__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda73__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda74__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())(param);
}
auto __lambda75__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())
                    .orElse(param);
}
auto __lambda76__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda77__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda78__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(param);
}
auto __lambda79__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda80__(auto param){
	return children.stream().map(CompileError.depth)(param);
}
auto __lambda81__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())(param);
}
auto __lambda82__(auto param){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())
                    .orElse(0)(param);
}
auto __lambda83__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda84__(auto compileerror){
	return CompileError.depth(compileerror);
}
/* private */int depth(){
	return 1 + children.stream().map(__lambda22__).collect(Max()).orElse(0);
}
/* @Override
        public */String display(){
	return format(0);
}
auto __lambda85__(auto first, auto second){
	return first.depth() - second.depth();
}
auto __lambda86__(auto first, auto second){
	return first.depth() - second.depth;
}
auto __lambda87__(auto first, auto second){
	return first.depth() - second;
}
auto __lambda88__(auto first, auto second){
	return first;
}
auto __lambda89__(auto first, auto second){
	return first.depth() - second;
}
auto __lambda90__(auto first, auto second){
	return first;
}
auto __lambda91__(auto first, auto second){
	return first.depth() - second.depth();
}
auto __lambda92__(auto first, auto second){
	return first.depth() - second.depth;
}
auto __lambda93__(auto first, auto second){
	return first.depth() - second;
}
auto __lambda94__(auto first, auto second){
	return first;
}
auto __lambda95__(auto first, auto second){
	return first.depth() - second;
}
auto __lambda96__(auto first, auto second){
	return first;
}
auto __lambda97__(auto result){
	return "\n" + result;
}
auto __lambda98__(auto result){
	return "\n";
}
auto __lambda99__(auto result){
	return "\n" + result;
}
auto __lambda100__(auto result){
	return "\n";
}
auto __lambda101__(auto result){
	return "\n" + result;
}
auto __lambda102__(auto result){
	return "\n";
}
auto __lambda103__(auto result){
	return "\n" + result;
}
auto __lambda104__(auto result){
	return "\n";
}
auto __lambda105__(auto result){
	return "\n" + result;
}
auto __lambda106__(auto result){
	return "\n";
}
auto __lambda107__(auto result){
	return "\n" + result;
}
auto __lambda108__(auto result){
	return "\n";
}
auto __lambda109__(auto result){
	return "\n" + result;
}
auto __lambda110__(auto result){
	return "\n";
}
auto __lambda111__(auto result){
	return "\n" + result;
}
auto __lambda112__(auto result){
	return "\n";
}
auto __lambda113__(auto result){
	return "\n" + result;
}
auto __lambda114__(auto result){
	return "\n";
}
auto __lambda115__(auto result){
	return "\n" + result;
}
auto __lambda116__(auto result){
	return "\n";
}
auto __lambda117__(auto result){
	return "\n" + result;
}
auto __lambda118__(auto result){
	return "\n";
}
auto __lambda119__(auto result){
	return "\n" + result;
}
auto __lambda120__(auto result){
	return "\n";
}
auto __lambda121__(auto result){
	return "\n" + result;
}
auto __lambda122__(auto result){
	return "\n";
}
auto __lambda123__(auto result){
	return "\n" + result;
}
auto __lambda124__(auto result){
	return "\n";
}
auto __lambda125__(auto result){
	return "\n" + result;
}
auto __lambda126__(auto result){
	return "\n";
}
auto __lambda127__(auto result){
	return "\n" + result;
}
auto __lambda128__(auto result){
	return "\n";
}
auto __lambda129__(auto result){
	return "\n" + result;
}
auto __lambda130__(auto result){
	return "\n";
}
auto __lambda131__(auto result){
	return "\n" + result;
}
auto __lambda132__(auto result){
	return "\n";
}
auto __lambda133__(auto result){
	return "\n" + result;
}
auto __lambda134__(auto result){
	return "\n";
}
auto __lambda135__(auto result){
	return "\n" + result;
}
auto __lambda136__(auto result){
	return "\n";
}
auto __lambda137__(auto result){
	return "\n" + result;
}
auto __lambda138__(auto result){
	return "\n";
}
auto __lambda139__(auto result){
	return "\n" + result;
}
auto __lambda140__(auto result){
	return "\n";
}
auto __lambda141__(auto result){
	return "\n" + result;
}
auto __lambda142__(auto result){
	return "\n";
}
auto __lambda143__(auto result){
	return "\n" + result;
}
auto __lambda144__(auto result){
	return "\n";
}
auto __lambda145__(auto result){
	return "\n" + result;
}
auto __lambda146__(auto result){
	return "\n";
}
auto __lambda147__(auto result){
	return "\n" + result;
}
auto __lambda148__(auto result){
	return "\n";
}
auto __lambda149__(auto result){
	return "\n" + result;
}
auto __lambda150__(auto result){
	return "\n";
}
auto __lambda151__(auto result){
	return "\n" + result;
}
auto __lambda152__(auto result){
	return "\n";
}
auto __lambda153__(auto result){
	return "\n" + result;
}
auto __lambda154__(auto result){
	return "\n";
}
auto __lambda155__(auto result){
	return "\n" + result;
}
auto __lambda156__(auto result){
	return "\n";
}
auto __lambda157__(auto result){
	return "\n" + result;
}
auto __lambda158__(auto result){
	return "\n";
}
auto __lambda159__(auto result){
	return "\n" + result;
}
auto __lambda160__(auto result){
	return "\n";
}
auto __lambda161__(auto result){
	return "\n" + result;
}
auto __lambda162__(auto result){
	return "\n";
}
auto __lambda163__(auto result){
	return "\n" + result;
}
auto __lambda164__(auto result){
	return "\n";
}
auto __lambda165__(auto result){
	return "\n" + result;
}
auto __lambda166__(auto result){
	return "\n";
}
auto __lambda167__(auto result){
	return "\n" + result;
}
auto __lambda168__(auto result){
	return "\n";
}
auto __lambda169__(auto result){
	return "\n" + result;
}
auto __lambda170__(auto result){
	return "\n";
}
auto __lambda171__(auto result){
	return "\n" + result;
}
auto __lambda172__(auto result){
	return "\n";
}
auto __lambda173__(auto result){
	return "\n" + result;
}
auto __lambda174__(auto result){
	return "\n";
}
auto __lambda175__(auto result){
	return "\n" + result;
}
auto __lambda176__(auto result){
	return "\n";
}
auto __lambda177__(auto result){
	return "\n" + result;
}
auto __lambda178__(auto result){
	return "\n";
}
auto __lambda179__(auto result){
	return "\n" + result;
}
auto __lambda180__(auto result){
	return "\n";
}
auto __lambda181__(auto result){
	return "\n" + result;
}
auto __lambda182__(auto result){
	return "\n";
}
auto __lambda183__(auto result){
	return "\n" + result;
}
auto __lambda184__(auto result){
	return "\n";
}
auto __lambda185__(auto result){
	return "\n" + result;
}
auto __lambda186__(auto result){
	return "\n";
}
auto __lambda187__(auto result){
	return "\n" + result;
}
auto __lambda188__(auto result){
	return "\n";
}
auto __lambda189__(auto result){
	return "\n" + result;
}
auto __lambda190__(auto result){
	return "\n";
}
auto __lambda191__(auto result){
	return "\n" + result;
}
auto __lambda192__(auto result){
	return "\n";
}
auto __lambda193__(auto result){
	return "\n" + result;
}
auto __lambda194__(auto result){
	return "\n";
}
auto __lambda195__(auto result){
	return "\n" + result;
}
auto __lambda196__(auto result){
	return "\n";
}
auto __lambda197__(auto result){
	return "\n" + result;
}
auto __lambda198__(auto result){
	return "\n";
}
auto __lambda199__(auto result){
	return "\n" + result;
}
auto __lambda200__(auto result){
	return "\n";
}
auto __lambda201__(auto result){
	return "\n" + result;
}
auto __lambda202__(auto result){
	return "\n";
}
auto __lambda203__(auto result){
	return "\n" + result;
}
auto __lambda204__(auto result){
	return "\n";
}
auto __lambda205__(auto result){
	return "\n" + result;
}
auto __lambda206__(auto result){
	return "\n";
}
auto __lambda207__(auto result){
	return "\n" + result;
}
auto __lambda208__(auto result){
	return "\n";
}
auto __lambda209__(auto result){
	return "\n" + result;
}
auto __lambda210__(auto result){
	return "\n";
}
auto __lambda211__(auto result){
	return "\n" + result;
}
auto __lambda212__(auto result){
	return "\n";
}
auto __lambda213__(auto result){
	return "\n" + result;
}
auto __lambda214__(auto result){
	return "\n";
}
auto __lambda215__(auto result){
	return "\n" + result;
}
auto __lambda216__(auto result){
	return "\n";
}
auto __lambda217__(auto result){
	return "\n" + result;
}
auto __lambda218__(auto result){
	return "\n";
}
auto __lambda219__(auto result){
	return "\n" + result;
}
auto __lambda220__(auto result){
	return "\n";
}
auto __lambda221__(auto result){
	return "\n" + result;
}
auto __lambda222__(auto result){
	return "\n";
}
auto __lambda223__(auto result){
	return "\n" + result;
}
auto __lambda224__(auto result){
	return "\n";
}
auto __lambda225__(auto result){
	return "\n" + result;
}
auto __lambda226__(auto result){
	return "\n";
}
auto __lambda227__(auto result){
	return "\n" + result;
}
auto __lambda228__(auto result){
	return "\n";
}
auto __lambda229__(auto result){
	return "\n" + result;
}
auto __lambda230__(auto result){
	return "\n";
}
auto __lambda231__(auto result){
	return "\n" + result;
}
auto __lambda232__(auto result){
	return "\n";
}
auto __lambda233__(auto result){
	return "\n" + result;
}
auto __lambda234__(auto result){
	return "\n";
}
auto __lambda235__(auto result){
	return "\n" + result;
}
auto __lambda236__(auto result){
	return "\n";
}
auto __lambda237__(auto result){
	return "\n" + result;
}
auto __lambda238__(auto result){
	return "\n";
}
auto __lambda239__(auto result){
	return "\n" + result;
}
auto __lambda240__(auto result){
	return "\n";
}
auto __lambda241__(auto result){
	return "\n" + result;
}
auto __lambda242__(auto result){
	return "\n";
}
auto __lambda243__(auto result){
	return "\n" + result;
}
auto __lambda244__(auto result){
	return "\n";
}
auto __lambda245__(auto result){
	return "\n" + result;
}
auto __lambda246__(auto result){
	return "\n";
}
auto __lambda247__(auto result){
	return "\n" + result;
}
auto __lambda248__(auto result){
	return "\n";
}
auto __lambda249__(auto result){
	return "\n" + result;
}
auto __lambda250__(auto result){
	return "\n";
}
auto __lambda251__(auto result){
	return "\n" + result;
}
auto __lambda252__(auto result){
	return "\n";
}
auto __lambda253__(auto result){
	return "\n" + result;
}
auto __lambda254__(auto result){
	return "\n";
}
auto __lambda255__(auto result){
	return "\n" + result;
}
auto __lambda256__(auto result){
	return "\n";
}
auto __lambda257__(auto result){
	return "\n" + result;
}
auto __lambda258__(auto result){
	return "\n";
}
auto __lambda259__(auto result){
	return "\n" + result;
}
auto __lambda260__(auto result){
	return "\n";
}
auto __lambda261__(auto result){
	return "\n" + result;
}
auto __lambda262__(auto result){
	return "\n";
}
auto __lambda263__(auto result){
	return "\n" + result;
}
auto __lambda264__(auto result){
	return "\n";
}
auto __lambda265__(auto result){
	return "\n" + result;
}
auto __lambda266__(auto result){
	return "\n";
}
auto __lambda267__(auto result){
	return "\n" + result;
}
auto __lambda268__(auto result){
	return "\n";
}
auto __lambda269__(auto result){
	return "\n" + result;
}
auto __lambda270__(auto result){
	return "\n";
}
auto __lambda271__(auto result){
	return "\n" + result;
}
auto __lambda272__(auto result){
	return "\n";
}
auto __lambda273__(auto result){
	return "\n" + result;
}
auto __lambda274__(auto result){
	return "\n";
}
auto __lambda275__(auto result){
	return "\n" + result;
}
auto __lambda276__(auto result){
	return "\n";
}
auto __lambda277__(auto result){
	return "\n" + result;
}
auto __lambda278__(auto result){
	return "\n";
}
auto __lambda279__(auto result){
	return "\n" + result;
}
auto __lambda280__(auto result){
	return "\n";
}
auto __lambda281__(auto result){
	return "\n" + result;
}
auto __lambda282__(auto result){
	return "\n";
}
auto __lambda283__(auto result){
	return "\n" + result;
}
auto __lambda284__(auto result){
	return "\n";
}
auto __lambda285__(auto result){
	return "\n" + result;
}
auto __lambda286__(auto result){
	return "\n";
}
auto __lambda287__(auto result){
	return "\n" + result;
}
auto __lambda288__(auto result){
	return "\n";
}
auto __lambda289__(auto result){
	return "\n" + result;
}
auto __lambda290__(auto result){
	return "\n";
}
auto __lambda291__(auto result){
	return "\n" + result;
}
auto __lambda292__(auto result){
	return "\n";
}
auto __lambda293__(auto result){
	return "\n" + result;
}
auto __lambda294__(auto result){
	return "\n";
}
auto __lambda295__(auto result){
	return "\n" + result;
}
auto __lambda296__(auto result){
	return "\n";
}
auto __lambda297__(auto result){
	return "\n" + result;
}
auto __lambda298__(auto result){
	return "\n";
}
auto __lambda299__(auto result){
	return "\n" + result;
}
auto __lambda300__(auto result){
	return "\n";
}
auto __lambda301__(auto result){
	return "\n" + result;
}
auto __lambda302__(auto result){
	return "\n";
}
auto __lambda303__(auto result){
	return "\n" + result;
}
auto __lambda304__(auto result){
	return "\n";
}
auto __lambda305__(auto result){
	return "\n" + result;
}
auto __lambda306__(auto result){
	return "\n";
}
auto __lambda307__(auto result){
	return "\n" + result;
}
auto __lambda308__(auto result){
	return "\n";
}
auto __lambda309__(auto result){
	return "\n" + result;
}
auto __lambda310__(auto result){
	return "\n";
}
auto __lambda311__(auto result){
	return "\n" + result;
}
auto __lambda312__(auto result){
	return "\n";
}
auto __lambda313__(auto result){
	return "\n" + result;
}
auto __lambda314__(auto result){
	return "\n";
}
auto __lambda315__(auto result){
	return "\n" + result;
}
auto __lambda316__(auto result){
	return "\n";
}
auto __lambda317__(auto result){
	return "\n" + result;
}
auto __lambda318__(auto result){
	return "\n";
}
auto __lambda319__(auto result){
	return "\n" + result;
}
auto __lambda320__(auto result){
	return "\n";
}
auto __lambda321__(auto result){
	return "\n" + result;
}
auto __lambda322__(auto result){
	return "\n";
}
auto __lambda323__(auto result){
	return "\n" + result;
}
auto __lambda324__(auto result){
	return "\n";
}
auto __lambda325__(auto result){
	return "\n" + result;
}
auto __lambda326__(auto result){
	return "\n";
}
auto __lambda327__(auto result){
	return "\n" + result;
}
auto __lambda328__(auto result){
	return "\n";
}
auto __lambda329__(auto result){
	return "\n" + result;
}
auto __lambda330__(auto result){
	return "\n";
}
auto __lambda331__(auto result){
	return "\n" + result;
}
auto __lambda332__(auto result){
	return "\n";
}
auto __lambda333__(auto result){
	return "\n" + result;
}
auto __lambda334__(auto result){
	return "\n";
}
auto __lambda335__(auto result){
	return "\n" + result;
}
auto __lambda336__(auto result){
	return "\n";
}
auto __lambda337__(auto result){
	return "\n" + result;
}
auto __lambda338__(auto result){
	return "\n";
}
auto __lambda339__(auto result){
	return "\n" + result;
}
auto __lambda340__(auto result){
	return "\n";
}
auto __lambda341__(auto result){
	return "\n" + result;
}
auto __lambda342__(auto result){
	return "\n";
}
auto __lambda343__(auto result){
	return "\n" + result;
}
auto __lambda344__(auto result){
	return "\n";
}
auto __lambda345__(auto result){
	return "\n" + result;
}
auto __lambda346__(auto result){
	return "\n";
}
auto __lambda347__(auto result){
	return "\n" + result;
}
auto __lambda348__(auto result){
	return "\n";
}
auto __lambda349__(auto result){
	return "\n" + result;
}
auto __lambda350__(auto result){
	return "\n";
}
auto __lambda351__(auto result){
	return "\n" + result;
}
auto __lambda352__(auto result){
	return "\n";
}
auto __lambda353__(auto result){
	return "\n" + result;
}
auto __lambda354__(auto result){
	return "\n";
}
auto __lambda355__(auto result){
	return "\n" + result;
}
auto __lambda356__(auto result){
	return "\n";
}
auto __lambda357__(auto result){
	return "\n" + result;
}
auto __lambda358__(auto result){
	return "\n";
}
auto __lambda359__(auto result){
	return "\n" + result;
}
auto __lambda360__(auto result){
	return "\n";
}
auto __lambda361__(auto result){
	return "\n" + result;
}
auto __lambda362__(auto result){
	return "\n";
}
auto __lambda363__(auto result){
	return "\n" + result;
}
auto __lambda364__(auto result){
	return "\n";
}
auto __lambda365__(auto result){
	return "\n" + result;
}
auto __lambda366__(auto result){
	return "\n";
}
auto __lambda367__(auto result){
	return "\n" + result;
}
auto __lambda368__(auto result){
	return "\n";
}
auto __lambda369__(auto result){
	return "\n" + result;
}
auto __lambda370__(auto result){
	return "\n";
}
auto __lambda371__(auto result){
	return "\n" + result;
}
auto __lambda372__(auto result){
	return "\n";
}
auto __lambda373__(auto result){
	return "\n" + result;
}
auto __lambda374__(auto result){
	return "\n";
}
auto __lambda375__(auto result){
	return "\n" + result;
}
auto __lambda376__(auto result){
	return "\n";
}
auto __lambda377__(auto result){
	return "\n" + result;
}
auto __lambda378__(auto result){
	return "\n";
}
auto __lambda379__(auto result){
	return "\n" + result;
}
auto __lambda380__(auto result){
	return "\n";
}
auto __lambda381__(auto result){
	return "\n" + result;
}
auto __lambda382__(auto result){
	return "\n";
}
auto __lambda383__(auto result){
	return "\n" + result;
}
auto __lambda384__(auto result){
	return "\n";
}
auto __lambda385__(auto result){
	return "\n" + result;
}
auto __lambda386__(auto result){
	return "\n";
}
auto __lambda387__(auto result){
	return "\n" + result;
}
auto __lambda388__(auto result){
	return "\n";
}
auto __lambda389__(auto result){
	return "\n" + result;
}
auto __lambda390__(auto result){
	return "\n";
}
auto __lambda391__(auto result){
	return "\n" + result;
}
auto __lambda392__(auto result){
	return "\n";
}
auto __lambda393__(auto result){
	return "\n" + result;
}
auto __lambda394__(auto result){
	return "\n";
}
auto __lambda395__(auto result){
	return "\n" + result;
}
auto __lambda396__(auto result){
	return "\n";
}
auto __lambda397__(auto result){
	return "\n" + result;
}
auto __lambda398__(auto result){
	return "\n";
}
auto __lambda399__(auto result){
	return "\n" + result;
}
auto __lambda400__(auto result){
	return "\n";
}
auto __lambda401__(auto result){
	return "\n" + result;
}
auto __lambda402__(auto result){
	return "\n";
}
auto __lambda403__(auto result){
	return "\n" + result;
}
auto __lambda404__(auto result){
	return "\n";
}
auto __lambda405__(auto result){
	return "\n" + result;
}
auto __lambda406__(auto result){
	return "\n";
}
auto __lambda407__(auto result){
	return "\n" + result;
}
auto __lambda408__(auto result){
	return "\n";
}
auto __lambda409__(auto result){
	return "\n" + result;
}
auto __lambda410__(auto result){
	return "\n";
}
auto __lambda411__(auto result){
	return "\n" + result;
}
auto __lambda412__(auto result){
	return "\n";
}
auto __lambda413__(auto result){
	return "\n" + result;
}
auto __lambda414__(auto result){
	return "\n";
}
auto __lambda415__(auto result){
	return "\n" + result;
}
auto __lambda416__(auto result){
	return "\n";
}
auto __lambda417__(auto result){
	return "\n" + result;
}
auto __lambda418__(auto result){
	return "\n";
}
auto __lambda419__(auto result){
	return "\n" + result;
}
auto __lambda420__(auto result){
	return "\n";
}
auto __lambda421__(auto result){
	return "\n" + result;
}
auto __lambda422__(auto result){
	return "\n";
}
auto __lambda423__(auto result){
	return "\n" + result;
}
auto __lambda424__(auto result){
	return "\n";
}
auto __lambda425__(auto result){
	return "\n" + result;
}
auto __lambda426__(auto result){
	return "\n";
}
auto __lambda427__(auto result){
	return "\n" + result;
}
auto __lambda428__(auto result){
	return "\n";
}
auto __lambda429__(auto result){
	return "\n" + result;
}
auto __lambda430__(auto result){
	return "\n";
}
auto __lambda431__(auto result){
	return "\n" + result;
}
auto __lambda432__(auto result){
	return "\n";
}
auto __lambda433__(auto result){
	return "\n" + result;
}
auto __lambda434__(auto result){
	return "\n";
}
auto __lambda435__(auto result){
	return "\n" + result;
}
auto __lambda436__(auto result){
	return "\n";
}
auto __lambda437__(auto result){
	return "\n" + result;
}
auto __lambda438__(auto result){
	return "\n";
}
auto __lambda439__(auto result){
	return "\n" + result;
}
auto __lambda440__(auto result){
	return "\n";
}
auto __lambda441__(auto result){
	return "\n" + result;
}
auto __lambda442__(auto result){
	return "\n";
}
auto __lambda443__(auto result){
	return "\n" + result;
}
auto __lambda444__(auto result){
	return "\n";
}
auto __lambda445__(auto result){
	return "\n" + result;
}
auto __lambda446__(auto result){
	return "\n";
}
auto __lambda447__(auto result){
	return "\n" + result;
}
auto __lambda448__(auto result){
	return "\n";
}
auto __lambda449__(auto result){
	return "\n" + result;
}
auto __lambda450__(auto result){
	return "\n";
}
auto __lambda451__(auto result){
	return "\n" + result;
}
auto __lambda452__(auto result){
	return "\n";
}
auto __lambda453__(auto result){
	return "\n" + result;
}
auto __lambda454__(auto result){
	return "\n";
}
auto __lambda455__(auto result){
	return "\n" + result;
}
auto __lambda456__(auto result){
	return "\n";
}
auto __lambda457__(auto result){
	return "\n" + result;
}
auto __lambda458__(auto result){
	return "\n";
}
auto __lambda459__(auto result){
	return "\n" + result;
}
auto __lambda460__(auto result){
	return "\n";
}
auto __lambda461__(auto result){
	return "\n" + result;
}
auto __lambda462__(auto result){
	return "\n";
}
auto __lambda463__(auto result){
	return "\n" + result;
}
auto __lambda464__(auto result){
	return "\n";
}
auto __lambda465__(auto result){
	return "\n" + result;
}
auto __lambda466__(auto result){
	return "\n";
}
auto __lambda467__(auto result){
	return "\n" + result;
}
auto __lambda468__(auto result){
	return "\n";
}
auto __lambda469__(auto result){
	return "\n" + result;
}
auto __lambda470__(auto result){
	return "\n";
}
auto __lambda471__(auto result){
	return "\n" + result;
}
auto __lambda472__(auto result){
	return "\n";
}
auto __lambda473__(auto result){
	return "\n" + result;
}
auto __lambda474__(auto result){
	return "\n";
}
auto __lambda475__(auto result){
	return "\n" + result;
}
auto __lambda476__(auto result){
	return "\n";
}
auto __lambda477__(auto result){
	return "\n" + result;
}
auto __lambda478__(auto result){
	return "\n";
}
auto __lambda479__(auto result){
	return "\n" + result;
}
auto __lambda480__(auto result){
	return "\n";
}
auto __lambda481__(auto result){
	return "\n" + result;
}
auto __lambda482__(auto result){
	return "\n";
}
auto __lambda483__(auto result){
	return "\n" + result;
}
auto __lambda484__(auto result){
	return "\n";
}
auto __lambda485__(auto result){
	return "\n" + result;
}
auto __lambda486__(auto result){
	return "\n";
}
auto __lambda487__(auto result){
	return "\n" + result;
}
auto __lambda488__(auto result){
	return "\n";
}
auto __lambda489__(auto result){
	return "\n" + result;
}
auto __lambda490__(auto result){
	return "\n";
}
auto __lambda491__(auto result){
	return "\n" + result;
}
auto __lambda492__(auto result){
	return "\n";
}
auto __lambda493__(auto result){
	return "\n" + result;
}
auto __lambda494__(auto result){
	return "\n";
}
auto __lambda495__(auto result){
	return "\n" + result;
}
auto __lambda496__(auto result){
	return "\n";
}
auto __lambda497__(auto result){
	return "\n" + result;
}
auto __lambda498__(auto result){
	return "\n";
}
auto __lambda499__(auto result){
	return "\n" + result;
}
auto __lambda500__(auto result){
	return "\n";
}
auto __lambda501__(auto result){
	return "\n" + result;
}
auto __lambda502__(auto result){
	return "\n";
}
auto __lambda503__(auto result){
	return "\n" + result;
}
auto __lambda504__(auto result){
	return "\n";
}
auto __lambda505__(auto result){
	return "\n" + result;
}
auto __lambda506__(auto result){
	return "\n";
}
auto __lambda507__(auto result){
	return "\n" + result;
}
auto __lambda508__(auto result){
	return "\n";
}
auto __lambda509__(auto result){
	return "\n" + result;
}
auto __lambda510__(auto result){
	return "\n";
}
auto __lambda511__(auto result){
	return "\n" + result;
}
auto __lambda512__(auto result){
	return "\n";
}
auto __lambda513__(auto result){
	return "\n" + result;
}
auto __lambda514__(auto result){
	return "\n";
}
auto __lambda515__(auto result){
	return "\n" + result;
}
auto __lambda516__(auto result){
	return "\n";
}
auto __lambda517__(auto result){
	return "\n" + result;
}
auto __lambda518__(auto result){
	return "\n";
}
auto __lambda519__(auto result){
	return "\n" + result;
}
auto __lambda520__(auto result){
	return "\n";
}
auto __lambda521__(auto result){
	return "\n" + result;
}
auto __lambda522__(auto result){
	return "\n";
}
auto __lambda523__(auto result){
	return "\n" + result;
}
auto __lambda524__(auto result){
	return "\n";
}
auto __lambda525__(auto result){
	return "\n" + result;
}
auto __lambda526__(auto result){
	return "\n";
}
auto __lambda527__(auto result){
	return "\n" + result;
}
auto __lambda528__(auto result){
	return "\n";
}
auto __lambda529__(auto result){
	return "\n" + result;
}
auto __lambda530__(auto result){
	return "\n";
}
auto __lambda531__(auto result){
	return "\n" + result;
}
auto __lambda532__(auto result){
	return "\n";
}
auto __lambda533__(auto result){
	return "\n" + result;
}
auto __lambda534__(auto result){
	return "\n";
}
auto __lambda535__(auto result){
	return "\n" + result;
}
auto __lambda536__(auto result){
	return "\n";
}
auto __lambda537__(auto result){
	return "\n" + result;
}
auto __lambda538__(auto result){
	return "\n";
}
auto __lambda539__(auto result){
	return "\n" + result;
}
auto __lambda540__(auto result){
	return "\n";
}
auto __lambda541__(auto result){
	return "\n" + result;
}
auto __lambda542__(auto result){
	return "\n";
}
auto __lambda543__(auto result){
	return "\n" + result;
}
auto __lambda544__(auto result){
	return "\n";
}
auto __lambda545__(auto result){
	return "\n" + result;
}
auto __lambda546__(auto result){
	return "\n";
}
auto __lambda547__(auto result){
	return "\n" + result;
}
auto __lambda548__(auto result){
	return "\n";
}
auto __lambda549__(auto result){
	return "\n" + result;
}
auto __lambda550__(auto result){
	return "\n";
}
auto __lambda551__(auto result){
	return "\n" + result;
}
auto __lambda552__(auto result){
	return "\n";
}
auto __lambda553__(auto result){
	return "\n" + result;
}
auto __lambda554__(auto result){
	return "\n";
}
auto __lambda555__(auto result){
	return "\n" + result;
}
auto __lambda556__(auto result){
	return "\n";
}
auto __lambda557__(auto result){
	return "\n" + result;
}
auto __lambda558__(auto result){
	return "\n";
}
auto __lambda559__(auto result){
	return "\n" + result;
}
auto __lambda560__(auto result){
	return "\n";
}
auto __lambda561__(auto result){
	return "\n" + result;
}
auto __lambda562__(auto result){
	return "\n";
}
auto __lambda563__(auto result){
	return "\n" + result;
}
auto __lambda564__(auto result){
	return "\n";
}
auto __lambda565__(auto result){
	return "\n" + result;
}
auto __lambda566__(auto result){
	return "\n";
}
auto __lambda567__(auto result){
	return "\n" + result;
}
auto __lambda568__(auto result){
	return "\n";
}
auto __lambda569__(auto result){
	return "\n" + result;
}
auto __lambda570__(auto result){
	return "\n";
}
auto __lambda571__(auto result){
	return "\n" + result;
}
auto __lambda572__(auto result){
	return "\n";
}
auto __lambda573__(auto result){
	return "\n" + result;
}
auto __lambda574__(auto result){
	return "\n";
}
auto __lambda575__(auto result){
	return "\n" + result;
}
auto __lambda576__(auto result){
	return "\n";
}
auto __lambda577__(auto result){
	return "\n" + result;
}
auto __lambda578__(auto result){
	return "\n";
}
auto __lambda579__(auto result){
	return "\n" + result;
}
auto __lambda580__(auto result){
	return "\n";
}
auto __lambda581__(auto result){
	return "\n" + result;
}
auto __lambda582__(auto result){
	return "\n";
}
auto __lambda583__(auto result){
	return "\n" + result;
}
auto __lambda584__(auto result){
	return "\n";
}
auto __lambda585__(auto result){
	return "\n" + result;
}
auto __lambda586__(auto result){
	return "\n";
}
auto __lambda587__(auto result){
	return "\n" + result;
}
auto __lambda588__(auto result){
	return "\n";
}
auto __lambda589__(auto result){
	return "\n" + result;
}
auto __lambda590__(auto result){
	return "\n";
}
auto __lambda591__(auto result){
	return "\n" + result;
}
auto __lambda592__(auto result){
	return "\n";
}
auto __lambda593__(auto result){
	return "\n" + result;
}
auto __lambda594__(auto result){
	return "\n";
}
auto __lambda595__(auto result){
	return "\n" + result;
}
auto __lambda596__(auto result){
	return "\n";
}
auto __lambda597__(auto result){
	return "\n" + result;
}
auto __lambda598__(auto result){
	return "\n";
}
auto __lambda599__(auto result){
	return "\n" + result;
}
auto __lambda600__(auto result){
	return "\n";
}
auto __lambda601__(auto result){
	return "\n" + result;
}
auto __lambda602__(auto result){
	return "\n";
}
auto __lambda603__(auto result){
	return "\n" + result;
}
auto __lambda604__(auto result){
	return "\n";
}
auto __lambda605__(auto result){
	return "\n" + result;
}
auto __lambda606__(auto result){
	return "\n";
}
auto __lambda607__(auto result){
	return "\n" + result;
}
auto __lambda608__(auto result){
	return "\n";
}
auto __lambda609__(auto result){
	return "\n" + result;
}
auto __lambda610__(auto result){
	return "\n";
}
auto __lambda611__(auto result){
	return "\n" + result;
}
auto __lambda612__(auto result){
	return "\n";
}
auto __lambda613__(auto result){
	return "\n" + result;
}
auto __lambda614__(auto result){
	return "\n";
}
auto __lambda615__(auto result){
	return "\n" + result;
}
auto __lambda616__(auto result){
	return "\n";
}
auto __lambda617__(auto result){
	return "\n" + result;
}
auto __lambda618__(auto result){
	return "\n";
}
auto __lambda619__(auto result){
	return "\n" + result;
}
auto __lambda620__(auto result){
	return "\n";
}
auto __lambda621__(auto result){
	return "\n" + result;
}
auto __lambda622__(auto result){
	return "\n";
}
auto __lambda623__(auto result){
	return "\n" + result;
}
auto __lambda624__(auto result){
	return "\n";
}
auto __lambda625__(auto result){
	return "\n" + result;
}
auto __lambda626__(auto result){
	return "\n";
}
auto __lambda627__(auto result){
	return "\n" + result;
}
auto __lambda628__(auto result){
	return "\n";
}
auto __lambda629__(auto result){
	return "\n" + result;
}
auto __lambda630__(auto result){
	return "\n";
}
auto __lambda631__(auto result){
	return "\n" + result;
}
auto __lambda632__(auto result){
	return "\n";
}
auto __lambda633__(auto result){
	return "\n" + result;
}
auto __lambda634__(auto result){
	return "\n";
}
auto __lambda635__(auto result){
	return "\n" + result;
}
auto __lambda636__(auto result){
	return "\n";
}
auto __lambda637__(auto result){
	return "\n" + result;
}
auto __lambda638__(auto result){
	return "\n";
}
auto __lambda639__(auto result){
	return "\n" + result;
}
auto __lambda640__(auto result){
	return "\n";
}
auto __lambda641__(auto result){
	return "\n" + result;
}
auto __lambda642__(auto result){
	return "\n";
}
auto __lambda643__(auto result){
	return "\n" + result;
}
auto __lambda644__(auto result){
	return "\n";
}
auto __lambda645__(auto result){
	return "\n" + result;
}
auto __lambda646__(auto result){
	return "\n";
}
auto __lambda647__(auto result){
	return "\n" + result;
}
auto __lambda648__(auto result){
	return "\n";
}
auto __lambda649__(auto result){
	return "\n" + result;
}
auto __lambda650__(auto result){
	return "\n";
}
auto __lambda651__(auto result){
	return "\n" + result;
}
auto __lambda652__(auto result){
	return "\n";
}
auto __lambda653__(auto result){
	return "\n" + result;
}
auto __lambda654__(auto result){
	return "\n";
}
auto __lambda655__(auto result){
	return "\n" + result;
}
auto __lambda656__(auto result){
	return "\n";
}
auto __lambda657__(auto result){
	return "\n" + result;
}
auto __lambda658__(auto result){
	return "\n";
}
auto __lambda659__(auto result){
	return "\n" + result;
}
auto __lambda660__(auto result){
	return "\n";
}
auto __lambda661__(auto result){
	return "\n" + result;
}
auto __lambda662__(auto result){
	return "\n";
}
auto __lambda663__(auto result){
	return "\n" + result;
}
auto __lambda664__(auto result){
	return "\n";
}
auto __lambda665__(auto result){
	return "\n" + result;
}
auto __lambda666__(auto result){
	return "\n";
}
auto __lambda667__(auto result){
	return "\n" + result;
}
auto __lambda668__(auto result){
	return "\n";
}
auto __lambda669__(auto result){
	return "\n" + result;
}
auto __lambda670__(auto result){
	return "\n";
}
auto __lambda671__(auto result){
	return "\n" + result;
}
auto __lambda672__(auto result){
	return "\n";
}
auto __lambda673__(auto result){
	return "\n" + result;
}
auto __lambda674__(auto result){
	return "\n";
}
auto __lambda675__(auto result){
	return "\n" + result;
}
auto __lambda676__(auto result){
	return "\n";
}
auto __lambda677__(auto result){
	return "\n" + result;
}
auto __lambda678__(auto result){
	return "\n";
}
auto __lambda679__(auto result){
	return "\n" + result;
}
auto __lambda680__(auto result){
	return "\n";
}
auto __lambda681__(auto result){
	return "\n" + result;
}
auto __lambda682__(auto result){
	return "\n";
}
auto __lambda683__(auto result){
	return "\n" + result;
}
auto __lambda684__(auto result){
	return "\n";
}
auto __lambda685__(auto result){
	return "\n" + result;
}
auto __lambda686__(auto result){
	return "\n";
}
auto __lambda687__(auto result){
	return "\n" + result;
}
auto __lambda688__(auto result){
	return "\n";
}
auto __lambda689__(auto result){
	return "\n" + result;
}
auto __lambda690__(auto result){
	return "\n";
}
auto __lambda691__(auto result){
	return "\n" + result;
}
auto __lambda692__(auto result){
	return "\n";
}
auto __lambda693__(auto result){
	return "\n" + result;
}
auto __lambda694__(auto result){
	return "\n";
}
auto __lambda695__(auto result){
	return "\n" + result;
}
auto __lambda696__(auto result){
	return "\n";
}
auto __lambda697__(auto result){
	return "\n" + result;
}
auto __lambda698__(auto result){
	return "\n";
}
auto __lambda699__(auto result){
	return "\n" + result;
}
auto __lambda700__(auto result){
	return "\n";
}
auto __lambda701__(auto result){
	return "\n" + result;
}
auto __lambda702__(auto result){
	return "\n";
}
auto __lambda703__(auto result){
	return "\n" + result;
}
auto __lambda704__(auto result){
	return "\n";
}
auto __lambda705__(auto result){
	return "\n" + result;
}
auto __lambda706__(auto result){
	return "\n";
}
auto __lambda707__(auto result){
	return "\n" + result;
}
auto __lambda708__(auto result){
	return "\n";
}
auto __lambda709__(auto result){
	return "\n" + result;
}
auto __lambda710__(auto result){
	return "\n";
}
auto __lambda711__(auto result){
	return "\n" + result;
}
auto __lambda712__(auto result){
	return "\n";
}
auto __lambda713__(auto result){
	return "\n" + result;
}
auto __lambda714__(auto result){
	return "\n";
}
auto __lambda715__(auto result){
	return "\n" + result;
}
auto __lambda716__(auto result){
	return "\n";
}
auto __lambda717__(auto result){
	return "\n" + result;
}
auto __lambda718__(auto result){
	return "\n";
}
auto __lambda719__(auto result){
	return "\n" + result;
}
auto __lambda720__(auto result){
	return "\n";
}
auto __lambda721__(auto result){
	return "\n" + result;
}
auto __lambda722__(auto result){
	return "\n";
}
auto __lambda723__(auto result){
	return "\n" + result;
}
auto __lambda724__(auto result){
	return "\n";
}
auto __lambda725__(auto result){
	return "\n" + result;
}
auto __lambda726__(auto result){
	return "\n";
}
auto __lambda727__(auto result){
	return "\n" + result;
}
auto __lambda728__(auto result){
	return "\n";
}
auto __lambda729__(auto result){
	return "\n" + result;
}
auto __lambda730__(auto result){
	return "\n";
}
auto __lambda731__(auto result){
	return "\n" + result;
}
auto __lambda732__(auto result){
	return "\n";
}
auto __lambda733__(auto result){
	return "\n" + result;
}
auto __lambda734__(auto result){
	return "\n";
}
/* private */String format(int depth){
	List__CompileError sorted = children.sort(__lambda85__);
	String joined = sorted.streamWithIndices().map(compileError -> "\t".repeat(depth) + compileError.left + ") " + compileError.right.format(depth + 1)).map(__lambda97__).collect(Joiner()).orElse("");
	return message + ": " + context + joined;
}
public OrState(){
	this(None(), Lists.empty());
}
/* public */OrState withValue(String value){
	if (1) {
	}
	return OrState(Some(value), errors);
}
/* public */OrState withError(CompileError error){
	if (1) {
	}
	return OrState(maybeValue, errors.add(error));
}
auto __lambda735__(auto ok){
	return Ok.new(ok);
}
auto __lambda736__(auto ){
	return Err(errors);
}
auto __lambda737__(auto param){
	return maybeValue.<Result<String, List_<CompileError>>>match(Ok.new, () -> new Err<>(errors))(param);
}
auto __lambda738__(auto param){
	return maybeValue.<Result<String, List_<CompileError>>>match(Ok.new,(param);
}
auto __lambda739__(auto param){
	return maybeValue.<Result<String, List_<CompileError>>>match(Ok.new, ()(param);
}
auto __lambda740__(auto ok){
	return Ok.new(ok);
}
auto __lambda741__(auto ){
	return Err(errors);
}
/* public */Result_String_List__CompileError toResult(){
	return maybeValue.<Result<String, List_<CompileError>>>match(__lambda735__, __lambda736__);
}
auto __lambda742__(auto inner){
	return Math.max(inner, element);
}
auto __lambda743__(auto inner){
	return Math.max;
}
auto __lambda744__(auto inner){
	return Math;
}
auto __lambda745__(auto inner){
	return Math;
}
auto __lambda746__(auto inner){
	return Math.max(inner, element);
}
auto __lambda747__(auto inner){
	return Math.max;
}
auto __lambda748__(auto inner){
	return Math;
}
auto __lambda749__(auto inner){
	return Math;
}
private Node(){
	this(Maps.empty());
}
private Node(Map__String_String maps){
	this.strings = maps;
}
/* private */Node withString(String propertyKey, String propertyValue){
	return Node(strings.with(propertyKey, propertyValue));
}
/* public */Option_String find(String propertyKey){
	return strings.find(propertyKey);
}
auto __lambda750__(auto list_){
	return List_.stream(list_);
}
auto __lambda751__(auto param){
	return frames.stream().flatMap(List_.stream)(param);
}
auto __lambda752__(auto param){
	return frames.stream().flatMap(List_.stream)
                    .next(param);
}
auto __lambda753__(auto list_){
	return List_.stream(list_);
}
auto __lambda754__(auto param){
	return frames.stream().flatMap(List_.stream)(param);
}
auto __lambda755__(auto param){
	return frames.stream().flatMap(List_.stream)
                    .next()(param);
}
auto __lambda756__(auto param){
	return frames.stream().flatMap(List_.stream)
                    .next()
                    .isEmpty(param);
}
auto __lambda757__(auto list_){
	return List_.stream(list_);
}
auto __lambda758__(auto param){
	return frames.stream().flatMap(List_.stream)(param);
}
auto __lambda759__(auto param){
	return frames.stream().flatMap(List_.stream)
                    .next(param);
}
auto __lambda760__(auto list_){
	return List_.stream(list_);
}
auto __lambda761__(auto param){
	return frames.stream().flatMap(List_.stream)(param);
}
auto __lambda762__(auto param){
	return frames.stream().flatMap(List_.stream)
                    .next()(param);
}
auto __lambda763__(auto param){
	return frames.stream().flatMap(List_.stream)
                    .next()
                    .isEmpty()(param);
}
auto __lambda764__(auto list_){
	return List_.stream(list_);
}
auto __lambda765__(auto list_){
	return List_.stream(list_);
}
/* private */int isNothingDefined(){
	return frames.stream().flatMap(__lambda750__).next().isEmpty();
}
auto __lambda766__(auto typearguments){
	return typeArguments.apply(typearguments);
}
auto __lambda767__(auto string){
	return String.equals(string);
}
auto __lambda768__(auto param){
	return Lists.indexOf(frames.last(), input, String.equals)(param);
}
auto __lambda769__(auto param){
	return Lists.indexOf(frames.last(), input, String.equals).flatMap(param);
}
auto __lambda770__(auto string){
	return String.equals(string);
}
auto __lambda771__(auto param){
	return Lists.indexOf(frames.last(), input, String.equals)(param);
}
auto __lambda772__(auto string){
	return String.equals(string);
}
auto __lambda773__(auto param){
	return Lists.indexOf(frames.last(), input, String.equals)(param);
}
auto __lambda774__(auto param){
	return Lists.indexOf(frames.last(), input, String.equals).flatMap(typeArguments(param);
}
auto __lambda775__(auto param){
	return Lists.indexOf(frames.last(), input, __lambda772__).flatMap(typeArguments.apply)(param);
}
auto __lambda776__(auto typearguments){
	return typeArguments.apply(typearguments);
}
auto __lambda777__(auto string){
	return String.equals(string);
}
/* private */Option_String findArgumentValue(String input){
	return Lists.indexOf(frames.last(), input, __lambda767__).flatMap(__lambda766__);
}
auto __lambda778__(auto string){
	return String.equals(string);
}
auto __lambda779__(auto param){
	return Lists.contains(frame, stripped, String.equals)(param);
}
auto __lambda780__(auto frame){
	return Lists.contains(frame, stripped, __lambda778__);
}
auto __lambda781__(auto string){
	return String.equals(string);
}
auto __lambda782__(auto frame){
	return Lists.contains;
}
auto __lambda783__(auto frame){
	return Lists;
}
auto __lambda784__(auto frame){
	return Lists;
}
auto __lambda785__(auto frame){
	return Lists.contains(frame, stripped, String;
}
auto __lambda786__(auto frame){
	return Lists;
}
auto __lambda787__(auto __lambda785__){
	return __lambda785__.equals)(__lambda785__);
}
auto __lambda788__(auto string){
	return String.equals(string);
}
auto __lambda789__(auto param){
	return frames.stream().anyMatch(frame -> Lists.contains(frame, stripped, String.equals))(param);
}
auto __lambda790__(auto string){
	return String.equals(string);
}
auto __lambda791__(auto param){
	return Lists.contains(frame, stripped, String.equals)(param);
}
auto __lambda792__(auto frame){
	return Lists.contains(frame, stripped, __lambda790__);
}
auto __lambda793__(auto string){
	return String.equals(string);
}
auto __lambda794__(auto frame){
	return Lists.contains;
}
auto __lambda795__(auto frame){
	return Lists;
}
auto __lambda796__(auto frame){
	return Lists;
}
auto __lambda797__(auto frame){
	return Lists.contains(frame, stripped, String;
}
auto __lambda798__(auto frame){
	return Lists;
}
auto __lambda799__(auto __lambda797__){
	return __lambda797__.equals)(__lambda797__);
}
auto __lambda800__(auto string){
	return String.equals(string);
}
/* private */int isTypeParam(String stripped){
	return frames.stream().anyMatch(__lambda780__);
}
/* private */ParseState withTypeArguments(List__String typeArguments){
	return ParseState(frames, typeArguments);
}
/* private */ParseState define(List__String frame){
	return ParseState(frames.add(frame), typeArguments);
}
/* @Override
        public */String display(){
	return child.display();
}
auto __lambda801__(auto error){
	return System.err.println(error.display());
}
auto __lambda802__(auto error){
	return System.err.println;
}
auto __lambda803__(auto error){
	return System.err;
}
auto __lambda804__(auto error){
	return System;
}
auto __lambda805__(auto error){
	return System.err.println(error;
}
auto __lambda806__(auto error){
	return System.err;
}
auto __lambda807__(auto error){
	return System;
}
auto __lambda808__(auto input){
	return runWithInput(source, input);
}
auto __lambda809__(auto input){
	return runWithInput;
}
auto __lambda810__(auto some){
	return Some.new(some);
}
auto __lambda811__(auto param){
	return source.readString().match(input -> runWithInput(source, input), Some.new)(param);
}
auto __lambda812__(auto param){
	return source.readString().match(input -> runWithInput(source, input), Some.new)
                .ifPresent(param);
}
/* public static */void main(String* args){
	Path_ source = Paths.get(".", "src", "java", "magma", "Main.java");
	source.readString().match(__lambda808__, __lambda810__).ifPresent(__lambda801__);
}
auto __lambda813__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda814__(auto param){
	return target.writeString(output).map(ApplicationError.new)(param);
}
auto __lambda815__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda816__(auto output){
	return 
	Path_ target = source.resolveSibling("main.c");
	return target.writeString(output).map(__lambda813__);;
}
auto __lambda817__(auto some){
	return Some.new(some);
}
auto __lambda818__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda819__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda820__(auto value){
	return value;
}
auto __lambda821__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda822__(auto value){
	return value;
}
auto __lambda823__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda824__(auto value){
	return value;
}
auto __lambda825__(auto param){
	return compile(input).mapValue(__lambda823__).mapErr(ApplicationError.new)(param);
}
auto __lambda826__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda827__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda828__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda829__(auto value){
	return value;
}
auto __lambda830__(auto param){
	return compile(input).mapValue(__lambda828__).mapErr(ApplicationError.new)
                .match(param);
}
auto __lambda831__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda832__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda833__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda834__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda835__(auto value){
	return value;
}
auto __lambda836__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda837__(auto value){
	return value;
}
auto __lambda838__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda839__(auto value){
	return value;
}
auto __lambda840__(auto param){
	return compile(input).mapValue(__lambda838__).mapErr(ApplicationError.new)(param);
}
auto __lambda841__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda842__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda843__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda844__(auto value){
	return value;
}
auto __lambda845__(auto param){
	return compile(input).mapValue(__lambda843__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source(param);
}
auto __lambda846__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda847__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda848__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda849__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda850__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda851__(auto value){
	return value;
}
auto __lambda852__(auto param){
	return compile(input).mapValue(__lambda850__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main(param);
}
auto __lambda853__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda854__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda855__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda856__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda857__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda858__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda859__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda860__(auto value){
	return value;
}
auto __lambda861__(auto param){
	return compile(input).mapValue(__lambda859__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target(param);
}
auto __lambda862__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda863__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda864__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda865__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda866__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda867__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda868__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda869__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda870__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda871__(auto value){
	return value;
}
auto __lambda872__(auto param){
	return compile(input).mapValue(__lambda870__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target.writeString(param);
}
auto __lambda873__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda874__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda875__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda876__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda877__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda878__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda879__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda880__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda881__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda882__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda883__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda884__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda885__(auto value){
	return value;
}
auto __lambda886__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda887__(auto value){
	return value;
}
auto __lambda888__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda889__(auto value){
	return value;
}
auto __lambda890__(auto param){
	return compile(input).mapValue(__lambda888__).mapErr(ApplicationError.new)(param);
}
auto __lambda891__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda892__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda893__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda894__(auto value){
	return value;
}
auto __lambda895__(auto param){
	return compile(input).mapValue(__lambda893__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source(param);
}
auto __lambda896__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda897__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda898__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda899__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda900__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda901__(auto value){
	return value;
}
auto __lambda902__(auto param){
	return compile(input).mapValue(__lambda900__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main(param);
}
auto __lambda903__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda904__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda905__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda906__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda907__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda908__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda909__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda910__(auto value){
	return value;
}
auto __lambda911__(auto param){
	return compile(input).mapValue(__lambda909__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target(param);
}
auto __lambda912__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda913__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda914__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda915__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda916__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda917__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda918__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda919__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda920__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda921__(auto value){
	return value;
}
auto __lambda922__(auto param){
	return compile(input).mapValue(__lambda920__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target.writeString(output)(param);
}
auto __lambda923__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda924__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda925__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda926__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda927__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda928__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda929__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda930__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda931__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda932__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda933__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda934__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda935__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda936__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda937__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda938__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda939__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda940__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda941__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda942__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda943__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda944__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda945__(auto value){
	return value;
}
auto __lambda946__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda947__(auto value){
	return value;
}
auto __lambda948__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda949__(auto value){
	return value;
}
auto __lambda950__(auto param){
	return compile(input).mapValue(__lambda948__).mapErr(ApplicationError.new)(param);
}
auto __lambda951__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda952__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda953__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda954__(auto value){
	return value;
}
auto __lambda955__(auto param){
	return compile(input).mapValue(__lambda953__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source(param);
}
auto __lambda956__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda957__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda958__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda959__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda960__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda961__(auto value){
	return value;
}
auto __lambda962__(auto param){
	return compile(input).mapValue(__lambda960__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main(param);
}
auto __lambda963__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda964__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda965__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda966__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda967__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda968__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda969__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda970__(auto value){
	return value;
}
auto __lambda971__(auto param){
	return compile(input).mapValue(__lambda969__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target(param);
}
auto __lambda972__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda973__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda974__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda975__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda976__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda977__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda978__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda979__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda980__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda981__(auto value){
	return value;
}
auto __lambda982__(auto param){
	return compile(input).mapValue(__lambda980__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target.writeString(param);
}
auto __lambda983__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda984__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda985__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda986__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda987__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda988__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda989__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda990__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda991__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda992__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda993__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda994__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda995__(auto value){
	return value;
}
auto __lambda996__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda997__(auto value){
	return value;
}
auto __lambda998__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda999__(auto value){
	return value;
}
auto __lambda1000__(auto param){
	return compile(input).mapValue(__lambda998__).mapErr(ApplicationError.new)(param);
}
auto __lambda1001__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1002__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1003__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1004__(auto value){
	return value;
}
auto __lambda1005__(auto param){
	return compile(input).mapValue(__lambda1003__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source(param);
}
auto __lambda1006__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1007__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1008__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1009__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1010__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1011__(auto value){
	return value;
}
auto __lambda1012__(auto param){
	return compile(input).mapValue(__lambda1010__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main(param);
}
auto __lambda1013__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1014__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1015__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1016__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1017__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1018__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1019__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1020__(auto value){
	return value;
}
auto __lambda1021__(auto param){
	return compile(input).mapValue(__lambda1019__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target(param);
}
auto __lambda1022__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1023__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1024__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1025__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1026__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1027__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1028__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1029__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1030__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1031__(auto value){
	return value;
}
auto __lambda1032__(auto param){
	return compile(input).mapValue(__lambda1030__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target.writeString(output)(param);
}
auto __lambda1033__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1034__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1035__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1036__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1037__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1038__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1039__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1040__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1041__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1042__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1043__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1044__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1045__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1046__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1047__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1048__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1049__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1050__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1051__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1052__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1053__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1054__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1055__(auto value){
	return value;
}
auto __lambda1056__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1057__(auto value){
	return value;
}
auto __lambda1058__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1059__(auto value){
	return value;
}
auto __lambda1060__(auto param){
	return compile(input).mapValue(__lambda1058__).mapErr(ApplicationError.new)(param);
}
auto __lambda1061__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1062__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1063__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1064__(auto value){
	return value;
}
auto __lambda1065__(auto param){
	return compile(input).mapValue(__lambda1063__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source(param);
}
auto __lambda1066__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1067__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1068__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1069__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1070__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1071__(auto value){
	return value;
}
auto __lambda1072__(auto param){
	return compile(input).mapValue(__lambda1070__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main(param);
}
auto __lambda1073__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1074__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1075__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1076__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1077__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1078__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1079__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1080__(auto value){
	return value;
}
auto __lambda1081__(auto param){
	return compile(input).mapValue(__lambda1079__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target(param);
}
auto __lambda1082__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1083__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1084__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1085__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1086__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1087__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1088__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1089__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1090__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1091__(auto value){
	return value;
}
auto __lambda1092__(auto param){
	return compile(input).mapValue(__lambda1090__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target.writeString(param);
}
auto __lambda1093__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1094__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1095__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1096__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1097__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1098__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1099__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1100__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1101__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1102__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1103__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1104__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1105__(auto value){
	return value;
}
auto __lambda1106__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1107__(auto value){
	return value;
}
auto __lambda1108__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1109__(auto value){
	return value;
}
auto __lambda1110__(auto param){
	return compile(input).mapValue(__lambda1108__).mapErr(ApplicationError.new)(param);
}
auto __lambda1111__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1112__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1113__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1114__(auto value){
	return value;
}
auto __lambda1115__(auto param){
	return compile(input).mapValue(__lambda1113__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source(param);
}
auto __lambda1116__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1117__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1118__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1119__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1120__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1121__(auto value){
	return value;
}
auto __lambda1122__(auto param){
	return compile(input).mapValue(__lambda1120__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main(param);
}
auto __lambda1123__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1124__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1125__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1126__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1127__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1128__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1129__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1130__(auto value){
	return value;
}
auto __lambda1131__(auto param){
	return compile(input).mapValue(__lambda1129__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target(param);
}
auto __lambda1132__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1133__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1134__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1135__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1136__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1137__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1138__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1139__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1140__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1141__(auto value){
	return value;
}
auto __lambda1142__(auto param){
	return compile(input).mapValue(__lambda1140__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target.writeString(output)(param);
}
auto __lambda1143__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1144__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1145__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1146__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1147__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1148__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1149__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1150__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1151__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1152__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1153__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1154__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1155__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1156__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1157__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1158__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1159__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1160__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1161__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1162__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1163__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1164__(auto value){
	return value;
}
auto __lambda1165__(auto param){
	return compile(input).mapValue(__lambda1163__).mapErr(ApplicationError.new)
                .match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target.writeString(output).map(ApplicationError(param);
}
auto __lambda1166__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1167__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1168__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1169__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1170__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1171__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1172__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1173__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1174__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1175__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1176__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1177__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1178__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1179__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1180__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1181__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1182__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1183__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1184__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1185__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1186__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1187__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1188__(auto param){
	return compile(input).mapValue(__lambda1054__).mapErr(__lambda1053__).match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target.writeString(output).map(ApplicationError.new);
                }, Some(param);
}
auto __lambda1189__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1190__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1191__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1192__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1193__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1194__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1195__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1196__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1197__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1198__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1199__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1200__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1201__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1202__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1203__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1204__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1205__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1206__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1207__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1208__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1209__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1210__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1211__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1212__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1213__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1214__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1215__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1216__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1217__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1218__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1219__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1220__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1221__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1222__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1223__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1224__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1225__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1226__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1227__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1228__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1229__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1230__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1231__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1232__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1233__(auto param){
	return compile(input).mapValue(__lambda944__).mapErr(__lambda943__).match(output -> {
                    Path_ target = source.resolveSibling("main.c");
                    return target.writeString(output).map(ApplicationError::new);
                }, Some.new)(param);
}
auto __lambda1234__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1235__(auto param){
	return target.writeString(output).map(ApplicationError.new)(param);
}
auto __lambda1236__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1237__(auto output){
	return 
	Path_ target = source.resolveSibling("main.c");
	return target.writeString(output).map(__lambda1234__);;
}
auto __lambda1238__(auto some){
	return Some.new(some);
}
auto __lambda1239__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1240__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1241__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1242__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1243__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1244__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1245__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1246__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1247__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1248__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1249__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1250__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1251__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1252__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1253__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1254__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1255__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1256__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1257__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1258__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1259__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1260__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1261__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1262__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1263__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1264__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1265__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1266__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1267__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1268__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1269__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1270__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1271__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1272__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1273__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1274__(auto param){
	return target.writeString(output).map(ApplicationError.new)(param);
}
auto __lambda1275__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1276__(auto output){
	return 
	Path_ target = source.resolveSibling("main.c");
	return target.writeString(output).map(__lambda1273__);;
}
auto __lambda1277__(auto some){
	return Some.new(some);
}
auto __lambda1278__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1279__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1280__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1281__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1282__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1283__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1284__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1285__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1286__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1287__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1288__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1289__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1290__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1291__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1292__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1293__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1294__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1295__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1296__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1297__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1298__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1299__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1300__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1301__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1302__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1303__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1304__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1305__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1306__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1307__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1308__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1309__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1310__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1311__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1312__(auto param){
	return source.resolveSibling("main.c");
                    return target.writeString(output).map(ApplicationError.new);
                }, Some(param);
}
auto __lambda1313__(auto param){
	return source.resolveSibling("main.c");
                    return target.writeString(output).map(ApplicationError::new);
                }, Some.new)(param);
}
auto __lambda1314__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1315__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1316__(auto value){
	return value;
}
auto __lambda1317__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1318__(auto value){
	return value;
}
auto __lambda1319__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1320__(auto value){
	return value;
}
auto __lambda1321__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1322__(auto value){
	return value;
}
auto __lambda1323__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1324__(auto param){
	return target.writeString(output).map(ApplicationError.new)(param);
}
auto __lambda1325__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1326__(auto output){
	return 
	Path_ target = source.resolveSibling("main.c");
	return target.writeString(output).map(__lambda1323__);;
}
auto __lambda1327__(auto some){
	return Some.new(some);
}
auto __lambda1328__(auto applicationerror){
	return ApplicationError.new(applicationerror);
}
auto __lambda1329__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1330__(auto value){
	return value;
}
auto __lambda1331__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1332__(auto value){
	return value;
}
auto __lambda1333__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1334__(auto value){
	return value;
}
auto __lambda1335__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda1336__(auto value){
	return value;
}
/* private static */Option_ApplicationError runWithInput(Path_ source, String input){
	return compile(input).mapValue(__lambda819__).mapErr(__lambda818__).match(__lambda816__, __lambda817__);
}
auto __lambda1337__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda1338__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda1339__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda1340__(auto compiled){
	return mergeAll(compiled, __lambda1339__);
}
auto __lambda1341__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda1342__(auto compiled){
	return mergeAll;
}
auto __lambda1343__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda1344__(auto main){
	return Main.generate(main);
}
auto __lambda1345__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1346__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1347__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1348__(auto param){
	return parseAll(segments, __lambda1347__).flatMapValue(Main.generate)(param);
}
auto __lambda1349__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1350__(auto param){
	return parseAll(segments, __lambda1349__).flatMapValue(Main.generate)
                .mapValue(param);
}
auto __lambda1351__(auto main){
	return Main.generate(main);
}
auto __lambda1352__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1353__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1354__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1355__(auto param){
	return parseAll(segments, __lambda1354__).flatMapValue(Main.generate)(param);
}
auto __lambda1356__(auto main){
	return Main.generate(main);
}
auto __lambda1357__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1358__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1359__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1360__(auto param){
	return parseAll(segments, __lambda1359__).flatMapValue(Main.generate)(param);
}
auto __lambda1361__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1362__(auto param){
	return parseAll(segments, __lambda1361__).flatMapValue(Main.generate)
                .mapValue(compiled -> mergeAll(compiled, Main(param);
}
auto __lambda1363__(auto main){
	return Main.generate(main);
}
auto __lambda1364__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1365__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1366__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1367__(auto param){
	return parseAll(segments, __lambda1366__).flatMapValue(Main.generate)(param);
}
auto __lambda1368__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1369__(auto param){
	return parseAll(segments, __lambda1368__).flatMapValue(Main.generate)
                .mapValue(compiled(param);
}
auto __lambda1370__(auto param){
	return parseAll(segments, __lambda1357__).flatMapValue(__lambda1356__).mapValue(compiled -> mergeAll(compiled, Main.mergeStatements))(param);
}
auto __lambda1371__(auto main){
	return Main.generate(main);
}
auto __lambda1372__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1373__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1374__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1375__(auto param){
	return parseAll(segments, __lambda1374__).flatMapValue(Main.generate)(param);
}
auto __lambda1376__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1377__(auto param){
	return parseAll(segments, __lambda1376__).flatMapValue(Main.generate)
                .mapValue(compiled(param);
}
auto __lambda1378__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda1379__(auto compiled){
	return mergeAll(compiled, __lambda1378__);
}
auto __lambda1380__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda1381__(auto compiled){
	return mergeAll;
}
auto __lambda1382__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda1383__(auto main){
	return Main.generate(main);
}
auto __lambda1384__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1385__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1386__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda1387__(auto main){
	return Main.compileRootSegment(main);
}
/* private static */Result_String_CompileError compile(String input){
	List__String segments = divideAll(input, __lambda1337__);
	return parseAll(segments, __lambda1345__).flatMapValue(__lambda1344__).mapValue(__lambda1340__);
}
auto __lambda1388__(auto err){
	return Err.new(err);
}
auto __lambda1389__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll(globals).addAll(functions));
}
auto __lambda1390__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll(globals);
}
auto __lambda1391__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll;
}
auto __lambda1392__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs);
}
auto __lambda1393__(auto ){
	return Ok(compiled.addAll(imports).addAll;
}
auto __lambda1394__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda1395__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda1396__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs);
}
auto __lambda1397__(auto ){
	return Ok(compiled.addAll(imports).addAll;
}
auto __lambda1398__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda1399__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda1400__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(param);
}
auto __lambda1401__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1402__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1403__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(param);
}
auto __lambda1404__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1405__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1406__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(param);
}
auto __lambda1407__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1408__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1409__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)(param);
}
auto __lambda1410__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1411__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1412__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(param);
}
auto __lambda1413__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1414__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1415__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(param);
}
auto __lambda1416__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1417__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1418__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(param);
}
auto __lambda1419__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1420__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1421__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(param);
}
auto __lambda1422__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1423__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1424__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)(param);
}
auto __lambda1425__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1426__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1427__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(structs)(param);
}
auto __lambda1428__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1429__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1430__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(structs)
                .addAll(param);
}
auto __lambda1431__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1432__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1433__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(param);
}
auto __lambda1434__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1435__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1436__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(param);
}
auto __lambda1437__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1438__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1439__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(param);
}
auto __lambda1440__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1441__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1442__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)(param);
}
auto __lambda1443__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1444__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1445__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(param);
}
auto __lambda1446__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1447__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1448__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(param);
}
auto __lambda1449__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1450__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1451__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(param);
}
auto __lambda1452__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1453__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1454__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(param);
}
auto __lambda1455__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1456__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1457__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)(param);
}
auto __lambda1458__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1459__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1460__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(structs)(param);
}
auto __lambda1461__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1462__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1463__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(structs)
                .addAll(globals)(param);
}
auto __lambda1464__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1465__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1466__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(structs)
                .addAll(globals)
                .addAll(functions)))(param);
}
auto __lambda1467__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(param);
}
auto __lambda1468__(auto param){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(param);
}
auto __lambda1469__(auto err){
	return Err.new(err);
}
auto __lambda1470__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll(globals).addAll(functions));
}
auto __lambda1471__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll(globals);
}
auto __lambda1472__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll;
}
auto __lambda1473__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs);
}
auto __lambda1474__(auto ){
	return Ok(compiled.addAll(imports).addAll;
}
auto __lambda1475__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda1476__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda1477__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs);
}
auto __lambda1478__(auto ){
	return Ok(compiled.addAll(imports).addAll;
}
auto __lambda1479__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda1480__(auto ){
	return Ok(compiled.addAll(imports);
}
/* private static */Result_List__String_CompileError generate(List__String compiled){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(__lambda1388__, __lambda1389__);
}
/* private static */Option_CompileError expandAllGenerics(){
	while (1) {
	}
	return None();
}
auto __lambda1481__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda1482__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda1483__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda1484__(auto main){
	return Main.mergeStatements(main);
}
/* private static */Result_String_CompileError compileStatements(String input, Rule compiler){
	return compileAll(divideAll(input, __lambda1481__), compiler, __lambda1482__);
}
auto __lambda1485__(auto compiled){
	return mergeAll(compiled, merger);
}
auto __lambda1486__(auto compiled){
	return mergeAll;
}
auto __lambda1487__(auto compiled){
	return mergeAll(compiled, merger);
}
auto __lambda1488__(auto compiled){
	return mergeAll;
}
/* private static */Result_String_CompileError compileAll(List__String segments, Rule compiler, StringBuilder (*)(StringBuilder, String) merger){
	return parseAll(segments, compiler).mapValue(__lambda1485__);
}
/* private static */String mergeAll(List__String compiled, StringBuilder (*)(StringBuilder, String) merger){
	return compiled.stream().foldWithInitial(StringBuilder(), merger).toString();
}
auto __lambda1489__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda1490__(auto param){
	return compiler.compile(segment).mapValue(compiled.add)(param);
}
auto __lambda1491__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue(__lambda1489__);
}
auto __lambda1492__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda1493__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue;
}
auto __lambda1494__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda1495__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda1496__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1497__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1498__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda1499__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda1500__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1501__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1502__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue(compiled;
}
auto __lambda1503__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda1504__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda1505__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1506__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1507__(auto __lambda1502__){
	return __lambda1502__.add)(__lambda1502__);
}
auto __lambda1508__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda1509__(auto param){
	return segments.stream().foldToResult(Lists.empty(), (compiled, segment) -> compiler.compile(segment).mapValue(compiled.add))(param);
}
auto __lambda1510__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda1511__(auto param){
	return compiler.compile(segment).mapValue(compiled.add)(param);
}
auto __lambda1512__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue(__lambda1510__);
}
auto __lambda1513__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda1514__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue;
}
auto __lambda1515__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda1516__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda1517__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1518__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1519__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda1520__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda1521__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1522__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1523__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue(compiled;
}
auto __lambda1524__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda1525__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda1526__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1527__(auto compiled, auto segment){
	return compiler;
}
auto __lambda1528__(auto __lambda1523__){
	return __lambda1523__.add)(__lambda1523__);
}
auto __lambda1529__(auto compiled){
	return compiled.add(compiled);
}
/* private static */Result_List__String_CompileError parseAll(List__String segments, Rule compiler){
	return segments.stream().foldToResult(Lists.empty(), __lambda1491__);
}
/* private static */StringBuilder mergeStatements(StringBuilder output, String str){
	return output.append(str);
}
/* private static */List__String divideAll(String input, State (*)(State, Character) divider){
	List__Character queue = Streams.from(input).collect(ListCollector());
	State current = State(queue);
	while (1) {
	}
	return current.advance().segments();
}
/* private static */Option_State divideDoubleQuotes(State state, char c){
	if (1) {
	}
	State current = state.append(c);
	while (1) {
	}
	return Some(current);
}
/* private static */Option_State divideSingleQuotes(State current, char c){
	if (1) {
	}
	State appended = current.append(c);
	char maybeEscape = current.pop();
	State withNext = appended.append(maybeEscape);
	State appended1 = maybeEscape == '\\' ? withNext.popAndAppend() : withNext;
	return Some(appended1.popAndAppend());
}
/* private static */State divideStatementChar(State state, char c){
	State appended = state.append(c);
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	return appended;
}
auto __lambda1530__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda1531__(auto main){
	return Main.compilePackage(main);
}
auto __lambda1532__(auto main){
	return Main.compileImport(main);
}
auto __lambda1533__(auto main){
	return Main.compileClass(main);
}
auto __lambda1534__(auto param){
	return Lists.of(
                Main.compileWhitespace,
                Main(param);
}
auto __lambda1535__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main.compilePackage,
                Main(param);
}
auto __lambda1536__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compilePackage,
                Main.compileImport,
                Main(param);
}
auto __lambda1537__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compilePackage,
                Main::compileImport,
                Main.compileClass
        )(param);
}
auto __lambda1538__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda1539__(auto main){
	return Main.compilePackage(main);
}
auto __lambda1540__(auto main){
	return Main.compileImport(main);
}
auto __lambda1541__(auto main){
	return Main.compileClass(main);
}
auto __lambda1542__(auto param){
	return Lists.of(
                Main.compileWhitespace,
                Main(param);
}
auto __lambda1543__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main.compilePackage,
                Main(param);
}
auto __lambda1544__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compilePackage,
                Main.compileImport,
                Main(param);
}
auto __lambda1545__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compilePackage,
                Main::compileImport,
                Main.compileClass
        )(param);
}
/* private static */Result_String_CompileError compileRootSegment(String value){
	return compileOr(value, Lists.of(__lambda1530__, __lambda1531__, __lambda1532__, __lambda1533__));
}
/* private static */Result_String_CompileError compilePackage(String input){
	if (1) {
	}
	return createPrefixErr(input, "package ");
}
/* private static */Result_String_CompileError compileClass(String input){
	List__List__String frame = Lists.<List_<String>>empty().add(Lists.empty());
	return compileTypedBlock(ParseState(frame, Lists.empty()), "class ", input);
}
/* private static */Result_String_CompileError compileImport(String input){
	String stripped = input.strip();
	if (1) {
	}
	String right = stripped.substring("import ".length());
	if (1) {
	}
	String left = right.substring(0, right.length() - ";".length());
	List__String slices = divideByChar(left, '.');
	String joined = slices.stream().collect(Joiner("/")).orElse("");
	String value = "#include \"./%s.h\"\n".formatted(joined);
	imports = imports.add(value);
	return Ok("");
}
/* private static */List__String divideByChar(String value, char delimiter){
	List__String slices = Lists.empty();
	StringBuilder buffer = StringBuilder();
	for (;;) {
	}
	slices = slices.add(buffer.toString());
	return slices;
}
/* private static */Result_String_CompileError compileWhitespace(String input){
	if (1) {
	}
	return Err(CompileError("Not whitespace", input));
}
auto __lambda1546__(auto ){
	return compileToStruct(state.define(Lists.empty()), modifiers, withoutParams, body);
}
auto __lambda1547__(auto ){
	return compileToStruct;
}
auto __lambda1548__(auto ){
	return compileToStruct(state.define(Lists.empty()), modifiers, withoutParams, body);
}
auto __lambda1549__(auto ){
	return compileToStruct;
}
/* private static */Result_String_CompileError compileTypedBlock(ParseState state, String keyword, String input){
	int classIndex = input.indexOf(keyword);
	if (1) {
	}
	String modifiers = input.substring(0, classIndex).strip();
	String afterKeyword = input.substring(classIndex + keyword.length());
	int contentStart = afterKeyword.indexOf("{");
	if (1) {
	}
	String beforeContent = afterKeyword.substring(0, contentStart).strip();
	String body = afterKeyword.substring(contentStart + "{".length()).strip();
	int permitsIndex = beforeContent.indexOf("permits");
	String withoutPermits = permitsIndex >= 0 ? beforeContent.substring(0, permitsIndex).strip() : beforeContent;
	int paramStart = withoutPermits.indexOf("(");
	String withoutParams = paramStart >= 0 ? withoutPermits.substring(0, paramStart) : withoutPermits;
	return compileTypedBlockBody(state, modifiers, withoutParams, body).orElseGet(__lambda1546__);
}
auto __lambda1550__(auto typeArguments){
	return 
	String joined = generateGenericName(name, typeArguments);
	ParseState state1 = state.withTypeArguments(typeArguments);
	return compileToStruct(state1.define(finalClassTypeParams), modifiers, joined, body);;
}
auto __lambda1551__(auto typeArguments){
	return 
	String joined = generateGenericName(name, typeArguments);
	ParseState state1 = state.withTypeArguments(typeArguments);
	return compileToStruct(state1.define(finalClassTypeParams), modifiers, joined, body);;
}
/* private static */Option_Result_String_CompileError compileTypedBlockBody(ParseState state, String modifiers, String nameSegment, String body){
	if (1) {
	}
	String withoutEnd = nameSegment.substring(0, nameSegment.length() - ">".length());
	int genStart = withoutEnd.indexOf("<");
	if (1) {
	}
	String name = withoutEnd.substring(0, genStart);
	String substring = withoutEnd.substring(genStart + "<".length());
	List__String finalClassTypeParams = splitParams(substring);
	generators = generators.with(name, __lambda1550__);
	return Some(Ok(""));
}
auto __lambda1552__(auto outputContent){
	return generateStruct(modifiers, name, outputContent);
}
auto __lambda1553__(auto outputContent){
	return generateStruct;
}
auto __lambda1554__(auto input1){
	return compileClassSegment(defined, input1);
}
auto __lambda1555__(auto input1){
	return compileClassSegment;
}
auto __lambda1556__(auto input1){
	return compileClassSegment(defined, input1);
}
auto __lambda1557__(auto input1){
	return compileClassSegment;
}
auto __lambda1558__(auto outputContent){
	return generateStruct(modifiers, name, outputContent);
}
auto __lambda1559__(auto outputContent){
	return generateStruct;
}
auto __lambda1560__(auto input1){
	return compileClassSegment(defined, input1);
}
auto __lambda1561__(auto input1){
	return compileClassSegment;
}
/* private static */Result_String_CompileError compileToStruct(ParseState defined, String modifiers, String name, String body){
	if (1) {
	}
	String inputContent = body.substring(0, body.length() - "}".length());
	return compileStatements(inputContent, __lambda1554__).mapValue(__lambda1552__);
}
/* private static */Result_String_CompileError createSuffixErr(String input, String suffix){
	return Err(CompileError("Suffix '" + suffix + "' not present", input));
}
/* private static */String generateStruct(String modifiers, String name, String content){
	String modifiersString = modifiers.isEmpty() ? "" : generatePlaceholder(modifiers) + " ";
	String generated = "%stypedef struct %s {%s\n} %s;\n".formatted(modifiersString, name, content, name);
	structs = structs.add(generated);
	return "";
}
auto __lambda1562__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda1563__(auto input){
	return compileTypedBlock(state, "class", input);
}
auto __lambda1564__(auto input){
	return compileTypedBlock;
}
auto __lambda1565__(auto input){
	return compileTypedBlock(state, "interface ", input);
}
auto __lambda1566__(auto input){
	return compileTypedBlock;
}
auto __lambda1567__(auto input){
	return compileTypedBlock(state, "record ", input);
}
auto __lambda1568__(auto input){
	return compileTypedBlock;
}
auto __lambda1569__(auto input){
	return compileMethod(state, input);
}
auto __lambda1570__(auto input){
	return compileMethod;
}
auto __lambda1571__(auto input){
	return compileGlobal(state, input);
}
auto __lambda1572__(auto input){
	return compileGlobal;
}
auto __lambda1573__(auto input){
	return compileDefinitionStatement(state, input);
}
auto __lambda1574__(auto input){
	return compileDefinitionStatement;
}
auto __lambda1575__(auto param){
	return Lists.of(
                Main.compileWhitespace,
                input -> compileTypedBlock(state, "class", input),
                input -> compileTypedBlock(state, "interface ", input),
                input -> compileTypedBlock(state, "record ", input),
                input -> compileMethod(state, input),
                input -> compileGlobal(state, input),
                input -> compileDefinitionStatement(state, input)
        )(param);
}
auto __lambda1576__(auto param){
	return Lists.of(
                Main.compileWhitespace,
                input(param);
}
auto __lambda1577__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda1578__(auto input){
	return compileTypedBlock(state, "class", input);
}
auto __lambda1579__(auto input){
	return compileTypedBlock;
}
auto __lambda1580__(auto input){
	return compileTypedBlock(state, "interface ", input);
}
auto __lambda1581__(auto input){
	return compileTypedBlock;
}
auto __lambda1582__(auto input){
	return compileTypedBlock(state, "record ", input);
}
auto __lambda1583__(auto input){
	return compileTypedBlock;
}
auto __lambda1584__(auto input){
	return compileMethod(state, input);
}
auto __lambda1585__(auto input){
	return compileMethod;
}
auto __lambda1586__(auto input){
	return compileGlobal(state, input);
}
auto __lambda1587__(auto input){
	return compileGlobal;
}
auto __lambda1588__(auto input){
	return compileDefinitionStatement(state, input);
}
auto __lambda1589__(auto input){
	return compileDefinitionStatement;
}
auto __lambda1590__(auto param){
	return Lists.of(
                Main.compileWhitespace,
                input -> compileTypedBlock(state, "class", input),
                input -> compileTypedBlock(state, "interface ", input),
                input -> compileTypedBlock(state, "record ", input),
                input -> compileMethod(state, input),
                input -> compileGlobal(state, input),
                input -> compileDefinitionStatement(state, input)
        )(param);
}
auto __lambda1591__(auto param){
	return Lists.of(
                Main.compileWhitespace,
                input(param);
}
/* private static */Result_String_CompileError compileClassSegment(ParseState state, String value){
	return compileOr(value, Lists.of(__lambda1562__, __lambda1563__, __lambda1565__, __lambda1567__, __lambda1569__, __lambda1571__, __lambda1573__));
}
auto __lambda1592__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1593__(auto param){
	return compileDefinition(sliced, state).mapValue(Main.generateStatement)(param);
}
auto __lambda1594__(auto main){
	return Main.generateStatement(main);
}
/* private static */Result_String_CompileError compileDefinitionStatement(ParseState state, String input){
	if (1) {
	}
	String sliced = input.substring(0, input.length() - ";".length());
	return compileDefinition(sliced, state).mapValue(__lambda1592__);
}
auto __lambda1595__(auto result){
	return 
	globals = result;
	return "";;
}
auto __lambda1596__(auto globals){
	return globals.add(globals);
}
auto __lambda1597__(auto value){
	return value + ";\n";
}
auto __lambda1598__(auto value){
	return value;
}
auto __lambda1599__(auto value){
	return value + ";\n";
}
auto __lambda1600__(auto value){
	return value;
}
auto __lambda1601__(auto value){
	return value + ";\n";
}
auto __lambda1602__(auto value){
	return value;
}
auto __lambda1603__(auto param){
	return maybeInitialization.mapValue(__lambda1601__).mapValue(globals.add)(param);
}
auto __lambda1604__(auto globals){
	return globals.add(globals);
}
auto __lambda1605__(auto globals){
	return globals.add(globals);
}
auto __lambda1606__(auto value){
	return value + ";\n";
}
auto __lambda1607__(auto value){
	return value;
}
auto __lambda1608__(auto param){
	return maybeInitialization.mapValue(__lambda1606__).mapValue(globals.add)
                .mapValue(param);
}
auto __lambda1609__(auto globals){
	return globals.add(globals);
}
auto __lambda1610__(auto globals){
	return globals.add(globals);
}
auto __lambda1611__(auto globals){
	return globals.add(globals);
}
auto __lambda1612__(auto value){
	return value + ";\n";
}
auto __lambda1613__(auto value){
	return value;
}
auto __lambda1614__(auto value){
	return value + ";\n";
}
auto __lambda1615__(auto value){
	return value;
}
auto __lambda1616__(auto value){
	return value + ";\n";
}
auto __lambda1617__(auto value){
	return value;
}
auto __lambda1618__(auto param){
	return maybeInitialization.mapValue(__lambda1616__).mapValue(globals.add)(param);
}
auto __lambda1619__(auto globals){
	return globals.add(globals);
}
auto __lambda1620__(auto globals){
	return globals.add(globals);
}
auto __lambda1621__(auto value){
	return value + ";\n";
}
auto __lambda1622__(auto value){
	return value;
}
auto __lambda1623__(auto param){
	return maybeInitialization.mapValue(__lambda1621__).mapValue(globals.add)
                .mapValue(result -> {
                    globals = result;
                    return "";
                })(param);
}
auto __lambda1624__(auto result){
	return 
	globals = result;
	return "";;
}
auto __lambda1625__(auto globals){
	return globals.add(globals);
}
auto __lambda1626__(auto globals){
	return globals.add(globals);
}
auto __lambda1627__(auto globals){
	return globals.add(globals);
}
auto __lambda1628__(auto result){
	return 
	globals = result;
	return "";;
}
auto __lambda1629__(auto globals){
	return globals.add(globals);
}
auto __lambda1630__(auto globals){
	return globals.add(globals);
}
auto __lambda1631__(auto globals){
	return globals.add(globals);
}
auto __lambda1632__(auto globals){
	return globals.add(globals);
}
auto __lambda1633__(auto value){
	return value + ";\n";
}
auto __lambda1634__(auto value){
	return value;
}
auto __lambda1635__(auto value){
	return value + ";\n";
}
auto __lambda1636__(auto value){
	return value;
}
auto __lambda1637__(auto value){
	return value + ";\n";
}
auto __lambda1638__(auto value){
	return value;
}
auto __lambda1639__(auto value){
	return value + ";\n";
}
auto __lambda1640__(auto value){
	return value;
}
auto __lambda1641__(auto result){
	return 
	globals = result;
	return "";;
}
auto __lambda1642__(auto globals){
	return globals.add(globals);
}
auto __lambda1643__(auto value){
	return value + ";\n";
}
auto __lambda1644__(auto value){
	return value;
}
auto __lambda1645__(auto value){
	return value + ";\n";
}
auto __lambda1646__(auto value){
	return value;
}
auto __lambda1647__(auto value){
	return value + ";\n";
}
auto __lambda1648__(auto value){
	return value;
}
auto __lambda1649__(auto value){
	return value + ";\n";
}
auto __lambda1650__(auto value){
	return value;
}
/* private static */Result_String_CompileError compileGlobal(ParseState state, String input){
	if (1) {
	}
	String substring = input.substring(0, input.length() - ";".length());
	Result_String_CompileError maybeInitialization = compileInitialization(state, substring);
	return maybeInitialization.mapValue(__lambda1597__).mapValue(__lambda1596__).mapValue(__lambda1595__);
}
auto __lambda1651__(auto outputParams){
	return compileMethodWithDefinition(state, outputParams, header, withBody);
}
auto __lambda1652__(auto outputParams){
	return compileMethodWithDefinition;
}
auto __lambda1653__(auto input1){
	return compileDefinition(input1, state);
}
auto __lambda1654__(auto input1){
	return compileDefinition;
}
auto __lambda1655__(auto input1){
	return compileDefinition(input1, state);
}
auto __lambda1656__(auto input1){
	return compileDefinition;
}
auto __lambda1657__(auto outputParams){
	return compileMethodWithDefinition(state, outputParams, header, withBody);
}
auto __lambda1658__(auto outputParams){
	return compileMethodWithDefinition;
}
auto __lambda1659__(auto input1){
	return compileDefinition(input1, state);
}
auto __lambda1660__(auto input1){
	return compileDefinition;
}
/* private static */Result_String_CompileError compileMethod(ParseState state, String input){
	int paramStart = input.indexOf("(");
	if (1) {
	}
	String header = input.substring(0, paramStart).strip();
	String withParams = input.substring(paramStart + "(".length());
	int paramEnd = withParams.indexOf(")");
	if (1) {
	}
	String paramString = withParams.substring(0, paramEnd);
	String withBody = withParams.substring(paramEnd + ")".length()).strip();
	return compileValues(paramString, __lambda1653__).flatMapValue(__lambda1651__);
}
auto __lambda1661__(auto definition){
	return compileMethodBody(state, definition, outputParams, withBody);
}
auto __lambda1662__(auto definition){
	return compileMethodBody;
}
auto __lambda1663__(auto definition){
	return compileMethodBody(state, definition, outputParams, withBody);
}
auto __lambda1664__(auto definition){
	return compileMethodBody;
}
/* private static */Result_String_CompileError compileMethodWithDefinition(ParseState state, String outputParams, String header, String withBody){
	return getStringCompileErrorResult(state, header).flatMapValue(__lambda1661__);
}
/* private static */Result_String_CompileError getStringCompileErrorResult(ParseState state, String header){
	return compileDefinition(header, state.withTypeArguments(state.typeArguments));
}
auto __lambda1665__(auto statement){
	return addFunction(statement, string);
}
auto __lambda1666__(auto statement){
	return addFunction;
}
auto __lambda1667__(auto input1){
	return compileStatementOrBlock(state, input1);
}
auto __lambda1668__(auto input1){
	return compileStatementOrBlock;
}
auto __lambda1669__(auto input1){
	return compileStatementOrBlock(state, input1);
}
auto __lambda1670__(auto input1){
	return compileStatementOrBlock;
}
auto __lambda1671__(auto statement){
	return addFunction(statement, string);
}
auto __lambda1672__(auto statement){
	return addFunction;
}
auto __lambda1673__(auto input1){
	return compileStatementOrBlock(state, input1);
}
auto __lambda1674__(auto input1){
	return compileStatementOrBlock;
}
/* private static */Result_String_CompileError compileMethodBody(ParseState state, String definition, String outputParams, String withBody){
	String string = generateInvokable(definition, outputParams);
	if (1) {
	}
	return compileStatements(withBody.substring(1, withBody.length() - 1), __lambda1667__).mapValue(__lambda1665__);
}
/* private static */String addFunction(String content, String string){
	String function = string + "{" + content + "\n}\n";
	functions = functions.add(function);
	return "";
}
/* private static */String generateInvokable(String definition, String params){
	return definition + "(" + params + ")";
}
auto __lambda1675__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda1676__(auto main){
	return Main.mergeValues(main);
}
auto __lambda1677__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda1678__(auto main){
	return Main.mergeValues(main);
}
/* private static */Result_String_CompileError compileValues(String input, Rule compiler){
	return compileAll(divideAll(input, __lambda1675__), compiler, __lambda1676__);
}
/* private static */State divideValueChar(State state, Character c){
	if (1) {
	}
	State appended = state.append(c);
	if (1) {
	}
	if (1) {
	}
	if (1) {
	}
	return appended;
}
/* private static */StringBuilder mergeValues(StringBuilder buffer, String element){
	if (1) {
	}
	return buffer.append(", ").append(element);
}
auto __lambda1679__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda1680__(auto main){
	return Main.compileIf(main);
}
auto __lambda1681__(auto main){
	return Main.compileWhile(main);
}
auto __lambda1682__(auto main){
	return Main.compileFor(main);
}
auto __lambda1683__(auto main){
	return Main.compileElse(main);
}
auto __lambda1684__(auto main){
	return Main.compilePostFix(main);
}
auto __lambda1685__(auto input){
	return compileStatement(state, input);
}
auto __lambda1686__(auto input){
	return compileStatement;
}
auto __lambda1687__(auto param){
	return Lists.of(
                Main.compileWhitespace,
                Main(param);
}
auto __lambda1688__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main.compileIf,
                Main(param);
}
auto __lambda1689__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main.compileWhile,
                Main(param);
}
auto __lambda1690__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main.compileFor,
                Main(param);
}
auto __lambda1691__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main.compileElse,
                Main(param);
}
auto __lambda1692__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main::compileElse,
                Main.compilePostFix,
                input -> compileStatement(state, input)
        )(param);
}
auto __lambda1693__(auto param){
	return Lists.of(
                Main.compileWhitespace,
                Main(param);
}
auto __lambda1694__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main.compileIf,
                Main(param);
}
auto __lambda1695__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main.compileWhile,
                Main(param);
}
auto __lambda1696__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main.compileFor,
                Main(param);
}
auto __lambda1697__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main.compileElse,
                Main(param);
}
auto __lambda1698__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main::compileElse,
                Main.compilePostFix,
                input(param);
}
auto __lambda1699__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda1700__(auto main){
	return Main.compileIf(main);
}
auto __lambda1701__(auto main){
	return Main.compileWhile(main);
}
auto __lambda1702__(auto main){
	return Main.compileFor(main);
}
auto __lambda1703__(auto main){
	return Main.compileElse(main);
}
auto __lambda1704__(auto main){
	return Main.compilePostFix(main);
}
auto __lambda1705__(auto input){
	return compileStatement(state, input);
}
auto __lambda1706__(auto input){
	return compileStatement;
}
auto __lambda1707__(auto param){
	return Lists.of(
                Main.compileWhitespace,
                Main(param);
}
auto __lambda1708__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main.compileIf,
                Main(param);
}
auto __lambda1709__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main.compileWhile,
                Main(param);
}
auto __lambda1710__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main.compileFor,
                Main(param);
}
auto __lambda1711__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main.compileElse,
                Main(param);
}
auto __lambda1712__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main::compileElse,
                Main.compilePostFix,
                input -> compileStatement(state, input)
        )(param);
}
auto __lambda1713__(auto param){
	return Lists.of(
                Main.compileWhitespace,
                Main(param);
}
auto __lambda1714__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main.compileIf,
                Main(param);
}
auto __lambda1715__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main.compileWhile,
                Main(param);
}
auto __lambda1716__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main.compileFor,
                Main(param);
}
auto __lambda1717__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main.compileElse,
                Main(param);
}
auto __lambda1718__(auto param){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main::compileElse,
                Main.compilePostFix,
                input(param);
}
/* private static */Result_String_CompileError compileStatementOrBlock(ParseState state, String value){
	return compileOr(value, Lists.of(__lambda1679__, __lambda1680__, __lambda1681__, __lambda1682__, __lambda1683__, __lambda1684__, __lambda1685__));
}
/* private static */Result_String_CompileError compilePostFix(String input){
	if (1) {
	}
	return createSuffixErr(input.strip(), "++;");
}
/* private static */Result_String_CompileError compileElse(String input){
	if (1) {
	}
	return createPrefixErr(input.strip(), "else ");
}
/* private static */Result_String_CompileError compileFor(String input){
	if (1) {
	}
	return createPrefixErr(input.strip(), "for ");
}
/* private static */Result_String_CompileError compileWhile(String input){
	String stripped = input.strip();
	if (1) {
	}
	return createPrefixErr(stripped, "while ");
}
/* private static */Result_String_CompileError compileIf(String input){
	String stripped = input.strip();
	if (1) {
	}
	return createPrefixErr(stripped, "if ");
}
auto __lambda1719__(auto withoutEnd){
	return compileReturn(state, withoutEnd);
}
auto __lambda1720__(auto withoutEnd){
	return compileReturn;
}
auto __lambda1721__(auto withoutEnd){
	return compileInitialization(state, withoutEnd);
}
auto __lambda1722__(auto withoutEnd){
	return compileInitialization;
}
auto __lambda1723__(auto withoutEnd){
	return compileAssignment(state, withoutEnd);
}
auto __lambda1724__(auto withoutEnd){
	return compileAssignment;
}
auto __lambda1725__(auto withoutEnd){
	return compileInvocationStatement(state, withoutEnd);
}
auto __lambda1726__(auto withoutEnd){
	return compileInvocationStatement;
}
auto __lambda1727__(auto withoutEnd){
	return compileReturn(state, withoutEnd);
}
auto __lambda1728__(auto withoutEnd){
	return compileReturn;
}
auto __lambda1729__(auto withoutEnd){
	return compileInitialization(state, withoutEnd);
}
auto __lambda1730__(auto withoutEnd){
	return compileInitialization;
}
auto __lambda1731__(auto withoutEnd){
	return compileAssignment(state, withoutEnd);
}
auto __lambda1732__(auto withoutEnd){
	return compileAssignment;
}
auto __lambda1733__(auto withoutEnd){
	return compileInvocationStatement(state, withoutEnd);
}
auto __lambda1734__(auto withoutEnd){
	return compileInvocationStatement;
}
/* private static */Result_String_CompileError compileStatement(ParseState state, String input){
	String stripped = input.strip();
	if (1) {
	}
	String slice = stripped.substring(0, stripped.length() - ";".length());
	return compileOr(slice, Lists.of(__lambda1719__, __lambda1721__, __lambda1723__, __lambda1725__));
}
/* private static */Result_String_CompileError compileReturn(ParseState state, String withoutEnd){
	if (1) {
	}
	return createPrefixErr(withoutEnd, "return ");
}
auto __lambda1735__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1736__(auto param){
	return compileInvocation(state, withoutEnd).mapValue(Main.generateStatement)(param);
}
auto __lambda1737__(auto main){
	return Main.generateStatement(main);
}
/* private static */Result_String_CompileError compileInvocationStatement(ParseState state, String withoutEnd){
	return compileInvocation(state, withoutEnd).mapValue(__lambda1735__);
}
auto __lambda1738__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1739__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1740__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1741__(auto tuple){
	return tuple;
}
auto __lambda1742__(auto tuple){
	return tuple.left;
}
auto __lambda1743__(auto tuple){
	return tuple;
}
auto __lambda1744__(auto tuple){
	return tuple.left;
}
auto __lambda1745__(auto tuple){
	return tuple;
}
auto __lambda1746__(auto ){
	return compileValue(value, state);
}
auto __lambda1747__(auto ){
	return compileValue;
}
auto __lambda1748__(auto ){
	return compileValue(value, state);
}
auto __lambda1749__(auto ){
	return compileValue;
}
auto __lambda1750__(auto ){
	return compileValue(value, state);
}
auto __lambda1751__(auto ){
	return compileValue;
}
auto __lambda1752__(auto ){
	return compileValue(value, state);
}
auto __lambda1753__(auto ){
	return compileValue;
}
auto __lambda1754__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1755__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1756__(auto tuple){
	return tuple;
}
auto __lambda1757__(auto tuple){
	return tuple.left;
}
auto __lambda1758__(auto tuple){
	return tuple;
}
auto __lambda1759__(auto tuple){
	return tuple.left;
}
auto __lambda1760__(auto tuple){
	return tuple;
}
auto __lambda1761__(auto ){
	return compileValue(value, state);
}
auto __lambda1762__(auto ){
	return compileValue;
}
auto __lambda1763__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1764__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1765__(auto tuple){
	return tuple;
}
auto __lambda1766__(auto tuple){
	return tuple.left;
}
auto __lambda1767__(auto tuple){
	return tuple;
}
auto __lambda1768__(auto tuple){
	return tuple.left;
}
auto __lambda1769__(auto tuple){
	return tuple;
}
auto __lambda1770__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1771__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1772__(auto tuple){
	return tuple;
}
auto __lambda1773__(auto tuple){
	return tuple.left;
}
auto __lambda1774__(auto tuple){
	return tuple;
}
auto __lambda1775__(auto tuple){
	return tuple.left;
}
auto __lambda1776__(auto tuple){
	return tuple;
}
auto __lambda1777__(auto ){
	return compileValue(value, state);
}
auto __lambda1778__(auto ){
	return compileValue;
}
auto __lambda1779__(auto ){
	return compileValue(value, state);
}
auto __lambda1780__(auto ){
	return compileValue;
}
auto __lambda1781__(auto ){
	return compileValue(value, state);
}
auto __lambda1782__(auto ){
	return compileValue;
}
auto __lambda1783__(auto ){
	return compileValue(value, state);
}
auto __lambda1784__(auto ){
	return compileValue;
}
auto __lambda1785__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1786__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1787__(auto tuple){
	return tuple;
}
auto __lambda1788__(auto tuple){
	return tuple.left;
}
auto __lambda1789__(auto tuple){
	return tuple;
}
auto __lambda1790__(auto tuple){
	return tuple.left;
}
auto __lambda1791__(auto tuple){
	return tuple;
}
auto __lambda1792__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1793__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1794__(auto tuple){
	return tuple;
}
auto __lambda1795__(auto tuple){
	return tuple.left;
}
auto __lambda1796__(auto tuple){
	return tuple;
}
auto __lambda1797__(auto tuple){
	return tuple.left;
}
auto __lambda1798__(auto tuple){
	return tuple;
}
auto __lambda1799__(auto ){
	return compileValue(value, state);
}
auto __lambda1800__(auto ){
	return compileValue;
}
auto __lambda1801__(auto ){
	return compileValue(value, state);
}
auto __lambda1802__(auto ){
	return compileValue;
}
auto __lambda1803__(auto ){
	return compileValue(value, state);
}
auto __lambda1804__(auto ){
	return compileValue;
}
auto __lambda1805__(auto ){
	return compileValue(value, state);
}
auto __lambda1806__(auto ){
	return compileValue;
}
auto __lambda1807__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1808__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1809__(auto tuple){
	return tuple;
}
auto __lambda1810__(auto tuple){
	return tuple.left;
}
auto __lambda1811__(auto tuple){
	return tuple;
}
auto __lambda1812__(auto tuple){
	return tuple.left;
}
auto __lambda1813__(auto tuple){
	return tuple;
}
auto __lambda1814__(auto ){
	return compileValue(value, state);
}
auto __lambda1815__(auto ){
	return compileValue;
}
auto __lambda1816__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1817__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1818__(auto tuple){
	return tuple;
}
auto __lambda1819__(auto tuple){
	return tuple.left;
}
auto __lambda1820__(auto tuple){
	return tuple;
}
auto __lambda1821__(auto tuple){
	return tuple.left;
}
auto __lambda1822__(auto tuple){
	return tuple;
}
auto __lambda1823__(auto param){
	return compileValue(destination, state).and(__lambda1799__).mapValue(__lambda1792__).mapValue(Main.generateStatement)(param);
}
auto __lambda1824__(auto ){
	return compileValue(value, state);
}
auto __lambda1825__(auto ){
	return compileValue;
}
auto __lambda1826__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1827__(auto param){
	return " = " + tuple.right).mapValue(Main.generateStatement)(param);
}
auto __lambda1828__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1829__(auto param){
	return tuple.right).mapValue(Main.generateStatement)(param);
}
auto __lambda1830__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1831__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1832__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1833__(auto tuple){
	return tuple;
}
auto __lambda1834__(auto tuple){
	return tuple.left;
}
auto __lambda1835__(auto tuple){
	return tuple;
}
auto __lambda1836__(auto tuple){
	return tuple.left;
}
auto __lambda1837__(auto tuple){
	return tuple;
}
auto __lambda1838__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1839__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1840__(auto tuple){
	return tuple;
}
auto __lambda1841__(auto tuple){
	return tuple.left;
}
auto __lambda1842__(auto tuple){
	return tuple;
}
auto __lambda1843__(auto tuple){
	return tuple.left;
}
auto __lambda1844__(auto tuple){
	return tuple;
}
auto __lambda1845__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1846__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1847__(auto tuple){
	return tuple;
}
auto __lambda1848__(auto tuple){
	return tuple.left;
}
auto __lambda1849__(auto tuple){
	return tuple;
}
auto __lambda1850__(auto tuple){
	return tuple.left;
}
auto __lambda1851__(auto tuple){
	return tuple;
}
auto __lambda1852__(auto ){
	return compileValue(value, state);
}
auto __lambda1853__(auto ){
	return compileValue;
}
auto __lambda1854__(auto ){
	return compileValue(value, state);
}
auto __lambda1855__(auto ){
	return compileValue;
}
auto __lambda1856__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1857__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1858__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1859__(auto tuple){
	return tuple;
}
auto __lambda1860__(auto tuple){
	return tuple.left;
}
auto __lambda1861__(auto tuple){
	return tuple;
}
auto __lambda1862__(auto tuple){
	return tuple.left;
}
auto __lambda1863__(auto tuple){
	return tuple;
}
auto __lambda1864__(auto ){
	return compileValue(value, state);
}
auto __lambda1865__(auto ){
	return compileValue;
}
auto __lambda1866__(auto ){
	return compileValue(value, state);
}
auto __lambda1867__(auto ){
	return compileValue;
}
auto __lambda1868__(auto ){
	return compileValue(value, state);
}
auto __lambda1869__(auto ){
	return compileValue;
}
auto __lambda1870__(auto ){
	return compileValue(value, state);
}
auto __lambda1871__(auto ){
	return compileValue;
}
auto __lambda1872__(auto ){
	return compileValue(value, state);
}
auto __lambda1873__(auto ){
	return compileValue;
}
/* private static */Result_String_CompileError compileAssignment(ParseState state, String withoutEnd){
	int valueSeparator = withoutEnd.indexOf("=");
	if (1) {
	}
	String destination = withoutEnd.substring(0, valueSeparator).strip();
	String value = withoutEnd.substring(valueSeparator + "=".length()).strip();
	return compileValue(destination, state).and(__lambda1746__).mapValue(__lambda1739__).mapValue(__lambda1738__);
}
auto __lambda1874__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1875__(auto outputValue){
	return generateStatement;
}
auto __lambda1876__(auto outputDefinition){
	return compileValue(inputValue, state).mapValue(__lambda1874__);
}
auto __lambda1877__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1878__(auto outputValue){
	return generateStatement;
}
auto __lambda1879__(auto outputDefinition){
	return compileValue(inputValue, state).mapValue;
}
auto __lambda1880__(auto outputDefinition){
	return compileValue(inputValue, state);
}
auto __lambda1881__(auto outputDefinition){
	return compileValue;
}
auto __lambda1882__(auto outputDefinition){
	return compileValue(inputValue, state);
}
auto __lambda1883__(auto outputDefinition){
	return compileValue;
}
auto __lambda1884__(auto outputDefinition){
	return compileValue(inputValue, state).mapValue(
                        outputValue -> generateStatement(outputDefinition;
}
auto __lambda1885__(auto outputDefinition){
	return compileValue(inputValue, state);
}
auto __lambda1886__(auto outputDefinition){
	return compileValue;
}
auto __lambda1887__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1888__(auto outputValue){
	return generateStatement;
}
auto __lambda1889__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1890__(auto outputValue){
	return generateStatement;
}
auto __lambda1891__(auto outputDefinition){
	return compileValue(inputValue, state).mapValue(__lambda1889__);
}
auto __lambda1892__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1893__(auto outputValue){
	return generateStatement;
}
auto __lambda1894__(auto outputDefinition){
	return compileValue(inputValue, state).mapValue;
}
auto __lambda1895__(auto outputDefinition){
	return compileValue(inputValue, state);
}
auto __lambda1896__(auto outputDefinition){
	return compileValue;
}
auto __lambda1897__(auto outputDefinition){
	return compileValue(inputValue, state);
}
auto __lambda1898__(auto outputDefinition){
	return compileValue;
}
auto __lambda1899__(auto outputDefinition){
	return compileValue(inputValue, state).mapValue(
                        outputValue -> generateStatement(outputDefinition;
}
auto __lambda1900__(auto outputDefinition){
	return compileValue(inputValue, state);
}
auto __lambda1901__(auto outputDefinition){
	return compileValue;
}
auto __lambda1902__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1903__(auto outputValue){
	return generateStatement;
}
/* private static */Result_String_CompileError compileInitialization(ParseState state, String withoutEnd){
	int separator = withoutEnd.indexOf("=");
	if (1) {
	}
	String inputDefinition = withoutEnd.substring(0, separator);
	String inputValue = withoutEnd.substring(separator + "=".length());
	return compileDefinition(inputDefinition, state).flatMapValue(__lambda1876__);
}
/* private static */String generateStatement(String value){
	return "\n\t" + value + ";";
}
auto __lambda1904__(auto main){
	return Main.compileString(main);
}
auto __lambda1905__(auto main){
	return Main.compileChar(main);
}
auto __lambda1906__(auto main){
	return Main.compileSymbol(main);
}
auto __lambda1907__(auto main){
	return Main.compileNumber(main);
}
auto __lambda1908__(auto input){
	return compileNot(state, input);
}
auto __lambda1909__(auto input){
	return compileNot;
}
auto __lambda1910__(auto input){
	return compileConstruction(state, input);
}
auto __lambda1911__(auto input){
	return compileConstruction;
}
auto __lambda1912__(auto input){
	return compileLambda(state, input);
}
auto __lambda1913__(auto input){
	return compileLambda;
}
auto __lambda1914__(auto input){
	return compileInvocation(state, input);
}
auto __lambda1915__(auto input){
	return compileInvocation;
}
auto __lambda1916__(auto input){
	return compileTernary(state, input);
}
auto __lambda1917__(auto input){
	return compileTernary;
}
auto __lambda1918__(auto input){
	return compileDataAccess(input, state);
}
auto __lambda1919__(auto input){
	return compileDataAccess;
}
auto __lambda1920__(auto input){
	return compileMethodAccess(state, input);
}
auto __lambda1921__(auto input){
	return compileMethodAccess;
}
auto __lambda1922__(auto input){
	return compileOperator(state, input, "+");
}
auto __lambda1923__(auto input){
	return compileOperator;
}
auto __lambda1924__(auto input){
	return compileOperator(state, input, "-");
}
auto __lambda1925__(auto input){
	return compileOperator;
}
auto __lambda1926__(auto input){
	return compileOperator(state, input, "==");
}
auto __lambda1927__(auto input){
	return compileOperator;
}
auto __lambda1928__(auto input){
	return compileOperator(state, input, ">=");
}
auto __lambda1929__(auto input){
	return compileOperator;
}
auto __lambda1930__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1931__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1932__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1933__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "+"),
                input -> compileOperator(state, input, "-"),
                input -> compileOperator(state, input, "=="),
                input -> compileOperator(state, input, ">=")
        )(param);
}
auto __lambda1934__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1935__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1936__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1937__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "(param);
}
auto __lambda1938__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1939__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1940__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1941__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda1942__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1943__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1944__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1945__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda1946__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1947__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1948__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1949__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "+"),
                input -> compileOperator(state, input, "-"),
                input -> compileOperator(state, input, "(param);
}
auto __lambda1950__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1951__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1952__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1953__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "(param);
}
auto __lambda1954__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1955__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1956__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1957__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda1958__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1959__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1960__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1961__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda1962__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1963__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1964__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1965__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "+"),
                input -> compileOperator(state, input, "-"),
                input -> compileOperator(state, input, "=="),
                input -> compileOperator(state, input, "(param);
}
auto __lambda1966__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1967__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1968__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1969__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "(param);
}
auto __lambda1970__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1971__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1972__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1973__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda1974__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1975__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1976__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1977__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda1978__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1979__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1980__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1981__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "+"),
                input -> compileOperator(state, input, "-"),
                input -> compileOperator(state, input, "(param);
}
auto __lambda1982__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1983__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1984__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1985__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "(param);
}
auto __lambda1986__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1987__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1988__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1989__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda1990__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda1991__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda1992__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda1993__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda1994__(auto main){
	return Main.compileString(main);
}
auto __lambda1995__(auto main){
	return Main.compileChar(main);
}
auto __lambda1996__(auto main){
	return Main.compileSymbol(main);
}
auto __lambda1997__(auto main){
	return Main.compileNumber(main);
}
auto __lambda1998__(auto input){
	return compileNot(state, input);
}
auto __lambda1999__(auto input){
	return compileNot;
}
auto __lambda2000__(auto input){
	return compileConstruction(state, input);
}
auto __lambda2001__(auto input){
	return compileConstruction;
}
auto __lambda2002__(auto input){
	return compileLambda(state, input);
}
auto __lambda2003__(auto input){
	return compileLambda;
}
auto __lambda2004__(auto input){
	return compileInvocation(state, input);
}
auto __lambda2005__(auto input){
	return compileInvocation;
}
auto __lambda2006__(auto input){
	return compileTernary(state, input);
}
auto __lambda2007__(auto input){
	return compileTernary;
}
auto __lambda2008__(auto input){
	return compileDataAccess(input, state);
}
auto __lambda2009__(auto input){
	return compileDataAccess;
}
auto __lambda2010__(auto input){
	return compileMethodAccess(state, input);
}
auto __lambda2011__(auto input){
	return compileMethodAccess;
}
auto __lambda2012__(auto input){
	return compileOperator(state, input, "+");
}
auto __lambda2013__(auto input){
	return compileOperator;
}
auto __lambda2014__(auto input){
	return compileOperator(state, input, "-");
}
auto __lambda2015__(auto input){
	return compileOperator;
}
auto __lambda2016__(auto input){
	return compileOperator(state, input, "==");
}
auto __lambda2017__(auto input){
	return compileOperator;
}
auto __lambda2018__(auto input){
	return compileOperator(state, input, ">=");
}
auto __lambda2019__(auto input){
	return compileOperator;
}
auto __lambda2020__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2021__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2022__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2023__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "+"),
                input -> compileOperator(state, input, "-"),
                input -> compileOperator(state, input, "=="),
                input -> compileOperator(state, input, ">=")
        )(param);
}
auto __lambda2024__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2025__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2026__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2027__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "(param);
}
auto __lambda2028__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2029__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2030__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2031__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda2032__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2033__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2034__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2035__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda2036__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2037__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2038__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2039__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "+"),
                input -> compileOperator(state, input, "-"),
                input -> compileOperator(state, input, "(param);
}
auto __lambda2040__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2041__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2042__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2043__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "(param);
}
auto __lambda2044__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2045__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2046__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2047__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda2048__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2049__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2050__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2051__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda2052__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2053__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2054__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2055__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "+"),
                input -> compileOperator(state, input, "-"),
                input -> compileOperator(state, input, "=="),
                input -> compileOperator(state, input, "(param);
}
auto __lambda2056__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2057__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2058__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2059__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "(param);
}
auto __lambda2060__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2061__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2062__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2063__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda2064__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2065__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2066__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2067__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda2068__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2069__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2070__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2071__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "+"),
                input -> compileOperator(state, input, "-"),
                input -> compileOperator(state, input, "(param);
}
auto __lambda2072__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2073__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2074__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2075__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(state, input),
                input -> compileConstruction(state, input),
                input -> compileLambda(state, input),
                input -> compileInvocation(state, input),
                input -> compileTernary(state, input),
                input -> compileDataAccess(input, state),
                input -> compileMethodAccess(state, input),
                input -> compileOperator(state, input, "(param);
}
auto __lambda2076__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2077__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2078__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2079__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
auto __lambda2080__(auto param){
	return Lists.of(
                Main.compileString,
                Main(param);
}
auto __lambda2081__(auto param){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(param);
}
auto __lambda2082__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(param);
}
auto __lambda2083__(auto param){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(param);
}
/* private static */Result_String_CompileError compileValue(String wrapped, ParseState state){
	return compileOr(wrapped, Lists.of(__lambda1904__, __lambda1905__, __lambda1906__, __lambda1907__, __lambda1908__, __lambda1910__, __lambda1912__, __lambda1914__, __lambda1916__, __lambda1918__, __lambda1920__, __lambda1922__, __lambda1924__, __lambda1926__, __lambda1928__));
}
/* private static */Result_String_CompileError compileNumber(String input){
	String stripped = input.strip();
	return isNumber(stripped) ? Ok(stripped) : Err(CompileError("Not a number", input));
}
/* private static */Result_String_CompileError compileSymbol(String input){
	String stripped = input.strip();
	return isSymbol(stripped) ? Ok(stripped) : Err(CompileError("Not a symbol", input));
}
auto __lambda2084__(auto param){
	return "."(param);
}
auto __lambda2085__(auto param){
	return stripped.lastIndexOf(".")(param);
}
auto __lambda2086__(auto param){
	return "."(param);
}
auto __lambda2087__(auto param){
	return "."(param);
}
auto __lambda2088__(auto param){
	return methodSeparator + "."(param);
}
auto __lambda2089__(auto param){
	return "."(param);
}
auto __lambda2090__(auto param){
	return methodSeparator + ".".length(param);
}
auto __lambda2091__(auto param){
	return "."(param);
}
auto __lambda2092__(auto param){
	return ".".length(param);
}
auto __lambda2093__(auto param){
	return methodSeparator + "."(param);
}
auto __lambda2094__(auto param){
	return "."(param);
}
auto __lambda2095__(auto param){
	return methodSeparator + ".".length()(param);
}
auto __lambda2096__(auto param){
	return "."(param);
}
auto __lambda2097__(auto param){
	return ".".length(param);
}
auto __lambda2098__(auto param){
	return "."(param);
}
auto __lambda2099__(auto param){
	return ".".length()(param);
}
auto __lambda2100__(auto param){
	return stripped.substring(methodSeparator + "."(param);
}
auto __lambda2101__(auto param){
	return "."(param);
}
auto __lambda2102__(auto param){
	return stripped.substring(methodSeparator + ".".length())(param);
}
auto __lambda2103__(auto param){
	return "."(param);
}
auto __lambda2104__(auto param){
	return ".".length())(param);
}
auto __lambda2105__(auto param){
	return methodSeparator + "."(param);
}
auto __lambda2106__(auto param){
	return "."(param);
}
auto __lambda2107__(auto param){
	return methodSeparator + ".".length(param);
}
auto __lambda2108__(auto param){
	return "."(param);
}
auto __lambda2109__(auto param){
	return ".".length(param);
}
auto __lambda2110__(auto param){
	return methodSeparator + "."(param);
}
auto __lambda2111__(auto param){
	return "."(param);
}
auto __lambda2112__(auto param){
	return methodSeparator + ".".length()(param);
}
auto __lambda2113__(auto param){
	return "."(param);
}
auto __lambda2114__(auto param){
	return ".".length(param);
}
auto __lambda2115__(auto param){
	return "."(param);
}
auto __lambda2116__(auto param){
	return ".".length()(param);
}
auto __lambda2117__(auto newObject){
	return 
	String caller = newObject + "." + property;
	String lower = newObject.toLowerCase();
	String paramName = isSymbol(lower) ? lower : "param";
	return generateLambda(Lists.<String>empty().add(paramName), generateInvocation(caller, paramName));;
}
auto __lambda2118__(auto newObject){
	return 
	String caller = newObject + "." + property;
	String lower = newObject.toLowerCase();
	String paramName = isSymbol(lower) ? lower : "param";
	return generateLambda(Lists.<String>empty().add(paramName), generateInvocation(caller, paramName));;
}
/* private static */Result_String_CompileError compileMethodAccess(ParseState state, String input){
	String stripped = input.strip();
	int methodSeparator = stripped.lastIndexOf("::");
	if (1) {
	}
	String object = stripped.substring(0, methodSeparator);
	String property = stripped.substring(__lambda2088__.length());
	return compileValue(object, state).flatMapValue(__lambda2117__);
}
auto __lambda2119__(auto newObject){
	return newObject + "." + property;
}
auto __lambda2120__(auto newObject){
	return newObject + ";
}
auto __lambda2121__(auto newObject){
	return newObject;
}
auto __lambda2122__(auto newObject){
	return newObject;
}
auto __lambda2123__(auto newObject){
	return newObject + "." + property;
}
auto __lambda2124__(auto newObject){
	return newObject + ";
}
auto __lambda2125__(auto newObject){
	return newObject;
}
auto __lambda2126__(auto newObject){
	return newObject;
}
/* private static */Result_String_CompileError compileDataAccess(String input, ParseState state){
	String stripped = input.strip();
	int dataSeparator = stripped.lastIndexOf(".");
	if (1) {
	}
	String object = stripped.substring(0, dataSeparator);
	String property = stripped.substring(dataSeparator + ".".length());
	return compileValue(object, state).mapValue(__lambda2119__);
}
auto __lambda2127__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple.right;
}
auto __lambda2128__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda2129__(auto tuple){
	return tuple.left;
}
auto __lambda2130__(auto tuple){
	return tuple;
}
auto __lambda2131__(auto tuple){
	return tuple.left.left;
}
auto __lambda2132__(auto tuple){
	return tuple.left;
}
auto __lambda2133__(auto tuple){
	return tuple;
}
auto __lambda2134__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple;
}
auto __lambda2135__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda2136__(auto tuple){
	return tuple.left;
}
auto __lambda2137__(auto tuple){
	return tuple;
}
auto __lambda2138__(auto tuple){
	return tuple.left.left;
}
auto __lambda2139__(auto tuple){
	return tuple.left;
}
auto __lambda2140__(auto tuple){
	return tuple;
}
auto __lambda2141__(auto tuple){
	return tuple.left.left + " ? " + tuple.left;
}
auto __lambda2142__(auto tuple){
	return tuple.left.left + " ? " + tuple;
}
auto __lambda2143__(auto tuple){
	return tuple.left;
}
auto __lambda2144__(auto tuple){
	return tuple;
}
auto __lambda2145__(auto tuple){
	return tuple.left.left;
}
auto __lambda2146__(auto tuple){
	return tuple.left;
}
auto __lambda2147__(auto tuple){
	return tuple;
}
auto __lambda2148__(auto tuple){
	return tuple.left.left;
}
auto __lambda2149__(auto tuple){
	return tuple.left;
}
auto __lambda2150__(auto tuple){
	return tuple;
}
auto __lambda2151__(auto tuple){
	return tuple.left.left;
}
auto __lambda2152__(auto tuple){
	return tuple.left;
}
auto __lambda2153__(auto tuple){
	return tuple;
}
auto __lambda2154__(auto tuple){
	return tuple.left.left;
}
auto __lambda2155__(auto tuple){
	return tuple.left;
}
auto __lambda2156__(auto tuple){
	return tuple;
}
auto __lambda2157__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2158__(auto ){
	return compileValue;
}
auto __lambda2159__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2160__(auto ){
	return compileValue;
}
auto __lambda2161__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2162__(auto ){
	return compileValue;
}
auto __lambda2163__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2164__(auto ){
	return compileValue;
}
auto __lambda2165__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2166__(auto ){
	return compileValue;
}
auto __lambda2167__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2168__(auto ){
	return compileValue;
}
auto __lambda2169__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2170__(auto ){
	return compileValue;
}
auto __lambda2171__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2172__(auto ){
	return compileValue;
}
auto __lambda2173__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2174__(auto ){
	return compileValue;
}
auto __lambda2175__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2176__(auto ){
	return compileValue;
}
auto __lambda2177__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2178__(auto ){
	return compileValue;
}
auto __lambda2179__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2180__(auto ){
	return compileValue;
}
auto __lambda2181__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2182__(auto ){
	return compileValue;
}
auto __lambda2183__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2184__(auto ){
	return compileValue;
}
auto __lambda2185__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2186__(auto ){
	return compileValue;
}
auto __lambda2187__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2188__(auto ){
	return compileValue;
}
auto __lambda2189__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2190__(auto ){
	return compileValue;
}
auto __lambda2191__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2192__(auto ){
	return compileValue;
}
auto __lambda2193__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2194__(auto ){
	return compileValue;
}
auto __lambda2195__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2196__(auto ){
	return compileValue;
}
auto __lambda2197__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2198__(auto ){
	return compileValue;
}
auto __lambda2199__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2200__(auto ){
	return compileValue;
}
auto __lambda2201__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2202__(auto ){
	return compileValue;
}
auto __lambda2203__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2204__(auto ){
	return compileValue;
}
auto __lambda2205__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2206__(auto ){
	return compileValue;
}
auto __lambda2207__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2208__(auto ){
	return compileValue;
}
auto __lambda2209__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2210__(auto ){
	return compileValue;
}
auto __lambda2211__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2212__(auto ){
	return compileValue;
}
auto __lambda2213__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2214__(auto ){
	return compileValue;
}
auto __lambda2215__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2216__(auto ){
	return compileValue;
}
auto __lambda2217__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2218__(auto ){
	return compileValue;
}
auto __lambda2219__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2220__(auto ){
	return compileValue;
}
auto __lambda2221__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2222__(auto ){
	return compileValue;
}
auto __lambda2223__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2224__(auto ){
	return compileValue;
}
auto __lambda2225__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2226__(auto ){
	return compileValue;
}
auto __lambda2227__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2228__(auto ){
	return compileValue;
}
auto __lambda2229__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2230__(auto ){
	return compileValue;
}
auto __lambda2231__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2232__(auto ){
	return compileValue;
}
auto __lambda2233__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2234__(auto ){
	return compileValue;
}
auto __lambda2235__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2236__(auto ){
	return compileValue;
}
auto __lambda2237__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2238__(auto ){
	return compileValue;
}
auto __lambda2239__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2240__(auto ){
	return compileValue;
}
auto __lambda2241__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2242__(auto ){
	return compileValue;
}
auto __lambda2243__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2244__(auto ){
	return compileValue;
}
auto __lambda2245__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2246__(auto ){
	return compileValue;
}
auto __lambda2247__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2248__(auto ){
	return compileValue;
}
auto __lambda2249__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2250__(auto ){
	return compileValue;
}
auto __lambda2251__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2252__(auto ){
	return compileValue;
}
auto __lambda2253__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2254__(auto ){
	return compileValue;
}
auto __lambda2255__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2256__(auto ){
	return compileValue;
}
auto __lambda2257__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2258__(auto ){
	return compileValue;
}
auto __lambda2259__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2260__(auto ){
	return compileValue;
}
auto __lambda2261__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2262__(auto ){
	return compileValue;
}
auto __lambda2263__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2264__(auto ){
	return compileValue;
}
auto __lambda2265__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2266__(auto ){
	return compileValue;
}
auto __lambda2267__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2268__(auto ){
	return compileValue;
}
auto __lambda2269__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2270__(auto ){
	return compileValue;
}
auto __lambda2271__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2272__(auto ){
	return compileValue;
}
auto __lambda2273__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2274__(auto ){
	return compileValue;
}
auto __lambda2275__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2276__(auto ){
	return compileValue;
}
auto __lambda2277__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2278__(auto ){
	return compileValue;
}
auto __lambda2279__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2280__(auto ){
	return compileValue;
}
auto __lambda2281__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2282__(auto ){
	return compileValue;
}
auto __lambda2283__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2284__(auto ){
	return compileValue;
}
auto __lambda2285__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2286__(auto ){
	return compileValue;
}
auto __lambda2287__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2288__(auto ){
	return compileValue;
}
auto __lambda2289__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2290__(auto ){
	return compileValue;
}
auto __lambda2291__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2292__(auto ){
	return compileValue;
}
auto __lambda2293__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2294__(auto ){
	return compileValue;
}
auto __lambda2295__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2296__(auto ){
	return compileValue;
}
auto __lambda2297__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2298__(auto ){
	return compileValue;
}
auto __lambda2299__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2300__(auto ){
	return compileValue;
}
auto __lambda2301__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2302__(auto ){
	return compileValue;
}
auto __lambda2303__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2304__(auto ){
	return compileValue;
}
auto __lambda2305__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2306__(auto ){
	return compileValue;
}
auto __lambda2307__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2308__(auto ){
	return compileValue;
}
auto __lambda2309__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2310__(auto ){
	return compileValue;
}
auto __lambda2311__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2312__(auto ){
	return compileValue;
}
auto __lambda2313__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2314__(auto ){
	return compileValue;
}
auto __lambda2315__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2316__(auto ){
	return compileValue;
}
auto __lambda2317__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple.right;
}
auto __lambda2318__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda2319__(auto tuple){
	return tuple.left;
}
auto __lambda2320__(auto tuple){
	return tuple;
}
auto __lambda2321__(auto tuple){
	return tuple.left.left;
}
auto __lambda2322__(auto tuple){
	return tuple.left;
}
auto __lambda2323__(auto tuple){
	return tuple;
}
auto __lambda2324__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple;
}
auto __lambda2325__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda2326__(auto tuple){
	return tuple.left;
}
auto __lambda2327__(auto tuple){
	return tuple;
}
auto __lambda2328__(auto tuple){
	return tuple.left.left;
}
auto __lambda2329__(auto tuple){
	return tuple.left;
}
auto __lambda2330__(auto tuple){
	return tuple;
}
auto __lambda2331__(auto tuple){
	return tuple.left.left + " ? " + tuple.left;
}
auto __lambda2332__(auto tuple){
	return tuple.left.left + " ? " + tuple;
}
auto __lambda2333__(auto tuple){
	return tuple.left;
}
auto __lambda2334__(auto tuple){
	return tuple;
}
auto __lambda2335__(auto tuple){
	return tuple.left.left;
}
auto __lambda2336__(auto tuple){
	return tuple.left;
}
auto __lambda2337__(auto tuple){
	return tuple;
}
auto __lambda2338__(auto tuple){
	return tuple.left.left;
}
auto __lambda2339__(auto tuple){
	return tuple.left;
}
auto __lambda2340__(auto tuple){
	return tuple;
}
auto __lambda2341__(auto tuple){
	return tuple.left.left;
}
auto __lambda2342__(auto tuple){
	return tuple.left;
}
auto __lambda2343__(auto tuple){
	return tuple;
}
auto __lambda2344__(auto tuple){
	return tuple.left.left;
}
auto __lambda2345__(auto tuple){
	return tuple.left;
}
auto __lambda2346__(auto tuple){
	return tuple;
}
auto __lambda2347__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2348__(auto ){
	return compileValue;
}
auto __lambda2349__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2350__(auto ){
	return compileValue;
}
auto __lambda2351__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2352__(auto ){
	return compileValue;
}
auto __lambda2353__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2354__(auto ){
	return compileValue;
}
auto __lambda2355__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2356__(auto ){
	return compileValue;
}
auto __lambda2357__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2358__(auto ){
	return compileValue;
}
auto __lambda2359__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2360__(auto ){
	return compileValue;
}
auto __lambda2361__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2362__(auto ){
	return compileValue;
}
auto __lambda2363__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2364__(auto ){
	return compileValue;
}
auto __lambda2365__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2366__(auto ){
	return compileValue;
}
auto __lambda2367__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple.right;
}
auto __lambda2368__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda2369__(auto tuple){
	return tuple.left;
}
auto __lambda2370__(auto tuple){
	return tuple;
}
auto __lambda2371__(auto tuple){
	return tuple.left.left;
}
auto __lambda2372__(auto tuple){
	return tuple.left;
}
auto __lambda2373__(auto tuple){
	return tuple;
}
auto __lambda2374__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple;
}
auto __lambda2375__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda2376__(auto tuple){
	return tuple.left;
}
auto __lambda2377__(auto tuple){
	return tuple;
}
auto __lambda2378__(auto tuple){
	return tuple.left.left;
}
auto __lambda2379__(auto tuple){
	return tuple.left;
}
auto __lambda2380__(auto tuple){
	return tuple;
}
auto __lambda2381__(auto tuple){
	return tuple.left.left + " ? " + tuple.left;
}
auto __lambda2382__(auto tuple){
	return tuple.left.left + " ? " + tuple;
}
auto __lambda2383__(auto tuple){
	return tuple.left;
}
auto __lambda2384__(auto tuple){
	return tuple;
}
auto __lambda2385__(auto tuple){
	return tuple.left.left;
}
auto __lambda2386__(auto tuple){
	return tuple.left;
}
auto __lambda2387__(auto tuple){
	return tuple;
}
auto __lambda2388__(auto tuple){
	return tuple.left.left;
}
auto __lambda2389__(auto tuple){
	return tuple.left;
}
auto __lambda2390__(auto tuple){
	return tuple;
}
auto __lambda2391__(auto tuple){
	return tuple.left.left;
}
auto __lambda2392__(auto tuple){
	return tuple.left;
}
auto __lambda2393__(auto tuple){
	return tuple;
}
auto __lambda2394__(auto tuple){
	return tuple.left.left;
}
auto __lambda2395__(auto tuple){
	return tuple.left;
}
auto __lambda2396__(auto tuple){
	return tuple;
}
auto __lambda2397__(auto ){
	return compileValue(ifFalse, state);
}
auto __lambda2398__(auto ){
	return compileValue;
}
auto __lambda2399__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2400__(auto ){
	return compileValue;
}
auto __lambda2401__(auto ){
	return compileValue(ifTrue, state);
}
auto __lambda2402__(auto ){
	return compileValue;
}
/* private static */Result_String_CompileError compileTernary(ParseState state, String input){
	String stripped = input.strip();
	int ternaryIndex = stripped.indexOf("?");
	if (1) {
	}
	String condition = stripped.substring(0, ternaryIndex).strip();
	String cases = stripped.substring(ternaryIndex + "?".length());
	int caseIndex = cases.indexOf(":");
	if (1) {
	}
	String ifTrue = cases.substring(0, caseIndex).strip();
	String ifFalse = cases.substring(caseIndex + ":".length()).strip();
	return compileValue(condition, state).and(__lambda2159__).and(__lambda2157__).mapValue(__lambda2127__);
}
auto __lambda2403__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda2404__(auto type){
	return type;
}
auto __lambda2405__(auto arguments){
	return compileType(state, caller).mapValue(__lambda2403__);
}
auto __lambda2406__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda2407__(auto type){
	return type;
}
auto __lambda2408__(auto arguments){
	return compileType(state, caller).mapValue;
}
auto __lambda2409__(auto arguments){
	return compileType(state, caller);
}
auto __lambda2410__(auto arguments){
	return compileType;
}
auto __lambda2411__(auto arguments){
	return compileType(state, caller);
}
auto __lambda2412__(auto arguments){
	return compileType;
}
auto __lambda2413__(auto arguments){
	return compileType(state, caller).mapValue(type -> type;
}
auto __lambda2414__(auto arguments){
	return compileType(state, caller);
}
auto __lambda2415__(auto arguments){
	return compileType;
}
auto __lambda2416__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda2417__(auto type){
	return type;
}
auto __lambda2418__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda2419__(auto type){
	return type;
}
auto __lambda2420__(auto arguments){
	return compileType(state, caller).mapValue(__lambda2418__);
}
auto __lambda2421__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda2422__(auto type){
	return type;
}
auto __lambda2423__(auto arguments){
	return compileType(state, caller).mapValue;
}
auto __lambda2424__(auto arguments){
	return compileType(state, caller);
}
auto __lambda2425__(auto arguments){
	return compileType;
}
auto __lambda2426__(auto arguments){
	return compileType(state, caller);
}
auto __lambda2427__(auto arguments){
	return compileType;
}
auto __lambda2428__(auto arguments){
	return compileType(state, caller).mapValue(type -> type;
}
auto __lambda2429__(auto arguments){
	return compileType(state, caller);
}
auto __lambda2430__(auto arguments){
	return compileType;
}
auto __lambda2431__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda2432__(auto type){
	return type;
}
/* private static */Result_String_CompileError compileConstruction(ParseState state, String input){
	String stripped = input.strip();
	if (1) {
	}
	String withoutNew = stripped.substring("new ".length());
	if (1) {
	}
	String slice = withoutNew.substring(0, withoutNew.length() - ")".length());
	int paramStart = slice.indexOf("(");
	String rawCaller = slice.substring(0, paramStart).strip();
	String caller = rawCaller.endsWith("<>") ? rawCaller.substring(0, rawCaller.length() - "<>".length()) : rawCaller;
	String inputArguments = slice.substring(paramStart + "(".length());
	return compileAllValues(state, inputArguments).flatMapValue(__lambda2405__);
}
/* private static */Result_String_CompileError createPrefixErr(String input, String prefix){
	return Err(CompileError("Prefix '" + prefix + "' not present", input));
}
auto __lambda2433__(auto result){
	return "!" + result;
}
auto __lambda2434__(auto result){
	return "!";
}
auto __lambda2435__(auto result){
	return "!" + result;
}
auto __lambda2436__(auto result){
	return "!";
}
/* private static */Result_String_CompileError compileNot(ParseState state, String input){
	String stripped = input.strip();
	if (1) {
	}
	return compileValue(stripped.substring(1), state).mapValue(__lambda2433__);
}
/* private static */Result_String_CompileError compileChar(String input){
	if (1) {
	}
	return Err(CompileError("Not a character", input));
}
/* private static */Result_String_CompileError compileString(String input){
	return input.strip().startsWith("\"") && input.strip().endsWith("\"")
                ? new Ok<>(input.strip())
                : new Err<>(CompileError("Not a string", input));
}
auto __lambda2437__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda2438__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda2439__(auto outputValue){
	return generateLambda;
}
auto __lambda2440__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda2441__(auto outputValue){
	return generateLambda;
}
auto __lambda2442__(auto paramNames){
	return 
	String inputValue = input.substring(arrowIndex + "->".length()).strip();
	return compileLambdaBody(parseState, inputValue).flatMapValue(__lambda2438__);;
}
auto __lambda2443__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda2444__(auto outputValue){
	return generateLambda;
}
auto __lambda2445__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda2446__(auto outputValue){
	return generateLambda;
}
auto __lambda2447__(auto paramNames){
	return 
	String inputValue = input.substring(arrowIndex + "->".length()).strip();
	return compileLambdaBody(parseState, inputValue).flatMapValue(__lambda2443__);;
}
auto __lambda2448__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda2449__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda2450__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda2451__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda2452__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda2453__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda2454__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda2455__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda2456__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda2457__(auto outputValue){
	return generateLambda;
}
auto __lambda2458__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda2459__(auto outputValue){
	return generateLambda;
}
auto __lambda2460__(auto paramNames){
	return 
	String inputValue = input.substring(arrowIndex + "->".length()).strip();
	return compileLambdaBody(parseState, inputValue).flatMapValue(__lambda2456__);;
}
/* private static */Result_String_CompileError compileLambda(ParseState parseState, String input){
	int arrowIndex = input.indexOf("->");
	if (1) {
	}
	String beforeArrow = input.substring(0, arrowIndex).strip();
	return findLambdaParams(beforeArrow).map(__lambda2442__).orElseGet(__lambda2437__);
}
/* private static */Err_String_CompileError createInfixRule(String input, String infix){
	return Err(CompileError("Infix '" + infix + "' not present", input));
}
/* private static */Option_List__String findLambdaParams(String beforeArrow){
	if (1) {
	}
	if (1) {
	}
	return None();
}
auto __lambda2461__(auto string){
	return String.strip(string);
}
auto __lambda2462__(auto param){
	return divideByChar(params, ',').stream().map(String.strip)(param);
}
auto __lambda2463__(auto param){
	return divideByChar(params, ',').stream().map(String.strip)
                .collect(param);
}
auto __lambda2464__(auto string){
	return String.strip(string);
}
auto __lambda2465__(auto param){
	return divideByChar(params, ',').stream().map(String.strip)(param);
}
auto __lambda2466__(auto param){
	return divideByChar(params, ',').stream().map(String.strip)
                .collect(new ListCollector<>())(param);
}
auto __lambda2467__(auto string){
	return String.strip(string);
}
/* private static */List__String splitParams(String params){
	return divideByChar(params, ',').stream().map(__lambda2461__).collect(ListCollector());
}
/* private static */Result_String_CompileError compileLambdaBody(ParseState state, String inputValue){
	if (1) {
	}
	else {}
}
auto __lambda2468__(auto tuple){
	return tuple.left + " " + operator + " " + tuple.right;
}
auto __lambda2469__(auto tuple){
	return tuple.left + " " + operator + " " + tuple;
}
auto __lambda2470__(auto tuple){
	return tuple;
}
auto __lambda2471__(auto tuple){
	return tuple.left;
}
auto __lambda2472__(auto tuple){
	return tuple;
}
auto __lambda2473__(auto tuple){
	return tuple.left;
}
auto __lambda2474__(auto tuple){
	return tuple;
}
auto __lambda2475__(auto ){
	return compileValue(right, state);
}
auto __lambda2476__(auto ){
	return compileValue;
}
auto __lambda2477__(auto ){
	return compileValue(right, state);
}
auto __lambda2478__(auto ){
	return compileValue;
}
auto __lambda2479__(auto ){
	return compileValue(right, state);
}
auto __lambda2480__(auto ){
	return compileValue;
}
auto __lambda2481__(auto ){
	return compileValue(right, state);
}
auto __lambda2482__(auto ){
	return compileValue;
}
auto __lambda2483__(auto tuple){
	return tuple.left + " " + operator + " " + tuple.right;
}
auto __lambda2484__(auto tuple){
	return tuple.left + " " + operator + " " + tuple;
}
auto __lambda2485__(auto tuple){
	return tuple;
}
auto __lambda2486__(auto tuple){
	return tuple.left;
}
auto __lambda2487__(auto tuple){
	return tuple;
}
auto __lambda2488__(auto tuple){
	return tuple.left;
}
auto __lambda2489__(auto tuple){
	return tuple;
}
auto __lambda2490__(auto tuple){
	return tuple.left + " " + operator + " " + tuple.right;
}
auto __lambda2491__(auto tuple){
	return tuple.left + " " + operator + " " + tuple;
}
auto __lambda2492__(auto tuple){
	return tuple;
}
auto __lambda2493__(auto tuple){
	return tuple.left;
}
auto __lambda2494__(auto tuple){
	return tuple;
}
auto __lambda2495__(auto tuple){
	return tuple.left;
}
auto __lambda2496__(auto tuple){
	return tuple;
}
auto __lambda2497__(auto ){
	return compileValue(right, state);
}
auto __lambda2498__(auto ){
	return compileValue;
}
/* private static */Result_String_CompileError compileOperator(ParseState state, String input, String operator){
	int operatorIndex = input.indexOf(operator);
	if (1) {
	}
	String left = input.substring(0, operatorIndex);
	String right = input.substring(operatorIndex + operator.length());
	return compileValue(left, state).and(__lambda2475__).mapValue(__lambda2468__);
}
/* private static */int isNumber(String stripped){
	for (;;) {
	}
	return true;
}
auto __lambda2499__(auto inner){
	return inner.orElse("");
}
auto __lambda2500__(auto inner){
	return inner.orElse;
}
auto __lambda2501__(auto inner){
	return inner;
}
auto __lambda2502__(auto inner){
	return inner;
}
auto __lambda2503__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda2504__(auto name){
	return generateDefinition;
}
auto __lambda2505__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda2506__(auto name){
	return generateDefinition;
}
auto __lambda2507__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda2508__(auto name){
	return generateDefinition;
}
auto __lambda2509__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda2510__(auto name){
	return generateDefinition;
}
auto __lambda2511__(auto inner){
	return inner.orElse("");
}
auto __lambda2512__(auto inner){
	return inner.orElse;
}
auto __lambda2513__(auto inner){
	return inner;
}
auto __lambda2514__(auto inner){
	return inner;
}
auto __lambda2515__(auto inner){
	return inner.orElse("");
}
auto __lambda2516__(auto inner){
	return inner.orElse;
}
auto __lambda2517__(auto inner){
	return inner;
}
auto __lambda2518__(auto inner){
	return inner;
}
auto __lambda2519__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda2520__(auto name){
	return generateDefinition;
}
auto __lambda2521__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda2522__(auto name){
	return generateDefinition;
}
auto __lambda2523__(auto tuple){
	return 
	addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(tuple.left, tuple.right));
	return lambda;;
}
auto __lambda2524__(auto ){
	return params;
}
auto __lambda2525__(auto ){
	return params;
}
auto __lambda2526__(auto ){
	return params;
}
auto __lambda2527__(auto ){
	return params;
}
auto __lambda2528__(auto ){
	return params;
}
auto __lambda2529__(auto tuple){
	return 
	addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(tuple.left, tuple.right));
	return lambda;;
}
auto __lambda2530__(auto tuple){
	return 
	addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(tuple.left, tuple.right));
	return lambda;;
}
auto __lambda2531__(auto ){
	return params;
}
/* private static */Result_String_CompileError generateLambda(List__String paramNames, String lambdaValue){
	String lambda = "__lambda" + lambdaCounter + "__";
	temp++;
	Result_String_CompileError definition = generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", lambda));
	Result_String_CompileError params = paramNames.stream().map(__lambda2503__).collect(Exceptional(Joiner(", "))).mapValue(__lambda2499__);
	return definition.and(__lambda2524__).mapValue(__lambda2523__);
}
auto __lambda2532__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda2533__(auto outputCaller){
	return generateInvocation;
}
auto __lambda2534__(auto outputValues){
	return compileValue(inputCaller, state).mapValue(__lambda2532__);
}
auto __lambda2535__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda2536__(auto outputCaller){
	return generateInvocation;
}
auto __lambda2537__(auto outputValues){
	return compileValue(inputCaller, state).mapValue;
}
auto __lambda2538__(auto outputValues){
	return compileValue(inputCaller, state);
}
auto __lambda2539__(auto outputValues){
	return compileValue;
}
auto __lambda2540__(auto outputValues){
	return compileValue(inputCaller, state);
}
auto __lambda2541__(auto outputValues){
	return compileValue;
}
auto __lambda2542__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda2543__(auto outputCaller){
	return generateInvocation;
}
auto __lambda2544__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda2545__(auto outputCaller){
	return generateInvocation;
}
auto __lambda2546__(auto outputValues){
	return compileValue(inputCaller, state).mapValue(__lambda2544__);
}
auto __lambda2547__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda2548__(auto outputCaller){
	return generateInvocation;
}
auto __lambda2549__(auto outputValues){
	return compileValue(inputCaller, state).mapValue;
}
auto __lambda2550__(auto outputValues){
	return compileValue(inputCaller, state);
}
auto __lambda2551__(auto outputValues){
	return compileValue;
}
auto __lambda2552__(auto outputValues){
	return compileValue(inputCaller, state);
}
auto __lambda2553__(auto outputValues){
	return compileValue;
}
auto __lambda2554__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda2555__(auto outputCaller){
	return generateInvocation;
}
/* private static */Result_String_CompileError compileInvocation(ParseState state, String input){
	String stripped = input.strip();
	if (1) {
	}
	String withoutEnd = stripped.substring(0, stripped.length() - ")".length());
	int argsStart =  - 1;
	int depth = 0;
	for (;;) {
	}
	if (1) {
	}
	String inputCaller = withoutEnd.substring(0, argsStart);
	String inputArguments = withoutEnd.substring(argsStart + 1);
	return compileAllValues(state, inputArguments).flatMapValue(__lambda2534__);
}
auto __lambda2556__(auto input){
	return compileValue(input, state);
}
auto __lambda2557__(auto input){
	return compileValue;
}
auto __lambda2558__(auto input){
	return compileValue(input, state);
}
auto __lambda2559__(auto input){
	return compileValue;
}
/* private static */Result_String_CompileError compileAllValues(ParseState state, String arguments){
	return compileValues(arguments, __lambda2556__);
}
/* private static */String generateInvocation(String caller, String arguments){
	return caller + "(" + arguments + ")";
}
auto __lambda2560__(auto node){
	return generateDefinition(node.withString("name", name));
}
auto __lambda2561__(auto node){
	return generateDefinition;
}
auto __lambda2562__(auto node){
	return generateDefinition(node.withString("name", name));
}
auto __lambda2563__(auto node){
	return generateDefinition;
}
/* private static */Result_String_CompileError compileDefinition(String input, ParseState state){
	String stripped = input.strip();
	if (1) {
	}
	int nameSeparator = stripped.lastIndexOf(" ");
	if (1) {
	}
	String beforeName = stripped.substring(0, nameSeparator);
	String name = stripped.substring(nameSeparator + " ".length());
	return compileBeforeName(state, beforeName).flatMapValue(__lambda2560__);
}
auto __lambda2564__(auto outputType){
	return modifiers1.withString("type", outputType);
}
auto __lambda2565__(auto outputType){
	return modifiers1.withString;
}
auto __lambda2566__(auto outputType){
	return modifiers1;
}
auto __lambda2567__(auto outputType){
	return modifiers1;
}
auto __lambda2568__(auto outputType){
	return modifiers1.withString("type", outputType);
}
auto __lambda2569__(auto outputType){
	return modifiers1.withString;
}
auto __lambda2570__(auto outputType){
	return modifiers1;
}
auto __lambda2571__(auto outputType){
	return modifiers1;
}
/* private static */Result_Node_CompileError compileBeforeName(ParseState state, String beforeName){
	int typeSeparator = findTypeSeparator(beforeName);
	if (1) {
	}
	String modifiers = generatePlaceholder(beforeName.substring(0, typeSeparator));
	String inputType = beforeName.substring(typeSeparator + 1);
	Node modifiers1 = Node().withString("modifiers", modifiers);
	return compileType(state, inputType).mapValue(__lambda2564__);
}
/* private static */int findTypeSeparator(String beforeName){
	int typeSeparator =  - 1;
	int depth = 0;
	for (;;) {
	}
	return typeSeparator;
}
/* private static */Result_String_CompileError generateDefinition(Node node){
	return Ok(node.find("modifiers").orElse("") + node.find("type").orElse("") + " " + node.find("name").orElse(""));
}
auto __lambda2572__(auto main){
	return Main.compilePrimitive(main);
}
auto __lambda2573__(auto main){
	return Main.compileSymbolType(main);
}
auto __lambda2574__(auto type){
	return compileTypeParam(state, type);
}
auto __lambda2575__(auto type){
	return compileTypeParam;
}
auto __lambda2576__(auto type){
	return compileArray(state, type);
}
auto __lambda2577__(auto type){
	return compileArray;
}
auto __lambda2578__(auto type){
	return compileGeneric(state, type);
}
auto __lambda2579__(auto type){
	return compileGeneric;
}
auto __lambda2580__(auto param){
	return Lists.of(
                Main.compilePrimitive,
                Main(param);
}
auto __lambda2581__(auto param){
	return Lists.of(
                Main::compilePrimitive,
                Main.compileSymbolType,
                type -> compileTypeParam(state, type),
                type -> compileArray(state, type),
                type -> compileGeneric(state, type)
        )(param);
}
auto __lambda2582__(auto param){
	return Lists.of(
                Main.compilePrimitive,
                Main(param);
}
auto __lambda2583__(auto param){
	return Lists.of(
                Main::compilePrimitive,
                Main.compileSymbolType,
                type(param);
}
auto __lambda2584__(auto main){
	return Main.compilePrimitive(main);
}
auto __lambda2585__(auto main){
	return Main.compileSymbolType(main);
}
auto __lambda2586__(auto type){
	return compileTypeParam(state, type);
}
auto __lambda2587__(auto type){
	return compileTypeParam;
}
auto __lambda2588__(auto type){
	return compileArray(state, type);
}
auto __lambda2589__(auto type){
	return compileArray;
}
auto __lambda2590__(auto type){
	return compileGeneric(state, type);
}
auto __lambda2591__(auto type){
	return compileGeneric;
}
auto __lambda2592__(auto param){
	return Lists.of(
                Main.compilePrimitive,
                Main(param);
}
auto __lambda2593__(auto param){
	return Lists.of(
                Main::compilePrimitive,
                Main.compileSymbolType,
                type -> compileTypeParam(state, type),
                type -> compileArray(state, type),
                type -> compileGeneric(state, type)
        )(param);
}
auto __lambda2594__(auto param){
	return Lists.of(
                Main.compilePrimitive,
                Main(param);
}
auto __lambda2595__(auto param){
	return Lists.of(
                Main::compilePrimitive,
                Main.compileSymbolType,
                type(param);
}
/* private static */Result_String_CompileError compileType(ParseState state, String input){
	return compileOr(input, Lists.of(__lambda2572__, __lambda2573__, __lambda2574__, __lambda2576__, __lambda2578__));
}
auto __lambda2596__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda2597__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda2598__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2599__(auto type1){
	return compileType;
}
auto __lambda2600__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2601__(auto type1){
	return compileType;
}
auto __lambda2602__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2603__(auto type1){
	return compileType;
}
auto __lambda2604__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2605__(auto type1){
	return compileType;
}
auto __lambda2606__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2607__(auto type1){
	return compileType;
}
auto __lambda2608__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2609__(auto type1){
	return compileType;
}
auto __lambda2610__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2611__(auto type1){
	return compileType;
}
auto __lambda2612__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2613__(auto type1){
	return compileType;
}
auto __lambda2614__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2615__(auto type1){
	return compileType;
}
auto __lambda2616__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2617__(auto type1){
	return compileType;
}
auto __lambda2618__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2619__(auto type1){
	return compileType;
}
auto __lambda2620__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2621__(auto type1){
	return compileType;
}
auto __lambda2622__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2623__(auto type1){
	return compileType;
}
auto __lambda2624__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2625__(auto type1){
	return compileType;
}
auto __lambda2626__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2627__(auto type1){
	return compileType;
}
auto __lambda2628__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2629__(auto type1){
	return compileType;
}
auto __lambda2630__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2631__(auto type1){
	return compileType;
}
auto __lambda2632__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2633__(auto type1){
	return compileType;
}
auto __lambda2634__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2635__(auto type1){
	return compileType;
}
auto __lambda2636__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2637__(auto type1){
	return compileType;
}
auto __lambda2638__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2639__(auto type1){
	return compileType;
}
auto __lambda2640__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2641__(auto type1){
	return compileType;
}
auto __lambda2642__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2643__(auto type1){
	return compileType;
}
auto __lambda2644__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2645__(auto type1){
	return compileType;
}
auto __lambda2646__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2647__(auto type1){
	return compileType;
}
auto __lambda2648__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2649__(auto type1){
	return compileType;
}
auto __lambda2650__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2651__(auto type1){
	return compileType;
}
auto __lambda2652__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2653__(auto type1){
	return compileType;
}
auto __lambda2654__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2655__(auto type1){
	return compileType;
}
auto __lambda2656__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2657__(auto type1){
	return compileType;
}
auto __lambda2658__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2659__(auto type1){
	return compileType;
}
auto __lambda2660__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2661__(auto type1){
	return compileType;
}
auto __lambda2662__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2663__(auto type1){
	return compileType;
}
auto __lambda2664__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2665__(auto type1){
	return compileType;
}
auto __lambda2666__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2667__(auto type1){
	return compileType;
}
auto __lambda2668__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2669__(auto type1){
	return compileType;
}
auto __lambda2670__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2671__(auto type1){
	return compileType;
}
auto __lambda2672__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2673__(auto type1){
	return compileType;
}
auto __lambda2674__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2675__(auto type1){
	return compileType;
}
auto __lambda2676__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2677__(auto type1){
	return compileType;
}
auto __lambda2678__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2679__(auto type1){
	return compileType;
}
auto __lambda2680__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2681__(auto type1){
	return compileType;
}
auto __lambda2682__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2683__(auto type1){
	return compileType;
}
auto __lambda2684__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2685__(auto type1){
	return compileType;
}
auto __lambda2686__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2687__(auto type1){
	return compileType;
}
auto __lambda2688__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2689__(auto type1){
	return compileType;
}
auto __lambda2690__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2691__(auto type1){
	return compileType;
}
auto __lambda2692__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2693__(auto type1){
	return compileType;
}
auto __lambda2694__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2695__(auto type1){
	return compileType;
}
auto __lambda2696__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2697__(auto type1){
	return compileType;
}
auto __lambda2698__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2699__(auto type1){
	return compileType;
}
auto __lambda2700__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2701__(auto type1){
	return compileType;
}
auto __lambda2702__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2703__(auto type1){
	return compileType;
}
auto __lambda2704__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2705__(auto type1){
	return compileType;
}
auto __lambda2706__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2707__(auto type1){
	return compileType;
}
auto __lambda2708__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2709__(auto type1){
	return compileType;
}
auto __lambda2710__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2711__(auto type1){
	return compileType;
}
auto __lambda2712__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2713__(auto type1){
	return compileType;
}
auto __lambda2714__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2715__(auto type1){
	return compileType;
}
auto __lambda2716__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2717__(auto type1){
	return compileType;
}
auto __lambda2718__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2719__(auto type1){
	return compileType;
}
auto __lambda2720__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2721__(auto type1){
	return compileType;
}
auto __lambda2722__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2723__(auto type1){
	return compileType;
}
auto __lambda2724__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2725__(auto type1){
	return compileType;
}
auto __lambda2726__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2727__(auto type1){
	return compileType;
}
auto __lambda2728__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2729__(auto type1){
	return compileType;
}
auto __lambda2730__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2731__(auto type1){
	return compileType;
}
auto __lambda2732__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2733__(auto type1){
	return compileType;
}
auto __lambda2734__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2735__(auto type1){
	return compileType;
}
auto __lambda2736__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2737__(auto type1){
	return compileType;
}
auto __lambda2738__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2739__(auto type1){
	return compileType;
}
auto __lambda2740__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2741__(auto type1){
	return compileType;
}
auto __lambda2742__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2743__(auto type1){
	return compileType;
}
auto __lambda2744__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2745__(auto type1){
	return compileType;
}
auto __lambda2746__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2747__(auto type1){
	return compileType;
}
auto __lambda2748__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2749__(auto type1){
	return compileType;
}
auto __lambda2750__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2751__(auto type1){
	return compileType;
}
auto __lambda2752__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2753__(auto type1){
	return compileType;
}
auto __lambda2754__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2755__(auto type1){
	return compileType;
}
auto __lambda2756__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2757__(auto type1){
	return compileType;
}
auto __lambda2758__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2759__(auto type1){
	return compileType;
}
auto __lambda2760__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2761__(auto type1){
	return compileType;
}
auto __lambda2762__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2763__(auto type1){
	return compileType;
}
auto __lambda2764__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2765__(auto type1){
	return compileType;
}
auto __lambda2766__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2767__(auto type1){
	return compileType;
}
auto __lambda2768__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2769__(auto type1){
	return compileType;
}
auto __lambda2770__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2771__(auto type1){
	return compileType;
}
auto __lambda2772__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2773__(auto type1){
	return compileType;
}
auto __lambda2774__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2775__(auto type1){
	return compileType;
}
auto __lambda2776__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2777__(auto type1){
	return compileType;
}
auto __lambda2778__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2779__(auto type1){
	return compileType;
}
auto __lambda2780__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2781__(auto type1){
	return compileType;
}
auto __lambda2782__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2783__(auto type1){
	return compileType;
}
auto __lambda2784__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2785__(auto type1){
	return compileType;
}
auto __lambda2786__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2787__(auto type1){
	return compileType;
}
auto __lambda2788__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2789__(auto type1){
	return compileType;
}
auto __lambda2790__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2791__(auto type1){
	return compileType;
}
auto __lambda2792__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2793__(auto type1){
	return compileType;
}
auto __lambda2794__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2795__(auto type1){
	return compileType;
}
auto __lambda2796__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2797__(auto type1){
	return compileType;
}
auto __lambda2798__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2799__(auto type1){
	return compileType;
}
auto __lambda2800__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2801__(auto type1){
	return compileType;
}
auto __lambda2802__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2803__(auto type1){
	return compileType;
}
auto __lambda2804__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2805__(auto type1){
	return compileType;
}
auto __lambda2806__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2807__(auto type1){
	return compileType;
}
auto __lambda2808__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2809__(auto type1){
	return compileType;
}
auto __lambda2810__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2811__(auto type1){
	return compileType;
}
auto __lambda2812__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2813__(auto type1){
	return compileType;
}
auto __lambda2814__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2815__(auto type1){
	return compileType;
}
auto __lambda2816__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2817__(auto type1){
	return compileType;
}
auto __lambda2818__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2819__(auto type1){
	return compileType;
}
auto __lambda2820__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2821__(auto type1){
	return compileType;
}
auto __lambda2822__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2823__(auto type1){
	return compileType;
}
auto __lambda2824__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2825__(auto type1){
	return compileType;
}
auto __lambda2826__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2827__(auto type1){
	return compileType;
}
auto __lambda2828__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2829__(auto type1){
	return compileType;
}
auto __lambda2830__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2831__(auto type1){
	return compileType;
}
auto __lambda2832__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2833__(auto type1){
	return compileType;
}
auto __lambda2834__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2835__(auto type1){
	return compileType;
}
auto __lambda2836__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2837__(auto type1){
	return compileType;
}
auto __lambda2838__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2839__(auto type1){
	return compileType;
}
auto __lambda2840__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2841__(auto type1){
	return compileType;
}
auto __lambda2842__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2843__(auto type1){
	return compileType;
}
auto __lambda2844__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2845__(auto type1){
	return compileType;
}
auto __lambda2846__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2847__(auto type1){
	return compileType;
}
auto __lambda2848__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2849__(auto type1){
	return compileType;
}
auto __lambda2850__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2851__(auto type1){
	return compileType;
}
auto __lambda2852__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2853__(auto type1){
	return compileType;
}
auto __lambda2854__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2855__(auto type1){
	return compileType;
}
auto __lambda2856__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2857__(auto type1){
	return compileType;
}
auto __lambda2858__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2859__(auto type1){
	return compileType;
}
auto __lambda2860__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2861__(auto type1){
	return compileType;
}
auto __lambda2862__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2863__(auto type1){
	return compileType;
}
auto __lambda2864__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2865__(auto type1){
	return compileType;
}
auto __lambda2866__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2867__(auto type1){
	return compileType;
}
auto __lambda2868__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2869__(auto type1){
	return compileType;
}
auto __lambda2870__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2871__(auto type1){
	return compileType;
}
auto __lambda2872__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2873__(auto type1){
	return compileType;
}
auto __lambda2874__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2875__(auto type1){
	return compileType;
}
auto __lambda2876__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2877__(auto type1){
	return compileType;
}
auto __lambda2878__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2879__(auto type1){
	return compileType;
}
auto __lambda2880__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2881__(auto type1){
	return compileType;
}
auto __lambda2882__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2883__(auto type1){
	return compileType;
}
auto __lambda2884__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2885__(auto type1){
	return compileType;
}
auto __lambda2886__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2887__(auto type1){
	return compileType;
}
auto __lambda2888__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2889__(auto type1){
	return compileType;
}
auto __lambda2890__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2891__(auto type1){
	return compileType;
}
auto __lambda2892__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2893__(auto type1){
	return compileType;
}
auto __lambda2894__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2895__(auto type1){
	return compileType;
}
auto __lambda2896__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2897__(auto type1){
	return compileType;
}
auto __lambda2898__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2899__(auto type1){
	return compileType;
}
auto __lambda2900__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2901__(auto type1){
	return compileType;
}
auto __lambda2902__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2903__(auto type1){
	return compileType;
}
auto __lambda2904__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2905__(auto type1){
	return compileType;
}
auto __lambda2906__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2907__(auto type1){
	return compileType;
}
auto __lambda2908__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2909__(auto type1){
	return compileType;
}
auto __lambda2910__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2911__(auto type1){
	return compileType;
}
auto __lambda2912__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2913__(auto type1){
	return compileType;
}
auto __lambda2914__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2915__(auto type1){
	return compileType;
}
auto __lambda2916__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2917__(auto type1){
	return compileType;
}
auto __lambda2918__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2919__(auto type1){
	return compileType;
}
auto __lambda2920__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2921__(auto type1){
	return compileType;
}
auto __lambda2922__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2923__(auto type1){
	return compileType;
}
auto __lambda2924__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2925__(auto type1){
	return compileType;
}
auto __lambda2926__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2927__(auto type1){
	return compileType;
}
auto __lambda2928__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2929__(auto type1){
	return compileType;
}
auto __lambda2930__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2931__(auto type1){
	return compileType;
}
auto __lambda2932__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2933__(auto type1){
	return compileType;
}
auto __lambda2934__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2935__(auto type1){
	return compileType;
}
auto __lambda2936__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2937__(auto type1){
	return compileType;
}
auto __lambda2938__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2939__(auto type1){
	return compileType;
}
auto __lambda2940__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2941__(auto type1){
	return compileType;
}
auto __lambda2942__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2943__(auto type1){
	return compileType;
}
auto __lambda2944__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2945__(auto type1){
	return compileType;
}
auto __lambda2946__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2947__(auto type1){
	return compileType;
}
auto __lambda2948__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2949__(auto type1){
	return compileType;
}
auto __lambda2950__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2951__(auto type1){
	return compileType;
}
auto __lambda2952__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2953__(auto type1){
	return compileType;
}
auto __lambda2954__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2955__(auto type1){
	return compileType;
}
auto __lambda2956__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2957__(auto type1){
	return compileType;
}
auto __lambda2958__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2959__(auto type1){
	return compileType;
}
auto __lambda2960__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2961__(auto type1){
	return compileType;
}
auto __lambda2962__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2963__(auto type1){
	return compileType;
}
auto __lambda2964__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2965__(auto type1){
	return compileType;
}
auto __lambda2966__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2967__(auto type1){
	return compileType;
}
auto __lambda2968__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2969__(auto type1){
	return compileType;
}
auto __lambda2970__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2971__(auto type1){
	return compileType;
}
auto __lambda2972__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2973__(auto type1){
	return compileType;
}
auto __lambda2974__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2975__(auto type1){
	return compileType;
}
auto __lambda2976__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2977__(auto type1){
	return compileType;
}
auto __lambda2978__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2979__(auto type1){
	return compileType;
}
auto __lambda2980__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2981__(auto type1){
	return compileType;
}
auto __lambda2982__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2983__(auto type1){
	return compileType;
}
auto __lambda2984__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2985__(auto type1){
	return compileType;
}
auto __lambda2986__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2987__(auto type1){
	return compileType;
}
auto __lambda2988__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2989__(auto type1){
	return compileType;
}
auto __lambda2990__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2991__(auto type1){
	return compileType;
}
auto __lambda2992__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2993__(auto type1){
	return compileType;
}
auto __lambda2994__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2995__(auto type1){
	return compileType;
}
auto __lambda2996__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2997__(auto type1){
	return compileType;
}
auto __lambda2998__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda2999__(auto type1){
	return compileType;
}
auto __lambda3000__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3001__(auto type1){
	return compileType;
}
auto __lambda3002__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3003__(auto type1){
	return compileType;
}
auto __lambda3004__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3005__(auto type1){
	return compileType;
}
auto __lambda3006__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3007__(auto type1){
	return compileType;
}
auto __lambda3008__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3009__(auto type1){
	return compileType;
}
auto __lambda3010__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3011__(auto type1){
	return compileType;
}
auto __lambda3012__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3013__(auto type1){
	return compileType;
}
auto __lambda3014__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3015__(auto type1){
	return compileType;
}
auto __lambda3016__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3017__(auto type1){
	return compileType;
}
auto __lambda3018__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3019__(auto type1){
	return compileType;
}
auto __lambda3020__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3021__(auto type1){
	return compileType;
}
auto __lambda3022__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3023__(auto type1){
	return compileType;
}
auto __lambda3024__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3025__(auto type1){
	return compileType;
}
auto __lambda3026__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3027__(auto type1){
	return compileType;
}
auto __lambda3028__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3029__(auto type1){
	return compileType;
}
auto __lambda3030__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3031__(auto type1){
	return compileType;
}
auto __lambda3032__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3033__(auto type1){
	return compileType;
}
auto __lambda3034__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3035__(auto type1){
	return compileType;
}
auto __lambda3036__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3037__(auto type1){
	return compileType;
}
auto __lambda3038__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3039__(auto type1){
	return compileType;
}
auto __lambda3040__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3041__(auto type1){
	return compileType;
}
auto __lambda3042__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3043__(auto type1){
	return compileType;
}
auto __lambda3044__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3045__(auto type1){
	return compileType;
}
auto __lambda3046__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3047__(auto type1){
	return compileType;
}
auto __lambda3048__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3049__(auto type1){
	return compileType;
}
auto __lambda3050__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3051__(auto type1){
	return compileType;
}
auto __lambda3052__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3053__(auto type1){
	return compileType;
}
auto __lambda3054__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3055__(auto type1){
	return compileType;
}
auto __lambda3056__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3057__(auto type1){
	return compileType;
}
auto __lambda3058__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3059__(auto type1){
	return compileType;
}
auto __lambda3060__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3061__(auto type1){
	return compileType;
}
auto __lambda3062__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3063__(auto type1){
	return compileType;
}
auto __lambda3064__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3065__(auto type1){
	return compileType;
}
auto __lambda3066__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3067__(auto type1){
	return compileType;
}
auto __lambda3068__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3069__(auto type1){
	return compileType;
}
auto __lambda3070__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3071__(auto type1){
	return compileType;
}
auto __lambda3072__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3073__(auto type1){
	return compileType;
}
auto __lambda3074__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3075__(auto type1){
	return compileType;
}
auto __lambda3076__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3077__(auto type1){
	return compileType;
}
auto __lambda3078__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3079__(auto type1){
	return compileType;
}
auto __lambda3080__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3081__(auto type1){
	return compileType;
}
auto __lambda3082__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3083__(auto type1){
	return compileType;
}
auto __lambda3084__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3085__(auto type1){
	return compileType;
}
auto __lambda3086__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3087__(auto type1){
	return compileType;
}
auto __lambda3088__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3089__(auto type1){
	return compileType;
}
auto __lambda3090__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3091__(auto type1){
	return compileType;
}
auto __lambda3092__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3093__(auto type1){
	return compileType;
}
auto __lambda3094__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3095__(auto type1){
	return compileType;
}
auto __lambda3096__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3097__(auto type1){
	return compileType;
}
auto __lambda3098__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3099__(auto type1){
	return compileType;
}
auto __lambda3100__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3101__(auto type1){
	return compileType;
}
auto __lambda3102__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3103__(auto type1){
	return compileType;
}
auto __lambda3104__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3105__(auto type1){
	return compileType;
}
auto __lambda3106__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3107__(auto type1){
	return compileType;
}
auto __lambda3108__(auto type1){
	return compileType(parseState, type1);
}
auto __lambda3109__(auto type1){
	return compileType;
}
/* private static */Result_String_CompileError compileGeneric(ParseState parseState, String type){
	String stripped = type.strip();
	if (1) {
	}
	String withoutEnd = stripped.substring(0, stripped.length() - ">".length());
	int genStart = withoutEnd.indexOf("<");
	if (1) {
	}
	String base = withoutEnd.substring(0, genStart).strip();
	if (1) {
	}
	String oldArguments = withoutEnd.substring(genStart + "<".length());
	List__String segments = divideAll(oldArguments, __lambda2596__);
	return parseAll(segments, __lambda2598__).mapValue(newArguments -> {
            switch (base) {
                case "Function" -> {
                    return generateFunctionalType(newArguments.apply(1).orElse(null), Lists.of(newArguments.apply(0).orElse(null)));
                }
                case "BiFunction" -> {
                    return generateFunctionalType(newArguments.apply(2).orElse(null), Lists.of(newArguments.apply(0).orElse(null), newArguments.apply(1).orElse(null)));
                }
                case "Consumer" -> {
                    return generateFunctionalType("void", Lists.of(newArguments.apply(0).orElse(null)));
                }
                case "Supplier" -> {
                    return generateFunctionalType(newArguments.apply(0).orElse(null), Lists.empty());
                }
            }

            if (parseState.isNothingDefined()) {
                Tuple<String, List_<String>> tuple = new Tuple<>(base, newArguments);
                if (!isDefined(toExpand, tuple)) {
                    toExpand = toExpand.add(tuple);
                }
            }

            return generateGenericName(base, newArguments);
        });
}
/* private static */Result_String_CompileError compileArray(ParseState parseState, String type){
	String stripped = type.strip();
	if (1) {
	}
	return createSuffixErr(stripped, "[]");
}
auto __lambda3110__(auto errors){
	return CompileError("No valid combination present", input, errors);
}
auto __lambda3111__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3112__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3113__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3114__(auto param){
	return rule.compile(input).match(orState::withValue, orState.withError)(param);
}
auto __lambda3115__(auto orState, auto rule){
	return rule.compile(input).match(__lambda3111__, __lambda3112__);
}
auto __lambda3116__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3117__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3118__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda3119__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3120__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3121__(auto orState, auto rule){
	return rule;
}
auto __lambda3122__(auto orState, auto rule){
	return rule;
}
auto __lambda3123__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3124__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3125__(auto orState, auto rule){
	return rule;
}
auto __lambda3126__(auto orState, auto rule){
	return rule;
}
auto __lambda3127__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3128__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda3129__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3130__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3131__(auto orState, auto rule){
	return rule;
}
auto __lambda3132__(auto orState, auto rule){
	return rule;
}
auto __lambda3133__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda3134__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3135__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3136__(auto orState, auto rule){
	return rule;
}
auto __lambda3137__(auto orState, auto rule){
	return rule;
}
auto __lambda3138__(auto __lambda3133__){
	return __lambda3133__.withValue, orState(__lambda3133__);
}
auto __lambda3139__(auto __lambda3128__){
	return __lambda3128__.withError)(__lambda3128__);
}
auto __lambda3140__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3141__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3142__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3143__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))(param);
}
auto __lambda3144__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3145__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult(param);
}
auto __lambda3146__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3147__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3148__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3149__(auto param){
	return rule.compile(input).match(orState::withValue, orState.withError)(param);
}
auto __lambda3150__(auto orState, auto rule){
	return rule.compile(input).match(__lambda3146__, __lambda3147__);
}
auto __lambda3151__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3152__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3153__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda3154__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3155__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3156__(auto orState, auto rule){
	return rule;
}
auto __lambda3157__(auto orState, auto rule){
	return rule;
}
auto __lambda3158__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3159__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3160__(auto orState, auto rule){
	return rule;
}
auto __lambda3161__(auto orState, auto rule){
	return rule;
}
auto __lambda3162__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3163__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda3164__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3165__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3166__(auto orState, auto rule){
	return rule;
}
auto __lambda3167__(auto orState, auto rule){
	return rule;
}
auto __lambda3168__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda3169__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3170__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3171__(auto orState, auto rule){
	return rule;
}
auto __lambda3172__(auto orState, auto rule){
	return rule;
}
auto __lambda3173__(auto __lambda3168__){
	return __lambda3168__.withValue, orState(__lambda3168__);
}
auto __lambda3174__(auto __lambda3163__){
	return __lambda3163__.withError)(__lambda3163__);
}
auto __lambda3175__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3176__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3177__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3178__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))(param);
}
auto __lambda3179__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3180__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult()(param);
}
auto __lambda3181__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3182__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult()
                .mapErr(param);
}
auto __lambda3183__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3184__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3185__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3186__(auto param){
	return rule.compile(input).match(orState::withValue, orState.withError)(param);
}
auto __lambda3187__(auto orState, auto rule){
	return rule.compile(input).match(__lambda3183__, __lambda3184__);
}
auto __lambda3188__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3189__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3190__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda3191__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3192__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3193__(auto orState, auto rule){
	return rule;
}
auto __lambda3194__(auto orState, auto rule){
	return rule;
}
auto __lambda3195__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3196__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3197__(auto orState, auto rule){
	return rule;
}
auto __lambda3198__(auto orState, auto rule){
	return rule;
}
auto __lambda3199__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3200__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda3201__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3202__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3203__(auto orState, auto rule){
	return rule;
}
auto __lambda3204__(auto orState, auto rule){
	return rule;
}
auto __lambda3205__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda3206__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3207__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3208__(auto orState, auto rule){
	return rule;
}
auto __lambda3209__(auto orState, auto rule){
	return rule;
}
auto __lambda3210__(auto __lambda3205__){
	return __lambda3205__.withValue, orState(__lambda3205__);
}
auto __lambda3211__(auto __lambda3200__){
	return __lambda3200__.withError)(__lambda3200__);
}
auto __lambda3212__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3213__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3214__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3215__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))(param);
}
auto __lambda3216__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3217__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult(param);
}
auto __lambda3218__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3219__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3220__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3221__(auto param){
	return rule.compile(input).match(orState::withValue, orState.withError)(param);
}
auto __lambda3222__(auto orState, auto rule){
	return rule.compile(input).match(__lambda3218__, __lambda3219__);
}
auto __lambda3223__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3224__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3225__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda3226__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3227__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3228__(auto orState, auto rule){
	return rule;
}
auto __lambda3229__(auto orState, auto rule){
	return rule;
}
auto __lambda3230__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3231__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3232__(auto orState, auto rule){
	return rule;
}
auto __lambda3233__(auto orState, auto rule){
	return rule;
}
auto __lambda3234__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3235__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda3236__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3237__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3238__(auto orState, auto rule){
	return rule;
}
auto __lambda3239__(auto orState, auto rule){
	return rule;
}
auto __lambda3240__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda3241__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3242__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3243__(auto orState, auto rule){
	return rule;
}
auto __lambda3244__(auto orState, auto rule){
	return rule;
}
auto __lambda3245__(auto __lambda3240__){
	return __lambda3240__.withValue, orState(__lambda3240__);
}
auto __lambda3246__(auto __lambda3235__){
	return __lambda3235__.withError)(__lambda3235__);
}
auto __lambda3247__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3248__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3249__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3250__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))(param);
}
auto __lambda3251__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3252__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult()(param);
}
auto __lambda3253__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3254__(auto param){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult()
                .mapErr(errors -> new CompileError("No valid combination present", input, errors))(param);
}
auto __lambda3255__(auto errors){
	return CompileError("No valid combination present", input, errors);
}
auto __lambda3256__(auto errors){
	return CompileError("No valid combination present", input, errors);
}
auto __lambda3257__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3258__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3259__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3260__(auto param){
	return rule.compile(input).match(orState::withValue, orState.withError)(param);
}
auto __lambda3261__(auto orState, auto rule){
	return rule.compile(input).match(__lambda3257__, __lambda3258__);
}
auto __lambda3262__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3263__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3264__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda3265__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3266__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3267__(auto orState, auto rule){
	return rule;
}
auto __lambda3268__(auto orState, auto rule){
	return rule;
}
auto __lambda3269__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3270__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3271__(auto orState, auto rule){
	return rule;
}
auto __lambda3272__(auto orState, auto rule){
	return rule;
}
auto __lambda3273__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3274__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda3275__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3276__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3277__(auto orState, auto rule){
	return rule;
}
auto __lambda3278__(auto orState, auto rule){
	return rule;
}
auto __lambda3279__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda3280__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3281__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3282__(auto orState, auto rule){
	return rule;
}
auto __lambda3283__(auto orState, auto rule){
	return rule;
}
auto __lambda3284__(auto __lambda3279__){
	return __lambda3279__.withValue, orState(__lambda3279__);
}
auto __lambda3285__(auto __lambda3274__){
	return __lambda3274__.withError)(__lambda3274__);
}
auto __lambda3286__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3287__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3288__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3289__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3290__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3291__(auto param){
	return rule.compile(input).match(orState::withValue, orState.withError)(param);
}
auto __lambda3292__(auto orState, auto rule){
	return rule.compile(input).match(__lambda3288__, __lambda3289__);
}
auto __lambda3293__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3294__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda3295__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda3296__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3297__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3298__(auto orState, auto rule){
	return rule;
}
auto __lambda3299__(auto orState, auto rule){
	return rule;
}
auto __lambda3300__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3301__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3302__(auto orState, auto rule){
	return rule;
}
auto __lambda3303__(auto orState, auto rule){
	return rule;
}
auto __lambda3304__(auto param){
	return rule.compile(input).match(orState.withValue, orState(param);
}
auto __lambda3305__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda3306__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3307__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3308__(auto orState, auto rule){
	return rule;
}
auto __lambda3309__(auto orState, auto rule){
	return rule;
}
auto __lambda3310__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda3311__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda3312__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda3313__(auto orState, auto rule){
	return rule;
}
auto __lambda3314__(auto orState, auto rule){
	return rule;
}
auto __lambda3315__(auto __lambda3310__){
	return __lambda3310__.withValue, orState(__lambda3310__);
}
auto __lambda3316__(auto __lambda3305__){
	return __lambda3305__.withError)(__lambda3305__);
}
auto __lambda3317__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda3318__(auto orstate){
	return orState.withError(orstate);
}
/* private static */Result_String_CompileError compileOr(String input, List__Rule rules){
	return rules.stream().foldWithInitial(OrState(), __lambda3115__).toResult().mapErr(__lambda3110__);
}
/* private static */Result_String_CompileError compilePrimitive(String type){
	if (1) {
	}
	if (1) {
	}
	return Err(CompileError("Not a primitive", type));
}
/* private static */Result_String_CompileError compileSymbolType(String type){
	if (1) {
	}
	return Err(CompileError("Not a symbol", type));
}
/* private static */Result_String_CompileError compileTypeParam(ParseState state, String type){
	String stripped = type.strip();
	if (1) {
	}
	return Ok(state.findArgumentValue(stripped).orElse(stripped));
}
auto __lambda3319__(auto string){
	return String.equals(string);
}
auto __lambda3320__(auto string){
	return String.equals(string);
}
auto __lambda3321__(auto param){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(param);
}
auto __lambda3322__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3320__);
}
auto __lambda3323__(auto string){
	return String.equals(string);
}
auto __lambda3324__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3325__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3326__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3327__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3328__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3329__(auto __lambda3327__){
	return __lambda3327__.equals)(__lambda3327__);
}
auto __lambda3330__(auto string){
	return String.equals(string);
}
auto __lambda3331__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3332__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3333__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3334__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3335__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3336__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3337__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(param);
}
auto __lambda3338__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3339__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3340__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String.equals))(param);
}
auto __lambda3341__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3342__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3343__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, __lambda3319__, __lambda3322__);
}
auto __lambda3344__(auto string){
	return String.equals(string);
}
auto __lambda3345__(auto string){
	return String.equals(string);
}
auto __lambda3346__(auto param){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(param);
}
auto __lambda3347__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3345__);
}
auto __lambda3348__(auto string){
	return String.equals(string);
}
auto __lambda3349__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3350__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3351__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3352__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3353__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3354__(auto __lambda3352__){
	return __lambda3352__.equals)(__lambda3352__);
}
auto __lambda3355__(auto string){
	return String.equals(string);
}
auto __lambda3356__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo;
}
auto __lambda3357__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3358__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3359__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3360__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3361__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists;
}
auto __lambda3362__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3363__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3364__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3365__(auto __lambda3363__){
	return __lambda3363__.equals,
                        (typeParams, typeParams2) -> Lists(__lambda3363__);
}
auto __lambda3366__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3367__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3368__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3369__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(param);
}
auto __lambda3370__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3371__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3372__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3373__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3374__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3375__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3376__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists;
}
auto __lambda3377__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3378__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3379__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3380__(auto __lambda3378__){
	return __lambda3378__.equals,
                        (typeParams, typeParams2) -> Lists(__lambda3378__);
}
auto __lambda3381__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3382__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3383__(auto __lambda3381__){
	return __lambda3381__.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(__lambda3381__);
}
auto __lambda3384__(auto __lambda3372__){
	return __lambda3372__.equals))(__lambda3372__);
}
auto __lambda3385__(auto string){
	return String.equals(string);
}
auto __lambda3386__(auto string){
	return String.equals(string);
}
auto __lambda3387__(auto param){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(param);
}
auto __lambda3388__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3386__);
}
auto __lambda3389__(auto string){
	return String.equals(string);
}
auto __lambda3390__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3391__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3392__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3393__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3394__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3395__(auto __lambda3393__){
	return __lambda3393__.equals)(__lambda3393__);
}
auto __lambda3396__(auto string){
	return String.equals(string);
}
auto __lambda3397__(auto param){
	return Lists.contains(toExpand, tuple,
                (stringListTuple, stringListTuple2) -> Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3398__(auto param){
	return Lists.contains(toExpand, tuple,
                (stringListTuple, stringListTuple2) -> Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3399__(auto param){
	return Lists.contains(toExpand, tuple,
                (stringListTuple, stringListTuple2) -> Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(param);
}
auto __lambda3400__(auto param){
	return Lists.contains(toExpand, tuple,
                (stringListTuple, stringListTuple2) -> Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String.equals)))(param);
}
auto __lambda3401__(auto string){
	return String.equals(string);
}
auto __lambda3402__(auto string){
	return String.equals(string);
}
auto __lambda3403__(auto param){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(param);
}
auto __lambda3404__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3402__);
}
auto __lambda3405__(auto string){
	return String.equals(string);
}
auto __lambda3406__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3407__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3408__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3409__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3410__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3411__(auto __lambda3409__){
	return __lambda3409__.equals)(__lambda3409__);
}
auto __lambda3412__(auto string){
	return String.equals(string);
}
auto __lambda3413__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3414__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3415__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3416__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3417__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3418__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3419__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(param);
}
auto __lambda3420__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3421__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3422__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String.equals))(param);
}
auto __lambda3423__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3424__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3425__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, __lambda3401__, __lambda3404__);
}
auto __lambda3426__(auto string){
	return String.equals(string);
}
auto __lambda3427__(auto string){
	return String.equals(string);
}
auto __lambda3428__(auto param){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(param);
}
auto __lambda3429__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3427__);
}
auto __lambda3430__(auto string){
	return String.equals(string);
}
auto __lambda3431__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3432__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3433__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3434__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3435__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3436__(auto __lambda3434__){
	return __lambda3434__.equals)(__lambda3434__);
}
auto __lambda3437__(auto string){
	return String.equals(string);
}
auto __lambda3438__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo;
}
auto __lambda3439__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3440__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3441__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3442__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3443__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists;
}
auto __lambda3444__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3445__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3446__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3447__(auto __lambda3445__){
	return __lambda3445__.equals,
                        (typeParams, typeParams2) -> Lists(__lambda3445__);
}
auto __lambda3448__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3449__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3450__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3451__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(param);
}
auto __lambda3452__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3453__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3454__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3455__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(param);
}
auto __lambda3456__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(param);
}
auto __lambda3457__(auto param){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(param);
}
auto __lambda3458__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists;
}
auto __lambda3459__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3460__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3461__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3462__(auto __lambda3460__){
	return __lambda3460__.equals,
                        (typeParams, typeParams2) -> Lists(__lambda3460__);
}
auto __lambda3463__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3464__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3465__(auto __lambda3463__){
	return __lambda3463__.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(__lambda3463__);
}
auto __lambda3466__(auto __lambda3454__){
	return __lambda3454__.equals))(__lambda3454__);
}
auto __lambda3467__(auto string){
	return String.equals(string);
}
auto __lambda3468__(auto string){
	return String.equals(string);
}
auto __lambda3469__(auto param){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(param);
}
auto __lambda3470__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3468__);
}
auto __lambda3471__(auto string){
	return String.equals(string);
}
auto __lambda3472__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3473__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3474__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3475__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3476__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3477__(auto __lambda3475__){
	return __lambda3475__.equals)(__lambda3475__);
}
auto __lambda3478__(auto string){
	return String.equals(string);
}
/* private static */int isDefined(List__Tuple_String_List__String toExpand, Tuple_String_List__String tuple){
	return Lists.contains(toExpand, tuple, __lambda3343__);
}
/* private static */String generateGenericName(String base, List__String newArguments){
	String joined = newArguments.stream().collect(Joiner("_")).orElse("");
	return base + "_" + String.join("_", joined);
}
/* private static */String generateFunctionalType(String returns, List__String newArguments){
	String joined = newArguments.stream().collect(Joiner(", ")).orElse("");
	return returns + " (*)(" + joined + ")";
}
/* private static */int isSymbol(String input){
	if (1) {
	}
	if (1) {
	}
	for (;;) {
	}
	return true;
}
/* private static */String generatePlaceholder(String input){
	return "/* " + input + " */";
}
/* @Override
        public <R> */R match(R (*)(T) whenOk, R (*)(X) whenErr){
	return whenErr.apply(error);
}
/* @Override
        public <R> */Result_R_X mapValue(R (*)(T) mapper){
	return Err(error);
}
/* @Override
        public <R> */Result_R_X flatMapValue(Result_R_X (*)(T) mapper){
	return Err(error);
}
/* @Override
        public */Option_X findError(){
	return Some(error);
}
/* @Override
        public <R> */Result_T_R mapErr(R (*)(X) mapper){
	return Err(mapper.apply(error));
}
/* @Override
        public <R> */Result_Tuple_T_R_X and(Result_R_X (*)() mapper){
	return Err(error);
}
int main(){
	__main__();
	return 0;
}
