#include "Main.h"
struct void main(struct String* args){SOURCE_DIRECTORY.walk().mapErr(ApplicationError.new).match(Main.runWithFiles, Some.new).ifPresent(__lambda0__);
}
struct Option_ApplicationError runWithFiles(struct Set__Path_ files){Set_<Path_> collect = files.stream()
                .filter(Path_::isRegularFile)
                .filter(path -> path.asString().endsWith(".java"))
                .collect(new SetCollector<>());return runWithSources(collect);
}
struct Option_ApplicationError runWithSources(struct Set__Path_ sources){return sources.stream().foldToResult(Maps.empty(), Main.preLoadSources).match(Main.postLoadTrees, Some.new);
}
struct Option_ApplicationError postLoadTrees(struct Map__Path__Node trees){return trees.stream().foldToResult(Lists.empty(), Main.postLoadTree).match(Main.complete, Some.new);
}
struct Result_Map__Path__Node_ApplicationError preLoadSources(struct Map__Path__Node trees, struct Path_ path){Source source = new PathSource(SOURCE_DIRECTORY, path);
        List_<String> namespace = source.computeNamespace();
        String name = source.computeName();
        State state = new State(namespace, name);if (isPlatformDependent(namespace)) return new Ok<>(trees);return source.readString().mapErr(ApplicationError.new).flatMapValue(__lambda1__).mapValue(__lambda2__);
}
struct Result_List__Path__ApplicationError postLoadTree(struct List__Path_ relatives, struct Tuple_Path__Node pathNodeTuple){Path_ path = pathNodeTuple.left();
        Node tree = pathNodeTuple.right();

        Source source = new PathSource(SOURCE_DIRECTORY, path);
        List_<String> namespace = source.computeNamespace();return Compiler.postLoad(State(namespace, source.computeName()), tree).mapErr(ApplicationError.new).flatMapValue(__lambda3__).mapValue(relatives.addAll);
}
struct Option_ApplicationError complete(struct List__Path_ relatives){Path_ build = TARGET_DIRECTORY.resolve("build.bat");
        String collect = relatives.stream()
                .map(Path_::asString)
                .map(path -> ".\\" + path + "^\n\t")
                .collect(new Joiner(""))
                .orElse("");return build.writeString("clang " + collect + " -o main.exe").map(ApplicationError.new).or(Main.startCommand);
}
struct Option_ApplicationError startCommand(){return Processes.executeCommand(Lists.of("cmd.exe", "/c", "build.bat"), TARGET_DIRECTORY).map(ApplicationError.new);
}
struct Result_List__Path__ApplicationError writeOutputs(struct Map__String_String output, struct List__String namespace, struct String name){Path_ targetParent = namespace.stream().foldWithInitial(TARGET_DIRECTORY, Path_::resolve);return ensureDirectories(targetParent).map(ApplicationError.new).match(Err.new, __lambda5__);
}
struct Result_List__Path__ApplicationError writeAndFoldOutput(struct List__Path_ current, struct Path_ targetParent, struct String name, struct String extension, struct String output){return writeOutput(targetParent, name, extension, output).mapValue(current.add);
}
struct Result_Path__ApplicationError writeOutput(struct Path_ parent, struct String name, struct String extension, struct String output){return parent.resolve(name+extension).writeString(output).map(ApplicationError.new).match(Err.new, __lambda6__);
}
struct Option_IOError ensureDirectories(struct Path_ targetParent){if (!targetParent.exists()) return targetParent.createAsDirectories();return None_();
}
int isPlatformDependent(struct List__String namespace){return namespace.findFirst().filter(__lambda7__).isPresent();
}
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda5__();
auto __lambda6__();
auto __lambda7__();

