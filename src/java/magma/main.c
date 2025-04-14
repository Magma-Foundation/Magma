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
#include <temp.h>
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
	char (*pop)();
} DivideState;
typedef struct {
	Deque_Character queue;
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
	char (*pop)();
	DivideState (*popAndAppend)();
	List__String (*segments)();
} MutableDivideState;
typedef struct {
} Node;
typedef struct {
} Main;
typedef struct {
	T (*get)();
	int (*size)();
	List__T (*add)();
	int (*isEmpty)();
	Tuple_T_List__T (*pop)();
} List__String;
typedef struct {
} Tuple_String_List__String;
typedef struct {
	T (*get)();
	int (*size)();
	List__T (*add)();
	int (*isEmpty)();
	Tuple_T_List__T (*pop)();
} List__T;
typedef struct {
} Tuple_T_List__T;
