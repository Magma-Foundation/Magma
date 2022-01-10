package com.meti;

import com.meti.compile.MagmaCCompiler;
import com.meti.compile.Output;
import com.meti.compile.clang.CFormat;
import com.meti.io.Path;
import com.meti.module.Module;
import com.meti.source.Package;
import com.meti.source.Source;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.meti.io.NIOPath.Root;

public class Application {
    public static final Path Out = Root.resolveChild("out");
    protected static final String AppName = "Application";
    protected final Module module;

    public Application(Module module) {
        this.module = module;
    }

    void run() throws ApplicationException {
        var sources = listSources();
        var inputMap = new HashMap<Package, String>();
        for (var source : sources) {
            try {
                inputMap.put(source.computePackage(), source.read());
            } catch (IOException e) {
                throw new ApplicationException(e);
            }
        }
        var outputMap = new MagmaCCompiler(inputMap).compile();
        var targets = new HashSet<String>();
        for (Package aPackage : outputMap.keySet()) {
            var output = compile(aPackage, outputMap.get(aPackage));
            targets.addAll(output);
        }

        build(targets);
    }

    private List<Source> listSources() throws ApplicationException {
        List<Source> sources;
        try {
            sources = module.listSources();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
        return sources;
    }

    private static Set<String> compile(Package package_, Output<String> output) throws ApplicationException {
        try {
            Out.ensureAsDirectory();

            var packagePath = package_.parent()
                    .reduce(Out, Path::resolveChild, (previous, next) -> next);
            packagePath.ensureAsDirectory();

            var targets = new HashSet<String>();
            var formats = output.streamFormats().collect(Collectors.toList());
            for (CFormat format : formats) {
                var target = packagePath
                        .resolveChild(package_.computeName() + "." + format.getExtension())
                        .ensureAsFile()
                        .writeAsString(output.apply(format, ""));
                targets.add(Out.relativize(target)
                        .asString()
                        .replace("\\", "//"));
            }
            return targets;
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    protected void build(Collection<String> targets) throws ApplicationException {
    }
}
