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
struct MutableDivideState implements DivideState {
	/* private final*/ /* Deque<Character>*/ queue;
	/* private final*/ /* List<String>*/ segments;
	/* private*/ int depth;
	/* private*/ struct StringBuilder buffer;
	/* @Override
        public*/ struct DivideState (*advance)();
	/* @Override
        public*/ struct DivideState (*append)();
	/* @Override
        public*/ /* List<String>*/ (*segments)();
	/* @Override
        public*/ int (*isLevel)();
	/* @Override
        public*/ struct DivideState (*enter)();
	/* @Override
        public*/ struct DivideState (*exit)();
	/* @Override
        public*/ int (*isShallow)();
	/* @Override
        public*/ int (*hasNext)();
	/* @Override
        public*/ char (*pop)();
	/* @Override
        public*/ struct DivideState (*popAndAppend)();
};
struct Main {
	/* private*/ struct record (*Node)();
	/* private static final List<String> structs =*/ struct new (*ArrayList<>)();
	/* public static*/ struct void (*main)();
	/* private static*/ struct String (*compile)();
	/* private static*/ struct String (*compileStatements)();
	/* private static*/ struct String (*mergeStatements)();
	/* private static*/ /* ArrayList<String>*/ (*compileStatementsToList)();
	/* private static*/ struct String (*compileRootSegment)();
	/* private static*/ /* Optional<String>*/ (*compileWhitespace)();
	/* private static*/ /* Optional<String>*/ (*compileClass)();
	/* private static*/ /* Optional<String>*/ (*compileToStruct)();
	/* private static*/ /* Optional<String>*/ (*compileSuffix)();
	/* private static*/ /* Optional<String>*/ (*compileInfix)();
	/* private static*/ /* Optional<String>*/ (*compileInfix)();
	/* private static*/ int (*locateFirst)();
	/* private static*/ struct String (*compileClassMember)();
	/* private static Optional<? extends*/ /* String>*/ (*compileConstructor)();
	/* private static*/ /* Optional<String>*/ (*compileDefinitionStatement)();
	/* private static*/ /* Optional<String>*/ (*generateDefinition)();
	/* private static*/ /* Optional<String>*/ (*compileMethod)();
	/* private static*/ /* Optional<String>*/ (*compileDefinition)();
	/* private static*/ /* Optional<String>*/ (*generateFunctionalDefinition)();
	/* private static*/ /* Optional<String>*/ (*generateStatement)();
	/* private static*/ /* Optional<String>*/ (*compileType)();
	/* private static*/ int (*isSymbol)();
	/* private static*/ /* List<String>*/ (*divide)();
	/* private static*/ struct DivideState (*divideDecorated)();
	/* private static*/ /* Optional<DivideState>*/ (*divideSingleQuotes)();
	/* private static*/ struct DivideState (*divideStatementChar)();
	/* private static*/ struct String (*generatePlaceholder)();
};
