package com.meti.app.compile.clazz;

import com.meti.app.compile.MapNode;
import com.meti.app.compile.Node;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.StringAttribute;
import com.meti.app.compile.attribute.StringSetAttribute;
import com.meti.core.Option;
import com.meti.java.JavaMap;
import com.meti.java.JavaSet;
import com.meti.java.String_;

import static com.meti.core.Options.$$;
import static com.meti.core.Options.$Option;
import static com.meti.java.JavaString.fromSlice;

public record RecordTransformer(Node root) implements Transformer {
    @Override
    public Option<Node> transform() {
        return $Option(() -> {
            if (!root.is(fromSlice("record"))) return $$();

            var name = root.apply(root.has(fromSlice("name")).$()).asString().$();
            var body = root.apply(root.has(fromSlice("statements")).$());

            return new MapNode(fromSlice("implementation"), JavaMap.<String_, Attribute>empty()
                    .insert(fromSlice("keywords"), new StringSetAttribute(JavaSet.of(fromSlice("class"))))
                    .insert(fromSlice("name"), new StringAttribute(name))
                    .insert(fromSlice("statements"), body));
        });
    }
}
