package magma;

import jvm.collect.list.Lists;
import jvm.collect.map.Maps;
import jvm.io.Paths;
import jvm.process.Processes;
import magma.collect.list.List_;
import magma.collect.map.Map_;
import magma.collect.set.SetCollector;
import magma.collect.set.Set_;
import magma.collect.stream.Joiner;
import magma.compile.Compiler;
import magma.compile.MapNode;
import magma.compile.Node;
import magma.compile.source.Location;
import magma.compile.source.PathSource;
import magma.compile.source.Source;
import magma.compile.transform.State;
import magma.io.IOError;
import magma.io.Path_;
import magma.option.None;
import magma.option.Option;
import magma.option.Some;
import magma.option.Tuple;
import magma.result.Err;
import magma.result.Ok;
import magma.result.Result;

public class Main {
    public static final Path_ SOURCE_DIRECTORY = Paths.get(".", "src", "java");
    public static final Path_ TARGET_DIRECTORY = Paths.get(".", "src", "clang");


    public static void main(String[] args) {
        SOURCE_DIRECTORY.walk()
                .mapErr(ApplicationError::new)
                .match(Main::runWithFiles, Some::new)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Option<ApplicationError> runWithFiles(Set_<Path_> files) {
        Set_<Path_> collect = files.stream()
                .filter(Path_::isRegularFile)
                .filter(path -> path.asString().endsWith(".java"))
                .collect(new SetCollector<>());

        return runWithSources(collect);
    }

    private static Option<ApplicationError> runWithSources(Set_<Path_> sources) {
        return sources.stream()
                .foldToResult(Maps.empty(), Main::preLoadSources)
                .mapValue(Main::modifyTrees)
                .match(Main::postLoadTrees, Some::new);
    }

    private static Map_<Location, Node> modifyTrees(Map_<Location, Node> trees) {
        var collected = trees.streamValues()
                .filter(node -> node.is("group"))
                .flatMap(root -> root.findNodeList("expansions").orElse(Lists.empty()).stream())
                .foldWithInitial(Lists.empty(), Main::foldUniquely);

        Location location = new Location(Lists.of("magma"), "Generated");
        Node block = new MapNode("block").withNodeList("children", collected);
        Node root = new MapNode("root");
        return trees.with(location, root.withNode("content", block));
    }

    private static List_<Node> foldUniquely(List_<Node> nodeList, Node node) {
        if (nodeList.contains(node, Node::equalsTo)) return nodeList;

        if (hasTypeParam(node)) {
            return nodeList;
        }
        return nodeList.add(node);
    }

    private static boolean hasTypeParam(Node node) {
        return node.is("type-param")
                || node.streamNodes().map(Tuple::right).anyMatch(Main::hasTypeParam)
                || node.streamNodeLists().map(Tuple::right).flatMap(List_::stream).anyMatch(Main::hasTypeParam);
    }

    private static Option<ApplicationError> postLoadTrees(Map_<Location, Node> trees) {
        return trees.stream()
                .foldToResult(Lists.empty(), Main::postLoadTree)
                .match(Main::complete, Some::new);
    }

    private static Result<List_<Path_>, ApplicationError> postLoadTree(List_<Path_> relatives, Tuple<Location, Node> pathNodeTuple) {
        Location location = pathNodeTuple.left();
        List_<String> namespace = location.namespace();

        return Compiler.postLoad(new State(location), pathNodeTuple.right())
                .mapErr(ApplicationError::new)
                .flatMapValue(postLoaded -> writeOutputs(postLoaded, namespace, location.name()))
                .mapValue(relatives::addAll);
    }

    private static Result<Map_<Location, Node>, ApplicationError> preLoadSources(Map_<Location, Node> trees, Path_ path) {
        System.out.println("Loading: " + path.asString());

        Source source = new PathSource(SOURCE_DIRECTORY, path);
        Location location = source.location();
        State state = new State(location);

        if (isPlatformDependent(location)) return new Ok<>(trees);
        return source.readString()
                .mapErr(ApplicationError::new)
                .flatMapValue(input -> Compiler.preLoad(input, state).mapErr(ApplicationError::new))
                .mapValue(node -> trees.with(location, node));

    }

    private static Option<ApplicationError> complete(List_<Path_> relatives) {
        Path_ build = TARGET_DIRECTORY.resolve("build.bat");
        String collect = relatives.stream()
                .map(Path_::asString)
                .map(path -> ".\\" + path + "^\n\t")
                .collect(new Joiner(""))
                .orElse("");

        return build.writeString("clang " + collect + " -o main.exe")
                .map(ApplicationError::new)
                .or(Main::startCommand);
    }

    private static Option<ApplicationError> startCommand() {
        return Processes.executeCommand(Lists.of("cmd.exe", "/c", "build.bat"), TARGET_DIRECTORY).map(ApplicationError::new);
    }

    private static Result<List_<Path_>, ApplicationError> writeOutputs(
            Map_<String, String> output,
            List_<String> namespace,
            String name
    ) {
        Path_ targetParent = namespace.stream().foldWithInitial(TARGET_DIRECTORY, Path_::resolve);
        return ensureDirectories(targetParent)
                .map(ApplicationError::new)
                .match(Err::new, () -> output.stream().foldToResult(Lists.empty(), (pathList, tuple) -> writeAndFoldOutput(pathList, targetParent, name, tuple.left(), tuple.right())));
    }

    private static Result<List_<Path_>, ApplicationError> writeAndFoldOutput(
            List_<Path_> current,
            Path_ targetParent,
            String name,
            String extension,
            String output
    ) {
        return writeOutput(targetParent, name, extension, output).mapValue(current::add);
    }

    private static Result<Path_, ApplicationError> writeOutput(Path_ parent, String name, String extension, String output) {
        return parent.resolve(name + extension)
                .writeString(output)
                .map(ApplicationError::new)
                .<Result<Path_, ApplicationError>>match(Err::new,
                        () -> new Ok<>(TARGET_DIRECTORY.relativize(parent.resolve(name + ".c"))));
    }

    private static Option<IOError> ensureDirectories(Path_ targetParent) {
        if (!targetParent.exists()) return targetParent.createAsDirectories();
        return new None<>();
    }

    private static boolean isPlatformDependent(Location location) {
        return location.namespace()
                .findFirst()
                .filter(first -> first.equals("jvm"))
                .isPresent();
    }
}
