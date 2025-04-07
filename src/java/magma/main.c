#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
/* private */ struct Rule {
	Result_String_CompileError compile(String input);
};
/* private static */ struct State {
	/* private final */List__Character queue;
	/* private final */List__String segments;
	/* private final */StringBuilder buffer;
	/* private final */int depth;
};
/* private static */ struct Streams {
};
/* private */ struct Joiner {
};
/* private static */ struct Tuples {
};
/* private */ struct CompileError {
};
/* private */ struct OrState {
};
/* private static final */ struct Node {
	/* private final */Map__String_String strings;
};
/* private static */ struct Maps {
};
/* public */ struct Main {
};
/* public */ struct List__T {
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
	List__T sort(Comparator_T comparator);
};
/* public */ struct Stream__R {
	/* <R> */Stream__R map(R (*)(R) mapper);
	/* <R> */R foldWithInitial(R initial, R (*)(R, R) folder);
	/* <C> */C collect(Collector_R_C collector);
	int anyMatch(Predicate_R predicate);
	/* <R> */Stream__R flatMap(Stream__R (*)(R) mapper);
	Stream__R concat(Stream__R other);
	Option_R next();
	int allMatch(Predicate_R predicate);
	Stream__R filter(Predicate_R predicate);
	/* <R, X> */Result_R_X foldToResult(R initial, Result_R_X (*)(R, R) folder);
};
/* sealed public */ struct Option_T {
	void ifPresent(void (*)(T) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(T) mapper);
	/* <R> */Option_R map(R (*)(T) mapper);
	T orElse(T other);
	int isPresent();
	Tuple_int_T toTuple(T other);
	T orElseGet(T (*)() other);
	Option_T or(Option_T (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_T_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(T) whenPresent, R (*)() whenEmpty);
};
/* sealed public */ struct Result_String_CompileError {
	/* <R> */R match(R (*)(String) whenOk, R (*)(CompileError) whenErr);
	/* <R> */Result_R_CompileError mapValue(R (*)(String) mapper);
	/* <R> */Result_R_CompileError flatMapValue(Result_R_CompileError (*)(String) mapper);
	Option_CompileError findError();
	/* <R> */Result_String_R mapErr(R (*)(CompileError) mapper);
	/* <R> */Result_Tuple_String_R_CompileError and(Result_R_CompileError (*)() mapper);
};
/* private */ struct Map__K_V {
	Map__K_V with(K key, V value);
	Option_V find(K key);
};
/* private */ struct Head_T {
	Option_T next();
};
/* public */ struct List__Character {
	List__Character add(Character element);
	List__Character addAll(List__Character elements);
	Stream__Character stream();
	Character popFirst();
	int hasElements();
	Option_Character apply(int index);
	int size();
	Character last();
	Stream__Tuple_Integer_Character streamWithIndices();
	Character first();
	List__Character sort(Comparator_Character comparator);
};
/* public */ struct List__String {
	List__String add(String element);
	List__String addAll(List__String elements);
	Stream__String stream();
	String popFirst();
	int hasElements();
	Option_String apply(int index);
	int size();
	String last();
	Stream__Tuple_Integer_String streamWithIndices();
	String first();
	List__String sort(Comparator_String comparator);
};
/* public */ struct Stream__Character {
	/* <R> */Stream__R map(R (*)(Character) mapper);
	/* <R> */R foldWithInitial(R initial, R (*)(R, Character) folder);
	/* <C> */C collect(Collector_Character_C collector);
	int anyMatch(Predicate_Character predicate);
	/* <R> */Stream__R flatMap(Stream__R (*)(Character) mapper);
	Stream__Character concat(Stream__Character other);
	Option_Character next();
	int allMatch(Predicate_Character predicate);
	Stream__Character filter(Predicate_Character predicate);
	/* <R, X> */Result_R_X foldToResult(R initial, Result_R_X (*)(R, Character) folder);
};
/* public */ struct Stream__T {
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
};
/* sealed public */ struct Option_String {
	void ifPresent(void (*)(String) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(String) mapper);
	/* <R> */Option_R map(R (*)(String) mapper);
	String orElse(String other);
	int isPresent();
	Tuple_int_String toTuple(String other);
	String orElseGet(String (*)() other);
	Option_String or(Option_String (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_String_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(String) whenPresent, R (*)() whenEmpty);
};
/* public */ struct Tuple_A_B {
};
/* public */ struct List__CompileError {
	List__CompileError add(CompileError element);
	List__CompileError addAll(List__CompileError elements);
	Stream__CompileError stream();
	CompileError popFirst();
	int hasElements();
	Option_CompileError apply(int index);
	int size();
	CompileError last();
	Stream__Tuple_Integer_CompileError streamWithIndices();
	CompileError first();
	List__CompileError sort(Comparator_CompileError comparator);
};
/* sealed public */ struct Result_String_List__CompileError {
	/* <R> */R match(R (*)(String) whenOk, R (*)(List__CompileError) whenErr);
	/* <R> */Result_R_List__CompileError mapValue(R (*)(String) mapper);
	/* <R> */Result_R_List__CompileError flatMapValue(Result_R_List__CompileError (*)(String) mapper);
	Option_List__CompileError findError();
	/* <R> */Result_String_R mapErr(R (*)(List__CompileError) mapper);
	/* <R> */Result_Tuple_String_R_List__CompileError and(Result_R_List__CompileError (*)() mapper);
};
/* sealed public */ struct Option_Integer {
	void ifPresent(void (*)(Integer) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(Integer) mapper);
	/* <R> */Option_R map(R (*)(Integer) mapper);
	Integer orElse(Integer other);
	int isPresent();
	Tuple_int_Integer toTuple(Integer other);
	Integer orElseGet(Integer (*)() other);
	Option_Integer or(Option_Integer (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_Integer_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(Integer) whenPresent, R (*)() whenEmpty);
};
/* public */ struct Collector_T_C {
	C createInitial();
	C fold(C current, T element);
};
/* private */ struct Map__String_String {
	Map__String_String with(String key, String value);
	Option_String find(String key);
};
/* public */ struct Tuple_String_List__String {
};
/* public */ struct List__Tuple_String_List__String {
	List__Tuple_String_List__String add(Tuple_String_List__String element);
	List__Tuple_String_List__String addAll(List__Tuple_String_List__String elements);
	Stream__Tuple_String_List__String stream();
	Tuple_String_List__String popFirst();
	int hasElements();
	Option_Tuple_String_List__String apply(int index);
	int size();
	Tuple_String_List__String last();
	Stream__Tuple_Integer_Tuple_String_List__String streamWithIndices();
	Tuple_String_List__String first();
	List__Tuple_String_List__String sort(Comparator_Tuple_String_List__String comparator);
};
/* private */ struct Map__String_Result_String_CompileError (*)(List__String) {
	Map__String_Result_String_CompileError (*)(List__String) with(String key, Result_String_CompileError (*)(List__String) value);
	Option_Result_String_CompileError (*)(List__String) find(String key);
};
/* sealed public */ struct Option_IOException {
	void ifPresent(void (*)(IOException) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(IOException) mapper);
	/* <R> */Option_R map(R (*)(IOException) mapper);
	IOException orElse(IOException other);
	int isPresent();
	Tuple_int_IOException toTuple(IOException other);
	IOException orElseGet(IOException (*)() other);
	Option_IOException or(Option_IOException (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_IOException_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(IOException) whenPresent, R (*)() whenEmpty);
};
/* sealed public */ struct Result_List__String_CompileError {
	/* <R> */R match(R (*)(List__String) whenOk, R (*)(CompileError) whenErr);
	/* <R> */Result_R_CompileError mapValue(R (*)(List__String) mapper);
	/* <R> */Result_R_CompileError flatMapValue(Result_R_CompileError (*)(List__String) mapper);
	Option_CompileError findError();
	/* <R> */Result_List__String_R mapErr(R (*)(CompileError) mapper);
	/* <R> */Result_Tuple_List__String_R_CompileError and(Result_R_CompileError (*)() mapper);
};
/* sealed public */ struct Option_CompileError {
	void ifPresent(void (*)(CompileError) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(CompileError) mapper);
	/* <R> */Option_R map(R (*)(CompileError) mapper);
	CompileError orElse(CompileError other);
	int isPresent();
	Tuple_int_CompileError toTuple(CompileError other);
	CompileError orElseGet(CompileError (*)() other);
	Option_CompileError or(Option_CompileError (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_CompileError_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(CompileError) whenPresent, R (*)() whenEmpty);
};
/* sealed public */ struct Option_State {
	void ifPresent(void (*)(State) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(State) mapper);
	/* <R> */Option_R map(R (*)(State) mapper);
	State orElse(State other);
	int isPresent();
	Tuple_int_State toTuple(State other);
	State orElseGet(State (*)() other);
	Option_State or(Option_State (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_State_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(State) whenPresent, R (*)() whenEmpty);
};
/* public */ struct List__List__String {
	List__List__String add(List__String element);
	List__List__String addAll(List__List__String elements);
	Stream__List__String stream();
	List__String popFirst();
	int hasElements();
	Option_List__String apply(int index);
	int size();
	List__String last();
	Stream__Tuple_Integer_List__String streamWithIndices();
	List__String first();
	List__List__String sort(Comparator_List__String comparator);
};
/* sealed public */ struct Option_Result_String_CompileError {
	void ifPresent(void (*)(Result_String_CompileError) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(Result_String_CompileError) mapper);
	/* <R> */Option_R map(R (*)(Result_String_CompileError) mapper);
	Result_String_CompileError orElse(Result_String_CompileError other);
	int isPresent();
	Tuple_int_Result_String_CompileError toTuple(Result_String_CompileError other);
	Result_String_CompileError orElseGet(Result_String_CompileError (*)() other);
	Option_Result_String_CompileError or(Option_Result_String_CompileError (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_Result_String_CompileError_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(Result_String_CompileError) whenPresent, R (*)() whenEmpty);
};
/* public */ struct Err_String_CompileError {
};
/* sealed public */ struct Option_List__String {
	void ifPresent(void (*)(List__String) ifPresent);
	/* <R> */Option_R flatMap(Option_R (*)(List__String) mapper);
	/* <R> */Option_R map(R (*)(List__String) mapper);
	List__String orElse(List__String other);
	int isPresent();
	Tuple_int_List__String toTuple(List__String other);
	List__String orElseGet(List__String (*)() other);
	Option_List__String or(Option_List__String (*)() other);
	int isEmpty();
	/* <R> */Option_Tuple_List__String_R and(Option_R (*)() other);
	/* <R> */R match(R (*)(List__String) whenPresent, R (*)() whenEmpty);
};
/* sealed public */ struct Result_Node_CompileError {
	/* <R> */R match(R (*)(Node) whenOk, R (*)(CompileError) whenErr);
	/* <R> */Result_R_CompileError mapValue(R (*)(Node) mapper);
	/* <R> */Result_R_CompileError flatMapValue(Result_R_CompileError (*)(Node) mapper);
	Option_CompileError findError();
	/* <R> */Result_Node_R mapErr(R (*)(CompileError) mapper);
	/* <R> */Result_Tuple_Node_R_CompileError and(Result_R_CompileError (*)() mapper);
};
/* public */ struct List__Rule {
	List__Rule add(Rule element);
	List__Rule addAll(List__Rule elements);
	Stream__Rule stream();
	Rule popFirst();
	int hasElements();
	Option_Rule apply(int index);
	int size();
	Rule last();
	Stream__Tuple_Integer_Rule streamWithIndices();
	Rule first();
	List__Rule sort(Comparator_Rule comparator);
};

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
auto __lambda0__(auto rangehead(value.length())).map(value){
	return RangeHead(value.length())).map(value.charAt(rangehead(value.length())).map(value);
}
auto __lambda1__(auto value){
	return value.charAt(value);
}
auto __lambda2__(auto headedstream(rangehead(value.length())).map(value){
	return HeadedStream(RangeHead(value.length())).map(value.charAt)(headedstream(rangehead(value.length())).map(value);
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
auto __lambda4__(auto rangehead(value.length())).map(value){
	return RangeHead(value.length())).map(value.charAt(rangehead(value.length())).map(value);
}
auto __lambda5__(auto value){
	return value.charAt(value);
}
auto __lambda6__(auto headedstream(rangehead(value.length())).map(value){
	return HeadedStream(RangeHead(value.length())).map(value.charAt)(headedstream(rangehead(value.length())).map(value);
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
auto __lambda23__(auto 1 + children.stream().map(compileerror){
	return 1 + children.stream().map(CompileError.depth)(1 + children.stream().map(compileerror);
}
auto __lambda24__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda25__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda26__(auto 1 + children.stream().map(compileerror){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(1 + children.stream().map(compileerror);
}
auto __lambda27__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda28__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda29__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(children.stream().map(compileerror);
}
auto __lambda30__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda31__(auto 1 + children.stream().map(compileerror){
	return 1 + children.stream().map(CompileError.depth)(1 + children.stream().map(compileerror);
}
auto __lambda32__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda33__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda34__(auto 1 + children.stream().map(compileerror){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(new Max())(1 + children.stream().map(compileerror);
}
auto __lambda35__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda36__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda37__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(children.stream().map(compileerror);
}
auto __lambda38__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda39__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda40__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())(children.stream().map(compileerror);
}
auto __lambda41__(auto 1 + children.stream().map(compileerror){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(new Max())
                    .orElse(1 + children.stream().map(compileerror);
}
auto __lambda42__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda43__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda44__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(children.stream().map(compileerror);
}
auto __lambda45__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda46__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda47__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())(children.stream().map(compileerror);
}
auto __lambda48__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())
                    .orElse(children.stream().map(compileerror);
}
auto __lambda49__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda50__(auto 1 + children.stream().map(compileerror){
	return 1 + children.stream().map(CompileError.depth)(1 + children.stream().map(compileerror);
}
auto __lambda51__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda52__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda53__(auto 1 + children.stream().map(compileerror){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(1 + children.stream().map(compileerror);
}
auto __lambda54__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda55__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda56__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(children.stream().map(compileerror);
}
auto __lambda57__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda58__(auto 1 + children.stream().map(compileerror){
	return 1 + children.stream().map(CompileError.depth)(1 + children.stream().map(compileerror);
}
auto __lambda59__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda60__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda61__(auto 1 + children.stream().map(compileerror){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(new Max())(1 + children.stream().map(compileerror);
}
auto __lambda62__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda63__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda64__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(children.stream().map(compileerror);
}
auto __lambda65__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda66__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda67__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())(children.stream().map(compileerror);
}
auto __lambda68__(auto 1 + children.stream().map(compileerror){
	return 1 + children.stream().map(CompileError.depth)
                    .collect(new Max())
                    .orElse(0)(1 + children.stream().map(compileerror);
}
auto __lambda69__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda70__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda71__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(children.stream().map(compileerror);
}
auto __lambda72__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda73__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda74__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())(children.stream().map(compileerror);
}
auto __lambda75__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())
                    .orElse(children.stream().map(compileerror);
}
auto __lambda76__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda77__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda78__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(children.stream().map(compileerror);
}
auto __lambda79__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda80__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)(children.stream().map(compileerror);
}
auto __lambda81__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())(children.stream().map(compileerror);
}
auto __lambda82__(auto children.stream().map(compileerror){
	return children.stream().map(CompileError.depth)
                    .collect(new Max())
                    .orElse(0)(children.stream().map(compileerror);
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
/* public */String display(){
	return format(0);
}
auto __lambda85__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda86__(auto comparator.comparingint(compileerror){
	return Comparator.comparingInt(CompileError.depth)(comparator.comparingint(compileerror);
}
auto __lambda87__(auto children.sort(comparator.comparingint(compileerror){
	return children.sort(Comparator.comparingInt(CompileError.depth))(children.sort(comparator.comparingint(compileerror);
}
auto __lambda88__(auto compileerror){
	return CompileError.depth(compileerror);
}
auto __lambda89__(auto comparator.comparingint(compileerror){
	return Comparator.comparingInt(CompileError.depth)(comparator.comparingint(compileerror);
}
auto __lambda90__(auto result){
	return "\n" + result;
}
auto __lambda91__(auto result){
	return "\n";
}
auto __lambda92__(auto result){
	return "\n" + result;
}
auto __lambda93__(auto result){
	return "\n";
}
auto __lambda94__(auto result){
	return "\n" + result;
}
auto __lambda95__(auto result){
	return "\n";
}
auto __lambda96__(auto result){
	return "\n" + result;
}
auto __lambda97__(auto result){
	return "\n";
}
auto __lambda98__(auto result){
	return "\n" + result;
}
auto __lambda99__(auto result){
	return "\n";
}
auto __lambda100__(auto result){
	return "\n" + result;
}
auto __lambda101__(auto result){
	return "\n";
}
auto __lambda102__(auto result){
	return "\n" + result;
}
auto __lambda103__(auto result){
	return "\n";
}
auto __lambda104__(auto result){
	return "\n" + result;
}
auto __lambda105__(auto result){
	return "\n";
}
auto __lambda106__(auto result){
	return "\n" + result;
}
auto __lambda107__(auto result){
	return "\n";
}
auto __lambda108__(auto result){
	return "\n" + result;
}
auto __lambda109__(auto result){
	return "\n";
}
auto __lambda110__(auto result){
	return "\n" + result;
}
auto __lambda111__(auto result){
	return "\n";
}
auto __lambda112__(auto result){
	return "\n" + result;
}
auto __lambda113__(auto result){
	return "\n";
}
auto __lambda114__(auto result){
	return "\n" + result;
}
auto __lambda115__(auto result){
	return "\n";
}
auto __lambda116__(auto result){
	return "\n" + result;
}
auto __lambda117__(auto result){
	return "\n";
}
auto __lambda118__(auto result){
	return "\n" + result;
}
auto __lambda119__(auto result){
	return "\n";
}
auto __lambda120__(auto result){
	return "\n" + result;
}
auto __lambda121__(auto result){
	return "\n";
}
auto __lambda122__(auto result){
	return "\n" + result;
}
auto __lambda123__(auto result){
	return "\n";
}
auto __lambda124__(auto result){
	return "\n" + result;
}
auto __lambda125__(auto result){
	return "\n";
}
auto __lambda126__(auto result){
	return "\n" + result;
}
auto __lambda127__(auto result){
	return "\n";
}
auto __lambda128__(auto result){
	return "\n" + result;
}
auto __lambda129__(auto result){
	return "\n";
}
auto __lambda130__(auto result){
	return "\n" + result;
}
auto __lambda131__(auto result){
	return "\n";
}
auto __lambda132__(auto result){
	return "\n" + result;
}
auto __lambda133__(auto result){
	return "\n";
}
auto __lambda134__(auto result){
	return "\n" + result;
}
auto __lambda135__(auto result){
	return "\n";
}
auto __lambda136__(auto result){
	return "\n" + result;
}
auto __lambda137__(auto result){
	return "\n";
}
auto __lambda138__(auto result){
	return "\n" + result;
}
auto __lambda139__(auto result){
	return "\n";
}
auto __lambda140__(auto result){
	return "\n" + result;
}
auto __lambda141__(auto result){
	return "\n";
}
auto __lambda142__(auto result){
	return "\n" + result;
}
auto __lambda143__(auto result){
	return "\n";
}
auto __lambda144__(auto result){
	return "\n" + result;
}
auto __lambda145__(auto result){
	return "\n";
}
auto __lambda146__(auto result){
	return "\n" + result;
}
auto __lambda147__(auto result){
	return "\n";
}
auto __lambda148__(auto result){
	return "\n" + result;
}
auto __lambda149__(auto result){
	return "\n";
}
auto __lambda150__(auto result){
	return "\n" + result;
}
auto __lambda151__(auto result){
	return "\n";
}
auto __lambda152__(auto result){
	return "\n" + result;
}
auto __lambda153__(auto result){
	return "\n";
}
auto __lambda154__(auto result){
	return "\n" + result;
}
auto __lambda155__(auto result){
	return "\n";
}
auto __lambda156__(auto result){
	return "\n" + result;
}
auto __lambda157__(auto result){
	return "\n";
}
auto __lambda158__(auto result){
	return "\n" + result;
}
auto __lambda159__(auto result){
	return "\n";
}
auto __lambda160__(auto result){
	return "\n" + result;
}
auto __lambda161__(auto result){
	return "\n";
}
auto __lambda162__(auto result){
	return "\n" + result;
}
auto __lambda163__(auto result){
	return "\n";
}
auto __lambda164__(auto result){
	return "\n" + result;
}
auto __lambda165__(auto result){
	return "\n";
}
auto __lambda166__(auto result){
	return "\n" + result;
}
auto __lambda167__(auto result){
	return "\n";
}
auto __lambda168__(auto result){
	return "\n" + result;
}
auto __lambda169__(auto result){
	return "\n";
}
auto __lambda170__(auto result){
	return "\n" + result;
}
auto __lambda171__(auto result){
	return "\n";
}
auto __lambda172__(auto result){
	return "\n" + result;
}
auto __lambda173__(auto result){
	return "\n";
}
auto __lambda174__(auto result){
	return "\n" + result;
}
auto __lambda175__(auto result){
	return "\n";
}
auto __lambda176__(auto result){
	return "\n" + result;
}
auto __lambda177__(auto result){
	return "\n";
}
auto __lambda178__(auto result){
	return "\n" + result;
}
auto __lambda179__(auto result){
	return "\n";
}
auto __lambda180__(auto result){
	return "\n" + result;
}
auto __lambda181__(auto result){
	return "\n";
}
auto __lambda182__(auto result){
	return "\n" + result;
}
auto __lambda183__(auto result){
	return "\n";
}
auto __lambda184__(auto result){
	return "\n" + result;
}
auto __lambda185__(auto result){
	return "\n";
}
auto __lambda186__(auto result){
	return "\n" + result;
}
auto __lambda187__(auto result){
	return "\n";
}
auto __lambda188__(auto result){
	return "\n" + result;
}
auto __lambda189__(auto result){
	return "\n";
}
auto __lambda190__(auto result){
	return "\n" + result;
}
auto __lambda191__(auto result){
	return "\n";
}
auto __lambda192__(auto result){
	return "\n" + result;
}
auto __lambda193__(auto result){
	return "\n";
}
auto __lambda194__(auto result){
	return "\n" + result;
}
auto __lambda195__(auto result){
	return "\n";
}
auto __lambda196__(auto result){
	return "\n" + result;
}
auto __lambda197__(auto result){
	return "\n";
}
auto __lambda198__(auto result){
	return "\n" + result;
}
auto __lambda199__(auto result){
	return "\n";
}
auto __lambda200__(auto result){
	return "\n" + result;
}
auto __lambda201__(auto result){
	return "\n";
}
auto __lambda202__(auto result){
	return "\n" + result;
}
auto __lambda203__(auto result){
	return "\n";
}
auto __lambda204__(auto result){
	return "\n" + result;
}
auto __lambda205__(auto result){
	return "\n";
}
auto __lambda206__(auto result){
	return "\n" + result;
}
auto __lambda207__(auto result){
	return "\n";
}
auto __lambda208__(auto result){
	return "\n" + result;
}
auto __lambda209__(auto result){
	return "\n";
}
auto __lambda210__(auto result){
	return "\n" + result;
}
auto __lambda211__(auto result){
	return "\n";
}
auto __lambda212__(auto result){
	return "\n" + result;
}
auto __lambda213__(auto result){
	return "\n";
}
auto __lambda214__(auto result){
	return "\n" + result;
}
auto __lambda215__(auto result){
	return "\n";
}
auto __lambda216__(auto result){
	return "\n" + result;
}
auto __lambda217__(auto result){
	return "\n";
}
auto __lambda218__(auto result){
	return "\n" + result;
}
auto __lambda219__(auto result){
	return "\n";
}
auto __lambda220__(auto result){
	return "\n" + result;
}
auto __lambda221__(auto result){
	return "\n";
}
auto __lambda222__(auto result){
	return "\n" + result;
}
auto __lambda223__(auto result){
	return "\n";
}
auto __lambda224__(auto result){
	return "\n" + result;
}
auto __lambda225__(auto result){
	return "\n";
}
auto __lambda226__(auto result){
	return "\n" + result;
}
auto __lambda227__(auto result){
	return "\n";
}
auto __lambda228__(auto result){
	return "\n" + result;
}
auto __lambda229__(auto result){
	return "\n";
}
auto __lambda230__(auto result){
	return "\n" + result;
}
auto __lambda231__(auto result){
	return "\n";
}
auto __lambda232__(auto result){
	return "\n" + result;
}
auto __lambda233__(auto result){
	return "\n";
}
auto __lambda234__(auto result){
	return "\n" + result;
}
auto __lambda235__(auto result){
	return "\n";
}
auto __lambda236__(auto result){
	return "\n" + result;
}
auto __lambda237__(auto result){
	return "\n";
}
auto __lambda238__(auto result){
	return "\n" + result;
}
auto __lambda239__(auto result){
	return "\n";
}
auto __lambda240__(auto result){
	return "\n" + result;
}
auto __lambda241__(auto result){
	return "\n";
}
auto __lambda242__(auto result){
	return "\n" + result;
}
auto __lambda243__(auto result){
	return "\n";
}
auto __lambda244__(auto result){
	return "\n" + result;
}
auto __lambda245__(auto result){
	return "\n";
}
auto __lambda246__(auto result){
	return "\n" + result;
}
auto __lambda247__(auto result){
	return "\n";
}
auto __lambda248__(auto result){
	return "\n" + result;
}
auto __lambda249__(auto result){
	return "\n";
}
auto __lambda250__(auto result){
	return "\n" + result;
}
auto __lambda251__(auto result){
	return "\n";
}
auto __lambda252__(auto result){
	return "\n" + result;
}
auto __lambda253__(auto result){
	return "\n";
}
auto __lambda254__(auto result){
	return "\n" + result;
}
auto __lambda255__(auto result){
	return "\n";
}
auto __lambda256__(auto result){
	return "\n" + result;
}
auto __lambda257__(auto result){
	return "\n";
}
auto __lambda258__(auto result){
	return "\n" + result;
}
auto __lambda259__(auto result){
	return "\n";
}
auto __lambda260__(auto result){
	return "\n" + result;
}
auto __lambda261__(auto result){
	return "\n";
}
auto __lambda262__(auto result){
	return "\n" + result;
}
auto __lambda263__(auto result){
	return "\n";
}
auto __lambda264__(auto result){
	return "\n" + result;
}
auto __lambda265__(auto result){
	return "\n";
}
auto __lambda266__(auto result){
	return "\n" + result;
}
auto __lambda267__(auto result){
	return "\n";
}
auto __lambda268__(auto result){
	return "\n" + result;
}
auto __lambda269__(auto result){
	return "\n";
}
auto __lambda270__(auto result){
	return "\n" + result;
}
auto __lambda271__(auto result){
	return "\n";
}
auto __lambda272__(auto result){
	return "\n" + result;
}
auto __lambda273__(auto result){
	return "\n";
}
auto __lambda274__(auto result){
	return "\n" + result;
}
auto __lambda275__(auto result){
	return "\n";
}
auto __lambda276__(auto result){
	return "\n" + result;
}
auto __lambda277__(auto result){
	return "\n";
}
auto __lambda278__(auto result){
	return "\n" + result;
}
auto __lambda279__(auto result){
	return "\n";
}
auto __lambda280__(auto result){
	return "\n" + result;
}
auto __lambda281__(auto result){
	return "\n";
}
auto __lambda282__(auto result){
	return "\n" + result;
}
auto __lambda283__(auto result){
	return "\n";
}
auto __lambda284__(auto result){
	return "\n" + result;
}
auto __lambda285__(auto result){
	return "\n";
}
auto __lambda286__(auto result){
	return "\n" + result;
}
auto __lambda287__(auto result){
	return "\n";
}
auto __lambda288__(auto result){
	return "\n" + result;
}
auto __lambda289__(auto result){
	return "\n";
}
auto __lambda290__(auto result){
	return "\n" + result;
}
auto __lambda291__(auto result){
	return "\n";
}
auto __lambda292__(auto result){
	return "\n" + result;
}
auto __lambda293__(auto result){
	return "\n";
}
auto __lambda294__(auto result){
	return "\n" + result;
}
auto __lambda295__(auto result){
	return "\n";
}
auto __lambda296__(auto result){
	return "\n" + result;
}
auto __lambda297__(auto result){
	return "\n";
}
auto __lambda298__(auto result){
	return "\n" + result;
}
auto __lambda299__(auto result){
	return "\n";
}
auto __lambda300__(auto result){
	return "\n" + result;
}
auto __lambda301__(auto result){
	return "\n";
}
auto __lambda302__(auto result){
	return "\n" + result;
}
auto __lambda303__(auto result){
	return "\n";
}
auto __lambda304__(auto result){
	return "\n" + result;
}
auto __lambda305__(auto result){
	return "\n";
}
auto __lambda306__(auto result){
	return "\n" + result;
}
auto __lambda307__(auto result){
	return "\n";
}
auto __lambda308__(auto result){
	return "\n" + result;
}
auto __lambda309__(auto result){
	return "\n";
}
auto __lambda310__(auto result){
	return "\n" + result;
}
auto __lambda311__(auto result){
	return "\n";
}
auto __lambda312__(auto result){
	return "\n" + result;
}
auto __lambda313__(auto result){
	return "\n";
}
auto __lambda314__(auto result){
	return "\n" + result;
}
auto __lambda315__(auto result){
	return "\n";
}
auto __lambda316__(auto result){
	return "\n" + result;
}
auto __lambda317__(auto result){
	return "\n";
}
auto __lambda318__(auto result){
	return "\n" + result;
}
auto __lambda319__(auto result){
	return "\n";
}
auto __lambda320__(auto result){
	return "\n" + result;
}
auto __lambda321__(auto result){
	return "\n";
}
auto __lambda322__(auto result){
	return "\n" + result;
}
auto __lambda323__(auto result){
	return "\n";
}
auto __lambda324__(auto result){
	return "\n" + result;
}
auto __lambda325__(auto result){
	return "\n";
}
auto __lambda326__(auto result){
	return "\n" + result;
}
auto __lambda327__(auto result){
	return "\n";
}
auto __lambda328__(auto result){
	return "\n" + result;
}
auto __lambda329__(auto result){
	return "\n";
}
auto __lambda330__(auto result){
	return "\n" + result;
}
auto __lambda331__(auto result){
	return "\n";
}
auto __lambda332__(auto result){
	return "\n" + result;
}
auto __lambda333__(auto result){
	return "\n";
}
auto __lambda334__(auto result){
	return "\n" + result;
}
auto __lambda335__(auto result){
	return "\n";
}
auto __lambda336__(auto result){
	return "\n" + result;
}
auto __lambda337__(auto result){
	return "\n";
}
auto __lambda338__(auto result){
	return "\n" + result;
}
auto __lambda339__(auto result){
	return "\n";
}
auto __lambda340__(auto result){
	return "\n" + result;
}
auto __lambda341__(auto result){
	return "\n";
}
auto __lambda342__(auto result){
	return "\n" + result;
}
auto __lambda343__(auto result){
	return "\n";
}
auto __lambda344__(auto result){
	return "\n" + result;
}
auto __lambda345__(auto result){
	return "\n";
}
auto __lambda346__(auto result){
	return "\n" + result;
}
auto __lambda347__(auto result){
	return "\n";
}
auto __lambda348__(auto result){
	return "\n" + result;
}
auto __lambda349__(auto result){
	return "\n";
}
auto __lambda350__(auto result){
	return "\n" + result;
}
auto __lambda351__(auto result){
	return "\n";
}
auto __lambda352__(auto result){
	return "\n" + result;
}
auto __lambda353__(auto result){
	return "\n";
}
auto __lambda354__(auto result){
	return "\n" + result;
}
auto __lambda355__(auto result){
	return "\n";
}
auto __lambda356__(auto result){
	return "\n" + result;
}
auto __lambda357__(auto result){
	return "\n";
}
auto __lambda358__(auto result){
	return "\n" + result;
}
auto __lambda359__(auto result){
	return "\n";
}
auto __lambda360__(auto result){
	return "\n" + result;
}
auto __lambda361__(auto result){
	return "\n";
}
auto __lambda362__(auto result){
	return "\n" + result;
}
auto __lambda363__(auto result){
	return "\n";
}
auto __lambda364__(auto result){
	return "\n" + result;
}
auto __lambda365__(auto result){
	return "\n";
}
auto __lambda366__(auto result){
	return "\n" + result;
}
auto __lambda367__(auto result){
	return "\n";
}
auto __lambda368__(auto result){
	return "\n" + result;
}
auto __lambda369__(auto result){
	return "\n";
}
auto __lambda370__(auto result){
	return "\n" + result;
}
auto __lambda371__(auto result){
	return "\n";
}
auto __lambda372__(auto result){
	return "\n" + result;
}
auto __lambda373__(auto result){
	return "\n";
}
auto __lambda374__(auto result){
	return "\n" + result;
}
auto __lambda375__(auto result){
	return "\n";
}
auto __lambda376__(auto result){
	return "\n" + result;
}
auto __lambda377__(auto result){
	return "\n";
}
auto __lambda378__(auto result){
	return "\n" + result;
}
auto __lambda379__(auto result){
	return "\n";
}
auto __lambda380__(auto result){
	return "\n" + result;
}
auto __lambda381__(auto result){
	return "\n";
}
auto __lambda382__(auto result){
	return "\n" + result;
}
auto __lambda383__(auto result){
	return "\n";
}
auto __lambda384__(auto result){
	return "\n" + result;
}
auto __lambda385__(auto result){
	return "\n";
}
auto __lambda386__(auto result){
	return "\n" + result;
}
auto __lambda387__(auto result){
	return "\n";
}
auto __lambda388__(auto result){
	return "\n" + result;
}
auto __lambda389__(auto result){
	return "\n";
}
auto __lambda390__(auto result){
	return "\n" + result;
}
auto __lambda391__(auto result){
	return "\n";
}
auto __lambda392__(auto result){
	return "\n" + result;
}
auto __lambda393__(auto result){
	return "\n";
}
auto __lambda394__(auto result){
	return "\n" + result;
}
auto __lambda395__(auto result){
	return "\n";
}
auto __lambda396__(auto result){
	return "\n" + result;
}
auto __lambda397__(auto result){
	return "\n";
}
auto __lambda398__(auto result){
	return "\n" + result;
}
auto __lambda399__(auto result){
	return "\n";
}
auto __lambda400__(auto result){
	return "\n" + result;
}
auto __lambda401__(auto result){
	return "\n";
}
auto __lambda402__(auto result){
	return "\n" + result;
}
auto __lambda403__(auto result){
	return "\n";
}
auto __lambda404__(auto result){
	return "\n" + result;
}
auto __lambda405__(auto result){
	return "\n";
}
auto __lambda406__(auto result){
	return "\n" + result;
}
auto __lambda407__(auto result){
	return "\n";
}
auto __lambda408__(auto result){
	return "\n" + result;
}
auto __lambda409__(auto result){
	return "\n";
}
auto __lambda410__(auto result){
	return "\n" + result;
}
auto __lambda411__(auto result){
	return "\n";
}
auto __lambda412__(auto result){
	return "\n" + result;
}
auto __lambda413__(auto result){
	return "\n";
}
auto __lambda414__(auto result){
	return "\n" + result;
}
auto __lambda415__(auto result){
	return "\n";
}
auto __lambda416__(auto result){
	return "\n" + result;
}
auto __lambda417__(auto result){
	return "\n";
}
auto __lambda418__(auto result){
	return "\n" + result;
}
auto __lambda419__(auto result){
	return "\n";
}
auto __lambda420__(auto result){
	return "\n" + result;
}
auto __lambda421__(auto result){
	return "\n";
}
auto __lambda422__(auto result){
	return "\n" + result;
}
auto __lambda423__(auto result){
	return "\n";
}
auto __lambda424__(auto result){
	return "\n" + result;
}
auto __lambda425__(auto result){
	return "\n";
}
auto __lambda426__(auto result){
	return "\n" + result;
}
auto __lambda427__(auto result){
	return "\n";
}
auto __lambda428__(auto result){
	return "\n" + result;
}
auto __lambda429__(auto result){
	return "\n";
}
auto __lambda430__(auto result){
	return "\n" + result;
}
auto __lambda431__(auto result){
	return "\n";
}
auto __lambda432__(auto result){
	return "\n" + result;
}
auto __lambda433__(auto result){
	return "\n";
}
auto __lambda434__(auto result){
	return "\n" + result;
}
auto __lambda435__(auto result){
	return "\n";
}
auto __lambda436__(auto result){
	return "\n" + result;
}
auto __lambda437__(auto result){
	return "\n";
}
auto __lambda438__(auto result){
	return "\n" + result;
}
auto __lambda439__(auto result){
	return "\n";
}
auto __lambda440__(auto result){
	return "\n" + result;
}
auto __lambda441__(auto result){
	return "\n";
}
auto __lambda442__(auto result){
	return "\n" + result;
}
auto __lambda443__(auto result){
	return "\n";
}
auto __lambda444__(auto result){
	return "\n" + result;
}
auto __lambda445__(auto result){
	return "\n";
}
auto __lambda446__(auto result){
	return "\n" + result;
}
auto __lambda447__(auto result){
	return "\n";
}
auto __lambda448__(auto result){
	return "\n" + result;
}
auto __lambda449__(auto result){
	return "\n";
}
auto __lambda450__(auto result){
	return "\n" + result;
}
auto __lambda451__(auto result){
	return "\n";
}
auto __lambda452__(auto result){
	return "\n" + result;
}
auto __lambda453__(auto result){
	return "\n";
}
auto __lambda454__(auto result){
	return "\n" + result;
}
auto __lambda455__(auto result){
	return "\n";
}
auto __lambda456__(auto result){
	return "\n" + result;
}
auto __lambda457__(auto result){
	return "\n";
}
auto __lambda458__(auto result){
	return "\n" + result;
}
auto __lambda459__(auto result){
	return "\n";
}
auto __lambda460__(auto result){
	return "\n" + result;
}
auto __lambda461__(auto result){
	return "\n";
}
auto __lambda462__(auto result){
	return "\n" + result;
}
auto __lambda463__(auto result){
	return "\n";
}
auto __lambda464__(auto result){
	return "\n" + result;
}
auto __lambda465__(auto result){
	return "\n";
}
auto __lambda466__(auto result){
	return "\n" + result;
}
auto __lambda467__(auto result){
	return "\n";
}
auto __lambda468__(auto result){
	return "\n" + result;
}
auto __lambda469__(auto result){
	return "\n";
}
auto __lambda470__(auto result){
	return "\n" + result;
}
auto __lambda471__(auto result){
	return "\n";
}
auto __lambda472__(auto result){
	return "\n" + result;
}
auto __lambda473__(auto result){
	return "\n";
}
auto __lambda474__(auto result){
	return "\n" + result;
}
auto __lambda475__(auto result){
	return "\n";
}
auto __lambda476__(auto result){
	return "\n" + result;
}
auto __lambda477__(auto result){
	return "\n";
}
auto __lambda478__(auto result){
	return "\n" + result;
}
auto __lambda479__(auto result){
	return "\n";
}
auto __lambda480__(auto result){
	return "\n" + result;
}
auto __lambda481__(auto result){
	return "\n";
}
auto __lambda482__(auto result){
	return "\n" + result;
}
auto __lambda483__(auto result){
	return "\n";
}
auto __lambda484__(auto result){
	return "\n" + result;
}
auto __lambda485__(auto result){
	return "\n";
}
auto __lambda486__(auto result){
	return "\n" + result;
}
auto __lambda487__(auto result){
	return "\n";
}
auto __lambda488__(auto result){
	return "\n" + result;
}
auto __lambda489__(auto result){
	return "\n";
}
auto __lambda490__(auto result){
	return "\n" + result;
}
auto __lambda491__(auto result){
	return "\n";
}
auto __lambda492__(auto result){
	return "\n" + result;
}
auto __lambda493__(auto result){
	return "\n";
}
auto __lambda494__(auto result){
	return "\n" + result;
}
auto __lambda495__(auto result){
	return "\n";
}
auto __lambda496__(auto result){
	return "\n" + result;
}
auto __lambda497__(auto result){
	return "\n";
}
auto __lambda498__(auto result){
	return "\n" + result;
}
auto __lambda499__(auto result){
	return "\n";
}
auto __lambda500__(auto result){
	return "\n" + result;
}
auto __lambda501__(auto result){
	return "\n";
}
auto __lambda502__(auto result){
	return "\n" + result;
}
auto __lambda503__(auto result){
	return "\n";
}
auto __lambda504__(auto result){
	return "\n" + result;
}
auto __lambda505__(auto result){
	return "\n";
}
auto __lambda506__(auto result){
	return "\n" + result;
}
auto __lambda507__(auto result){
	return "\n";
}
auto __lambda508__(auto result){
	return "\n" + result;
}
auto __lambda509__(auto result){
	return "\n";
}
auto __lambda510__(auto result){
	return "\n" + result;
}
auto __lambda511__(auto result){
	return "\n";
}
auto __lambda512__(auto result){
	return "\n" + result;
}
auto __lambda513__(auto result){
	return "\n";
}
auto __lambda514__(auto result){
	return "\n" + result;
}
auto __lambda515__(auto result){
	return "\n";
}
auto __lambda516__(auto result){
	return "\n" + result;
}
auto __lambda517__(auto result){
	return "\n";
}
auto __lambda518__(auto result){
	return "\n" + result;
}
auto __lambda519__(auto result){
	return "\n";
}
auto __lambda520__(auto result){
	return "\n" + result;
}
auto __lambda521__(auto result){
	return "\n";
}
auto __lambda522__(auto result){
	return "\n" + result;
}
auto __lambda523__(auto result){
	return "\n";
}
auto __lambda524__(auto result){
	return "\n" + result;
}
auto __lambda525__(auto result){
	return "\n";
}
auto __lambda526__(auto result){
	return "\n" + result;
}
auto __lambda527__(auto result){
	return "\n";
}
auto __lambda528__(auto result){
	return "\n" + result;
}
auto __lambda529__(auto result){
	return "\n";
}
auto __lambda530__(auto result){
	return "\n" + result;
}
auto __lambda531__(auto result){
	return "\n";
}
auto __lambda532__(auto result){
	return "\n" + result;
}
auto __lambda533__(auto result){
	return "\n";
}
auto __lambda534__(auto result){
	return "\n" + result;
}
auto __lambda535__(auto result){
	return "\n";
}
auto __lambda536__(auto result){
	return "\n" + result;
}
auto __lambda537__(auto result){
	return "\n";
}
auto __lambda538__(auto result){
	return "\n" + result;
}
auto __lambda539__(auto result){
	return "\n";
}
auto __lambda540__(auto result){
	return "\n" + result;
}
auto __lambda541__(auto result){
	return "\n";
}
auto __lambda542__(auto result){
	return "\n" + result;
}
auto __lambda543__(auto result){
	return "\n";
}
auto __lambda544__(auto result){
	return "\n" + result;
}
auto __lambda545__(auto result){
	return "\n";
}
auto __lambda546__(auto result){
	return "\n" + result;
}
auto __lambda547__(auto result){
	return "\n";
}
auto __lambda548__(auto result){
	return "\n" + result;
}
auto __lambda549__(auto result){
	return "\n";
}
auto __lambda550__(auto result){
	return "\n" + result;
}
auto __lambda551__(auto result){
	return "\n";
}
auto __lambda552__(auto result){
	return "\n" + result;
}
auto __lambda553__(auto result){
	return "\n";
}
auto __lambda554__(auto result){
	return "\n" + result;
}
auto __lambda555__(auto result){
	return "\n";
}
auto __lambda556__(auto result){
	return "\n" + result;
}
auto __lambda557__(auto result){
	return "\n";
}
auto __lambda558__(auto result){
	return "\n" + result;
}
auto __lambda559__(auto result){
	return "\n";
}
auto __lambda560__(auto result){
	return "\n" + result;
}
auto __lambda561__(auto result){
	return "\n";
}
auto __lambda562__(auto result){
	return "\n" + result;
}
auto __lambda563__(auto result){
	return "\n";
}
auto __lambda564__(auto result){
	return "\n" + result;
}
auto __lambda565__(auto result){
	return "\n";
}
auto __lambda566__(auto result){
	return "\n" + result;
}
auto __lambda567__(auto result){
	return "\n";
}
auto __lambda568__(auto result){
	return "\n" + result;
}
auto __lambda569__(auto result){
	return "\n";
}
auto __lambda570__(auto result){
	return "\n" + result;
}
auto __lambda571__(auto result){
	return "\n";
}
auto __lambda572__(auto result){
	return "\n" + result;
}
auto __lambda573__(auto result){
	return "\n";
}
auto __lambda574__(auto result){
	return "\n" + result;
}
auto __lambda575__(auto result){
	return "\n";
}
auto __lambda576__(auto result){
	return "\n" + result;
}
auto __lambda577__(auto result){
	return "\n";
}
auto __lambda578__(auto result){
	return "\n" + result;
}
auto __lambda579__(auto result){
	return "\n";
}
auto __lambda580__(auto result){
	return "\n" + result;
}
auto __lambda581__(auto result){
	return "\n";
}
auto __lambda582__(auto result){
	return "\n" + result;
}
auto __lambda583__(auto result){
	return "\n";
}
auto __lambda584__(auto result){
	return "\n" + result;
}
auto __lambda585__(auto result){
	return "\n";
}
auto __lambda586__(auto result){
	return "\n" + result;
}
auto __lambda587__(auto result){
	return "\n";
}
auto __lambda588__(auto result){
	return "\n" + result;
}
auto __lambda589__(auto result){
	return "\n";
}
auto __lambda590__(auto result){
	return "\n" + result;
}
auto __lambda591__(auto result){
	return "\n";
}
auto __lambda592__(auto result){
	return "\n" + result;
}
auto __lambda593__(auto result){
	return "\n";
}
auto __lambda594__(auto result){
	return "\n" + result;
}
auto __lambda595__(auto result){
	return "\n";
}
auto __lambda596__(auto result){
	return "\n" + result;
}
auto __lambda597__(auto result){
	return "\n";
}
auto __lambda598__(auto result){
	return "\n" + result;
}
auto __lambda599__(auto result){
	return "\n";
}
auto __lambda600__(auto result){
	return "\n" + result;
}
auto __lambda601__(auto result){
	return "\n";
}
auto __lambda602__(auto result){
	return "\n" + result;
}
auto __lambda603__(auto result){
	return "\n";
}
auto __lambda604__(auto result){
	return "\n" + result;
}
auto __lambda605__(auto result){
	return "\n";
}
auto __lambda606__(auto result){
	return "\n" + result;
}
auto __lambda607__(auto result){
	return "\n";
}
auto __lambda608__(auto result){
	return "\n" + result;
}
auto __lambda609__(auto result){
	return "\n";
}
auto __lambda610__(auto result){
	return "\n" + result;
}
auto __lambda611__(auto result){
	return "\n";
}
auto __lambda612__(auto result){
	return "\n" + result;
}
auto __lambda613__(auto result){
	return "\n";
}
auto __lambda614__(auto result){
	return "\n" + result;
}
auto __lambda615__(auto result){
	return "\n";
}
auto __lambda616__(auto result){
	return "\n" + result;
}
auto __lambda617__(auto result){
	return "\n";
}
auto __lambda618__(auto result){
	return "\n" + result;
}
auto __lambda619__(auto result){
	return "\n";
}
auto __lambda620__(auto result){
	return "\n" + result;
}
auto __lambda621__(auto result){
	return "\n";
}
auto __lambda622__(auto result){
	return "\n" + result;
}
auto __lambda623__(auto result){
	return "\n";
}
auto __lambda624__(auto result){
	return "\n" + result;
}
auto __lambda625__(auto result){
	return "\n";
}
auto __lambda626__(auto result){
	return "\n" + result;
}
auto __lambda627__(auto result){
	return "\n";
}
auto __lambda628__(auto result){
	return "\n" + result;
}
auto __lambda629__(auto result){
	return "\n";
}
auto __lambda630__(auto result){
	return "\n" + result;
}
auto __lambda631__(auto result){
	return "\n";
}
auto __lambda632__(auto result){
	return "\n" + result;
}
auto __lambda633__(auto result){
	return "\n";
}
auto __lambda634__(auto result){
	return "\n" + result;
}
auto __lambda635__(auto result){
	return "\n";
}
auto __lambda636__(auto result){
	return "\n" + result;
}
auto __lambda637__(auto result){
	return "\n";
}
auto __lambda638__(auto result){
	return "\n" + result;
}
auto __lambda639__(auto result){
	return "\n";
}
auto __lambda640__(auto result){
	return "\n" + result;
}
auto __lambda641__(auto result){
	return "\n";
}
auto __lambda642__(auto result){
	return "\n" + result;
}
auto __lambda643__(auto result){
	return "\n";
}
auto __lambda644__(auto result){
	return "\n" + result;
}
auto __lambda645__(auto result){
	return "\n";
}
auto __lambda646__(auto result){
	return "\n" + result;
}
auto __lambda647__(auto result){
	return "\n";
}
auto __lambda648__(auto result){
	return "\n" + result;
}
auto __lambda649__(auto result){
	return "\n";
}
auto __lambda650__(auto result){
	return "\n" + result;
}
auto __lambda651__(auto result){
	return "\n";
}
auto __lambda652__(auto result){
	return "\n" + result;
}
auto __lambda653__(auto result){
	return "\n";
}
auto __lambda654__(auto result){
	return "\n" + result;
}
auto __lambda655__(auto result){
	return "\n";
}
auto __lambda656__(auto result){
	return "\n" + result;
}
auto __lambda657__(auto result){
	return "\n";
}
auto __lambda658__(auto result){
	return "\n" + result;
}
auto __lambda659__(auto result){
	return "\n";
}
auto __lambda660__(auto result){
	return "\n" + result;
}
auto __lambda661__(auto result){
	return "\n";
}
auto __lambda662__(auto result){
	return "\n" + result;
}
auto __lambda663__(auto result){
	return "\n";
}
auto __lambda664__(auto result){
	return "\n" + result;
}
auto __lambda665__(auto result){
	return "\n";
}
auto __lambda666__(auto result){
	return "\n" + result;
}
auto __lambda667__(auto result){
	return "\n";
}
auto __lambda668__(auto result){
	return "\n" + result;
}
auto __lambda669__(auto result){
	return "\n";
}
auto __lambda670__(auto result){
	return "\n" + result;
}
auto __lambda671__(auto result){
	return "\n";
}
auto __lambda672__(auto result){
	return "\n" + result;
}
auto __lambda673__(auto result){
	return "\n";
}
auto __lambda674__(auto result){
	return "\n" + result;
}
auto __lambda675__(auto result){
	return "\n";
}
auto __lambda676__(auto result){
	return "\n" + result;
}
auto __lambda677__(auto result){
	return "\n";
}
auto __lambda678__(auto result){
	return "\n" + result;
}
auto __lambda679__(auto result){
	return "\n";
}
auto __lambda680__(auto result){
	return "\n" + result;
}
auto __lambda681__(auto result){
	return "\n";
}
auto __lambda682__(auto result){
	return "\n" + result;
}
auto __lambda683__(auto result){
	return "\n";
}
auto __lambda684__(auto result){
	return "\n" + result;
}
auto __lambda685__(auto result){
	return "\n";
}
auto __lambda686__(auto result){
	return "\n" + result;
}
auto __lambda687__(auto result){
	return "\n";
}
auto __lambda688__(auto result){
	return "\n" + result;
}
auto __lambda689__(auto result){
	return "\n";
}
auto __lambda690__(auto result){
	return "\n" + result;
}
auto __lambda691__(auto result){
	return "\n";
}
auto __lambda692__(auto result){
	return "\n" + result;
}
auto __lambda693__(auto result){
	return "\n";
}
auto __lambda694__(auto result){
	return "\n" + result;
}
auto __lambda695__(auto result){
	return "\n";
}
auto __lambda696__(auto result){
	return "\n" + result;
}
auto __lambda697__(auto result){
	return "\n";
}
auto __lambda698__(auto result){
	return "\n" + result;
}
auto __lambda699__(auto result){
	return "\n";
}
auto __lambda700__(auto result){
	return "\n" + result;
}
auto __lambda701__(auto result){
	return "\n";
}
auto __lambda702__(auto result){
	return "\n" + result;
}
auto __lambda703__(auto result){
	return "\n";
}
auto __lambda704__(auto result){
	return "\n" + result;
}
auto __lambda705__(auto result){
	return "\n";
}
auto __lambda706__(auto result){
	return "\n" + result;
}
auto __lambda707__(auto result){
	return "\n";
}
auto __lambda708__(auto result){
	return "\n" + result;
}
auto __lambda709__(auto result){
	return "\n";
}
auto __lambda710__(auto result){
	return "\n" + result;
}
auto __lambda711__(auto result){
	return "\n";
}
auto __lambda712__(auto result){
	return "\n" + result;
}
auto __lambda713__(auto result){
	return "\n";
}
auto __lambda714__(auto result){
	return "\n" + result;
}
auto __lambda715__(auto result){
	return "\n";
}
auto __lambda716__(auto result){
	return "\n" + result;
}
auto __lambda717__(auto result){
	return "\n";
}
auto __lambda718__(auto result){
	return "\n" + result;
}
auto __lambda719__(auto result){
	return "\n";
}
auto __lambda720__(auto result){
	return "\n" + result;
}
auto __lambda721__(auto result){
	return "\n";
}
auto __lambda722__(auto result){
	return "\n" + result;
}
auto __lambda723__(auto result){
	return "\n";
}
auto __lambda724__(auto result){
	return "\n" + result;
}
auto __lambda725__(auto result){
	return "\n";
}
auto __lambda726__(auto result){
	return "\n" + result;
}
auto __lambda727__(auto result){
	return "\n";
}
/* private */String format(int depth){
	List__CompileError sorted = children.sort(Comparator.comparingInt(__lambda85__));
	String joined = sorted.streamWithIndices().map(compileError -> "\t".repeat(depth) + compileError.left + ") " + compileError.right.format(depth + 1)).map(__lambda90__).collect(Joiner()).orElse("");
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
auto __lambda728__(auto ok){
	return Ok.new(ok);
}
auto __lambda729__(auto ){
	return Err(errors);
}
auto __lambda730__(auto maybevalue.<result<string, list_<compileerror>>>match(ok){
	return maybeValue.<Result<String, List_<CompileError>>>match(Ok.new, () -> new Err<>(errors))(maybevalue.<result<string, list_<compileerror>>>match(ok);
}
auto __lambda731__(auto maybevalue.<result<string, list_<compileerror>>>match(ok){
	return maybeValue.<Result<String, List_<CompileError>>>match(Ok.new,(maybevalue.<result<string, list_<compileerror>>>match(ok);
}
auto __lambda732__(auto maybevalue.<result<string, list_<compileerror>>>match(ok){
	return maybeValue.<Result<String, List_<CompileError>>>match(Ok.new, ()(maybevalue.<result<string, list_<compileerror>>>match(ok);
}
auto __lambda733__(auto ok){
	return Ok.new(ok);
}
auto __lambda734__(auto ){
	return Err(errors);
}
/* public */Result_String_List__CompileError toResult(){
	return maybeValue.<Result<String, List_<CompileError>>>match(__lambda728__, __lambda729__);
}
auto __lambda735__(auto inner){
	return Math.max(inner, element);
}
auto __lambda736__(auto inner){
	return Math.max;
}
auto __lambda737__(auto inner){
	return Math;
}
auto __lambda738__(auto inner){
	return Math;
}
auto __lambda739__(auto inner){
	return Math.max(inner, element);
}
auto __lambda740__(auto inner){
	return Math.max;
}
auto __lambda741__(auto inner){
	return Math;
}
auto __lambda742__(auto inner){
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
/* public static <K, V> */Map__K_V empty(){
	return JavaMap();
}
auto __lambda743__(auto throwable){
	return Throwable.printStackTrace(throwable);
}
auto __lambda744__(auto input){
	return runWithInput(source, input);
}
auto __lambda745__(auto input){
	return runWithInput;
}
auto __lambda746__(auto some){
	return Some.new(some);
}
auto __lambda747__(auto files.readstring(source).match(input -> runwithinput(source, input), some){
	return Files.readString(source).match(input -> runWithInput(source, input), Some.new)(files.readstring(source).match(input -> runwithinput(source, input), some);
}
auto __lambda748__(auto files.readstring(source).match(input -> runwithinput(source, input), some){
	return Files.readString(source).match(input -> runWithInput(source, input), Some.new)
                .ifPresent(files.readstring(source).match(input -> runwithinput(source, input), some);
}
/* public static */void main(String* args){
	Path source = Paths.get(".", "src", "java", "magma", "Main.java");
	Files.readString(source).match(__lambda744__, __lambda746__).ifPresent(__lambda743__);
}
auto __lambda749__(auto value){
	return value;
}
auto __lambda750__(auto err){
	return 
	System.err.println(err.display());
	return "";;
}
auto __lambda751__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda752__(auto value){
	return value;
}
auto __lambda753__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda754__(auto value){
	return value;
}
auto __lambda755__(auto value){
	return value;
}
auto __lambda756__(auto err){
	return 
	System.err.println(err.display());
	return "";;
}
auto __lambda757__(auto value){
	return value;
}
auto __lambda758__(auto err){
	return 
	System.err.println(err.display());
	return "";;
}
auto __lambda759__(auto value){
	return value;
}
auto __lambda760__(auto err){
	return 
	System.err.println(err.display());
	return "";;
}
auto __lambda761__(auto value){
	return value + "int main(){\n\t__main__();\n\treturn 0;\n}\n";
}
auto __lambda762__(auto value){
	return value;
}
/* private static */Option_IOException runWithInput(Path source, String input){
	String output = compile(input).mapValue(__lambda751__).match(__lambda749__, __lambda750__);
	Path target = source.resolveSibling("main.c");
	return Files.writeString(target, output);
}
auto __lambda763__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda764__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda765__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda766__(auto compiled){
	return mergeAll(compiled, __lambda765__);
}
auto __lambda767__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda768__(auto compiled){
	return mergeAll;
}
auto __lambda769__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda770__(auto main){
	return Main.generate(main);
}
auto __lambda771__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda772__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda773__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda774__(auto parseall(segments, __lambda773__).flatmapvalue(main){
	return parseAll(segments, __lambda773__).flatMapValue(Main.generate)(parseall(segments, __lambda773__).flatmapvalue(main);
}
auto __lambda775__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda776__(auto parseall(segments, __lambda775__).flatmapvalue(main){
	return parseAll(segments, __lambda775__).flatMapValue(Main.generate)
                .mapValue(parseall(segments, __lambda775__).flatmapvalue(main);
}
auto __lambda777__(auto main){
	return Main.generate(main);
}
auto __lambda778__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda779__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda780__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda781__(auto parseall(segments, __lambda780__).flatmapvalue(main){
	return parseAll(segments, __lambda780__).flatMapValue(Main.generate)(parseall(segments, __lambda780__).flatmapvalue(main);
}
auto __lambda782__(auto main){
	return Main.generate(main);
}
auto __lambda783__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda784__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda785__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda786__(auto parseall(segments, __lambda785__).flatmapvalue(main){
	return parseAll(segments, __lambda785__).flatMapValue(Main.generate)(parseall(segments, __lambda785__).flatmapvalue(main);
}
auto __lambda787__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda788__(auto parseall(segments, __lambda787__).flatmapvalue(main){
	return parseAll(segments, __lambda787__).flatMapValue(Main.generate)
                .mapValue(compiled -> mergeAll(compiled, Main(parseall(segments, __lambda787__).flatmapvalue(main);
}
auto __lambda789__(auto main){
	return Main.generate(main);
}
auto __lambda790__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda791__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda792__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda793__(auto parseall(segments, __lambda792__).flatmapvalue(main){
	return parseAll(segments, __lambda792__).flatMapValue(Main.generate)(parseall(segments, __lambda792__).flatmapvalue(main);
}
auto __lambda794__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda795__(auto parseall(segments, __lambda794__).flatmapvalue(main){
	return parseAll(segments, __lambda794__).flatMapValue(Main.generate)
                .mapValue(compiled(parseall(segments, __lambda794__).flatmapvalue(main);
}
auto __lambda796__(auto parseall(segments, __lambda783__).flatmapvalue(__lambda782__).mapvalue(compiled -> mergeall(compiled, main){
	return parseAll(segments, __lambda783__).flatMapValue(__lambda782__).mapValue(compiled -> mergeAll(compiled, Main.mergeStatements))(parseall(segments, __lambda783__).flatmapvalue(__lambda782__).mapvalue(compiled -> mergeall(compiled, main);
}
auto __lambda797__(auto main){
	return Main.generate(main);
}
auto __lambda798__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda799__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda800__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda801__(auto parseall(segments, __lambda800__).flatmapvalue(main){
	return parseAll(segments, __lambda800__).flatMapValue(Main.generate)(parseall(segments, __lambda800__).flatmapvalue(main);
}
auto __lambda802__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda803__(auto parseall(segments, __lambda802__).flatmapvalue(main){
	return parseAll(segments, __lambda802__).flatMapValue(Main.generate)
                .mapValue(compiled(parseall(segments, __lambda802__).flatmapvalue(main);
}
auto __lambda804__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda805__(auto compiled){
	return mergeAll(compiled, __lambda804__);
}
auto __lambda806__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda807__(auto compiled){
	return mergeAll;
}
auto __lambda808__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda809__(auto main){
	return Main.generate(main);
}
auto __lambda810__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda811__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda812__(auto main){
	return Main.compileRootSegment(main);
}
auto __lambda813__(auto main){
	return Main.compileRootSegment(main);
}
/* private static */Result_String_CompileError compile(String input){
	List__String segments = divideAll(input, __lambda763__);
	return parseAll(segments, __lambda771__).flatMapValue(__lambda770__).mapValue(__lambda766__);
}
auto __lambda814__(auto err){
	return Err.new(err);
}
auto __lambda815__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll(globals).addAll(functions));
}
auto __lambda816__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll(globals);
}
auto __lambda817__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll;
}
auto __lambda818__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs);
}
auto __lambda819__(auto ){
	return Ok(compiled.addAll(imports).addAll;
}
auto __lambda820__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda821__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda822__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs);
}
auto __lambda823__(auto ){
	return Ok(compiled.addAll(imports).addAll;
}
auto __lambda824__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda825__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda826__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda827__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda828__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda829__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda830__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda831__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda832__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda833__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda834__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda835__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda836__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda837__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda838__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda839__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda840__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda841__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda842__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda843__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda844__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda845__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda846__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda847__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda848__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda849__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda850__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda851__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda852__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda853__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(structs)(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda854__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda855__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda856__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(structs)
                .addAll(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda857__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda858__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda859__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda860__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda861__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda862__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda863__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda864__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda865__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda866__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda867__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda868__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda869__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda870__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda871__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda872__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda873__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda874__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda875__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda876__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda877__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda878__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda879__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda880__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda881__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda882__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda883__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda884__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda885__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda886__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(structs)(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda887__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda888__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda889__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(structs)
                .addAll(globals)(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda890__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda891__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda892__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, () -> new Ok<>(compiled.addAll(imports)
                .addAll(structs)
                .addAll(globals)
                .addAll(functions)))(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda893__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new,(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda894__(auto expandallgenerics().<result<list_<string>, compileerror>>match(err){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(Err.new, ()(expandallgenerics().<result<list_<string>, compileerror>>match(err);
}
auto __lambda895__(auto err){
	return Err.new(err);
}
auto __lambda896__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll(globals).addAll(functions));
}
auto __lambda897__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll(globals);
}
auto __lambda898__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs).addAll;
}
auto __lambda899__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs);
}
auto __lambda900__(auto ){
	return Ok(compiled.addAll(imports).addAll;
}
auto __lambda901__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda902__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda903__(auto ){
	return Ok(compiled.addAll(imports).addAll(structs);
}
auto __lambda904__(auto ){
	return Ok(compiled.addAll(imports).addAll;
}
auto __lambda905__(auto ){
	return Ok(compiled.addAll(imports);
}
auto __lambda906__(auto ){
	return Ok(compiled.addAll(imports);
}
/* private static */Result_List__String_CompileError generate(List__String compiled){
	return expandAllGenerics().<Result<List_<String>, CompileError>>match(__lambda814__, __lambda815__);
}
/* private static */Option_CompileError expandAllGenerics(){
	while (1) {
	}
	return None();
}
auto __lambda907__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda908__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda909__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda910__(auto main){
	return Main.mergeStatements(main);
}
/* private static */Result_String_CompileError compileStatements(String input, Rule compiler){
	return compileAll(divideAll(input, __lambda907__), compiler, __lambda908__);
}
auto __lambda911__(auto compiled){
	return mergeAll(compiled, merger);
}
auto __lambda912__(auto compiled){
	return mergeAll;
}
auto __lambda913__(auto compiled){
	return mergeAll(compiled, merger);
}
auto __lambda914__(auto compiled){
	return mergeAll;
}
/* private static */Result_String_CompileError compileAll(List__String segments, Rule compiler, StringBuilder (*)(StringBuilder, String) merger){
	return parseAll(segments, compiler).mapValue(__lambda911__);
}
/* private static */String mergeAll(List__String compiled, StringBuilder (*)(StringBuilder, String) merger){
	return compiled.stream().foldWithInitial(StringBuilder(), merger).toString();
}
auto __lambda915__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda916__(auto compiler.compile(segment).mapvalue(compiled){
	return compiler.compile(segment).mapValue(compiled.add)(compiler.compile(segment).mapvalue(compiled);
}
auto __lambda917__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue(__lambda915__);
}
auto __lambda918__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda919__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue;
}
auto __lambda920__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda921__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda922__(auto compiled, auto segment){
	return compiler;
}
auto __lambda923__(auto compiled, auto segment){
	return compiler;
}
auto __lambda924__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda925__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda926__(auto compiled, auto segment){
	return compiler;
}
auto __lambda927__(auto compiled, auto segment){
	return compiler;
}
auto __lambda928__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue(compiled;
}
auto __lambda929__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda930__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda931__(auto compiled, auto segment){
	return compiler;
}
auto __lambda932__(auto compiled, auto segment){
	return compiler;
}
auto __lambda933__(auto __lambda928__){
	return __lambda928__.add)(__lambda928__);
}
auto __lambda934__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda935__(auto segments.stream().foldtoresult(lists.empty(), (compiled, segment) -> compiler.compile(segment).mapvalue(compiled){
	return segments.stream().foldToResult(Lists.empty(), (compiled, segment) -> compiler.compile(segment).mapValue(compiled.add))(segments.stream().foldtoresult(lists.empty(), (compiled, segment) -> compiler.compile(segment).mapvalue(compiled);
}
auto __lambda936__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda937__(auto compiler.compile(segment).mapvalue(compiled){
	return compiler.compile(segment).mapValue(compiled.add)(compiler.compile(segment).mapvalue(compiled);
}
auto __lambda938__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue(__lambda936__);
}
auto __lambda939__(auto compiled){
	return compiled.add(compiled);
}
auto __lambda940__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue;
}
auto __lambda941__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda942__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda943__(auto compiled, auto segment){
	return compiler;
}
auto __lambda944__(auto compiled, auto segment){
	return compiler;
}
auto __lambda945__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda946__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda947__(auto compiled, auto segment){
	return compiler;
}
auto __lambda948__(auto compiled, auto segment){
	return compiler;
}
auto __lambda949__(auto compiled, auto segment){
	return compiler.compile(segment).mapValue(compiled;
}
auto __lambda950__(auto compiled, auto segment){
	return compiler.compile(segment);
}
auto __lambda951__(auto compiled, auto segment){
	return compiler.compile;
}
auto __lambda952__(auto compiled, auto segment){
	return compiler;
}
auto __lambda953__(auto compiled, auto segment){
	return compiler;
}
auto __lambda954__(auto __lambda949__){
	return __lambda949__.add)(__lambda949__);
}
auto __lambda955__(auto compiled){
	return compiled.add(compiled);
}
/* private static */Result_List__String_CompileError parseAll(List__String segments, Rule compiler){
	return segments.stream().foldToResult(Lists.empty(), __lambda917__);
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
auto __lambda956__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda957__(auto main){
	return Main.compilePackage(main);
}
auto __lambda958__(auto main){
	return Main.compileImport(main);
}
auto __lambda959__(auto main){
	return Main.compileClass(main);
}
auto __lambda960__(auto lists.of(
                main){
	return Lists.of(
                Main.compileWhitespace,
                Main(lists.of(
                main);
}
auto __lambda961__(auto lists.of(
                main::compilewhitespace,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main.compilePackage,
                Main(lists.of(
                main::compilewhitespace,
                main);
}
auto __lambda962__(auto lists.of(
                main::compilewhitespace,
                main::compilepackage,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compilePackage,
                Main.compileImport,
                Main(lists.of(
                main::compilewhitespace,
                main::compilepackage,
                main);
}
auto __lambda963__(auto lists.of(
                main::compilewhitespace,
                main::compilepackage,
                main::compileimport,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compilePackage,
                Main::compileImport,
                Main.compileClass
        )(lists.of(
                main::compilewhitespace,
                main::compilepackage,
                main::compileimport,
                main);
}
auto __lambda964__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda965__(auto main){
	return Main.compilePackage(main);
}
auto __lambda966__(auto main){
	return Main.compileImport(main);
}
auto __lambda967__(auto main){
	return Main.compileClass(main);
}
auto __lambda968__(auto lists.of(
                main){
	return Lists.of(
                Main.compileWhitespace,
                Main(lists.of(
                main);
}
auto __lambda969__(auto lists.of(
                main::compilewhitespace,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main.compilePackage,
                Main(lists.of(
                main::compilewhitespace,
                main);
}
auto __lambda970__(auto lists.of(
                main::compilewhitespace,
                main::compilepackage,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compilePackage,
                Main.compileImport,
                Main(lists.of(
                main::compilewhitespace,
                main::compilepackage,
                main);
}
auto __lambda971__(auto lists.of(
                main::compilewhitespace,
                main::compilepackage,
                main::compileimport,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compilePackage,
                Main::compileImport,
                Main.compileClass
        )(lists.of(
                main::compilewhitespace,
                main::compilepackage,
                main::compileimport,
                main);
}
/* private static */Result_String_CompileError compileRootSegment(String value){
	return compileOr(value, Lists.of(__lambda956__, __lambda957__, __lambda958__, __lambda959__));
}
/* private static */Result_String_CompileError compilePackage(String input){
	if (1) {
	}
	return createPrefixErr(input, "package ");
}
/* private static */Result_String_CompileError compileClass(String input){
	List__List__String frame = Lists.<List_<String>>empty().add(Lists.empty());
	return compileTypedBlock(input, "class ", frame);
}
/* private static */Result_String_CompileError compileImport(String input){
	String stripped = input.strip();
	if (1) {
	}
	String value = "#include <temp.h>\n";
	imports = imports.add(value);
	return Ok("");
}
/* private static */Result_String_CompileError compileWhitespace(String input){
	if (1) {
	}
	return Err(CompileError("Not whitespace", input));
}
auto __lambda972__(auto ){
	return compileToStruct(modifiers, withoutParams, body, typeParams, Lists.empty(), Lists.empty());
}
auto __lambda973__(auto ){
	return compileToStruct;
}
auto __lambda974__(auto ){
	return compileToStruct(modifiers, withoutParams, body, typeParams, Lists.empty(), Lists.empty());
}
auto __lambda975__(auto ){
	return compileToStruct;
}
/* private static */Result_String_CompileError compileTypedBlock(String input, String keyword, List__List__String typeParams){
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
	return compileGenericTypedBlock(modifiers, withoutParams, body, typeParams).orElseGet(__lambda972__);
}
auto __lambda976__(auto string){
	return String.strip(string);
}
auto __lambda977__(auto lists.fromnative(arrays.aslist(substring.split(pattern.quote(",")))).stream().map(string){
	return Lists.fromNative(Arrays.asList(substring.split(Pattern.quote(",")))).stream().map(String.strip)(lists.fromnative(arrays.aslist(substring.split(pattern.quote(",")))).stream().map(string);
}
auto __lambda978__(auto lists.fromnative(arrays.aslist(substring.split(pattern.quote(",")))).stream().map(string){
	return Lists.fromNative(Arrays.asList(substring.split(Pattern.quote(",")))).stream().map(String.strip)
                .collect(lists.fromnative(arrays.aslist(substring.split(pattern.quote(",")))).stream().map(string);
}
auto __lambda979__(auto string){
	return String.strip(string);
}
auto __lambda980__(auto lists.fromnative(arrays.aslist(substring.split(pattern.quote(",")))).stream().map(string){
	return Lists.fromNative(Arrays.asList(substring.split(Pattern.quote(",")))).stream().map(String.strip)(lists.fromnative(arrays.aslist(substring.split(pattern.quote(",")))).stream().map(string);
}
auto __lambda981__(auto lists.fromnative(arrays.aslist(substring.split(pattern.quote(",")))).stream().map(string){
	return Lists.fromNative(Arrays.asList(substring.split(Pattern.quote(",")))).stream().map(String.strip)
                .collect(new ListCollector<>())(lists.fromnative(arrays.aslist(substring.split(pattern.quote(",")))).stream().map(string);
}
auto __lambda982__(auto string){
	return String.strip(string);
}
auto __lambda983__(auto typeArguments){
	return 
	String joined = generateGenericName(name, typeArguments);
	return compileToStruct(modifiers, joined, body, typeParams, finalClassTypeParams, typeArguments);;
}
auto __lambda984__(auto typeArguments){
	return 
	String joined = generateGenericName(name, typeArguments);
	return compileToStruct(modifiers, joined, body, typeParams, finalClassTypeParams, typeArguments);;
}
/* private static */Option_Result_String_CompileError compileGenericTypedBlock(String modifiers, String withoutPermits, String body, List__List__String typeParams){
	if (1) {
	}
	String withoutEnd = withoutPermits.substring(0, withoutPermits.length() - ">".length());
	int genStart = withoutEnd.indexOf("<");
	if (1) {
	}
	String name = withoutEnd.substring(0, genStart);
	String substring = withoutEnd.substring(genStart + "<".length());
	List__String finalClassTypeParams = Lists.fromNative(Arrays.asList(substring.split(Pattern.quote(",")))).stream().map(__lambda976__).collect(ListCollector());
	generators = generators.with(name, __lambda983__);
	return Some(Ok(""));
}
auto __lambda985__(auto outputContent){
	return generateStruct(modifiers, name, outputContent);
}
auto __lambda986__(auto outputContent){
	return generateStruct;
}
auto __lambda987__(auto input1){
	return compileClassSegment(input1, merged, typeArguments);
}
auto __lambda988__(auto input1){
	return compileClassSegment;
}
auto __lambda989__(auto input1){
	return compileClassSegment(input1, merged, typeArguments);
}
auto __lambda990__(auto input1){
	return compileClassSegment;
}
auto __lambda991__(auto outputContent){
	return generateStruct(modifiers, name, outputContent);
}
auto __lambda992__(auto outputContent){
	return generateStruct;
}
auto __lambda993__(auto input1){
	return compileClassSegment(input1, merged, typeArguments);
}
auto __lambda994__(auto input1){
	return compileClassSegment;
}
/* private static */Result_String_CompileError compileToStruct(String modifiers, String name, String body, List__List__String outerTypeParams, List__String innerTypeParams, List__String typeArguments){
	List__List__String merged = outerTypeParams.add(innerTypeParams);
	if (1) {
	}
	String inputContent = body.substring(0, body.length() - "}".length());
	return compileStatements(inputContent, __lambda987__).mapValue(__lambda985__);
}
/* private static */Result_String_CompileError createSuffixErr(String input, String suffix){
	return Err(CompileError("Suffix '" + suffix + "' not present", input));
}
/* private static */String generateStruct(String modifiers, String name, String content){
	String modifiersString = modifiers.isEmpty() ? "" : generatePlaceholder(modifiers) + " ";
	String generated = modifiersString + "struct " + name + " {" +
                content +
                "\n};\n";
	structs = structs.add(generated);
	return "";
}
auto __lambda995__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda996__(auto input){
	return compileTypedBlock(input, "class", typeParams);
}
auto __lambda997__(auto input){
	return compileTypedBlock;
}
auto __lambda998__(auto input){
	return compileTypedBlock(input, "interface ", typeParams);
}
auto __lambda999__(auto input){
	return compileTypedBlock;
}
auto __lambda1000__(auto input){
	return compileTypedBlock(input, "record ", typeParams);
}
auto __lambda1001__(auto input){
	return compileTypedBlock;
}
auto __lambda1002__(auto input){
	return compileMethod(input, typeParams, typeArguments);
}
auto __lambda1003__(auto input){
	return compileMethod;
}
auto __lambda1004__(auto input){
	return compileGlobal(input, typeParams, typeArguments);
}
auto __lambda1005__(auto input){
	return compileGlobal;
}
auto __lambda1006__(auto input){
	return compileDefinitionStatement(input, typeParams, typeArguments);
}
auto __lambda1007__(auto input){
	return compileDefinitionStatement;
}
auto __lambda1008__(auto lists.of(
                main){
	return Lists.of(
                Main.compileWhitespace,
                input -> compileTypedBlock(input, "class", typeParams),
                input -> compileTypedBlock(input, "interface ", typeParams),
                input -> compileTypedBlock(input, "record ", typeParams),
                input -> compileMethod(input, typeParams, typeArguments),
                input -> compileGlobal(input, typeParams, typeArguments),
                input -> compileDefinitionStatement(input, typeParams, typeArguments)
        )(lists.of(
                main);
}
auto __lambda1009__(auto lists.of(
                main){
	return Lists.of(
                Main.compileWhitespace,
                input(lists.of(
                main);
}
auto __lambda1010__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda1011__(auto input){
	return compileTypedBlock(input, "class", typeParams);
}
auto __lambda1012__(auto input){
	return compileTypedBlock;
}
auto __lambda1013__(auto input){
	return compileTypedBlock(input, "interface ", typeParams);
}
auto __lambda1014__(auto input){
	return compileTypedBlock;
}
auto __lambda1015__(auto input){
	return compileTypedBlock(input, "record ", typeParams);
}
auto __lambda1016__(auto input){
	return compileTypedBlock;
}
auto __lambda1017__(auto input){
	return compileMethod(input, typeParams, typeArguments);
}
auto __lambda1018__(auto input){
	return compileMethod;
}
auto __lambda1019__(auto input){
	return compileGlobal(input, typeParams, typeArguments);
}
auto __lambda1020__(auto input){
	return compileGlobal;
}
auto __lambda1021__(auto input){
	return compileDefinitionStatement(input, typeParams, typeArguments);
}
auto __lambda1022__(auto input){
	return compileDefinitionStatement;
}
auto __lambda1023__(auto lists.of(
                main){
	return Lists.of(
                Main.compileWhitespace,
                input -> compileTypedBlock(input, "class", typeParams),
                input -> compileTypedBlock(input, "interface ", typeParams),
                input -> compileTypedBlock(input, "record ", typeParams),
                input -> compileMethod(input, typeParams, typeArguments),
                input -> compileGlobal(input, typeParams, typeArguments),
                input -> compileDefinitionStatement(input, typeParams, typeArguments)
        )(lists.of(
                main);
}
auto __lambda1024__(auto lists.of(
                main){
	return Lists.of(
                Main.compileWhitespace,
                input(lists.of(
                main);
}
/* private static */Result_String_CompileError compileClassSegment(String value, List__List__String typeParams, List__String typeArguments){
	return compileOr(value, Lists.of(__lambda995__, __lambda996__, __lambda998__, __lambda1000__, __lambda1002__, __lambda1004__, __lambda1006__));
}
auto __lambda1025__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1026__(auto compiledefinition(sliced, typeparams, typearguments).mapvalue(main){
	return compileDefinition(sliced, typeParams, typeArguments).mapValue(Main.generateStatement)(compiledefinition(sliced, typeparams, typearguments).mapvalue(main);
}
auto __lambda1027__(auto main){
	return Main.generateStatement(main);
}
/* private static */Result_String_CompileError compileDefinitionStatement(String input, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	String sliced = input.substring(0, input.length() - ";".length());
	return compileDefinition(sliced, typeParams, typeArguments).mapValue(__lambda1025__);
}
auto __lambda1028__(auto result){
	return 
	globals = result;
	return "";;
}
auto __lambda1029__(auto globals){
	return globals.add(globals);
}
auto __lambda1030__(auto value){
	return value + ";\n";
}
auto __lambda1031__(auto value){
	return value;
}
auto __lambda1032__(auto value){
	return value + ";\n";
}
auto __lambda1033__(auto value){
	return value;
}
auto __lambda1034__(auto value){
	return value + ";\n";
}
auto __lambda1035__(auto value){
	return value;
}
auto __lambda1036__(auto maybeinitialization.mapvalue(__lambda1034__).mapvalue(globals){
	return maybeInitialization.mapValue(__lambda1034__).mapValue(globals.add)(maybeinitialization.mapvalue(__lambda1034__).mapvalue(globals);
}
auto __lambda1037__(auto globals){
	return globals.add(globals);
}
auto __lambda1038__(auto globals){
	return globals.add(globals);
}
auto __lambda1039__(auto value){
	return value + ";\n";
}
auto __lambda1040__(auto value){
	return value;
}
auto __lambda1041__(auto maybeinitialization.mapvalue(__lambda1039__).mapvalue(globals){
	return maybeInitialization.mapValue(__lambda1039__).mapValue(globals.add)
                .mapValue(maybeinitialization.mapvalue(__lambda1039__).mapvalue(globals);
}
auto __lambda1042__(auto globals){
	return globals.add(globals);
}
auto __lambda1043__(auto globals){
	return globals.add(globals);
}
auto __lambda1044__(auto globals){
	return globals.add(globals);
}
auto __lambda1045__(auto value){
	return value + ";\n";
}
auto __lambda1046__(auto value){
	return value;
}
auto __lambda1047__(auto value){
	return value + ";\n";
}
auto __lambda1048__(auto value){
	return value;
}
auto __lambda1049__(auto value){
	return value + ";\n";
}
auto __lambda1050__(auto value){
	return value;
}
auto __lambda1051__(auto maybeinitialization.mapvalue(__lambda1049__).mapvalue(globals){
	return maybeInitialization.mapValue(__lambda1049__).mapValue(globals.add)(maybeinitialization.mapvalue(__lambda1049__).mapvalue(globals);
}
auto __lambda1052__(auto globals){
	return globals.add(globals);
}
auto __lambda1053__(auto globals){
	return globals.add(globals);
}
auto __lambda1054__(auto value){
	return value + ";\n";
}
auto __lambda1055__(auto value){
	return value;
}
auto __lambda1056__(auto maybeinitialization.mapvalue(__lambda1054__).mapvalue(globals){
	return maybeInitialization.mapValue(__lambda1054__).mapValue(globals.add)
                .mapValue(result -> {
                    globals = result;
                    return "";
                })(maybeinitialization.mapvalue(__lambda1054__).mapvalue(globals);
}
auto __lambda1057__(auto result){
	return 
	globals = result;
	return "";;
}
auto __lambda1058__(auto globals){
	return globals.add(globals);
}
auto __lambda1059__(auto globals){
	return globals.add(globals);
}
auto __lambda1060__(auto globals){
	return globals.add(globals);
}
auto __lambda1061__(auto result){
	return 
	globals = result;
	return "";;
}
auto __lambda1062__(auto globals){
	return globals.add(globals);
}
auto __lambda1063__(auto globals){
	return globals.add(globals);
}
auto __lambda1064__(auto globals){
	return globals.add(globals);
}
auto __lambda1065__(auto globals){
	return globals.add(globals);
}
auto __lambda1066__(auto value){
	return value + ";\n";
}
auto __lambda1067__(auto value){
	return value;
}
auto __lambda1068__(auto value){
	return value + ";\n";
}
auto __lambda1069__(auto value){
	return value;
}
auto __lambda1070__(auto value){
	return value + ";\n";
}
auto __lambda1071__(auto value){
	return value;
}
auto __lambda1072__(auto value){
	return value + ";\n";
}
auto __lambda1073__(auto value){
	return value;
}
auto __lambda1074__(auto result){
	return 
	globals = result;
	return "";;
}
auto __lambda1075__(auto globals){
	return globals.add(globals);
}
auto __lambda1076__(auto value){
	return value + ";\n";
}
auto __lambda1077__(auto value){
	return value;
}
auto __lambda1078__(auto value){
	return value + ";\n";
}
auto __lambda1079__(auto value){
	return value;
}
auto __lambda1080__(auto value){
	return value + ";\n";
}
auto __lambda1081__(auto value){
	return value;
}
auto __lambda1082__(auto value){
	return value + ";\n";
}
auto __lambda1083__(auto value){
	return value;
}
/* private static */Result_String_CompileError compileGlobal(String input, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	String substring = input.substring(0, input.length() - ";".length());
	Result_String_CompileError maybeInitialization = compileInitialization(substring, typeParams, typeArguments);
	return maybeInitialization.mapValue(__lambda1030__).mapValue(__lambda1029__).mapValue(__lambda1028__);
}
auto __lambda1084__(auto outputParams){
	return getStringOption(typeParams, typeArguments, outputParams, header, withBody);
}
auto __lambda1085__(auto outputParams){
	return getStringOption;
}
auto __lambda1086__(auto input1){
	return compileDefinition(input1, typeParams, typeArguments);
}
auto __lambda1087__(auto input1){
	return compileDefinition;
}
auto __lambda1088__(auto input1){
	return compileDefinition(input1, typeParams, typeArguments);
}
auto __lambda1089__(auto input1){
	return compileDefinition;
}
auto __lambda1090__(auto outputParams){
	return getStringOption(typeParams, typeArguments, outputParams, header, withBody);
}
auto __lambda1091__(auto outputParams){
	return getStringOption;
}
auto __lambda1092__(auto input1){
	return compileDefinition(input1, typeParams, typeArguments);
}
auto __lambda1093__(auto input1){
	return compileDefinition;
}
/* private static */Result_String_CompileError compileMethod(String input, List__List__String typeParams, List__String typeArguments){
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
	return compileValues(paramString, __lambda1086__).flatMapValue(__lambda1084__);
}
auto __lambda1094__(auto definition){
	return getOption(typeParams, typeArguments, outputParams, withBody, definition);
}
auto __lambda1095__(auto definition){
	return getOption;
}
auto __lambda1096__(auto definition){
	return getOption(typeParams, typeArguments, outputParams, withBody, definition);
}
auto __lambda1097__(auto definition){
	return getOption;
}
/* private static */Result_String_CompileError getStringOption(List__List__String typeParams, List__String typeArguments, String outputParams, String header, String withBody){
	return compileDefinition(header, typeParams, typeArguments).flatMapValue(__lambda1094__);
}
auto __lambda1098__(auto statement){
	return addFunction(statement, string);
}
auto __lambda1099__(auto statement){
	return addFunction;
}
auto __lambda1100__(auto input1){
	return compileStatementOrBlock(input1, typeParams, typeArguments);
}
auto __lambda1101__(auto input1){
	return compileStatementOrBlock;
}
auto __lambda1102__(auto input1){
	return compileStatementOrBlock(input1, typeParams, typeArguments);
}
auto __lambda1103__(auto input1){
	return compileStatementOrBlock;
}
auto __lambda1104__(auto statement){
	return addFunction(statement, string);
}
auto __lambda1105__(auto statement){
	return addFunction;
}
auto __lambda1106__(auto input1){
	return compileStatementOrBlock(input1, typeParams, typeArguments);
}
auto __lambda1107__(auto input1){
	return compileStatementOrBlock;
}
/* private static */Result_String_CompileError getOption(List__List__String typeParams, List__String typeArguments, String outputParams, String withBody, String definition){
	String string = generateInvokable(definition, outputParams);
	if (1) {
	}
	return compileStatements(withBody.substring(1, withBody.length() - 1), __lambda1100__).mapValue(__lambda1098__);
}
/* private static */String addFunction(String content, String string){
	String function = string + "{" + content + "\n}\n";
	functions = functions.add(function);
	return "";
}
/* private static */String generateInvokable(String definition, String params){
	return definition + "(" + params + ")";
}
auto __lambda1108__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda1109__(auto main){
	return Main.mergeValues(main);
}
auto __lambda1110__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda1111__(auto main){
	return Main.mergeValues(main);
}
/* private static */Result_String_CompileError compileValues(String input, Rule compiler){
	return compileAll(divideAll(input, __lambda1108__), compiler, __lambda1109__);
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
auto __lambda1112__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda1113__(auto main){
	return Main.compileIf(main);
}
auto __lambda1114__(auto main){
	return Main.compileWhile(main);
}
auto __lambda1115__(auto main){
	return Main.compileFor(main);
}
auto __lambda1116__(auto main){
	return Main.compileElse(main);
}
auto __lambda1117__(auto main){
	return Main.compilePostFix(main);
}
auto __lambda1118__(auto input){
	return compileStatement(input, typeParams, typeArguments);
}
auto __lambda1119__(auto input){
	return compileStatement;
}
auto __lambda1120__(auto lists.of(
                main){
	return Lists.of(
                Main.compileWhitespace,
                Main(lists.of(
                main);
}
auto __lambda1121__(auto lists.of(
                main::compilewhitespace,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main.compileIf,
                Main(lists.of(
                main::compilewhitespace,
                main);
}
auto __lambda1122__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main.compileWhile,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main);
}
auto __lambda1123__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main.compileFor,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main);
}
auto __lambda1124__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main.compileElse,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main);
}
auto __lambda1125__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main::compileelse,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main::compileElse,
                Main.compilePostFix,
                input -> compileStatement(input, typeParams, typeArguments)
        )(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main::compileelse,
                main);
}
auto __lambda1126__(auto lists.of(
                main){
	return Lists.of(
                Main.compileWhitespace,
                Main(lists.of(
                main);
}
auto __lambda1127__(auto lists.of(
                main::compilewhitespace,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main.compileIf,
                Main(lists.of(
                main::compilewhitespace,
                main);
}
auto __lambda1128__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main.compileWhile,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main);
}
auto __lambda1129__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main.compileFor,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main);
}
auto __lambda1130__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main.compileElse,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main);
}
auto __lambda1131__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main::compileelse,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main::compileElse,
                Main.compilePostFix,
                input(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main::compileelse,
                main);
}
auto __lambda1132__(auto main){
	return Main.compileWhitespace(main);
}
auto __lambda1133__(auto main){
	return Main.compileIf(main);
}
auto __lambda1134__(auto main){
	return Main.compileWhile(main);
}
auto __lambda1135__(auto main){
	return Main.compileFor(main);
}
auto __lambda1136__(auto main){
	return Main.compileElse(main);
}
auto __lambda1137__(auto main){
	return Main.compilePostFix(main);
}
auto __lambda1138__(auto input){
	return compileStatement(input, typeParams, typeArguments);
}
auto __lambda1139__(auto input){
	return compileStatement;
}
auto __lambda1140__(auto lists.of(
                main){
	return Lists.of(
                Main.compileWhitespace,
                Main(lists.of(
                main);
}
auto __lambda1141__(auto lists.of(
                main::compilewhitespace,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main.compileIf,
                Main(lists.of(
                main::compilewhitespace,
                main);
}
auto __lambda1142__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main.compileWhile,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main);
}
auto __lambda1143__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main.compileFor,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main);
}
auto __lambda1144__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main.compileElse,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main);
}
auto __lambda1145__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main::compileelse,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main::compileElse,
                Main.compilePostFix,
                input -> compileStatement(input, typeParams, typeArguments)
        )(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main::compileelse,
                main);
}
auto __lambda1146__(auto lists.of(
                main){
	return Lists.of(
                Main.compileWhitespace,
                Main(lists.of(
                main);
}
auto __lambda1147__(auto lists.of(
                main::compilewhitespace,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main.compileIf,
                Main(lists.of(
                main::compilewhitespace,
                main);
}
auto __lambda1148__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main.compileWhile,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main);
}
auto __lambda1149__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main.compileFor,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main);
}
auto __lambda1150__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main.compileElse,
                Main(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main);
}
auto __lambda1151__(auto lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main::compileelse,
                main){
	return Lists.of(
                Main::compileWhitespace,
                Main::compileIf,
                Main::compileWhile,
                Main::compileFor,
                Main::compileElse,
                Main.compilePostFix,
                input(lists.of(
                main::compilewhitespace,
                main::compileif,
                main::compilewhile,
                main::compilefor,
                main::compileelse,
                main);
}
/* private static */Result_String_CompileError compileStatementOrBlock(String value, List__List__String typeParams, List__String typeArguments){
	return compileOr(value, Lists.of(__lambda1112__, __lambda1113__, __lambda1114__, __lambda1115__, __lambda1116__, __lambda1117__, __lambda1118__));
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
auto __lambda1152__(auto withoutEnd){
	return compileReturn(withoutEnd, typeParams, typeArguments);
}
auto __lambda1153__(auto withoutEnd){
	return compileReturn;
}
auto __lambda1154__(auto withoutEnd){
	return compileInitialization(withoutEnd, typeParams, typeArguments);
}
auto __lambda1155__(auto withoutEnd){
	return compileInitialization;
}
auto __lambda1156__(auto withoutEnd){
	return compileAssignment(withoutEnd, typeParams, typeArguments);
}
auto __lambda1157__(auto withoutEnd){
	return compileAssignment;
}
auto __lambda1158__(auto withoutEnd){
	return compileInvocationStatement(withoutEnd, typeParams, typeArguments);
}
auto __lambda1159__(auto withoutEnd){
	return compileInvocationStatement;
}
auto __lambda1160__(auto withoutEnd){
	return compileReturn(withoutEnd, typeParams, typeArguments);
}
auto __lambda1161__(auto withoutEnd){
	return compileReturn;
}
auto __lambda1162__(auto withoutEnd){
	return compileInitialization(withoutEnd, typeParams, typeArguments);
}
auto __lambda1163__(auto withoutEnd){
	return compileInitialization;
}
auto __lambda1164__(auto withoutEnd){
	return compileAssignment(withoutEnd, typeParams, typeArguments);
}
auto __lambda1165__(auto withoutEnd){
	return compileAssignment;
}
auto __lambda1166__(auto withoutEnd){
	return compileInvocationStatement(withoutEnd, typeParams, typeArguments);
}
auto __lambda1167__(auto withoutEnd){
	return compileInvocationStatement;
}
/* private static */Result_String_CompileError compileStatement(String input, List__List__String typeParams, List__String typeArguments){
	String stripped = input.strip();
	if (1) {
	}
	String slice = stripped.substring(0, stripped.length() - ";".length());
	return compileOr(slice, Lists.of(__lambda1152__, __lambda1154__, __lambda1156__, __lambda1158__));
}
/* private static */Result_String_CompileError compileReturn(String withoutEnd, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	return createPrefixErr(withoutEnd, "return ");
}
auto __lambda1168__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1169__(auto compileinvocation(withoutend, typeparams, typearguments).mapvalue(main){
	return compileInvocation(withoutEnd, typeParams, typeArguments).mapValue(Main.generateStatement)(compileinvocation(withoutend, typeparams, typearguments).mapvalue(main);
}
auto __lambda1170__(auto main){
	return Main.generateStatement(main);
}
/* private static */Result_String_CompileError compileInvocationStatement(String withoutEnd, List__List__String typeParams, List__String typeArguments){
	return compileInvocation(withoutEnd, typeParams, typeArguments).mapValue(__lambda1168__);
}
auto __lambda1171__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1172__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1173__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1174__(auto tuple){
	return tuple;
}
auto __lambda1175__(auto tuple){
	return tuple.left;
}
auto __lambda1176__(auto tuple){
	return tuple;
}
auto __lambda1177__(auto tuple){
	return tuple.left;
}
auto __lambda1178__(auto tuple){
	return tuple;
}
auto __lambda1179__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1180__(auto ){
	return compileValue;
}
auto __lambda1181__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1182__(auto ){
	return compileValue;
}
auto __lambda1183__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1184__(auto ){
	return compileValue;
}
auto __lambda1185__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1186__(auto ){
	return compileValue;
}
auto __lambda1187__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1188__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1189__(auto tuple){
	return tuple;
}
auto __lambda1190__(auto tuple){
	return tuple.left;
}
auto __lambda1191__(auto tuple){
	return tuple;
}
auto __lambda1192__(auto tuple){
	return tuple.left;
}
auto __lambda1193__(auto tuple){
	return tuple;
}
auto __lambda1194__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1195__(auto ){
	return compileValue;
}
auto __lambda1196__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1197__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1198__(auto tuple){
	return tuple;
}
auto __lambda1199__(auto tuple){
	return tuple.left;
}
auto __lambda1200__(auto tuple){
	return tuple;
}
auto __lambda1201__(auto tuple){
	return tuple.left;
}
auto __lambda1202__(auto tuple){
	return tuple;
}
auto __lambda1203__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1204__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1205__(auto tuple){
	return tuple;
}
auto __lambda1206__(auto tuple){
	return tuple.left;
}
auto __lambda1207__(auto tuple){
	return tuple;
}
auto __lambda1208__(auto tuple){
	return tuple.left;
}
auto __lambda1209__(auto tuple){
	return tuple;
}
auto __lambda1210__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1211__(auto ){
	return compileValue;
}
auto __lambda1212__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1213__(auto ){
	return compileValue;
}
auto __lambda1214__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1215__(auto ){
	return compileValue;
}
auto __lambda1216__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1217__(auto ){
	return compileValue;
}
auto __lambda1218__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1219__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1220__(auto tuple){
	return tuple;
}
auto __lambda1221__(auto tuple){
	return tuple.left;
}
auto __lambda1222__(auto tuple){
	return tuple;
}
auto __lambda1223__(auto tuple){
	return tuple.left;
}
auto __lambda1224__(auto tuple){
	return tuple;
}
auto __lambda1225__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1226__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1227__(auto tuple){
	return tuple;
}
auto __lambda1228__(auto tuple){
	return tuple.left;
}
auto __lambda1229__(auto tuple){
	return tuple;
}
auto __lambda1230__(auto tuple){
	return tuple.left;
}
auto __lambda1231__(auto tuple){
	return tuple;
}
auto __lambda1232__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1233__(auto ){
	return compileValue;
}
auto __lambda1234__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1235__(auto ){
	return compileValue;
}
auto __lambda1236__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1237__(auto ){
	return compileValue;
}
auto __lambda1238__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1239__(auto ){
	return compileValue;
}
auto __lambda1240__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1241__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1242__(auto tuple){
	return tuple;
}
auto __lambda1243__(auto tuple){
	return tuple.left;
}
auto __lambda1244__(auto tuple){
	return tuple;
}
auto __lambda1245__(auto tuple){
	return tuple.left;
}
auto __lambda1246__(auto tuple){
	return tuple;
}
auto __lambda1247__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1248__(auto ){
	return compileValue;
}
auto __lambda1249__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1250__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1251__(auto tuple){
	return tuple;
}
auto __lambda1252__(auto tuple){
	return tuple.left;
}
auto __lambda1253__(auto tuple){
	return tuple;
}
auto __lambda1254__(auto tuple){
	return tuple.left;
}
auto __lambda1255__(auto tuple){
	return tuple;
}
auto __lambda1256__(auto compilevalue(destination, typeparams, typearguments).and(__lambda1232__).mapvalue(__lambda1225__).mapvalue(main){
	return compileValue(destination, typeParams, typeArguments).and(__lambda1232__).mapValue(__lambda1225__).mapValue(Main.generateStatement)(compilevalue(destination, typeparams, typearguments).and(__lambda1232__).mapvalue(__lambda1225__).mapvalue(main);
}
auto __lambda1257__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1258__(auto ){
	return compileValue;
}
auto __lambda1259__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1260__(auto " = " + tuple.right).mapvalue(main){
	return " = " + tuple.right).mapValue(Main.generateStatement)(" = " + tuple.right).mapvalue(main);
}
auto __lambda1261__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1262__(auto tuple.right).mapvalue(main){
	return tuple.right).mapValue(Main.generateStatement)(tuple.right).mapvalue(main);
}
auto __lambda1263__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1264__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1265__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1266__(auto tuple){
	return tuple;
}
auto __lambda1267__(auto tuple){
	return tuple.left;
}
auto __lambda1268__(auto tuple){
	return tuple;
}
auto __lambda1269__(auto tuple){
	return tuple.left;
}
auto __lambda1270__(auto tuple){
	return tuple;
}
auto __lambda1271__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1272__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1273__(auto tuple){
	return tuple;
}
auto __lambda1274__(auto tuple){
	return tuple.left;
}
auto __lambda1275__(auto tuple){
	return tuple;
}
auto __lambda1276__(auto tuple){
	return tuple.left;
}
auto __lambda1277__(auto tuple){
	return tuple;
}
auto __lambda1278__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1279__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1280__(auto tuple){
	return tuple;
}
auto __lambda1281__(auto tuple){
	return tuple.left;
}
auto __lambda1282__(auto tuple){
	return tuple;
}
auto __lambda1283__(auto tuple){
	return tuple.left;
}
auto __lambda1284__(auto tuple){
	return tuple;
}
auto __lambda1285__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1286__(auto ){
	return compileValue;
}
auto __lambda1287__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1288__(auto ){
	return compileValue;
}
auto __lambda1289__(auto main){
	return Main.generateStatement(main);
}
auto __lambda1290__(auto tuple){
	return tuple.left + " = " + tuple.right;
}
auto __lambda1291__(auto tuple){
	return tuple.left + " = " + tuple;
}
auto __lambda1292__(auto tuple){
	return tuple;
}
auto __lambda1293__(auto tuple){
	return tuple.left;
}
auto __lambda1294__(auto tuple){
	return tuple;
}
auto __lambda1295__(auto tuple){
	return tuple.left;
}
auto __lambda1296__(auto tuple){
	return tuple;
}
auto __lambda1297__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1298__(auto ){
	return compileValue;
}
auto __lambda1299__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1300__(auto ){
	return compileValue;
}
auto __lambda1301__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1302__(auto ){
	return compileValue;
}
auto __lambda1303__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1304__(auto ){
	return compileValue;
}
auto __lambda1305__(auto ){
	return compileValue(value, typeParams, typeArguments);
}
auto __lambda1306__(auto ){
	return compileValue;
}
/* private static */Result_String_CompileError compileAssignment(String withoutEnd, List__List__String typeParams, List__String typeArguments){
	int valueSeparator = withoutEnd.indexOf("=");
	if (1) {
	}
	String destination = withoutEnd.substring(0, valueSeparator).strip();
	String value = withoutEnd.substring(valueSeparator + "=".length()).strip();
	return compileValue(destination, typeParams, typeArguments).and(__lambda1179__).mapValue(__lambda1172__).mapValue(__lambda1171__);
}
auto __lambda1307__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1308__(auto outputValue){
	return generateStatement;
}
auto __lambda1309__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments).mapValue(__lambda1307__);
}
auto __lambda1310__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1311__(auto outputValue){
	return generateStatement;
}
auto __lambda1312__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments).mapValue;
}
auto __lambda1313__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments);
}
auto __lambda1314__(auto outputDefinition){
	return compileValue;
}
auto __lambda1315__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments);
}
auto __lambda1316__(auto outputDefinition){
	return compileValue;
}
auto __lambda1317__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments).mapValue(
                        outputValue -> generateStatement(outputDefinition;
}
auto __lambda1318__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments);
}
auto __lambda1319__(auto outputDefinition){
	return compileValue;
}
auto __lambda1320__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1321__(auto outputValue){
	return generateStatement;
}
auto __lambda1322__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1323__(auto outputValue){
	return generateStatement;
}
auto __lambda1324__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments).mapValue(__lambda1322__);
}
auto __lambda1325__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1326__(auto outputValue){
	return generateStatement;
}
auto __lambda1327__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments).mapValue;
}
auto __lambda1328__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments);
}
auto __lambda1329__(auto outputDefinition){
	return compileValue;
}
auto __lambda1330__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments);
}
auto __lambda1331__(auto outputDefinition){
	return compileValue;
}
auto __lambda1332__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments).mapValue(
                        outputValue -> generateStatement(outputDefinition;
}
auto __lambda1333__(auto outputDefinition){
	return compileValue(inputValue, typeParams, typeArguments);
}
auto __lambda1334__(auto outputDefinition){
	return compileValue;
}
auto __lambda1335__(auto outputValue){
	return generateStatement(outputDefinition + " = " + outputValue);
}
auto __lambda1336__(auto outputValue){
	return generateStatement;
}
/* private static */Result_String_CompileError compileInitialization(String withoutEnd, List__List__String typeParams, List__String typeArguments){
	int separator = withoutEnd.indexOf("=");
	if (1) {
	}
	String inputDefinition = withoutEnd.substring(0, separator);
	String inputValue = withoutEnd.substring(separator + "=".length());
	return compileDefinition(inputDefinition, typeParams, typeArguments).flatMapValue(__lambda1309__);
}
/* private static */String generateStatement(String value){
	return "\n\t" + value + ";";
}
auto __lambda1337__(auto main){
	return Main.compileString(main);
}
auto __lambda1338__(auto main){
	return Main.compileChar(main);
}
auto __lambda1339__(auto main){
	return Main.compileSymbol(main);
}
auto __lambda1340__(auto main){
	return Main.compileNumber(main);
}
auto __lambda1341__(auto input){
	return compileNot(input, typeParams, typeArguments);
}
auto __lambda1342__(auto input){
	return compileNot;
}
auto __lambda1343__(auto input){
	return compileConstruction(input, typeParams, typeArguments);
}
auto __lambda1344__(auto input){
	return compileConstruction;
}
auto __lambda1345__(auto input){
	return compileLambda(input, typeParams, typeArguments);
}
auto __lambda1346__(auto input){
	return compileLambda;
}
auto __lambda1347__(auto input){
	return compileInvocation(input, typeParams, typeArguments);
}
auto __lambda1348__(auto input){
	return compileInvocation;
}
auto __lambda1349__(auto input){
	return compileTernary(input, typeParams, typeArguments);
}
auto __lambda1350__(auto input){
	return compileTernary;
}
auto __lambda1351__(auto input){
	return compileDataAccess(input, typeParams, typeArguments);
}
auto __lambda1352__(auto input){
	return compileDataAccess;
}
auto __lambda1353__(auto input){
	return compileMethodAccess(input, typeParams, typeArguments);
}
auto __lambda1354__(auto input){
	return compileMethodAccess;
}
auto __lambda1355__(auto input){
	return compileOperator(input, "+", typeParams, typeArguments);
}
auto __lambda1356__(auto input){
	return compileOperator;
}
auto __lambda1357__(auto input){
	return compileOperator(input, "-", typeParams, typeArguments);
}
auto __lambda1358__(auto input){
	return compileOperator;
}
auto __lambda1359__(auto input){
	return compileOperator(input, "==", typeParams, typeArguments);
}
auto __lambda1360__(auto input){
	return compileOperator;
}
auto __lambda1361__(auto input){
	return compileOperator(input, ">=", typeParams, typeArguments);
}
auto __lambda1362__(auto input){
	return compileOperator;
}
auto __lambda1363__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1364__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1365__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1366__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "+", typeParams, typeArguments),
                input -> compileOperator(input, "-", typeParams, typeArguments),
                input -> compileOperator(input, "==", typeParams, typeArguments),
                input -> compileOperator(input, ">=", typeParams, typeArguments)
        )(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1367__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1368__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1369__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1370__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1371__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1372__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1373__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1374__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1375__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1376__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1377__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1378__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1379__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1380__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1381__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1382__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "+", typeParams, typeArguments),
                input -> compileOperator(input, "-", typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1383__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1384__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1385__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1386__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1387__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1388__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1389__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1390__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1391__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1392__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1393__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1394__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1395__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1396__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1397__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1398__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "+", typeParams, typeArguments),
                input -> compileOperator(input, "-", typeParams, typeArguments),
                input -> compileOperator(input, "==", typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1399__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1400__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1401__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1402__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1403__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1404__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1405__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1406__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1407__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1408__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1409__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1410__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1411__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1412__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1413__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1414__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "+", typeParams, typeArguments),
                input -> compileOperator(input, "-", typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1415__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1416__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1417__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1418__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1419__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1420__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1421__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1422__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1423__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1424__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1425__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1426__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1427__(auto main){
	return Main.compileString(main);
}
auto __lambda1428__(auto main){
	return Main.compileChar(main);
}
auto __lambda1429__(auto main){
	return Main.compileSymbol(main);
}
auto __lambda1430__(auto main){
	return Main.compileNumber(main);
}
auto __lambda1431__(auto input){
	return compileNot(input, typeParams, typeArguments);
}
auto __lambda1432__(auto input){
	return compileNot;
}
auto __lambda1433__(auto input){
	return compileConstruction(input, typeParams, typeArguments);
}
auto __lambda1434__(auto input){
	return compileConstruction;
}
auto __lambda1435__(auto input){
	return compileLambda(input, typeParams, typeArguments);
}
auto __lambda1436__(auto input){
	return compileLambda;
}
auto __lambda1437__(auto input){
	return compileInvocation(input, typeParams, typeArguments);
}
auto __lambda1438__(auto input){
	return compileInvocation;
}
auto __lambda1439__(auto input){
	return compileTernary(input, typeParams, typeArguments);
}
auto __lambda1440__(auto input){
	return compileTernary;
}
auto __lambda1441__(auto input){
	return compileDataAccess(input, typeParams, typeArguments);
}
auto __lambda1442__(auto input){
	return compileDataAccess;
}
auto __lambda1443__(auto input){
	return compileMethodAccess(input, typeParams, typeArguments);
}
auto __lambda1444__(auto input){
	return compileMethodAccess;
}
auto __lambda1445__(auto input){
	return compileOperator(input, "+", typeParams, typeArguments);
}
auto __lambda1446__(auto input){
	return compileOperator;
}
auto __lambda1447__(auto input){
	return compileOperator(input, "-", typeParams, typeArguments);
}
auto __lambda1448__(auto input){
	return compileOperator;
}
auto __lambda1449__(auto input){
	return compileOperator(input, "==", typeParams, typeArguments);
}
auto __lambda1450__(auto input){
	return compileOperator;
}
auto __lambda1451__(auto input){
	return compileOperator(input, ">=", typeParams, typeArguments);
}
auto __lambda1452__(auto input){
	return compileOperator;
}
auto __lambda1453__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1454__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1455__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1456__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "+", typeParams, typeArguments),
                input -> compileOperator(input, "-", typeParams, typeArguments),
                input -> compileOperator(input, "==", typeParams, typeArguments),
                input -> compileOperator(input, ">=", typeParams, typeArguments)
        )(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1457__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1458__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1459__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1460__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1461__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1462__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1463__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1464__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1465__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1466__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1467__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1468__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1469__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1470__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1471__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1472__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "+", typeParams, typeArguments),
                input -> compileOperator(input, "-", typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1473__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1474__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1475__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1476__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1477__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1478__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1479__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1480__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1481__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1482__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1483__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1484__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1485__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1486__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1487__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1488__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "+", typeParams, typeArguments),
                input -> compileOperator(input, "-", typeParams, typeArguments),
                input -> compileOperator(input, "==", typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1489__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1490__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1491__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1492__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1493__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1494__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1495__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1496__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1497__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1498__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1499__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1500__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1501__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1502__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1503__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1504__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "+", typeParams, typeArguments),
                input -> compileOperator(input, "-", typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1505__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1506__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1507__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1508__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input -> compileNot(input, typeParams, typeArguments),
                input -> compileConstruction(input, typeParams, typeArguments),
                input -> compileLambda(input, typeParams, typeArguments),
                input -> compileInvocation(input, typeParams, typeArguments),
                input -> compileTernary(input, typeParams, typeArguments),
                input -> compileDataAccess(input, typeParams, typeArguments),
                input -> compileMethodAccess(input, typeParams, typeArguments),
                input -> compileOperator(input, "(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1509__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1510__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1511__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1512__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
auto __lambda1513__(auto lists.of(
                main){
	return Lists.of(
                Main.compileString,
                Main(lists.of(
                main);
}
auto __lambda1514__(auto lists.of(
                main::compilestring,
                main){
	return Lists.of(
                Main::compileString,
                Main.compileChar,
                Main(lists.of(
                main::compilestring,
                main);
}
auto __lambda1515__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main.compileSymbol,
                Main(lists.of(
                main::compilestring,
                main::compilechar,
                main);
}
auto __lambda1516__(auto lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main){
	return Lists.of(
                Main::compileString,
                Main::compileChar,
                Main::compileSymbol,
                Main.compileNumber,
                input(lists.of(
                main::compilestring,
                main::compilechar,
                main::compilesymbol,
                main);
}
/* private static */Result_String_CompileError compileValue(String wrapped, List__List__String typeParams, List__String typeArguments){
	return compileOr(wrapped, Lists.of(__lambda1337__, __lambda1338__, __lambda1339__, __lambda1340__, __lambda1341__, __lambda1343__, __lambda1345__, __lambda1347__, __lambda1349__, __lambda1351__, __lambda1353__, __lambda1355__, __lambda1357__, __lambda1359__, __lambda1361__));
}
/* private static */Result_String_CompileError compileNumber(String input){
	String stripped = input.strip();
	return isNumber(stripped) ? Ok(stripped) : Err(CompileError("Not a number", input));
}
/* private static */Result_String_CompileError compileSymbol(String input){
	String stripped = input.strip();
	return isSymbol(stripped) ? Ok(stripped) : Err(CompileError("Not a symbol", input));
}
auto __lambda1517__(auto "){
	return "."(");
}
auto __lambda1518__(auto stripped.lastindexof("){
	return stripped.lastIndexOf(".")(stripped.lastindexof(");
}
auto __lambda1519__(auto "){
	return "."(");
}
auto __lambda1520__(auto "){
	return "."(");
}
auto __lambda1521__(auto methodseparator + "){
	return methodSeparator + "."(methodseparator + ");
}
auto __lambda1522__(auto "){
	return "."(");
}
auto __lambda1523__(auto methodseparator + "){
	return methodSeparator + ".".length(methodseparator + ");
}
auto __lambda1524__(auto "){
	return "."(");
}
auto __lambda1525__(auto "){
	return ".".length(");
}
auto __lambda1526__(auto methodseparator + "){
	return methodSeparator + "."(methodseparator + ");
}
auto __lambda1527__(auto "){
	return "."(");
}
auto __lambda1528__(auto methodseparator + "){
	return methodSeparator + ".".length()(methodseparator + ");
}
auto __lambda1529__(auto "){
	return "."(");
}
auto __lambda1530__(auto "){
	return ".".length(");
}
auto __lambda1531__(auto "){
	return "."(");
}
auto __lambda1532__(auto "){
	return ".".length()(");
}
auto __lambda1533__(auto stripped.substring(methodseparator + "){
	return stripped.substring(methodSeparator + "."(stripped.substring(methodseparator + ");
}
auto __lambda1534__(auto "){
	return "."(");
}
auto __lambda1535__(auto stripped.substring(methodseparator + "){
	return stripped.substring(methodSeparator + ".".length())(stripped.substring(methodseparator + ");
}
auto __lambda1536__(auto "){
	return "."(");
}
auto __lambda1537__(auto "){
	return ".".length())(");
}
auto __lambda1538__(auto methodseparator + "){
	return methodSeparator + "."(methodseparator + ");
}
auto __lambda1539__(auto "){
	return "."(");
}
auto __lambda1540__(auto methodseparator + "){
	return methodSeparator + ".".length(methodseparator + ");
}
auto __lambda1541__(auto "){
	return "."(");
}
auto __lambda1542__(auto "){
	return ".".length(");
}
auto __lambda1543__(auto methodseparator + "){
	return methodSeparator + "."(methodseparator + ");
}
auto __lambda1544__(auto "){
	return "."(");
}
auto __lambda1545__(auto methodseparator + "){
	return methodSeparator + ".".length()(methodseparator + ");
}
auto __lambda1546__(auto "){
	return "."(");
}
auto __lambda1547__(auto "){
	return ".".length(");
}
auto __lambda1548__(auto "){
	return "."(");
}
auto __lambda1549__(auto "){
	return ".".length()(");
}
auto __lambda1550__(auto newObject){
	return 
	String caller = newObject + "." + property;
	String paramName = newObject.toLowerCase();
	return generateLambda(Lists.<String>empty().add(paramName), generateInvocation(caller, paramName));;
}
auto __lambda1551__(auto newObject){
	return 
	String caller = newObject + "." + property;
	String paramName = newObject.toLowerCase();
	return generateLambda(Lists.<String>empty().add(paramName), generateInvocation(caller, paramName));;
}
/* private static */Result_String_CompileError compileMethodAccess(String input, List__List__String typeParams, List__String typeArguments){
	String stripped = input.strip();
	int methodSeparator = stripped.lastIndexOf("::");
	if (1) {
	}
	String object = stripped.substring(0, methodSeparator);
	String property = stripped.substring(__lambda1521__.length());
	return compileValue(object, typeParams, typeArguments).flatMapValue(__lambda1550__);
}
auto __lambda1552__(auto newObject){
	return newObject + "." + property;
}
auto __lambda1553__(auto newObject){
	return newObject + ";
}
auto __lambda1554__(auto newObject){
	return newObject;
}
auto __lambda1555__(auto newObject){
	return newObject;
}
auto __lambda1556__(auto newObject){
	return newObject + "." + property;
}
auto __lambda1557__(auto newObject){
	return newObject + ";
}
auto __lambda1558__(auto newObject){
	return newObject;
}
auto __lambda1559__(auto newObject){
	return newObject;
}
/* private static */Result_String_CompileError compileDataAccess(String input, List__List__String typeParams, List__String typeArguments){
	String stripped = input.strip();
	int dataSeparator = stripped.lastIndexOf(".");
	if (1) {
	}
	String object = stripped.substring(0, dataSeparator);
	String property = stripped.substring(dataSeparator + ".".length());
	return compileValue(object, typeParams, typeArguments).mapValue(__lambda1552__);
}
auto __lambda1560__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple.right;
}
auto __lambda1561__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda1562__(auto tuple){
	return tuple.left;
}
auto __lambda1563__(auto tuple){
	return tuple;
}
auto __lambda1564__(auto tuple){
	return tuple.left.left;
}
auto __lambda1565__(auto tuple){
	return tuple.left;
}
auto __lambda1566__(auto tuple){
	return tuple;
}
auto __lambda1567__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple;
}
auto __lambda1568__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda1569__(auto tuple){
	return tuple.left;
}
auto __lambda1570__(auto tuple){
	return tuple;
}
auto __lambda1571__(auto tuple){
	return tuple.left.left;
}
auto __lambda1572__(auto tuple){
	return tuple.left;
}
auto __lambda1573__(auto tuple){
	return tuple;
}
auto __lambda1574__(auto tuple){
	return tuple.left.left + " ? " + tuple.left;
}
auto __lambda1575__(auto tuple){
	return tuple.left.left + " ? " + tuple;
}
auto __lambda1576__(auto tuple){
	return tuple.left;
}
auto __lambda1577__(auto tuple){
	return tuple;
}
auto __lambda1578__(auto tuple){
	return tuple.left.left;
}
auto __lambda1579__(auto tuple){
	return tuple.left;
}
auto __lambda1580__(auto tuple){
	return tuple;
}
auto __lambda1581__(auto tuple){
	return tuple.left.left;
}
auto __lambda1582__(auto tuple){
	return tuple.left;
}
auto __lambda1583__(auto tuple){
	return tuple;
}
auto __lambda1584__(auto tuple){
	return tuple.left.left;
}
auto __lambda1585__(auto tuple){
	return tuple.left;
}
auto __lambda1586__(auto tuple){
	return tuple;
}
auto __lambda1587__(auto tuple){
	return tuple.left.left;
}
auto __lambda1588__(auto tuple){
	return tuple.left;
}
auto __lambda1589__(auto tuple){
	return tuple;
}
auto __lambda1590__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1591__(auto ){
	return compileValue;
}
auto __lambda1592__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1593__(auto ){
	return compileValue;
}
auto __lambda1594__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1595__(auto ){
	return compileValue;
}
auto __lambda1596__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1597__(auto ){
	return compileValue;
}
auto __lambda1598__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1599__(auto ){
	return compileValue;
}
auto __lambda1600__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1601__(auto ){
	return compileValue;
}
auto __lambda1602__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1603__(auto ){
	return compileValue;
}
auto __lambda1604__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1605__(auto ){
	return compileValue;
}
auto __lambda1606__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1607__(auto ){
	return compileValue;
}
auto __lambda1608__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1609__(auto ){
	return compileValue;
}
auto __lambda1610__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1611__(auto ){
	return compileValue;
}
auto __lambda1612__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1613__(auto ){
	return compileValue;
}
auto __lambda1614__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1615__(auto ){
	return compileValue;
}
auto __lambda1616__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1617__(auto ){
	return compileValue;
}
auto __lambda1618__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1619__(auto ){
	return compileValue;
}
auto __lambda1620__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1621__(auto ){
	return compileValue;
}
auto __lambda1622__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1623__(auto ){
	return compileValue;
}
auto __lambda1624__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1625__(auto ){
	return compileValue;
}
auto __lambda1626__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1627__(auto ){
	return compileValue;
}
auto __lambda1628__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1629__(auto ){
	return compileValue;
}
auto __lambda1630__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1631__(auto ){
	return compileValue;
}
auto __lambda1632__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1633__(auto ){
	return compileValue;
}
auto __lambda1634__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1635__(auto ){
	return compileValue;
}
auto __lambda1636__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1637__(auto ){
	return compileValue;
}
auto __lambda1638__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1639__(auto ){
	return compileValue;
}
auto __lambda1640__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1641__(auto ){
	return compileValue;
}
auto __lambda1642__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1643__(auto ){
	return compileValue;
}
auto __lambda1644__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1645__(auto ){
	return compileValue;
}
auto __lambda1646__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1647__(auto ){
	return compileValue;
}
auto __lambda1648__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1649__(auto ){
	return compileValue;
}
auto __lambda1650__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1651__(auto ){
	return compileValue;
}
auto __lambda1652__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1653__(auto ){
	return compileValue;
}
auto __lambda1654__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1655__(auto ){
	return compileValue;
}
auto __lambda1656__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1657__(auto ){
	return compileValue;
}
auto __lambda1658__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1659__(auto ){
	return compileValue;
}
auto __lambda1660__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1661__(auto ){
	return compileValue;
}
auto __lambda1662__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1663__(auto ){
	return compileValue;
}
auto __lambda1664__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1665__(auto ){
	return compileValue;
}
auto __lambda1666__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1667__(auto ){
	return compileValue;
}
auto __lambda1668__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1669__(auto ){
	return compileValue;
}
auto __lambda1670__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1671__(auto ){
	return compileValue;
}
auto __lambda1672__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1673__(auto ){
	return compileValue;
}
auto __lambda1674__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1675__(auto ){
	return compileValue;
}
auto __lambda1676__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1677__(auto ){
	return compileValue;
}
auto __lambda1678__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1679__(auto ){
	return compileValue;
}
auto __lambda1680__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1681__(auto ){
	return compileValue;
}
auto __lambda1682__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1683__(auto ){
	return compileValue;
}
auto __lambda1684__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1685__(auto ){
	return compileValue;
}
auto __lambda1686__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1687__(auto ){
	return compileValue;
}
auto __lambda1688__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1689__(auto ){
	return compileValue;
}
auto __lambda1690__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1691__(auto ){
	return compileValue;
}
auto __lambda1692__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1693__(auto ){
	return compileValue;
}
auto __lambda1694__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1695__(auto ){
	return compileValue;
}
auto __lambda1696__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1697__(auto ){
	return compileValue;
}
auto __lambda1698__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1699__(auto ){
	return compileValue;
}
auto __lambda1700__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1701__(auto ){
	return compileValue;
}
auto __lambda1702__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1703__(auto ){
	return compileValue;
}
auto __lambda1704__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1705__(auto ){
	return compileValue;
}
auto __lambda1706__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1707__(auto ){
	return compileValue;
}
auto __lambda1708__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1709__(auto ){
	return compileValue;
}
auto __lambda1710__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1711__(auto ){
	return compileValue;
}
auto __lambda1712__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1713__(auto ){
	return compileValue;
}
auto __lambda1714__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1715__(auto ){
	return compileValue;
}
auto __lambda1716__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1717__(auto ){
	return compileValue;
}
auto __lambda1718__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1719__(auto ){
	return compileValue;
}
auto __lambda1720__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1721__(auto ){
	return compileValue;
}
auto __lambda1722__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1723__(auto ){
	return compileValue;
}
auto __lambda1724__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1725__(auto ){
	return compileValue;
}
auto __lambda1726__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1727__(auto ){
	return compileValue;
}
auto __lambda1728__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1729__(auto ){
	return compileValue;
}
auto __lambda1730__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1731__(auto ){
	return compileValue;
}
auto __lambda1732__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1733__(auto ){
	return compileValue;
}
auto __lambda1734__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1735__(auto ){
	return compileValue;
}
auto __lambda1736__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1737__(auto ){
	return compileValue;
}
auto __lambda1738__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1739__(auto ){
	return compileValue;
}
auto __lambda1740__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1741__(auto ){
	return compileValue;
}
auto __lambda1742__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1743__(auto ){
	return compileValue;
}
auto __lambda1744__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1745__(auto ){
	return compileValue;
}
auto __lambda1746__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1747__(auto ){
	return compileValue;
}
auto __lambda1748__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1749__(auto ){
	return compileValue;
}
auto __lambda1750__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple.right;
}
auto __lambda1751__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda1752__(auto tuple){
	return tuple.left;
}
auto __lambda1753__(auto tuple){
	return tuple;
}
auto __lambda1754__(auto tuple){
	return tuple.left.left;
}
auto __lambda1755__(auto tuple){
	return tuple.left;
}
auto __lambda1756__(auto tuple){
	return tuple;
}
auto __lambda1757__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple;
}
auto __lambda1758__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda1759__(auto tuple){
	return tuple.left;
}
auto __lambda1760__(auto tuple){
	return tuple;
}
auto __lambda1761__(auto tuple){
	return tuple.left.left;
}
auto __lambda1762__(auto tuple){
	return tuple.left;
}
auto __lambda1763__(auto tuple){
	return tuple;
}
auto __lambda1764__(auto tuple){
	return tuple.left.left + " ? " + tuple.left;
}
auto __lambda1765__(auto tuple){
	return tuple.left.left + " ? " + tuple;
}
auto __lambda1766__(auto tuple){
	return tuple.left;
}
auto __lambda1767__(auto tuple){
	return tuple;
}
auto __lambda1768__(auto tuple){
	return tuple.left.left;
}
auto __lambda1769__(auto tuple){
	return tuple.left;
}
auto __lambda1770__(auto tuple){
	return tuple;
}
auto __lambda1771__(auto tuple){
	return tuple.left.left;
}
auto __lambda1772__(auto tuple){
	return tuple.left;
}
auto __lambda1773__(auto tuple){
	return tuple;
}
auto __lambda1774__(auto tuple){
	return tuple.left.left;
}
auto __lambda1775__(auto tuple){
	return tuple.left;
}
auto __lambda1776__(auto tuple){
	return tuple;
}
auto __lambda1777__(auto tuple){
	return tuple.left.left;
}
auto __lambda1778__(auto tuple){
	return tuple.left;
}
auto __lambda1779__(auto tuple){
	return tuple;
}
auto __lambda1780__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1781__(auto ){
	return compileValue;
}
auto __lambda1782__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1783__(auto ){
	return compileValue;
}
auto __lambda1784__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1785__(auto ){
	return compileValue;
}
auto __lambda1786__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1787__(auto ){
	return compileValue;
}
auto __lambda1788__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1789__(auto ){
	return compileValue;
}
auto __lambda1790__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1791__(auto ){
	return compileValue;
}
auto __lambda1792__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1793__(auto ){
	return compileValue;
}
auto __lambda1794__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1795__(auto ){
	return compileValue;
}
auto __lambda1796__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1797__(auto ){
	return compileValue;
}
auto __lambda1798__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1799__(auto ){
	return compileValue;
}
auto __lambda1800__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple.right;
}
auto __lambda1801__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda1802__(auto tuple){
	return tuple.left;
}
auto __lambda1803__(auto tuple){
	return tuple;
}
auto __lambda1804__(auto tuple){
	return tuple.left.left;
}
auto __lambda1805__(auto tuple){
	return tuple.left;
}
auto __lambda1806__(auto tuple){
	return tuple;
}
auto __lambda1807__(auto tuple){
	return tuple.left.left + " ? " + tuple.left.right + " : " + tuple;
}
auto __lambda1808__(auto tuple){
	return tuple.left.left + ";
}
auto __lambda1809__(auto tuple){
	return tuple.left;
}
auto __lambda1810__(auto tuple){
	return tuple;
}
auto __lambda1811__(auto tuple){
	return tuple.left.left;
}
auto __lambda1812__(auto tuple){
	return tuple.left;
}
auto __lambda1813__(auto tuple){
	return tuple;
}
auto __lambda1814__(auto tuple){
	return tuple.left.left + " ? " + tuple.left;
}
auto __lambda1815__(auto tuple){
	return tuple.left.left + " ? " + tuple;
}
auto __lambda1816__(auto tuple){
	return tuple.left;
}
auto __lambda1817__(auto tuple){
	return tuple;
}
auto __lambda1818__(auto tuple){
	return tuple.left.left;
}
auto __lambda1819__(auto tuple){
	return tuple.left;
}
auto __lambda1820__(auto tuple){
	return tuple;
}
auto __lambda1821__(auto tuple){
	return tuple.left.left;
}
auto __lambda1822__(auto tuple){
	return tuple.left;
}
auto __lambda1823__(auto tuple){
	return tuple;
}
auto __lambda1824__(auto tuple){
	return tuple.left.left;
}
auto __lambda1825__(auto tuple){
	return tuple.left;
}
auto __lambda1826__(auto tuple){
	return tuple;
}
auto __lambda1827__(auto tuple){
	return tuple.left.left;
}
auto __lambda1828__(auto tuple){
	return tuple.left;
}
auto __lambda1829__(auto tuple){
	return tuple;
}
auto __lambda1830__(auto ){
	return compileValue(ifFalse, typeParams, typeArguments);
}
auto __lambda1831__(auto ){
	return compileValue;
}
auto __lambda1832__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1833__(auto ){
	return compileValue;
}
auto __lambda1834__(auto ){
	return compileValue(ifTrue, typeParams, typeArguments);
}
auto __lambda1835__(auto ){
	return compileValue;
}
/* private static */Result_String_CompileError compileTernary(String input, List__List__String typeParams, List__String typeArguments){
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
	return compileValue(condition, typeParams, typeArguments).and(__lambda1592__).and(__lambda1590__).mapValue(__lambda1560__);
}
auto __lambda1836__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda1837__(auto type){
	return type;
}
auto __lambda1838__(auto arguments){
	return compileType(caller, typeParams, typeArguments).mapValue(__lambda1836__);
}
auto __lambda1839__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda1840__(auto type){
	return type;
}
auto __lambda1841__(auto arguments){
	return compileType(caller, typeParams, typeArguments).mapValue;
}
auto __lambda1842__(auto arguments){
	return compileType(caller, typeParams, typeArguments);
}
auto __lambda1843__(auto arguments){
	return compileType;
}
auto __lambda1844__(auto arguments){
	return compileType(caller, typeParams, typeArguments);
}
auto __lambda1845__(auto arguments){
	return compileType;
}
auto __lambda1846__(auto arguments){
	return compileType(caller, typeParams, typeArguments).mapValue(type -> type;
}
auto __lambda1847__(auto arguments){
	return compileType(caller, typeParams, typeArguments);
}
auto __lambda1848__(auto arguments){
	return compileType;
}
auto __lambda1849__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda1850__(auto type){
	return type;
}
auto __lambda1851__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda1852__(auto type){
	return type;
}
auto __lambda1853__(auto arguments){
	return compileType(caller, typeParams, typeArguments).mapValue(__lambda1851__);
}
auto __lambda1854__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda1855__(auto type){
	return type;
}
auto __lambda1856__(auto arguments){
	return compileType(caller, typeParams, typeArguments).mapValue;
}
auto __lambda1857__(auto arguments){
	return compileType(caller, typeParams, typeArguments);
}
auto __lambda1858__(auto arguments){
	return compileType;
}
auto __lambda1859__(auto arguments){
	return compileType(caller, typeParams, typeArguments);
}
auto __lambda1860__(auto arguments){
	return compileType;
}
auto __lambda1861__(auto arguments){
	return compileType(caller, typeParams, typeArguments).mapValue(type -> type;
}
auto __lambda1862__(auto arguments){
	return compileType(caller, typeParams, typeArguments);
}
auto __lambda1863__(auto arguments){
	return compileType;
}
auto __lambda1864__(auto type){
	return type + "(" + arguments + ")";
}
auto __lambda1865__(auto type){
	return type;
}
/* private static */Result_String_CompileError compileConstruction(String input, List__List__String typeParams, List__String typeArguments){
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
	return compileAllValues(inputArguments, typeParams, typeArguments).flatMapValue(__lambda1838__);
}
/* private static */Result_String_CompileError createPrefixErr(String input, String prefix){
	return Err(CompileError("Prefix '" + prefix + "' not present", input));
}
auto __lambda1866__(auto result){
	return "!" + result;
}
auto __lambda1867__(auto result){
	return "!";
}
auto __lambda1868__(auto result){
	return "!" + result;
}
auto __lambda1869__(auto result){
	return "!";
}
/* private static */Result_String_CompileError compileNot(String input, List__List__String typeParams, List__String typeArguments){
	String stripped = input.strip();
	if (1) {
	}
	return compileValue(stripped.substring(1), typeParams, typeArguments).mapValue(__lambda1866__);
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
auto __lambda1870__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda1871__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda1872__(auto outputValue){
	return generateLambda;
}
auto __lambda1873__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda1874__(auto outputValue){
	return generateLambda;
}
auto __lambda1875__(auto paramNames){
	return 
	String inputValue = input.substring(arrowIndex + "->".length()).strip();
	return compileLambdaBody(inputValue, typeParams, typeArguments).flatMapValue(__lambda1871__);;
}
auto __lambda1876__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda1877__(auto outputValue){
	return generateLambda;
}
auto __lambda1878__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda1879__(auto outputValue){
	return generateLambda;
}
auto __lambda1880__(auto paramNames){
	return 
	String inputValue = input.substring(arrowIndex + "->".length()).strip();
	return compileLambdaBody(inputValue, typeParams, typeArguments).flatMapValue(__lambda1876__);;
}
auto __lambda1881__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda1882__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda1883__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda1884__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda1885__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda1886__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda1887__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda1888__(auto ){
	return Err(CompileError("Not a lambda statement", input));
}
auto __lambda1889__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda1890__(auto outputValue){
	return generateLambda;
}
auto __lambda1891__(auto outputValue){
	return generateLambda(paramNames, outputValue);
}
auto __lambda1892__(auto outputValue){
	return generateLambda;
}
auto __lambda1893__(auto paramNames){
	return 
	String inputValue = input.substring(arrowIndex + "->".length()).strip();
	return compileLambdaBody(inputValue, typeParams, typeArguments).flatMapValue(__lambda1889__);;
}
/* private static */Result_String_CompileError compileLambda(String input, List__List__String typeParams, List__String typeArguments){
	int arrowIndex = input.indexOf("->");
	if (1) {
	}
	String beforeArrow = input.substring(0, arrowIndex).strip();
	return findLambdaParams(beforeArrow).map(__lambda1875__).orElseGet(__lambda1870__);
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
/* private static */Result_String_CompileError compileLambdaBody(String inputValue, List__List__String typeParams, List__String typeArguments){
	if (1) {
	}
	else {}
}
auto __lambda1894__(auto tuple){
	return tuple.left + " " + operator + " " + tuple.right;
}
auto __lambda1895__(auto tuple){
	return tuple.left + " " + operator + " " + tuple;
}
auto __lambda1896__(auto tuple){
	return tuple;
}
auto __lambda1897__(auto tuple){
	return tuple.left;
}
auto __lambda1898__(auto tuple){
	return tuple;
}
auto __lambda1899__(auto tuple){
	return tuple.left;
}
auto __lambda1900__(auto tuple){
	return tuple;
}
auto __lambda1901__(auto ){
	return compileValue(right, typeParams, typeArguments);
}
auto __lambda1902__(auto ){
	return compileValue;
}
auto __lambda1903__(auto ){
	return compileValue(right, typeParams, typeArguments);
}
auto __lambda1904__(auto ){
	return compileValue;
}
auto __lambda1905__(auto ){
	return compileValue(right, typeParams, typeArguments);
}
auto __lambda1906__(auto ){
	return compileValue;
}
auto __lambda1907__(auto ){
	return compileValue(right, typeParams, typeArguments);
}
auto __lambda1908__(auto ){
	return compileValue;
}
auto __lambda1909__(auto tuple){
	return tuple.left + " " + operator + " " + tuple.right;
}
auto __lambda1910__(auto tuple){
	return tuple.left + " " + operator + " " + tuple;
}
auto __lambda1911__(auto tuple){
	return tuple;
}
auto __lambda1912__(auto tuple){
	return tuple.left;
}
auto __lambda1913__(auto tuple){
	return tuple;
}
auto __lambda1914__(auto tuple){
	return tuple.left;
}
auto __lambda1915__(auto tuple){
	return tuple;
}
auto __lambda1916__(auto tuple){
	return tuple.left + " " + operator + " " + tuple.right;
}
auto __lambda1917__(auto tuple){
	return tuple.left + " " + operator + " " + tuple;
}
auto __lambda1918__(auto tuple){
	return tuple;
}
auto __lambda1919__(auto tuple){
	return tuple.left;
}
auto __lambda1920__(auto tuple){
	return tuple;
}
auto __lambda1921__(auto tuple){
	return tuple.left;
}
auto __lambda1922__(auto tuple){
	return tuple;
}
auto __lambda1923__(auto ){
	return compileValue(right, typeParams, typeArguments);
}
auto __lambda1924__(auto ){
	return compileValue;
}
/* private static */Result_String_CompileError compileOperator(String input, String operator, List__List__String typeParams, List__String typeArguments){
	int operatorIndex = input.indexOf(operator);
	if (1) {
	}
	String left = input.substring(0, operatorIndex);
	String right = input.substring(operatorIndex + operator.length());
	return compileValue(left, typeParams, typeArguments).and(__lambda1901__).mapValue(__lambda1894__);
}
/* private static */int isNumber(String stripped){
	for (;;) {
	}
	return true;
}
auto __lambda1925__(auto inner){
	return inner.orElse("");
}
auto __lambda1926__(auto inner){
	return inner.orElse;
}
auto __lambda1927__(auto inner){
	return inner;
}
auto __lambda1928__(auto inner){
	return inner;
}
auto __lambda1929__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda1930__(auto name){
	return generateDefinition;
}
auto __lambda1931__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda1932__(auto name){
	return generateDefinition;
}
auto __lambda1933__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda1934__(auto name){
	return generateDefinition;
}
auto __lambda1935__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda1936__(auto name){
	return generateDefinition;
}
auto __lambda1937__(auto inner){
	return inner.orElse("");
}
auto __lambda1938__(auto inner){
	return inner.orElse;
}
auto __lambda1939__(auto inner){
	return inner;
}
auto __lambda1940__(auto inner){
	return inner;
}
auto __lambda1941__(auto inner){
	return inner.orElse("");
}
auto __lambda1942__(auto inner){
	return inner.orElse;
}
auto __lambda1943__(auto inner){
	return inner;
}
auto __lambda1944__(auto inner){
	return inner;
}
auto __lambda1945__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda1946__(auto name){
	return generateDefinition;
}
auto __lambda1947__(auto name){
	return generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", name));
}
auto __lambda1948__(auto name){
	return generateDefinition;
}
auto __lambda1949__(auto tuple){
	return 
	addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(tuple.left, tuple.right));
	return lambda;;
}
auto __lambda1950__(auto ){
	return params;
}
auto __lambda1951__(auto ){
	return params;
}
auto __lambda1952__(auto ){
	return params;
}
auto __lambda1953__(auto ){
	return params;
}
auto __lambda1954__(auto ){
	return params;
}
auto __lambda1955__(auto tuple){
	return 
	addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(tuple.left, tuple.right));
	return lambda;;
}
auto __lambda1956__(auto tuple){
	return 
	addFunction("\n\treturn " + lambdaValue + ";", generateInvokable(tuple.left, tuple.right));
	return lambda;;
}
auto __lambda1957__(auto ){
	return params;
}
/* private static */Result_String_CompileError generateLambda(List__String paramNames, String lambdaValue){
	String lambda = "__lambda" + lambdaCounter + "__";
	temp++;
	Result_String_CompileError definition = generateDefinition(Node().withString("modifiers", "").withString("type", "auto").withString("name", lambda));
	Result_String_CompileError params = paramNames.stream().map(__lambda1929__).collect(Exceptional(Joiner(", "))).mapValue(__lambda1925__);
	return definition.and(__lambda1950__).mapValue(__lambda1949__);
}
auto __lambda1958__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda1959__(auto outputCaller){
	return generateInvocation;
}
auto __lambda1960__(auto outputValues){
	return compileValue(inputCaller, typeParams, typeArguments).mapValue(__lambda1958__);
}
auto __lambda1961__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda1962__(auto outputCaller){
	return generateInvocation;
}
auto __lambda1963__(auto outputValues){
	return compileValue(inputCaller, typeParams, typeArguments).mapValue;
}
auto __lambda1964__(auto outputValues){
	return compileValue(inputCaller, typeParams, typeArguments);
}
auto __lambda1965__(auto outputValues){
	return compileValue;
}
auto __lambda1966__(auto outputValues){
	return compileValue(inputCaller, typeParams, typeArguments);
}
auto __lambda1967__(auto outputValues){
	return compileValue;
}
auto __lambda1968__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda1969__(auto outputCaller){
	return generateInvocation;
}
auto __lambda1970__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda1971__(auto outputCaller){
	return generateInvocation;
}
auto __lambda1972__(auto outputValues){
	return compileValue(inputCaller, typeParams, typeArguments).mapValue(__lambda1970__);
}
auto __lambda1973__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda1974__(auto outputCaller){
	return generateInvocation;
}
auto __lambda1975__(auto outputValues){
	return compileValue(inputCaller, typeParams, typeArguments).mapValue;
}
auto __lambda1976__(auto outputValues){
	return compileValue(inputCaller, typeParams, typeArguments);
}
auto __lambda1977__(auto outputValues){
	return compileValue;
}
auto __lambda1978__(auto outputValues){
	return compileValue(inputCaller, typeParams, typeArguments);
}
auto __lambda1979__(auto outputValues){
	return compileValue;
}
auto __lambda1980__(auto outputCaller){
	return generateInvocation(outputCaller, outputValues);
}
auto __lambda1981__(auto outputCaller){
	return generateInvocation;
}
/* private static */Result_String_CompileError compileInvocation(String input, List__List__String typeParams, List__String typeArguments){
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
	return compileAllValues(inputArguments, typeParams, typeArguments).flatMapValue(__lambda1960__);
}
auto __lambda1982__(auto input){
	return compileValue(input, typeParams, typeArguments);
}
auto __lambda1983__(auto input){
	return compileValue;
}
auto __lambda1984__(auto input){
	return compileValue(input, typeParams, typeArguments);
}
auto __lambda1985__(auto input){
	return compileValue;
}
/* private static */Result_String_CompileError compileAllValues(String arguments, List__List__String typeParams, List__String typeArguments){
	return compileValues(arguments, __lambda1982__);
}
/* private static */String generateInvocation(String caller, String arguments){
	return caller + "(" + arguments + ")";
}
auto __lambda1986__(auto node){
	return 
	return generateDefinition(node.withString("name", name));;
}
auto __lambda1987__(auto node){
	return 
	return generateDefinition(node.withString("name", name));;
}
/* private static */Result_String_CompileError compileDefinition(String input, List__List__String typeParams, List__String typeArguments){
	String stripped = input.strip();
	if (1) {
	}
	int nameSeparator = stripped.lastIndexOf(" ");
	if (1) {
	}
	String beforeName = stripped.substring(0, nameSeparator);
	String name = stripped.substring(nameSeparator + " ".length());
	return compileBeforeName(typeParams, typeArguments, beforeName).flatMapValue(__lambda1986__);
}
auto __lambda1988__(auto outputType){
	return modifiers1.withString("type", outputType);
}
auto __lambda1989__(auto outputType){
	return modifiers1.withString;
}
auto __lambda1990__(auto outputType){
	return modifiers1;
}
auto __lambda1991__(auto outputType){
	return modifiers1;
}
auto __lambda1992__(auto outputType){
	return modifiers1.withString("type", outputType);
}
auto __lambda1993__(auto outputType){
	return modifiers1.withString;
}
auto __lambda1994__(auto outputType){
	return modifiers1;
}
auto __lambda1995__(auto outputType){
	return modifiers1;
}
/* private static */Result_Node_CompileError compileBeforeName(List__List__String typeParams, List__String typeArguments, String beforeName){
	int typeSeparator = findTypeSeparator(beforeName);
	if (1) {
	}
	String modifiers = generatePlaceholder(beforeName.substring(0, typeSeparator));
	String inputType = beforeName.substring(typeSeparator + 1);
	Node modifiers1 = Node().withString("modifiers", modifiers);
	return compileType(inputType, typeParams, typeArguments).mapValue(__lambda1988__);
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
auto __lambda1996__(auto type){
	return compileTypeParam(type, frames, typeArguments);
}
auto __lambda1997__(auto type){
	return compileTypeParam;
}
auto __lambda1998__(auto main){
	return Main.compilePrimitive(main);
}
auto __lambda1999__(auto type){
	return compileArray(type, frames, typeArguments);
}
auto __lambda2000__(auto type){
	return compileArray;
}
auto __lambda2001__(auto main){
	return Main.compileSymbolType(main);
}
auto __lambda2002__(auto type){
	return compileGeneric(type, frames, typeArguments);
}
auto __lambda2003__(auto type){
	return compileGeneric;
}
auto __lambda2004__(auto lists.of(
                type -> compiletypeparam(type, frames, typearguments),
                main){
	return Lists.of(
                type -> compileTypeParam(type, frames, typeArguments),
                Main.compilePrimitive,
                type -> compileArray(type, frames, typeArguments),
                Main(lists.of(
                type -> compiletypeparam(type, frames, typearguments),
                main);
}
auto __lambda2005__(auto lists.of(
                type -> compiletypeparam(type, frames, typearguments),
                main::compileprimitive,
                type -> compilearray(type, frames, typearguments),
                main){
	return Lists.of(
                type -> compileTypeParam(type, frames, typeArguments),
                Main::compilePrimitive,
                type -> compileArray(type, frames, typeArguments),
                Main.compileSymbolType,
                type -> compileGeneric(type, frames, typeArguments)
        )(lists.of(
                type -> compiletypeparam(type, frames, typearguments),
                main::compileprimitive,
                type -> compilearray(type, frames, typearguments),
                main);
}
auto __lambda2006__(auto type){
	return compileTypeParam(type, frames, typeArguments);
}
auto __lambda2007__(auto type){
	return compileTypeParam;
}
auto __lambda2008__(auto main){
	return Main.compilePrimitive(main);
}
auto __lambda2009__(auto type){
	return compileArray(type, frames, typeArguments);
}
auto __lambda2010__(auto type){
	return compileArray;
}
auto __lambda2011__(auto main){
	return Main.compileSymbolType(main);
}
auto __lambda2012__(auto type){
	return compileGeneric(type, frames, typeArguments);
}
auto __lambda2013__(auto type){
	return compileGeneric;
}
auto __lambda2014__(auto lists.of(
                type -> compiletypeparam(type, frames, typearguments),
                main){
	return Lists.of(
                type -> compileTypeParam(type, frames, typeArguments),
                Main.compilePrimitive,
                type -> compileArray(type, frames, typeArguments),
                Main(lists.of(
                type -> compiletypeparam(type, frames, typearguments),
                main);
}
auto __lambda2015__(auto lists.of(
                type -> compiletypeparam(type, frames, typearguments),
                main::compileprimitive,
                type -> compilearray(type, frames, typearguments),
                main){
	return Lists.of(
                type -> compileTypeParam(type, frames, typeArguments),
                Main::compilePrimitive,
                type -> compileArray(type, frames, typeArguments),
                Main.compileSymbolType,
                type -> compileGeneric(type, frames, typeArguments)
        )(lists.of(
                type -> compiletypeparam(type, frames, typearguments),
                main::compileprimitive,
                type -> compilearray(type, frames, typearguments),
                main);
}
/* private static */Result_String_CompileError compileType(String input, List__List__String frames, List__String typeArguments){
	return compileOr(input, Lists.of(__lambda1996__, __lambda1998__, __lambda1999__, __lambda2001__, __lambda2002__));
}
auto __lambda2016__(auto errors){
	return CompileError("No valid combination present", input, errors);
}
auto __lambda2017__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2018__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2019__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2020__(auto rule.compile(input).match(orstate::withvalue, orstate){
	return rule.compile(input).match(orState::withValue, orState.withError)(rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2021__(auto orState, auto rule){
	return rule.compile(input).match(__lambda2017__, __lambda2018__);
}
auto __lambda2022__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2023__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2024__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda2025__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2026__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2027__(auto orState, auto rule){
	return rule;
}
auto __lambda2028__(auto orState, auto rule){
	return rule;
}
auto __lambda2029__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2030__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2031__(auto orState, auto rule){
	return rule;
}
auto __lambda2032__(auto orState, auto rule){
	return rule;
}
auto __lambda2033__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2034__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda2035__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2036__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2037__(auto orState, auto rule){
	return rule;
}
auto __lambda2038__(auto orState, auto rule){
	return rule;
}
auto __lambda2039__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda2040__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2041__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2042__(auto orState, auto rule){
	return rule;
}
auto __lambda2043__(auto orState, auto rule){
	return rule;
}
auto __lambda2044__(auto __lambda2039__){
	return __lambda2039__.withValue, orState(__lambda2039__);
}
auto __lambda2045__(auto __lambda2034__){
	return __lambda2034__.withError)(__lambda2034__);
}
auto __lambda2046__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2047__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2048__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate);
}
auto __lambda2049__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2050__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate);
}
auto __lambda2051__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2052__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2053__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2054__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2055__(auto rule.compile(input).match(orstate::withvalue, orstate){
	return rule.compile(input).match(orState::withValue, orState.withError)(rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2056__(auto orState, auto rule){
	return rule.compile(input).match(__lambda2052__, __lambda2053__);
}
auto __lambda2057__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2058__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2059__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda2060__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2061__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2062__(auto orState, auto rule){
	return rule;
}
auto __lambda2063__(auto orState, auto rule){
	return rule;
}
auto __lambda2064__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2065__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2066__(auto orState, auto rule){
	return rule;
}
auto __lambda2067__(auto orState, auto rule){
	return rule;
}
auto __lambda2068__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2069__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda2070__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2071__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2072__(auto orState, auto rule){
	return rule;
}
auto __lambda2073__(auto orState, auto rule){
	return rule;
}
auto __lambda2074__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda2075__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2076__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2077__(auto orState, auto rule){
	return rule;
}
auto __lambda2078__(auto orState, auto rule){
	return rule;
}
auto __lambda2079__(auto __lambda2074__){
	return __lambda2074__.withValue, orState(__lambda2074__);
}
auto __lambda2080__(auto __lambda2069__){
	return __lambda2069__.withError)(__lambda2069__);
}
auto __lambda2081__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2082__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2083__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate);
}
auto __lambda2084__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2085__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate);
}
auto __lambda2086__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult()(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2087__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate);
}
auto __lambda2088__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult()
                .mapErr(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2089__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2090__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2091__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2092__(auto rule.compile(input).match(orstate::withvalue, orstate){
	return rule.compile(input).match(orState::withValue, orState.withError)(rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2093__(auto orState, auto rule){
	return rule.compile(input).match(__lambda2089__, __lambda2090__);
}
auto __lambda2094__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2095__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2096__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda2097__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2098__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2099__(auto orState, auto rule){
	return rule;
}
auto __lambda2100__(auto orState, auto rule){
	return rule;
}
auto __lambda2101__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2102__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2103__(auto orState, auto rule){
	return rule;
}
auto __lambda2104__(auto orState, auto rule){
	return rule;
}
auto __lambda2105__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2106__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda2107__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2108__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2109__(auto orState, auto rule){
	return rule;
}
auto __lambda2110__(auto orState, auto rule){
	return rule;
}
auto __lambda2111__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda2112__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2113__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2114__(auto orState, auto rule){
	return rule;
}
auto __lambda2115__(auto orState, auto rule){
	return rule;
}
auto __lambda2116__(auto __lambda2111__){
	return __lambda2111__.withValue, orState(__lambda2111__);
}
auto __lambda2117__(auto __lambda2106__){
	return __lambda2106__.withError)(__lambda2106__);
}
auto __lambda2118__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2119__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2120__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate);
}
auto __lambda2121__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2122__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate);
}
auto __lambda2123__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2124__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2125__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2126__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2127__(auto rule.compile(input).match(orstate::withvalue, orstate){
	return rule.compile(input).match(orState::withValue, orState.withError)(rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2128__(auto orState, auto rule){
	return rule.compile(input).match(__lambda2124__, __lambda2125__);
}
auto __lambda2129__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2130__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2131__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda2132__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2133__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2134__(auto orState, auto rule){
	return rule;
}
auto __lambda2135__(auto orState, auto rule){
	return rule;
}
auto __lambda2136__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2137__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2138__(auto orState, auto rule){
	return rule;
}
auto __lambda2139__(auto orState, auto rule){
	return rule;
}
auto __lambda2140__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2141__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda2142__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2143__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2144__(auto orState, auto rule){
	return rule;
}
auto __lambda2145__(auto orState, auto rule){
	return rule;
}
auto __lambda2146__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda2147__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2148__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2149__(auto orState, auto rule){
	return rule;
}
auto __lambda2150__(auto orState, auto rule){
	return rule;
}
auto __lambda2151__(auto __lambda2146__){
	return __lambda2146__.withValue, orState(__lambda2146__);
}
auto __lambda2152__(auto __lambda2141__){
	return __lambda2141__.withError)(__lambda2141__);
}
auto __lambda2153__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2154__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2155__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate);
}
auto __lambda2156__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2157__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate);
}
auto __lambda2158__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult()(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2159__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState.withValue, orState(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate);
}
auto __lambda2160__(auto rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate){
	return rules.stream().foldWithInitial(new OrState(), (orState, rule) -> rule.compile(input).match(orState::withValue, orState.withError))
                .toResult()
                .mapErr(errors -> new CompileError("No valid combination present", input, errors))(rules.stream().foldwithinitial(new orstate(), (orstate, rule) -> rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2161__(auto errors){
	return CompileError("No valid combination present", input, errors);
}
auto __lambda2162__(auto errors){
	return CompileError("No valid combination present", input, errors);
}
auto __lambda2163__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2164__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2165__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2166__(auto rule.compile(input).match(orstate::withvalue, orstate){
	return rule.compile(input).match(orState::withValue, orState.withError)(rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2167__(auto orState, auto rule){
	return rule.compile(input).match(__lambda2163__, __lambda2164__);
}
auto __lambda2168__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2169__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2170__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda2171__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2172__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2173__(auto orState, auto rule){
	return rule;
}
auto __lambda2174__(auto orState, auto rule){
	return rule;
}
auto __lambda2175__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2176__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2177__(auto orState, auto rule){
	return rule;
}
auto __lambda2178__(auto orState, auto rule){
	return rule;
}
auto __lambda2179__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2180__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda2181__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2182__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2183__(auto orState, auto rule){
	return rule;
}
auto __lambda2184__(auto orState, auto rule){
	return rule;
}
auto __lambda2185__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda2186__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2187__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2188__(auto orState, auto rule){
	return rule;
}
auto __lambda2189__(auto orState, auto rule){
	return rule;
}
auto __lambda2190__(auto __lambda2185__){
	return __lambda2185__.withValue, orState(__lambda2185__);
}
auto __lambda2191__(auto __lambda2180__){
	return __lambda2180__.withError)(__lambda2180__);
}
auto __lambda2192__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2193__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2194__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2195__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2196__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2197__(auto rule.compile(input).match(orstate::withvalue, orstate){
	return rule.compile(input).match(orState::withValue, orState.withError)(rule.compile(input).match(orstate::withvalue, orstate);
}
auto __lambda2198__(auto orState, auto rule){
	return rule.compile(input).match(__lambda2194__, __lambda2195__);
}
auto __lambda2199__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2200__(auto orstate){
	return orState.withError(orstate);
}
auto __lambda2201__(auto orState, auto rule){
	return rule.compile(input).match;
}
auto __lambda2202__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2203__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2204__(auto orState, auto rule){
	return rule;
}
auto __lambda2205__(auto orState, auto rule){
	return rule;
}
auto __lambda2206__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2207__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2208__(auto orState, auto rule){
	return rule;
}
auto __lambda2209__(auto orState, auto rule){
	return rule;
}
auto __lambda2210__(auto rule.compile(input).match(orstate){
	return rule.compile(input).match(orState.withValue, orState(rule.compile(input).match(orstate);
}
auto __lambda2211__(auto orState, auto rule){
	return rule.compile(input).match(orState::withValue, orState;
}
auto __lambda2212__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2213__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2214__(auto orState, auto rule){
	return rule;
}
auto __lambda2215__(auto orState, auto rule){
	return rule;
}
auto __lambda2216__(auto orState, auto rule){
	return rule.compile(input).match(orState;
}
auto __lambda2217__(auto orState, auto rule){
	return rule.compile(input);
}
auto __lambda2218__(auto orState, auto rule){
	return rule.compile;
}
auto __lambda2219__(auto orState, auto rule){
	return rule;
}
auto __lambda2220__(auto orState, auto rule){
	return rule;
}
auto __lambda2221__(auto __lambda2216__){
	return __lambda2216__.withValue, orState(__lambda2216__);
}
auto __lambda2222__(auto __lambda2211__){
	return __lambda2211__.withError)(__lambda2211__);
}
auto __lambda2223__(auto orstate){
	return orState.withValue(orstate);
}
auto __lambda2224__(auto orstate){
	return orState.withError(orstate);
}
/* private static */Result_String_CompileError compileOr(String input, List__Rule rules){
	return rules.stream().foldWithInitial(OrState(), __lambda2021__).toResult().mapErr(__lambda2016__);
}
/* private static */Result_String_CompileError compilePrimitive(String type){
	if (1) {
	}
	if (1) {
	}
	return Err(CompileError("Not a primitive", type));
}
/* private static */Result_String_CompileError compileArray(String type, List__List__String frames, List__String typeArguments){
	String stripped = type.strip();
	if (1) {
	}
	return createSuffixErr(stripped, "[]");
}
/* private static */Result_String_CompileError compileSymbolType(String type){
	if (1) {
	}
	return Err(CompileError("Not a symbol", type));
}
auto __lambda2225__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda2226__(auto main){
	return Main.divideValueChar(main);
}
auto __lambda2227__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2228__(auto type1){
	return compileType;
}
auto __lambda2229__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2230__(auto type1){
	return compileType;
}
auto __lambda2231__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2232__(auto type1){
	return compileType;
}
auto __lambda2233__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2234__(auto type1){
	return compileType;
}
auto __lambda2235__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2236__(auto type1){
	return compileType;
}
auto __lambda2237__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2238__(auto type1){
	return compileType;
}
auto __lambda2239__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2240__(auto type1){
	return compileType;
}
auto __lambda2241__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2242__(auto type1){
	return compileType;
}
auto __lambda2243__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2244__(auto type1){
	return compileType;
}
auto __lambda2245__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2246__(auto type1){
	return compileType;
}
auto __lambda2247__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2248__(auto type1){
	return compileType;
}
auto __lambda2249__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2250__(auto type1){
	return compileType;
}
auto __lambda2251__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2252__(auto type1){
	return compileType;
}
auto __lambda2253__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2254__(auto type1){
	return compileType;
}
auto __lambda2255__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2256__(auto type1){
	return compileType;
}
auto __lambda2257__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2258__(auto type1){
	return compileType;
}
auto __lambda2259__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2260__(auto type1){
	return compileType;
}
auto __lambda2261__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2262__(auto type1){
	return compileType;
}
auto __lambda2263__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2264__(auto type1){
	return compileType;
}
auto __lambda2265__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2266__(auto type1){
	return compileType;
}
auto __lambda2267__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2268__(auto type1){
	return compileType;
}
auto __lambda2269__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2270__(auto type1){
	return compileType;
}
auto __lambda2271__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2272__(auto type1){
	return compileType;
}
auto __lambda2273__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2274__(auto type1){
	return compileType;
}
auto __lambda2275__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2276__(auto type1){
	return compileType;
}
auto __lambda2277__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2278__(auto type1){
	return compileType;
}
auto __lambda2279__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2280__(auto type1){
	return compileType;
}
auto __lambda2281__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2282__(auto type1){
	return compileType;
}
auto __lambda2283__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2284__(auto type1){
	return compileType;
}
auto __lambda2285__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2286__(auto type1){
	return compileType;
}
auto __lambda2287__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2288__(auto type1){
	return compileType;
}
auto __lambda2289__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2290__(auto type1){
	return compileType;
}
auto __lambda2291__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2292__(auto type1){
	return compileType;
}
auto __lambda2293__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2294__(auto type1){
	return compileType;
}
auto __lambda2295__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2296__(auto type1){
	return compileType;
}
auto __lambda2297__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2298__(auto type1){
	return compileType;
}
auto __lambda2299__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2300__(auto type1){
	return compileType;
}
auto __lambda2301__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2302__(auto type1){
	return compileType;
}
auto __lambda2303__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2304__(auto type1){
	return compileType;
}
auto __lambda2305__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2306__(auto type1){
	return compileType;
}
auto __lambda2307__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2308__(auto type1){
	return compileType;
}
auto __lambda2309__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2310__(auto type1){
	return compileType;
}
auto __lambda2311__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2312__(auto type1){
	return compileType;
}
auto __lambda2313__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2314__(auto type1){
	return compileType;
}
auto __lambda2315__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2316__(auto type1){
	return compileType;
}
auto __lambda2317__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2318__(auto type1){
	return compileType;
}
auto __lambda2319__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2320__(auto type1){
	return compileType;
}
auto __lambda2321__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2322__(auto type1){
	return compileType;
}
auto __lambda2323__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2324__(auto type1){
	return compileType;
}
auto __lambda2325__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2326__(auto type1){
	return compileType;
}
auto __lambda2327__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2328__(auto type1){
	return compileType;
}
auto __lambda2329__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2330__(auto type1){
	return compileType;
}
auto __lambda2331__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2332__(auto type1){
	return compileType;
}
auto __lambda2333__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2334__(auto type1){
	return compileType;
}
auto __lambda2335__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2336__(auto type1){
	return compileType;
}
auto __lambda2337__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2338__(auto type1){
	return compileType;
}
auto __lambda2339__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2340__(auto type1){
	return compileType;
}
auto __lambda2341__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2342__(auto type1){
	return compileType;
}
auto __lambda2343__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2344__(auto type1){
	return compileType;
}
auto __lambda2345__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2346__(auto type1){
	return compileType;
}
auto __lambda2347__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2348__(auto type1){
	return compileType;
}
auto __lambda2349__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2350__(auto type1){
	return compileType;
}
auto __lambda2351__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2352__(auto type1){
	return compileType;
}
auto __lambda2353__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2354__(auto type1){
	return compileType;
}
auto __lambda2355__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2356__(auto type1){
	return compileType;
}
auto __lambda2357__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2358__(auto type1){
	return compileType;
}
auto __lambda2359__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2360__(auto type1){
	return compileType;
}
auto __lambda2361__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2362__(auto type1){
	return compileType;
}
auto __lambda2363__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2364__(auto type1){
	return compileType;
}
auto __lambda2365__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2366__(auto type1){
	return compileType;
}
auto __lambda2367__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2368__(auto type1){
	return compileType;
}
auto __lambda2369__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2370__(auto type1){
	return compileType;
}
auto __lambda2371__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2372__(auto type1){
	return compileType;
}
auto __lambda2373__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2374__(auto type1){
	return compileType;
}
auto __lambda2375__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2376__(auto type1){
	return compileType;
}
auto __lambda2377__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2378__(auto type1){
	return compileType;
}
auto __lambda2379__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2380__(auto type1){
	return compileType;
}
auto __lambda2381__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2382__(auto type1){
	return compileType;
}
auto __lambda2383__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2384__(auto type1){
	return compileType;
}
auto __lambda2385__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2386__(auto type1){
	return compileType;
}
auto __lambda2387__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2388__(auto type1){
	return compileType;
}
auto __lambda2389__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2390__(auto type1){
	return compileType;
}
auto __lambda2391__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2392__(auto type1){
	return compileType;
}
auto __lambda2393__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2394__(auto type1){
	return compileType;
}
auto __lambda2395__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2396__(auto type1){
	return compileType;
}
auto __lambda2397__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2398__(auto type1){
	return compileType;
}
auto __lambda2399__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2400__(auto type1){
	return compileType;
}
auto __lambda2401__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2402__(auto type1){
	return compileType;
}
auto __lambda2403__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2404__(auto type1){
	return compileType;
}
auto __lambda2405__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2406__(auto type1){
	return compileType;
}
auto __lambda2407__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2408__(auto type1){
	return compileType;
}
auto __lambda2409__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2410__(auto type1){
	return compileType;
}
auto __lambda2411__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2412__(auto type1){
	return compileType;
}
auto __lambda2413__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2414__(auto type1){
	return compileType;
}
auto __lambda2415__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2416__(auto type1){
	return compileType;
}
auto __lambda2417__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2418__(auto type1){
	return compileType;
}
auto __lambda2419__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2420__(auto type1){
	return compileType;
}
auto __lambda2421__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2422__(auto type1){
	return compileType;
}
auto __lambda2423__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2424__(auto type1){
	return compileType;
}
auto __lambda2425__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2426__(auto type1){
	return compileType;
}
auto __lambda2427__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2428__(auto type1){
	return compileType;
}
auto __lambda2429__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2430__(auto type1){
	return compileType;
}
auto __lambda2431__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2432__(auto type1){
	return compileType;
}
auto __lambda2433__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2434__(auto type1){
	return compileType;
}
auto __lambda2435__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2436__(auto type1){
	return compileType;
}
auto __lambda2437__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2438__(auto type1){
	return compileType;
}
auto __lambda2439__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2440__(auto type1){
	return compileType;
}
auto __lambda2441__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2442__(auto type1){
	return compileType;
}
auto __lambda2443__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2444__(auto type1){
	return compileType;
}
auto __lambda2445__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2446__(auto type1){
	return compileType;
}
auto __lambda2447__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2448__(auto type1){
	return compileType;
}
auto __lambda2449__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2450__(auto type1){
	return compileType;
}
auto __lambda2451__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2452__(auto type1){
	return compileType;
}
auto __lambda2453__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2454__(auto type1){
	return compileType;
}
auto __lambda2455__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2456__(auto type1){
	return compileType;
}
auto __lambda2457__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2458__(auto type1){
	return compileType;
}
auto __lambda2459__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2460__(auto type1){
	return compileType;
}
auto __lambda2461__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2462__(auto type1){
	return compileType;
}
auto __lambda2463__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2464__(auto type1){
	return compileType;
}
auto __lambda2465__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2466__(auto type1){
	return compileType;
}
auto __lambda2467__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2468__(auto type1){
	return compileType;
}
auto __lambda2469__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2470__(auto type1){
	return compileType;
}
auto __lambda2471__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2472__(auto type1){
	return compileType;
}
auto __lambda2473__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2474__(auto type1){
	return compileType;
}
auto __lambda2475__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2476__(auto type1){
	return compileType;
}
auto __lambda2477__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2478__(auto type1){
	return compileType;
}
auto __lambda2479__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2480__(auto type1){
	return compileType;
}
auto __lambda2481__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2482__(auto type1){
	return compileType;
}
auto __lambda2483__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2484__(auto type1){
	return compileType;
}
auto __lambda2485__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2486__(auto type1){
	return compileType;
}
auto __lambda2487__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2488__(auto type1){
	return compileType;
}
auto __lambda2489__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2490__(auto type1){
	return compileType;
}
auto __lambda2491__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2492__(auto type1){
	return compileType;
}
auto __lambda2493__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2494__(auto type1){
	return compileType;
}
auto __lambda2495__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2496__(auto type1){
	return compileType;
}
auto __lambda2497__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2498__(auto type1){
	return compileType;
}
auto __lambda2499__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2500__(auto type1){
	return compileType;
}
auto __lambda2501__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2502__(auto type1){
	return compileType;
}
auto __lambda2503__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2504__(auto type1){
	return compileType;
}
auto __lambda2505__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2506__(auto type1){
	return compileType;
}
auto __lambda2507__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2508__(auto type1){
	return compileType;
}
auto __lambda2509__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2510__(auto type1){
	return compileType;
}
auto __lambda2511__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2512__(auto type1){
	return compileType;
}
auto __lambda2513__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2514__(auto type1){
	return compileType;
}
auto __lambda2515__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2516__(auto type1){
	return compileType;
}
auto __lambda2517__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2518__(auto type1){
	return compileType;
}
auto __lambda2519__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2520__(auto type1){
	return compileType;
}
auto __lambda2521__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2522__(auto type1){
	return compileType;
}
auto __lambda2523__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2524__(auto type1){
	return compileType;
}
auto __lambda2525__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2526__(auto type1){
	return compileType;
}
auto __lambda2527__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2528__(auto type1){
	return compileType;
}
auto __lambda2529__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2530__(auto type1){
	return compileType;
}
auto __lambda2531__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2532__(auto type1){
	return compileType;
}
auto __lambda2533__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2534__(auto type1){
	return compileType;
}
auto __lambda2535__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2536__(auto type1){
	return compileType;
}
auto __lambda2537__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2538__(auto type1){
	return compileType;
}
auto __lambda2539__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2540__(auto type1){
	return compileType;
}
auto __lambda2541__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2542__(auto type1){
	return compileType;
}
auto __lambda2543__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2544__(auto type1){
	return compileType;
}
auto __lambda2545__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2546__(auto type1){
	return compileType;
}
auto __lambda2547__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2548__(auto type1){
	return compileType;
}
auto __lambda2549__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2550__(auto type1){
	return compileType;
}
auto __lambda2551__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2552__(auto type1){
	return compileType;
}
auto __lambda2553__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2554__(auto type1){
	return compileType;
}
auto __lambda2555__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2556__(auto type1){
	return compileType;
}
auto __lambda2557__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2558__(auto type1){
	return compileType;
}
auto __lambda2559__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2560__(auto type1){
	return compileType;
}
auto __lambda2561__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2562__(auto type1){
	return compileType;
}
auto __lambda2563__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2564__(auto type1){
	return compileType;
}
auto __lambda2565__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2566__(auto type1){
	return compileType;
}
auto __lambda2567__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2568__(auto type1){
	return compileType;
}
auto __lambda2569__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2570__(auto type1){
	return compileType;
}
auto __lambda2571__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2572__(auto type1){
	return compileType;
}
auto __lambda2573__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2574__(auto type1){
	return compileType;
}
auto __lambda2575__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2576__(auto type1){
	return compileType;
}
auto __lambda2577__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2578__(auto type1){
	return compileType;
}
auto __lambda2579__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2580__(auto type1){
	return compileType;
}
auto __lambda2581__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2582__(auto type1){
	return compileType;
}
auto __lambda2583__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2584__(auto type1){
	return compileType;
}
auto __lambda2585__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2586__(auto type1){
	return compileType;
}
auto __lambda2587__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2588__(auto type1){
	return compileType;
}
auto __lambda2589__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2590__(auto type1){
	return compileType;
}
auto __lambda2591__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2592__(auto type1){
	return compileType;
}
auto __lambda2593__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2594__(auto type1){
	return compileType;
}
auto __lambda2595__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2596__(auto type1){
	return compileType;
}
auto __lambda2597__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2598__(auto type1){
	return compileType;
}
auto __lambda2599__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2600__(auto type1){
	return compileType;
}
auto __lambda2601__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2602__(auto type1){
	return compileType;
}
auto __lambda2603__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2604__(auto type1){
	return compileType;
}
auto __lambda2605__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2606__(auto type1){
	return compileType;
}
auto __lambda2607__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2608__(auto type1){
	return compileType;
}
auto __lambda2609__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2610__(auto type1){
	return compileType;
}
auto __lambda2611__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2612__(auto type1){
	return compileType;
}
auto __lambda2613__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2614__(auto type1){
	return compileType;
}
auto __lambda2615__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2616__(auto type1){
	return compileType;
}
auto __lambda2617__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2618__(auto type1){
	return compileType;
}
auto __lambda2619__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2620__(auto type1){
	return compileType;
}
auto __lambda2621__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2622__(auto type1){
	return compileType;
}
auto __lambda2623__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2624__(auto type1){
	return compileType;
}
auto __lambda2625__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2626__(auto type1){
	return compileType;
}
auto __lambda2627__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2628__(auto type1){
	return compileType;
}
auto __lambda2629__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2630__(auto type1){
	return compileType;
}
auto __lambda2631__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2632__(auto type1){
	return compileType;
}
auto __lambda2633__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2634__(auto type1){
	return compileType;
}
auto __lambda2635__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2636__(auto type1){
	return compileType;
}
auto __lambda2637__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2638__(auto type1){
	return compileType;
}
auto __lambda2639__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2640__(auto type1){
	return compileType;
}
auto __lambda2641__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2642__(auto type1){
	return compileType;
}
auto __lambda2643__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2644__(auto type1){
	return compileType;
}
auto __lambda2645__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2646__(auto type1){
	return compileType;
}
auto __lambda2647__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2648__(auto type1){
	return compileType;
}
auto __lambda2649__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2650__(auto type1){
	return compileType;
}
auto __lambda2651__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2652__(auto type1){
	return compileType;
}
auto __lambda2653__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2654__(auto type1){
	return compileType;
}
auto __lambda2655__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2656__(auto type1){
	return compileType;
}
auto __lambda2657__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2658__(auto type1){
	return compileType;
}
auto __lambda2659__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2660__(auto type1){
	return compileType;
}
auto __lambda2661__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2662__(auto type1){
	return compileType;
}
auto __lambda2663__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2664__(auto type1){
	return compileType;
}
auto __lambda2665__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2666__(auto type1){
	return compileType;
}
auto __lambda2667__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2668__(auto type1){
	return compileType;
}
auto __lambda2669__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2670__(auto type1){
	return compileType;
}
auto __lambda2671__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2672__(auto type1){
	return compileType;
}
auto __lambda2673__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2674__(auto type1){
	return compileType;
}
auto __lambda2675__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2676__(auto type1){
	return compileType;
}
auto __lambda2677__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2678__(auto type1){
	return compileType;
}
auto __lambda2679__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2680__(auto type1){
	return compileType;
}
auto __lambda2681__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2682__(auto type1){
	return compileType;
}
auto __lambda2683__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2684__(auto type1){
	return compileType;
}
auto __lambda2685__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2686__(auto type1){
	return compileType;
}
auto __lambda2687__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2688__(auto type1){
	return compileType;
}
auto __lambda2689__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2690__(auto type1){
	return compileType;
}
auto __lambda2691__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2692__(auto type1){
	return compileType;
}
auto __lambda2693__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2694__(auto type1){
	return compileType;
}
auto __lambda2695__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2696__(auto type1){
	return compileType;
}
auto __lambda2697__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2698__(auto type1){
	return compileType;
}
auto __lambda2699__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2700__(auto type1){
	return compileType;
}
auto __lambda2701__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2702__(auto type1){
	return compileType;
}
auto __lambda2703__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2704__(auto type1){
	return compileType;
}
auto __lambda2705__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2706__(auto type1){
	return compileType;
}
auto __lambda2707__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2708__(auto type1){
	return compileType;
}
auto __lambda2709__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2710__(auto type1){
	return compileType;
}
auto __lambda2711__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2712__(auto type1){
	return compileType;
}
auto __lambda2713__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2714__(auto type1){
	return compileType;
}
auto __lambda2715__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2716__(auto type1){
	return compileType;
}
auto __lambda2717__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2718__(auto type1){
	return compileType;
}
auto __lambda2719__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2720__(auto type1){
	return compileType;
}
auto __lambda2721__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2722__(auto type1){
	return compileType;
}
auto __lambda2723__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2724__(auto type1){
	return compileType;
}
auto __lambda2725__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2726__(auto type1){
	return compileType;
}
auto __lambda2727__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2728__(auto type1){
	return compileType;
}
auto __lambda2729__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2730__(auto type1){
	return compileType;
}
auto __lambda2731__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2732__(auto type1){
	return compileType;
}
auto __lambda2733__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2734__(auto type1){
	return compileType;
}
auto __lambda2735__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2736__(auto type1){
	return compileType;
}
auto __lambda2737__(auto type1){
	return compileType(type1, frames, typeArguments);
}
auto __lambda2738__(auto type1){
	return compileType;
}
/* private static */Result_String_CompileError compileGeneric(String type, List__List__String frames, List__String typeArguments){
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
	List__String segments = divideAll(oldArguments, __lambda2225__);
	return parseAll(segments, __lambda2227__).mapValue(newArguments -> {
            switch (base) {
                case "Function" -> {
                    return generateFunctionalType(newArguments.apply(1).orElse(null), Lists.fromNative(Collections.singletonList(newArguments.apply(0).orElse(null))));
                }
                case "BiFunction" -> {
                    return generateFunctionalType(newArguments.apply(2).orElse(null), Lists.fromNative(Arrays.asList(newArguments.apply(0).orElse(null), newArguments.apply(1).orElse(null))));
                }
                case "Consumer" -> {
                    return generateFunctionalType("void", Lists.fromNative(Collections.singletonList(newArguments.apply(0).orElse(null))));
                }
                case "Supplier" -> {
                    return generateFunctionalType(newArguments.apply(0).orElse(null), Lists.empty());
                }
            }

            if (hasNoTypeParams(frames)) {
                Tuple<String, List_<String>> tuple = new Tuple<>(base, newArguments);
                if (!isDefined(toExpand, tuple)) {
                    toExpand = toExpand.add(tuple);
                }
            }

            return generateGenericName(base, newArguments);
        });
}
auto __lambda2739__(auto index){
	return typeArguments.apply(index).orElse(null);
}
auto __lambda2740__(auto index){
	return typeArguments.apply(index).orElse;
}
auto __lambda2741__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2742__(auto index){
	return typeArguments.apply;
}
auto __lambda2743__(auto index){
	return typeArguments;
}
auto __lambda2744__(auto index){
	return typeArguments;
}
auto __lambda2745__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2746__(auto index){
	return typeArguments.apply;
}
auto __lambda2747__(auto index){
	return typeArguments;
}
auto __lambda2748__(auto index){
	return typeArguments;
}
auto __lambda2749__(auto string){
	return String.equals(string);
}
auto __lambda2750__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2751__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(lists.indexof(last, stripped, string);
}
auto __lambda2752__(auto string){
	return String.equals(string);
}
auto __lambda2753__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2754__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2755__(auto string){
	return String.equals(string);
}
auto __lambda2756__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2757__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2758__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(lists.indexof(last, stripped, string);
}
auto __lambda2759__(auto string){
	return String.equals(string);
}
auto __lambda2760__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2761__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2762__(auto string){
	return String.equals(string);
}
auto __lambda2763__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2764__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2765__(auto string){
	return String.equals(string);
}
auto __lambda2766__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2767__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2768__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index)(lists.indexof(last, stripped, string);
}
auto __lambda2769__(auto string){
	return String.equals(string);
}
auto __lambda2770__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2771__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2772__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))(lists.indexof(last, stripped, string);
}
auto __lambda2773__(auto string){
	return String.equals(string);
}
auto __lambda2774__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2775__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2776__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))
                .orElse(lists.indexof(last, stripped, string);
}
auto __lambda2777__(auto string){
	return String.equals(string);
}
auto __lambda2778__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2779__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2780__(auto index){
	return typeArguments.apply(index).orElse(null);
}
auto __lambda2781__(auto index){
	return typeArguments.apply(index).orElse;
}
auto __lambda2782__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2783__(auto index){
	return typeArguments.apply;
}
auto __lambda2784__(auto index){
	return typeArguments;
}
auto __lambda2785__(auto index){
	return typeArguments;
}
auto __lambda2786__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2787__(auto index){
	return typeArguments.apply;
}
auto __lambda2788__(auto index){
	return typeArguments;
}
auto __lambda2789__(auto index){
	return typeArguments;
}
auto __lambda2790__(auto string){
	return String.equals(string);
}
auto __lambda2791__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2792__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(lists.indexof(last, stripped, string);
}
auto __lambda2793__(auto string){
	return String.equals(string);
}
auto __lambda2794__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2795__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2796__(auto string){
	return String.equals(string);
}
auto __lambda2797__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2798__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2799__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(lists.indexof(last, stripped, string);
}
auto __lambda2800__(auto string){
	return String.equals(string);
}
auto __lambda2801__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2802__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2803__(auto string){
	return String.equals(string);
}
auto __lambda2804__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2805__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2806__(auto string){
	return String.equals(string);
}
auto __lambda2807__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2808__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2809__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index)(lists.indexof(last, stripped, string);
}
auto __lambda2810__(auto string){
	return String.equals(string);
}
auto __lambda2811__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2812__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2813__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))(lists.indexof(last, stripped, string);
}
auto __lambda2814__(auto string){
	return String.equals(string);
}
auto __lambda2815__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2816__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2817__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))
                .orElse(stripped)(lists.indexof(last, stripped, string);
}
auto __lambda2818__(auto string){
	return String.equals(string);
}
auto __lambda2819__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2820__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2821__(auto index){
	return typeArguments.apply(index).orElse(null);
}
auto __lambda2822__(auto index){
	return typeArguments.apply(index).orElse;
}
auto __lambda2823__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2824__(auto index){
	return typeArguments.apply;
}
auto __lambda2825__(auto index){
	return typeArguments;
}
auto __lambda2826__(auto index){
	return typeArguments;
}
auto __lambda2827__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2828__(auto index){
	return typeArguments.apply;
}
auto __lambda2829__(auto index){
	return typeArguments;
}
auto __lambda2830__(auto index){
	return typeArguments;
}
auto __lambda2831__(auto string){
	return String.equals(string);
}
auto __lambda2832__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2833__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(lists.indexof(last, stripped, string);
}
auto __lambda2834__(auto string){
	return String.equals(string);
}
auto __lambda2835__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2836__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2837__(auto string){
	return String.equals(string);
}
auto __lambda2838__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2839__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2840__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(lists.indexof(last, stripped, string);
}
auto __lambda2841__(auto string){
	return String.equals(string);
}
auto __lambda2842__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2843__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2844__(auto string){
	return String.equals(string);
}
auto __lambda2845__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2846__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2847__(auto string){
	return String.equals(string);
}
auto __lambda2848__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2849__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2850__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index)(lists.indexof(last, stripped, string);
}
auto __lambda2851__(auto string){
	return String.equals(string);
}
auto __lambda2852__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2853__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2854__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))(lists.indexof(last, stripped, string);
}
auto __lambda2855__(auto string){
	return String.equals(string);
}
auto __lambda2856__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2857__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2858__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))
                .orElse(lists.indexof(last, stripped, string);
}
auto __lambda2859__(auto string){
	return String.equals(string);
}
auto __lambda2860__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2861__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2862__(auto index){
	return typeArguments.apply(index).orElse(null);
}
auto __lambda2863__(auto index){
	return typeArguments.apply(index).orElse;
}
auto __lambda2864__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2865__(auto index){
	return typeArguments.apply;
}
auto __lambda2866__(auto index){
	return typeArguments;
}
auto __lambda2867__(auto index){
	return typeArguments;
}
auto __lambda2868__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2869__(auto index){
	return typeArguments.apply;
}
auto __lambda2870__(auto index){
	return typeArguments;
}
auto __lambda2871__(auto index){
	return typeArguments;
}
auto __lambda2872__(auto string){
	return String.equals(string);
}
auto __lambda2873__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2874__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(lists.indexof(last, stripped, string);
}
auto __lambda2875__(auto string){
	return String.equals(string);
}
auto __lambda2876__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2877__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2878__(auto string){
	return String.equals(string);
}
auto __lambda2879__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2880__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2881__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(lists.indexof(last, stripped, string);
}
auto __lambda2882__(auto string){
	return String.equals(string);
}
auto __lambda2883__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2884__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2885__(auto string){
	return String.equals(string);
}
auto __lambda2886__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2887__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2888__(auto string){
	return String.equals(string);
}
auto __lambda2889__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2890__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2891__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index)(lists.indexof(last, stripped, string);
}
auto __lambda2892__(auto string){
	return String.equals(string);
}
auto __lambda2893__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2894__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2895__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))(lists.indexof(last, stripped, string);
}
auto __lambda2896__(auto string){
	return String.equals(string);
}
auto __lambda2897__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2898__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2899__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))
                .orElse(stripped)(lists.indexof(last, stripped, string);
}
auto __lambda2900__(auto string){
	return String.equals(string);
}
auto __lambda2901__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2902__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2903__(auto string){
	return String.equals(string);
}
auto __lambda2904__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2905__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2906__(auto string){
	return String.equals(string);
}
auto __lambda2907__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2908__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2909__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(lists.indexof(last, stripped, string);
}
auto __lambda2910__(auto string){
	return String.equals(string);
}
auto __lambda2911__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2912__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2913__(auto string){
	return String.equals(string);
}
auto __lambda2914__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2915__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2916__(auto string){
	return String.equals(string);
}
auto __lambda2917__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2918__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2919__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index)(lists.indexof(last, stripped, string);
}
auto __lambda2920__(auto string){
	return String.equals(string);
}
auto __lambda2921__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2922__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2923__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(lists.indexof(last, stripped, string);
}
auto __lambda2924__(auto string){
	return String.equals(string);
}
auto __lambda2925__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2926__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2927__(auto string){
	return String.equals(string);
}
auto __lambda2928__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2929__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2930__(auto string){
	return String.equals(string);
}
auto __lambda2931__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2932__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2933__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(lists.indexof(last, stripped, string);
}
auto __lambda2934__(auto string){
	return String.equals(string);
}
auto __lambda2935__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2936__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2937__(auto string){
	return String.equals(string);
}
auto __lambda2938__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2939__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2940__(auto string){
	return String.equals(string);
}
auto __lambda2941__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2942__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2943__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index)(lists.indexof(last, stripped, string);
}
auto __lambda2944__(auto string){
	return String.equals(string);
}
auto __lambda2945__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2946__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2947__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null)(lists.indexof(last, stripped, string);
}
auto __lambda2948__(auto string){
	return String.equals(string);
}
auto __lambda2949__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2950__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2951__(auto index){
	return typeArguments.apply(index).orElse(null);
}
auto __lambda2952__(auto index){
	return typeArguments.apply(index).orElse;
}
auto __lambda2953__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2954__(auto index){
	return typeArguments.apply;
}
auto __lambda2955__(auto index){
	return typeArguments;
}
auto __lambda2956__(auto index){
	return typeArguments;
}
auto __lambda2957__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2958__(auto index){
	return typeArguments.apply;
}
auto __lambda2959__(auto index){
	return typeArguments;
}
auto __lambda2960__(auto index){
	return typeArguments;
}
auto __lambda2961__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals(lists.indexof(last, stripped, string);
}
auto __lambda2962__(auto string){
	return String.equals(string);
}
auto __lambda2963__(auto string){
	return String.equals(string);
}
auto __lambda2964__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2965__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda2966__(auto string){
	return String.equals(string);
}
auto __lambda2967__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2968__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2969__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index(lists.indexof(last, stripped, string);
}
auto __lambda2970__(auto string){
	return String.equals(string);
}
auto __lambda2971__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda2972__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda2973__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals(lists.indexof(last, stripped, string);
}
auto __lambda2974__(auto string){
	return String.equals(string);
}
auto __lambda2975__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals(lists.indexof(last, stripped, string);
}
auto __lambda2976__(auto string){
	return String.equals(string);
}
auto __lambda2977__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals(lists.indexof(last, stripped, string);
}
auto __lambda2978__(auto string){
	return String.equals(string);
}
auto __lambda2979__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals(lists.indexof(last, stripped, string);
}
auto __lambda2980__(auto string){
	return String.equals(string);
}
auto __lambda2981__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals(lists.indexof(last, stripped, string);
}
auto __lambda2982__(auto string){
	return String.equals(string);
}
auto __lambda2983__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals(lists.indexof(last, stripped, string);
}
auto __lambda2984__(auto string){
	return String.equals(string);
}
auto __lambda2985__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals(lists.indexof(last, stripped, string);
}
auto __lambda2986__(auto string){
	return String.equals(string);
}
auto __lambda2987__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals(lists.indexof(last, stripped, string);
}
auto __lambda2988__(auto string){
	return String.equals(string);
}
auto __lambda2989__(auto index){
	return typeArguments.apply(index).orElse(null);
}
auto __lambda2990__(auto index){
	return typeArguments.apply(index).orElse;
}
auto __lambda2991__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2992__(auto index){
	return typeArguments.apply;
}
auto __lambda2993__(auto index){
	return typeArguments;
}
auto __lambda2994__(auto index){
	return typeArguments;
}
auto __lambda2995__(auto index){
	return typeArguments.apply(index);
}
auto __lambda2996__(auto index){
	return typeArguments.apply;
}
auto __lambda2997__(auto index){
	return typeArguments;
}
auto __lambda2998__(auto index){
	return typeArguments;
}
auto __lambda2999__(auto string){
	return String.equals(string);
}
auto __lambda3000__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3001__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(lists.indexof(last, stripped, string);
}
auto __lambda3002__(auto string){
	return String.equals(string);
}
auto __lambda3003__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3004__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda3005__(auto string){
	return String.equals(string);
}
auto __lambda3006__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3007__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda3008__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(lists.indexof(last, stripped, string);
}
auto __lambda3009__(auto string){
	return String.equals(string);
}
auto __lambda3010__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3011__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda3012__(auto string){
	return String.equals(string);
}
auto __lambda3013__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3014__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda3015__(auto string){
	return String.equals(string);
}
auto __lambda3016__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3017__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda3018__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index)(lists.indexof(last, stripped, string);
}
auto __lambda3019__(auto string){
	return String.equals(string);
}
auto __lambda3020__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3021__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda3022__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))(lists.indexof(last, stripped, string);
}
auto __lambda3023__(auto string){
	return String.equals(string);
}
auto __lambda3024__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3025__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda3026__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))
                .orElse(lists.indexof(last, stripped, string);
}
auto __lambda3027__(auto string){
	return String.equals(string);
}
auto __lambda3028__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3029__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda3030__(auto index){
	return typeArguments.apply(index).orElse(null);
}
auto __lambda3031__(auto index){
	return typeArguments.apply(index).orElse;
}
auto __lambda3032__(auto index){
	return typeArguments.apply(index);
}
auto __lambda3033__(auto index){
	return typeArguments.apply;
}
auto __lambda3034__(auto index){
	return typeArguments;
}
auto __lambda3035__(auto index){
	return typeArguments;
}
auto __lambda3036__(auto index){
	return typeArguments.apply(index);
}
auto __lambda3037__(auto index){
	return typeArguments.apply;
}
auto __lambda3038__(auto index){
	return typeArguments;
}
auto __lambda3039__(auto index){
	return typeArguments;
}
auto __lambda3040__(auto string){
	return String.equals(string);
}
auto __lambda3041__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3042__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(lists.indexof(last, stripped, string);
}
auto __lambda3043__(auto string){
	return String.equals(string);
}
auto __lambda3044__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3045__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda3046__(auto string){
	return String.equals(string);
}
auto __lambda3047__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3048__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda3049__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(lists.indexof(last, stripped, string);
}
auto __lambda3050__(auto string){
	return String.equals(string);
}
auto __lambda3051__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3052__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda3053__(auto string){
	return String.equals(string);
}
auto __lambda3054__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3055__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments(lists.indexof(last, stripped, string);
}
auto __lambda3056__(auto string){
	return String.equals(string);
}
auto __lambda3057__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3058__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda3059__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index)(lists.indexof(last, stripped, string);
}
auto __lambda3060__(auto string){
	return String.equals(string);
}
auto __lambda3061__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3062__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda3063__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))(lists.indexof(last, stripped, string);
}
auto __lambda3064__(auto string){
	return String.equals(string);
}
auto __lambda3065__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3066__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
auto __lambda3067__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index -> typeArguments.apply(index).orElse(null))
                .orElse(stripped)(lists.indexof(last, stripped, string);
}
auto __lambda3068__(auto string){
	return String.equals(string);
}
auto __lambda3069__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)(lists.indexof(last, stripped, string);
}
auto __lambda3070__(auto lists.indexof(last, stripped, string){
	return Lists.indexOf(last, stripped, String.equals)
                .map(index(lists.indexof(last, stripped, string);
}
/* private static */Result_String_CompileError compileTypeParam(String type, List__List__String frames, List__String typeArguments){
	String stripped = type.strip();
	if (1) {
	}
	List__String last = frames.last();
	return Ok(Lists.indexOf(last, stripped, __lambda2749__).map(__lambda2739__).orElse(stripped));
}
auto __lambda3071__(auto string){
	return String.equals(string);
}
auto __lambda3072__(auto string){
	return String.equals(string);
}
auto __lambda3073__(auto lists.equalsto(typeparams, typeparams2, string){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(lists.equalsto(typeparams, typeparams2, string);
}
auto __lambda3074__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3072__);
}
auto __lambda3075__(auto string){
	return String.equals(string);
}
auto __lambda3076__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3077__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3078__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3079__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3080__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3081__(auto __lambda3079__){
	return __lambda3079__.equals)(__lambda3079__);
}
auto __lambda3082__(auto string){
	return String.equals(string);
}
auto __lambda3083__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3084__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3085__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3086__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3087__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3088__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3089__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3090__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3091__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3092__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string::equals,
                        (typeparams, typeparams2) -> lists.equalsto(typeparams, typeparams2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String.equals))(tuples.equalsto(stringlisttuple, stringlisttuple2, string::equals,
                        (typeparams, typeparams2) -> lists.equalsto(typeparams, typeparams2, string);
}
auto __lambda3093__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3094__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3095__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, __lambda3071__, __lambda3074__);
}
auto __lambda3096__(auto string){
	return String.equals(string);
}
auto __lambda3097__(auto string){
	return String.equals(string);
}
auto __lambda3098__(auto lists.equalsto(typeparams, typeparams2, string){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(lists.equalsto(typeparams, typeparams2, string);
}
auto __lambda3099__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3097__);
}
auto __lambda3100__(auto string){
	return String.equals(string);
}
auto __lambda3101__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3102__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3103__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3104__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3105__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3106__(auto __lambda3104__){
	return __lambda3104__.equals)(__lambda3104__);
}
auto __lambda3107__(auto string){
	return String.equals(string);
}
auto __lambda3108__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo;
}
auto __lambda3109__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3110__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3111__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3112__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3113__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists;
}
auto __lambda3114__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3115__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3116__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3117__(auto __lambda3115__){
	return __lambda3115__.equals,
                        (typeParams, typeParams2) -> Lists(__lambda3115__);
}
auto __lambda3118__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3119__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3120__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3121__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3122__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3123__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3124__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3125__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3126__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3127__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3128__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists;
}
auto __lambda3129__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3130__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3131__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3132__(auto __lambda3130__){
	return __lambda3130__.equals,
                        (typeParams, typeParams2) -> Lists(__lambda3130__);
}
auto __lambda3133__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3134__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3135__(auto __lambda3133__){
	return __lambda3133__.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(__lambda3133__);
}
auto __lambda3136__(auto __lambda3124__){
	return __lambda3124__.equals))(__lambda3124__);
}
auto __lambda3137__(auto string){
	return String.equals(string);
}
auto __lambda3138__(auto string){
	return String.equals(string);
}
auto __lambda3139__(auto lists.equalsto(typeparams, typeparams2, string){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(lists.equalsto(typeparams, typeparams2, string);
}
auto __lambda3140__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3138__);
}
auto __lambda3141__(auto string){
	return String.equals(string);
}
auto __lambda3142__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3143__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3144__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3145__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3146__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3147__(auto __lambda3145__){
	return __lambda3145__.equals)(__lambda3145__);
}
auto __lambda3148__(auto string){
	return String.equals(string);
}
auto __lambda3149__(auto lists.contains(toexpand, tuple,
                (stringlisttuple, stringlisttuple2) -> tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Lists.contains(toExpand, tuple,
                (stringListTuple, stringListTuple2) -> Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(lists.contains(toexpand, tuple,
                (stringlisttuple, stringlisttuple2) -> tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3150__(auto lists.contains(toexpand, tuple,
                (stringlisttuple, stringlisttuple2) -> tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Lists.contains(toExpand, tuple,
                (stringListTuple, stringListTuple2) -> Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(lists.contains(toexpand, tuple,
                (stringlisttuple, stringlisttuple2) -> tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3151__(auto lists.contains(toexpand, tuple,
                (stringlisttuple, stringlisttuple2) -> tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Lists.contains(toExpand, tuple,
                (stringListTuple, stringListTuple2) -> Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(lists.contains(toexpand, tuple,
                (stringlisttuple, stringlisttuple2) -> tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3152__(auto lists.contains(toexpand, tuple,
                (stringlisttuple, stringlisttuple2) -> tuples.equalsto(stringlisttuple, stringlisttuple2, string::equals,
                        (typeparams, typeparams2) -> lists.equalsto(typeparams, typeparams2, string){
	return Lists.contains(toExpand, tuple,
                (stringListTuple, stringListTuple2) -> Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String.equals)))(lists.contains(toexpand, tuple,
                (stringlisttuple, stringlisttuple2) -> tuples.equalsto(stringlisttuple, stringlisttuple2, string::equals,
                        (typeparams, typeparams2) -> lists.equalsto(typeparams, typeparams2, string);
}
auto __lambda3153__(auto string){
	return String.equals(string);
}
auto __lambda3154__(auto string){
	return String.equals(string);
}
auto __lambda3155__(auto lists.equalsto(typeparams, typeparams2, string){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(lists.equalsto(typeparams, typeparams2, string);
}
auto __lambda3156__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3154__);
}
auto __lambda3157__(auto string){
	return String.equals(string);
}
auto __lambda3158__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3159__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3160__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3161__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3162__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3163__(auto __lambda3161__){
	return __lambda3161__.equals)(__lambda3161__);
}
auto __lambda3164__(auto string){
	return String.equals(string);
}
auto __lambda3165__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3166__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3167__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3168__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3169__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3170__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3171__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3172__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3173__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3174__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string::equals,
                        (typeparams, typeparams2) -> lists.equalsto(typeparams, typeparams2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String.equals))(tuples.equalsto(stringlisttuple, stringlisttuple2, string::equals,
                        (typeparams, typeparams2) -> lists.equalsto(typeparams, typeparams2, string);
}
auto __lambda3175__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3176__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3177__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, __lambda3153__, __lambda3156__);
}
auto __lambda3178__(auto string){
	return String.equals(string);
}
auto __lambda3179__(auto string){
	return String.equals(string);
}
auto __lambda3180__(auto lists.equalsto(typeparams, typeparams2, string){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(lists.equalsto(typeparams, typeparams2, string);
}
auto __lambda3181__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3179__);
}
auto __lambda3182__(auto string){
	return String.equals(string);
}
auto __lambda3183__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3184__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3185__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3186__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3187__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3188__(auto __lambda3186__){
	return __lambda3186__.equals)(__lambda3186__);
}
auto __lambda3189__(auto string){
	return String.equals(string);
}
auto __lambda3190__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo;
}
auto __lambda3191__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3192__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3193__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3194__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3195__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists;
}
auto __lambda3196__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3197__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3198__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3199__(auto __lambda3197__){
	return __lambda3197__.equals,
                        (typeParams, typeParams2) -> Lists(__lambda3197__);
}
auto __lambda3200__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3201__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3202__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3203__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3204__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3205__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3206__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3207__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2) -> Lists(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3208__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3209__(auto tuples.equalsto(stringlisttuple, stringlisttuple2, string){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String.equals,
                        (typeParams, typeParams2)(tuples.equalsto(stringlisttuple, stringlisttuple2, string);
}
auto __lambda3210__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String::equals,
                        (typeParams, typeParams2) -> Lists;
}
auto __lambda3211__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3212__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3213__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3214__(auto __lambda3212__){
	return __lambda3212__.equals,
                        (typeParams, typeParams2) -> Lists(__lambda3212__);
}
auto __lambda3215__(auto stringListTuple, auto stringListTuple2){
	return Tuples.equalsTo(stringListTuple, stringListTuple2, String;
}
auto __lambda3216__(auto stringListTuple, auto stringListTuple2){
	return Tuples;
}
auto __lambda3217__(auto __lambda3215__){
	return __lambda3215__.equals,
                        (typeParams, typeParams2) -> Lists.equalsTo(typeParams, typeParams2, String(__lambda3215__);
}
auto __lambda3218__(auto __lambda3206__){
	return __lambda3206__.equals))(__lambda3206__);
}
auto __lambda3219__(auto string){
	return String.equals(string);
}
auto __lambda3220__(auto string){
	return String.equals(string);
}
auto __lambda3221__(auto lists.equalsto(typeparams, typeparams2, string){
	return Lists.equalsTo(typeParams, typeParams2, String.equals)(lists.equalsto(typeparams, typeparams2, string);
}
auto __lambda3222__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, __lambda3220__);
}
auto __lambda3223__(auto string){
	return String.equals(string);
}
auto __lambda3224__(auto typeParams, auto typeParams2){
	return Lists.equalsTo;
}
auto __lambda3225__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3226__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3227__(auto typeParams, auto typeParams2){
	return Lists.equalsTo(typeParams, typeParams2, String;
}
auto __lambda3228__(auto typeParams, auto typeParams2){
	return Lists;
}
auto __lambda3229__(auto __lambda3227__){
	return __lambda3227__.equals)(__lambda3227__);
}
auto __lambda3230__(auto string){
	return String.equals(string);
}
/* private static */int isDefined(List__Tuple_String_List__String toExpand, Tuple_String_List__String tuple){
	return Lists.contains(toExpand, tuple, __lambda3095__);
}
/* private static */String generateGenericName(String base, List__String newArguments){
	String joined = newArguments.stream().collect(Joiner("_")).orElse("");
	return base + "_" + String.join("_", joined);
}
auto __lambda3231__(auto list_){
	return List_.stream(list_);
}
auto __lambda3232__(auto frames.stream().flatmap(list_){
	return frames.stream().flatMap(List_.stream)(frames.stream().flatmap(list_);
}
auto __lambda3233__(auto frames.stream().flatmap(list_){
	return frames.stream().flatMap(List_.stream)
                .next(frames.stream().flatmap(list_);
}
auto __lambda3234__(auto list_){
	return List_.stream(list_);
}
auto __lambda3235__(auto frames.stream().flatmap(list_){
	return frames.stream().flatMap(List_.stream)(frames.stream().flatmap(list_);
}
auto __lambda3236__(auto frames.stream().flatmap(list_){
	return frames.stream().flatMap(List_.stream)
                .next()(frames.stream().flatmap(list_);
}
auto __lambda3237__(auto list_){
	return List_.stream(list_);
}
/* private static */int hasNoTypeParams(List__List__String frames){
	Option_String next = frames.stream().flatMap(__lambda3231__).next();
	return next.isEmpty();
}
auto __lambda3238__(auto string){
	return String.equals(string);
}
auto __lambda3239__(auto lists.contains(frame, stripped, string){
	return Lists.contains(frame, stripped, String.equals)(lists.contains(frame, stripped, string);
}
auto __lambda3240__(auto frame){
	return Lists.contains(frame, stripped, __lambda3238__);
}
auto __lambda3241__(auto string){
	return String.equals(string);
}
auto __lambda3242__(auto frame){
	return Lists.contains;
}
auto __lambda3243__(auto frame){
	return Lists;
}
auto __lambda3244__(auto frame){
	return Lists;
}
auto __lambda3245__(auto frame){
	return Lists.contains(frame, stripped, String;
}
auto __lambda3246__(auto frame){
	return Lists;
}
auto __lambda3247__(auto __lambda3245__){
	return __lambda3245__.equals)(__lambda3245__);
}
auto __lambda3248__(auto string){
	return String.equals(string);
}
auto __lambda3249__(auto frames.stream().anymatch(frame -> lists.contains(frame, stripped, string){
	return frames.stream().anyMatch(frame -> Lists.contains(frame, stripped, String.equals))(frames.stream().anymatch(frame -> lists.contains(frame, stripped, string);
}
auto __lambda3250__(auto string){
	return String.equals(string);
}
auto __lambda3251__(auto lists.contains(frame, stripped, string){
	return Lists.contains(frame, stripped, String.equals)(lists.contains(frame, stripped, string);
}
auto __lambda3252__(auto frame){
	return Lists.contains(frame, stripped, __lambda3250__);
}
auto __lambda3253__(auto string){
	return String.equals(string);
}
auto __lambda3254__(auto frame){
	return Lists.contains;
}
auto __lambda3255__(auto frame){
	return Lists;
}
auto __lambda3256__(auto frame){
	return Lists;
}
auto __lambda3257__(auto frame){
	return Lists.contains(frame, stripped, String;
}
auto __lambda3258__(auto frame){
	return Lists;
}
auto __lambda3259__(auto __lambda3257__){
	return __lambda3257__.equals)(__lambda3257__);
}
auto __lambda3260__(auto string){
	return String.equals(string);
}
/* private static */int isTypeParam(List__List__String frames, String stripped){
	return frames.stream().anyMatch(__lambda3240__);
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
        public <R> */R match(R (*)(String) whenOk, R (*)(CompileError) whenErr){
	return whenErr.apply(error);
}
/* @Override
        public <R> */Result_R_CompileError mapValue(R (*)(String) mapper){
	return Err(error);
}
/* @Override
        public <R> */Result_R_CompileError flatMapValue(Result_R_CompileError (*)(String) mapper){
	return Err(error);
}
/* @Override
        public */Option_CompileError findError(){
	return Some(error);
}
/* @Override
        public <R> */Result_String_R mapErr(R (*)(CompileError) mapper){
	return Err(mapper.apply(error));
}
/* @Override
        public <R> */Result_Tuple_String_R_CompileError and(Result_R_CompileError (*)() mapper){
	return Err(error);
}
int main(){
	__main__();
	return 0;
}
