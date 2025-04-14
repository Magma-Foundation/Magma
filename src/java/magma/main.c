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
#include <temp.h>
/* Map_char_ref_String_*//* Function_char_ref_char_ref*//* Function_char_ref_T*//* Function_char_ref_Option_char_ref*//* BiFunction_char_ref_char_ref_Option_char_ref*//* BiFunction_char_ref_char_ref_int*//* BiFunction_char_ref_char_ref_Option_T*//* BiFunction_DivideState_char_DivideState*//* BiFunction_char_char_int*//* BiFunction_DivideState_DivideState_int*//* Function_T_R*//* Predicate_T*//* BiFunction_R_T_R*//* Function_T_Iterator_R*//* BiFunction_List__char_ref_List__char_ref_int*//* BiFunction_T_T_int*//* BiFunction_String__String__int*//* BiFunction_Tuple_char_ref_List__char_ref_Tuple_char_ref_List__char_ref_int*//* Function_char_ref_R*//* Predicate_char_ref*//* BiFunction_R_char_ref_R*//* Function_char_ref_Iterator_R*//* BiFunction_List__char_List__char_int*//* Function_char_R*//* Predicate_char*//* BiFunction_R_char_R*//* Function_char_Iterator_R*//* Function_R_R*//* Predicate_R*//* BiFunction_R_R_R*//* Function_R_Iterator_R*//* BiFunction_List__T_List__T_int*//* BiFunction_List__String__List__String__int*//* Function_String__R*//* Predicate_String_*//* BiFunction_R_String__R*//* Function_String__Iterator_R*//* BiFunction_List__Tuple_char_ref_List__char_ref_List__Tuple_char_ref_List__char_ref_int*//* Function_Tuple_char_ref_List__char_ref_R*//* Predicate_Tuple_char_ref_List__char_ref*//* BiFunction_R_Tuple_char_ref_List__char_ref_R*//* Function_Tuple_char_ref_List__char_ref_Iterator_R*/typedef struct {
	Option_Tuple_char_ref_List__char_ref (*next)();
} Head_Tuple_char_ref_List__char_ref;
typedef struct {
	Option_char_ref (*next)();
} Head_char_ref;
typedef struct {
	Option_R (*next)();
} Head_R;
typedef struct {
	Option_T (*next)();
} Head_T;
typedef struct {
	Option_char (*next)();
} Head_char;
typedef struct {
	Map_char_ref_String_ internalMap;
	Node (*withString)(char*, String_);
	Option_String_ (*findString)(char*);
} Node;
typedef struct {
	C (*createInitial)();
	C (*fold)(C, String_);
} Collector_String__C;
typedef struct {
	C (*createInitial)();
	C (*fold)(C, char);
} Collector_char_C;
typedef struct {
	C (*createInitial)();
	C (*fold)(C, R);
} Collector_R_C;
typedef struct {
	C (*createInitial)();
	C (*fold)(C, T);
} Collector_T_C;
typedef struct {
	C (*createInitial)();
	C (*fold)(C, Tuple_char_ref_List__char_ref);
} Collector_Tuple_char_ref_List__char_ref_C;
typedef struct {
	C (*createInitial)();
	C (*fold)(C, char*);
} Collector_char_ref_C;
typedef struct {
	 (*)(Option_T);
	 (*)();
} Iterators;
typedef struct {
	DivideState (*popAndAppend)();
	DivideState (*advance)();
	DivideState (*append)(char);
	List__char_ref (*segments)();
	int (*isLevel)();
	DivideState (*enter)();
	DivideState (*exit)();
	int (*isShallow)();
	int (*hasNext)();
	Tuple_char_DivideState (*pop)();
} DivideState;
typedef struct {
	String_ (*get)(int);
	int (*size)();
	List__String_ (*add)(String_);
	int (*isEmpty)();
	Tuple_String__List__String_ (*pop)();
	Iterator_String_ (*iter)();
	List__String_ (*addAll)(List__String_);
	List__String_ (*sort)(BiFunction_String__String__int);
} List__String_;
typedef struct {
	String_ left;
	List__String_ right;
	 (*)(Tuple_String__List__String_, Tuple_String__List__String_, BiFunction_String__String__int, BiFunction_List__String__List__String__int);
} Tuple_String__List__String_;
typedef struct {
	Tuple_char_ref_List__char_ref left;
	List__Tuple_char_ref_List__char_ref right;
	 (*)(Tuple_Tuple_char_ref_List__char_ref_List__Tuple_char_ref_List__char_ref, Tuple_Tuple_char_ref_List__char_ref_List__Tuple_char_ref_List__char_ref, BiFunction_Tuple_char_ref_List__char_ref_Tuple_char_ref_List__char_ref_int, BiFunction_List__Tuple_char_ref_List__char_ref_List__Tuple_char_ref_List__char_ref_int);
} Tuple_Tuple_char_ref_List__char_ref_List__Tuple_char_ref_List__char_ref;
typedef struct {
	T left;
	List__T right;
	 (*)(Tuple_T_List__T, Tuple_T_List__T, BiFunction_T_T_int, BiFunction_List__T_List__T_int);
} Tuple_T_List__T;
typedef struct {
	char left;
	DivideState right;
	 (*)(Tuple_char_DivideState, Tuple_char_DivideState, BiFunction_char_char_int, BiFunction_DivideState_DivideState_int);
} Tuple_char_DivideState;
typedef struct {
	char left;
	List__char right;
	 (*)(Tuple_char_List__char, Tuple_char_List__char, BiFunction_char_char_int, BiFunction_List__char_List__char_int);
} Tuple_char_List__char;
typedef struct {
	char* left;
	List__char_ref right;
	 (*)(Tuple_char_ref_List__char_ref, Tuple_char_ref_List__char_ref, BiFunction_char_ref_char_ref_int, BiFunction_List__char_ref_List__char_ref_int);
} Tuple_char_ref_List__char_ref;
typedef struct {
	List__char queue;
	List__char_ref segments;
	StringBuilder buffer;
	int depth;
	DivideState (*advance)();
	DivideState (*append)(char);
	int (*isLevel)();
	DivideState (*enter)();
	DivideState (*exit)();
	int (*isShallow)();
	int (*hasNext)();
	Tuple_char_DivideState (*pop)();
	DivideState (*popAndAppend)();
	List__char_ref (*segments)();
} MutableDivideState;
typedef struct {
	Tuple_char_ref_List__char_ref (*get)(int);
	int (*size)();
	List__Tuple_char_ref_List__char_ref (*add)(Tuple_char_ref_List__char_ref);
	int (*isEmpty)();
	Tuple_Tuple_char_ref_List__char_ref_List__Tuple_char_ref_List__char_ref (*pop)();
	Iterator_Tuple_char_ref_List__char_ref (*iter)();
	List__Tuple_char_ref_List__char_ref (*addAll)(List__Tuple_char_ref_List__char_ref);
	List__Tuple_char_ref_List__char_ref (*sort)(BiFunction_Tuple_char_ref_List__char_ref_Tuple_char_ref_List__char_ref_int);
} List__Tuple_char_ref_List__char_ref;
typedef struct {
	T (*get)(int);
	int (*size)();
	List__T (*add)(T);
	int (*isEmpty)();
	Tuple_T_List__T (*pop)();
	Iterator_T (*iter)();
	List__T (*addAll)(List__T);
	List__T (*sort)(BiFunction_T_T_int);
} List__T;
typedef struct {
	char (*get)(int);
	int (*size)();
	List__char (*add)(char);
	int (*isEmpty)();
	Tuple_char_List__char (*pop)();
	Iterator_char (*iter)();
	List__char (*addAll)(List__char);
	List__char (*sort)(BiFunction_char_char_int);
} List__char;
typedef struct {
	char* (*get)(int);
	int (*size)();
	List__char_ref (*add)(char*);
	int (*isEmpty)();
	Tuple_char_ref_List__char_ref (*pop)();
	Iterator_char_ref (*iter)();
	List__char_ref (*addAll)(List__char_ref);
	List__char_ref (*sort)(BiFunction_char_ref_char_ref_int);
} List__char_ref;
typedef struct {
	Head_String_ head;
	Iterator_R (*map)(Function_String__R);
	C (*collect)(Collector_String__C);
	Iterator_String_ (*filter)(Predicate_String_);
	R (*fold)(R, BiFunction_R_String__R);
	Iterator_R (*flatMap)(Function_String__Iterator_R);
	Iterator_String_ (*concat)(Iterator_String_);
	Option_String_ (*next)();
} Iterator_String_;
typedef struct {
	Head_R head;
	Iterator_R (*map)(Function_R_R);
	C (*collect)(Collector_R_C);
	Iterator_R (*filter)(Predicate_R);
	R (*fold)(R, BiFunction_R_R_R);
	Iterator_R (*flatMap)(Function_R_Iterator_R);
	Iterator_R (*concat)(Iterator_R);
	Option_R (*next)();
} Iterator_R;
typedef struct {
	Head_T head;
	Iterator_R (*map)(Function_T_R);
	C (*collect)(Collector_T_C);
	Iterator_T (*filter)(Predicate_T);
	R (*fold)(R, BiFunction_R_T_R);
	Iterator_R (*flatMap)(Function_T_Iterator_R);
	Iterator_T (*concat)(Iterator_T);
	Option_T (*next)();
} Iterator_T;
typedef struct {
	Head_Tuple_char_ref_List__char_ref head;
	Iterator_R (*map)(Function_Tuple_char_ref_List__char_ref_R);
	C (*collect)(Collector_Tuple_char_ref_List__char_ref_C);
	Iterator_Tuple_char_ref_List__char_ref (*filter)(Predicate_Tuple_char_ref_List__char_ref);
	R (*fold)(R, BiFunction_R_Tuple_char_ref_List__char_ref_R);
	Iterator_R (*flatMap)(Function_Tuple_char_ref_List__char_ref_Iterator_R);
	Iterator_Tuple_char_ref_List__char_ref (*concat)(Iterator_Tuple_char_ref_List__char_ref);
	Option_Tuple_char_ref_List__char_ref (*next)();
} Iterator_Tuple_char_ref_List__char_ref;
typedef struct {
	Head_char_ref head;
	Iterator_R (*map)(Function_char_ref_R);
	C (*collect)(Collector_char_ref_C);
	Iterator_char_ref (*filter)(Predicate_char_ref);
	R (*fold)(R, BiFunction_R_char_ref_R);
	Iterator_R (*flatMap)(Function_char_ref_Iterator_R);
	Iterator_char_ref (*concat)(Iterator_char_ref);
	Option_char_ref (*next)();
} Iterator_char_ref;
typedef struct {
	Head_char head;
	Iterator_R (*map)(Function_char_R);
	C (*collect)(Collector_char_C);
	Iterator_char (*filter)(Predicate_char);
	R (*fold)(R, BiFunction_R_char_R);
	Iterator_R (*flatMap)(Function_char_Iterator_R);
	Iterator_char (*concat)(Iterator_char);
	Option_char (*next)();
} Iterator_char;
typedef struct {
	 (*)();
	 (*)();
	 (*)();
	 (*)();
	 (*)();
	 (*)();
	 (*)();
	 (*)(char**);
	 (*)(char*);
	 (*)(List__char_ref);
	 (*)();
	 (*)(Tuple_char_ref_List__char_ref, List__char_ref);
	 (*)(char*, Function_char_ref_char_ref);
	 (*)(List__char_ref);
	 (*)(char*, Function_char_ref_char_ref);
	 (*)(List__char_ref, Function_char_ref_T);
	 (*)(char*);
	 (*)(char*);
	 (*)(char*);
	 (*)(char*, char*);
	 (*)(char*, char*, List__char_ref);
	 (*)(char*);
	 (*)(char*, char*, List__char_ref, List__char_ref);
	 (*)(char*, List__char_ref);
	 (*)(char*, List__char_ref, List__char_ref, List__char_ref, char*, List__char_ref);
	 (*)(char*, char*);
	 (*)(char*, char*, Function_char_ref_Option_char_ref);
	 (*)(char*, char*, BiFunction_char_ref_char_ref_Option_char_ref);
	 (*)(char*, char*, BiFunction_char_ref_char_ref_int, BiFunction_char_ref_char_ref_Option_T);
	 (*)(char*, char*);
	 (*)(char*, List__char_ref, List__char_ref, List__char_ref);
	 (*)(char*);
	 (*)(char*, List__char_ref, List__char_ref);
	 (*)(Node);
	 (*)(char*, List__char_ref, List__char_ref, List__char_ref);
	 (*)(char*, List__char_ref, List__char_ref);
	 (*)(List__char_ref, List__char_ref, Node, char*, char*);
	 (*)(char*);
	 (*)(Node, List__String_);
	 (*)(char*);
	 (*)(char*, List__char_ref, List__char_ref);
	 (*)(char*, List__char_ref, List__char_ref);
	 (*)(List__char_ref, List__char_ref, char*);
	 (*)(DivideState, char);
	 (*)(char*, List__char_ref, List__Tuple_char_ref_List__char_ref);
	 (*)(char*);
	 (*)(char*);
	 (*)(char*, BiFunction_DivideState_char_DivideState);
	 (*)(DivideState, char, BiFunction_DivideState_char_DivideState);
	 (*)(DivideState, char);
	 (*)(DivideState, char);
	 (*)(DivideState, char);
	 (*)(char*);
} Main;
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
void temp(){
}
