package com.meti.app;

import com.meti.api.collect.java.List;
import com.meti.api.collect.map.Maps;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.io.Path;
import com.meti.app.compile.stage.CMagmaCompiler;
import com.meti.app.compile.stage.Target;
import com.meti.app.module.Module;
import com.meti.app.source.Packaging;
import com.meti.app.source.Source;

import java.io.IOException;

import static com.meti.api.io.NIOPath.Root;

public class Application {
    public static final Path Out = Root.resolveChild("out");
    protected static final String AppName = "Application";
    protected final Module module;

    public Application(Module module) {
        this.module = module;
    }

    void run() throws ApplicationException {
        try {
            var sources = streamSources()
                    .foldRight(Maps.<Packaging, String>empty(), (packagingStringMap, source) -> packagingStringMap.put(source.computePackage(), source.read()));

            var outputMap = new CMagmaCompiler(sources).compile();
            var targets = outputMap.streamKeys()
                    .flatMap(key -> compile(key, outputMap.apply(key)))
                    .foldRight(List.<String>createList(), List::add);

            build(targets);
        } catch (StreamException | IOException e) {
            throw new ApplicationException(e);
        }
    }

    private Stream<Source> streamSources() throws ApplicationException {
        try {
            return module.streamSources();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private static Stream<String> compile(Packaging packaging_, Target<String> output) throws ApplicationException {
        try {
            Out.ensureAsDirectory();

            var packagePath = packaging_.streamParent()
                    .reduce(Out, Path::resolveChild, (previous, next) -> next);
            packagePath.ensureAsDirectory();

            return output.streamFormats().map(format -> {
                var target = packagePath
                        .resolveChild(packaging_.computeName() + "." + format.getExtension())
                        .ensureAsFile()
                        .writeAsString(output.apply(format, ""));
                return Out.relativize(target)
                        .asString()
                        .replace("\\", "//");
            });
        } catch (IOException | StreamException e) {
            throw new ApplicationException(e);
        }
    }

    protected void build(List<String> targets) throws ApplicationException {
    }
}
