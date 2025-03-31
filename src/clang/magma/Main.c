#include "Main.h"
magma.void main(String* args){SOURCE_DIRECTORY.walk().mapErr(ApplicationError.new).match(Main.runWithFiles, Some.new).ifPresent(__lambda0__.err.println(error.display()));
}
magma.option.Option<magma.ApplicationError> runWithFiles(magma.collect.set.Set_<magma.io.Path_> files){Set_<Path_> collect = files.stream()
                .filter(Path_::isRegularFile)
                .filter(path -> path.asString().endsWith(".java"))
                .collect(new SetCollector<>());return runWithSources(collect);
}
magma.option.Option<magma.ApplicationError> runWithSources(magma.collect.set.Set_<magma.io.Path_> sources){return sources.stream().foldToResult(Maps.empty(), Main.preLoadSources).mapValue(Main.getPathNodeMap).match(Main.postLoadTrees, Some.new);
}
magma.collect.map.Map_<magma.io.Path_, magma.compile.Node> getPathNodeMap(magma.collect.map.Map_<magma.io.Path_, magma.compile.Node> trees){trees.streamValues().flatMap(Main.findExpansionsInTargetSet).map(Main.getString).collect(()).stream().collect(()).sort(String.compareTo).forEach(System.out.println);return trees;
}
String getString(magma.compile.Node element){return CommonLang.createGenericRule(JavaLang.createTypeRule()).generate(element.retype("generic")).match(__lambda1__, __lambda2__);
}
magma.collect.stream.Stream<magma.compile.Node> findExpansionsInTargetSet(magma.compile.Node value){return value.streamNodes().map(Tuple.right).flatMap(Main.findExpansionsInRoot);
}
magma.collect.stream.Stream<magma.compile.Node> findExpansionsInRoot(magma.compile.Node value){return value.findNode("content").orElse(MapNode()).findNodeList("children").orElse(Lists.empty()).stream().filter(__lambda3__.is("expansion"));
}
magma.option.Option<magma.ApplicationError> postLoadTrees(magma.collect.map.Map_<magma.io.Path_, magma.compile.Node> trees){return trees.stream().foldToResult(Lists.empty(), Main.postLoadTree).match(Main.complete, Some.new);
}
magma.result.Result<magma.collect.map.Map_<magma.io.Path_, magma.compile.Node>, magma.ApplicationError> preLoadSources(magma.collect.map.Map_<magma.io.Path_, magma.compile.Node> trees, magma.io.Path_ path){System.out.println("Loading: "+path.asString());

        Source source = new PathSource(SOURCE_DIRECTORY, path);
        List_<String> namespace = source.computeNamespace();
        String name = source.computeName();
        State state = new State(namespace, name);if (isPlatformDependent(namespace)) return new Ok<>(trees);return source.readString().mapErr(ApplicationError.new).flatMapValue(__lambda4__.preLoad(input, state).mapErr(ApplicationError.new)).mapValue(__lambda5__.with(path, node));
}
magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError> postLoadTree(magma.collect.list.List_<magma.io.Path_> relatives, magma.option.Tuple<magma.io.Path_, magma.compile.Node> pathNodeTuple){Path_ path = pathNodeTuple.left();
        Node tree = pathNodeTuple.right();

        Source source = new PathSource(SOURCE_DIRECTORY, path);
        List_<String> namespace = source.computeNamespace();return Compiler.postLoad(State(namespace, source.computeName()), tree).mapErr(ApplicationError.new).flatMapValue(__lambda6__(postLoaded, namespace, source.computeName())).mapValue(relatives.addAll);
}
magma.option.Option<magma.ApplicationError> complete(magma.collect.list.List_<magma.io.Path_> relatives){Path_ build = TARGET_DIRECTORY.resolve("build.bat");
        String collect = relatives.stream()
                .map(Path_::asString)
                .map(path -> ".\\" + path + "^\n\t")
                .collect(new Joiner(""))
                .orElse("");return build.writeString("clang " + collect + " -o main.exe").map(ApplicationError.new).or(Main.startCommand);
}
magma.option.Option<magma.ApplicationError> startCommand(){return Processes.executeCommand(Lists.of("cmd.exe", "/c", "build.bat"), TARGET_DIRECTORY).map(ApplicationError.new);
}
magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError> writeOutputs(magma.collect.map.Map_<String, String> output, magma.collect.list.List_<String> namespace, String name){Path_ targetParent = namespace.stream().foldWithInitial(TARGET_DIRECTORY, Path_::resolve);return ensureDirectories(targetParent).map(ApplicationError.new).match(Err.new, __lambda7__.stream().foldToResult(Lists.empty(), __lambda8__(pathList, targetParent, name, tuple.left(), tuple.right())));
}
magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError> writeAndFoldOutput(magma.collect.list.List_<magma.io.Path_> current, magma.io.Path_ targetParent, String name, String extension, String output){return writeOutput(targetParent, name, extension, output).mapValue(current.add);
}
magma.result.Result<magma.io.Path_, magma.ApplicationError> writeOutput(magma.io.Path_ parent, String name, String extension, String output){return parent.resolve(name+extension).writeString(output).map(ApplicationError.new).match(Err.new, __lambda9__);
}
magma.option.Option<magma.io.IOError> ensureDirectories(magma.io.Path_ targetParent){if (!targetParent.exists()) return targetParent.createAsDirectories();return ();
}
magma.boolean isPlatformDependent(magma.collect.list.List_<String> namespace){return namespace.findFirst().filter(__lambda10__.equals("jvm")).isPresent();
}
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

