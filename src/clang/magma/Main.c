#include "Main.h"
int __lambda0__(){return System;
}
int __lambda1__(){return writeOutputs;
}
int __lambda2__(){return Compiler;
}
int __lambda3__(){return trees;
}
int __lambda4__(){return output;
}
int __lambda5__(){return writeAndFoldOutput;
}
int __lambda6__(){return (TARGET_DIRECTORY.relativize(parent.resolve(name+".c")));
}
int __lambda7__(){return first;
}
void main(String* args){SOURCE_DIRECTORY.walk().mapErr(ApplicationError.new).match(Main.runWithFiles, Some.new).ifPresent(__lambda0__.err.println(error.display()));
}
Option<ApplicationError> runWithFiles(Set_<Path_> files){Set_<Path_> collect = files.stream()
                .filter(Path_::isRegularFile)
                .filter(path -> path.asString().endsWith(".java"))
                .collect(new SetCollector<>());return runWithSources(collect);
}
Option<ApplicationError> runWithSources(Set_<Path_> sources){return sources.stream().foldToResult(Maps.empty(), Main.preLoadSources).mapValue(Main.modifyTrees).match(Main.postLoadTrees, Some.new);
}
Map_<Location, Node> modifyTrees(Map_<Location, Node> trees){Location location = new Location(Lists.of("magma"), "Generated");
        MapNode block = new MapNode("block");
        MapNode root = new MapNode("root");return trees.with(location, root.withNode("content", block));
}
Option<ApplicationError> postLoadTrees(Map_<Location, Node> trees){return trees.stream().foldToResult(Lists.empty(), Main.postLoadTree).match(Main.complete, Some.new);
}
Result<List_<Path_>, ApplicationError> postLoadTree(List_<Path_> relatives, Tuple<Location, Node> pathNodeTuple){Location location = pathNodeTuple.left();
        List_<String> namespace = location.namespace();return Compiler.postLoad(State(location), pathNodeTuple.right()).mapErr(ApplicationError.new).flatMapValue(__lambda1__(postLoaded, namespace, location.name())).mapValue(relatives.addAll);
}
Result<Map_<Location, Node>, ApplicationError> preLoadSources(Map_<Location, Node> trees, Path_ path){System.out.println("Loading: "+path.asString());

        Source source = new PathSource(SOURCE_DIRECTORY, path);
        Location location = source.location();
        State state = new State(location);if (isPlatformDependent(location)) return new Ok<>(trees);return source.readString().mapErr(ApplicationError.new).flatMapValue(__lambda2__.preLoad(input, state).mapErr(ApplicationError.new)).mapValue(__lambda3__.with(location, node));
}
Option<ApplicationError> complete(List_<Path_> relatives){Path_ build = TARGET_DIRECTORY.resolve("build.bat");
        String collect = relatives.stream()
                .map(Path_::asString)
                .map(path -> ".\\" + path + "^\n\t")
                .collect(new Joiner(""))
                .orElse("");return build.writeString("clang " + collect + " -o main.exe").map(ApplicationError.new).or(Main.startCommand);
}
Option<ApplicationError> startCommand(){return Processes.executeCommand(Lists.of("cmd.exe", "/c", "build.bat"), TARGET_DIRECTORY).map(ApplicationError.new);
}
Result<List_<Path_>, ApplicationError> writeOutputs(Map_<String, String> output, List_<String> namespace, String name){Path_ targetParent = namespace.stream().foldWithInitial(TARGET_DIRECTORY, Path_::resolve);return ensureDirectories(targetParent).map(ApplicationError.new).match(Err.new, __lambda4__.stream().foldToResult(Lists.empty(), __lambda5__(pathList, targetParent, name, tuple.left(), tuple.right())));
}
Result<List_<Path_>, ApplicationError> writeAndFoldOutput(List_<Path_> current, Path_ targetParent, String name, String extension, String output){return writeOutput(targetParent, name, extension, output).mapValue(current.add);
}
Result<Path_, ApplicationError> writeOutput(Path_ parent, String name, String extension, String output){return parent.resolve(name+extension).writeString(output).map(ApplicationError.new).match(Err.new, __lambda6__);
}
Option<IOError> ensureDirectories(Path_ targetParent){if (!targetParent.exists()) return targetParent.createAsDirectories();return ();
}
int isPlatformDependent(Location location){return location.namespace().findFirst().filter(__lambda7__.equals("jvm")).isPresent();
}
