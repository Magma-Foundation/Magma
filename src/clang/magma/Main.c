#include "Main.h"
struct void main(struct String* args);
struct Option_ApplicationError runWithFiles(struct Set__Path_ files);
struct Option_ApplicationError runWithSources(struct Set__Path_ sources);
struct Option_ApplicationError complete(struct List__Path_ relatives);
struct Option_ApplicationError startCommand();
struct Result_List__Path__ApplicationError foldIntoRelatives(struct List__Path_ relatives, struct Path_ path);
struct Result_List__Path__ApplicationError compileSource(struct Source source);
struct Result_List__Path__ApplicationError compileAndWrite(struct String input, struct List__String namespace, struct String name);
struct Result_List__Path__ApplicationError writeOutputs(struct Map__String_String output, struct List__String namespace, struct String name);
struct Result_List__Path__ApplicationError writeAndFoldOutput(struct List__Path_ current, struct Path_ targetParent, struct String name, struct String extension, struct String output);
struct Result_Path__ApplicationError writeOutput(struct Path_ parent, struct String name, struct String extension, struct String output);
struct Option_IOError ensureDirectories(struct Path_ targetParent);
int isPlatformDependent(struct List__String namespace);
