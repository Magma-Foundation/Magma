#ifndef magma_Main
#define magma_Main
#include "../windows/collect/list/Lists.h"
#include "../windows/collect/map/Maps.h"
#include "../windows/io/Paths.h"
#include "../windows/process/Processes.h"
#include "../magma/collect/list/ListCollector.h"
#include "../magma/collect/list/List_.h"
#include "../magma/collect/map/Map_.h"
#include "../magma/collect/set/SetCollector.h"
#include "../magma/collect/set/Set_.h"
#include "../magma/collect/stream/Joiner.h"
#include "../magma/collect/stream/Stream.h"
#include "../magma/compile/Compiler.h"
#include "../magma/compile/MapNode.h"
#include "../magma/compile/Node.h"
#include "../magma/compile/lang/CommonLang.h"
#include "../magma/compile/lang/JavaLang.h"
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
#include "../java/util/List.h"
struct Main{magma.io.Path_ SOURCE_DIRECTORY;magma.io.Path_ TARGET_DIRECTORY;
};
// expand magma.option.Option<magma.ApplicationError>
// expand magma.collect.set.Set_<magma.io.Path_>
// expand magma.option.Option<magma.ApplicationError>
// expand magma.collect.set.Set_<magma.io.Path_>
// expand magma.collect.map.Map_<magma.io.Path_, magma.compile.Node>
// expand magma.collect.set.SetCollector<>
// expand magma.collect.list.ListCollector<>
// expand magma.collect.map.Map_<magma.io.Path_, magma.compile.Node>
// expand magma.collect.stream.Stream<magma.compile.Node>
// expand magma.collect.stream.Stream<magma.compile.Node>
// expand magma.option.Option<magma.ApplicationError>
// expand magma.collect.map.Map_<magma.io.Path_, magma.compile.Node>
// expand magma.result.Result<magma.collect.map.Map_<magma.io.Path_, magma.compile.Node>, magma.ApplicationError>
// expand magma.collect.map.Map_<magma.io.Path_, magma.compile.Node>
// expand magma.collect.map.Map_<magma.io.Path_, magma.compile.Node>
// expand magma.collect.map.Map_<magma.io.Path_, magma.compile.Node>
// expand magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError>
// expand magma.collect.list.List_<magma.io.Path_>
// expand magma.collect.list.List_<magma.io.Path_>
// expand magma.collect.list.List_<magma.io.Path_>
// expand magma.option.Tuple<magma.io.Path_, magma.compile.Node>
// expand magma.option.Option<magma.ApplicationError>
// expand magma.collect.list.List_<magma.io.Path_>
// expand magma.option.Option<magma.ApplicationError>
// expand magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError>
// expand magma.collect.list.List_<magma.io.Path_>
// expand magma.collect.list.List_<magma.io.Path_>
// expand magma.collect.map.Map_<String, String>
// expand magma.collect.list.List_<String>
// expand magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError>
// expand magma.collect.list.List_<magma.io.Path_>
// expand magma.collect.list.List_<magma.io.Path_>
// expand magma.collect.list.List_<magma.io.Path_>
// expand magma.result.Result<magma.io.Path_, magma.ApplicationError>
// expand magma.result.Result<magma.io.Path_, magma.ApplicationError>
// expand magma.option.Option<magma.io.IOError>
// expand magma.option.None<>
// expand magma.collect.list.List_<String>
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
auto __lambda6__();
auto __lambda7__();
auto __lambda8__();
auto __lambda9__();
auto __lambda10__();
magma.void main(String* args);
magma.option.Option<magma.ApplicationError> runWithFiles(magma.collect.set.Set_<magma.io.Path_> files);
magma.option.Option<magma.ApplicationError> runWithSources(magma.collect.set.Set_<magma.io.Path_> sources);
magma.collect.map.Map_<magma.io.Path_, magma.compile.Node> getPathNodeMap(magma.collect.map.Map_<magma.io.Path_, magma.compile.Node> trees);
String getString(magma.compile.Node element);
magma.collect.stream.Stream<magma.compile.Node> findExpansionsInTargetSet(magma.compile.Node value);
magma.collect.stream.Stream<magma.compile.Node> findExpansionsInRoot(magma.compile.Node value);
magma.option.Option<magma.ApplicationError> postLoadTrees(magma.collect.map.Map_<magma.io.Path_, magma.compile.Node> trees);
magma.result.Result<magma.collect.map.Map_<magma.io.Path_, magma.compile.Node>, magma.ApplicationError> preLoadSources(magma.collect.map.Map_<magma.io.Path_, magma.compile.Node> trees, magma.io.Path_ path);
magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError> postLoadTree(magma.collect.list.List_<magma.io.Path_> relatives, magma.option.Tuple<magma.io.Path_, magma.compile.Node> pathNodeTuple);
magma.option.Option<magma.ApplicationError> complete(magma.collect.list.List_<magma.io.Path_> relatives);
magma.option.Option<magma.ApplicationError> startCommand();
magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError> writeOutputs(magma.collect.map.Map_<String, String> output, magma.collect.list.List_<String> namespace, String name);
magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError> writeAndFoldOutput(magma.collect.list.List_<magma.io.Path_> current, magma.io.Path_ targetParent, String name, String extension, String output);
magma.result.Result<magma.io.Path_, magma.ApplicationError> writeOutput(magma.io.Path_ parent, String name, String extension, String output);
magma.option.Option<magma.io.IOError> ensureDirectories(magma.io.Path_ targetParent);
int isPlatformDependent(magma.collect.list.List_<String> namespace);
#endif
