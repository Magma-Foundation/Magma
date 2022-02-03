package com.meti.app.compile.feature.scope;

import com.meti.api.collect.java.List;
import com.meti.app.compile.common.Definition;
import com.meti.app.compile.node.Type;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;

public class Declaration extends Definition {
    public Declaration(String name, Type type, Flag... flags) {
        this(new RootText(name), type, flags);
    }

    public Declaration(Input name, Type type, Flag... flags) {
        this(name, type, List.apply(flags));
    }

    public Declaration(Input name, Type type, List<Flag> flags) {
        super(flags, name, type);
    }

    @Override
    protected Definition complete(Input name, Type type) {
        return new Declaration(name, type, flags);
    }

    @Override
    public boolean is(Category category) {
        return category == Category.Declaration;
    }
}
