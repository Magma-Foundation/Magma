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
struct DivideState {
	struct DivideState (*popAndAppend)();
	struct DivideState (*advance)();
	struct DivideState (*append)();
	List__String (*segments)();
	int (*isLevel)();
	struct DivideState (*enter)();
	struct DivideState (*exit)();
	int (*isShallow)();
	int (*hasNext)();
	char (*pop)();
};
struct MutableDivideState {
	Deque_Character queue;
	List__String segments;
	struct StringBuilder buffer;
	int depth;
	struct DivideState (*advance)();
	struct DivideState (*append)();
	int (*isLevel)();
	struct DivideState (*enter)();
	struct DivideState (*exit)();
	int (*isShallow)();
	int (*hasNext)();
	char (*pop)();
	struct DivideState (*popAndAppend)();
	List__String (*segments)();
};
struct Node {
};
struct Main {
};
struct List__String {
	struct T (*get)();
	int (*size)();
	List__T (*add)();
};
struct Tuple_String List_<String> {
};
