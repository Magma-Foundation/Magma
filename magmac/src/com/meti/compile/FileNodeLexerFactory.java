package com.meti.compile;

import com.meti.api.collect.Collectors;
import com.meti.api.collect.ImmutableLists;
import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.result.Result;
import com.meti.compile.rule.RuleException;
import com.meti.compile.rule.RuleNodeLexers;

public class FileNodeLexerFactory {
    private final JavaString content;

    public FileNodeLexerFactory(JavaString content) {
        this.content = content;
    }

    public Result<CompoundLexer, RuleException> create(JavaString input, JavaString type) {
        return content.split(";")
                .map(JavaString::strip)
                .filter(value -> !value.isBlank())
                .map(line -> RuleNodeLexers.create(line.value(), input, type))
                .collect(Collectors.exceptionally(ImmutableLists.into()))
                .mapValue(value -> {
                    return new CompoundLexer() {
                        @Override
                        Iterator<? extends NodeLexer> enumerateLexers() {
                            return value.iter();
                        }
                    };
                });
    }
}
