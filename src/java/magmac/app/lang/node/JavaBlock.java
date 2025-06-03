package magmac.app.lang.node;

import magmac.api.collect.list.List;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;

public final class JavaBlock extends Block<JavaBlockHeader, JavaFunctionSegment> implements JavaFunctionSegment {
    public JavaBlock(JavaBlockHeader header, List<JavaFunctionSegment> segments) {
        super(header, segments);
    }
}
