package magmac.app.lang.java;

import magmac.api.collect.list.List;
import magmac.app.lang.node.Block;
import magmac.app.lang.node.JavaBlockHeader;

public final class JavaBlock extends Block<JavaBlockHeader, JavaFunctionSegment> implements JavaFunctionSegment {
    public JavaBlock(JavaBlockHeader header, List<JavaFunctionSegment> segments) {
        super(header, segments);
    }
}
