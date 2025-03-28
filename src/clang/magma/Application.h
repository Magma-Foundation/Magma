#ifndef magma_Application
#define magma_Application
#include "../windows/api/collect/Lists.h"
#include "../windows/api/io/JavaPaths.h"
#include "../magma/api/io/Path_.h"
#include "../magma/api/process/Process_.h"
#include "../windows/api/process/Processes.h"
#include "../windows/app/compile/PathSource.h"
#include "../magma/api/collect/List_.h"
#include "../magma/api/option/None.h"
#include "../magma/api/option/Option.h"
#include "../magma/api/option/Some.h"
#include "../magma/api/result/Err.h"
#include "../magma/api/result/Ok.h"
#include "../magma/api/result/Result.h"
#include "../magma/api/result/Tuple.h"
#include "../magma/app/ApplicationError.h"
#include "../magma/app/compile/ImmutableState.h"
#include "../magma/api/concurrent/InterruptedError.h"
#include "../magma/app/compile/Source.h"
#include "../magma/app/compile/State.h"
#include "../java/util/ArrayList.h"
#include "../java/util/List.h"
#include "../java/util/Set.h"
#include "../java/util/stream/Collectors.h"
struct Application {
};
#endif
