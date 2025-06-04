package magmac.app.lang.web;

import magmac.app.compile.node.InlineNodeList;
import magmac.app.compile.node.MapNode;
import magmac.app.compile.node.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndentationTest {
    @Test
    public void addsIndentationToStructure() {
        Node block = new MapNode("block").withNodeList("children", InlineNodeList.empty());
        Node method = new MapNode("method").withNodeList("children", InlineNodeList.of(block));
        Node clazz = new MapNode("class").withNodeList("members", InlineNodeList.of(method));
        Node root = new MapNode("root").withNodeList("children", InlineNodeList.of(clazz));

        Indentation.apply(root);

        Node classOut = root.findNodeList("children").orElse(null).iter().next().orElse(null);
        assertEquals("\n", classOut.findString("before").orElse(null));
        assertEquals("\n", classOut.findString("after").orElse(null));

        Node methodOut = classOut.findNodeList("members").orElse(null).iter().next().orElse(null);
        assertEquals("\n\t", methodOut.findString("before").orElse(null));
        assertEquals("\n\t", methodOut.findString("after").orElse(null));

        Node blockOut = methodOut.findNodeList("children").orElse(null).iter().next().orElse(null);
        assertEquals("\n\t\t", blockOut.findString("before").orElse(null));
        assertEquals("\n\t\t", blockOut.findString("after").orElse(null));
    }
}
