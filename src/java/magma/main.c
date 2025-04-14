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
	/* List<String>*/ (*segments)();
	int (*isLevel)();
	struct DivideState (*enter)();
	struct DivideState (*exit)();
	int (*isShallow)();
	int (*hasNext)();
	char (*pop)();
};
struct MutableDivideState {
	/* Deque<Character>*/ queue;
	/* List<String>*/ segments;
	int depth;
	struct StringBuilder buffer;
	struct DivideState (*advance)();
	struct DivideState (*append)();
	/* List<String>*/ (*segments)();
	int (*isLevel)();
	struct DivideState (*enter)();
	struct DivideState (*exit)();
	int (*isShallow)();
	int (*hasNext)();
	char (*pop)();
	struct DivideState (*popAndAppend)();
};
struct Node(String beforeType, String type, String name) {
};
struct Main {
};
