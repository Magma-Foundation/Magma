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
	String value;
} String_;
typedef struct {
	int length;
	int index;
	Option_Integer (*next)();
} RangeHead;
/* Tuple_char_DivideState*//* Tuple_String_List__String*//* Tuple_char_List__char*/typedef struct {
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
/* Option_String*//* Option_char*//* Option_DivideState*//* Option_R*//* Option_T*//* Option_Integer*/typedef struct {
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
/* List__char*//* List__String*//* Iterator_char*//* Iterator_String*//* Iterator_R*//* Iterator_T*/typedef struct {
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
