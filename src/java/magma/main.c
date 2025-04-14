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
typedef struct {
	int length;
	int index;
	Option_int (*next)();
} RangeHead;
typedef struct {
	char left;
	DivideState right;
} Tuple_char_DivideState;
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
	char* delimiter;
	Option_char_ref (*createInitial)();
	Option_char_ref (*fold)();
} Joiner;
typedef struct {
	char left;
	List__char right;
} Tuple_char_List__char;
typedef struct {
	char* left;
	List__char_ref right;
} Tuple_char_ref_List__char_ref;
typedef struct {
} Iterators;
typedef struct {
	Option_R (*map)();
	char (*orElse)();
	int (*isPresent)();
	Option_char (*or)();
	char (*orElseGet)();
	Option_R (*flatMap)();
} Option_char;
typedef struct {
	Option_R (*map)();
	R (*orElse)();
	int (*isPresent)();
	Option_R (*or)();
	R (*orElseGet)();
	Option_R (*flatMap)();
} Option_R;
typedef struct {
	Option_R (*map)();
	T (*orElse)();
	int (*isPresent)();
	Option_T (*or)();
	T (*orElseGet)();
	Option_R (*flatMap)();
} Option_T;
typedef struct {
	Option_R (*map)();
	DivideState (*orElse)();
	int (*isPresent)();
	Option_DivideState (*or)();
	DivideState (*orElseGet)();
	Option_R (*flatMap)();
} Option_DivideState;
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
	Option_R (*map)();
	int (*orElse)();
	int (*isPresent)();
	Option_int (*or)();
	int (*orElseGet)();
	Option_R (*flatMap)();
} Option_int;
typedef struct {
	Option_R (*map)();
	char* (*orElse)();
	int (*isPresent)();
	Option_char_ref (*or)();
	char* (*orElseGet)();
	Option_R (*flatMap)();
} Option_char_ref;
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
