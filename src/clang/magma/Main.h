#ifndef magma_Main
#define magma_Main
#include "../windows/collect/list/Lists.h"
#include "../windows/collect/map/Maps.h"
#include "../windows/io/Paths.h"
#include "../windows/process/Processes.h"
#include "../magma/collect/list/List_.h"
#include "../magma/collect/map/Map_.h"
#include "../magma/collect/set/SetCollector.h"
#include "../magma/collect/set/Set_.h"
#include "../magma/collect/stream/Joiner.h"
#include "../magma/compile/Compiler.h"
#include "../magma/compile/Node.h"
#include "../magma/compile/source/PathSource.h"
#include "../magma/compile/source/Source.h"
#include "../magma/compile/transform/State.h"
#include "../magma/io/IOError.h"
#include "../magma/io/Path_.h"
#include "../magma/option/None.h"
#include "../magma/option/Option.h"
#include "../magma/option/Some.h"
#include "../magma/option/Tuple.h"
#include "../magma/result/Err.h"
#include "../magma/result/Ok.h"
#include "../magma/result/Result.h"
struct Main{struct Path_ SOURCE_DIRECTORY;struct Path_ TARGET_DIRECTORY;
};
// expand Option_ApplicationError = Option<struct ApplicationError>
// expand Set__Path_ = Set_<struct Path_>
// expand Option_ApplicationError = Option<struct ApplicationError>
// expand Set__Path_ = Set_<struct Path_>
// expand Option_ApplicationError = Option<struct ApplicationError>
// expand Map__Path__Node = Map_<struct Path_, struct Node>
// expand Result_Map__Path__Node_ApplicationError = Result<struct Map__Path__Node, struct ApplicationError>
// expand Map__Path__Node = Map_<struct Path_, struct Node>
// expand Map__Path__Node = Map_<struct Path_, struct Node>
// expand Result_List__Path__ApplicationError = Result<struct List__Path_, struct ApplicationError>
// expand List__Path_ = List_<struct Path_>
// expand List__Path_ = List_<struct Path_>
// expand Tuple_Path__Node = Tuple<struct Path_, struct Node>
// expand Option_ApplicationError = Option<struct ApplicationError>
// expand List__Path_ = List_<struct Path_>
// expand Option_ApplicationError = Option<struct ApplicationError>
// expand Result_List__Path__ApplicationError = Result<struct List__Path_, struct ApplicationError>
// expand List__Path_ = List_<struct Path_>
// expand Map__String_String = Map_<struct String, struct String>
// expand List__String = List_<struct String>
// expand Result_List__Path__ApplicationError = Result<struct List__Path_, struct ApplicationError>
// expand List__Path_ = List_<struct Path_>
// expand List__Path_ = List_<struct Path_>
// expand Result_Path__ApplicationError = Result<struct Path_, struct ApplicationError>
// expand Result_Path__ApplicationError = Result<struct Path_, struct ApplicationError>
// expand Ok_ = Ok<struct >
// expand Option_IOError = Option<struct IOError>
// expand None_ = None<struct >
// expand List__String = List_<struct String>
struct void main(struct String* args);
struct Option_ApplicationError runWithFiles(struct Set__Path_ files);
struct Option_ApplicationError runWithSources(struct Set__Path_ sources);
struct Option_ApplicationError postLoadTrees(struct Map__Path__Node trees);
struct Result_Map__Path__Node_ApplicationError preLoadSources(struct Map__Path__Node trees, struct Path_ path);
struct Result_List__Path__ApplicationError postLoadTree(struct List__Path_ relatives, struct Tuple_Path__Node pathNodeTuple);
struct Option_ApplicationError complete(struct List__Path_ relatives);
struct Option_ApplicationError startCommand();
struct Result_List__Path__ApplicationError writeOutputs(struct Map__String_String output, struct List__String namespace, struct String name);
struct Result_List__Path__ApplicationError writeAndFoldOutput(struct List__Path_ current, struct Path_ targetParent, struct String name, struct String extension, struct String output);
struct Result_Path__ApplicationError writeOutput(struct Path_ parent, struct String name, struct String extension, struct String output);
struct Option_IOError ensureDirectories(struct Path_ targetParent);
int isPlatformDependent(struct List__String namespace);
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda5__();
auto __lambda6__();
auto __lambda7__();
#endif

