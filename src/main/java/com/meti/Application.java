package com.meti;

import com.meti.io.NIOPath;
import com.meti.module.Module;
import com.meti.source.Source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.meti.io.NIOPath.Root;

public abstract class Application {
    public static final NIOPath Out = Root.resolveChild("out");
    protected static final String AppName = "Application";
    protected final Module module;

    public Application(Module module) {
        this.module = module;
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

    private static String readSource(Source source) throws ApplicationException {
        try {
            return source.read();
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    protected abstract void build(ArrayList<String> targets) throws ApplicationException;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Application) obj;
        return Objects.equals(this.module, that.module);
    }

    @Override
    public int hashCode() {
        return Objects.hash(module);
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

    void run() throws ApplicationException {
        var sources = listSources();
        var targets = new ArrayList<String>();
        for (var source : sources) {
            targets.add(Application.compile(source));
        }

        build(targets);
    }

    @Override
    public String toString() {
        return "Application[" +
               "module=" + module + ']';
    }
}
