#include "Main.h"
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
auto __lambda11__();
magma.void main(String* args){SOURCE_DIRECTORY.walk().mapErr(ApplicationError.new).match(Main.runWithFiles, Some.new).ifPresent(__lambda0__.err.println(error.display()));
}
magma.option.Option<magma.ApplicationError> runWithFiles(magma.collect.set.Set_<magma.io.Path_> files){Set_<Path_> collect = files.stream()
                .filter(Path_::isRegularFile)
                .filter(path -> path.asString().endsWith(".java"))
                .collect(new SetCollector<>());return runWithSources(collect);
}
magma.option.Option<magma.ApplicationError> runWithSources(magma.collect.set.Set_<magma.io.Path_> sources){return sources.stream().foldToResult(Maps.empty(), Main.preLoadSources).mapValue(Main.getPathNodeMap).match(Main.postLoadTrees, Some.new);
}
magma.collect.map.Map_<magma.compile.source.Location, magma.compile.Node> getPathNodeMap(magma.collect.map.Map_<magma.compile.source.Location, magma.compile.Node> trees){Map_<Location, List_<Node>> expansions = trees.streamValues()
                .flatMap(Main::findExpansionsInTargetSet)
                .foldWithInitial(Maps.empty(), (map, expansion) -> foldIntoCache(map, trees, expansion));

        return expansions.stream().foldWithInitial(trees, (current, entry) -> {
            Location location = entry.left();
            List_<Node> mergedChildren = entry.right()
                    .stream()
                    .flatMap(node -> {
                        Node content = node.findNode("content")
                                .or(() -> node.findNode("child").flatMap(child -> child.findNode("content")))
                                .orElse(new MapNode());
                        List_<Node> children = content.findNodeList("children").orElse(Lists.empty());
                        return children.stream();
                    })
                    .collect(new ListCollector<>());

            Node block = new MapNode("block").withNodeList("children", mergedChildren);
            Node root = new MapNode("root").withNode("content", block);
            return current.with(location.resolveSibling(location.name() + "_expansion"), root);
        });
}
magma.collect.map.Map_<magma.compile.source.Location, magma.collect.list.List_<magma.compile.Node>> foldIntoCache(magma.collect.map.Map_<magma.compile.source.Location, magma.collect.list.List_<magma.compile.Node>> cache, magma.collect.map.Map_<magma.compile.source.Location, magma.compile.Node> trees, magma.compile.Node expansion){Node base = expansion.findNode("base").orElse(new MapNode());
        List_<String> segments = StringLists.fromQualified(base);return trees.stream().filter(__lambda1__(entry, segments)).next().map(__lambda2__(cache, entry)).orElse(cache);
}
magma.collect.map.Map_<magma.compile.source.Location, magma.collect.list.List_<magma.compile.Node>> foldEntry(magma.collect.map.Map_<magma.compile.source.Location, magma.collect.list.List_<magma.compile.Node>> map, magma.option.Tuple<magma.compile.source.Location, magma.compile.Node> entry){return map.ensure(entry.left(), __lambda3__.add(entry.right()), __lambda4__.of(entry.right()));
}
int isDefined(magma.option.Tuple<magma.compile.source.Location, magma.compile.Node> entry, magma.collect.list.List_<String> segments){Location location = entry.left();return location.namespace().add(location.name()).equalsTo(segments);
}
magma.collect.stream.Stream<magma.compile.Node> findExpansionsInTargetSet(magma.compile.Node value){return value.findNodeList("expansions").orElse(Lists.empty()).stream();
}
magma.option.Option<magma.ApplicationError> postLoadTrees(magma.collect.map.Map_<magma.compile.source.Location, magma.compile.Node> trees){return trees.stream().foldToResult(Lists.empty(), Main.postLoadTree).match(Main.complete, Some.new);
}
magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError> postLoadTree(magma.collect.list.List_<magma.io.Path_> relatives, magma.option.Tuple<magma.compile.source.Location, magma.compile.Node> pathNodeTuple){Location location = pathNodeTuple.left();
        List_<String> namespace = location.namespace();return Compiler.postLoad(State(location), pathNodeTuple.right()).mapErr(ApplicationError.new).flatMapValue(__lambda5__(postLoaded, namespace, location.name())).mapValue(relatives.addAll);
}
magma.result.Result<magma.collect.map.Map_<magma.compile.source.Location, magma.compile.Node>, magma.ApplicationError> preLoadSources(magma.collect.map.Map_<magma.compile.source.Location, magma.compile.Node> trees, magma.io.Path_ path){System.out.println("Loading: "+path.asString());

        Source source = new PathSource(SOURCE_DIRECTORY, path);
        Location location = source.location();
        State state = new State(location);if (isPlatformDependent(location)) return new Ok<>(trees);return source.readString().mapErr(ApplicationError.new).flatMapValue(__lambda6__.preLoad(input, state).mapErr(ApplicationError.new)).mapValue(__lambda7__.with(location, node));
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
magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError> writeOutputs(magma.collect.map.Map_<String, String> output, magma.collect.list.List_<String> namespace, String name){Path_ targetParent = namespace.stream().foldWithInitial(TARGET_DIRECTORY, Path_::resolve);return ensureDirectories(targetParent).map(ApplicationError.new).match(Err.new, __lambda8__.stream().foldToResult(Lists.empty(), __lambda9__(pathList, targetParent, name, tuple.left(), tuple.right())));
}
magma.result.Result<magma.collect.list.List_<magma.io.Path_>, magma.ApplicationError> writeAndFoldOutput(magma.collect.list.List_<magma.io.Path_> current, magma.io.Path_ targetParent, String name, String extension, String output){return writeOutput(targetParent, name, extension, output).mapValue(current.add);
}
magma.result.Result<magma.io.Path_, magma.ApplicationError> writeOutput(magma.io.Path_ parent, String name, String extension, String output){return parent.resolve(name+extension).writeString(output).map(ApplicationError.new).match(Err.new, __lambda10__);
}
magma.option.Option<magma.io.IOError> ensureDirectories(magma.io.Path_ targetParent){if (!targetParent.exists()) return targetParent.createAsDirectories();return ();
}
int isPlatformDependent(magma.compile.source.Location location){return location.namespace().findFirst().filter(__lambda11__.equals("jvm")).isPresent();
}
