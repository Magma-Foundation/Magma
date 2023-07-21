package com.meti.app;

import com.meti.java.JavaString;
import com.meti.java.List;
import com.meti.java.String_;

import static com.meti.java.JavaString.fromSlice;

public record Class_(String_ name, List<Renderable> parameters, Renderable body) implements Renderable {
    @Override
    public String_ render() {
        var joinedParameters = parameters.iter()
                .map(Renderable::render)
                .collect(JavaString.joining(fromSlice(",")))
                .unwrapOrElse(JavaString.Empty);

        return fromSlice("class def " + name().unwrap() + "(" + joinedParameters.unwrap() + ") => " + body().render().unwrap());
    }
}