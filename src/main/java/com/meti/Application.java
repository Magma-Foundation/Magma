package com.meti;

import com.meti.compile.MagmaCCompiler;
import com.meti.io.NIOPath;
import com.meti.module.Module;
import com.meti.source.Source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.meti.io.NIOPath.Root;

public class Application {
    public static final NIOPath Out = Root.resolveChild("out");
    protected static final String AppName = "Application";
    protected final Module module;

    public Application(Module module) {
        this.module = module;
    }

    void run() throws ApplicationException {
        var sources = listSources();
        var targets = new ArrayList<String>();
        for (var source : sources) {
            targets.add(Application.compile(source));
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

    private static String compile(Source source) throws ApplicationException {
        var name = source.computeName();
        var input = Application.readSource(source);
        var output = new MagmaCCompiler(input).compile();

        try {
            Out.ensureAsDirectory();
            var target = source.computePackage()
                    .reduce(Out, NIOPath::resolveChild, (previous, next) -> next)
                    .resolveChild(name + ".c")
                    .ensureAsFile()
                    .writeAsString(output);
            return Out.relativize(target).asString();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    protected void build(Collection<String> targets) throws ApplicationException {
    }

    private static String readSource(Source source) throws ApplicationException {
        try {
            return source.read();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
