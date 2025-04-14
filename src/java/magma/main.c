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
/* Function_char_ref_char_ref*//* Function_char_ref_Option_char_ref*//* BiFunction_char_ref_char_ref_Option_char_ref*//* BiFunction_char_ref_char_ref_int*//* Function_Node_Option_char_ref*//* BiFunction_DivideState_char_DivideState*//* BiFunction_char_char_int*//* BiFunction_DivideState_DivideState_int*//* Function_T_R*//* Predicate_T*//* BiFunction_R_T_R*//* Function_T_Iterator_R*//* BiFunction_List__char_ref_List__char_ref_int*//* BiFunction_Tuple_char_ref_List__char_ref_Tuple_char_ref_List__char_ref_int*//* Function_char_ref_R*//* Predicate_char_ref*//* BiFunction_R_char_ref_R*//* Function_char_ref_Iterator_R*//* BiFunction_List__char_List__char_int*//* Function_char_R*//* Predicate_char*//* BiFunction_R_char_R*//* Function_char_Iterator_R*//* Function_R_R*//* Predicate_R*//* BiFunction_R_R_R*//* Function_R_Iterator_R*//* BiFunction_List__Tuple_char_ref_List__char_ref_List__Tuple_char_ref_List__char_ref_int*//* Function_Tuple_char_ref_List__char_ref_R*//* Predicate_Tuple_char_ref_List__char_ref*//* BiFunction_R_Tuple_char_ref_List__char_ref_R*//* Function_Tuple_char_ref_List__char_ref_Iterator_R*/typedef struct {
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
	C (*createInitial)();
	C (*fold)();
} Collector_char_C;
typedef struct {
	C (*createInitial)();
	C (*fold)();
} Collector_R_C;
typedef struct {
	C (*createInitial)();
	C (*fold)();
} Collector_T_C;
typedef struct {
	C (*createInitial)();
	C (*fold)();
} Collector_Tuple_char_ref_List__char_ref_C;
typedef struct {
	C (*createInitial)();
	C (*fold)();
} Collector_char_ref_C;
typedef struct {
} Iterators;
typedef struct {
	DivideState (*popAndAppend)();
	DivideState (*advance)();
	DivideState (*append)();
	List__char_ref (*segments)();
	int (*isLevel)();
	DivideState (*enter)();
	DivideState (*exit)();
	int (*isShallow)();
	int (*hasNext)();
	Tuple_char_DivideState (*pop)();
} DivideState;
typedef struct {
	Tuple_char_ref_List__char_ref left;
	List__Tuple_char_ref_List__char_ref right;
} Tuple_Tuple_char_ref_List__char_ref_List__Tuple_char_ref_List__char_ref;
typedef struct {
	char left;
	DivideState right;
} Tuple_char_DivideState;
typedef struct {
	char left;
	List__char right;
} Tuple_char_List__char;
typedef struct {
	char* left;
	List__char_ref right;
} Tuple_char_ref_List__char_ref;
typedef struct {
	List__char queue;
	List__char_ref segments;
	StringBuilder buffer;
	int depth;
	DivideState (*advance)();
	DivideState (*append)();
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
	Tuple_char_ref_List__char_ref (*get)();
	int (*size)();
	List__Tuple_char_ref_List__char_ref (*add)();
	int (*isEmpty)();
	Tuple_Tuple_char_ref_List__char_ref_List__Tuple_char_ref_List__char_ref (*pop)();
	Iterator_Tuple_char_ref_List__char_ref (*iter)();
	List__Tuple_char_ref_List__char_ref (*addAll)();
	List__Tuple_char_ref_List__char_ref (*sort)();
} List__Tuple_char_ref_List__char_ref;
typedef struct {
	char (*get)();
	int (*size)();
	List__char (*add)();
	int (*isEmpty)();
	Tuple_char_List__char (*pop)();
	Iterator_char (*iter)();
	List__char (*addAll)();
	List__char (*sort)();
} List__char;
typedef struct {
	char* (*get)();
	int (*size)();
	List__char_ref (*add)();
	int (*isEmpty)();
	Tuple_char_ref_List__char_ref (*pop)();
	Iterator_char_ref (*iter)();
	List__char_ref (*addAll)();
	List__char_ref (*sort)();
} List__char_ref;
typedef struct {
	Head_R head;
	Iterator_R (*map)();
	C (*collect)();
	Iterator_R (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_R (*concat)();
	Option_R (*next)();
} Iterator_R;
typedef struct {
	Head_T head;
	Iterator_R (*map)();
	C (*collect)();
	Iterator_T (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_T (*concat)();
	Option_T (*next)();
} Iterator_T;
typedef struct {
	Head_Tuple_char_ref_List__char_ref head;
	Iterator_R (*map)();
	C (*collect)();
	Iterator_Tuple_char_ref_List__char_ref (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_Tuple_char_ref_List__char_ref (*concat)();
	Option_Tuple_char_ref_List__char_ref (*next)();
} Iterator_Tuple_char_ref_List__char_ref;
typedef struct {
	Head_char_ref head;
	Iterator_R (*map)();
	C (*collect)();
	Iterator_char_ref (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_char_ref (*concat)();
	Option_char_ref (*next)();
} Iterator_char_ref;
typedef struct {
	Head_char head;
	Iterator_R (*map)();
	C (*collect)();
	Iterator_char (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_char (*concat)();
	Option_char (*next)();
} Iterator_char;
typedef struct {
} Main;
