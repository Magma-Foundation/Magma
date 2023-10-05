package com.meti.compile;

import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.compile.block.BlockLexer;
import com.meti.compile.clazz.ClassLexer;
import com.meti.compile.function.RecordLexer;
import com.meti.compile.imports.ImportLexer;
import com.meti.compile.package_.PackageLexer;
import com.meti.compile.rule.RuleNodeLexers;
import com.meti.compile.trait.InterfaceLexer;

public final class JavaLexer extends CompoundLexer {
    private final JavaString type;

    public JavaLexer(JavaString stripped, JavaString type) {
        super(stripped);
        this.type = type;
    }

    @Override
    Iterator<NodeLexer> enumerateLexers() {
        return Iterators.from(
                RuleNodeLexers.createDeclarationLexer("definition = <type> \" \" (name)", input, type),
                new InterfaceLexer(input),
                new PackageLexer(input),
                new ImportLexer(input),
                new BlockLexer(input),
                new ClassLexer(input),
                new RecordLexer(input)
        );
    }
}