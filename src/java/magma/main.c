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
	String beforeType;
	String type;
	String name;
} Node;
typedef struct {
} Main;
typedef struct {
	String (*get)();
	int (*size)();
	List__String (*add)();
	int (*isEmpty)();
	Tuple_String_List__String (*pop)();
} List__String;
typedef struct {
	String left;
	List__String right;
} Tuple_String_List__String;
