#include "/../jvm/collect/list/Lists.h"
#include "/../jvm/io/Paths.h"
#include "/../jvm/process/Processes.h"
#include "/../magma/collect/stream/Joiner.h"
#include "/../magma/collect/list/List_.h"
#include "/../magma/collect/set/SetCollector.h"
#include "/../magma/collect/set/Set_.h"
#include "/../magma/compile/Compiler.h"
#include "/../magma/compile/source/PathSource.h"
#include "/../magma/compile/source/Source.h"
#include "/../magma/io/IOError.h"
#include "/../magma/io/Path_.h"
#include "/../magma/option/None.h"
#include "/../magma/option/Option.h"
#include "/../magma/option/Some.h"
#include "/../magma/result/Err.h"
#include "/../magma/result/Ok.h"
#include "/../magma/result/Result.h"
struct Main{struct Path_ SOURCE_DIRECTORYstruct Path_ TARGET_DIRECTORY};
struct void main(struct String* args){
}
struct Option_ApplicationError runWithFiles(struct Set__Path_ files){
}
struct Option_ApplicationError runWithSources(struct Set__Path_ sources){
}
struct Option_ApplicationError complete(struct List__Path_ relatives){
}
struct Option_ApplicationError startCommand(){
}
struct Result_List__Path__ApplicationError foldIntoRelatives(struct List__Path_ relatives, struct Path_ path){
}
struct Result_Option_Path__ApplicationError compileSource(struct Source source){
}
struct Result_Option_Path__ApplicationError writeOutput(struct Path_ parent, struct String output, struct String name){
}
struct Option_IOError ensureDirectories(struct Path_ targetParent){
}
int isPlatformDependent(struct List__String namespace){
}
int main(){
	return 0;
}
