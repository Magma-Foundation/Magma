#include "Main.h"
struct void main(struct String* args}{SOURCE_DIRECTORY.walk()
                .mapErr(ApplicationError::new)
                .match(Main::runWithFiles, Some::new)
                .ifPresent(error -> System.err.println(error.display()));}Option<struct ApplicationError> runWithFiles(Set_<struct Path_> files}{Set_<Path_> collect = files.stream()
                .filter(Path_::isRegularFile)
                .filter(path -> path.asString().endsWith())
                .collect(new SetCollector<>());

        return runWithSources(collect);}Option<struct ApplicationError> runWithSources(Set_<struct Path_> sources}{return sources.stream()
                .foldToResult(Lists.empty(), Main::foldIntoRelatives)
                .match(Main::complete, Some::new);}Option<struct ApplicationError> complete(List_<struct Path_> relatives}{Path_ build = TARGET_DIRECTORY.resolve();
        String collect = relatives.stream()
                .map(Path_::asString)
                .map(path ->  + path + )
                .collect(new Joiner())
                .orElse();

        return build.writeString( + collect + )
                .map(ApplicationError::new)
                .or(Main::startCommand);}Option<struct ApplicationError> startCommand(}{return Processes.executeCommand(Lists.of(, , ), TARGET_DIRECTORY).map(ApplicationError::new);}Result<List_<struct Path_>, struct ApplicationError> foldIntoRelatives(List_<struct Path_> relatives, struct Path_ path}{return compileSource(new PathSource(SOURCE_DIRECTORY, path)).mapValue(relatives::addAll);}Result<List_<struct Path_>, struct ApplicationError> compileSource(struct Source source}{List_<String> namespace = source.computeNamespace();
        if (isPlatformDependent(namespace)) return new Ok<>(Lists.empty());

        String name = source.computeName();
        return source.readString()
                .mapErr(ApplicationError::new)
                .flatMapValue(input -> compileAndWrite(input, namespace, name));}Result<List_<struct Path_>, struct ApplicationError> compileAndWrite(struct String input, List_<struct String> namespace, struct String name}{return Compiler.compile(input, namespace, name)
                .mapErr(ApplicationError::new)
                .flatMapValue(output -> writeOutputs(output, namespace, name));}Result<List_<struct Path_>, struct ApplicationError> writeOutputs(Map_<struct String, struct String> output, List_<struct String> namespace, struct String name}{Path_ targetParent = namespace.stream().foldWithInitial(TARGET_DIRECTORY, Path_::resolve);
        return ensureDirectories(targetParent)
                .map(ApplicationError::new)
                .match(Err::new, () -> output.stream().foldToResult(Lists.empty(), (pathList, tuple) -> writeAndFoldOutput(pathList, targetParent, name, tuple.left(), tuple.right())));}Result<List_<struct Path_>, struct ApplicationError> writeAndFoldOutput(List_<struct Path_> current, struct Path_ targetParent, struct String name, struct String extension, struct String output}{return writeOutput(targetParent, name, extension, output).mapValue(current::add);}Result<struct Path_, struct ApplicationError> writeOutput(struct Path_ parent, struct String name, struct String extension, struct String output}{return parent.resolve(name + extension)
                .writeString(output)
                .map(ApplicationError::new)
                .<Result<Path_, ApplicationError>>match(Err::new,
                        () -> new Ok<>(TARGET_DIRECTORY.relativize(parent.resolve(name + ))));}Option<struct IOError> ensureDirectories(struct Path_ targetParent}{if (!targetParent.exists()) return targetParent.createAsDirectories();
        return new None<>();}int isPlatformDependent(List_<struct String> namespace}{return namespace.findFirst()
                .filter(first -> first.equals())
                .isPresent();}