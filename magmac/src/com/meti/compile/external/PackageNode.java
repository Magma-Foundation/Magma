package com.meti.compile.external;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.compile.node.Node;

public class PackageNode implements Node {
    @Override
    public Option<String> render() {
        return None.None();
    }

    @Override
    public boolean is(String name) {
        return name.equals("package");
    }
}
