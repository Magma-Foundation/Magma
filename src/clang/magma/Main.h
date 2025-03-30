#ifndef magma_Main
#define magma_Main
#include "../windows/collect/list/Lists.h"
#include "../windows/io/Paths.h"
#include "../windows/process/Processes.h"
#include "../magma/collect/list/List_.h"
#include "../magma/collect/map/Map_.h"
#include "../magma/collect/set/SetCollector.h"
#include "../magma/collect/set/Set_.h"
#include "../magma/collect/stream/Joiner.h"
#include "../magma/compile/Compiler.h"
#include "../magma/compile/source/PathSource.h"
#include "../magma/compile/source/Source.h"
#include "../magma/io/IOError.h"
#include "../magma/io/Path_.h"
#include "../magma/option/None.h"
#include "../magma/option/Option.h"
#include "../magma/option/Some.h"
#include "../magma/result/Err.h"
#include "../magma/result/Ok.h"
#include "../magma/result/Result.h"
struct Main{struct Path_ SOURCE_DIRECTORYstruct Path_ TARGET_DIRECTORY};
struct void main(struct String* args);
Option<struct ApplicationError> runWithFiles(Set_<struct Path_> files);
Option<struct ApplicationError> runWithSources(Set_<struct Path_> sources);
Option<struct ApplicationError> complete(List_<struct Path_> relatives);
Option<struct ApplicationError> startCommand();
Result<List_<struct Path_>, struct ApplicationError> foldIntoRelatives(List_<struct Path_> relatives, struct Path_ path);
Result<List_<struct Path_>, struct ApplicationError> compileSource(struct Source source);
Result<List_<struct Path_>, struct ApplicationError> compileAndWrite(struct String input, List_<struct String> namespace, struct String name);
Result<List_<struct Path_>, struct ApplicationError> writeOutputs(Map_<struct String, struct String> output, List_<struct String> namespace, struct String name);
Result<List_<struct Path_>, struct ApplicationError> writeAndFoldOutput(List_<struct Path_> current, struct Path_ targetParent, struct String name, struct String extension, struct String output);
Result<struct Path_, struct ApplicationError> writeOutput(struct Path_ parent, struct String name, struct String extension, struct String output);
Option<struct IOError> ensureDirectories(struct Path_ targetParent);
int isPlatformDependent(List_<struct String> namespace);
#endif
