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
#include "../magma/compile/MapNode.h"
#include "../magma/compile/Node.h"
#include "../magma/compile/source/Location.h"
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
struct Main{Path_ SOURCE_DIRECTORY;Path_ TARGET_DIRECTORY;
};
// expand Option<ApplicationError>
// expand Set_<Path_>
// expand Option<ApplicationError>
// expand Set_<Path_>
// expand Map_<Location, Node>
// expand Map_<Location, Node>
// expand List_<Node>
// expand List_<Node>
// expand Option<ApplicationError>
// expand Map_<Location, Node>
// expand Result<List_<Path_>, ApplicationError>
// expand List_<Path_>
// expand List_<Path_>
// expand List_<Path_>
// expand Tuple<Location, Node>
// expand Result<Map_<Location, Node>, ApplicationError>
// expand Map_<Location, Node>
// expand Map_<Location, Node>
// expand Map_<Location, Node>
// expand Option<ApplicationError>
// expand List_<Path_>
// expand Option<ApplicationError>
// expand Result<List_<Path_>, ApplicationError>
// expand List_<Path_>
// expand List_<Path_>
// expand Map_<String, String>
// expand List_<String>
// expand Result<List_<Path_>, ApplicationError>
// expand List_<Path_>
// expand List_<Path_>
// expand List_<Path_>
// expand Result<Path_, ApplicationError>
// expand Result<Path_, ApplicationError>
// expand Option<IOError>
// expand None<>
// expand Ok<>
int __lambda0__();
int __lambda1__();
int __lambda2__();
int __lambda3__();
int __lambda4__();
int __lambda5__();
int __lambda6__();
int __lambda7__();
void main(String* args);
Option<ApplicationError> runWithFiles(Set_<Path_> files);
Option<ApplicationError> runWithSources(Set_<Path_> sources);
Map_<Location, Node> modifyTrees(Map_<Location, Node> trees);
List_<Node> getNodeList(List_<Node> nodeList, Node node);
Option<ApplicationError> postLoadTrees(Map_<Location, Node> trees);
Result<List_<Path_>, ApplicationError> postLoadTree(List_<Path_> relatives, Tuple<Location, Node> pathNodeTuple);
Result<Map_<Location, Node>, ApplicationError> preLoadSources(Map_<Location, Node> trees, Path_ path);
Option<ApplicationError> complete(List_<Path_> relatives);
Option<ApplicationError> startCommand();
Result<List_<Path_>, ApplicationError> writeOutputs(Map_<String, String> output, List_<String> namespace, String name);
Result<List_<Path_>, ApplicationError> writeAndFoldOutput(List_<Path_> current, Path_ targetParent, String name, String extension, String output);
Result<Path_, ApplicationError> writeOutput(Path_ parent, String name, String extension, String output);
Option<IOError> ensureDirectories(Path_ targetParent);
int isPlatformDependent(Location location);
#endif
