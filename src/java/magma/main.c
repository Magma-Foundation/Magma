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
#include <temp.h>
typedef struct {
	char* (*asCharArray)();
} String_;
typedef struct {
	int length;
	int index;
	Option_Integer (*next)();
} RangeHead;
typedef struct {
	char left;
	DivideState right;
} Tuple_char_DivideState;
typedef struct {
	String left;
	List__String right;
} Tuple_String_List__String;
typedef struct {
	char left;
	List__char right;
} Tuple_char_List__char;
typedef struct {
	char* value;
	char* (*asCharArray)();
} JavaString;
typedef struct {
	String_ beforeType;
	String_ type;
	String_ name;
} Node;
typedef struct {
	Option_String (*createInitial)();
	Option_String (*fold)();
} Joiner;
typedef struct {
} Iterators;
typedef struct {
	Option_R (*map)();
	String (*orElse)();
	int (*isPresent)();
	Option_String (*or)();
	String (*orElseGet)();
	Option_R (*flatMap)();
} Option_String;
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
	DivideState (*orElse)();
	int (*isPresent)();
	Option_DivideState (*or)();
	DivideState (*orElseGet)();
	Option_R (*flatMap)();
} Option_DivideState;
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
	Integer (*orElse)();
	int (*isPresent)();
	Option_Integer (*or)();
	Integer (*orElseGet)();
	Option_R (*flatMap)();
} Option_Integer;
typedef struct {
	DivideState (*popAndAppend)();
	DivideState (*advance)();
	DivideState (*append)();
	List__String (*segments)();
	int (*isLevel)();
	DivideState (*enter)();
	DivideState (*exit)();
	int (*isShallow)();
	int (*hasNext)();
	Tuple_char_DivideState (*pop)();
} DivideState;
typedef struct {
	char (*get)();
	int (*size)();
	List__char (*add)();
	int (*isEmpty)();
	Tuple_char_List__char (*pop)();
	Iterator_char (*iter)();
	List__char (*addAll)();
} List__char;
typedef struct {
	String (*get)();
	int (*size)();
	List__String (*add)();
	int (*isEmpty)();
	Tuple_String_List__String (*pop)();
	Iterator_String (*iter)();
	List__String (*addAll)();
} List__String;
typedef struct {
	Iterator_R (*map)();
	C (*collect)();
	Iterator_char (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_char (*concat)();
	Option_char (*next)();
} Iterator_char;
typedef struct {
	Iterator_R (*map)();
	C (*collect)();
	Iterator_String (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_String (*concat)();
	Option_String (*next)();
} Iterator_String;
typedef struct {
	Iterator_R (*map)();
	C (*collect)();
	Iterator_R (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_R (*concat)();
	Option_R (*next)();
} Iterator_R;
typedef struct {
	Iterator_R (*map)();
	C (*collect)();
	Iterator_T (*filter)();
	R (*fold)();
	Iterator_R (*flatMap)();
	Iterator_T (*concat)();
	Option_T (*next)();
} Iterator_T;
typedef struct {
	List__char queue;
	List__String segments;
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
	List__String (*segments)();
} MutableDivideState;
typedef struct {
} Main;
