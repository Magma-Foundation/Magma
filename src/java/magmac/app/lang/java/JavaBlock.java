package magmac.app.lang.java;

import magmac.api.collect.list.List;
import magmac.app.lang.node.Block;

public final class JavaBlock extends Block<JavaLang.JavaBlockHeader, JavaFunctionSegment> implements JavaFunctionSegment {
    public JavaBlock(JavaLang.JavaBlockHeader header, List<JavaFunctionSegment> segments) {
        super(header, segments);
    }
}
