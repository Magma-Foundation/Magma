package com.meti.app;

import com.meti.java.JavaString;
import com.meti.java.List;
import com.meti.java.Set;
import com.meti.java.String_;

import static com.meti.java.JavaString.Empty;
import static com.meti.java.JavaString.fromSlice;

public record Function(Set<String_> keywords, String_ name, List<Renderable> parameters,
                       Renderable body) implements Renderable {
    @Override
    public String_ render() {
        var joinedParameters = parameters.iter()
                .map(Renderable::render)
                .collect(JavaString.joining(fromSlice(", ")))
                .unwrapOrElse(JavaString.Empty);

        var renderedKeywords = keywords.iter()
                .map(value -> value.append(" "))
                .collect(JavaString.joiningEmpty())
                .unwrapOrElse(Empty);

        return renderedKeywords.append("def")
                .appendOwned(name).append("(")
                .appendOwned(joinedParameters).append(") => ")
                .appendOwned(body.render());
    }
}