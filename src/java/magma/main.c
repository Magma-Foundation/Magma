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
struct List_ {
	struct T (*get)();
	int (*size)();
	/* List_<T>*/ (*add)();
};
struct DivideState {
	struct DivideState (*popAndAppend)();
	struct DivideState (*advance)();
	struct DivideState (*append)();
	/* List_<String>*/ (*segments)();
	int (*isLevel)();
	struct DivideState (*enter)();
	struct DivideState (*exit)();
	int (*isShallow)();
	int (*hasNext)();
	char (*pop)();
};
struct MutableDivideState {
	/* Deque<Character>*/ queue;
	/* List_<String>*/ segments;
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
	/* List_<String>*/ (*segments)();
};
struct Node {
};
struct Main {
};
